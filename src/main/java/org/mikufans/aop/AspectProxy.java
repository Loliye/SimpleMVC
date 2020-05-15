package org.mikufans.aop;

import lombok.extern.slf4j.Slf4j;
import org.mikufans.aop.proxy.Proxy;
import org.mikufans.aop.proxy.ProxyChain;

import java.lang.reflect.Method;

/**
 * 切面代理
 */
@Slf4j
public abstract class AspectProxy implements Proxy
{
    @Override
    public Object doProxy(ProxyChain proxyChain)
    {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try
        {
            if (this.intercept(cls, method, params))
            {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            } else result = proxyChain.doProxyChain();
        } catch (Throwable throwable)
        {
            log.error("AOP 异常", throwable);
            error(cls, method, params, throwable);
        } finally
        {
            end();
        }
        return result;
    }

    public void begin()
    {
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable
    {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable
    {
    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable
    {
    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e)
    {
    }

    public void end()
    {
    }
}
