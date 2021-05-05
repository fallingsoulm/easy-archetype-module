package io.github.fallingsoulm.easy.archetype.framework.thread;

import java.util.Map;

/**
 * 业务线程池拦截器
 *
 * @author luyanan
 * @since 2021/1/22
 */
public interface BusinessThreadInterceptor {

	/**
	 * 主线程执行的方法
	 * @param threadLocal
	 * @return void
	 * @since 2021/1/22
	 */
	void main(InheritableThreadLocal<Map<String, Object>> threadLocal);

	/**
	 * 子线程执行的方法
	 * @param threadLocal
	 * @return void
	 * @since 2021/1/22
	 */
	void childThread(InheritableThreadLocal<Map<String, Object>> threadLocal);

}
