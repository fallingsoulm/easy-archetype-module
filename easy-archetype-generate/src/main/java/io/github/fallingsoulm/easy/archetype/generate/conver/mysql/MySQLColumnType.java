package io.github.fallingsoulm.easy.archetype.generate.conver.mysql;

import io.github.fallingsoulm.easy.archetype.generate.conver.IColumnType;
import io.github.fallingsoulm.easy.archetype.generate.exception.GeneratorException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mysql类型
 *
 * @author luyanan
 * @since 2021/1/31
 **/
public enum MySQLColumnType implements IColumnType {

	// 数值类型

	/**
	 * TINYINT
	 *
	 * @since 2021/1/31
	 */
	TINYINT("TINYINT", null),
	/**
	 * SMALLINT
	 *
	 * @since 2021/1/31
	 */
	SMALLINT("SMALLINT", null),
	/**
	 * MEDIUMINT
	 *
	 * @since 2021/1/31
	 */
	MEDIUMINT("MEDIUMINT", null),
	/**
	 * INT
	 *
	 * @since 2021/1/31
	 */
	INT("INT", null),
	/**
	 * INTEGER
	 *
	 * @since 2021/1/31
	 */
	INTEGER("INTEGER", null),
	/**
	 * BIGINT
	 *
	 * @since 2021/1/31
	 */
	BIGINT("BIGINT", null),
	/**
	 * FLOAT
	 *
	 * @since 2021/1/31
	 */
	FLOAT("FLOAT", null),
	/**
	 * DOUBLE
	 *
	 * @since 2021/1/31
	 */
	DOUBLE("DOUBLE", null),
	/**
	 * DECIMAL
	 *
	 * @since 2021/1/31
	 */
	DECIMAL("DECIMAL", null),
	// 日期类型
	/**
	 * DATE
	 *
	 * @since 2021/1/31
	 */
	DATE("DATE", null),
	/**
	 * TIME
	 *
	 * @since 2021/1/31
	 */
	TIME("TIME", null),
	/**
	 * YEAR
	 *
	 * @since 2021/1/31
	 */
	YEAR("YEAR", null),
	/**
	 * DATETIME
	 *
	 * @since 2021/1/31
	 */
	DATETIME("DATETIME", null),
	/**
	 * TIMESTAMP
	 *
	 * @since 2021/1/31
	 */
	TIMESTAMP("TIMESTAMP", null),

	// 字符串类型
	/**
	 * CHAR
	 *
	 * @since 2021/1/31
	 */
	CHAR("CHAR", null),
	/**
	 * VARCHAR
	 *
	 * @since 2021/1/31
	 */
	VARCHAR("VARCHAR", null),
	/**
	 * TINYBLOB
	 *
	 * @since 2021/1/31
	 */
	TINYBLOB("TINYBLOB", null),
	/**
	 * TINYTEXT
	 *
	 * @since 2021/1/31
	 */
	TINYTEXT("TINYTEXT", null),
	/**
	 * BLOB
	 *
	 * @since 2021/1/31
	 */
	BLOB("BLOB", null),
	/**
	 * TEXT
	 *
	 * @since 2021/1/31
	 */
	TEXT("TEXT", null),
	/**
	 * MEDIUMBLOB
	 *
	 * @since 2021/1/31
	 */
	MEDIUMBLOB("MEDIUMBLOB", null),
	/**
	 * MEDIUMTEXT
	 *
	 * @since 2021/1/31
	 */
	MEDIUMTEXT("MEDIUMTEXT", null),
	/**
	 * LONGBLOB
	 *
	 * @since 2021/1/31
	 */
	LONGBLOB("LONGBLOB", null),
	/**
	 * LONGTEXT
	 *
	 * @since 2021/1/31
	 */
	LONGTEXT("LONGTEXT", null),
	/**
	 * 未知类型
	 *
	 * @since 2021/1/31
	 */
	UNUNKNOWN_TYPE("UNKNOWN_TYPE", null),

	;

	/**
	 * 数据库类型
	 *
	 * @since 2021/1/31
	 */
	private String type;

	/**
	 * 包路径
	 *
	 * @since 2021/1/31
	 */
	private String pak;

	MySQLColumnType(String type, String pak) {
		this.type = type;
		this.pak = pak;
	}

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getPak() {
		return pak;
	}

	/**
	 * 根据数据库类型获取相应的枚举
	 * @param type 数据库类型
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.conver.mysql.MySQLColumnType
	 * @since 2021/1/31
	 */
	public static MySQLColumnType get(String type) {
		List<MySQLColumnType> allEnums = Arrays.asList(MySQLColumnType.values());
		Map<String, MySQLColumnType> map = new HashMap<String, MySQLColumnType>();
		allEnums.stream().forEach(allEnum -> {
			map.put(allEnum.type, allEnum);
		});

		type = type.toUpperCase();
		MySQLColumnType iColumnType = map.get(type);
		if (null == iColumnType) {
			throw new GeneratorException(MySQLColumnType.UNUNKNOWN_TYPE.type + "--->" + type);
		}
		return iColumnType;
	}

}
