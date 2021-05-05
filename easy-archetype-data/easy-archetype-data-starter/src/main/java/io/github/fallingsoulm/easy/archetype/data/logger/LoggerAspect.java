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

import java.lang.reflect.Method;

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
		}
		catch (Exception e) {
			exception = e;
			throw e;
		}
		finally {
			if (ignoreLogger == null || !ignoreLogger.type().equals(IgnoreLogger.IgnoreLoggerType.ALL)) {
				// 发布事件
				SpringContextHolder.publishEvent(new LoggerEvent(LoggerVo.builder().startTime(startTime)
						.apiOperation(apiOperation).endTime(System.currentTimeMillis()).exception(exception)
						.request(SpringContextHolder.getRequest())
						.methodSignature((MethodSignature) proceedingJoinPoint.getSignature())
						.args((ignoreLogger != null && ignoreLogger.type().equals(IgnoreLogger.IgnoreLoggerType.PARAMS))
								? null : proceedingJoinPoint.getArgs())
						.result((ignoreLogger != null
								&& ignoreLogger.type().equals(IgnoreLogger.IgnoreLoggerType.RESULT)) ? null : result)
						.build()));
			}
		}

		return result;
	}

}
