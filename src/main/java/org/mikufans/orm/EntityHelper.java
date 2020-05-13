package org.mikufans.orm;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.mikufans.core.ClassHelper;
import org.mikufans.orm.annotation.Column;
import org.mikufans.orm.annotation.Entity;
import org.mikufans.orm.annotation.Table;
import org.mikufans.util.MapUtil;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntityHelper
{
    //实体类->数据库表名
    private static final Map<Class<?>, String> entityClassTableNameMap = new HashMap<>();

    //实体类->(class字段名，表中列名)
    private static final Map<Class<?>, Map<String, String>> entityClassFieldMap = new HashMap<>();

    static
    {
        //扫描包 加入map中
        List<Class<?>> entityClassList = ClassHelper.getClassListByAnnotation(Entity.class);
        for (Class<?> entityClass : entityClassList)
        {
            initEntityNameMap(entityClass);
            initEntityFieldMapMap(entityClass);
        }
    }

    /**
     * 将实体类 和数据库表名对应上
     *
     * @param entityClass bean:驼峰   数据库表名：下划线形式
     */
    private static void initEntityNameMap(Class<?> entityClass)
    {
        String tableName;
        if (entityClass.isAnnotationPresent(Table.class))
        {
            tableName = entityClass.getAnnotation(Table.class).value();
        } else tableName = HumpToUnderline(entityClass.getSimpleName());
        entityClassTableNameMap.put(entityClass, tableName);
    }

    /**
     * 处理实体类中的所有字段
     *
     * @param entityClass
     */
    private static void initEntityFieldMapMap(Class<?> entityClass)
    {
        //不含有父类的字段
        Field[] fields = entityClass.getDeclaredFields();
        if (ArrayUtils.isEmpty(fields))
            return;

        Map<String, String> fieldMap = new HashMap<>();
        for (Field field : fields)
        {
            String fieldName = field.getName();
            String columnName;
            if (field.isAnnotationPresent(Column.class))
                columnName = field.getAnnotation(Column.class).value();
            else columnName = HumpToUnderline(fieldName);
            fieldMap.put(fieldName, columnName);
        }
        entityClassFieldMap.put(entityClass, fieldMap);
    }


    public static String getTableName(Class<?> entityClass)
    {
        return entityClassTableNameMap.get(entityClass);
    }

    public static Map<String, String> getFieldMap(Class<?> entityClass)
    {
        return entityClassFieldMap.get(entityClass);
    }

    public static Map<String, String> getColumnMap(Class<?> entityClass)
    {
        return MapUtil.invert(getFieldMap(entityClass));
    }

    public static String getColumnName(Class<?> entityClass, String fieldName)
    {
        String name = getFieldMap(entityClass).get(fieldName);
        return StringUtils.isNotEmpty(name) ? name : fieldName;
    }

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    private static String HumpToUnderline(String str)
    {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuilder builder = new StringBuilder(str);


        for (int i = 0; matcher.find(); i++)
        {
            builder.replace(matcher.start() + i, matcher.end() + i, "_" + matcher.group().toLowerCase());
            //            System.out.println(matcher.start() + "  " + matcher.end() + "  " + matcher.group());
        }
        if (builder.charAt(0) == '_')
            builder.deleteCharAt(0);
        return builder.toString();
    }

    @Test
    public void test()
    {
        System.out.println(HumpToUnderline("TableStu"));
    }

}
