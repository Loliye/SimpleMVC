package org.mikufans.core;

import org.mikufans.SimpleConstants;
import org.mikufans.util.PropertiesUtil;

import java.util.Properties;

/**
 * 读取配置文件
 */
public class ConfigHelper
{
    /**
     * 配置文件
     */
    private static final Properties configProps = PropertiesUtil.loadProps(SimpleConstants.CONFIG_PROPS);

    public static String getString(String key)
    {
        return PropertiesUtil.getString(configProps, key);
    }

    public static String getString(String key, String defaultValue)
    {
        return PropertiesUtil.getString(configProps, key, defaultValue);
    }

    public static int getInt(String key)
    {
        return PropertiesUtil.getNumber(configProps, key);
    }

    public static int getInt(String key, int defaultValue)
    {
        return PropertiesUtil.getNumber(configProps, key, defaultValue);
    }

    /**
     * 默认为false
     * @param key
     * @return
     */
    public static boolean getBoolean(String key)
    {
        return PropertiesUtil.getBoolean(configProps,key);
    }
}
