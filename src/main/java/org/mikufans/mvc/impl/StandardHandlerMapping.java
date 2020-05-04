package org.mikufans.mvc.impl;

import org.mikufans.mvc.ControllerHelper;
import org.mikufans.mvc.Handler;
import org.mikufans.mvc.HandlerMapping;
import org.mikufans.mvc.Requester;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StandardHandlerMapping implements HandlerMapping
{
    @Override
    public Handler getHandler(String currentRequestMethod, String currentRequestPath)
    {
        Handler handler = null;
        Map<Requester, Handler> map = ControllerHelper.getControllerMap();

        for (Map.Entry<Requester, Handler> entry : map.entrySet())
        {
            Requester requester = entry.getKey();
            String method = requester.getRequestMethod();
            String path = requester.getRequestPath();
            //获取请求路径匹配路径 参数
            Matcher requestPathMatcher = Pattern.compile(path).matcher(currentRequestPath);

            //请求方法  路径进行匹配
            if (method.equalsIgnoreCase(currentRequestMethod) && requestPathMatcher.matches())
            {
                handler = entry.getValue();
                //设置请求路由
                if (handler != null)
                    handler.setRequestPathMatcher(requestPathMatcher);
                break;
            }
        }
        return handler;
    }
}
