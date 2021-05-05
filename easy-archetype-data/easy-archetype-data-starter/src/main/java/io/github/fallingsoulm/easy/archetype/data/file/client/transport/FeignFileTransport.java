package io.github.fallingsoulm.easy.archetype.data.file.client.transport;

import cn.hutool.core.lang.Assert;
import io.github.fallingsoulm.easy.archetype.data.file.client.FileClientProperties;
import io.github.fallingsoulm.easy.archetype.data.file.client.IFileTransport;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * 利用Feign进行文件传输
 *
 * @author luyanan
 * @since 2021/2/26
 **/
@RequiredArgsConstructor
public class FeignFileTransport implements IFileTransport {

	private final FileClientProperties fileClientProperties;

	private final Decoder decoder;

	private final Encoder encoder;

	private final Contract contract;

	private final Client client;

	private FileFeignApi fileFeignApi;


	@PostConstruct
	public void init() {

		Assert.notBlank(fileClientProperties.getServerServiceName(), "文件服务端的服务名配置不能为空");
		this.fileFeignApi = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract)
				.target(FileFeignApi.class, "http://" + fileClientProperties.getServerServiceName() + "/");
	}

	@SneakyThrows
	@Override
	public String upload(String path, String fileName, InputStream is, boolean rename) {
		MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, MediaType.MULTIPART_FORM_DATA_VALUE, is);
		return this.fileFeignApi.upload(path, multipartFile, rename).getData().toString();
	}

	@Override
	public boolean removeFile(String path) {
		return Boolean.valueOf(this.fileFeignApi.removeFile(path).getData().toString());
	}
}
