package io.github.fallingsoulm.easy.archetype.generate.template;

import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.generate.config.GlobalConfig;
import io.github.fallingsoulm.easy.archetype.generate.config.TemplateConfig;
import io.github.fallingsoulm.easy.archetype.generate.core.TableFieldEntity;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;

import java.util.*;

/**
 * 模板抽象类
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public abstract class AbstractTemplate implements ITemplate {

	/**
	 * 全局配置
	 *
	 * @since 2021/2/2
	 */
	protected GlobalConfig globalConfig;

	/**
	 * 表信息
	 *
	 * @since 2021/2/2
	 */
	protected TableInfoEntity tableInfoEntity;

	/**
	 * 需要导入的包[import]
	 *
	 * @since 2021/2/2
	 */
	protected Set<String> importSet = new HashSet<>();

	/**
	 * 类上需要添加的注解
	 *
	 * @since 2021/2/2
	 */
	protected Set<String> annotationSet = new HashSet<>();

	/**
	 * 忽略显示的字段
	 *
	 * @since 2021/2/2
	 */
	protected List<String> excludeFieldSet = new ArrayList<>();

	/**
	 * 模板配置
	 *
	 * @since 2021/2/2
	 */
	protected TemplateConfig templateConfig = new TemplateConfig();

	/**
	 * 模板处理器
	 *
	 * @since 2021/2/2
	 */
	public TemplateConfigHandler templateConfigHandler;

	/**
	 * 添加需要排除的字段
	 * @param excludeField 需要排除的字段
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.template.AbstractTemplate
	 * @since 2021/2/2
	 */
	public AbstractTemplate setExcludeField(String... excludeField) {
		Arrays.stream(excludeField).filter(a -> StrUtil.isNotBlank(a)).forEach(a -> {
			excludeFieldSet.add(a);
		});
		return this;
	}

	/**
	 * 添加需要排除的字段
	 * @param excludeField 需要排除的字段
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.template.AbstractTemplate
	 * @since 2021/2/2
	 */
	public AbstractTemplate setExcludeField(Collection<String> excludeField) {
		excludeField.stream().filter(a -> StrUtil.isNotBlank(a)).forEach(a -> {
			excludeFieldSet.add(a);
		});
		return this;
	}

	@Override
	public List<String> excludeField() {
		return this.excludeFieldSet;
	}

	/**
	 * 添加 import
	 * @param imports 需要导入的包
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.template.AbstractTemplate
	 * @since 2021/2/2
	 */
	public AbstractTemplate setImport(String... imports) {
		Arrays.stream(imports).filter(a -> StrUtil.isNotBlank(a)).forEach(a -> {
			importSet.add(a);
		});
		return this;
	}

	/**
	 * 添加 import
	 * @param imports 需要导入的包
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.template.AbstractTemplate
	 * @since 2021/2/2
	 */
	public AbstractTemplate setImport(Collection<String> imports) {
		imports.stream().filter(a -> StrUtil.isNotBlank(a)).forEach(a -> {
			importSet.add(a);
		});
		return this;
	}

	/**
	 * 添加 import
	 * @param imports 需要导入的包
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.template.AbstractTemplate
	 * @since 2021/2/2
	 */
	public AbstractTemplate setImport(Class<?>... imports) {
		Arrays.stream(imports).filter(a -> null != a).forEach(a -> {
			importSet.add(a.getName());
		});
		return this;
	}

	/**
	 * 添加注解
	 * @param annotations 注解
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.template.AbstractTemplate
	 * @since 2021/2/2
	 */
	public AbstractTemplate setAnnotations(String... annotations) {
		Arrays.stream(annotations).filter(a -> StrUtil.isNotBlank(a)).forEach(a -> {
			annotationSet.add(a);
		});
		return this;
	}

	/**
	 * 添加注解
	 * @param annotations 注解
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.template.AbstractTemplate
	 * @since 2021/2/2
	 */
	public AbstractTemplate setAnnotations(Class<?>... annotations) {
		Arrays.stream(annotations).filter(a -> null != a).forEach(a -> {
			annotationSet.add("@" + a.getSimpleName());
		});
		return this;
	}

	@Override
	public void setGlobalConfig(GlobalConfig globalConfig) {

		this.globalConfig = globalConfig;
	}

	@Override
	public void setTableInfoEntity(TableInfoEntity tableInfoEntity) {

		this.tableInfoEntity = tableInfoEntity;
	}

	/**
	 * 设置模板处理类
	 * @param templateConfigHandler 模板处理类
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.template.AbstractTemplate
	 * @since 2021/2/2
	 */
	public AbstractTemplate templateConfigHandler(TemplateConfigHandler templateConfigHandler) {
		this.templateConfigHandler = templateConfigHandler;
		templateConfigHandler.handler(templateConfig);
		return this;
	}

	@Override
	public TemplateConfig config() {
		templateConfig.setAnnotations(annotationSet);
		templateConfig.setImports(importSet);
		templateConfig.setClassName(className());
		templateConfig.setFullPkg(fullPkg());
		templateConfig.setClassPkg(fullPkg());
		String idFieldName = getIdField(this.tableInfoEntity).getColumnName();
		templateConfig.setIdFieldName(idFieldName);

		String idFieldType = getIdField(this.tableInfoEntity).getColumnType();
		templateConfig.setIdFieldType(idFieldType);

		this.tableInfoEntity.setIdFieldName(idFieldName);
		this.tableInfoEntity.setIdFieldType(idFieldType);

		String uncapFirstEntityName = StrUtil.lowerFirst(this.className());
		templateConfig.setUncapFirstClassName(uncapFirstEntityName);

		return templateConfig;
	}

	/**
	 * 获取id字段
	 * @param tableInfoEntity
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.core.TableFieldEntity
	 * @since 2021/2/2
	 */
	private TableFieldEntity getIdField(TableInfoEntity tableInfoEntity) {

		List<TableFieldEntity> tableFieldEntities = tableInfoEntity.getTableFieldEntityList();
		TableFieldEntity tableFieldEntity = tableFieldEntities.stream()
				.filter(a -> a.getPrimaryKey().equals(Boolean.TRUE)).findFirst()
				// 当找不见主键的时候,默认寻找第一个不为时间类型的字段作为主键
				.orElse(tableFieldEntities.stream()
						.filter(a -> !a.getJdbcType().contains("time") || !a.getJdbcType().contains("date")).findFirst()
						.orElseThrow(() -> {
							return new NullPointerException(tableInfoEntity.getTableName() + ":找不见主键");
						}));
		return tableFieldEntity;

	}

	@Override
	public String classPkg() {
		return (this.globalConfig.getParentPkg() + "." + module() + "." + pkg()).replaceAll("\\.+", ".");
	}

	@Override
	public String fullPkg() {
		return classPkg() + "." + this.className();
	}

	@Override
	public String className() {
		return String.format(fileNameFormat(), this.tableInfoEntity.getEntityName());
	}

	@Override
	public String fileNameSuffix() {
		return ".java";
	}

	@Override
	public void before(TableInfoEntity tableInfoEntity, TemplateConfig config, Map<String, Object> data) {

	}

	@Override
	public void after(Map<String, Object> data) {

	}

	@Override
	public String module() {
		return this.globalConfig.getModule();
	}

}
