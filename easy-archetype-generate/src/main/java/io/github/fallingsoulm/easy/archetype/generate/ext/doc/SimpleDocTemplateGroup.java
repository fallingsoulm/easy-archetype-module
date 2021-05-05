package io.github.fallingsoulm.easy.archetype.generate.ext.doc;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.db.ds.simple.SimpleDataSource;
import io.github.fallingsoulm.easy.archetype.generate.config.GlobalConfig;
import io.github.fallingsoulm.easy.archetype.generate.conver.IColumnType;
import io.github.fallingsoulm.easy.archetype.generate.conver.IColumnTypeConver;
import io.github.fallingsoulm.easy.archetype.generate.core.TableFieldEntity;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;
import io.github.fallingsoulm.easy.archetype.generate.engine.ITemplateEngine;
import io.github.fallingsoulm.easy.archetype.generate.file.OutputFile;
import io.github.fallingsoulm.easy.archetype.generate.query.ITableQueryResult;
import io.github.fallingsoulm.easy.archetype.generate.template.AbstractTemplateGroup;
import io.github.fallingsoulm.easy.archetype.generate.template.ITemplate;
import io.github.fallingsoulm.easy.archetype.generate.template.TemplateBuilder;
import io.github.fallingsoulm.easy.archetype.generate.utils.NamingStrategy;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据库文档生成
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public class SimpleDocTemplateGroup extends AbstractTemplateGroup {

	@Override
	public String name() {
		return this.getClass().getName();
	}

	@Override
	public List<OutputFile> generate(List<TableInfoEntity> tableInfoEntities, ITemplateEngine templateEngine,
			GlobalConfig globalConfig) {
		List<OutputFile> outputFileList = new ArrayList<>();
		List<ITemplate> templateList = new ArrayList<>();
		TemplateBuilder templateBuilder = TemplateBuilder.builder().tableInfoEntity(new TableInfoEntity())
				.globalConfig(globalConfig);
		registryTemplateList(templateList, templateBuilder);
		for (ITemplate template : templateList) {
			Map<String, Object> data = new HashMap<>(8);
			data.put("tableInfoEntities", tableInfoEntities);
			data.put("since", DateUtil.date(new Date()).toString("yyyy-MM-dd"));
			String fileName = fileName(template, "数据库文档");
			// defaultInitData(data, globalConfig);
			String process = templateEngine.process(data, template.templatePath());

			String filePath = getFilePath(template);
			OutputFile outputFile = new OutputFile();
			outputFile.setContent(process);
			outputFile.setFileName(fileName + template.fileNameSuffix());
			outputFile.setFilePath(filePath);
			outputFileList.add(outputFile);
		}
		return outputFileList;
	}

	@Override
	public void registryTemplateList(List<ITemplate> templateList, TemplateBuilder templateBuilder) {
		ITemplate documentTemplate = templateBuilder.build(new DocumentTemplate());
		templateList.add(documentTemplate);
	}

	@Override
	public List<TableInfoEntity> getGenerateTableInfo(ITableQueryResult tableQueryResult, GlobalConfig globalConfig,
			IColumnTypeConver columnTypeConver) {
		SimpleDataSource dataSource = new SimpleDataSource(globalConfig.getDataSourceDriverClassName(),
				globalConfig.getDataSourceUrl(), globalConfig.getDataSourceUserName(),
				globalConfig.getDataSourcePassWord());

		List<TableInfoEntity> tableInfoEntitys = tableQueryResult.getTableInfoEntitys(dataSource);

		// 这里进行表过滤
		List<TableInfoEntity> result = new ArrayList<>();
		if (null != globalConfig.getIncludes() && globalConfig.getIncludes().length > 0) {
			for (String s : globalConfig.getIncludes()) {
				result.addAll(tableInfoEntitys.stream().filter(a -> ReUtil.isMatch(s, a.getTableName()))
						.collect(Collectors.toList()));
			}
		}

		if (null != globalConfig.getExclude() && globalConfig.getExclude().length > 0) {
			for (String s : globalConfig.getExclude()) {
				tableInfoEntitys = tableInfoEntitys.stream().filter(a -> !ReUtil.isMatch(s, a.getTableName()))
						.collect(Collectors.toList());
			}
			result.addAll(tableInfoEntitys);

		}
		for (TableInfoEntity tableInfoEntity : result) {
			// 实体名
			tableInfoEntity.setEntityName(
					entityNameHandler(globalConfig.getRemoveTablePrefix(), tableInfoEntity.getTableName()));
			// 设置字段列表
			List<TableFieldEntity> tableFieldEntitys = tableQueryResult
					.getTableFieldEntitys(tableInfoEntity.getTableName(), dataSource);
			tableInfoEntity.setTableFieldEntityList(tableFieldEntitys);

			// 设置索引列表
			tableInfoEntity.setTableIndexEntityList(
					tableQueryResult.getTableIndexEntitys(tableInfoEntity.getTableName(), dataSource));

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
		return result;
	}

}
