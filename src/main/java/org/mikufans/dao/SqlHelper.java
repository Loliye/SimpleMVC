package org.mikufans.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mikufans.SimpleConstants;
import org.mikufans.orm.EntityHelper;
import org.mikufans.util.PropertiesUtil;

import java.util.Collection;
import java.util.Properties;

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
        if(CollectionUtils.isNotEmpty(fieldNames))
        {

        }
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
}
