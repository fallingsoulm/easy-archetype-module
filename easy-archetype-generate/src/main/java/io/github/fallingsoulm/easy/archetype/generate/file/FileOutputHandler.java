package io.github.fallingsoulm.easy.archetype.generate.file;

import io.github.fallingsoulm.easy.archetype.generate.config.GlobalConfig;

import java.util.List;

/**
 * 文件输出处理
 *
 * @author luyanan
 * @since 2021/2/1
 **/
@FunctionalInterface
public interface FileOutputHandler {

	/**
	 * 文件处理
	 * @param outputFiles 输出文件
	 * @param globalConfig 全部配置
	 * @return void
	 * @since 2021/2/1
	 */
	void handler(List<OutputFile> outputFiles, GlobalConfig globalConfig);

}
