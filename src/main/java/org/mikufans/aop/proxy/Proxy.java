package org.mikufans.aop.proxy;

/**
 * 代理接口
 */
public interface Proxy
{
    /**
     * 执行链代理
     * @param proxyChain
     * @return
     */
    Object doProxy(ProxyChain proxyChain);
}
