package io.github.fallingsoulm.easy.archetype.data.file.filehost;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author luyanan
 * @since 2021/6/6
 **/
@Slf4j
public class DefaultAnnotationIntrospector extends JacksonAnnotationIntrospector {
    /**
     * <自定义注解类名,注解处理类>
     */
    private Map<String, AnnotationHandler> annotationHandlerMap;

//    @Override
//    public Object findDeserializer(Annotated a) {
//
//    }

    @Override
    public Object findSerializer(Annotated am) {
        //如果没有设置任何注解处理类，则直接返回
        if (annotationHandlerMap == null || annotationHandlerMap.isEmpty()) {
            return super.findSerializer(am);
        }
        Iterator<Map.Entry<String, AnnotationHandler>> it =
                annotationHandlerMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, AnnotationHandler> entry = it.next();
            //获取注解
            Annotation an = getAnnotation(am, entry.getKey());
            if (an != null) {
                String fieldName = am.getName();
                //获取属性名
                if (fieldName.startsWith("get")) {
                    String first = String.valueOf(fieldName.charAt(3)).toLowerCase();
                    fieldName = first + fieldName.substring(4);
                }
                if (log.isDebugEnabled()) {
                    log.debug("serialize {} by {}", fieldName, entry.getValue());
                }
                return new AnnotationJsonSerializer(fieldName, an, entry.getValue());
            }
        }
        return super.findSerializer(am);
    }

    private Annotation getAnnotation(Annotated am, String clsName) {
        try {
            Class<? extends Annotation> cls = (Class<? extends Annotation>) Class.forName(clsName);
            return am.getAnnotation(cls);
        } catch (Exception e) {
            log.error("getAnnotation error.", e);
        }
        return null;
    }

    @Override
    public Version version() {
        return super.version();
    }

    public Map<String, AnnotationHandler> getAnnotationHandlerMap() {
        return annotationHandlerMap;
    }

    public void setAnnotationHandlerMap(Map<String, AnnotationHandler> annotationHandlerMap) {
        this.annotationHandlerMap = annotationHandlerMap;
    }

    public void addAnnotationHandler(Class<? extends Annotation> cls, AnnotationHandler annotationHandler) {
        if (this.annotationHandlerMap == null) {
            this.annotationHandlerMap = new HashMap<>();
        }
        this.annotationHandlerMap.put(cls.getName(), annotationHandler);
    }

}
