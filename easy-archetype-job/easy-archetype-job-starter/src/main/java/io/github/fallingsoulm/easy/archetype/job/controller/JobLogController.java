package io.github.fallingsoulm.easy.archetype.job.controller;

import io.github.fallingsoulm.easy.archetype.framework.page.PageInfo;
import io.github.fallingsoulm.easy.archetype.framework.page.PageRequestParams;
import io.github.fallingsoulm.easy.archetype.framework.page.RespEntity;
import io.github.fallingsoulm.easy.archetype.framework.spring.annotation.RestGetMapping;
import io.github.fallingsoulm.easy.archetype.job.entity.JobLogVo;
import io.github.fallingsoulm.easy.archetype.job.service.JobLogStorageStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 任务调度日志
 *
 * @author luyanan
 * @since 2021/3/24
 */
@Api(tags = "任务调度日志")
@RestController
@RequestMapping("job/log")
public class JobLogController {

	@Autowired
	private JobLogStorageStrategy jobLogStorageStrategy;


	/**
	 * 分页查询
	 *
	 * @param pageRequestParams
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.RespEntity
	 * @since 2021/3/24
	 */
	@ApiOperation(value = " 分页查询")
	@PostMapping("list")
	public RespEntity page(@RequestBody PageRequestParams<JobLogVo> pageRequestParams) {
		PageInfo<JobLogVo> pageInfo = jobLogStorageStrategy.findByPage(pageRequestParams);
		return RespEntity.success(pageInfo);
	}


	/**
	 * 根据日志id查询日志
	 *
	 * @param jobLogId
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.RespEntity<io.github.fallingsoulm.easy.archetype.job.entity.JobLogVo>
	 * @since 2021/3/24
	 */
	@ApiOperation(value = " 根据日志id查询日志")
	@RestGetMapping("/{jobLogId}")
	public RespEntity<JobLogVo> getInfo(@PathVariable("jobLogId") String jobLogId) {
		JobLogVo logVo = jobLogStorageStrategy.findById(jobLogId);
		return RespEntity.success(logVo);
	}


	/**
	 * 根据日志集合id删除
	 *
	 * @param jobLogIds 日志id集合
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.RespEntity
	 * @since 2021/3/24
	 */
	@ApiOperation(value = "根据日志集合id删除")
	@DeleteMapping("/{jobLogIds}")
	public RespEntity remove(@PathVariable String[] jobLogIds) {
		jobLogStorageStrategy.deleteByIds(jobLogIds);
		return RespEntity.success();
	}


	/**
	 * 清空日志
	 *
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.RespEntity
	 * @since 2021/3/24
	 */
	@ApiOperation(value = "")
	@DeleteMapping("/clean")
	public RespEntity clean() {
		jobLogStorageStrategy.clearLog();
		return RespEntity.success();
	}

}
