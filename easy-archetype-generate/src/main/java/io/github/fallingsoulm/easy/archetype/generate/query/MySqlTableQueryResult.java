package io.github.fallingsoulm.easy.archetype.generate.query;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.RsHandler;
import io.github.fallingsoulm.easy.archetype.generate.core.TableFieldEntity;
import io.github.fallingsoulm.easy.archetype.generate.core.TableIndexEntity;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mysql数据查询
 *
 * @author luyanan
 * @since 2021/2/1
 **/
public class MySqlTableQueryResult implements ITableQueryResult {

	/**
	 * 获取表的所有字段信息 另一种写法 show full fields from tableName
	 */
	private static String TABLE_FIELD_SQL = "select * from information_schema.columns where table_name =    ? AND table_schema = ? ";

	/**
	 * 获取当前数据库的所有表信息 另一种写法 : show table status;
	 */
	private static String TABLE_INFO_SQL = "select table_name AS tableName,ENGINE as pipeline,"
			+ " CREATE_TIME AS createTime, TABLE_COLLATION AS encode, "
			+ "TABLE_COMMENT AS COMMENT from information_schema.tables where table_schema =  ? ";

	/**
	 * 查询索引
	 */
	private static String TABLE_INDEX_SQL = "show index from   ";

	@Override
	public List<TableInfoEntity> getTableInfoEntitys(DataSource dataSource) {
		List<TableInfoEntity> tableInfoEntities = new ArrayList<>();
		try {
			String dataBase = dataSource.getConnection().getCatalog();
			List<Entity> query = Db.use(dataSource).query(TABLE_INFO_SQL, dataBase);
			for (Entity entity : query) {
				TableInfoEntity tableInfoEntity = TableInfoEntity.builder().tableName(entity.getStr("tableName"))
						.comment(entity.getStr("COMMENT")).engine(entity.getStr("pipeline"))
						.createTime(entity.getDate("createTime")).encode(entity.getStr("encode")).build();

				tableInfoEntities.add(tableInfoEntity);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return tableInfoEntities;
	}

	@Override
	public List<TableFieldEntity> getTableFieldEntitys(String tableName, DataSource dataSource) {

		try {
			List<TableFieldEntity> query = Db.use(dataSource).query(TABLE_FIELD_SQL,
					new RsHandler<List<TableFieldEntity>>() {

						@Override
						public List<TableFieldEntity> handle(ResultSet rs) throws SQLException {
							List<TableFieldEntity> tableFieldEntities = new ArrayList<>();
							while (rs.next()) {
								TableFieldEntity tableFieldEntity = new TableFieldEntity();
								// 字段类型·
								tableFieldEntity.setJdbcFieldName(
										rs.getString("COLUMN_NAME") == null ? "" : rs.getString("COLUMN_NAME"));
								// 字段类型

								String dateType = rs.getString("DATA_TYPE");

								tableFieldEntity.setJdbcType(dateType == null ? "" : dateType);
								// 是否可以为空

								tableFieldEntity
										.setNullable("no".equalsIgnoreCase(rs.getObject("IS_NULLABLE").toString())
												? Boolean.FALSE : Boolean.TRUE);
								// 默认值
								tableFieldEntity.setColumnDefault(
										rs.getString("COLUMN_DEFAULT") == null ? "" : rs.getString("COLUMN_DEFAULT"));
								// 编码
								tableFieldEntity.setJdbcColumnCharacter(rs.getString("CHARACTER_SET_NAME") == null ? ""
										: rs.getString("CHARACTER_SET_NAME"));
								// 字段长度
								tableFieldEntity.setJdbcLength(rs.getString("COLUMN_TYPE").toUpperCase()
										.replace(rs.getString("DATA_TYPE").toUpperCase(), ""));
								// 是否为主键
								tableFieldEntity.setPrimaryKey(
										"PRI".equals(rs.getString("COLUMN_KEY")) ? Boolean.TRUE : Boolean.FALSE);
								// 是否自增
								tableFieldEntity.setIncrement("auto_increment".equalsIgnoreCase(rs.getString("EXTRA"))
										? Boolean.TRUE : Boolean.FALSE);
								// 注释
								tableFieldEntity.setComment(rs.getString("COLUMN_COMMENT").replaceAll("\\n", ""));
								tableFieldEntities.add(tableFieldEntity);
							}
							return tableFieldEntities;
						}
					}, tableName, dataSource.getConnection().getCatalog());
			return query;

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<TableIndexEntity> getTableIndexEntitys(String tableName, DataSource dataSource) {
		List<TableIndexEntity> tableIndexEntities = new ArrayList<>();
		Map<String, TableIndexEntity> map = new HashMap<>(8);

		try {
			Db.use(dataSource).query(TABLE_INDEX_SQL + tableName, new RsHandler() {
				@Override
				public Object handle(ResultSet resultSet) throws SQLException {

					TableIndexEntity tableIndexEntity = null;
					while (resultSet.next()) {
						String keyName = resultSet.getString("Key_name");
						// 字段名称
						String columnName = resultSet.getString("Column_name");

						// 当包含的时候,直接追加字段就可以了
						if (map.containsKey(keyName)) {
							tableIndexEntity = map.get(keyName);
							tableIndexEntity.setColumn(tableIndexEntity.getColumn() + "," + columnName);
						}
						else {
							tableIndexEntity = TableIndexEntity.builder()
									// 索引名称
									.name(keyName == null ? "" : keyName)
									// 索引字段
									.column(columnName == null ? "" : columnName)
									// 索引类型
									.indexType(resultSet.getString("Index_type") == null ? ""
											: resultSet.getString("Index_type"))
									// 索引方法
									.indexMethod(resultSet.getInt("Non_unique") == 0 ? "UNIQUE" : "NORMAL")
									// 索引注释
									.indexComment(resultSet.getString("Index_comment") == null ? ""
											: resultSet.getString("Index_comment"))
									.build();
						}
						map.put(keyName, tableIndexEntity);
					}
					return null;
				}

			});
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return map.values().stream().collect(Collectors.toList());
	}

}
