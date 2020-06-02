package org.mikufans.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * handler 调用器
 */
public interface HandlerInvoker
{
    /**
     * 处理具体的业务方法
     *
     * @param request
     * @param response
     * @param handler
     */
    void invokerHandler(HttpServletRequest request, HttpServletResponse response, Handler handler)  throws Exception;
}
