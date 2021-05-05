package io.github.fallingsoulm.easy.archetype.generate.config;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 全局配置
 *
 * @author luyanan
 * @since 2021/1/31
 **/
public class GlobalConfig extends HashMap<String, Object> {

	/**
	 * 设置数据库url
	 * @param dataSourceUrl 数据库url
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.config.GlobalConfig
	 * @since 2021/1/31
	 */
	public GlobalConfig setDataSourceUrl(String dataSourceUrl) {
		this.put(GlobalConfigKey.DATA_SOURCE_URL, dataSourceUrl);
		return this;
	}

	/**
	 * 获取 数据库url
	 * @return java.lang.String 数据库url
	 * @since 2021/1/31
	 */
	public String getDataSourceUrl() {
		return this.get(GlobalConfigKey.DATA_SOURCE_URL).toString();
	}

	/**
	 * 设置数据库用户名
	 * @param dataSourceUserName 数据库用户名
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.config.GlobalConfig
	 * @since 2021/1/31
	 */
	public GlobalConfig setDataSourceUserName(String dataSourceUserName) {
		this.put(GlobalConfigKey.DATA_SOURCE_USER_NAME, dataSourceUserName);
		return this;
	}

	/**
	 * 获取数据库用户名
	 * @return java.lang.String 数据库用户名
	 * @since 2021/1/31
	 */
	public String getDataSourceUserName() {
		return this.get(GlobalConfigKey.DATA_SOURCE_USER_NAME).toString();
	}

	/**
	 * 设置数据库驱动
	 * @param dataSourceDriverClassName 数据库驱动
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.config.GlobalConfig
	 * @since 2021/1/31
	 */
	public GlobalConfig setDataSourceDriverClassName(String dataSourceDriverClassName) {
		this.put(GlobalConfigKey.DATA_SOURCE_DRIVER_CLASS_NAME, dataSourceDriverClassName);
		return this;
	}

	/**
	 * 获取数据库驱动
	 * @return java.lang.String 数据库驱动
	 * @since 2021/1/31
	 */
	public String getDataSourceDriverClassName() {
		return this.get(GlobalConfigKey.DATA_SOURCE_DRIVER_CLASS_NAME).toString();
	}

	/**
	 * 设置数据库密码
	 * @param dataSourcePassWord 数据库密码
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.config.GlobalConfig
	 * @since 2021/1/31
	 */
	public GlobalConfig setDataSourcePassWord(String dataSourcePassWord) {
		this.put(GlobalConfigKey.DATA_SOURCE_PASS_WORD, dataSourcePassWord);
		return this;
	}

	/**
	 * 获取数据库密码
	 * @return java.lang.String 数据库密码
	 * @since 2021/1/31
	 */
	public String getDataSourcePassWord() {
		return this.get(GlobalConfigKey.DATA_SOURCE_PASS_WORD).toString();
	}

	/**
	 * 设置需要生成的表
	 * @param includes 需要生成的表
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.config.GlobalConfig
	 * @since 2021/1/31
	 */
	public GlobalConfig setIncludes(String includes) {
		this.put(GlobalConfigKey.INCLUDES, includes);
		return this;
	}

	/**
	 * 获取需要生成的表
	 * @return java.lang.String[] 需要生成的表
	 * @since 2021/1/31
	 */
	public String[] getIncludes() {
		return Arrays.stream(this.getOrDefault(GlobalConfigKey.INCLUDES, "").toString().split(","))
				.filter(a -> StrUtil.isNotBlank(a)).toArray(String[]::new);
	}

	/**
	 * 设置需要排除的表
	 * @param exclude 需要排除的表
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.config.GlobalConfig
	 * @since 2021/1/31
	 */
	public GlobalConfig setExclude(String exclude) {
		this.put(GlobalConfigKey.EXCLUDE, exclude);
		return this;
	}

	/**
	 * 获取需要排除的表
	 * @return java.lang.String[]
	 * @since 2021/1/31
	 */
	public String[] getExclude() {
		return Arrays.stream(this.getOrDefault(GlobalConfigKey.EXCLUDE, "").toString().split(","))
				.filter(a -> StrUtil.isNotBlank(a)).toArray(String[]::new);
	}

	/**
	 * 设置需要移除的表前缀
	 * @param removeTablePrefix 移除的表前缀
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.config.GlobalConfig
	 * @since 2021/1/31
	 */
	public GlobalConfig setRemoveTablePrefix(String removeTablePrefix) {

		this.put(GlobalConfigKey.REMOVE_TABLE_PREFIX, removeTablePrefix);
		return this;
	}

	/**
	 * 获取移除的表前缀
	 * @return java.lang.String[] 移除的表前缀
	 * @since 2021/1/31
	 */
	public String[] getRemoveTablePrefix() {

		return Arrays.stream(this.getOrDefault(GlobalConfigKey.REMOVE_TABLE_PREFIX, "").toString().split(","))
				.filter(a -> StrUtil.isNotBlank(a)).toArray(String[]::new);
	}

	/**
	 * 设置用户名
	 * @param author 用户名
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.config.GlobalConfig
	 * @since 2021/1/31
	 */
	public GlobalConfig setAuthor(String author) {
		this.put(GlobalConfigKey.AUTHOR, author);
		return this;
	}

	/**
	 * 获取用户名
	 * @return java.lang.String 用户名
	 * @since 2021/1/31
	 */
	public String getAuthor() {
		return this.get(GlobalConfigKey.AUTHOR).toString();
	}

	/**
	 * 设置父包路径
	 * @param parentPkg 父包路径
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.config.GlobalConfig
	 * @since 2021/1/31
	 */
	public GlobalConfig setParentPkg(String parentPkg) {

		this.put(GlobalConfigKey.PARENT_PKG, parentPkg);
		return this;
	}

	/**
	 * 获取父包路径
	 * @return java.lang.String 父包路径
	 * @since 2021/1/31
	 */
	public String getParentPkg() {
		return this.get(GlobalConfigKey.PARENT_PKG).toString();
	}

	/**
	 * 设置模块名
	 * @param module 模块名
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.config.GlobalConfig
	 * @since 2021/1/31
	 */
	public GlobalConfig setModule(String module) {
		this.put(GlobalConfigKey.MODULE, module);
		return this;
	}

	/**
	 * 获取模块名
	 * @return java.lang.String 模块名
	 * @since 2021/1/31
	 */
	public String getModule() {
		return this.get(GlobalConfigKey.MODULE).toString();
	}

	/**
	 * 设置输出路径
	 * @param outPutFile 输出路径
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.config.GlobalConfig
	 * @since 2021/1/31
	 */
	public GlobalConfig setOutPutFile(String outPutFile) {
		this.put(GlobalConfigKey.OUT_PUT_FILE, outPutFile);
		return this;
	}

	/**
	 * 获取输出路径
	 * @return java.lang.String 输出路径
	 * @since 2021/1/31
	 */
	public String getOutPutFile() {
		return this.get(GlobalConfigKey.OUT_PUT_FILE).toString();
	}

}
