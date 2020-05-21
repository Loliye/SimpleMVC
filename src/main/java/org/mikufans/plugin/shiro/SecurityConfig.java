package org.mikufans.plugin.shiro;

import lombok.extern.slf4j.Slf4j;
import org.mikufans.core.ConfigHelper;

@Slf4j
public final class SecurityConfig
{
    public static String getRealms()
    {
        return ConfigHelper.getString(SecurityConstant.REALMS);
    }

    public static SimpleSecurity getSimpleSecurity()
    {
        String clsName = ConfigHelper.getString(SecurityConstant.SIMPLE_SECURITY);
        Class<?> cls = null;
        try
        {
            cls = Class.forName(clsName);
        } catch (ClassNotFoundException e)
        {
            log.error("无法从 " + SecurityConstant.SIMPLE_SECURITY + " 配置中找到对应的类", e);
        }
        SimpleSecurity smartSecurity = null;
        if (cls != null)
        {
            try
            {
                smartSecurity = (SimpleSecurity) cls.newInstance();
            } catch (Exception e)
            {
                log.error(SimpleSecurity.class.getSimpleName() + " 实例化异常", e);
            }
        }
        return smartSecurity;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        System.out.println(getSimpleSecurity());
    }


    public static String getJdbcAuthcQuery()
    {
        return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
    }

    public static String getJdbcRolesQuery()
    {
        return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
    }

    public static String getJdbcPermissionsQuery()
    {
        return ConfigHelper.getString(SecurityConstant.JDBC_PERMISSIONS_QUERY);
    }

    public static String getAdUrl()
    {
        return ConfigHelper.getString(SecurityConstant.AD_URL);
    }

    public static String getAdSystemUsername()
    {
        return ConfigHelper.getString(SecurityConstant.AD_SYSTEM_USERNAME);
    }

    public static String getAdSystemPassword()
    {
        return ConfigHelper.getString(SecurityConstant.AD_SYSTEM_PASSWORD);
    }

    public static String getAdSearchBase()
    {
        return ConfigHelper.getString(SecurityConstant.AD_SEARCH_BASE);
    }

    public static boolean isCacheable()
    {
        return ConfigHelper.getBoolean(SecurityConstant.CACHEABLE);
    }
}
