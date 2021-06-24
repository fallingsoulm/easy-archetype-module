package io.github.fallingsoulm.easy.archetype.framework.utils;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
		if (null == source) {
			return null;
		}
		T t = instantiateClass(targetClass);
		BeanUtils.copyProperties(source, t);
		return t;
	}


	/**
	 * List集合复制
	 *
	 * @param targetClass
	 * @return java.util.List<T>
	 * @since 2021/5/7
	 */
	public static <T, D> List<T> copyList(List<D> sources, Class<T> targetClass) {
		if (CollectionUtil.isEmpty(sources)) {
			return new ArrayList<>();
		}
		return sources
				.stream()
				.map(s -> copyProperties(s, targetClass)).collect(Collectors.toList());
	}

}
