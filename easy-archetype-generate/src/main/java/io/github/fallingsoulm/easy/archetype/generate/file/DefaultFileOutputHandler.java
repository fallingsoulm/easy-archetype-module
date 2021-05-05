package io.github.fallingsoulm.easy.archetype.generate.file;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import io.github.fallingsoulm.easy.archetype.generate.config.GlobalConfig;

import java.util.List;

/**
 * 默认文件输出处理器
 *
 * @author luyanan
 * @since 2021/2/1
 **/
public class DefaultFileOutputHandler implements FileOutputHandler {

	@Override
	public void handler(List<OutputFile> outputFiles, GlobalConfig globalConfig) {
		if (CollectionUtil.isEmpty(outputFiles)) {
			return;
		}
		for (OutputFile outputFile : outputFiles) {
			String file = globalConfig.getOutPutFile() + "/" + outputFile.getFilePath() + "/"
					+ outputFile.getFileName();
			FileUtil.writeString(outputFile.getContent(), file, "utf-8");
			System.out.println("模板生成:" + file);
		}
	}

}
