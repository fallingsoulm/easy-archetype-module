package io.github.fallingsoulm.easy.archetype.framework.xss.core;

/**
 * 使用ThreadLocal 存储线程间的数据
 *
 * @author luyanan
 * @since 2021/2/8
 **/
public class XssHolder {

	private static final ThreadLocal<Boolean> TL = new ThreadLocal<>();

	/**
	 * 是否开启
	 * @return boolean
	 * @since 2021/2/8
	 */
	public static boolean isEnabled() {
		return Boolean.TRUE.equals(TL.get());
	}

	/**
	 * 设置为开启状态
	 * @return void
	 * @since 2021/2/8
	 */
	public static void setEnable() {
		TL.set(Boolean.TRUE);
	}

	/**
	 * 移除
	 * @return void
	 * @since 2021/2/8
	 */
	public static void remove() {
		TL.remove();
	}

}
