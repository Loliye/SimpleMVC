package org.mikufans;

import org.mikufans.ioc.BeanHelper;
import org.mikufans.ioc.IocHelper;
import org.mikufans.util.ClassUtil;

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
