package org.mikufans.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mikufans.SimpleConstants;
import org.mikufans.orm.EntityHelper;
import org.mikufans.util.MapUtil;
import org.mikufans.util.PropertiesUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * sql语句生成
 */
@Slf4j
public class SqlHelper
{
    private static final Properties sqlProps = PropertiesUtil.loadProps(SimpleConstants.SQL_PROPS);


    /**
     * 获取sql文件中的语句
     *
     * @param key
     * @return
     */
    public static String getSql(String key)
    {
        String string;
        if (sqlProps.containsKey(key))
            string = sqlProps.getProperty(key);
        else throw new RuntimeException("无法在 " + SimpleConstants.SQL_PROPS + " 文件中获取属性：" + key);
        return string;
    }

    /**
     * 生成select语句
     *
     * @param entityClass
     * @param condition
     * @param sort
     * @return
     */
    public static String generateSelectSql(Class<?> entityClass, String condition, String sort)
    {
        StringBuilder sql = new StringBuilder("select * from ").append(getTable(entityClass));
        sql.append(generateWhere(condition));
        sql.append(generateOrder(sort));
        return sql.toString();
    }

    /**
     * 生成insert语句
     *
     * @param entityClass
     * @param fieldNames
     * @return
     */
    public static String generateInsertSql(Class<?> entityClass, Collection<String> fieldNames)
    {
        StringBuilder sql = new StringBuilder("insert into").append(getTable(entityClass));
        if (CollectionUtils.isNotEmpty(fieldNames))
        {
            int i = 0;
            StringBuilder columns = new StringBuilder(" ");
            StringBuilder values = new StringBuilder(" values ");
            for (String fieldName : fieldNames)
            {
                String columnName = EntityHelper.getColumnName(entityClass, fieldName);
                if (i == 0)
                {
                    columns.append("(").append(columnName);
                    values.append("(?");
                } else
                {
                    columns.append(", ").append(columnName);
                    values.append(",?");
                }
                if (i == fieldNames.size() - 1)
                {
                    columns.append(")");
                    values.append(")");
                }
                i++;
            }
            sql.append(columns).append(values);
        }
        return sql.toString();
    }

    /**
     * 生成delete语
     *
     * @param entityClass
     * @param condition
     * @return
     */
    public static String generateDeleteSql(Class<?> entityClass, String condition)
    {
        StringBuilder sql = new StringBuilder("delete from ").append(getTable(entityClass));
        sql.append(generateWhere(condition));
        return sql.toString();
    }

    /**
     * 生成update语句
     *
     * @param entityClass
     * @param fieldMap
     * @param condition
     * @return
     */
    public static String generageUpdateSql(Class<?> entityClass, Map<String, Object> fieldMap, String condition)
    {
        StringBuilder sql = new StringBuilder("update ").append(getTable(entityClass));
        if (MapUtil.isNotEmpty(fieldMap))
        {
            sql.append(" set");
            int i = 0;
            for (Map.Entry<String, Object> entry : fieldMap.entrySet())
            {
                String fieldName = entry.getKey();
                String columnName = EntityHelper.getColumnName(entityClass, fieldName);
                if (i == 0)
                    sql.append(columnName).append(" = ?");
                else sql.append(", ").append(columnName).append(" = ?");
                i++;
            }
        }
        sql.append(generateWhere(condition));
        return sql.toString();
    }

    public static String generateSelectSqlForCount(Class<?> entityClass, String condition)
    {
        StringBuilder sql = new StringBuilder("select count(*) from ").append(getTable(entityClass));
        sql.append(generateWhere(condition));
        return sql.toString();
    }

    /**
     * 分页 select语句
     *
     * @param pageNumber
     * @param pageSize
     * @param entityClass
     * @param condition
     * @param sort
     * @return
     */
    public static String generateSelectSqlForPager(int pageNumber, int pageSize, Class<?> entityClass, String condition, String sort)
    {
        StringBuilder sql = new StringBuilder();
        String table = getTable(entityClass);
        String where = generateWhere(condition);
        String order = generateOrder(sort);
        String dbType = DatabaseHelper.getDataBaseType();
        //todo  处理不同数据库类型
        if (dbType.equalsIgnoreCase("mysql"))
        {
            int pageStart = (pageNumber - 1) * pageSize;
            appendSqlForMysql(sql, table, where, order, pageStart, pageSize);
        }
        return sql.toString();

    }

    private static String getTable(Class<?> entityClass)
    {
        return EntityHelper.getTableName(entityClass);
    }

    private static String generateWhere(String condition)
    {
        String where = "";
        if (StringUtils.isNotEmpty(condition))
            where += " where " + condition;
        return where;
    }

    private static String generateOrder(String sort)
    {
        String order = "";
        if (StringUtils.isNotEmpty(sort))
            order += " order by " + sort;
        return order;
    }

    /**
     * select * from 表名 where 条件 order by 排序 limit 开始位置, 结束位置
     *
     * @param sql
     * @param table
     * @param where
     * @param order
     * @param pageStart
     * @param pageEnd
     */
    private static void appendSqlForMysql(StringBuilder sql, String table, String where,
                                          String order, int pageStart, int pageEnd)
    {
        sql.append("select * from ").append(table);
        sql.append(where).append(order);
        sql.append(" limit ").append(pageStart).append(pageEnd);
    }
}
