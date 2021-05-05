package io.github.fallingsoulm.easy.archetype.job.exception;

/**
 * 任务异常
 *
 * @author luyanan
 * @since 2021/3/18
 **/
public class JobException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JobException(String message) {
		super(message);
	}
}
