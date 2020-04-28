package org.mikufans;

import org.mikufans.ioc.BeanHelper;
import org.mikufans.ioc.IocHelper;
import org.mikufans.util.ClassUtil;

/**
 * 各部分组件的初始化
 */
public class HelperLoader
{
    public static void init()
    {
        Class<?>[] classes = {
                BeanHelper.class,
                IocHelper.class,
        };

        for (Class<?> cls : classes)
            ClassUtil.loadClass(cls.getName());
    }
}
