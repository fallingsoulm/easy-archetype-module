package io.github.fallingsoulm.easy.archetype.generate.config;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.convert.ConversionHandler;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;

import java.io.FileReader;
import java.util.*;

/**
 * 配置文件解析
 *
 * @author luyanan
 * @since 2021/1/31
 **/
public class ConfigParser {

	/**
	 * 配置文件解析
	 * @param configPath 配置文件所在路径
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 * @since 2021/1/31
	 */
	@SneakyThrows
	public Map<String, String> parser(String configPath) {
		Map<String, String> result = new HashMap<>(8);
		VariableConfig variableConfig = new VariableConfig();
		// 内置变量和占位符修复

		configPath = variableConfig.variableReplace(configPath);
		CompositeConfiguration cc = new CompositeConfiguration();
		PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
		propertiesConfiguration.setThrowExceptionOnMissing(true);
		propertiesConfiguration.setListDelimiterHandler(new DefaultListDelimiterHandler(';'));
		propertiesConfiguration.setIncludesAllowed(false);
		propertiesConfiguration.read(new FileReader(configPath));
		cc.addConfiguration(propertiesConfiguration);
		String propertiesSource = cc.getString(GenerateConstants.PROPERTIES_SOURCE);
		if (StrUtil.isNotBlank(propertiesSource)) {
			propertiesSource = variableConfig.variableReplace(propertiesSource);
			if (propertiesSource.endsWith(GenerateConstants.YML)) {
				YAMLConfiguration yamlConfiguration = new YAMLConfiguration();
				yamlConfiguration.read(new FileReader(propertiesSource));
				cc.addConfiguration(yamlConfiguration);
			}
			else if (propertiesSource.endsWith(GenerateConstants.PROPERTIES)) {
				PropertiesConfiguration p = new PropertiesConfiguration();
				p.read(new FileReader(propertiesSource));
				cc.addConfiguration(p);
			}

		}

		Iterator<String> iterator = cc.getKeys();

		cc.setConversionHandler(new ConversionHandler() {
			@Override
			public <T> T to(Object o, Class<T> aClass, ConfigurationInterpolator configurationInterpolator) {
				return valueProcessing(o);
			}

			@Override
			public Object toArray(Object o, Class<?> aClass, ConfigurationInterpolator configurationInterpolator) {
				return o;
			}

			@Override
			public <T> void toCollection(Object o, Class<T> aClass, ConfigurationInterpolator configurationInterpolator,
					Collection<T> collection) {
			}
		});

		while (iterator.hasNext()) {
			String next = iterator.next();
			String string = cc.get(Object.class, next).toString();
			result.put(next, string);
		}

		variableConfig.variableReplace(result);
		return result;
	}

	/**
	 * value值处理
	 * @param obj
	 * @return T
	 * @since 2021/1/31
	 */
	private <T> T valueProcessing(Object obj) {
		if (obj instanceof ArrayList) {
			ArrayList<T> values = (ArrayList) obj;
			// 选择第一个不为空的
			for (T value : values) {
				if (isBoolean(value)) {
					return value;
				}
			}
		}
		return (T) obj;
	}

	/**
	 * 是否为第一个非空
	 * @param value
	 * @return boolean
	 * @since 2021/1/31
	 */
	private <T> boolean isBoolean(T value) {
		return value != null && (value instanceof String && StrUtil.isNotBlank(value.toString()))
				&& value.toString().matches("\\$\\{.*}");
	}

}
