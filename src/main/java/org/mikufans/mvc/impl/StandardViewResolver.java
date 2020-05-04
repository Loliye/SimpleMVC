package org.mikufans.mvc.impl;

import org.apache.commons.collections.MapUtils;
import org.mikufans.SimpleConstants;
import org.mikufans.mvc.ViewResolver;
import org.mikufans.mvc.bean.Result;
import org.mikufans.mvc.bean.View;
import org.mikufans.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 视图解析器
 * <p>
 * 返回的数据处理
 */
public class StandardViewResolver implements ViewResolver
{
    @Override
    public void resolveView(HttpServletRequest request, HttpServletResponse response, Object methodResult)
    {
        if (methodResult == null)
            return;
        //返回视图
        if (methodResult instanceof View)
        {
            View view = (View) methodResult;
            //重定向处理
            if (view.isRedirect())
            {
                String path = view.getPath();
                WebUtil.redirectRequest(path, request, response);
            } else
            {
                //转发处理
                String path = SimpleConstants.JSP_PATH + view.getPath();
                Map<String, Object> data = view.getData();
                if (MapUtils.isNotEmpty(data))
                {
                    for (Map.Entry<String, Object> entry : data.entrySet())
                    {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                }
                WebUtil.forwardRequest(path, request, response);
            }
        } else
        {
            Result result = (Result) methodResult;
            //todo 文件的处理
            if (false)
            {

            } else
            {
                //json数据处理
                WebUtil.writeJSON(response, result);
            }

        }

    }
}
