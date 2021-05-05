package io.github.fallingsoulm.easy.archetype.framework.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

/**
 * 业务线程池
 *
 * @author luyanan
 * @since 2021/1/22
 **/
@Slf4j
public class BusinessThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

	public final static String BEAN_NAME = "businessThreadPoolTaskExecutor";

	private ObjectProvider<BusinessThreadInterceptor> businessThreadInterceptorObjectProvider;

	public static InheritableThreadLocal<Map<String, Object>> threadLocal = new InheritableThreadLocal<>();

	public BusinessThreadPoolTaskExecutor(
			ObjectProvider<BusinessThreadInterceptor> businessThreadInterceptorObjectProvider) {
		this.businessThreadInterceptorObjectProvider = businessThreadInterceptorObjectProvider;
	}

	@Override
	public void execute(Runnable task) {

		// 先执行主线程拦截器
		businessThreadInterceptorObjectProvider.orderedStream().forEachOrdered(a -> {
			a.main(threadLocal);
		});

		// 执行子线程
		new AsyncRunnable(task, threadLocal, businessThreadInterceptorObjectProvider).run();
	}

	public static class AsyncRunnable implements Runnable {

		private Runnable runnable;

		private HashMap hashMap = new HashMap();

		private InheritableThreadLocal<Map<String, Object>> threadLocal;

		private ObjectProvider<BusinessThreadInterceptor> businessThreadInterceptorObjectProvider;

		public AsyncRunnable(Runnable runnable, InheritableThreadLocal<Map<String, Object>> threadLocal,
				ObjectProvider<BusinessThreadInterceptor> businessThreadInterceptorObjectProvider) {
			this.runnable = runnable;
			this.threadLocal = threadLocal;
			this.businessThreadInterceptorObjectProvider = businessThreadInterceptorObjectProvider;
			copy(runnable, threadLocal);
		}

		@Override
		public void run() {
			try {
				threadLocal.set(hashMap);
				businessThreadInterceptorObjectProvider.orderedStream().forEachOrdered(a -> {
					a.childThread(threadLocal);
				});
				runnable.run();
			}
			finally {
				hashMap = new HashMap(16);
				threadLocal.remove();
			}

		}

		private void copy(Runnable runnable, InheritableThreadLocal<Map<String, Object>> threadLocal) {
			if (null != threadLocal.get()) {
				hashMap.putAll(threadLocal.get());
			}
		}

	}

	/**
	 * 无返回值的异步
	 * @param runnable
	 * @return java.util.concurrent.CompletableFuture
	 * @since 2021/1/22
	 */
	public CompletableFuture runAsync(Runnable runnable) {
		return CompletableFuture.runAsync(runnable, this);
	}

	/**
	 * 有返回值的异步
	 * @param supplier
	 * @return java.util.concurrent.CompletableFuture<U>
	 * @since 2021/1/22
	 */
	public <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
		return CompletableFuture.supplyAsync(supplier);
	}

	/**
	 * 获取监控数据
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.thread.ThreadPoolMonitorVo
	 * @since 2021/1/22
	 */
	public ThreadPoolMonitorVo getThreadPoolMonitorVo() {
		ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
		if (null == threadPoolExecutor) {
			return null;
		}
		return ThreadPoolMonitorVo.builder().threadNamePrefix(this.getThreadNamePrefix())
				.activeCount(threadPoolExecutor.getActiveCount())
				.completedTaskCount(threadPoolExecutor.getCompletedTaskCount())
				.taskCount(threadPoolExecutor.getTaskCount()).queueSize(threadPoolExecutor.getQueue().size())
				.corePoolSize(threadPoolExecutor.getCorePoolSize()).maxPoolSize(threadPoolExecutor.getPoolSize())
				.rejectedExecutionHandler(threadPoolExecutor.getRejectedExecutionHandler().getClass().getSimpleName())
				.build();
	}

}
