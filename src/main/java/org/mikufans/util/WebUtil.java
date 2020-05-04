package org.mikufans.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class WebUtil
{
    /**
     * 获取请求路径
     *
     * @param request
     * @return
     */
    public static String getRequestPath(HttpServletRequest request)
    {
        String servletPath = request.getServletPath();
        String pathInfo = StringUtils.defaultIfEmpty(request.getPathInfo(), "");
        return servletPath + pathInfo;
    }

    public static void redirectRequest(String path, HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            response.sendRedirect(request.getContextPath() + path);
        } catch (Exception e)
        {
            log.error("重定向请求出错！", e);
            throw new RuntimeException(e);
        }
    }

    public static void sendError(int code, String message, HttpServletResponse response)
    {
        try
        {
            response.sendError(code, message);
        } catch (IOException e)
        {
            log.error("返回错误结果失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取请求中的所有参数
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getRequestParamMap(HttpServletRequest request)
    {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        try
        {
            String method = request.getMethod();
            if (method.equalsIgnoreCase("put") || method.equalsIgnoreCase("delete"))
            {
                String queryString = URLDecoder.decode(SteamUtil.getString(request.getInputStream()));
                if (StringUtils.isNotEmpty(queryString))
                {
                    String[] queryArr = StringUtils.splitByWholeSeparator(queryString, "&");
                    if (!ArrayUtils.isEmpty(queryArr))
                    {
                        for (String query : queryArr)
                        {
                            String[] arr = StringUtils.splitByWholeSeparator(query, "=");
                            if (!ArrayUtils.isEmpty(arr) && arr.length == 2)
                            {
                                String name = arr[0];
                                String value = arr[1];
                                if (checkParamName(name))
                                    value = paramMap.get(name) + String.valueOf((char) 29) + value;
                                paramMap.put(name, value);
                            }
                        }
                    }
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 忽略jquery的缓存参数
     *
     * @param paramName
     * @return
     */
    private static boolean checkParamName(String paramName)
    {
        return !paramName.equals("_");
    }
}
