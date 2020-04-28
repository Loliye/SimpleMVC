package org.mikufans.ioc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.mikufans.core.ClassHelper;
import org.mikufans.core.exception.InitError;
import org.mikufans.ioc.annotation.Impl;
import org.mikufans.ioc.annotation.Inject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;


/**
 * ioc容器  初始化
 */
public class IocHelper
{
    static
    {
        try
        {
            Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet())
            {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取bean类中所有得字段（不含有父类得）
                Field[] beanFields = beanClass.getDeclaredFields();
                if (!ArrayUtils.isEmpty(beanFields))
                {
                    for (Field beanFiled : beanFields)
                    {
                        //依赖注入进行赋值
                        if (beanFiled.isAnnotationPresent(Inject.class))
                        {
                            Class<?> interfaceClass = beanFiled.getType();
                            Class<?> implClass = findImplClass(interfaceClass);

                            if (implClass != null)
                            {
                                Object implInstance = beanMap.get(implClass);
                                if (implInstance != null)
                                {
                                    //设置为public
                                    beanFiled.setAccessible(true);
                                    //赋于初始值
                                    beanFiled.set(beanInstance, implInstance);
                                }
                                else throw new InitError("依赖注入出错！" + beanClass.getSimpleName());
                            }
                        }
                    }
                }
            }
        } catch (Exception e)
        {
            throw new InitError("IocHelper初始化失败！", e);
        }
    }


    /**
     * 查找实现类
     *
     * @param interfaceClass
     * @return
     */
    public static Class<?> findImplClass(Class<?> interfaceClass)
    {
        Class<?> implClass = interfaceClass;

        if (interfaceClass.isAnnotationPresent(Impl.class))
        {
            implClass = interfaceClass.getAnnotation(Impl.class).value();
        } else
        {
            List<Class<?>> implClassList = ClassHelper.getClassListBySuper(interfaceClass);
            if (CollectionUtils.isNotEmpty(implClassList))
            {
                implClass = implClassList.get(0);
            }
        }
        return implClass;
    }
}
