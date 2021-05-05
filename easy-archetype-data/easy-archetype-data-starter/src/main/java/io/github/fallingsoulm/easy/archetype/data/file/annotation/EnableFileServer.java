package io.github.fallingsoulm.easy.archetype.data.file.annotation;

import io.github.fallingsoulm.easy.archetype.data.file.FileServerSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启文件上传服务
 *
 * @author luyanan
 * @since 2021/2/24
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FileServerSelector.class)
public @interface EnableFileServer {


	/**
	 * 文件模式
	 *
	 * @since 2021/2/24
	 */

	FileMode mode();

	public enum FileMode {

		/**
		 * 服务端, 负责文件存储
		 *
		 * @since 2021/2/24
		 */

		SERVER,
		/**
		 * 负责将文件转发给客户端
		 *
		 * @since 2021/2/24
		 */

		CLIENT;
	}

}
