package org.mikufans.plugin.shiro;

public interface SecurityConstant
{
    String REALMS = "shiro.plugin.security.realms";
    String REALMS_JDBC = "jdbc";
    String REALMS_AD = "ad";
    String REALMS_CUSTOM = "custom";

    String SIMPLE_SECURITY = "shiro.plugin.security.custom.class";

    String JDBC_AUTHC_QUERY = "shiro.plugin.security.jdbc.authc_query";
    String JDBC_ROLES_QUERY = "shiro.plugin.security.jdbc.roles_query";
    String JDBC_PERMISSIONS_QUERY = "shiro.plugin.security.jdbc.permissions_query";

    String AD_URL = "shiro.plugin.security.ad.url";
    String AD_SYSTEM_USERNAME = "shiro.plugin.security.ad.system_username";
    String AD_SYSTEM_PASSWORD = "shiro.plugin.security.ad.system_password";
    String AD_SEARCH_BASE = "shiro.plugin.security.ad.search_base";

    String CACHEABLE = "shiro.plugin.security.cacheable";
}
