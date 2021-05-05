package io.github.fallingsoulm.easy.archetype.security.oauth.server.exception;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * CustomerOauthException 的序列化实现
 *
 * @author luyanan
 * @since 2021/2/19
 **/
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {
	public CustomOauthExceptionSerializer() {
		super(CustomOauthException.class);
	}

	@Override
	public void serialize(CustomOauthException value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		Map<String, Object> res = new HashMap<>();
		res.put("status", value.getHttpErrorCode());
		res.put("msg", value.getMessage());
		if (value.getAdditionalInformation() != null) {
			value.getAdditionalInformation().entrySet().forEach(entry -> {
				res.put(entry.getKey(), entry.getValue());
			});
		}
		jsonGenerator.writeObject(res);
	}
}
