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
	default Long getUserId() {
		return getUserId(true);
	}

	/**
	 * 获取当前用户
	 *
	 * @return io.github.fallingsoulm.easy.archetype.common.user.CurrUserVo
	 * @since 2021/1/31
	 */
	default LoginUserVo getUser() {

		return getUser(true);
	}


	/**
	 * 获取用户id
	 *
	 * @param required 是否必须
	 * @return java.lang.Long
	 * @since 2021/1/31
	 */
	Long getUserId(boolean required);

	/**
	 * 获取当前用户
	 *
	 * @param required 是否必须
	 * @return io.github.fallingsoulm.easy.archetype.common.user.CurrUserVo
	 * @since 2021/1/31
	 */
	LoginUserVo getUser(boolean required);
}
