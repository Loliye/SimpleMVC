package org.mikufans.core;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 类扫描器
 */
public interface ClassScanner
{


    List<Class<?>> getClassList(String packageName);

    /**
     * 获取包下面的 带有注解的类
     * @param packageName
     * @param annotationClass
     * @return
     */
    List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass);

    /**
     * 获取包下面指定的父类或接口的相关类
     * @param packageName 包名
     * @param superClass 父类或接口
     * @return
     */
    List<Class<?>> getClassListBySuper(String packageName,Class<?> superClass);
}
