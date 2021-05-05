package io.github.fallingsoulm.easy.archetype.framework.page;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统一返回
 *
 * @author luyanan
 * @since 2021/1/17
 **/
@Data
public class RespEntity<T> implements Serializable {

	private static final long serialVersionUID = 1996369589258128651L;

	public static final String SUCCESS_STATUS = "200";

	/**
	 * <p>
	 * 返回状态码
	 * </p>
	 *
	 * @since 2021/1/17
	 */
	private String status;

	/**
	 * <p>
	 * 消息
	 * </p>
	 *
	 * @since 2021/1/17
	 */
	private Object[] msg;

	/**
	 * <p>
	 * 返回的数据
	 * </p>
	 *
	 * @since 2021/1/17
	 */
	private T data;

	/**
	 * <p>
	 * 错误时间戳
	 * </p>
	 *
	 * @since 2021/1/17
	 */
	private Long timestamp;

	/**
	 * <p>
	 * 错误路径
	 * </p>
	 *
	 * @since 2021/1/17
	 */
	private String path;


	/**
	 * 请求id
	 *
	 * @since 2021/2/28
	 */
	private String requestId;


	public String getMsg() {
		if (null != msg && msg.length > 0) {
			return Arrays.stream(msg).map(a -> a.toString()).collect(Collectors.joining(","));
		}
		return null;
	}

	/**
	 * 成功返回
	 *
	 * @param data 返回的数据
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.RespEntity<T>
	 * @since 2021/1/24
	 */
	public static <T> RespEntity<T> success(T data) {
		return initSuccessData(data, SUCCESS_STATUS, null);
	}

	/**
	 * 成功返回
	 *
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.RespEntity<T>
	 * @since 2021/1/24
	 */
	public static RespEntity success() {
		return initSuccessData(null, SUCCESS_STATUS, null);
	}

	/**
	 * 成功返回,返回类型为Map
	 *
	 * @param handler map处理类
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.page.RespEntity
	 * @since 2021/2/28
	 */
	public static RespEntity success(RespEntityMapHandler handler) {
		Map<String, Object> map = new HashMap<>(16);
		handler.handler(map);
		return initSuccessData(map, SUCCESS_STATUS, null);
	}


	/**
	 * 是否成功
	 *
	 * @return boolean
	 * @since 2021/2/28
	 */
	public boolean isSuccess() {
		return this.getStatus().equals(SUCCESS_STATUS);
	}

	/**
	 * 是否失败
	 *
	 * @return boolean
	 * @since 2021/2/28
	 */
	public boolean isError() {
		return !isSuccess();
	}


	/**
	 * 异常信息
	 *
	 * @param status
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.RespEntity<T>
	 * @since 2021/1/24
	 */
	public static <T> RespEntity<T> error(String status, Object... args) {
		return initErrorData(status, args);
	}

	@FunctionalInterface
	public static interface RespEntityMapHandler {

		/**
		 * 设置元素
		 *
		 * @param map
		 * @return void
		 * @since 2021/2/4
		 */
		void handler(Map<String, Object> map);

	}


	/**
	 * 初始化成功的数据
	 *
	 * @param data   数据
	 * @param msg    消息
	 * @param status 状态码
	 * @return void
	 * @since 2021/2/28
	 */
	private static <T> RespEntity initSuccessData(T data, String status, Object... msg) {
		RespEntity<T> respEntity = new RespEntity();
		respEntity.setData(data);
		respEntity.setMsg(msg);
		respEntity.setStatus(status);
		if (null != GlobalRespEntity.GLOBAL_RESPENTITY_HANDLER) {
			GlobalRespEntity.GLOBAL_RESPENTITY_HANDLER.successHandler(respEntity);
		}
		return respEntity;
	}


	/**
	 * 初始化异常数据
	 *
	 * @param msg    异常消息
	 * @param status 异常编码
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.page.RespEntity<T>
	 * @since 2021/2/28
	 */
	private static <T> RespEntity<T> initErrorData(String status, Object... msg) {
		RespEntity<T> respEntity = new RespEntity();
		respEntity.setMsg(msg);
		respEntity.setStatus(status);
		if (null != GlobalRespEntity.GLOBAL_RESPENTITY_HANDLER) {
			GlobalRespEntity.GLOBAL_RESPENTITY_HANDLER.errorHandler(respEntity);
		}
		return respEntity;
	}

}