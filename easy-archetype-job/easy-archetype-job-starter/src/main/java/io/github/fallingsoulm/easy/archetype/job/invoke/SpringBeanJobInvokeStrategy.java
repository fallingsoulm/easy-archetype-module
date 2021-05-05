package io.github.fallingsoulm.easy.archetype.job.invoke;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.framework.spring.SpringContextHolder;
import io.github.fallingsoulm.easy.archetype.job.entity.JobVo;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * SpringBean 反射调用
 *
 * @author luyanan
 * @since 2021/3/18
 **/
public class SpringBeanJobInvokeStrategy implements JobInvokeStrategy {
	@Override
	public String type() {
		return "bean";
	}

	@SneakyThrows
	@Override
	public void invoke(JobVo jobVo) {
		String invokeTarget = jobVo.getInvokeTarget();
		String beanName = getBeanName(invokeTarget);
		String methodName = getMethodName(invokeTarget);
		List<Object[]> methodParams = getMethodParams(invokeTarget);
		if (!isValidClassName(beanName)) {
			Object bean = SpringContextHolder.getBean(beanName);
			invokeMethod(bean, methodName, methodParams);
		} else {
			Object instance = Class.forName(beanName).newInstance();
			invokeMethod(instance, methodName, methodParams);
		}
	}


	/**
	 * 反射调用任务方法
	 *
	 * @param bean         目标对象
	 * @param methodName   方法名
	 * @param methodParams 方法参数
	 * @return void
	 * @since 2021/3/18
	 */
	private void invokeMethod(Object bean, String methodName, List<Object[]> methodParams) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		if (CollectionUtil.isNotEmpty(methodParams)) {
			Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
			method.invoke(bean, getMethodParamsValue(methodParams));
		} else {
			Method method = bean.getClass().getDeclaredMethod(methodName);
			method.invoke(bean);
		}
	}


	/**
	 * 获取参数值
	 *
	 * @param methodParams 参数列表
	 * @return java.lang.Object[]
	 * @since 2021/3/18
	 */
	private Object[] getMethodParamsValue(List<Object[]> methodParams) {

		Object[] classs = new Object[methodParams.size()];
		int index = 0;
		for (Object[] param : methodParams) {
			classs[index] = param[0];
			index++;

		}
		return classs;
	}

	/**
	 * 获取参数类型
	 *
	 * @param methodParams 参数相关列表
	 * @return java.lang.Class<?>[]
	 * @since 2021/3/18
	 */
	public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {

		Class<?>[] classes = new Class<?>[methodParams.size()];
		int index = 0;
		for (Object[] param : methodParams) {
			classes[index] = (Class<?>) param[1];
			index++;
		}
		return classes;
	}

	/**
	 * 校验是否为class包名
	 *
	 * @param str 名称
	 * @return boolean
	 * @since 2021/3/18
	 */
	private boolean isValidClassName(String str) {
		return StrUtil.count(str, ".") > 1;
	}

	/**
	 * 获取Method方法参数相关列表
	 *
	 * @param invokeTarget 目标字符串
	 * @return java.util.List<java.lang.Object [ ]>
	 * @since 2021/3/18
	 */
	private List<Object[]> getMethodParams(String invokeTarget) {
		String methodStr = StrUtil.subBetween(invokeTarget, "(", ")");
		if (StrUtil.isEmpty(methodStr)) {
			return null;
		}
		String[] methodParams = methodStr.split(",");
		List<Object[]> classs = new LinkedList<>();
		for (int i = 0; i < methodParams.length; i++) {
			String str = StrUtil.trimToEmpty(methodParams[i]);
			if (StrUtil.contains(str, "'")) {
				// String字符串类型, 包含'
				classs.add(new Object[]{StrUtil.replace(str, "'", ""), String.class});
			}
			// boolean 布尔类型，true或者false
			else if (StrUtil.equals(str, "true") || StrUtil.equals(str, "false")) {
				classs.add(new Object[]{Boolean.valueOf(str), Boolean.class});
			}
			// Long 长整形,包含L
			else if (StrUtil.containsIgnoreCase(str, "L")) {
				classs.add(new Object[]{Long.valueOf(StrUtil.replaceIgnoreCase(str, "L", "")), Long.class});
			}
			// double 浮点类型,包含D
			else if (StrUtil.containsIgnoreCase(str, "D")) {
				classs.add(new Object[]{Double.valueOf(StrUtil.replaceIgnoreCase(str, "D", "")), Double.class});
			}
			// 其他类型都归类型整形
			else {
				classs.add(new Object[]{Integer.valueOf(str), Integer.class});
			}

		}
		return classs;
	}

	/**
	 * 获取方法名
	 *
	 * @param invokeTarget 目标字符串
	 * @return java.lang.String
	 * @since 2021/3/18
	 */
	private String getMethodName(String invokeTarget) {
		String methodName = StrUtil.subBefore(invokeTarget, "(", true);
		return StrUtil.subAfter(methodName, ".", true);

	}

	/**
	 * 获取Bean的名称
	 *
	 * @param invokeTarget 目标字符串
	 * @return java.lang.String
	 * @since 2021/3/18
	 */
	private String getBeanName(String invokeTarget) {
		String beanName = StrUtil.subBefore(invokeTarget, "(", true);
		return StrUtil.subBefore(beanName, ".", true);
	}

}
