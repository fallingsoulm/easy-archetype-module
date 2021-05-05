package io.github.fallingsoulm.easy.archetype.job.entity;

import io.github.fallingsoulm.easy.archetype.framework.jdbc.FieldType;
import io.github.fallingsoulm.easy.archetype.framework.jdbc.annotation.TableId;
import io.github.fallingsoulm.easy.archetype.framework.jdbc.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务日志
 *
 * @author luyanan
 * @since 2021/3/14
 **/

@TableName("j_job_log")
@Data
public class JobLogVo implements Serializable {
	private static final long serialVersionUID = 465522275199286269L;

	@TableId(type = FieldType.INPUT)
	private String id;

	/**
	 * 任务id
	 *
	 * @since 2021/3/14
	 */
	private String jobId;

	/**
	 * 任务名称
	 *
	 * @since 2021/3/14
	 */

	private String jobName;


	/**
	 * 任务分组
	 *
	 * @since 2021/3/14
	 */

	private String jobGroup;

	/**
	 * 调用类型
	 *
	 * @since 2021/3/18
	 */

	private String invokeType;

	/**
	 * 调用目标字符串
	 *
	 * @since 2021/3/14
	 */

	private String invokeTarget;


	/**
	 * 日志信息
	 *
	 * @since 2021/3/14
	 */

	private String jobMessage;


	/**
	 * 执行状态 0=正常,1=失败
	 *
	 * @since 2021/3/14
	 */

	private String status;


	/**
	 * 异常信息
	 *
	 * @since 2021/3/14
	 */

	private String exceptionInfo;

	/**
	 * 开始时间
	 *
	 * @since 2021/3/14
	 */

	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @since 2021/3/14
	 */

	private Date endTime;

	/**
	 * 耗时
	 *
	 * @since 2021/3/24
	 */

	private Long elapsedTime;

}
