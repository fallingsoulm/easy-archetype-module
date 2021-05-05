package io.github.fallingsoulm.easy.archetype.generate.conver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java数据类型
 *
 * @author luyanan
 * @since 2021/1/31
 **/
public enum JAVAColumnTypeEnums implements IColumnType {

	/**
	 * byte
	 *
	 * @since 2021/1/31
	 */
	BASE_BYTE("byte", null),

	/**
	 * short 类型
	 *
	 * @since 2021/1/31
	 */
	BASE_SHORT("short", null),

	/**
	 * char类型
	 *
	 * @since 2021/1/31
	 */
	BASE_CHAR("char", null),

	/**
	 * int类型
	 *
	 * @since 2021/1/31
	 */
	BASE_INT("int", null),

	/**
	 * long类型
	 *
	 * @since 2021/1/31
	 */
	BASE_LONG("long", null),

	/**
	 * float 类型
	 *
	 * @since 2021/1/31
	 */
	BASE_FLOAT("float", null),

	/**
	 * double 类型
	 *
	 * @since 2021/1/31
	 */
	BASE_DOUBLE("double", null),

	/**
	 * boolean 类型
	 *
	 * @since 2021/1/31
	 */
	BASE_BOOLEAN("boolean", null),

	/*************** 包装类型 ******************/

	/**
	 * Byte类型
	 *
	 * @since 2021/1/31
	 */
	BYTE("Byte", null),

	/**
	 * Short 类型
	 *
	 * @since 2021/1/31
	 */
	SHORT("Short", null),

	/**
	 * Character 类型
	 *
	 * @since 2021/1/31
	 */
	CHARACTER("Character", null),

	/**
	 * Integer 类型
	 *
	 * @since 2021/1/31
	 */
	INTEGER("Integer", null),

	/**
	 * Long 类型
	 *
	 * @since 2021/1/31
	 */
	LONG("Long", null),

	/**
	 * Float 类型
	 *
	 * @since 2021/1/31
	 */
	FLOAT("Float", null),

	/**
	 * Double 类型
	 *
	 * @since 2021/1/31
	 */
	DOUBLE("Double", null),

	/**
	 * Boolean 类型
	 *
	 * @since 2021/1/31
	 */
	BOOLEAN("Boolean", null),

	/**
	 * String 类型
	 *
	 * @since 2021/1/31
	 */
	STRING("String", null),

	/********************* sql 包下数据类型 **************************/
	/**
	 * Date 类型
	 *
	 * @since 2021/1/31
	 */
	DATE_SQL("Date", "java.sql.Date"),

	/**
	 * Time 类型
	 *
	 * @since 2021/1/31
	 */
	TIME("Time", "java.sql.Time"),

	/**
	 * Timestamp 类型
	 *
	 * @since 2021/1/31
	 */
	TIMESTAMP("Timestamp", "java.sql.Timestamp"),

	/**
	 * Blob 类型
	 *
	 * @since 2021/1/31
	 */
	BLOB("Blob", "java.sql.Blob"),

	/**
	 * Clob 类型
	 *
	 * @since 2021/1/31
	 */
	CLOB("Clob", "java.sql.Clob"),

	/******************** java8 新时间类型 ********************/

	/**
	 * LocalDate 类型
	 *
	 * @since 2021/1/31
	 */
	LOCAL_DATE("LocalDate", "java.time.LocalDate"),
	/**
	 * LocalTime 类型
	 *
	 * @since 2021/1/31
	 */
	LOCAL_TIME("LocalTime", "java.time.LocalTime"),

	/**
	 * Year 类型
	 *
	 * @since 2021/1/31
	 */
	YEAR("Year", "java.time.Year"),

	/**
	 * YearMonth 类型
	 *
	 * @since 2021/1/31
	 */
	YEAR_MONTH("YearMonth", "java.time.YearMonth"),

	/**
	 * LocalDateTime 类型
	 *
	 * @since 2021/1/31
	 */
	LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime"),

	/************** 其他类型 ***********************/

	/**
	 * byte[] 类型
	 *
	 * @since 2021/1/31
	 */
	BYTE_ARRAY("byte[]", null),

	/**
	 * Object 类型
	 *
	 * @since 2021/1/31
	 */
	OBJECT("Object", null),

	/**
	 * Date 类型
	 *
	 * @since 2021/1/31
	 */
	DATE("Date", "java.util.Date"),

	/**
	 * BigInteger 类型
	 *
	 * @since 2021/1/31
	 */
	BIG_INTEGER("BigInteger", "java.math.BigInteger"),

	/**
	 * BigDecimal 类型
	 *
	 * @since 2021/1/31
	 */
	BIG_DECIMAL("BigDecimal", "java.math.BigDecimal");
	;

	/**
	 * 类型
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

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getPak() {
		return pak;
	}

	JAVAColumnTypeEnums(String type, String pak) {
		this.type = type;
		this.pak = pak;
	}

	/**
	 * 根据类型获取java枚举类型
	 * @param type
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.conver.IColumnType
	 * @since 2021/1/31
	 */
	public IColumnType get(String type) {

		List<JAVAColumnTypeEnums> allEnums = Arrays.asList(JAVAColumnTypeEnums.values());
		Map<String, IColumnType> map = new HashMap<>();
		allEnums.stream().forEach(javaColumnTypeEnums -> {
			map.put(javaColumnTypeEnums.type, javaColumnTypeEnums);
		});
		IColumnType iColumnType = map.get(type);
		return iColumnType;
	}

}
