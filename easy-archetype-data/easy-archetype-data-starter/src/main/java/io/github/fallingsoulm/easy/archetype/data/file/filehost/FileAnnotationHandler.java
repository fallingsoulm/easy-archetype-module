package io.github.fallingsoulm.easy.archetype.data.file.filehost;


import io.github.fallingsoulm.easy.archetype.data.file.FileTemplate;
import io.github.fallingsoulm.easy.archetype.data.file.annotation.FileHostIgnore;
import io.github.fallingsoulm.easy.archetype.data.file.annotation.FileHostProperty;
import io.github.fallingsoulm.easy.archetype.framework.spring.SpringContextHolder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;

/**
 * @author luyanan
 * @since 2021/6/6
 **/
public class FileAnnotationHandler implements AnnotationHandler {

	@Autowired
	private FileTemplate fileTemplate;


	@Override
	public Class<? extends Annotation> anotationClass() {
		return FileHostProperty.class;
	}

	@SneakyThrows
	@Override
	public HandleResult handle(Annotation annotation, String name, Object value) {


		Object newValue = value;

		HandlerMethod handlerMethod = SpringContextHolder.getHandlerMethod();
		FileHostIgnore fileHostIgnore = handlerMethod.getBean().getClass().getAnnotation(FileHostIgnore.class);
		if (null == fileHostIgnore) {
			fileHostIgnore = handlerMethod.getMethod().getAnnotation(FileHostIgnore.class);
		}


		if (null != handlerMethod
				&& (null == fileHostIgnore
				|| !fileHostIgnore.addHost())
				&& value instanceof String) {
			newValue = fileTemplate.addHost((String) value);
		}

		return new HandleResult(name, newValue, HandleResult.Type.REPLACE);
	}
}
