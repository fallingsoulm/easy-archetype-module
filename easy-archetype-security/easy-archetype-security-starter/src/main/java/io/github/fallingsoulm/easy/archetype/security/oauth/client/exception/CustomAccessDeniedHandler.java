package io.github.fallingsoulm.easy.archetype.security.oauth.client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 授权失败后的返回信息
 *
 * @author luyanan
 * @since 2021/2/19
 **/
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		Map<String, Object> map = new HashMap<>();
		map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		map.put("msg", "没有权限");
		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(map));
	}
}
