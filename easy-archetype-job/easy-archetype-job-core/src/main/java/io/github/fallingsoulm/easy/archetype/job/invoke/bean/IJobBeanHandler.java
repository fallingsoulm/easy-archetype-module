package io.github.fallingsoulm.easy.archetype.job.invoke.bean;

/**
 * bean类型的定时任务
 *
 * @author luyanan
 * @since 2021/6/27
 **/
public interface IJobBeanHandler {

	/**
	 * 是否为异步执行, true则为异步, fase则为同步
	 *
	 * @return boolean
	 * @since 2021/6/27
	 */
	default boolean async() {
		return false;
	}

	/**
	 * 定时任务执行
	 *
	 * @param params 参数
	 * @return com.wblog.web.task.handler.JobRespEntity
	 * @throws Exception 任务执行异常
	 * @since 2021/6/25
	 */
	JobRespEntity execute(String params) throws Exception;

}
