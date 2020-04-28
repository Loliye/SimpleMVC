package org.mikufans;


import org.apache.commons.lang.StringUtils;
import org.mikufans.core.ClassScanner;
import org.mikufans.core.ConfigHelper;
import org.mikufans.core.impl.StandardClassScanner;
import org.mikufans.util.ClassUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存放各种实例
 */
public class InstanceFactory
{
    /**
     * 缓存实例对象
     */
    private static final Map<String, Object> cache = new ConcurrentHashMap<>();

    private static final String CLASS_SCANNER = "simple.class_scanner";


    /**
     * 获取各个处理器实例对象
     *
     * @param cacheKey
     * @param defaultImplClass
     * @param <T>
     * @return
     */
    public static <T> T getInstance(String cacheKey, Class<T> defaultImplClass)
    {
        //在缓存中先查看  没有就反射获取在存放在至缓存中
        if (cache.containsKey(cacheKey))
        {
            return (T) cache.get(cacheKey);
        }

        String implClassName = ConfigHelper.getString(cacheKey);
        if (StringUtils.isEmpty(implClassName))
            implClassName = defaultImplClass.getName();
        System.out.println(implClassName);
        T instance = null;
        try
        {
            instance = (T) ClassUtil.loadClass(implClassName).newInstance();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        if (instance != null)
            cache.put(cacheKey, instance);

        return instance;
    }


    //todo
    public static ClassScanner getClassScanner()
    {
        return getInstance(CLASS_SCANNER, StandardClassScanner.class);
        //        return new StandardClassScanner();
    }
}
