package org.mikufans.orm;

import org.apache.commons.lang.ArrayUtils;
import org.mikufans.dao.DatabaseHelper;
import org.mikufans.dao.SqlHelper;
import org.mikufans.util.MapUtil;
import org.mikufans.util.ObjectUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供数据库相关的操作
 * 增删改查
 */
public class DataSet
{
    /**
     * 查询单条数据，并转化为相应实体类对象
     */
    public static <T> T select(Class<T> entityClass, String condition, Object... params)
    {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, "");
        return DatabaseHelper.queryEntity(entityClass, sql, params);
    }

    /**
     * 根据条件查询多条数据
     *
     * @param entityClass 返回对象类型
     * @param condition   条件
     * @param sort        排序
     * @param params      参数
     * @param <T>
     * @return
     */
    public static <T> List<T> selectListWithConditionAndSort(Class<T> entityClass, String condition, String sort, Object... params)
    {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, sort);
        return DatabaseHelper.queryEntityList(entityClass, sql, params);
    }

    /**
     * 查询数据行数
     *
     * @param entityClass
     * @param condition
     * @param params
     * @return
     */
    public static long selectCount(Class<?> entityClass, String condition, Object... params)
    {
        String sql = SqlHelper.generateSelectSqlForCount(entityClass, condition);
        return DatabaseHelper.queryCount(sql, params);
    }

    /**
     * 分页查询 返回list集
     *
     * @param pageNumber
     * @param pageSize
     * @param entityClass
     * @param condition
     * @param sort
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> selectListForPager(int pageNumber, int pageSize, Class<T> entityClass, String condition,
                                                 String sort, Object... params)
    {
        String sql = SqlHelper.generateSelectSqlForPager(pageNumber, pageSize, entityClass, condition, sort);
        return DatabaseHelper.queryEntityList(entityClass, sql, params);
    }

    /**
     * 查询多条数据  并转化为map
     */
    public static <PK, T> Map<PK, T> selectMapWithPK(Class<T> entityClass, String pkName, String condition, String sort, Object... params)
    {
        Map<PK, T> map = new LinkedHashMap<>();
        List<T> list = selectListWithConditionAndSort(entityClass, condition, sort, params);
        for (T obj : list)
        {
            PK pk = (PK) ObjectUtil.getFieldValue(obj, pkName);
            map.put(pk, obj);
        }
        return map;
    }

    /**
     * 根据列名查询单条数据，转化为对应的对象
     *
     * @param entityClass
     * @param columnName
     * @param condition
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T selectColumn(Class<?> entityClass, String columnName, String condition, Object... params)
    {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, "");
        sql = sql.replace("*", columnName);
        return DatabaseHelper.queryColumn(sql, params);
    }

    public static <T> List<T> selectColumnList(Class<?> entityClass, String columnName, String condition, String sort, Object... params)
    {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, sort);
        sql = sql.replace("*", columnName);
        return DatabaseHelper.queryColumnList(sql, params);
    }

    /**
     * 插入一条数据
     * 参数为空不执行  返回空
     *
     * @param entityClass
     * @param fieldMap
     * @return
     */
    public static boolean insert(Class<?> entityClass, Map<String, Object> fieldMap)
    {
        if (MapUtil.isNotEmpty(fieldMap))
            return true;

        String sql = SqlHelper.generateInsertSql(entityClass, fieldMap.keySet());
        int rows = DatabaseHelper.update(sql, fieldMap.values().toArray());
        return rows > 0;
    }

    /**
     * 插入个实体对象
     *
     * @param object
     * @return
     */
    public static boolean insert(Object object)
    {
        if (object == null)
            throw new IllegalArgumentException();
        Class<?> entityClass = object.getClass();
        Map<String, Object> fieldMap = ObjectUtil.getFieldMap(object);
        return insert(entityClass, fieldMap);
    }

    /**
     * 更新数据
     *
     * @param entityClass
     * @param fieldMap
     * @param condition
     * @param params
     * @return
     */
    public static boolean update(Class<?> entityClass, Map<String, Object> fieldMap, String condition, Object... params)
    {
        if (MapUtil.isEmpty(fieldMap))
            return true;

        String sql = SqlHelper.generageUpdateSql(entityClass, fieldMap, condition);
        int rows = DatabaseHelper.update(sql, ArrayUtils.add(fieldMap.values().toArray(), params));
        return rows > 0;
    }

    /**
     * 更新一个实体对象
     */
    public static boolean update(Object entityObject, String pkName)
    {
        if (entityObject == null)
            throw new IllegalArgumentException();
        Class<?> entityClass = entityObject.getClass();
        Map<String, Object> fieldMap = ObjectUtil.getFieldMap(entityObject);
        String condition = pkName + " = ?";
        Object[] params = {ObjectUtil.getFieldValue(entityObject, pkName)};
        return update(entityClass, fieldMap, condition, params);
    }


}
