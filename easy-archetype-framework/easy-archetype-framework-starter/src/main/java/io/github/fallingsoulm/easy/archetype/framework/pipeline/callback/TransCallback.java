package io.github.fallingsoulm.easy.archetype.framework.pipeline.callback;


import io.github.fallingsoulm.easy.archetype.framework.pipeline.TransHandlerContext;

/**
 * 任务执行回调
 *
 * @author luyanan
 * @since 2021/7/17
 **/
@FunctionalInterface
public interface TransCallback {

	/**
	 * 任务执行回调
	 *
	 * @param context 上下文
	 * @return void
	 * @since 2021/7/17
	 */
	void call(TransHandlerContext context);

}
