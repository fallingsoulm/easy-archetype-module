package io.github.fallingsoulm.easy.archetype.generate.template;

import io.github.fallingsoulm.easy.archetype.generate.core.FactoryRegistryHandler;
import io.github.fallingsoulm.easy.archetype.generate.exception.GeneratorException;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板组注册
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public class TemplateGroupRegister {

	private Map<Class<? extends ITemplateGroup>, ITemplateGroup> groupMap = new HashMap<>();

	/**
	 * 注册
	 * @param factoryRegistryHandler 注册类
	 * @return void
	 * @since 2021/2/2
	 */
	public void registry(
			FactoryRegistryHandler<Map<Class<? extends ITemplateGroup>, ITemplateGroup>> factoryRegistryHandler) {

		if (null != factoryRegistryHandler) {
			factoryRegistryHandler.registry(groupMap);
		}
	}

	public ITemplateGroup getTemplateGroup(Class<? extends ITemplateGroup> templateGroupClass) {
		ITemplateGroup iTemplateGroup = this.groupMap.get(templateGroupClass);
		if (null == iTemplateGroup) {
			throw new GeneratorException("未注册的:[" + templateGroupClass.getName() + "]模板组");
		}
		return iTemplateGroup;
	}

	public Map<Class<? extends ITemplateGroup>, ITemplateGroup> getGroupMap() {
		return groupMap;
	}

	public static TemplateGroupRegister builder() {
		return new TemplateGroupRegister();
	}

}
