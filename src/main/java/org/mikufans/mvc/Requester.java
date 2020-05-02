package org.mikufans.mvc;

/**
 * 封装request请求信息
 */
public class Requester
{
    private String requestMethod;
    private String requestPath;

    public Requester(String requestMethod, String requestPath)
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
