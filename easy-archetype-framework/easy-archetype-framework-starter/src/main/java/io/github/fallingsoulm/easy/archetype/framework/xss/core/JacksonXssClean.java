package io.github.fallingsoulm.easy.archetype.framework.xss.core;

import io.github.fallingsoulm.easy.archetype.framework.xss.utils.XssUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * jackson xss处理
 *
 * @author luyanan
 * @since 2021/2/8
 **/
@Slf4j
public class JacksonXssClean extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		// XSS filter
		String text = jsonParser.getValueAsString();
		if (text == null) {
			return null;
		}
		else if (XssHolder.isEnabled()) {
			String value = XssUtil.clean(text);
			log.trace("Json property value:{} cleaned up by xss, current value is:{}.", text, value);
			return value;
		}
		else {
			return text;
		}
	}

}
