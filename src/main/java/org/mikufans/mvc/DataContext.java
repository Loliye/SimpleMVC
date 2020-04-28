package org.mikufans.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据上下文
 */
public class DataContext
{
    /**
     * 各个线程的data
     */
    private static final ThreadLocal<DataContext> dataContextContainer=new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    public static void init(HttpServletRequest request,HttpServletResponse response)
    {
        DataContext dataContext=new DataContext();
        dataContext.request=request;
        dataContext.response=response;
        dataContextContainer.set(dataContext);
    }


}
