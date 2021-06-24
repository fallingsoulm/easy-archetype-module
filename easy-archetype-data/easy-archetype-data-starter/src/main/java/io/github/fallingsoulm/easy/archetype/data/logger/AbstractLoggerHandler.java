package io.github.fallingsoulm.easy.archetype.data.logger;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志解析
 *
 * @author luyanan
 * @since 2021/1/23
 **/
public abstract class AbstractLoggerHandler implements LoggerHandler {

	@Override
	public void handler(LoggerVo loggerVo) {
		Method method = loggerVo.getMethod();
		// 忽略注解
		Map<String, Object> loggerData = new HashMap<>(16);
		// 方法名
		loggerData.put("methodName", method.getDeclaringClass().getName() + "." + method.getName());
		// 请求url
		loggerData.put("uri", loggerVo.getRequest().getRequestURI());
		// 来源ip
		loggerData.put("sourceIp", ServletUtil.getClientIPByHeader(loggerVo.getRequest()));
		// 开始时间
		loggerData.put("startTime", loggerVo.getStartTime() + "");
		// 结束时间
		loggerData.put("endTime", loggerVo.getEndTime() + "");
		// 参数
		loggerData.put("params", null == loggerVo.getArgs() ? null
				: loggerVo.getArgs());
		// 方法介绍
		loggerData.put("desp", getDesp(loggerVo.getMethod()));
		// 头部信息
		// 耗时
		if (null != loggerVo.getEndTime() && null != loggerVo.getStartTime()) {

			loggerData.put("elapsedTime", (loggerVo.getEndTime() - loggerVo.getStartTime()) + "");
		}

		// 状态
		Integer status = 200;

		if (null != loggerVo.getException()) {
			status = 500;
			loggerData.put("errorMsg", ExceptionUtil.getMessage(loggerVo.getException()));
		}
		loggerData.put("status", status);
		// 返回结果
		loggerData.put("result", null != loggerVo.getResult() ? JSON.toJSONString(loggerVo.getResult())
				: JSON.toJSONString(new

				Object()));

		handler(loggerVo, loggerData);

	}

	/**
	 * 结果处理
	 *
	 * @param loggerVo   日志类
	 * @param loggerData 解析结果
	 * @return void
	 * @since 2021/1/23
	 */
	protected abstract void handler(LoggerVo loggerVo, Map<String, Object> loggerData);


	/**
	 * 方法介绍分割线
	 *
	 * @since 2021/1/24
	 */
	protected static final String DESP_DIVIDING_LINE = "-";

	/**
	 * 获取方法介绍
	 *
	 * @param method
	 * @return java.lang.String
	 * @since 2021/1/23
	 */
	protected String getDesp(Method method) {
		StringBuilder desp = new StringBuilder();
		if (method.getDeclaringClass().isAnnotationPresent(Api.class)) {
			String apiValue = method.getDeclaringClass().getAnnotation(Api.class).value();
			if (StringUtils.isEmpty(apiValue)) {
				apiValue = method.getDeclaringClass().getAnnotation(Api.class).description();
			}
			desp.append(apiValue).append(DESP_DIVIDING_LINE);
		}
		if (method.isAnnotationPresent(ApiOperation.class)) {
			desp.append(method.getAnnotation(ApiOperation.class).value());
		}
		if (desp.toString().endsWith(DESP_DIVIDING_LINE)) {
			desp.deleteCharAt(desp.length() - 1);
		}
		return desp.toString();
	}

}
