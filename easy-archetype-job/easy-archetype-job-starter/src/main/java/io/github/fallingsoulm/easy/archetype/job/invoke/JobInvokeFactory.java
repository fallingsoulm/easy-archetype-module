package io.github.fallingsoulm.easy.archetype.job.invoke;

import cn.hutool.core.lang.Assert;
import io.github.fallingsoulm.easy.archetype.job.exception.JobException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务调用工厂
 *
 * @author luyanan
 * @since 2021/3/18
 **/
public class JobInvokeFactory {

	@Autowired
	private ObjectProvider<JobInvokeStrategy> jobInvokeStrategies;


	/**
	 * 所有的类型
	 *
	 * @return java.util.List<java.lang.String>
	 * @since 2021/4/6
	 */
	public List<String> types() {
		return this.jobInvokeStrategies.stream().map(a -> a.type()).collect(Collectors.toList());
	}

	public JobInvokeStrategy jobInvokeStrategy(String type) {
		Assert.notBlank(type, "任务调用类型不能为空");
		return jobInvokeStrategies
				.stream()
				.filter(a -> a.type().equals(type))
				.findFirst().orElseThrow(() -> new JobException("没有找见[" + type + "]的执行器"));
	}

}
