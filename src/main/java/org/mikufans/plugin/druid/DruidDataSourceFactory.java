package org.mikufans.plugin.druid;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.mikufans.ds.AbstractDataSourceFactory;

import java.sql.SQLException;

@Slf4j
public class DruidDataSourceFactory extends AbstractDataSourceFactory<DruidDataSource>
{
    @Override
    public DruidDataSource createDataSource()
    {
        return new DruidDataSource();
    }

    @Override
    public void setDriver(DruidDataSource ds, String driver)
    {
        ds.setDriverClassName(driver);
    }

    @Override
    public void setUrl(DruidDataSource ds, String url)
    {
        ds.setUrl(url);
    }

    @Override
    public void setUsername(DruidDataSource ds, String username)
    {
        ds.setUsername(username);
    }

    @Override
    public void setPassword(DruidDataSource ds, String password)
    {
        ds.setPassword(password);
    }

    @Override
    public void setAdvancedConfig(DruidDataSource ds)
    {
        try
        {
            ds.setFilters("stat");
        } catch (SQLException e)
        {
            log.error("错误：设置 Stat Filter 失败！", e);
        }
    }
}
