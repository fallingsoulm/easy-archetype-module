package io.github.fallingsoulm.easy.archetype.job.invoke;

import io.github.fallingsoulm.easy.archetype.job.entity.JobVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 任务执行监控
 *
 * @author luyanan
 * @since 2021/8/1
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobMonitorVo {

	public enum Status {

		RUNNING(0),
		SUCCESS(1),
		ERROR(2);


		/**
		 * 状态
		 *
		 * @author luyanan
		 * @since 2021/8/1
		 */
		private Integer status;


		Status(Integer status) {
			this.status = status;
		}

		public Integer getStatus() {
			return status;
		}
	}

	/**
	 * 任务执行开始时间
	 *
	 * @author luyanan
	 * @since 2021/8/1
	 */
	private Long executeStartTime;


	/**
	 * 任务执行结束时间
	 *
	 * @author luyanan
	 * @since 2021/8/1
	 */
	private Long executeEndTime;


	/**
	 * 任务执行状态 0:执行中  1:结束  2:异常
	 *
	 * @author luyanan
	 * @since 2021/8/1
	 */
	private Integer status;

	/**
	 * 任务
	 *
	 * @author luyanan
	 * @since 2021/8/1
	 */
	private JobVo jobVo;

	/**
	 * 错误原因
	 *
	 * @author luyanan
	 * @since 2021/8/1
	 */
	private String errorMsg;
}
