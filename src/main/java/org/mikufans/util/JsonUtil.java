package org.mikufans.util;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

@Slf4j
public class JsonUtil
{

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String toJSON(T obj)
    {
        String json;
        try
        {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (IOException e)
        {
            log.error("java对象转json出错！", e);
            throw new RuntimeException(e);
        }
        return json;
    }
}
