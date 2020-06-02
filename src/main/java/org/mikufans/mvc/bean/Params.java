package org.mikufans.mvc.bean;

import java.util.Map;

/**
 * request 请求参数
 */
public class Params
{
    private final Map<String, Object> fieldMap;

    public Params(Map<String, Object> fieldMap)
    {
        this.fieldMap = fieldMap;
    }

    public Map<String, Object> getFieldMap()
    {
        return fieldMap;
    }

    private Object get(String name)
    {return fieldMap.get(name);}


    public String getString(String name)
    {
        return String.valueOf(get(name));
    }

    public int getInt(String name)
    {
        return Integer.valueOf(getString(name));
    }

    public long getLong(String name)
    {
        return Long.valueOf(getString(name));
    }

    public double getDouble(String name)
    {
        return Double.valueOf(getString(name));
    }

    public boolean getBoolean(String name)
    {
        return Boolean.valueOf(getString(name));
    }

}
