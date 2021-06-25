package io.github.fallingsoulm.easy.archetype.data.file.client.transport;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import io.github.fallingsoulm.easy.archetype.data.file.FileFilterArgs;
import io.github.fallingsoulm.easy.archetype.framework.page.RespEntity;
import io.github.fallingsoulm.easy.archetype.data.file.client.FileClientProperties;
import io.github.fallingsoulm.easy.archetype.data.file.client.IFileTransport;
import io.github.fallingsoulm.easy.archetype.data.file.exception.FileException;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过http 进行文件传输
 *
 * @author luyanan
 * @since 2021/2/26
 **/
@RequiredArgsConstructor
public class HttpFileTransport implements IFileTransport {

	private final FileClientProperties fileClientProperties;

	@Override
	public String upload(String path, String fileName, InputStream is, boolean rename) {
		Assert.notBlank(fileClientProperties.getServerHost(), "http传输的时候的服务端的host配置不能为空");
		File file = FileUtil.writeFromStream(is, FileUtil.getTmpDirPath());
		HashMap<String, Object> paramMap = new HashMap<String, Object>(2);
		//文件上传只需将参数中的键指定（默认file），值设为文件对象即可，对于使用者来说，文件上传与普通表单提交并无区别
		paramMap.put("file", file);
		paramMap.put("rename", rename);
		String result = HttpUtil.post(fileClientProperties.getServerHost() + "/" + path, paramMap);
		return resultParser(result);
	}

	/**
	 * 结果解析
	 *
	 * @param result 结果
	 * @return java.lang.String
	 * @since 2021/2/26
	 */
	private String resultParser(String result) {
		Assert.notBlank(result, "文件上传异常");
		RespEntity<String> respEntity = JSON.parseObject(result, RespEntity.class);
		if (respEntity.getStatus() != RespEntity.SUCCESS_STATUS) {
			throw new FileException("文件上传失败:" + respEntity.getMsg());
		}
		return respEntity.getData();
	}

	@Override
	public boolean removeFile(String path) {
		Assert.notBlank(fileClientProperties.getServerHost(), "http传输的时候的服务端的host配置不能为空");
		String url = fileClientProperties.getServerHost() + "/file/removeFile/" + path;
		String result = HttpUtil.get(url);
		String resultParser = resultParser(result);
		return Boolean.valueOf(resultParser);
	}

	@Override
	public List<String> loopFiles(FileFilterArgs fileFilterArgs) {
		String url = fileClientProperties.getServerHost() + "/file/loopFiles";
		Map<String, Object> params = new HashMap<>();
		params.put("fileFilterArgs", fileFilterArgs);
		String post = HttpUtil.post(url, JSON.toJSONString(fileFilterArgs));
		return JSON.parseArray(post, String.class);
	}
}
