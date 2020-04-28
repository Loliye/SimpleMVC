package org.mikufans.mvc;

import java.lang.reflect.Method;
import java.util.regex.Matcher;

/**
 * 处理request 请求相关的信息
 * 用户映射前端请求
 */
public class Handler
{
    private Class<?> requestClass;
    private Method reqeustMethod;
    private Matcher requestPathMatcher;

    public Handler(Class<?> requestClass, Method reqeustMethod)
    {
        this.requestClass = requestClass;
        this.reqeustMethod = reqeustMethod;
    }

    public Class<?> getRequestClass()
    {
        return requestClass;
    }

    public Method getReqeustMethod()
    {
        return reqeustMethod;
    }

    public Matcher getRequestPathMatcher()
    {
        return requestPathMatcher;
    }

    public void setRequestPathMatcher(Matcher requestPathMatcher)
    {
        this.requestPathMatcher = requestPathMatcher;
    }
}
