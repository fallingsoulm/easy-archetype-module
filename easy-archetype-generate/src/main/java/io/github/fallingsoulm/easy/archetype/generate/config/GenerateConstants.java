package io.github.fallingsoulm.easy.archetype.generate.config;

/**
 * 静态变量
 *
 * @author luyanan
 * @since 2021/1/31
 **/
public class GenerateConstants {

	/*********** 内置变量 ***************/
	/**
	 * classpath
	 *
	 * @since 2021/1/31
	 */
	public final static String CLASS_PATH = "classpath:";

	/**
	 * 项目所在的路径
	 *
	 * @since 2021/1/31
	 */
	public final static String PROJECT_PATH = "projectpath:";

	/**************** 支持文件格式 **************/

	/**
	 * properties
	 *
	 * @since 2021/1/31
	 */
	public final static String PROPERTIES = "properties";

	/**
	 * yml
	 *
	 * @since 2021/1/31
	 */
	public final static String YML = "yml";

	/***************** 其他配置 *******************/

	/**
	 * 默认的模板的配置文件所在的路径
	 *
	 * @since 2021/1/31
	 */
	public static final String DEFAULT_PROPERTIES = CLASS_PATH + "generate.properties";

	/**
	 * 外部配置文件的key
	 *
	 * @since 2021/1/31
	 */
	public static final String PROPERTIES_SOURCE = "propertiesSource";

}
