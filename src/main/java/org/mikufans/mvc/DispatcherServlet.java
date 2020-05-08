package org.mikufans.mvc;

import lombok.extern.slf4j.Slf4j;
import org.mikufans.InstanceFactory;
import org.mikufans.SimpleConstants;
import org.mikufans.util.WebUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前端请求的分发器
 */
@WebServlet(urlPatterns = "/", loadOnStartup = 0)
@Slf4j
public class DispatcherServlet extends HttpServlet
{

    private HandlerMapping handlerMapping = InstanceFactory.getHandlerMapping();
    private HandlerInvoker handlerInvoker = InstanceFactory.getHandlerInvoker();
    private HandlerExceptionResolver handlerExceptionResolver = InstanceFactory.getHandlerExceptionResolver();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding(SimpleConstants.UTF_8);
        String requestMethod = req.getMethod();
        String requestPath = WebUtil.getRequestPath(req);
        log.debug("[simple] {}:{}", requestMethod, requestPath);

        // 空 定向到首页
        if (requestPath.equals("/") || requestPath.equals(""))
        {
            WebUtil.redirectRequest(SimpleConstants.HOME_PAGE, req, resp);
            return;
        }

        if (requestPath.endsWith("/"))
            requestPath = requestPath.substring(0, requestPath.length() - 1);
        //获取handler
        Handler handler = handlerMapping.getHandler(requestMethod, requestPath);
        if (handler == null)
        {
            WebUtil.sendError(HttpServletResponse.SC_NOT_FOUND, "the page is not found", resp);
            return;
        }

        //初始化dataContext
        DataContext.init(req, resp);
        try
        {
            //调用handler中方法
            handlerInvoker.invokerHandler(req, resp, handler);
        } catch (Exception e)
        {
            //发生异常返回  对应的异常页面
            handlerExceptionResolver.resolverHandlerException(req, resp, e);
        } finally
        {
            DataContext.destory();
        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ServletContext servletContext = config.getServletContext();
        UploadHelper.init(servletContext);
    }
}
