package io.github.fallingsoulm.easy.archetype.framework.pipeline.callback;

import io.github.fallingsoulm.easy.archetype.framework.pipeline.TransHandler;
import io.github.fallingsoulm.easy.archetype.framework.pipeline.TransHandlerContext;
import org.springframework.jca.cci.connection.TransactionAwareConnectionFactoryProxy;

import java.util.concurrent.Callable;

/**
 * 回调的异步任务
 *
 * @author luyanan
 * @since 2021/7/17
 **/
public class TransHandlerCallable implements Callable<TransCallback> {


	/**
	 * 上下文
	 *
	 * @author luyanan
	 * @since 2021/7/17
	 */
	private TransHandlerContext transHandlerContext;

	public TransHandlerCallable(TransHandlerContext transHandlerContext, TransHandler transHandler) {
		this.transHandlerContext = transHandlerContext;
		this.transHandler = transHandler;
	}

	/**
	 * 执行的任务
	 *
	 * @author luyanan
	 * @since 2021/7/17
	 */
	private TransHandler transHandler;

	@Override
	public TransCallback call() throws Exception {
		transHandler.handler(this.transHandlerContext);
		return transHandler.callback();
	}
}
