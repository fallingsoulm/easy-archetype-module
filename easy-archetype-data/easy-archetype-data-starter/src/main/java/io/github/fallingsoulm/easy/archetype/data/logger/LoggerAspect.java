package io.github.fallingsoulm.easy.archetype.data.logger;

import io.github.fallingsoulm.easy.archetype.data.logger.annotation.IgnoreLogger;
import io.github.fallingsoulm.easy.archetype.framework.spring.SpringContextHolder;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志拦截器
 *
 * @author luyanan
 * @since 2021/1/22
 **/
@Aspect
@Slf4j
public class LoggerAspect {

	@SneakyThrows
	@Around("@annotation(apiOperation)")
	public Object around(ProceedingJoinPoint proceedingJoinPoint, ApiOperation apiOperation) {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		Method method = methodSignature.getMethod();
		IgnoreLogger ignoreLogger = method.getAnnotation(IgnoreLogger.class);
		// 开始时间
		Long startTime = System.currentTimeMillis();
		Exception exception = null;
		Object result = null;
		try {
			result = proceedingJoinPoint.proceed();
		} catch (Exception e) {
			exception = e;
			throw e;
		} finally {
			if (ignoreLogger == null || !ignoreLogger.type().equals(IgnoreLogger.IgnoreLoggerType.ALL)) {
				// 发布事件
				SpringContextHolder.publishEvent(new LoggerEvent(LoggerVo.builder().startTime(startTime)
						.endTime(System.currentTimeMillis()).exception(exception)
						.request(SpringContextHolder.getRequest())
						.method(method)
						.args((ignoreLogger != null && ignoreLogger.type().equals(IgnoreLogger.IgnoreLoggerType.PARAMS))
								? null : getRequestParams(methodSignature, proceedingJoinPoint.getArgs()))
						.result((ignoreLogger != null
								&& ignoreLogger.type().equals(IgnoreLogger.IgnoreLoggerType.RESULT)) ? null : result)
						.build()));
			}
		}

		return result;
	}

	/**
	 * 获取方法参数
	 *
	 * @param methodSignature 方法签名
	 * @param args            参数
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 * @since 2021/1/23
	 */
	protected Map<String, Object> getRequestParams(MethodSignature methodSignature, Object[] args) {
		Map<String, Object> requestParams = new HashMap<>(16);

		// 参数名
		String[] paramNames = methodSignature.getParameterNames();
		if (null == paramNames || paramNames.length == 0) {
			return new HashMap<>(16);
		}

		for (int i = 0; i < paramNames.length; i++) {
			Object value = args[i];
			if (null == value) {
				continue;
			}

			// 如果是文件对象
			if (value instanceof MultipartFile) {
				MultipartFile file = (MultipartFile) value;
				// 获取文件名
				value = file.getOriginalFilename();
			} else if (value instanceof ServletResponse || value instanceof ServletRequest) {

				continue;
			} else if (value instanceof Model || value.getClass().getName().startsWith("org.springframework")) {
				continue;
			}

			requestParams.put(paramNames[i], value);
		}
		return requestParams;
	}

}
