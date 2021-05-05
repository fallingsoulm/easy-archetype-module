package io.github.fallingsoulm.easy.archetype.framework.extension;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.framework.extension.annotation.Activate;
import io.github.fallingsoulm.easy.archetype.framework.extension.annotation.Adaptive;
import io.github.fallingsoulm.easy.archetype.framework.extension.annotation.SPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * SPI扩展类
 *
 * @author luyanan
 * @since 2021/3/4
 **/
@Slf4j
public class ExtensionLoader<T> {


	/**
	 * 扩展类的接口
	 *
	 * @author luyanan
	 * @since 2021/3/4
	 */
	private Class<T> type;


	/**
	 * 依赖注入
	 *
	 * @author luyanan
	 * @since 2021/3/4
	 */
	private InjectExtension<T> injectExtension;


	/**
	 * 扩展类的路径
	 *
	 * @author luyanan
	 * @since 2021/3/4
	 */
	private Set<String> extensionDir = new HashSet<>();


	/**
	 * 扩展类父路径
	 *
	 * @author luyanan
	 * @since 2021/3/4
	 */
	private final String parentExtensionDir = "META-INF/";


	/**
	 * 接口与实例的缓存
	 *
	 * @since 2021/3/4
	 */

	private static final ConcurrentHashMap<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();


	/**
	 * 保存扫描出来的配置文件
	 *
	 * @since 2021/3/4
	 */

	private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();
	/**
	 * 扩展名与扩展对象实例缓存
	 *
	 * @since 2021/3/4
	 */

	private final ConcurrentHashMap<String, Holder<T>> cachedInstances = new ConcurrentHashMap<>();


	/**
	 * SPI value 拆分正则
	 *
	 * @since 2021/3/4
	 */

	private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");


	/**
	 * 默认的扩展名(SPI注解的value值)
	 *
	 * @since 2021/3/4
	 */

	private String cachedDefaultName;


	/**
	 * 自适应扩展点所在类
	 *
	 * @since 2021/3/4
	 */

	private volatile Class<?> cachedAdaptiveClass = null;

	/**
	 * True字符串,避免出现魔法值
	 *
	 * @since 2021/3/4
	 */

	private static String TRUE = "true";
	/**
	 * 缓存自动激活扩展点
	 *
	 * @since 2021/3/4
	 */
	private final Map<String, Object> cachedActivates = new ConcurrentHashMap<>();


	/**
	 * 扩展类与扩展名之间的缓存
	 *
	 * @since 2021/3/4
	 */

	private final ConcurrentHashMap<Class<?>, String> cachedNames = new ConcurrentHashMap<>();


	/**
	 * 自适应扩展点
	 *
	 * @since 2021/3/4
	 */

	private final Holder<T> cachedAdaptiveInstance = new Holder<>();


	/**
	 * 类加载器
	 *
	 * @since 2021/3/4
	 */

	private ClassLoader classLoader;


	/**
	 * 判断扩展类是否添加了@SPI注解
	 *
	 * @param type 扩展类
	 * @return boolean
	 * @since 2021/3/4
	 */
	private static <T> boolean withExtensionAnnotation(Class<T> type) {
		return type.isAnnotationPresent(SPI.class);
	}


	/**
	 * 根据名称创建或者获取Holder
	 *
	 * @param name 扩展名
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.extension.Holder<T>
	 * @since 2021/3/4
	 */
	private Holder<T> getOrCreateHolder(String name) {
		Holder<T> holder = cachedInstances.get(name);
		if (null == holder) {
			cachedInstances.putIfAbsent(name, new Holder<>());
			holder = cachedInstances.get(name);
		}
		return holder;
	}


	/**
	 * 根据名称创建扩展
	 *
	 * @param name 扩展名
	 * @return T 扩展实例
	 * @since 2021/3/4
	 */
	private T createExtension(String name) {
		Class<?> clazz = getExtensionClasses(extensionDir, type).get(name);
		if (null == clazz) {
			throw new NullPointerException("could not find type extension ");
		}
		T instance = null;
		try {
			instance = (T) EXTENSION_INSTANCES.get(clazz);
			if (instance == null) {
				EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
				instance = (T) EXTENSION_INSTANCES.get(clazz);
			}
			injectExtension(instance);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}

	/**
	 * 实现依赖注入,如果被加载的实例中,有成员属性添加了@Inject 注解的话, 也会进行依赖注入
	 *
	 * @param instance 对象
	 * @return void
	 * @since 2021/3/5
	 */
	private T injectExtension(T instance) {
		return injectExtension.injectExtension(instance, extensionDir, this.classLoader);
	}


	/**
	 * 扫描指定目录下的配置信息,然后保存到一个HashMap中
	 * key: 配置文件的key
	 * value: 对象配置类的实例
	 *
	 * @param extensionDir 扩展类路径
	 * @param type         扩展点接口类
	 * @return java.util.Map<java.lang.String, java.lang.Class < ?>> 扩展类和实例类的对应集合
	 * @since 2021/3/4
	 */
	private Map<String, Class<?>> getExtensionClasses(Set<String> extensionDir, Class<T> type) {

		Map<String, Class<?>> classes = cachedClasses.get();
		if (null == classes) {
			synchronized (cachedClasses) {
				if (null == classes) {
					// 加载配置文件
					classes = loadExtensionClasses(extensionDir, type);
					cachedClasses.set(classes);
				}
			}
		}
		return classes;
	}

	/**
	 * 加载配置文件
	 *
	 * @param extensionDir 扩展类的目录
	 * @param type         扩展类接口
	 * @return java.util.Map<java.lang.String, java.lang.Class < ?>>
	 * @since 2021/3/5
	 */
	private Map<String, Class<?>> loadExtensionClasses(Set<String> extensionDir, Class<T> type) {
		// 缓存默认的扩展名
		cacheDefaultExtensionName(type);
		Map<String, Class<?>> extensionClasses = new HashMap<>(8);
		for (String dir : extensionDir) {
			loadDirectory(extensionClasses, dir, type);
		}
		return extensionClasses;
	}

	/**
	 * 加载配置文件
	 *
	 * @param extensionClasses 扩展类
	 * @param dir              扩展类目名
	 * @param type             扩展类接口
	 * @return void
	 * @since 2021/3/5
	 */
	private void loadDirectory(Map<String, Class<?>> extensionClasses, String dir, Class<T> type) {
		String fileName = dir + type.getName();
		Enumeration<URL> urls;
		ClassLoader classLoader = findClassLoader();
		try {
			if (classLoader != null) {
				urls = classLoader.getResources(fileName);
			} else {
				urls = ClassLoader.getSystemResources(fileName);
			}
			if (urls != null) {
				while (urls.hasMoreElements()) {
					URL url = urls.nextElement();
					loadResource(extensionClasses, classLoader, url, type);
				}
			}
		} catch (IOException e) {
			log.error("Exception occurred when loading extension class (interface: " +
					type + ", description file: " + fileName + ").", e);
		}

	}

	/**
	 * 扩展类加载
	 *
	 * @param extensionClasses 扩展类加载
	 * @param classLoader      类加载器
	 * @param url              地址路径
	 * @param type             扩展类接口
	 * @return void
	 * @since 2021/3/5
	 */
	private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader, URL url, Class<T> type) {

		try {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
				String line;
				while ((line = reader.readLine()) != null) {
					int ci = line.indexOf("#");
					if (ci >= 0) {
						line = line.substring(0, ci);
					}
					line = line.trim();
					if (line.length() > 0) {
						String name = null;
						int i = line.indexOf("=");
						if (i > 0) {
							name = line.substring(0, i).trim();
							line = line.substring(i + 1).trim();
						}
						if (line.length() > 0) {
							loadClass(extensionClasses, Class.forName(line, true, classLoader), name, type);
						}
					}
				}

			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载扩展类
	 *
	 * @param extensionClasses 扩展类存储集合
	 * @param clazz            类
	 * @param name             扩展名称
	 * @param type             扩展类接口
	 * @return void
	 * @since 2021/3/5
	 */
	private void loadClass(Map<String, Class<?>> extensionClasses, Class<?> clazz, String name, Class<T> type) {
		if (!type.isAssignableFrom(clazz)) {
			throw new IllegalStateException("Error occurred when loading extension class (interface: " +
					type + ", class line: " + clazz.getName() + "), class "
					+ clazz.getName() + " is not subtype of interface.");
		}

		// 自适应扩展点
		if (clazz.isAnnotationPresent(Adaptive.class)) {
			cacheAdaptiveClass(clazz);
		} else {
			String[] names = NAME_SEPARATOR.split(name);
			if (!ArrayUtil.isEmpty(names)) {
				cacheActivateClass(clazz, names[0]);

				for (String s : names) {
					cacheNames(clazz, s);
					saveInExtensionClass(extensionClasses, clazz, s, type);
				}
			}
		}


	}

	/**
	 * 将扩展类信息保存到 extensionClasses 集合中
	 *
	 * @param extensionClasses 扩展类信息集合
	 * @param clazz            扩展类
	 * @param name             扩展名称
	 * @param type             扩展类接口
	 * @return void
	 * @since 2021/3/5
	 */
	private void saveInExtensionClass(Map<String, Class<?>> extensionClasses, Class<?> clazz, String name, Class<T> type) {
		Class<?> c = extensionClasses.get(name);
		if (c == null) {
			extensionClasses.put(name, clazz);
		} else if (c != clazz) {
			throw new IllegalStateException("Duplicate extension " + type.getName()
					+ " name " + name + " on " + c.getName() + " and " + clazz.getName());
		}
	}

	/**
	 * 扩展类和扩展名称的缓存
	 *
	 * @param clazz 扩展类
	 * @param name  扩展名称
	 * @return void
	 * @since 2021/3/5
	 */
	private void cacheNames(Class<?> clazz, String name) {
		if (!cachedNames.contains(clazz)) {
			cachedNames.put(clazz, name);
		}
	}

	/**
	 * 缓存自动激活扩展点
	 *
	 * @param clazz 扩展点类
	 * @param name  扩展点的名称
	 * @return void
	 * @since 2021/3/5
	 */
	private void cacheActivateClass(Class<?> clazz, String name) {
		Activate activate = clazz.getAnnotation(Activate.class);
		if (null != activate) {
			cachedActivates.put(name, activate);
		}


	}

	/**
	 * 缓存自适应扩展点
	 *
	 * @param clazz 自适应扩展类
	 * @return void
	 * @since 2021/3/5
	 */
	private void cacheAdaptiveClass(Class<?> clazz) {
		if (cachedAdaptiveClass == null) {
			cachedAdaptiveClass = clazz;
		} else {
			throw new IllegalStateException("More than 1 adaptive class found: "
					+ cachedAdaptiveClass.getClass().getName()
					+ ", " + clazz.getClass().getName());
		}
	}

	/**
	 * 获取类加载器
	 *
	 * @return java.lang.ClassLoader
	 * @since 2021/3/5
	 */
	private ClassLoader findClassLoader() {
		return this.classLoader != null ? this.classLoader : ExtensionLoader.class.getClassLoader();
	}

	/**
	 * 缓存默认的扩展名
	 *
	 * @param type 扩展类接口
	 * @return void
	 * @since 2021/3/5
	 */
	private void cacheDefaultExtensionName(Class<T> type) {
		SPI spi = type.getAnnotation(SPI.class);
		if (null != spi) {
			String value = spi.value();
			if ((value = value.trim()).length() > 0) {
				String[] names = NAME_SEPARATOR.split(value);
				if (names.length > 1) {
					throw new IllegalStateException("More than 1 default extension name on extension " + type.getName()
							+ ": " + Arrays.toString(names));
				}
				if (names.length == 1) {
					cachedDefaultName = names[0];
				}

			}
		}

	}


	/**
	 * 扩展类
	 *
	 * @author luyanan
	 * @since 2021/3/6
	 */
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	private static class ExInfo<T> {

		/**
		 * 排序
		 *
		 * @since 2021/3/6
		 */

		private int order;


		/**
		 * 扩展类
		 *
		 * @since 2021/3/6
		 */

		private T ext;
	}


	/**
	 * 是否匹配
	 *
	 * @param value  被匹配项
	 * @param values 集合
	 * @return boolean
	 * @since 2021/3/7
	 */
	private boolean isMatch(String value, String[] values) {
		if (StrUtil.isBlank(value)) {
			return true;
		}
		if (values != null && values.length > 0) {
			for (String s : values) {
				if (value.equals(s)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 创建自适应扩展点
	 *
	 * @return T
	 * @since 2021/3/7
	 */
	private T createAdaptiveExtension() {
		try {
			Object instance = getAdaptiveExtensionClass().newInstance();
			return injectExtension((T) instance);
		} catch (Exception e) {
			throw new IllegalStateException("Can't create adaptive extension " + type + ", cause: " + e.getMessage(), e);
		}

	}

	/**
	 * 获取自适应扩展点的类
	 *
	 * @return java.lang.Class<?>
	 * @since 2021/3/7
	 */
	private Class<?> getAdaptiveExtensionClass() {
		getExtensionClasses(extensionDir, type);
		if (cachedAdaptiveClass == null) {
			throw new NullPointerException("didn't find adaptive class");
		}
		return cachedAdaptiveClass;
	}


	/*************************************公开方法****************************************/


	public ExtensionLoader(Class<T> type) {
		// 添加默认的扩展类
		if (CollectionUtil.isEmpty(this.extensionDir)) {
			extensionDir("extension");
		}
		// 添加默认的依赖注入
		if (this.injectExtension == null) {
			this.injectExtension = new SimpleInjectExtension();
		}
		this.type = type;
	}

	/**
	 * 设置classLoader
	 *
	 * @param classLoader 类加载器
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.extension.ExtensionLoader
	 * @since 2021/3/4
	 */
	public ExtensionLoader classLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
		return this;
	}


	/**
	 * 设置依赖注入
	 *
	 * @param injectExtension 依赖注入
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.extension.ExtensionLoader
	 * @since 2021/3/4
	 */
	public ExtensionLoader injectExtension(InjectExtension injectExtension) {
		this.injectExtension = injectExtension;
		return this;
	}


	/**
	 * 设置扩展类路径
	 *
	 * @param extensionDir 扩展类路径
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.extension.ExtensionLoader
	 * @since 2021/3/4
	 */
	public ExtensionLoader extensionDir(String extensionDir) {
		this.extensionDir.add((this.parentExtensionDir + extensionDir + "/").replaceAll("/+", "/"));
		return this;
	}

	/**
	 * 获取扩展类获取ExtensionLoader
	 *
	 * @param type 扩展类
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.extension.ExtensionLoader<T>
	 * @since 2021/3/4
	 */
	public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {

		if (null == type) {
			throw new IllegalArgumentException("Extension type == null");
		}
		// type 必须为接口
		if (!type.isInterface()) {
			throw new IllegalArgumentException("Extension type (" + type + ")is not a  interface");
		}
		// 判断是否加了@SPI注解
		if (!withExtensionAnnotation(type)) {
			throw new IllegalArgumentException("Extension type (" + type +
					")is not a  extension,because it is Not annotated with @" + SPI.class.getSimpleName() + "!");
		}
		return new ExtensionLoader<>(type);
	}


	/**
	 * 根据名称获取配置的扩展点
	 *
	 * @param name 扩展点的名称
	 * @return T
	 * @since 2021/3/4
	 */
	public T getExtension(String name) {
		if (StrUtil.isEmpty(name)) {
			throw new IllegalArgumentException("name == null");
		}
		// 如果名称为true, 则返回一个默认的扩展点
		if (TRUE.equals(name)) {
			return getDefaultExtension();
		}
		Holder<T> holder = getOrCreateHolder(name);
		T instance = holder.get();
		if (instance == null) {
			synchronized (holder) {
				instance = holder.get();
				if (null == instance) {
					// 根据名称创建实例
					instance = (T) createExtension(name);
					// 存起来
					holder.set(instance);
				}
			}
		}
		return instance;
	}


	/**
	 * 获取所有的扩展点
	 *
	 * @return java.util.List<T>
	 * @since 2021/3/4
	 */
	public List<T> getExtension() {
		getExtensionClasses(extensionDir, type);
		return cachedClasses.get().entrySet().stream().map(a -> getExtension(a.getKey())).collect(Collectors.toList());
	}


	/**
	 * 获取默认的扩展点(就是@SPI 注解的value值指定的扩展点)
	 *
	 * @return T
	 * @since 2021/3/4
	 */
	public T getDefaultExtension() {
		getExtensionClasses(extensionDir, type);
		if (StrUtil.isBlank(cachedDefaultName) || TRUE.equals(cachedDefaultName)) {
			return null;
		}
		return getExtension(cachedDefaultName);
	}


	/**
	 * 判断名字为@name的扩展点是否存在
	 *
	 * @param name 扩展点的名字
	 * @return boolean
	 * @since 2021/3/4
	 */
	public boolean hasExtension(String name) {
		if (StrUtil.isBlank(name)) {
			throw new NullPointerException("name ==null");
		}
		Class<?> clazz = this.getExtensionClasses(extensionDir, type).get(name);
		return null != clazz;
	}

	/**
	 * 获取默认的扩展点名称(@SPI注解上的value值)
	 *
	 * @return java.lang.String
	 * @since 2021/3/4
	 */
	public String getDefaultExtensionName() {
		getExtensionClasses(extensionDir, type);
		return cachedDefaultName;
	}


	/**
	 * 获取自动激活扩展点
	 *
	 * @param params 参数
	 * @return java.util.List<T>
	 * @since 2021/3/4
	 */
	public List<T> getAdaptiveExtension(Params params) {
		List<T> exts;
		getExtensionClasses(extensionDir, type);
		Set<String> names = null == params.getParams() ? new HashSet<>(0) : params.getParams();
		List<ExInfo> exInfoList = new ArrayList<>();
		for (Map.Entry<String, Object> entry : cachedActivates.entrySet()) {
			String name = entry.getKey();
			Object activate = entry.getValue();
			String[] activateGroup, activateValue;
			int order = 0;
			if (activate instanceof Activate) {
				activateGroup = ((Activate) activate).group();
				activateValue = ((Activate) activate).value();
				order = ((Activate) activate).order();
			} else {
				continue;
			}
			if (isMatch(params.getGroup(), activateGroup)) {
				// 判断 params 的params是否跟注解中的匹配
				if (activateValue == null || activateValue.length == 0 || ArrayUtil.contains(activateValue, names)) {
					T ext = getExtension(name);
					exInfoList.add(new ExInfo(order, ext));
				}

			}
		}
		List<Object> list = exInfoList.stream().sorted(Comparator.comparing(ExInfo::getOrder)).map(ExInfo::getExt).collect(Collectors.toList());
		exts = (List<T>) list;
		return exts;
	}

	/**
	 * 获取自适应的扩展点
	 *
	 * @return T
	 * @since 2021/3/4
	 */
	public T getAdaptiveExtension() {
		T instance = cachedAdaptiveInstance.get();
		if (null == instance) {
			synchronized (cachedAdaptiveInstance) {
				if (null == instance) {
					// 创建自适应扩展点
					instance = createAdaptiveExtension();
					cachedAdaptiveInstance.set(instance);
				}
			}
		}
		return instance;
	}


	/**
	 * 获取所有的扩展类名称
	 *
	 * @return java.util.List<java.lang.String>
	 * @since 2021/3/4
	 */
	public List<String> getExtensionNames() {
		getExtensionClasses(extensionDir, type);
		return cachedClasses.get().keySet().stream().collect(Collectors.toList());
	}


}
