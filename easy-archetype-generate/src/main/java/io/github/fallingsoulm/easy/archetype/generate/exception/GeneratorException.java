package io.github.fallingsoulm.easy.archetype.generate.exception;

/**
 * 代码生成异常类
 *
 * @author luyanan
 * @since 2021/1/31
 **/
public class GeneratorException extends RuntimeException {

	/**
	 * 异常信息
	 *
	 * @since 2021/1/31
	 */
	private String message;

	public GeneratorException(String message) {
		super(message);
		this.message = message;
	}

	public GeneratorException() {
		super();
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
