package org.mikufans.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类名->表的名字
 * 指定数据库表的名字
 * bean:驼峰   数据库表名：下划线形式
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table
{
    String value();
}
