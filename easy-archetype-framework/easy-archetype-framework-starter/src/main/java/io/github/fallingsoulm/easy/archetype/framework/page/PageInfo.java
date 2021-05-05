package io.github.fallingsoulm.easy.archetype.framework.page;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 分页返回封装
 * </p>
 *
 * @author luyanan
 * @since 2021/1/12
 **/
@NoArgsConstructor
@Data
public class PageInfo<T> implements Serializable {

	private static final long serialVersionUID = -8618901392085749669L;

	/**
	 * <p>
	 * 当前页数
	 * </p>
	 *
	 * @since 2021/1/12
	 */
	private Integer pageNum;

	/**
	 * <p>
	 * 当前每页条数
	 * </p>
	 *
	 * @since 2021/1/12
	 */
	private Integer pageSize;

	/**
	 * <p内容
	 * </p>
	 *
	 * @since 2021/1/12
	 */
	private List<T> content;

	/**
	 * <p>
	 * 总条数
	 * </p>
	 *
	 * @since 2021/1/12
	 */
	private Long totalElements;

	/**
	 * <p>
	 * 是否为起始页
	 * </p>
	 *
	 * @since 2021/1/12
	 */
	private Boolean first;

	/**
	 * <p>
	 * 是否为最后一页
	 * </p>
	 *
	 * @since 2021/1/12
	 */
	private Boolean last;

	/**
	 * <p>
	 * 总页数
	 * </p>
	 *
	 * @since 2021/1/12
	 */
	private Integer totalPages;

	public Long getTotalPages() {
		if (null != this.totalElements) {
			return this.totalElements / pageSize + 1;
		}
		return 0L;
	}

	public boolean getFirst() {
		return 1 == PageRequestParams.FIRST_PAGE_INDEX;
	}

	public void setFirst() {
		this.first = 1 == this.pageSize;
	}

	public PageInfo(List content, Long totalElements, PageRequestParams pageRequestParams) {
		this.totalElements = totalElements;
		this.content = content;
		this.pageNum = pageRequestParams.getPageNum();
		this.pageSize = pageRequestParams.getPageSize();
	}

}
