package io.github.fallingsoulm.easy.archetype.job.entity;

import io.github.fallingsoulm.easy.archetype.framework.jdbc.FieldType;
import io.github.fallingsoulm.easy.archetype.framework.jdbc.annotation.TableField;
import io.github.fallingsoulm.easy.archetype.framework.jdbc.annotation.TableId;
import io.github.fallingsoulm.easy.archetype.framework.jdbc.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务都调度表
 *
 * @author luyanan
 * @since 2021/3/14
 **/
@TableName("j_job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobVo implements Serializable {

	private static final long serialVersionUID = 6261541571820467080L;


	/**
	 * 任务ID
	 *
	 * @since 2021/3/14
	 */
	@TableId(type = FieldType.INPUT)
	private String jobId;


	/**
	 * 任务名称
	 *
	 * @since 2021/3/14
	 */

	private String jobName;

	/**
	 * 任务组名
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
	 * 参数
	 *
	 * @author luyan
	 * @since 2021/6/27
	 */
	private String params;
	/**
	 * cron表达式
	 *
	 * @since 2021/3/14
	 */

	private String cronExpression;

	/**
	 * 计划策略 0=默认,1=立即触发执行,2=触发一次执行,3=不触发立即执行
	 *
	 * @since 2021/3/14
	 */

	private String misfirePolicy;

	/**
	 * 是否允许并发 0允许 1禁止
	 *
	 * @since 2021/3/14
	 */

	private String concurrent;


	/**
	 * 任务状态 0正常 1暂停
	 *
	 * @since 2021/3/14
	 */

	private String status;

	/**
	 * 创建人
	 *
	 * @since 2021/3/24
	 */

	private String createBy;


	/**
	 * 创建时间
	 *
	 * @since 2021/3/24
	 */

	private Date createTime;


	/**
	 * 修改人
	 *
	 * @since 2021/3/24
	 */

	private String updateBy;


	/**
	 * 修改时间
	 *
	 * @since 2021/3/24
	 */

	private Date updateTime;
}
