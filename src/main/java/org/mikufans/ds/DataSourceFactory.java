package org.mikufans.ds;

import javax.sql.DataSource;

/**
 * 数据源工厂
 */
public interface DataSourceFactory
{
    /**
     * 获取数据源
     * @return
     */
    DataSource getDataSource();
}
