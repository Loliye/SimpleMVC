package org.mikufans.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * handler 异常处理
 */
public interface HandlerExceptionResolver
{
    /**
     * 解析  处理 handler异常
     * @param request
     * @param response
     * @param e
     */
    void resolverHandlerException(HttpServletRequest request, HttpServletResponse response,Exception e);
}
