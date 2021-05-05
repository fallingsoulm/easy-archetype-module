package io.github.fallingsoulm.easy.archetype.framework.page;

/**
 * 全局RespEntity处理器
 *
 * @author luyanan
 * @since 2021/2/27
 **/
public class GlobalRespEntity {

	public static GlobalRespEntityHandler GLOBAL_RESPENTITY_HANDLER;

	public static void handler(GlobalRespEntityHandler globalRespEntityHandler) {
		GlobalRespEntity.GLOBAL_RESPENTITY_HANDLER = globalRespEntityHandler;
	}


	/**
	 * 全局返回处理
	 *
	 * @since 2021/2/27
	 */
	public interface GlobalRespEntityHandler<T> {
		/**
		 * 成功的处理
		 *
		 * @param respEntity 返回结果
		 * @return void
		 * @since 2021/2/27
		 */
		default void successHandler(RespEntity<T> respEntity) {

		}


		/**
		 * 异常的处理
		 *
		 * @param respEntity
		 * @return void
		 * @since 2021/2/27
		 */
		default void errorHandler(RespEntity<T> respEntity) {

		}
	}
}
