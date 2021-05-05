package io.github.fallingsoulm.easy.archetype.generate.conver;

/**
 * 字段类型转换
 *
 * @author luyanan
 * @since 2021/1/31
 **/
public interface IColumnType {

	/**
	 * 获取类型
	 * @return java.lang.String
	 * @since 2021/1/31
	 */
	String getType();

	/**
	 * 获取完整的包名
	 * @return java.lang.String
	 * @since 2021/1/31
	 */
	String getPak();

}
