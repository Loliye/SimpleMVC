package org.mikufans.mvc.impl;

import lombok.extern.slf4j.Slf4j;
import org.mikufans.mvc.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class StandardHandlerExceptionResolver implements HandlerExceptionResolver
{
    @Override
    public void resolverHandlerException(HttpServletRequest request, HttpServletResponse response, Exception e)
    {
        Throwable cause = e.getCause();
        //todo  异常分类处理
        if(cause==null)
        {
            log.error(e.getMessage(), e);
            return;
        }


    }
}
