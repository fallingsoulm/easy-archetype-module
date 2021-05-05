package io.github.fallingsoulm.easy.archetype.framework.xss.core;

import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.framework.xss.utils.XssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

/**
 * 表单xss处理
 *
 * @author luyanan
 * @since 2021/2/8
 **/
@ControllerAdvice
public class FormXssClean {

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {

	}

	@Slf4j
	public static class StringPropertiesEditor extends PropertyEditorSupport {

		@Override
		public String getAsText() {
			Object value = getValue();
			return value != null ? value.toString() : StrUtil.EMPTY;
		}

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (text == null) {
				setValue(null);
			}
			else if (XssHolder.isEnabled()) {
				String value = XssUtil.clean(text);
				setValue(value);
				log.trace("Request parameter value:{} cleaned up by xss, current value is:{}.", text, value);
			}
			else {
				setValue(text);
			}
		}

	}

}
