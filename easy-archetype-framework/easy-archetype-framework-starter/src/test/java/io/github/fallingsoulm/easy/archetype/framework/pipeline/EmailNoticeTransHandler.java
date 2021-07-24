package io.github.fallingsoulm.easy.archetype.framework.pipeline;

import io.github.fallingsoulm.easy.archetype.framework.pipeline.callback.TransCallback;

/**
 * 邮件通知
 *
 * @author luyanan
 * @since 2021/7/17
 **/
public class EmailNoticeTransHandler implements TransHandler {
	@Override
	public boolean handler(TransHandlerContext context) {
		System.out.println("发送邮件");
		return true;
	}

	@Override
	public TransCallback callback() {
		return context -> System.out.println("邮件发送成功");
	}
}
