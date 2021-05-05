package io.github.fallingsoulm.easy.archetype.generate.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ClassUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 变量配置
 *
 * @author luyanan
 * @since 2021/1/31
 **/
public class VariableConfig {

	private Map<String, String> variableMap = new HashMap<>();

	public VariableConfig() {
		String classPath = ClassUtil.getClassPath();
		variableMap.put(GenerateConstants.CLASS_PATH, classPath + "/");
		variableMap.put(GenerateConstants.PROJECT_PATH, System.getProperty("user.dir") + "/");
	}

	/**
	 * 变量替换
	 * @param str 变量
	 * @return java.lang.String
	 * @since 2021/1/31
	 */
	public String variableReplace(String str) {
		// 解决内置变量
		for (Map.Entry<String, String> e : variableMap.entrySet()) {
			if (str.startsWith(e.getKey())) {
				str = str.replace(e.getKey(), e.getValue()).replaceAll("/+", "/");

				return str;
			}
		}
		return str;
	}

	/**
	 * 变量替换
	 * @param map 变量
	 * @return void
	 * @since 2021/1/31
	 */
	public void variableReplace(Map<String, String> map) {

		if (CollectionUtil.isEmpty(map)) {
			return;
		}

		map.entrySet().forEach(entry -> {

			String key = entry.getKey();
			String value = entry.getValue();

			// 解决内置变量
			for (Map.Entry<String, String> e : variableMap.entrySet()) {
				if (value.startsWith(e.getKey())) {
					value = value.replace(e.getKey(), e.getValue()).replaceAll("/+", "/");
					map.put(key, value);
				}
			}
			// 解决${} 占位符替换
			if (isPlaceholder(value)) {
				String placeholder = getPlaceholder(value);
				value = map.get(placeholder);
				map.put(key, value);
			}

		});
	}

	/**
	 * 变量是否为占位符
	 * @param value 变量
	 * @return boolean
	 * @since 2021/1/31
	 */
	private boolean isPlaceholder(String value) {
		return value.startsWith("${") && value.endsWith("}");
	}

	/**
	 * 获取变量的占位符参数
	 * @param value 变量
	 * @return java.lang.String
	 * @since 2021/1/31
	 */
	public String getPlaceholder(String value) {
		value = value.trim();
		if (!isPlaceholder(value)) {
			return null;
		}
		value = value.substring(2);
		value = value.substring(0, value.length() - 1);
		return value;
	}

}
