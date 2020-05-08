package org.mikufans.core.impl;

import org.mikufans.core.ClassScanner;
import org.mikufans.core.impl.support.AnnotationClassTemplate;
import org.mikufans.core.impl.support.ClassTemplate;
import org.mikufans.core.impl.support.SupperClassTemplate;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 类扫描器
 */
public class StandardClassScanner implements ClassScanner
{
    @Override
    public List<Class<?>> getClassList(String packageName)
    {
        return new ClassTemplate(packageName)
        {

            @Override
            public boolean checkAddClass(Class<?> cls)
            {
                String className = cls.getName();
                String pkgName = className.substring(0, className.lastIndexOf("."));
                return pkgName.startsWith(packageName);
            }
        }.getClassList();
    }

    @Override
    public List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass)
    {
        return new AnnotationClassTemplate(packageName, annotationClass)
        {
            @Override
            public boolean checkAddClass(Class<?> cls)
            {
                return cls.isAnnotationPresent(annotationClass);
            }
        }.getClassList();
    }

    @Override
    public List<Class<?>> getClassListBySuper(String packageName, Class<?> superClass)
    {
        return new SupperClassTemplate(packageName, superClass)
        {
            @Override
            public boolean checkAddClass(Class<?> cls)
            {
                return superClass.isAssignableFrom(cls) && !superClass.equals(cls);
            }
        }.getClassList();
    }

}
