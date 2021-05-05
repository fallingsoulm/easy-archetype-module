package io.github.fallingsoulm.easy.archetype.framework.sensitive;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对象脱敏注解
 *
 * @author luyanan
 * @since 2021/2/8
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerialize.class)
public @interface Sensitive {

	/**
	 * 脱敏数据类型, 非Customer时, 将忽略 refixNoMaskLen 和 suffixNoMaskLen 和 maskStr
	 */
	SensitiveTypeEnum type() default SensitiveTypeEnum.CUSTOMER;

	/**
	 * 脱敏策略
	 * @return java.lang.Class<? extends
	 * io.github.fallingsoulm.easy.archetype.framework.sensitive.SensitiveStrategy>
	 * @since 2021/2/8
	 */
	Class<? extends SensitiveStrategy> sensitiveStrategy() default DefaultSensitiveStrategy.class;

}
