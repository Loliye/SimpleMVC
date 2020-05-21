package org.mikufans.plugin.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import org.apache.commons.lang.StringUtils;
import org.mikufans.SimpleConstants;
import org.mikufans.core.ConfigHelper;
import org.mikufans.plugin.WebPlugin;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class DruidPlugin extends WebPlugin
{

    private static final String DRUID_STAT_URL = "/druid/*";

    @Override
    public void register(ServletContext servletContext)
    {
        // 添加 StatViewServlet
        ServletRegistration.Dynamic druidServlet = servletContext.addServlet("DruidStatView", StatViewServlet.class);
        // 从配置文件中获取该 Servlet 的 URL 路径
        String druidStatUrl = SimpleConstants.DRUID_URL;
        // 若该 URL 路径不存在，则使用默认值
        if (StringUtils.isEmpty(druidStatUrl))
        {
            druidStatUrl = DRUID_STAT_URL;
        }
        // 向 Servlet 中添加 URL 路径
        druidServlet.addMapping(druidStatUrl);
    }
}
