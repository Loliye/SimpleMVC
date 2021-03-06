package org.mikufans.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.mikufans.SimpleConstants;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

@Slf4j
public class PropertiesUtil
{

    public static Properties loadProps(String propsPath)
    {
        Properties properties = new Properties();
        InputStream inputStream = null;

        try
        {
            if (StringUtils.isBlank(propsPath))
                throw new IllegalAccessException();
            String suffix = ".properties";
            if (propsPath.lastIndexOf(suffix) == -1)
                propsPath += suffix;

            inputStream = ClassUtil.getClassLoader().getResourceAsStream(propsPath);
            if (inputStream != null)
                properties.load(new InputStreamReader(inputStream,SimpleConstants.UTF_8));
        } catch (IOException e)
        {
            log.error("加载配置文件出错", e);
        } catch (IllegalAccessException e)
        {
            log.error("未发现配置文件", e);
        } finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                } catch (IOException e)
                {
                    log.error("释放资源出错");
                }
            }
        }
        return properties;
    }

    public static void main(String[] args)
    {
        System.out.println(loadProps("messages.properties").getProperty("not.null"));
        System.out.println(loadProps(SimpleConstants.CONFIG_PROPS));
    }

    public static String getString(Properties properties, String key)
    {
        String value = "";
        if (properties.containsKey(key))
            value = properties.getProperty(key);
        return value;
    }

    public static String getString(Properties properties, String key, String defaultValue)
    {
        String value = defaultValue;
        if (properties.containsKey(key))
            value = properties.getProperty(key);
        return value;
    }

    public static int getNumber(Properties properties, String key)
    {
        int value = 0;
        if (properties.containsKey(key))
            value = Integer.valueOf(properties.getProperty(key));
        return value;
    }

    public static int getNumber(Properties properties, String key, int defaultValue)
    {
        int value = defaultValue;
        if(properties.containsKey(key))
            value= Integer.parseInt(properties.getProperty(key));
        return value;
    }

    /**
     * 默认false
     *
     * @param properties
     * @param key
     * @return
     */
    public static boolean getBoolean(Properties properties, String key)
    {
        boolean value = false;
        if (properties.containsKey(key))
            value = Boolean.parseBoolean(properties.getProperty(key));
        return value;
    }

}
