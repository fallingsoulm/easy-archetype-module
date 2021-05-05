package io.github.fallingsoulm.easy.archetype.job.task;

import cn.hutool.core.util.StrUtil;

/**
 * 测试任务
 *
 * @author luyanan
 * @since 2021/4/6
 **/
public class DemoTask {
	public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
		System.out.println(StrUtil.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
	}

	public void ryParams(String params) {
		System.out.println("执行有参方法：" + params);
	}

	public void ryNoParams() {
		System.out.println("执行无参方法");
	}
}
