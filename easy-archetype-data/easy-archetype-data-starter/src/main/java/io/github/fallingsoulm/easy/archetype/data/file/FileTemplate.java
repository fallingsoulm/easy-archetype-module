package io.github.fallingsoulm.easy.archetype.data.file;

import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.framework.constant.Constants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 文件上传模板类
 *
 * @author luyanan
 * @since 2021/2/22
 **/
@RequiredArgsConstructor
public class FileTemplate {
	/**
	 * 多文件分隔符
	 *
	 * @since 2021/2/22
	 */
	public static final String MULTIPLE_FILES_SEPARATE = ",";

	private final IFileService fileService;

	private final FileProperties fileProperties;

	/**
	 * 将文件的相对路径补充为全路径(支持多个文件,多个文件路径用,隔开)
	 *
	 * @param path 文件的相对路径
	 * @return java.lang.String
	 * @since 2021/2/22
	 */
	public String addHost(String path) {
		if (StrUtil.isBlank(path)) {
			return path;
		}
		return Arrays.stream(path.split(MULTIPLE_FILES_SEPARATE)).filter(a -> StrUtil.isNotBlank(a))
				.map(p -> {
					if (p.startsWith(Constants.HTTP) || p.startsWith(Constants.HTTPS)) {
						return p;
					}
					return (fileProperties.getFileHost() + "/" + p).replace("/+", "/");
				}).collect(Collectors.joining(MULTIPLE_FILES_SEPARATE));
	}


	/**
	 * 移除文件的host为相对路径(支持多个文件,多个文件路径用,隔开)
	 *
	 * @param path 文件全路径
	 * @return java.lang.String
	 * @since 2021/2/22
	 */
	public String removeHost(String path) {
		if (StrUtil.isBlank(path)) {
			return path;
		}
		return Arrays
				.stream(path.split(MULTIPLE_FILES_SEPARATE))
				.map(a -> a.replace(fileProperties.getFileHost(), path))
				.collect(Collectors.joining(MULTIPLE_FILES_SEPARATE));

	}


	/**
	 * 文件上传
	 *
	 * @param path     文件路径
	 * @param fileName 文件名
	 * @param is       文件流
	 * @param rename   是否重命名
	 * @return java.lang.String 文件的路径(相对路径)
	 * @since 2021/2/22
	 */
	public String upload(String path, String fileName, InputStream is, boolean rename) {
		return fileService.upload(path, fileName, is, rename);
	}

	/**
	 * 文件上传(自动重命名)
	 *
	 * @param path     文件路径
	 * @param fileName 文件名
	 * @param is       文件流
	 * @return java.lang.String 文件的路径(相对路径)
	 * @since 2021/2/22
	 */
	public String upload(String path, String fileName, InputStream is) {
		return fileService.upload(path, fileName, is, true);
	}

	/**
	 * 文件上传
	 *
	 * @param file 文件
	 * @param path 文件上传的路径
	 * @return java.lang.String 文件的路径
	 * @since 2021/2/22
	 */
	@SneakyThrows
	public String upload(MultipartFile file, String path) {
		return fileService.upload(path, file.getOriginalFilename(), file.getInputStream(), true);
	}


	/**
	 * 根据文件的相对路径删除文件
	 *
	 * @param path 文件的相对路径
	 * @return boolean
	 * @since 2021/2/22
	 */
	public boolean removeFile(String path) {
		return fileService.removeFile(path);
	}
}
