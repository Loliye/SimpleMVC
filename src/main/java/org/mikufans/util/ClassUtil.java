package org.mikufans.util;

import lombok.extern.slf4j.Slf4j;
import java.net.URL;


/**
 * class操作工具类
 */
@Slf4j
public class ClassUtil
{

    public static ClassLoader getClassLoader()
    {
        return Thread.currentThread().getContextClassLoader();
    }

    public static String getClassPath()
    {
        String classPath = "";
        URL resource = getClassLoader().getResource("");
        if (resource != null)
            classPath = resource.getPath();
        return classPath;
    }

    public static Class<?> loadClass(String className)
    {
        return loadClass(className,true);
    }

    public static Class<?> loadClass(String className, boolean isInit)
    {
        Class<?> cls;
//        System.out.println(className);
        try
        {
            cls = Class.forName(className, isInit, getClassLoader());
        } catch (ClassNotFoundException e)
        {
//            System.out.println("class加载出错！");
            log.error("class加载出错！", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static boolean isInt(Class<?> cls)
    {
        return cls.equals(int.class) || cls.equals(Integer.class);
    }

    public static boolean isLong(Class<?> cls)
    {
        return cls.equals(long.class) || cls.equals(Long.class);
    }

    public static boolean isDouble(Class<?> cls)
    {
        return cls.equals(double.class) || cls.equals(Double.class);
    }

    public static boolean isString(Class<?> cls)
    {
        return cls.equals(String.class);
    }

}
