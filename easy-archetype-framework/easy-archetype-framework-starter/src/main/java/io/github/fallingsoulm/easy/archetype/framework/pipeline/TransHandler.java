package io.github.fallingsoulm.easy.archetype.framework.pipeline;

import io.github.fallingsoulm.easy.archetype.framework.pipeline.callback.TransCallback;

/**
 * 任务处理执行器
 *
 * @author luyanan
 * @since 2021/7/17
 **/
public interface TransHandler {


	/**
	 * 是否支持此次执行
	 *
	 * @param context 上下文
	 * @return boolean
	 * @since 2021/7/17
	 */
	default boolean support(TransHandlerContext context) {
		return true;
	}


	/**
	 * 任务执行
	 *
	 * @param context 上下文
	 * @return boolean true的时候则继续执行下一个handler, 否则结束handler, Chain的执行直接返回
	 * @since 2021/7/17
	 */
	boolean handler(TransHandlerContext context);


	/**
	 * 获取回调
	 *
	 * @return io.github.fallingsoulm.easy.archetype.framework.pipeline.callback.TransCallback
	 * @since 2021/7/17
	 */
	default TransCallback callback() {
		return null;
	}
}
