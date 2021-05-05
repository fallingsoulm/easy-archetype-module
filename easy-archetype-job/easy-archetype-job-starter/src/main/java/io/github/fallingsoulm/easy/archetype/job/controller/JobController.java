package io.github.fallingsoulm.easy.archetype.job.controller;

import io.github.fallingsoulm.easy.archetype.framework.page.PageInfo;
import io.github.fallingsoulm.easy.archetype.framework.page.PageRequestParams;
import io.github.fallingsoulm.easy.archetype.framework.page.RespEntity;
import io.github.fallingsoulm.easy.archetype.job.entity.JobVo;
import io.github.fallingsoulm.easy.archetype.job.exception.JobException;
import io.github.fallingsoulm.easy.archetype.job.invoke.JobInvokeFactory;
import io.github.fallingsoulm.easy.archetype.job.service.JobService;
import io.github.fallingsoulm.easy.archetype.job.utils.CronUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度任务
 *
 * @author luyanan
 * @since 2021/3/24
 */
@Api(tags = "调度任务")
@RestController
@RequestMapping("job")
public class JobController {


	@Autowired
	private JobService jobService;

	@Autowired
	private JobInvokeFactory jobInvokeFactory;

	/**
	 * 分页列表
	 *
	 * @param pageRequestParams
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.RespEntity<io.github.fallingsoulm.easy.archetype.framework.page.PageInfo < io.github.fallingsoulm.easy.archetype.job.entity.JobVo>>
	 * @since 2021/3/24
	 */
	@ApiOperation(value = "分页列表")
	@PostMapping("list")
	public RespEntity<PageInfo<JobVo>> page(@RequestBody PageRequestParams<JobVo> pageRequestParams) {
		PageInfo<JobVo> pageInfo = jobService.page(pageRequestParams);
		return RespEntity.success(pageInfo);
	}


	/**
	 * 根据任务id查询详情
	 *
	 * @param jobId
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.RespEntity<io.github.fallingsoulm.easy.archetype.job.entity.JobVo>
	 * @since 2021/3/24
	 */
	@ApiOperation(value = "根据任务id查询详情")
	@GetMapping(value = "/{jobId}")
	public RespEntity<JobVo> info(@PathVariable("jobId") String jobId) {
		JobVo jobVo = jobService.findById(jobId);
		return RespEntity.success(jobVo);
	}

	/**
	 * 添加任务
	 *
	 * @param jobVo
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.RespEntity
	 * @since 2021/3/24
	 */
	@ApiOperation(value = "添加任务")
	@PostMapping()
	public RespEntity add(@Validated() @RequestBody JobVo jobVo) throws SchedulerException {

		if (!CronUtils.isValid(jobVo.getCronExpression())) {
			throw new JobException("cron表达式不正确");
		}

		jobService.insertJob(jobVo);
		return RespEntity.success();
	}


	/**
	 * 编辑
	 *
	 * @param jobVo
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.RespEntity
	 * @since 2021/3/24
	 */
	@ApiOperation(value = "编辑")
	@PutMapping
	public RespEntity edit(@Validated @RequestBody JobVo jobVo) throws SchedulerException {
		if (!CronUtils.isValid(jobVo.getCronExpression())) {
			throw new JobException("cron表达式不正确");
		}
		jobService.updateJob(jobVo);
		return RespEntity.success();
	}

	/**
	 * 修改状态
	 *
	 * @param jobVo
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.RespEntity
	 * @since 2021/3/24
	 */
	@ApiOperation(value = " 修改状态")
	@PutMapping("changeStatus")
	public RespEntity changeStatus(@RequestBody JobVo jobVo) throws SchedulerException {
		JobVo vo = jobService.findById(jobVo.getJobId());
		vo.setStatus(jobVo.getStatus());
		jobService.changeStatus(vo);
		return RespEntity.success();
	}

	/**
	 * 定时任务立即执行
	 *
	 * @param jobVo
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.RespEntity
	 * @since 2021/3/24
	 */
	@ApiOperation(value = " 定时任务立即执行")
	@PutMapping("run")
	public RespEntity run(@RequestBody JobVo jobVo) throws SchedulerException {
		jobService.run(jobVo);
		return RespEntity.success();
	}


	/**
	 * 删除定时任务
	 *
	 * @param jobIds
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.RespEntity
	 * @since 2021/3/24
	 */
	@ApiOperation(value = "删除定时任务")
	@DeleteMapping("/{jobIds}")
	public RespEntity remove(@PathVariable String[] jobIds) throws SchedulerException {
		jobService.deleteJobByIds(jobIds);
		return RespEntity.success();
	}


	/**
	 * 反射类型
	 *
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.RespEntity<java.util.List < java.lang.String>>
	 * @since 2021/4/6
	 */
	@ApiOperation(value = "反射类型")
	@GetMapping("invoke/types")
	public RespEntity<List<String>> invokeTypes() {
		List<String> types = jobInvokeFactory.types();
		return RespEntity.success(types);

	}
}
