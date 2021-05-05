package io.github.fallingsoulm.easy.archetype.data.configcenter.jdbc;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReferenceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.handler.RsHandler;
import cn.hutool.db.sql.SqlExecutor;
import io.github.fallingsoulm.easy.archetype.data.configcenter.PropertySource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用JDBC存储配置
 *
 * @author luyanan
 * @since 2021/1/30
 **/
@RequiredArgsConstructor
public class JdbcPropertySource implements PropertySource {

	/**
	 * 默认的分组
	 *
	 * @since 2021/1/30
	 */
	private static final String DEFAULT_GROUP = "default";

	private DataSource dataSource;

	/**
	 * 分组
	 *
	 * @since 2021/1/30
	 */
	private String group;

	public JdbcPropertySource(DataSource dataSource, String group) {
		this.dataSource = dataSource;
		this.group = group;
	}


	public JdbcPropertySource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private String sql = "select id,group_name,config_key,config_value,remark,create_time,update_time from cc_config";

	@SneakyThrows
	@Override
	public Map<String, Object> properties() {
		List<ConfigEntity> configs = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			configs = SqlExecutor.query(connection, sql, (RsHandler<List<ConfigEntity>>) rs -> {
				List<ConfigEntity> configEntities = new ArrayList<>();
				while (rs.next()) {
					ConfigEntity configEntity = new ConfigEntity();
					for (Field declaredField : ConfigEntity.class.getDeclaredFields()) {
						String name = declaredField.getName();
						// 转换为数据库字段
						String s = StrUtil.toSymbolCase(name, '_');
						Object object = rs.getObject(s);
						ReflectUtil.setFieldValue(configEntity, name, object);
					}
					configEntities.add(configEntity);
				}
				return configEntities;
			});

		} finally {
			if (null != connection) {
				connection.close();
			}
		}
		Map<String, Object> properties = new HashMap<>(16);

		if (CollectionUtil.isNotEmpty(configs)) {
			configs.stream().forEach(a -> {
				properties.put(a.getConfigKey(), a.getConfigValue());
			});
		}
		return properties;
	}

}
