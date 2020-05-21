package org.mikufans.mvc;

import org.apache.commons.lang.StringUtils;
import org.mikufans.HelperLoader;
import org.mikufans.SimpleConstants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContainListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        ServletContext servletContext = sce.getServletContext();
        HelperLoader.init();
        //defaultServlet  映射所有静态资源
        registerDefaultServlet(servletContext);
        //jspServlet 映射所有jsp请求
        registerJspServlet(servletContext);
    }

    private void registerDefaultServlet(ServletContext servletContext)
    {
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping("/index.html");
        defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping("*.css","*.js","*.ico","*.png","*.jpg");
        String path = SimpleConstants.WWW_PATH;
        if (StringUtils.isNotEmpty(path))
        {
            defaultServlet.addMapping(path + "*");
        }
    }

    private void registerJspServlet(ServletContext context)
    {
        ServletRegistration jspServlet = context.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp","*.jsp");
        String path = SimpleConstants.JSP_PATH;
        if (StringUtils.isNotEmpty(path))
        {
            jspServlet.addMapping(path + "*");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {

    }
}
