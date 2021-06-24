package io.github.fallingsoulm.easy.archetype.data.logger;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志类的vo
 *
 * @author luyanan
 * @since 2021/1/23
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoggerVo {

	/**
	 * 方法
	 *
	 * @since 2021/1/23
	 */
	private Method method;

	/**
	 * 请求参数
	 *
	 * @author luyanan
	 * @since 2021/1/23
	 */
	private Map<String, Object> args;

	/**
	 * 抛出的异常
	 *
	 * @since 2021/1/23
	 */
	private Throwable exception;

	/**
	 * 开始时间
	 *
	 * @since 2021/1/23
	 */
	private Long startTime;

	/**
	 * 结束时间
	 *
	 * @since 2021/1/23
	 */
	private Long endTime;

	/**
	 * 返回结果
	 *
	 * @since 2021/1/23
	 */
	private Object result;

	/**
	 * http request对象
	 *
	 * @since 2021/1/23
	 */
	private HttpServletRequest request;

}
