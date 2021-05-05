package io.github.fallingsoulm.easy.archetype.framework.sensitive;

/**
 * 脱敏的策略
 *
 * @author luyanan
 * @since 2021/2/8
 **/
public interface SensitiveStrategy {

	/**
	 * 转换
	 * @param type 脱敏类型
	 * @param value 需要转换的值
	 * @return java.lang.String 转换后的值
	 * @since 2021/2/8
	 */
	String conver(SensitiveTypeEnum type, String value);

}
