package io.github.fallingsoulm.easy.archetype.framework.pipeline;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import io.github.fallingsoulm.easy.archetype.framework.pipeline.callback.TransCallback;
import io.github.fallingsoulm.easy.archetype.framework.pipeline.callback.TransHandlerCallable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 任务执行器
 *
 * @author luyanan
 * @since 2021/7/17
 **/
public class PipelineProcessor {

	public PipelineProcessor(ExecutorService executorService,
							 List<TransHandler> syncTransHandlerList, List<TransHandler> asyncTransHandlerList, TransHandlerContext context) {
		this.executorService = executorService;
		this.syncTransHandlerList = syncTransHandlerList;
		this.asyncTransHandlerList = asyncTransHandlerList;
		this.context = context;
	}

	/**
	 * 线程池
	 *
	 * @author luyanan
	 * @since 2021/7/17
	 */
	private ExecutorService executorService;


	/**
	 * 同步需要执行的任务
	 *
	 * @author luyanan
	 * @since 2021/7/17
	 */
	private List<TransHandler> syncTransHandlerList = new ArrayList<>();


	/**
	 * 异步需要执行的任务
	 *
	 * @author luyanan
	 * @since 2021/7/17
	 */
	private List<TransHandler> asyncTransHandlerList = new ArrayList<>();

	/**
	 * 上下文
	 *
	 * @author luyanan
	 * @since 2021/7/17
	 */
	private TransHandlerContext context;


	/**
	 * 任务开始执行
	 *
	 * @return void
	 * @since 2021/7/17
	 */
	public void start() {
		// 先执行异步任务
		// 1. 初始化线程池
		if (null == this.executorService && CollectionUtil.isNotEmpty(asyncTransHandlerList)) {
			ThreadFactory threadFactory = new ThreadFactoryBuilder().setNamePrefix("pipe-").build();
			this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
					60L, TimeUnit.SECONDS,
					new SynchronousQueue<Runnable>(), threadFactory);
		}
		// 2. 执行异步任务
		for (TransHandler handler : asyncTransHandlerList) {
			if (!handler.support(this.context)) {
				continue;
			}
//			if (null != handler.callback()) {
//				this.executorService.execute(() -> {
//
//					handler.handler(this.context);
//					Future<Object> submit = this.executorService.submit(new Callable<Object>() {
//						@Override
//						public Object call() throws Exception {
//							return null;
//						}
//					});
//					submit.
//
//				});
////				Future<TransCallback> submit = this.executorService.submit(new TransHandlerCallable(this.context, handler));
////				TransCallback transCallback = submit.get();
////				transCallback.call(this.context);
//			} else {
			this.executorService.execute(() -> {
				handler.handler(this.context);
				if (null != handler.callback()) {
					TransCallback callback = handler.callback();
					callback.call(this.context);
				}
			});
//			}
		}


		// 执行同步任务

		boolean haxNext;
		for (TransHandler handler : syncTransHandlerList) {
			if (!handler.support(this.context)) {
				continue;
			}
			haxNext = handler.handler(this.context);

			// 执行回调任务
			if (null != handler.callback()) {
				handler.callback();
			}
			if (!haxNext) {
				break;
			}
		}
	}

	/**
	 * 结束
	 *
	 * @return void
	 * @since 2021/7/17
	 */
	public void shutdown() {
		if (null != this.executorService) {
			this.executorService.shutdown();
		}
	}


}