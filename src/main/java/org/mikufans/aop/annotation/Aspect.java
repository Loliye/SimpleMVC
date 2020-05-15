package org.mikufans.aop.annotation;

import java.lang.annotation.*;

/**
 * 定义切面
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect
{
    /**
     * 包名
     *
     * @return
     */
    String packName() default "";

    /**
     * 类名
     *
     * @return
     */
    String className() default "";

    /**
     * 注解
     *
     * @return
     */
    Class<? extends Annotation> annotation() default Aspect.class;
}
