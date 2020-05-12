package org.mikufans.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.mikufans.InstanceFactory;
import org.mikufans.SimpleConstants;
import org.mikufans.core.ConfigHelper;
import org.mikufans.ds.DataSourceFactory;
import org.mikufans.util.ClassUtil;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
public class DatabaseHelper
{

    /**
     * 局部的线程池
     */
    private static final ThreadLocal<Connection> connContainer = new ThreadLocal<>();

    private static final DataSourceFactory dataSourceFactory = InstanceFactory.getDataSourceFactory();

    private static final DataAccessor dataAccessor = InstanceFactory.getDataAccessor();

    private static final String dataBaseType = ConfigHelper.getString(SimpleConstants.DataBaseType);

    public static String getDataBaseType() {return dataBaseType;}

    public static DataSource getDataSource()
    {
        return dataSourceFactory.getDataSource();
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection()
    {
        Connection connection;
        try
        {
            connection = connContainer.get();
            if (connection == null)
            {
                connection = getDataSource().getConnection();
                if (connection != null)
                    connContainer.set(connection);
            }

        } catch (SQLException e)
        {
            log.error("获取数据库连接出错！", e);
            throw new RuntimeException(e);
        }
        return connection;
    }

    /**
     * 提交事务
     */
    public static void commitTransaction()
    {
        Connection connection = getConnection();
        try
        {
            if (connection != null)
            {
                connection.commit();
                connection.close();
            }
        } catch (SQLException e)
        {
            log.error("提交事务出错!", e);
            throw new RuntimeException(e);
        } finally
        {
            connContainer.remove();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction()
    {
        Connection connection = getConnection();
        if (connection != null)
        {
            try
            {
                connection.rollback();
                connection.close();
            } catch (SQLException e)
            {
                log.error("回滚事务出错!", e);
                throw new RuntimeException(e);
            } finally
            {
                connContainer.remove();
            }
        }
    }

    /**
     * 初始化sql脚本
     *
     * @param sqlPath
     */
    public static void initSql(String sqlPath)
    {
        File sqlFile = new File(ClassUtil.getClassPath() + sqlPath);
        try
        {
            List<String> sqlList = FileUtils.readLines(sqlFile);
            for (String sql : sqlList)
                update(sql);

        } catch (IOException e)
        {
            log.error("初始化sql脚本出错!", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据 SQL 语句查询 Entity
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params)
    {
        return dataAccessor.queryEntity(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Entity 列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params)
    {
        return dataAccessor.queryEntityList(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Entity 映射（Field Name => Field Value）
     */
    public static <K, V> Map<K, V> queryEntityMap(Class<V> entityClass, String sql, Object... params)
    {
        return dataAccessor.queryEntityMap(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Array 格式的字段（单条记录）
     */
    public static Object[] queryArray(String sql, Object... params)
    {
        return dataAccessor.queryArray(sql, params);
    }

    /**
     * 根据 SQL 语句查询 Array 格式的字段列表（多条记录）
     */
    public static List<Object[]> queryArrayList(String sql, Object... params)
    {
        return dataAccessor.queryArrayList(sql, params);
    }

    /**
     * 根据 SQL 语句查询 Map 格式的字段（单条记录）
     */
    public static Map<String, Object> queryMap(String sql, Object... params)
    {
        return dataAccessor.queryMap(sql, params);
    }

    /**
     * 根据 SQL 语句查询 Map 格式的字段列表（多条记录）
     */
    public static List<Map<String, Object>> queryMapList(String sql, Object... params)
    {
        return dataAccessor.queryMapList(sql, params);
    }

    /**
     * 根据 SQL 语句查询指定字段（单条记录）
     */
    public static <T> T queryColumn(String sql, Object... params)
    {
        return dataAccessor.queryColumn(sql, params);
    }

    /**
     * 根据 SQL 语句查询指定字段列表（多条记录）
     */
    public static <T> List<T> queryColumnList(String sql, Object... params)
    {
        return dataAccessor.queryColumnList(sql, params);
    }

    /**
     * 根据 SQL 语句查询指定字段映射（多条记录）
     */
    public static <T> Map<T, Map<String, Object>> queryColumnMap(String column, String sql, Object... params)
    {
        return dataAccessor.queryColumnMap(column, sql, params);
    }

    /**
     * 根据 SQL 语句查询记录条数
     */
    public static long queryCount(String sql, Object... params)
    {
        return dataAccessor.queryCount(sql, params);
    }

    /**
     * 执行更新语句（包括：update、insert、delete）
     */
    public static int update(String sql, Object... params)
    {
        return dataAccessor.update(sql, params);
    }

    /**
     * 执行插入语句，返回插入后的主键
     */
    public static Serializable insertReturnPK(String sql, Object... params)
    {
        return dataAccessor.insertReturnPK(sql, params);
    }

}
