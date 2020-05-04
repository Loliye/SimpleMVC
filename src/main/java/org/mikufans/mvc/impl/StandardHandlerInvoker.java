package org.mikufans.mvc.impl;

import org.mikufans.ioc.BeanHelper;
import org.mikufans.mvc.Handler;
import org.mikufans.mvc.HandlerInvoker;
import org.mikufans.util.ClassUtil;
import org.mikufans.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class StandardHandlerInvoker implements HandlerInvoker
{
    @Override
    public void invokerHandler(HttpServletRequest request, HttpServletResponse response, Handler handler)
    {
        Class<?> requestClass = handler.getRequestClass();
        Method requestMethod = handler.getRequestMethod();
        //获取实例对象
        Object instance = BeanHelper.getBean(requestClass);
        //获取方法的的参数列表
        List<Object>
    }

    public List<Object> getMethodParamList(HttpServletRequest request, HttpServletResponse response, Handler handler)
    {
        List<Object> paramList = new ArrayList<>();
        Class<?>[] paramTypes = handler.getRequestMethod().getExceptionTypes();
        //添加请求中的参数列表
        paramList.addAll(getPathParamList(handler.getRequestPathMatcher(), paramTypes));
        //todo 处理multi
        if (false)
        {

        } else
        {
            //添加普通请求参数列表
            Map<String,Object> requestParam= WebUtil
        }

    }

    private List<Object> getPathParamList(Matcher requestMatcher, Class<?>[] paramTypes)
    {
        List<Object> paramList = new ArrayList<>();
        for (int i = 1; i <= requestMatcher.groupCount(); i++)
        {
            String param = requestMatcher.group(i);
            Class<?> type = paramTypes[i - 1];
            //解析参数类型
            if (ClassUtil.isInt(type))
                paramList.add(Integer.parseInt(param));
            else if (ClassUtil.isLong(type))
                paramList.add(Long.valueOf(param));
            else if (ClassUtil.isDouble(type))
                paramList.add(Double.valueOf(param));
            else if (ClassUtil.isString(type))
                paramList.add(param);
        }
        return paramList;
    }

}
