package io.github.fallingsoulm.easy.archetype.framework.pipeline;

import cn.hutool.core.lang.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * PipelineProcessor  执行器
 *
 * @author luyanan
 * @since 2021/7/17
 **/
public class PipelineProcessorEngine {


	/**
	 * 任务执行上下文
	 *
	 * @author luyanan
	 * @since 2021/7/17
	 */
	private TransHandlerContext context;

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
	 * 线程池
	 *
	 * @author luyanan
	 * @since 2021/7/17
	 */
	private ExecutorService executorService;

	/**
	 * 处理器
	 *
	 * @author luyanan
	 * @since 2021/7/17
	 */
	private PipelineProcessor pipelineProcessor;

	/**
	 * 流程开始创建
	 *
	 * @param transHandlerContext 上下文
	 * @since 2021/7/17
	 */
	public PipelineProcessorEngine create(TransHandlerContext transHandlerContext) {
		Assert.notNull(transHandlerContext, "上下文不能为空");
		this.context = transHandlerContext;
		return this;
	}


	/**
	 * 添加同步执行的任务
	 *
	 * @param transHandler 待执行的任务
	 * @since 2021/7/17
	 */
	public PipelineProcessorEngine and(TransHandler transHandler) {
		Assert.notNull(transHandler, "执行的任务不能为空");
		syncTransHandlerList.add(transHandler);
		return this;
	}


	/**
	 * 添加需要异步执行的任务
	 *
	 * @param transHandler 任务
	 * @since 2021/7/17
	 */
	public PipelineProcessorEngine or(TransHandler transHandler) {
		Assert.notNull(transHandler, "执行的任务不能为空");
		asyncTransHandlerList.add(transHandler);
		return this;
	}

	/**
	 * 线程池
	 *
	 * @param executorService 线程池
	 * @since 2021/7/17
	 */
	public PipelineProcessorEngine executorService(ExecutorService executorService) {
		this.executorService = executorService;
		return this;
	}


	/**
	 * 任务开始执行
	 *
	 * @since 2021/7/17
	 */
	public void start() {
		this.pipelineProcessor = new PipelineProcessor(this.executorService, syncTransHandlerList, asyncTransHandlerList, context);
		this.pipelineProcessor.start();
	}


	/**
	 * 等所有任务都执行结束
	 *
	 * @return void
	 * @since 2021/7/17
	 */
	public void shutdown() {
		this.pipelineProcessor.shutdown();
	}

	/**
	 * 自我构建
	 *
	 * @return io.github.fallingsoulm.easy.archetype.framework.pipeline.PipelineProcessorEngine
	 * @since 2021/7/17
	 */
	public static PipelineProcessorEngine build() {
		return new PipelineProcessorEngine();
	}

}
