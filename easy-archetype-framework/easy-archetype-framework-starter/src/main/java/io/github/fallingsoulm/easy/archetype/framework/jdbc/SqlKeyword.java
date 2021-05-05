package io.github.fallingsoulm.easy.archetype.framework.jdbc;

import java.text.MessageFormat;

/**
 * SQL关键字
 *
 * @author luyanan
 * @since 2021/3/8
 **/
public enum SqlKeyword {

	AND(" AND {0} = ?"),
	OR("OR"),
	IN(" AND {0} IN "),
	NOT("NOT"),
	LIKE("LIKE CONCAT ({0},?,{1}) "),
	EQ("{0} = ?"),
	NE("{0} <> ?"),
	GT("{0} > ?"),
	GE("{0} >= ?"),
	LT("{0} < ?"),
	LE("{0} <= ?"),
	IS_NULL("{0} IS NULL"),
	IS_NOT_NULL("{0} IS NOT NULL"),
	GROUP_BY("GROUP BY {0}"),
	HAVING("HAVING"),
	ORDER_BY("ORDER BY {0}"),
	EXISTS("EXISTS"),
	BETWEEN("BETWEEN ? AND ? "),
	ASC("ASC"),
	DESC("DESC");

	private final String keyword;

	SqlKeyword(final String keyword) {
		this.keyword = keyword;
	}

	public String getSqlSegment(Object... args) {
		return MessageFormat.format(this.keyword, args);
	}

}
