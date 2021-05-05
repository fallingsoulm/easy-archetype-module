package io.github.fallingsoulm.easy.archetype.security.core;

/**
 * 获取登录的用户Service
 *
 * @author luyanan
 * @since 2021/2/19
 **/
public interface LoginUserService {

	/**
	 * 获取用户id
	 *
	 * @return java.lang.Long
	 * @since 2021/1/31
	 */
	Long getUserId();

	/**
	 * 获取当前用户
	 *
	 * @return io.github.fallingsoulm.easy.archetype.common.user.CurrUserVo
	 * @since 2021/1/31
	 */
	LoginUserVo getUser();
}
