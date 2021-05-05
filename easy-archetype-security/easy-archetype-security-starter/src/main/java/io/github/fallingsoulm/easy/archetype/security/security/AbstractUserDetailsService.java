package io.github.fallingsoulm.easy.archetype.security.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import io.github.fallingsoulm.easy.archetype.security.core.LoginUserVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户查询抽象类
 *
 * @author luyanan
 * @since 2021/2/18
 **/
@Slf4j
public abstract class AbstractUserDetailsService implements UserDetailsService {
	@SneakyThrows
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("用户:{}开始登录", username);
		// 查询用户信息
		LoginUserVo loginUserVo = loginUserVo(username);
		if (null == loginUserVo) {
			throw new UsernameNotFoundException("用户:" + username + "不存在");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (CollectionUtil.isNotEmpty(loginUserVo.getPermissions())) {
			authorities = loginUserVo.getPermissions()
					.stream()
					.filter(a -> StrUtil.isNotBlank(a))
					.map(a -> new SimpleGrantedAuthority(a))
					.distinct()
					.collect(Collectors.toList());
		}
		String password = loginUserVo.getPassword();
		loginUserVo.setPassword(null);
		ObjectMapper objectMapper = new ObjectMapper();
		return new User(objectMapper.writeValueAsString(loginUserVo), password, authorities);
	}


	/**
	 * 获取用户信息
	 *
	 * @param username
	 * @return io.github.fallingsoulm.easy.archetype.security.core.LoginUserVo
	 * @since 2021/2/18
	 */
	protected abstract LoginUserVo loginUserVo(String username);
}
