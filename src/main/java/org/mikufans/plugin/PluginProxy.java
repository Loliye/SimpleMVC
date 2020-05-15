package org.mikufans.plugin;

import org.mikufans.aop.proxy.Proxy;

import java.util.List;

/**
 * 插件代理
 */
public abstract class PluginProxy implements Proxy
{
    public abstract List<Class<?>> getTargetClassList();
}
