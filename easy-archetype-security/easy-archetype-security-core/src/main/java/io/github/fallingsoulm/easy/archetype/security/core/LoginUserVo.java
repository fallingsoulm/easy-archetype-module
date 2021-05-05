package io.github.fallingsoulm.easy.archetype.security.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 登录用户的Vo类
 *
 * @author luyanan
 * @since 2021/2/18
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo {

	/**
	 * 用户id
	 *
	 * @since 2021/1/31
	 */
	private Long userId;

	/**
	 * 用户账号
	 */
	private String userName;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 用户类型（00系统用户）
	 */
	private String userType;


	/**
	 * 密码
	 *
	 * @since 2021/2/18
	 */

	private String password;
	/**
	 * 权限列表
	 *
	 * @since 2021/2/12
	 */
	private Set<String> permissions;


	/**
	 * 角色列表
	 *
	 * @since 2021/2/12
	 */
	private Set<String> roles;

	/**
	 * 附加的一些东西
	 *
	 * @since 2021/2/18
	 */

	private Map<String, Object> data = new HashMap<>();

}
