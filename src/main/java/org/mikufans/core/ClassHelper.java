package org.mikufans.core;

import org.mikufans.InstanceFactory;
import org.mikufans.SimpleConstants;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 读取相关类
 */
public class ClassHelper
{
    private static final String basePackage = ConfigHelper.getString(SimpleConstants.PACKAGE_PATH);

    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    public static List<Class<?>> getClassList()
    {
        return classScanner.getClassList(basePackage);
    }

    public static List<Class<?>> getClassListBySuper(Class<?> superClass)
    {
        return classScanner.getClassListBySuper(basePackage, superClass);
    }

    public static List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> annotationClass)
    {
        return classScanner.getClassListByAnnotation(basePackage, annotationClass);
    }

}
