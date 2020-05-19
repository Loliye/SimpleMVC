package org.mikufans;

import org.mikufans.aop.AopHelper;
import org.mikufans.dao.DatabaseHelper;
import org.mikufans.ioc.BeanHelper;
import org.mikufans.ioc.IocHelper;
import org.mikufans.mvc.ControllerHelper;
import org.mikufans.orm.EntityHelper;
import org.mikufans.util.ClassUtil;

/**
 * 各部分组件的初始化
 */
public final class HelperLoader
{
    public static void init()
    {
        Class<?>[] classes = {
                DatabaseHelper.class,
                EntityHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class,

        };

        for (Class<?> cls : classes)
            ClassUtil.loadClass(cls.getName());
    }
}
