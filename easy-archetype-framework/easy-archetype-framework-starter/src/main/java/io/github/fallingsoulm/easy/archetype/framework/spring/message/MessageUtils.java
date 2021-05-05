package io.github.fallingsoulm.easy.archetype.framework.spring.message;

import io.github.fallingsoulm.easy.archetype.framework.spring.SpringContextHolder;
import lombok.experimental.UtilityClass;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 消息工具类
 *
 * @author luyanan
 * @since 2021/1/24
 **/
@UtilityClass
public class MessageUtils {

	/**
	 * 根据编码获取异常信息
	 * @param code 编译
	 * @param args 参数
	 * @return java.lang.String
	 * @since 2021/1/24
	 */
	public String getMessage(String code, Object... args) {
		// 先从消息文件中获取
		MessageSource messageSource = SpringContextHolder.getBean(MessageSource.class);
		if (null != messageSource) {
			return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
		}
		return code;
	}

}
