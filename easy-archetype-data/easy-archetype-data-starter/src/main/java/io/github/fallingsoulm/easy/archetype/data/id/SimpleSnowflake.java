package io.github.fallingsoulm.easy.archetype.data.id;

/**
 * @author luyanan
 * @since 2021/5/14
 **/

import cn.hutool.core.date.SystemClock;
import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 分布式id
 *
 * @author luyanan
 * @since 2021/5/13
 **/

public class SimpleSnowflake implements Serializable {
	private static final long serialVersionUID = 1L;

	private final long twepoch;
	private final long workerIdBits = 1L;
	private final long dataCenterIdBits = 1L;
	//// 最大支持机器节点数0~31，一共32个
	// 最大支持数据中心节点数0~31，一共32个
	@SuppressWarnings({"PointlessBitwiseExpression", "FieldCanBeLocal"})
	private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
	@SuppressWarnings({"PointlessBitwiseExpression", "FieldCanBeLocal"})
	private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
	// 序列号12位
	private final long sequenceBits = 4;
	// 机器节点左移12位
	private final long workerIdShift = sequenceBits;
	// 数据中心节点左移17位
	private final long dataCenterIdShift = sequenceBits + workerIdBits;
	// 时间毫秒数左移22位
	private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
	@SuppressWarnings({"PointlessBitwiseExpression", "FieldCanBeLocal"})
	private final long sequenceMask = -1L ^ (-1L << sequenceBits);// 4095

	private final long workerId;
	private final long dataCenterId;
	private final boolean useSystemClock;
	private long sequence = 0L;
	private long lastTimestamp = -1L;

	/**
	 * 构造
	 *
	 * @param workerId     终端ID
	 * @param dataCenterId 数据中心ID
	 */
	public SimpleSnowflake(long workerId, long dataCenterId) {
		this(workerId, dataCenterId, false);
	}

	/**
	 * 构造
	 *
	 * @param workerId         终端ID
	 * @param dataCenterId     数据中心ID
	 * @param isUseSystemClock 是否使用{@link SystemClock} 获取当前时间戳
	 */
	public SimpleSnowflake(long workerId, long dataCenterId, boolean isUseSystemClock) {
		this(null, workerId, dataCenterId, isUseSystemClock);
	}

	/**
	 * @param epochDate        初始化时间起点（null表示默认起始日期）,后期修改会导致id重复,如果要修改连workerId dataCenterId，慎用
	 * @param workerId         工作机器节点id
	 * @param dataCenterId     数据中心id
	 * @param isUseSystemClock 是否使用{@link SystemClock} 获取当前时间戳
	 * @since 5.1.3
	 */
	public SimpleSnowflake(Date epochDate, long workerId, long dataCenterId, boolean isUseSystemClock) {
		if (null != epochDate) {
			this.twepoch = epochDate.getTime();
		} else {
			// Thu, 04 Nov 2010 01:42:54 GMT
			this.twepoch = 1288834974657L;
		}
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(StrUtil.format("worker Id can't be greater than {} or less than 0", maxWorkerId));
		}
		if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
			throw new IllegalArgumentException(StrUtil.format("datacenter Id can't be greater than {} or less than 0", maxDataCenterId));
		}
		this.workerId = workerId;
		this.dataCenterId = dataCenterId;
		this.useSystemClock = isUseSystemClock;
	}

	/**
	 * 根据Snowflake的ID，获取机器id
	 *
	 * @param id snowflake算法生成的id
	 * @return 所属机器的id
	 */
	public long getWorkerId(long id) {
		return id >> workerIdShift & ~(-1L << workerIdBits);
	}

	/**
	 * 根据Snowflake的ID，获取数据中心id
	 *
	 * @param id snowflake算法生成的id
	 * @return 所属数据中心
	 */
	public long getDataCenterId(long id) {
		return id >> dataCenterIdShift & ~(-1L << dataCenterIdBits);
	}

	/**
	 * 根据Snowflake的ID，获取生成时间
	 *
	 * @param id snowflake算法生成的id
	 * @return 生成的时间
	 */
	public long getGenerateDateTime(long id) {
		return (id >> timestampLeftShift & ~(-1L << 41L)) + twepoch;
	}

	/**
	 * 下一个ID
	 *
	 * @return ID
	 */
	public synchronized long nextId() {
		long timestamp = genTime();
		if (timestamp < lastTimestamp) {
			if (lastTimestamp - timestamp < 2000) {
				// 容忍2秒内的回拨，避免NTP校时造成的异常
				timestamp = lastTimestamp;
			} else {
				// 如果服务器时间有问题(时钟后退) 报错。
				throw new IllegalStateException(StrUtil.format("Clock moved backwards. Refusing to generate id for {}ms", lastTimestamp - timestamp));
			}
		}

		if (timestamp == lastTimestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}

		lastTimestamp = timestamp;

		return (((timestamp - twepoch)) << timestampLeftShift) | (dataCenterId << dataCenterIdShift) | (workerId << workerIdShift) | sequence;
	}

	/**
	 * 下一个ID（字符串形式）
	 *
	 * @return ID 字符串形式
	 */
	public String nextIdStr() {
		return Long.toString(nextId());
	}

	// ------------------------------------------------------------------------------------------------------------------------------------ Private method start

	/**
	 * 循环等待下一个时间
	 *
	 * @param lastTimestamp 上次记录的时间
	 * @return 下一个时间
	 */
	private long tilNextMillis(long lastTimestamp) {
		long timestamp = genTime();
		// 循环直到操作系统时间戳变化
		while (timestamp == lastTimestamp) {
			timestamp = genTime();
		}
		if (timestamp < lastTimestamp) {
			// 如果发现新的时间戳比上次记录的时间戳数值小，说明操作系统时间发生了倒退，报错
			throw new IllegalStateException(
					StrUtil.format("Clock moved backwards. Refusing to generate id for {}ms", lastTimestamp - timestamp));
		}
		return timestamp;
	}

	/**
	 * 生成时间戳
	 *
	 * @return 时间戳
	 */
	private long genTime() {
		return this.useSystemClock ? SystemClock.now() : System.currentTimeMillis();
	}
	// ------------------------------------------------------------------------------------------------------------------------------------ Private method end


	public static void main(String[] args) {
		SimpleSnowflake simpleSnowflake = new SimpleSnowflake(0, 0);
		final Set<Long> sets = Collections.synchronizedSet(new HashSet<>());
		Long count = 10000000L;
		ThreadPoolExecutor executor = ExecutorBuilder.create()
				.setCorePoolSize(1000)
				.setMaxPoolSize(1000)
				.setWorkQueue(new LinkedBlockingQueue<>()).build();

		for (Long i = 0L; i < count; i++) {
			executor.execute(() -> {
				long nextId = simpleSnowflake.nextId();
				sets.add(nextId);
			});
		}
		executor.shutdown();

		while (true) {
			if (executor.isTerminated()) {
				System.out.println("size:" + sets.size() + "--" + (sets.size() == count.intValue()));
				break;
			}
		}
	}
}
