package io.github.fallingsoulm.easy.archetype.data.configcenter.jdbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 配置vo类
 *
 * @author luyanan
 * @since 2021/1/30
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigEntity {

	/**
	 * id
	 *
	 * @since 2021/1/30
	 */
	private Long id;

	/**
	 * 分组
	 *
	 * @since 2021/1/30
	 */
	private String groupName;

	/**
	 * 配置的key
	 *
	 * @since 2021/1/30
	 */
	private String configKey;

	/**
	 * 配置的value值
	 *
	 * @since 2021/1/30
	 */
	private String configValue;

	/**
	 * 备注
	 *
	 * @since 2021/1/30
	 */
	private String remark;

	/**
	 * 创建时间
	 *
	 * @since 2021/1/30
	 */
	private Date createTime;

	/**
	 * 修改时间
	 *
	 * @since 2021/1/30
	 */
	private Date updateTime;

}
