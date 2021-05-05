package io.github.fallingsoulm.easy.archetype.framework.jdbc;

import io.github.fallingsoulm.easy.archetype.framework.jdbc.annotation.TableField;
import io.github.fallingsoulm.easy.archetype.framework.jdbc.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统用户
 *
 * @author luyanan
 * @since 2021/3/9
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysUser {

	@TableId(type = FieldType.INPUT)
	private Long id;

	@TableField("nick_name")
	private String nickName;

	/**
	 * 创建时间
	 *
	 * @since 2021/3/9
	 */

	private Date createTime;

}
