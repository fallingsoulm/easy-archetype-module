package io.github.fallingsoulm.easy.archetype.framework.extension;

/**
 * 对象存储
 *
 * @author luyanan
 * @since 2021/3/4
 **/
public class Holder<T> {

	/**
	 * 存储的值
	 *
	 * @author luyanan
	 * @since 2021/3/4
	 */
	private volatile T value;


	/**
	 * 获取值
	 *
	 * @return T
	 * @since 2021/3/4
	 */
	public T get() {
		return this.value;
	}

	/**
	 * 设置值
	 *
	 * @param value
	 * @return void
	 * @since 2021/3/4
	 */
	public void set(T value) {
		this.value = value;
	}
}
