package org.mikufans.plugin.shiro.realm;

import org.apache.shiro.authc.credential.PasswordMatcher;
import org.mikufans.dao.DatabaseHelper;
import org.mikufans.plugin.shiro.SecurityConfig;

public class SimpleJdbcRealm extends org.apache.shiro.realm.jdbc.JdbcRealm
{
    public SimpleJdbcRealm()
    {
        super.setDataSource(DatabaseHelper.getDataSource());
        super.setAuthenticationQuery(SecurityConfig.getJdbcAuthcQuery());
        super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
        super.setPermissionsQuery(SecurityConfig.getJdbcPermissionsQuery());
        super.setPermissionsLookupEnabled(true);
        super.setCredentialsMatcher(new PasswordMatcher());
    }
}
