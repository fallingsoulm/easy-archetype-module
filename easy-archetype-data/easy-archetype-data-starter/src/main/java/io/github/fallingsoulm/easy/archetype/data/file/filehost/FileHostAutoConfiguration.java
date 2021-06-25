package io.github.fallingsoulm.easy.archetype.data.file.filehost;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyanan
 * @since 2021/6/6
 **/
@Configuration
public class FileHostAutoConfiguration {


	@Bean("fileAnnotationHandler")
	public AnnotationHandler fileAnnotationHandler() {
		return new FileAnnotationHandler();
	}

	@Bean
	public DefaultAnnotationIntrospector defaultAnnotationIntrospector(ObjectMapper objectMapper, ObjectProvider<AnnotationHandler> annotationHandlers) {
		DefaultAnnotationIntrospector introspector = new DefaultAnnotationIntrospector();
		objectMapper.setAnnotationIntrospector(introspector);
		annotationHandlers.stream().forEach(annotationHandler -> {
			introspector.addAnnotationHandler(annotationHandler.anotationClass(), annotationHandler);
		});
		return introspector;
	}

//    @Bean
//    public FileAnnotationHandler dictAnnotationHandler(@Autowired DefaultAnnotationIntrospector introspector, ObjectProvider<AnnotationHandler> annotationHandlers) {
//        FileAnnotationHandler handler = new FileAnnotationHandler();
//
//        introspector.addAnnotationHandler(AddHost.class, handler);
//        return handler;
//    }

	//    @Primary
//    @Bean
//    public ObjectMapper objectMapper(@Autowired DefaultAnnotationIntrospector introspector, ObjectMapper objectMapper) {
////        //注解扩展json序列化
////        if (defaultAnnotationIntrospector != null) {
////            _deserializationConfig = _deserializationConfig.withAppendedAnnotationIntrospector(defaultAnnotationIntrospector);
////            _serializationConfig = _serializationConfig.withAppendedAnnotationIntrospector(defaultAnnotationIntrospector);
////        }
//        objectMapper.setAnnotationIntrospector(introspector);
////        CustomObjectMapper objectMapper = new CustomObjectMapper();
////        objectMapper.setDefaultAnnotationIntrospector(introspector);
////        objectMapper.init();
//        return objectMapper;
//    }
//    @Bean
//    @Primary
////    @ConditionalOnMissingBean
//    ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder, DefaultAnnotationIntrospector introspector) {
//        return builder.createXmlMapper(false).build().setAnnotationIntrospector(introspector);
//    }

//    @Bean
//    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(@Autowired ObjectMapper objectMapper) {
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setObjectMapper(objectMapper);
//        return converter;
//    }

}
