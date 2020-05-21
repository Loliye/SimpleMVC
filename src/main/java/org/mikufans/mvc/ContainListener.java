package org.mikufans.mvc;

import org.apache.commons.lang.StringUtils;
import org.mikufans.HelperLoader;
import org.mikufans.SimpleConstants;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.util.Map;

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

//        Map<String, ? extends FilterRegistration> filterRegistrations = servletContext.getFilterRegistrations();
//        for (Map.Entry<String,? extends FilterRegistration> entry:filterRegistrations.entrySet())
//        {
//            System.out.println(entry.getKey()+" : "+entry.getValue());
//        }
    }

    private void registerDefaultServlet(ServletContext servletContext)
    {
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping("/index.html");
        defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping("*.css", "*.js", "*.ico", "*.png", "*.jpg");
        String path = SimpleConstants.STATIC_PATH;
        if (StringUtils.isNotEmpty(path))
        {
            defaultServlet.addMapping(path + "*");
        }
    }

    private void registerJspServlet(ServletContext context)
    {
        ServletRegistration jspServlet = context.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp", "*.jsp");
        String path = SimpleConstants.JSP_PATH;
        if (StringUtils.isNotEmpty(path))
        {
            jspServlet.addMapping(path + "*");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
//        Map<String, ? extends FilterRegistration> filterRegistrations = sce.getServletContext().getFilterRegistrations();
//        for (Map.Entry<String,? extends FilterRegistration> entry:filterRegistrations.entrySet())
//        {
//            System.out.println(entry.getKey()+" : "+entry.getValue());
//        }
    }
}
