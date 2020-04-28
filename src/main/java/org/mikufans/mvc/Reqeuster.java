package org.mikufans.mvc;

/**
 * 封装request请求信息
 */
public class Reqeuster
{
    private String requestMethod;
    private String requestPath;

    public Reqeuster(String requestMethod, String requestPath)
    {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod()
    {
        return requestMethod;
    }

    public String getRequestPath()
    {
        return requestPath;
    }
}
