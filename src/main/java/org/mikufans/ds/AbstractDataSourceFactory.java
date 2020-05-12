package org.mikufans.ds;

import org.mikufans.SimpleConstants;
import org.mikufans.core.ConfigHelper;

import javax.sql.DataSource;

/**
 * 抽象数据源工厂
 * @param <T>
 */
public abstract class AbstractDataSourceFactory<T extends DataSource> implements DataSourceFactory
{
    protected final String driver = ConfigHelper.getString(SimpleConstants.DRIVER);
    protected final String url = ConfigHelper.getString(SimpleConstants.URL);
    protected final String username = ConfigHelper.getString(SimpleConstants.username);
    protected final String password = ConfigHelper.getString(SimpleConstants.password);

    @Override
    public final T getDataSource()
    {
        // 创建数据源对象
        T dataSource = createDataSource();
        // 设置基础属性
        setDriver(dataSource, driver);
        setUrl(dataSource, url);
        setUsername(dataSource, username);
        setPassword(dataSource, password);
        // 设置高级属性
        setAdvancedConfig(dataSource);
        return dataSource;
    }

    public abstract T createDataSource();

    public abstract void setDriver(T dataSource, String driver);

    public abstract void setUrl(T dataSource, String url);

    public abstract void setUsername(T dataSource, String username);

    public abstract void setPassword(T dataSource, String password);

    public abstract void setAdvancedConfig(T dataSource);
}
