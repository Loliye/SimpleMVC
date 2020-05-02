package org.mikufans.mvc;

import com.sun.deploy.net.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 视图解析器
 */
public interface ViewResolver
{
    /**
     * 解析视图
     * @param request
     * @param response
     * @param methodResult
     */
    void resolvView(HttpServletRequest request, HttpServletResponse response,Object methodResult);
}
