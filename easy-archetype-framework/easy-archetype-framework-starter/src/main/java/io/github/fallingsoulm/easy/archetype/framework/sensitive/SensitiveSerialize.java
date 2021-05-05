package io.github.fallingsoulm.easy.archetype.framework.sensitive;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

/**
 * 脱敏序列化
 *
 * @author luyanan
 * @since 2021/2/8
 **/
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveSerialize extends JsonSerializer<String> implements ContextualSerializer {

	/**
	 * 脱敏类型
	 *
	 * @since 2021/2/8
	 */
	private SensitiveTypeEnum type;

	/**
	 * 脱敏策略
	 *
	 * @since 2021/2/8
	 */
	Class<? extends SensitiveStrategy> sensitiveStrategy;

	@Override
	public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {

		SensitiveStrategy strategy = SensitiveStrategyFactory.get(sensitiveStrategy);
		String conver = strategy.conver(type, s);
		jsonGenerator.writeString(conver);
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty)
			throws JsonMappingException {
		if (beanProperty != null) {
			if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
				Sensitive sensitive = beanProperty.getAnnotation(Sensitive.class);
				if (sensitive == null) {
					sensitive = beanProperty.getContextAnnotation(Sensitive.class);
				}
				if (sensitive != null) {
					return new SensitiveSerialize(sensitive.type(), sensitive.sensitiveStrategy());
				}
			}
			return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
		}
		return serializerProvider.findNullValueSerializer(null);
	}

}
