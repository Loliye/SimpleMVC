package org.mikufans.util;

import jdk.internal.util.xml.impl.Input;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流操作工具
 */
@Slf4j
public class SteamUtil
{
    public static String getString(InputStream inputStream)
    {
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }

        } catch (IOException e)
        {
            log.error("steam转化String出错",e);
            throw new RuntimeException(e);
        }
        return buffer.toString();
    }
}
