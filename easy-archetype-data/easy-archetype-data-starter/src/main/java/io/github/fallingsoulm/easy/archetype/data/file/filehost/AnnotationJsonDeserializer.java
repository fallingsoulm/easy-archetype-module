package io.github.fallingsoulm.easy.archetype.data.file.filehost;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * 注解Json反序列化
 *
 * @author luyanan
 * @since 2021/6/6
 **/
public class AnnotationJsonDeserializer extends JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Object currentValue = p.getCurrentValue();

        return currentValue;
    }
}
