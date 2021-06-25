package io.github.fallingsoulm.easy.archetype.data.file;

import io.github.fallingsoulm.easy.archetype.data.file.annotation.EnableFileServer;
import io.github.fallingsoulm.easy.archetype.data.file.client.FileClientAutoConfiguration;
import io.github.fallingsoulm.easy.archetype.data.file.filehost.FileHostAutoConfiguration;
import io.github.fallingsoulm.easy.archetype.data.file.server.FileServiceAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件服务配置文件动态导入
 *
 * @author luyanan
 * @since 2021/2/24
 **/
@Slf4j
public class FileServerSelector implements ImportSelector {


	@Override
	public String[] selectImports(AnnotationMetadata annotationMetadata) {
		Map<String, Object> attrs = annotationMetadata
				.getAnnotationAttributes(EnableFileServer.class.getName());
		EnableFileServer.FileMode fileMode = (EnableFileServer.FileMode) attrs.get("mode");
		List<String> clazzs = new ArrayList<>();
		if (fileMode.equals(EnableFileServer.FileMode.SERVER)) {
			log.debug("开启文件服务服务端");
			clazzs.add(FileServiceAutoConfiguration.class.getName());
		} else {
			log.debug("开启文件客户端服务");
			clazzs.add(FileClientAutoConfiguration.class.getName());
		}
		boolean fileHost = (boolean) attrs.get("fileHost");
		if (fileHost) {
			clazzs.add(FileHostAutoConfiguration.class.getName());
		}
		return clazzs.toArray(new String[clazzs.size()]);

	}
}
