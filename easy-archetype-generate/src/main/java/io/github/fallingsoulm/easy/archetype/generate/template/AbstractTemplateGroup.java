package io.github.fallingsoulm.easy.archetype.generate.template;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.db.ds.simple.SimpleDataSource;
import io.github.fallingsoulm.easy.archetype.generate.config.GlobalConfig;
import io.github.fallingsoulm.easy.archetype.generate.config.TemplateConfig;
import io.github.fallingsoulm.easy.archetype.generate.conver.IColumnType;
import io.github.fallingsoulm.easy.archetype.generate.conver.IColumnTypeConver;
import io.github.fallingsoulm.easy.archetype.generate.core.TableFieldEntity;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;
import io.github.fallingsoulm.easy.archetype.generate.engine.ITemplateEngine;
import io.github.fallingsoulm.easy.archetype.generate.file.OutputFile;
import io.github.fallingsoulm.easy.archetype.generate.query.ITableQueryResult;
import io.github.fallingsoulm.easy.archetype.generate.utils.NamingStrategy;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 模板组抽象类
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public abstract class AbstractTemplateGroup implements ITemplateGroup {

	@Override
	public String name() {
		return this.getClass().getSimpleName();
	}

	@Override
	public List<TableInfoEntity> getGenerateTableInfo(ITableQueryResult tableQueryResult, GlobalConfig globalConfig,
			IColumnTypeConver columnTypeConver) {
		SimpleDataSource dataSource = new SimpleDataSource(globalConfig.getDataSourceUrl(),
				globalConfig.getDataSourceUserName(), globalConfig.getDataSourcePassWord(),
				globalConfig.getDataSourceDriverClassName());

		List<TableInfoEntity> tableInfoEntitys = tableQueryResult.getTableInfoEntitys(dataSource);

		// 这里进行表过滤
		// List<TableInfoEntity> result = new ArrayList<>();
		if (null != globalConfig.getIncludes() && globalConfig.getIncludes().length > 0) {
			for (String s : globalConfig.getIncludes()) {
				tableInfoEntitys = tableInfoEntitys.stream().filter(a -> ReUtil.isMatch(s, a.getTableName()))
						.collect(Collectors.toList());
			}
		}

		if (null != globalConfig.getExclude() && globalConfig.getExclude().length > 0) {
			for (String s : globalConfig.getExclude()) {
				tableInfoEntitys = tableInfoEntitys.stream().filter(a -> !ReUtil.isMatch(s, a.getTableName()))
						.collect(Collectors.toList());
			}
			// result.addAll(tableInfoEntitys);

		}
		for (TableInfoEntity tableInfoEntity : tableInfoEntitys) {
			// 实体名
			tableInfoEntity.setEntityName(
					entityNameHandler(globalConfig.getRemoveTablePrefix(), tableInfoEntity.getTableName()));
			// 设置字段列表
			List<TableFieldEntity> tableFieldEntitys = tableQueryResult
					.getTableFieldEntitys(tableInfoEntity.getTableName(), dataSource);
			tableInfoEntity.setTableFieldEntityList(tableFieldEntitys);

			// 设置索引列表
			// tableInfoEntity.setTableIndexEntityList(tableQueryResult.getTableIndexEntitys(tableInfoEntity.getTableName(),
			// dataSource));

			for (TableFieldEntity tableFieldEntity : tableFieldEntitys) {
				// 字段驼峰
				tableFieldEntity.setColumnName(NamingStrategy.underlineToCamel(tableFieldEntity.getJdbcFieldName()));
				IColumnType iColumnType = columnTypeConver.convert(tableFieldEntity.getJdbcType());
				if (iColumnType != null) {
					tableFieldEntity.setColumnType(iColumnType.getType());
					tableFieldEntity.setColumnImport(iColumnType.getPak());
				}
			}
		}
		return tableInfoEntitys;
	}

	@Override
	public List<OutputFile> generate(List<TableInfoEntity> tableInfoEntities, ITemplateEngine templateEngine,
			GlobalConfig globalConfig) {
		TemplateBuilder templateBuilder = new TemplateBuilder();
		templateBuilder.globalConfig(globalConfig);

		List<OutputFile> outputFileList = new ArrayList<>();
		for (TableInfoEntity tableInfoEntity : tableInfoEntities) {

			templateBuilder.tableInfoEntity(tableInfoEntity);
			for (ITemplate iTemplate : getTemplateList(templateBuilder)) {

				// 深克隆一下
				TableInfoEntity entity = ObjectUtil.clone(tableInfoEntity);

				// 前置处理
				Map<String, Object> data = new HashMap<>(8);

				TemplateConfig config = iTemplate.config();
				iTemplate.before(entity, config, data);
				// 字段过滤
				List<String> excludeField = iTemplate.excludeField();
				if (CollectionUtil.isNotEmpty(excludeField)) {
					List<TableFieldEntity> tableFieldEntities = entity.getTableFieldEntityList().stream().filter(a -> {
						for (String s : excludeField) {
							if (s.toLowerCase().equals(a.getColumnName().toLowerCase())) {
								return false;
							}
						}
						return true;
					})

							.collect(Collectors.toList());
					entity.setTableFieldEntityList(tableFieldEntities);
				}
				// 设置内置变量
				// 类的名称
				data.put("className", iTemplate.className());
				data.put("config", config);
				data.put("entity", entity);
				data.put("classPkg", iTemplate.classPkg());
				data.put("since", DateUtil.date(new Date()).toString("yyyy-MM-dd"));

				// 全局的配置初始化到作为变量
				data.putAll(globalConfig);

				// 后置处理
				iTemplate.after(data);

				String process = templateEngine.process(data, iTemplate.templatePath());
				OutputFile outputFile = new OutputFile();
				outputFile.setContent(process);
				// 文件名
				outputFile
						.setFileName(fileName(iTemplate, tableInfoEntity.getEntityName()) + iTemplate.fileNameSuffix());
				// 输出路径
				outputFile.setFilePath(getFilePath(iTemplate));
				outputFileList.add(outputFile);
			}
		}

		return outputFileList;
	}

	/**
	 * 获取文件路径
	 * @param iTemplate
	 * @return java.lang.String
	 * @since 2021/2/2
	 */
	protected String getFilePath(ITemplate iTemplate) {
		return iTemplate.classPkg().replaceAll("\\.", "/");
	}

	/**
	 * 获取文件名
	 * @param template
	 * @param name
	 * @return java.lang.String
	 * @since 2021/2/2
	 */
	protected String fileName(ITemplate template, String name) {
		return String.format(template.fileNameFormat(), name);
	}

	/**
	 * 获取模板列表
	 * @param templateBuilder
	 * @return java.util.List<io.github.fallingsoulm.easy.archetype.framework.core.generate.template.ITemplate>
	 * @since 2021/2/2
	 */
	protected List<ITemplate> getTemplateList(TemplateBuilder templateBuilder) {
		List<ITemplate> templateList = new ArrayList<>();
		// 添加模板路径
		registryTemplateList(templateList, templateBuilder);
		return templateList;
	}

	/**
	 * 模板注册
	 * @param templateList 模板列表
	 * @param templateBuilder 模板构建
	 * @return void
	 * @since 2021/2/2
	 */
	public abstract void registryTemplateList(List<ITemplate> templateList, TemplateBuilder templateBuilder);

	/**
	 * 表名转换为实体类
	 * @param removeTablePrefix 移除的表前缀
	 * @param tableName 表名
	 * @return java.lang.String
	 * @since 2021/2/2
	 */
	protected String entityNameHandler(String[] removeTablePrefix, String tableName) {

		if (removeTablePrefix != null && removeTablePrefix.length > 0) {
			for (String tablePrefix : removeTablePrefix) {
				tableName = tableName.startsWith(tablePrefix) ? tableName.replaceFirst(tablePrefix, "") : tableName;
			}
		}
		return NamingStrategy.capitalFirst(NamingStrategy.underlineToCamel(tableName));
	}

}
