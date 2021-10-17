package com.java.ccs.spring;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author caocs
 * @date 2021/10/6
 * 自定义的ApplicationContext。相当于Spring中
 */
public class CcsApplicationContext {

    /**
     * 配置类
     */
    private Class configClass;
    /**
     * 单例池
     */
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    /**
     * 扫描到的所有bean定义
     */
    private ConcurrentHashMap<String, CcsBeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    /**
     * 存放beanPostProcessor实例（包括我们自定义的）
     */
    private List<CcsBeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public CcsApplicationContext(Class configClass) {
        this.configClass = configClass;
        scanComponent(configClass);

        for (Map.Entry<String, CcsBeanDefinition> entry : beanDefinitions.entrySet()) {
            String beanName = entry.getKey();
            CcsBeanDefinition beanDefinition = entry.getValue();
            if ("singleton".equals(beanDefinition.getScope())) {
                Object bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
        }

    }

    public Object createBean(String beanName, CcsBeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getClazz();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            // 依赖注入
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(CcsAutowired.class)) {
                    Object bean = getBean(declaredField.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(instance, bean);
                }
            }
            // Aware回调。BeanNameAware实现
            if (instance instanceof CcsBeanNameAware) {
                ((CcsBeanNameAware) instance).setBeanName(beanName);
            }

            for (CcsBeanPostProcessor beanPostProcessor : beanPostProcessors) {
                instance = beanPostProcessor.postProcessorBeforeInitialization(instance, beanName);
            }

            // 初始化
            if (instance instanceof CcsInitializingBean) {
                try {
                    ((CcsInitializingBean) instance).afterPropertiesSet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // BeanPostProcessor
            for (CcsBeanPostProcessor beanPostProcessor : beanPostProcessors) {
                // 此时，返回的instance可能已经不再是原来的对象了
                instance = beanPostProcessor.postProcessorAfterInitialization(instance, beanName);
            }

            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void scanComponent(Class configClass) {
        // 解析配置类
        // @CcsComponentScan注解解析-->扫描路径-->扫描-->BeanDefinitionMap
        CcsComponentScan ccsComponentScan = (CcsComponentScan) configClass.getDeclaredAnnotation(CcsComponentScan.class);
        String scanPath = ccsComponentScan.value();
        scanPath = scanPath.replace(".", "/");
        ClassLoader classLoader = CcsApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource(scanPath);
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String fileName = f.getAbsolutePath();
                if (fileName.endsWith(".class")) {
                    String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                    className = className.replace("\\", ".");
                    System.out.println(f);
                    try {
                        Class<?> clazz = classLoader.loadClass(className);
                        if (clazz.isAnnotationPresent(CcsComponent.class)) {
                            // 解析是否是BeanPostProcessor
                            if (CcsBeanPostProcessor.class.isAssignableFrom(clazz)) {
                                CcsBeanPostProcessor instance = (CcsBeanPostProcessor) clazz.getDeclaredConstructor().newInstance();
                                beanPostProcessors.add(instance);
                            }

                            // 解析类，判断当前bean是单例bean还是prototype的bean。 ---> BeanDefinition
                            CcsComponent ccsComponent = clazz.getDeclaredAnnotation(CcsComponent.class);
                            String beanName = ccsComponent.value();
                            CcsBeanDefinition beanDefinition = new CcsBeanDefinition();
                            beanDefinition.setClazz(clazz);
                            if (clazz.isAnnotationPresent(CcsScope.class)) {
                                CcsScope ccsScope = clazz.getDeclaredAnnotation(CcsScope.class);
                                beanDefinition.setScope(ccsScope.value());
                            } else {
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinitions.put(beanName, beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Object getBean(String beanName) {
        if (beanDefinitions.containsKey(beanName)) {
            CcsBeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if ("singleton".equals(beanDefinition.getScope())) {
                // 如果是单例，则从单例池中拿这个对象
                return singletonObjects.get(beanName);
            } else {
                // 创建新对象
                return createBean(beanName, beanDefinition);
            }
        } else {
            System.out.println("异常：未找到对应bean");
        }
        return null;
    }

}
