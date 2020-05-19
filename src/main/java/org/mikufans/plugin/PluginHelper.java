package org.mikufans.plugin;

import org.junit.runners.model.InitializationError;
import org.mikufans.InstanceFactory;
import org.mikufans.SimpleConstants;
import org.mikufans.core.ClassScanner;
import org.mikufans.core.exception.InitError;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class PluginHelper
{
    private static final List<Plugin> pluginList = new ArrayList<>();

    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    static
    {
        try
        {
            // 获取并遍历所有的插件类（实现了 Plugin 接口的类）
            List<Class<?>> pluginClassList = classScanner.getClassListBySuper(SimpleConstants.PLUGIN_PACKAGE, Plugin.class);
            for (Class<?> pluginClass : pluginClassList)
            {
                // 创建插件实例
                Plugin plugin = (Plugin) pluginClass.newInstance();
                // 调用初始化方法
                plugin.init();
                // 将插件实例添加到插件列表中
                pluginList.add(plugin);
            }
        } catch (Exception e)
        {
            throw new InitError("初始化PluginHelper出错！", e);
        }
    }

    /**
     * 获取所有插件
     */
    public static List<Plugin> getPluginList()
    {
        return pluginList;
    }
}
