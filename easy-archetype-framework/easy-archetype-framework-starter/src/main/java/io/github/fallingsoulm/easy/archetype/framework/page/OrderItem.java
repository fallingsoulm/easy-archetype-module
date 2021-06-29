package io.github.fallingsoulm.easy.archetype.framework.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排序实体
 *
 * @author luyanan
 * @since 2021/6/27
 **/
@Data
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 3613312305980905219L;
	private String column;
	private boolean asc = true;

	public static OrderItem asc(String column) {
		return build(column, true);
	}

	public static OrderItem desc(String column) {
		return build(column, false);
	}

	public static List<OrderItem> ascs(String... columns) {
		return (List) Arrays.stream(columns).map(OrderItem::asc).collect(Collectors.toList());
	}

	public static List<OrderItem> descs(String... columns) {
		return (List)Arrays.stream(columns).map(OrderItem::desc).collect(Collectors.toList());
	}

	private static OrderItem build(String column, boolean asc) {
		OrderItem item = new OrderItem();
		item.setColumn(column);
		item.setAsc(asc);
		return item;
	}
}
