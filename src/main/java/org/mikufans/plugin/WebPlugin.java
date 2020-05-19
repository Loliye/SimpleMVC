package org.mikufans.plugin;


import javax.servlet.ServletContext;

public abstract class WebPlugin implements Plugin
{

    @Override
    public void init()
    {
    }

    @Override
    public void destroy()
    {
    }

    public abstract void register(ServletContext servletContext);
}
