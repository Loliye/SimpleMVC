package org.mikufans.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * handler 调用器
 */
public interface HandlerInvoker
{
    void invokerHandler(HttpServletRequest request, HttpServletResponse response,Handler handler);
}
