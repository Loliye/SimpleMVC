package org.mikufans.plugin.shiro.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 判断当前用户是否已认证
 */
@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authenticated {
}
