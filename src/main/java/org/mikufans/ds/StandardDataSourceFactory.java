package org.mikufans.ds;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class StandardDataSourceFactory extends AbstractDataSourceFactory<BasicDataSource>
{
    @Override
    public BasicDataSource createDataSource()
    {
        return new BasicDataSource();
    }

    @Override
    public void setDriver(BasicDataSource dataSource, String driver)
    {
        dataSource.setDriverClassName(driver);
    }

    @Override
    public void setUrl(BasicDataSource dataSource, String url)
    {
        dataSource.setUrl(url);
    }

    @Override
    public void setUsername(BasicDataSource dataSource, String username)
    {
        dataSource.setUsername(username);
    }

    @Override
    public void setPassword(BasicDataSource dataSource, String password)
    {
        dataSource.setPassword(password);
    }

    @Override
    public void setAdvancedConfig(BasicDataSource dataSource)
    {
        // 解决 java.sql.SQLException: Already closed. 的问题（连接池会自动关闭长时间没有使用的连接）
        dataSource.setValidationQuery("select 1 from dual");
    }
}
