package io.github.fallingsoulm.easy.archetype.data.file.client;

import io.github.fallingsoulm.easy.archetype.data.file.FileProperties;
import io.github.fallingsoulm.easy.archetype.data.file.client.transport.FeignFileTransport;
import io.github.fallingsoulm.easy.archetype.data.file.client.transport.HttpFileTransport;
import feign.Client;
import feign.Contract;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件客户端自动配置
 *
 * @author luyanan
 * @since 2021/2/26
 **/
@Configuration
@EnableConfigurationProperties({FileProperties.class, FileClientProperties.class})
public class FileClientAutoConfiguration {


	@ConditionalOnProperty(prefix = FileClientProperties.PREFIX, name = "transport", havingValue = "http", matchIfMissing = false)
	@Bean
	public IFileTransport HttpFileTransport(FileClientProperties fileClientProperties) {
		return new HttpFileTransport(fileClientProperties);
	}


	@ConditionalOnBean({Decoder.class, Encoder.class, Client.class, Contract.class})
	@ConditionalOnProperty(prefix = FileClientProperties.PREFIX, name = "transport", havingValue = "feign", matchIfMissing = false)
	@Bean
	public IFileTransport HttpFileTransport(FileClientProperties fileClientProperties, Decoder decoder, Encoder encoder,
											Client client, Contract contract) {
		return new FeignFileTransport(fileClientProperties, decoder, encoder, contract, client);
	}
}
