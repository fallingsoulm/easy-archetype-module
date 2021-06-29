package io.github.fallingsoulm.easy.archetype.job;


import io.github.fallingsoulm.easy.archetype.job.annotation.EnableJob;
import io.github.fallingsoulm.easy.archetype.job.annotation.Job;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 定时任务bean注册
 *
 * @author luyanan
 * @since 2021/6/25
 **/
public class JobRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
	private ResourceLoader resourceLoader;
	private Environment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}


	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		ClassPathScanningCandidateComponentProvider scanner = getScanner();
		scanner.setResourceLoader(this.resourceLoader);
		AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(
				Job.class);
		scanner.addIncludeFilter(annotationTypeFilter);
		Set<String> basePackages = getBasePackages(metadata);

		for (String basePackage : basePackages) {
			Set<BeanDefinition> candidateComponents = scanner
					.findCandidateComponents(basePackage);
			for (BeanDefinition candidateComponent : candidateComponents) {
				AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
				AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
				Map<String, Object> jobAttr = annotationMetadata
						.getAnnotationAttributes(
								Job.class.getCanonicalName());
				// 分组
				String group = (String) jobAttr.get("group");
				//任务名称
				String name = (String) jobAttr.get("name");
				// cron 表达式
				String cron = (String) jobAttr.get("cron");
				//是否允许并发
				boolean concurrent = (boolean) jobAttr.get("concurrent");
				// 是否允许自动注册
				boolean autoRegister = (boolean) jobAttr.get("autoRegister");
				if (!autoRegister) {
					return;
				}
				String className = annotationMetadata.getClassName();
				BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(className);
				definition.addPropertyValue("group", group);
				definition.addPropertyValue("name", name);
				definition.addPropertyValue("cron", cron);
				definition.addPropertyValue("concurrent", concurrent);
				definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
				AbstractBeanDefinition abstractBeanDefinition = definition.getBeanDefinition();
				abstractBeanDefinition.setPrimary(true);
				BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className,
						new String[]{});
				BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);


			}

		}

	}

	/**
	 * 获取扫描的包路径
	 *
	 * @param metadata
	 * @return java.util.Set<java.lang.String>
	 * @since 2021/6/25
	 */
	private Set<String> getBasePackages(AnnotationMetadata metadata) {
		Map<String, Object> attributes = metadata
				.getAnnotationAttributes(EnableJob.class.getCanonicalName());

		Set<String> basePackages = new HashSet<>();
		for (String pkg : (String[]) attributes.get("value")) {
			if (StringUtils.hasText(pkg)) {
				basePackages.add(pkg);
			}
		}
		for (String pkg : (String[]) attributes.get("basePackages")) {
			if (StringUtils.hasText(pkg)) {
				basePackages.add(pkg);
			}
		}
		for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
			basePackages.add(ClassUtils.getPackageName(clazz));
		}

		if (basePackages.isEmpty()) {
			basePackages.add(
					ClassUtils.getPackageName(metadata.getClassName()));
		}
		return basePackages;

	}


	protected ClassPathScanningCandidateComponentProvider getScanner() {
		return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
			@Override
			protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
				boolean isCandidate = false;
				if (beanDefinition.getMetadata().isIndependent() && !beanDefinition.getMetadata().isAnnotation()) {
					isCandidate = true;
				}

				return isCandidate;
			}
		};
	}
}
