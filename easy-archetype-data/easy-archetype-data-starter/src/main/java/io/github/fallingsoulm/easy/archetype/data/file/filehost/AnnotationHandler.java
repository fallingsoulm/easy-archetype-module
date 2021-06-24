package io.github.fallingsoulm.easy.archetype.data.file.filehost;

import java.lang.annotation.Annotation;

/**
 * @author luyanan
 * @since 2021/6/6
 **/
public interface AnnotationHandler {

    Class<? extends Annotation> anotationClass();

    /**
     * 注解处理
     *
     * @param annotation 注解
     * @param name       属性名
     * @param value      属性值
     * @return 处理结果
     */
    HandleResult handle(Annotation annotation, String name, Object value);

}
