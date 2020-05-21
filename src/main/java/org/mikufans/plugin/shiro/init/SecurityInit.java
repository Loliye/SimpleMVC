package org.mikufans.plugin.shiro.init;

import org.apache.shiro.web.env.EnvironmentLoaderListener;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

public class SecurityInit implements ServletContainerInitializer
{
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException
    {
        ctx.addListener(EnvironmentLoaderListener.class);
        FilterRegistration.Dynamic shiroFilter = ctx.addFilter("ShiroFilter", SecurityFilter.class);
        shiroFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
