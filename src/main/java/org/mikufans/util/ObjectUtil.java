package org.mikufans.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class ObjectUtil
{
    /**
     * 获取object对象中的成员属性值
     *
     * @param object
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object object, String fieldName)
    {
        Object propertyValue = null;
        if (PropertyUtils.isReadable(object, fieldName))
        {
            try
            {
                propertyValue = PropertyUtils.getProperty(object, fieldName);
            } catch (Exception e)
            {
                log.error("获取成员变量出错!", e);
                throw new RuntimeException(e);
            }
        }
        return propertyValue;
    }


    /**
     * 将对象拆成k v对(字段名，字段值)
     *
     * @param object
     * @param isStaticIgnored
     * @return
     */
    public static Map<String, Object> getFieldMap(Object object, boolean isStaticIgnored)
    {
        Map<String, Object> fieldMap = new LinkedHashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            if (isStaticIgnored && Modifier.isStatic(field.getModifiers()))
                continue;

            String fieldName = field.getName();
            Object fieldValue = ObjectUtil.getFieldValue(object, fieldName);
            fieldMap.put(fieldName, fieldValue);
        }
        return fieldMap;
    }

    /**
     * 将对象拆成k v对(字段名，字段值)
     */
    public static Map<String, Object> getFieldMap(Object object)
    {
        return getFieldMap(object, true);
    }
}
