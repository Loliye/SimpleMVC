package org.mikufans.mvc;

/**
 * 映射对应的handler
 */
public interface HandlerMapping
{
    /**
     * 获取对应的handler
     * @param currentRequestMethod
     * @param currentRequestPath
     * @return
     */
    Handler getHandler(String currentRequestMethod,String currentRequestPath);
}
