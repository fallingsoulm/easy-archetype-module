package io.github.fallingsoulm.easy.archetype.framework.extension;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * 参数
 *
 * @author luyanan
 * @since 2021/3/4
 **/
@Getter
public class Params {

	/**
	 * 分组
	 *
	 * @since 2021/3/4
	 */

	private String group;


	/**
	 * 参数
	 *
	 * @since 2021/3/4
	 */

	private Set<String> params = new HashSet<>();


	/**
	 * 设置分组
	 *
	 * @param group 分组
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.extension.Params
	 * @since 2021/3/4
	 */
	public Params addGroup(String group) {
		this.group = group;
		return this;
	}

	/**
	 * 设置参数
	 *
	 * @param param 参数
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.extension.Params
	 * @since 2021/3/4
	 */
	public Params addParam(String param) {
		params.add(param);
		return this;
	}

	/**
	 * 移除参数
	 *
	 * @param param 参数
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.extension.Params
	 * @since 2021/3/4
	 */
	public Params remove(String param) {
		params.remove(param);
		return this;
	}

	/**
	 * builder
	 *
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.extension.Params
	 * @since 2021/3/7
	 */
	public static Params builder() {
		return new Params();
	}

}
