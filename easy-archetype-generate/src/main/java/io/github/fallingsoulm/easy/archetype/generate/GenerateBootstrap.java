package io.github.fallingsoulm.easy.archetype.generate;

import cn.hutool.core.lang.Assert;
import io.github.fallingsoulm.easy.archetype.generate.config.ConfigHandler;
import io.github.fallingsoulm.easy.archetype.generate.config.ConfigParser;
import io.github.fallingsoulm.easy.archetype.generate.config.GenerateConstants;
import io.github.fallingsoulm.easy.archetype.generate.config.GlobalConfig;
import io.github.fallingsoulm.easy.archetype.generate.conver.ColumnTypeConverFactory;
import io.github.fallingsoulm.easy.archetype.generate.conver.IColumnTypeConver;
import io.github.fallingsoulm.easy.archetype.generate.conver.mysql.MysqlColumnTypeConver;
import io.github.fallingsoulm.easy.archetype.generate.core.FactoryRegistryHandler;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;
import io.github.fallingsoulm.easy.archetype.generate.engine.FreemarkerTemplateEngine;
import io.github.fallingsoulm.easy.archetype.generate.engine.ITemplateEngine;
import io.github.fallingsoulm.easy.archetype.generate.ext.doc.SimpleDocTemplateGroup;
import io.github.fallingsoulm.easy.archetype.generate.ext.simple.SimpleTemplateGroup;
import io.github.fallingsoulm.easy.archetype.generate.file.DefaultFileOutputHandler;
import io.github.fallingsoulm.easy.archetype.generate.file.FileOutputHandler;
import io.github.fallingsoulm.easy.archetype.generate.file.OutputFile;
import io.github.fallingsoulm.easy.archetype.generate.query.ITableQueryResult;
import io.github.fallingsoulm.easy.archetype.generate.query.MySqlTableQueryResult;
import io.github.fallingsoulm.easy.archetype.generate.query.TableQueryResultRegistryFactory;
import io.github.fallingsoulm.easy.archetype.generate.template.ITemplateGroup;
import io.github.fallingsoulm.easy.archetype.generate.template.TemplateGroupRegister;

import java.sql.Driver;
import java.util.List;
import java.util.Map;

/**
 * 代码生成启动类
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public class GenerateBootstrap {

	/**
	 * 全局配置
	 *
	 * @since 2021/2/2
	 */
	private GlobalConfig globalConfig;

	/**
	 * 设置全局配置文件
	 * @param globalConfig 全局配置文件
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.GenerateBootstrap
	 * @since 2021/2/2
	 */
	public GenerateBootstrap globalConfig(GlobalConfig globalConfig) {
		this.globalConfig = globalConfig;
		return this;
	}

	/**
	 * 通过配置文件加载配置
	 * @param propertiesPath 配置文件路径
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.GenerateBootstrap
	 * @since 2021/2/2
	 */
	public GenerateBootstrap globalConfig(String propertiesPath) {

		ConfigParser configParser = new ConfigParser();

		this.globalConfig = new GlobalConfig();
		this.globalConfig.putAll(configParser.parser(propertiesPath));
		return this;
	}

	/**
	 * 通过默认的配置文件加载配置
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.GenerateBootstrap
	 * @since 2021/2/2
	 */
	public GenerateBootstrap globalConfig() {
		return globalConfig(GenerateConstants.DEFAULT_PROPERTIES);
	}

	/**
	 * 类型转换器
	 *
	 * @since 2021/2/2
	 */
	private ColumnTypeConverFactory columnTypeConverFactory = new ColumnTypeConverFactory();

	/**
	 * 注册类型转化
	 * @param factoryRegistryHandler 注册工厂
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.GenerateBootstrap
	 * @since 2021/2/2
	 */
	public GenerateBootstrap columnTypeConverRegistry(
			FactoryRegistryHandler<Map<Class<? extends Driver>, IColumnTypeConver>> factoryRegistryHandler) {
		Assert.notNull(factoryRegistryHandler, "factoryRegistryHandler 不能为null");
		columnTypeConverFactory.registry(factoryRegistryHandler);
		return this;
	}

	/**
	 * 数据查询的工厂
	 *
	 * @since 2021/2/2
	 */
	private TableQueryResultRegistryFactory tableQueryResultRegistryFactory = new TableQueryResultRegistryFactory();

	/**
	 * 数据查询类型注册
	 * @param factoryRegistryHandler 类型注册工厂
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.GenerateBootstrap
	 * @since 2021/2/2
	 */
	public GenerateBootstrap tableQueryResultRegistry(
			FactoryRegistryHandler<Map<Class<? extends Driver>, ITableQueryResult>> factoryRegistryHandler) {
		Assert.notNull(factoryRegistryHandler, "tableQueryResultRegistry 不能为null");
		tableQueryResultRegistryFactory.registry(factoryRegistryHandler);
		return this;
	}

	/**
	 * 模板引擎
	 *
	 * @since 2021/2/2
	 */
	private ITemplateEngine templateEngine;

	/**
	 * 设置模板引擎
	 * @param templateEngine 模板引擎
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.GenerateBootstrap
	 * @since 2021/2/2
	 */
	public GenerateBootstrap engine(ITemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
		return this;
	}

	/**
	 * 文件处理类
	 *
	 * @since 2021/2/2
	 */
	private FileOutputHandler fileOutputHandler;

	/**
	 * 设置文件处理类
	 * @param fileOutputHandler 文件处理类
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.GenerateBootstrap
	 * @since 2021/2/2
	 */
	public GenerateBootstrap fileOutputHandler(FileOutputHandler fileOutputHandler) {
		this.fileOutputHandler = fileOutputHandler;
		return this;
	}

	/**
	 * 模板组注册器
	 *
	 * @since 2021/2/2
	 */
	private TemplateGroupRegister templateGroupRegister = TemplateGroupRegister.builder();

	/**
	 * 模板组注册
	 * @param factoryRegistryHandler 注册器
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.GenerateBootstrap
	 * @since 2021/2/2
	 */
	public GenerateBootstrap templateGroupRegister(
			FactoryRegistryHandler<Map<Class<? extends ITemplateGroup>, ITemplateGroup>> factoryRegistryHandler) {
		this.templateGroupRegister.registry(factoryRegistryHandler);
		return this;
	}

	/**
	 * 配置处理类
	 *
	 * @since 2021/2/2
	 */
	private ConfigHandler configHandler;

	/**
	 * 设置配置处理类
	 * @param configHandler 配置处理类
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.GenerateBootstrap
	 * @since 2021/2/2
	 */
	public GenerateBootstrap configHandler(ConfigHandler configHandler) {
		this.configHandler = configHandler;
		return this;
	}

	/**
	 * 初始化加载
	 * @return void
	 * @since 2021/2/2
	 */
	public void init() {
		// 1. 配置信息初始化
		if (null == this.globalConfig) {
			globalConfig();
		}
		// 2. 数据转换器初始化
		this.columnTypeConverRegistry(a -> {
			a.put(com.mysql.cj.jdbc.Driver.class, new MysqlColumnTypeConver());
			a.put(com.mysql.jdbc.Driver.class, new MysqlColumnTypeConver());
		});
		// 3. 表数据查询器初始化
		this.tableQueryResultRegistry(a -> {
			a.put(com.mysql.cj.jdbc.Driver.class, new MySqlTableQueryResult());
			a.put(com.mysql.jdbc.Driver.class, new MySqlTableQueryResult());
		});
		// 4. 注册模板
		if (null == this.templateEngine) {
			this.templateEngine = new FreemarkerTemplateEngine();
		}

		// 5. 注册结果保存类
		if (null == this.fileOutputHandler) {
			this.fileOutputHandler = new DefaultFileOutputHandler();
		}
		// 6. 注册基本模板组
		this.templateGroupRegister.registry(a -> {
			a.put(SimpleTemplateGroup.class, new SimpleTemplateGroup(true, true));
			a.put(SimpleDocTemplateGroup.class, new SimpleDocTemplateGroup());
		});
	}

	/**
	 * 代码生成
	 * @param templateGroupClass
	 * @return void
	 * @since 2021/2/2
	 */
	public void generate(Class<? extends ITemplateGroup>... templateGroupClass) {
		// 1. 初始化(默认值)配置
		init();
		if (this.configHandler != null) {
			configHandler.handler(this.globalConfig);
		}
		Assert.notNull(this.globalConfig, "globalConfig 不能为null");
		Assert.notEmpty(templateGroupClass, "templateGroupClass 不能为空");
		if (templateGroupClass.length == 0) {

		}
		Class<? extends Driver> driverClass = null;
		try {
			driverClass = (Class<? extends Driver>) Class.forName(this.globalConfig.getDataSourceDriverClassName());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 2. 获取结果查询器
		ITableQueryResult tableQueryResult = this.tableQueryResultRegistryFactory.get(driverClass);
		// 3. 获取类型转换器
		IColumnTypeConver converFactoryConver = columnTypeConverFactory.getConver(driverClass);

		// 4. 获取模板组
		for (Class<? extends ITemplateGroup> groupClass : templateGroupClass) {

			// 5. 代码生成
			ITemplateGroup templateGroup = this.templateGroupRegister.getTemplateGroup(groupClass);
			// 6. 查询出来所有需要处理的表
			List<TableInfoEntity> tableInfoEntities = templateGroup.getGenerateTableInfo(tableQueryResult, globalConfig,
					converFactoryConver);
			System.out.println(groupClass.getName() + "模板组: 待生成的表的数量为:" + tableInfoEntities.size());
			List<OutputFile> outputFiles = templateGroup.generate(tableInfoEntities, this.templateEngine,
					this.globalConfig);
			// 交给文件处理器处理
			this.fileOutputHandler.handler(outputFiles, this.globalConfig);
		}

	}

}
