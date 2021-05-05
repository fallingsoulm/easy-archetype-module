package io.github.fallingsoulm.easy.archetype.generate.conver.mysql;

import io.github.fallingsoulm.easy.archetype.generate.conver.IColumnType;
import io.github.fallingsoulm.easy.archetype.generate.conver.IColumnTypeConver;
import io.github.fallingsoulm.easy.archetype.generate.conver.JAVAColumnTypeEnums;

import java.util.HashMap;
import java.util.Map;

/**
 * 字段映射(SQL->JAVA)
 *
 * @author luyanan
 * @since 2021/1/31
 **/
public class MysqlColumnTypeConver implements IColumnTypeConver {

	@Override
	public IColumnType convert(String jdbcType) {

		return COLUMN_TYPE_MAP.get(jdbcType.toUpperCase());
	}

	/**
	 * 数据库类型与java类型的对应关系存储
	 *
	 * @since 2021/1/31
	 */
	private static Map<String, IColumnType> COLUMN_TYPE_MAP = new HashMap<>();

	static {

		COLUMN_TYPE_MAP.put(MySQLColumnType.TINYINT.getType(), JAVAColumnTypeEnums.INTEGER);
		COLUMN_TYPE_MAP.put(MySQLColumnType.SMALLINT.getType(), JAVAColumnTypeEnums.INTEGER);
		COLUMN_TYPE_MAP.put(MySQLColumnType.MEDIUMINT.getType(), JAVAColumnTypeEnums.LONG);
		COLUMN_TYPE_MAP.put(MySQLColumnType.INT.getType(), JAVAColumnTypeEnums.INTEGER);
		COLUMN_TYPE_MAP.put(MySQLColumnType.INTEGER.getType(), JAVAColumnTypeEnums.INTEGER);
		COLUMN_TYPE_MAP.put(MySQLColumnType.BIGINT.getType(), JAVAColumnTypeEnums.LONG);
		COLUMN_TYPE_MAP.put(MySQLColumnType.FLOAT.getType(), JAVAColumnTypeEnums.FLOAT);
		COLUMN_TYPE_MAP.put(MySQLColumnType.DOUBLE.getType(), JAVAColumnTypeEnums.DOUBLE);
		COLUMN_TYPE_MAP.put(MySQLColumnType.DECIMAL.getType(), JAVAColumnTypeEnums.BIG_DECIMAL);
		COLUMN_TYPE_MAP.put(MySQLColumnType.DATE.getType(), JAVAColumnTypeEnums.DATE);
		COLUMN_TYPE_MAP.put(MySQLColumnType.TIME.getType(), JAVAColumnTypeEnums.DATE);
		COLUMN_TYPE_MAP.put(MySQLColumnType.YEAR.getType(), JAVAColumnTypeEnums.DATE);
		COLUMN_TYPE_MAP.put(MySQLColumnType.DATETIME.getType(), JAVAColumnTypeEnums.DATE);
		COLUMN_TYPE_MAP.put(MySQLColumnType.TIMESTAMP.getType(), JAVAColumnTypeEnums.DATE);
		COLUMN_TYPE_MAP.put(MySQLColumnType.CHAR.getType(), JAVAColumnTypeEnums.STRING);
		COLUMN_TYPE_MAP.put(MySQLColumnType.VARCHAR.getType(), JAVAColumnTypeEnums.STRING);
		COLUMN_TYPE_MAP.put(MySQLColumnType.TINYBLOB.getType(), JAVAColumnTypeEnums.STRING);
		COLUMN_TYPE_MAP.put(MySQLColumnType.TINYTEXT.getType(), JAVAColumnTypeEnums.STRING);
		COLUMN_TYPE_MAP.put(MySQLColumnType.BLOB.getType(), JAVAColumnTypeEnums.STRING);
		COLUMN_TYPE_MAP.put(MySQLColumnType.TEXT.getType(), JAVAColumnTypeEnums.STRING);
		COLUMN_TYPE_MAP.put(MySQLColumnType.MEDIUMBLOB.getType(), JAVAColumnTypeEnums.STRING);
		COLUMN_TYPE_MAP.put(MySQLColumnType.MEDIUMTEXT.getType(), JAVAColumnTypeEnums.STRING);
		COLUMN_TYPE_MAP.put(MySQLColumnType.LONGBLOB.getType(), JAVAColumnTypeEnums.STRING);
		COLUMN_TYPE_MAP.put(MySQLColumnType.LONGTEXT.getType(), JAVAColumnTypeEnums.STRING);

	}

	/**
	 * 添加关联关系
	 * @param jdbcTye
	 * @param columnType
	 * @return void
	 * @since 2021/1/31
	 */
	public void add(String jdbcTye, IColumnType columnType) {
		COLUMN_TYPE_MAP.put(jdbcTye.toUpperCase(), columnType);
	}

}
