package io.github.fallingsoulm.easy.archetype.generate.utils;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 从数据库表到文件的命名策略
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public enum NamingStrategy {

	/**
	 * 不做任何改变，原样输出
	 */
	no_change,
	/**
	 * 下划线转驼峰命名
	 */
	underline_to_camel;

	/**
	 * <p>
	 * 转换
	 * </p>
	 * @param name 名称
	 * @return {@link String}
	 * @author luyanan
	 * @since 2020/1/3
	 */
	public static String underlineToCamel(String name) {
		// 快速检查
		if (StrUtil.isEmpty(name)) {
			// 没必要转换
			return "";
		}
		String tempName = name;
		// 大写数字下划线组成转为小写 , 允许混合模式转为小写
		if (isCapitalMode(name) || isMixedMode(name)) {
			tempName = name.toLowerCase();
		}
		StringBuilder result = new StringBuilder();
		// 用下划线将原始字符串分割
		String[] camels = tempName.split("_");
		// 跳过原始字符串中开头、结尾的下换线或双重下划线
		// 处理真正的驼峰片段
		Arrays.stream(camels).filter(camel -> !StrUtil.isEmpty(camel)).forEach(camel -> {
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel);
			}
			else {
				// 其他的驼峰片段，首字母大写
				result.append(capitalFirst(camel));
			}
		});
		return result.toString();
	}

	/**
	 * 是否为大写命名
	 * @param word 待判断字符串
	 * @return ignore
	 */
	public static boolean isCapitalMode(String word) {
		return null != word && word.matches("^[0-9A-Z/_]+$");
	}

	/**
	 * 是否为驼峰下划线混合命名
	 * @param word 待判断字符串
	 * @return ignore
	 */
	public static boolean isMixedMode(String word) {
		return matches(".*[A-Z]+.*", word) && matches(".*[/_]+.*", word);
	}

	/**
	 * 正则表达式匹配
	 * @param regex 正则表达式字符串
	 * @param input 要匹配的字符串
	 * @return 如果 input 符合 regex 正则表达式格式, 返回true, 否则返回 false;
	 */
	public static boolean matches(String regex, String input) {
		if (null == regex || null == input) {
			return false;
		}
		return Pattern.matches(regex, input);
	}

	/**
	 * 去掉指定的前缀
	 * @param name ignore
	 * @param prefix ignore
	 * @return ignore
	 */
	public static String removePrefix(String name, String... prefix) {
		if (StrUtil.isEmpty(name)) {
			return "";
		}
		if (null != prefix) {
			// 判断是否有匹配的前缀，然后截取前缀
			// 删除前缀
			return Arrays.stream(prefix).filter(pf -> name.toLowerCase().matches("^" + pf.toLowerCase() + ".*"))
					.findFirst().map(pf -> name.substring(pf.length())).orElse(name);
		}
		return name;
	}

	/**
	 * 判断是否包含prefix
	 * @param name ignore
	 * @param prefix ignore
	 * @return ignore
	 */
	public static boolean isPrefixContained(String name, String... prefix) {
		if (null == prefix || StrUtil.isEmpty(name)) {
			return false;
		}
		return Arrays.stream(prefix).anyMatch(pf -> name.toLowerCase().matches("^" + pf.toLowerCase() + ".*"));
	}

	/**
	 * 去掉下划线前缀且将后半部分转成驼峰格式
	 * @param name ignore
	 * @param tablePrefix ignore
	 * @return ignore
	 */
	public static String removePrefixAndCamel(String name, String[] tablePrefix) {
		return underlineToCamel(removePrefix(name, tablePrefix));
	}

	/**
	 * 实体首字母大写
	 * @param name 待转换的字符串
	 * @return 转换后的字符串
	 */
	public static String capitalFirst(String name) {
		if (StrUtil.isNotEmpty(name)) {
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		return "";
	}

}
