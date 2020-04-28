package org.mikufans.ioc;

import org.mikufans.core.ClassHelper;
import org.mikufans.ioc.annotation.Bean;
import org.mikufans.mvc.annotation.Controller;
import sun.nio.ch.IOStatus;

import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanHelper
{
    /**
     * ioc容器
     */
    private static final Map<Class<?>, Object> beanMap = new HashMap<>();

    //初始化ioc容器
    static
    {
        //todo  扫描包 service controller mapper
        try
        {
            List<Class<?>> classList = ClassHelper.getClassList();
            for (Class<?> cls : classList)
            {
                if (cls.isAnnotationPresent(Bean.class)||cls.isAnnotationPresent(Controller.class))
                {
                    Object object = cls.newInstance();
                    beanMap.put(cls, object);
                }
            }
        } catch (Exception e)
        {
            throw new Error("beanHelper初始化错误！", e);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {return beanMap;}

    public static <T> T getBean(Class<T> cls)
    {
        if (!beanMap.containsKey(cls))
        {
            throw new RuntimeException("找不到相关的实体类！" + cls);
        }
        return (T) beanMap.get(cls);
    }

    public static void setBean(Class<?> cls, Object object) {beanMap.put(cls, object);}
}
