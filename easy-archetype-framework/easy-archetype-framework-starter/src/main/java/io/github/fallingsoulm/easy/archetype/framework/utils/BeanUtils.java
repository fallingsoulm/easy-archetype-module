package io.github.fallingsoulm.easy.archetype.framework.utils;

/**
 * 对象复制工具类
 *
 * @author luyanan
 * @since 2021/2/12
 **/
public class BeanUtils extends org.springframework.beans.BeanUtils {


	/**
	 * 对象复制
	 *
	 * @param source      来源对象
	 * @param targetClass 目标对象的class
	 * @return T
	 * @since 2021/2/12
	 */
	public static <T> T copyProperties(Object source, Class<T> targetClass) {
		T t = instantiateClass(targetClass);
		BeanUtils.copyProperties(source, t);
		return t;
	}


}
