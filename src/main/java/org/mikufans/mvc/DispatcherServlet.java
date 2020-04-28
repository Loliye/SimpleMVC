package org.mikufans.mvc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet
{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        super.service(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }
}
