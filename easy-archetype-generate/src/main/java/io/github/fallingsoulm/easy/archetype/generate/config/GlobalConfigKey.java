package io.github.fallingsoulm.easy.archetype.generate.config;

/**
 * 全局配置的key
 *
 * @author luyanan
 * @since 2021/1/31
 **/
public interface GlobalConfigKey {

	/**
	 * 数据库url
	 *
	 * @since 2021/1/31
	 */
	String DATA_SOURCE_URL = "spring.datasource.url";

	/**
	 * 数据库驱动
	 *
	 * @since 2021/1/31
	 */
	String DATA_SOURCE_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";

	/**
	 * 数据库 用户名
	 *
	 * @since 2021/1/31
	 */
	String DATA_SOURCE_USER_NAME = "spring.datasource.username";

	/**
	 * 数据库密码
	 *
	 * @since 2021/1/31
	 */
	String DATA_SOURCE_PASS_WORD = "spring.datasource.password";

	/**
	 * 需要生成的表(多个用,隔开,支持正则)
	 *
	 * @since 2021/1/31
	 */
	String INCLUDES = "includes";

	/**
	 * 需要排除的表(多个用,隔开,支持正则)
	 *
	 * @since 2021/1/31
	 */
	String EXCLUDE = "exclude";

	/**
	 * 移除的表前缀
	 *
	 * @since 2021/1/31
	 */
	String REMOVE_TABLE_PREFIX = "removeTablePrefix";

	/**
	 * 作者
	 *
	 * @since 2021/1/31
	 */
	String AUTHOR = "author";

	/**
	 * 模块
	 *
	 * @since 2021/1/31
	 */
	String MODULE = "module";

	/**
	 * 输出的路径
	 *
	 * @since 2021/1/31
	 */
	String OUT_PUT_FILE = "outPutFile";

	/**
	 * 父包路径
	 *
	 * @since 2021/1/31
	 */
	String PARENT_PKG = "parentPkg";

}
