package io.github.fallingsoulm.easy.archetype.security.core;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 实现
 *
 * @author luyanan
 * @since 2021/2/19
 **/
@Slf4j
public class LoginUserServiceImpl implements LoginUserService {
	@Override
	public Long getUserId(boolean required) {
		LoginUserVo user = getUser(required);
		return null != user ? user.getUserId() : null;
	}

	@Override
	public LoginUserVo getUser(boolean required) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			if (required) {
				throw new AccountExpiredException("请重新登录");
			} else {
				return null;
			}
		}


		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (null == principal ||
				(principal instanceof String && principal.toString().equals("anonymousUser"))) {
			if (required) {
				throw new AccountExpiredException("请重新登录");
			} else {
				return null;
			}
		}
		try {
			return JSON.parseObject(principal.toString(), LoginUserVo.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取用户信息失败:{}", e);
			return null;
		}
	}
}
