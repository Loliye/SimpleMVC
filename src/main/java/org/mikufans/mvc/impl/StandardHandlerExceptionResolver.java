package org.mikufans.mvc.impl;

import org.mikufans.mvc.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StandardHandlerExceptionResolver implements HandlerExceptionResolver
{
    @Override
    public void resolverHandlerException(HttpServletRequest request, HttpServletResponse response, Exception e)
    {

    }
}
