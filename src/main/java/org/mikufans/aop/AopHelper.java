package org.mikufans.aop;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mikufans.InstanceFactory;
import org.mikufans.SimpleConstants;
import org.mikufans.aop.annotation.Aspect;
import org.mikufans.aop.annotation.AspectOrder;
import org.mikufans.aop.proxy.Proxy;
import org.mikufans.core.ClassHelper;
import org.mikufans.core.ClassScanner;
import org.mikufans.plugin.PluginProxy;
import org.mikufans.transaction.annotation.Service;
import org.mikufans.transaction.annotation.Transaction;
import org.mikufans.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.*;

public class AopHelper
{
    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    static
    {
        try
        {
            //切面代理类  被切目标类
            Map<Class<?>, List<Class<?>>> proxyMap = getProxyMap();
            //被切目标类  切面代理类列表
            Map<Class<?>, List<Proxy>> targetMap = getTargetMap(proxyMap);
        } catch (Exception e)
        {

        }
    }

    private static Map<Class<?>, List<Class<?>>> getProxyMap() throws InstantiationException, IllegalAccessException
    {
        Map<Class<?>, List<Class<?>>> proxyMap = new LinkedHashMap<>();
        addPluginProxy(proxyMap);
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 插件代理
     *
     * @param proxyMap
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static void addPluginProxy(Map<Class<?>, List<Class<?>>> proxyMap) throws IllegalAccessException, InstantiationException
    {
        // 获取插件包名下父类为 PluginProxy 的所有类（插件代理类）
        List<Class<?>> pluginProxyClassList = classScanner.getClassListBySuper(SimpleConstants.PLUGIN_PAGEAGE, PluginProxy.class);
        if (CollectionUtils.isNotEmpty(pluginProxyClassList))
        {
            for (Class<?> pluginProxyClass : pluginProxyClassList)
            {
                PluginProxy pluginProxy = (PluginProxy) pluginProxyClass.newInstance();
                //代理类-》目标类
                proxyMap.put(pluginProxyClass, pluginProxy.getTargetClassList());
            }
        }
    }

    /**
     * 切面代理
     * 实现的AspectProxy类  进行切面
     */
    private static void addAspectProxy(Map<Class<?>, List<Class<?>>> proxyMap)
    {
        List<Class<?>> aspectProxyClassList = ClassHelper.getClassListBySuper(AspectProxy.class);
        aspectProxyClassList.addAll(classScanner.getClassListBySuper(SimpleConstants.PLUGIN_PAGEAGE, AspectProxy.class));
        sortAspectProxyClassList(aspectProxyClassList);
        for (Class<?> aspectProxyClass : aspectProxyClassList)
        {
            if (aspectProxyClass.isAnnotationPresent(Aspect.class))
            {
                //获取注解 创建被切的目标类 初始化容器
                Aspect aspect = aspectProxyClass.getAnnotation(Aspect.class);
                List<Class<?>> targetClassList = getTargetClassList(aspect);
                proxyMap.put(aspectProxyClass, targetClassList);
            }
        }
    }

    /**
     * 事务切面
     */
    private static void addTransactionProxy(Map<Class<?>, List<Class<?>>> proxyMap)
    {
        List<Class<?>> serviceClassList = ClassHelper.getClassListByAnnotation(Service.class);
        proxyMap.put(Transaction.class, serviceClassList);
    }

    /**
     * aspectOrder进行排序
     */
    private static void sortAspectProxyClassList(List<Class<?>> proxyClassList)
    {
        Collections.sort(proxyClassList, new Comparator<Class<?>>()
        {
            @Override
            public int compare(Class<?> o1, Class<?> o2)
            {

                if (o1.isAnnotationPresent(AspectOrder.class) || o2.isAnnotationPresent(AspectOrder.class))
                {
                    // 若有 Order 注解，则优先比较（序号的值越小越靠前）
                    if (o1.isAnnotationPresent(AspectOrder.class))
                        return getOrderValue(o1) - getOrderValue(o2);
                    else return getOrderValue(o2) - getOrderValue(o1);
                } else
                {
                    // 若无 Order 注解，则比较类名（按字母顺序升序排列）
                    return o1.hashCode() - o2.hashCode();
                }
            }

            private int getOrderValue(Class<?> aspect)
            {
                return aspect.getAnnotation(AspectOrder.class) != null ? aspect.getAnnotation(AspectOrder.class).value() : 0;
            }
        });
    }

    /**
     * 获取Aspect注解的类
     */
    private static List<Class<?>> getTargetClassList(Aspect aspect)
    {
        List<Class<?>> targetClassList = new ArrayList<>();
        String packageName = aspect.packName();
        String className = aspect.className();
        Class<? extends Annotation> annotation = aspect.annotation();

        if (StringUtils.isNotEmpty(packageName))
        {
            //包名 类名不为空 直接添加
            if (StringUtils.isNotEmpty(className))
            {
                targetClassList.add(ClassUtil.loadClass(packageName + "." + className, false));
            } else
            {
                //类名为空 且不是aspect注解  添加包下面所带的注解所有类
                if (annotation != null && !annotation.equals(Aspect.class))
                    targetClassList.addAll(classScanner.getClassListByAnnotation(packageName, annotation));
                else targetClassList.addAll(classScanner.getClassList(packageName));
            }
        } else
        {
            // 若注解不为空且不是 Aspect 注解，则添加应用包名下带有该注解的所有类
            if (annotation != null && !annotation.equals(Aspect.class))
                targetClassList.addAll(ClassHelper.getClassListByAnnotation(annotation));
        }
        return targetClassList;
    }

    private static Map<Class<?>, List<Proxy>> getTargetMap(Map<Class<?>, List<Class<?>>> proxyMap) throws IllegalAccessException, InstantiationException
    {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, List<Class<?>>> proxyEntry : proxyMap.entrySet())
        {
            Class<?> proxyClass = proxyEntry.getKey();
            List<Class<?>> targetClassList = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassList)
            {
                //创建切面类的实例
                Proxy baseAspect = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(baseAspect))
                    targetMap.get(targetClass).add(baseAspect);
                else
                {
                    List<Proxy> baseAspectList = new ArrayList<>();
                    baseAspectList.add(baseAspect);
                    targetMap.put(targetClass, baseAspectList);
                }
            }
        }

        return targetMap;
    }

}
