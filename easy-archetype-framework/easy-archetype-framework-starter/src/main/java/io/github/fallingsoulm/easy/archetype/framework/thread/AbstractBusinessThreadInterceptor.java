package io.github.fallingsoulm.easy.archetype.framework.thread;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务线程拦截器的抽象类
 *
 * @author luyanan
 * @since 2021/1/22
 **/
public abstract class AbstractBusinessThreadInterceptor implements BusinessThreadInterceptor {

	@Override
	public void main(InheritableThreadLocal<Map<String, Object>> threadLocal) {

		Map<String, Object> map = threadLocal.get();
		if (null == map) {
			map = new HashMap<>(16);
		}
		mainHandler(map);
		threadLocal.set(map);
	}

	@Override
	public void childThread(InheritableThreadLocal<Map<String, Object>> threadLocal) {
		Map<String, Object> map = threadLocal.get();
		childThreadHandler(map);
	}

	/**
	 * 主线程执行的方法
	 * @param attrMap 参数集
	 * @return void
	 * @since 2021/1/22
	 */
	protected abstract void mainHandler(Map<String, Object> attrMap);

	/**
	 * 子线程执行的方法
	 * @param attrMap 参数集
	 * @return void
	 * @since 2021/1/22
	 */
	protected abstract void childThreadHandler(Map<String, Object> attrMap);

}
