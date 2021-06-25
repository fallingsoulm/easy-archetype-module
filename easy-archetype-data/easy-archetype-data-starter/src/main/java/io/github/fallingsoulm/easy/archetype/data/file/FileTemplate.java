package io.github.fallingsoulm.easy.archetype.data.file;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.framework.constant.Constants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
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
	 * 给路径添加前缀
	 *
	 * @param paths
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 * @since 2021/5/30
	 */
	public Map<String, String> addHost(List<String> paths) {
		return Optional
				.ofNullable(paths)
				.filter(a -> CollectionUtil.isNotEmpty(a)).map(ps -> {
					if (CollectionUtil.isEmpty(ps)) {
						return new HashMap<>();
					}
					Map<String, String> pathMap = new HashMap<>(paths.size());
					ps.forEach(p -> {
						String absolutePath = null;
						if (p.startsWith(Constants.HTTP) || p.startsWith(Constants.HTTPS)) {
							absolutePath = p;
						} else {
							absolutePath = (fileProperties.getFileHost() + "/" + p).replace("/+", "/");
						}
						pathMap.put(p, absolutePath);
					});
					return pathMap;
				}).orElse(new HashMap(0));
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

	public Map<String, String> removeHost(List<String> paths) {
		return Optional
				.ofNullable(paths)
				.filter(a -> CollectionUtil.isNotEmpty(a)).map(ps -> {
					if (CollectionUtil.isEmpty(ps)) {
						return new HashMap<>();
					}
					Map<String, String> pathMap = new HashMap<>(paths.size());
					ps.stream().filter(a -> StrUtil.isNotBlank(a)).forEach(p -> {
						String relativePath = p.replace(fileProperties.getFileHost(), "");
						pathMap.put(p, relativePath);
					});
					return pathMap;
				}).orElse(new HashMap(0));
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


	/**
	 * 列出该目录下的所有文件
	 *
	 * @param fileFilterArgs 文件过滤参数
	 * @return java.util.List<java.lang.String>
	 * @since 2021/5/7
	 */
	public List<String> loopFiles(FileFilterArgs fileFilterArgs) {

		return fileService.loopFiles(fileFilterArgs);
	}
}
