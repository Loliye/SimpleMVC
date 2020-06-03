package com.mikufans.config;

import com.mikufans.bean.User;
import org.mikufans.dao.DatabaseHelper;
import org.mikufans.ioc.annotation.Bean;
import org.mikufans.plugin.shiro.ShiroUser;
import org.mikufans.plugin.shiro.SimpleSecurity;
import org.mikufans.transaction.annotation.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Bean
public class ShiroSecurity implements SimpleSecurity
{
    @Override
    public String getPassword(String username)
    {
        return DatabaseHelper.queryColumn("select password from user where username=?", username);
    }

    @Override
    public Set<String> getRoleNameSet(String username)
    {
        String sql = "select distinct r.role_name from user u, user_role ur, role r where u.id = ur.user_id and r.id = ur.role_id and u.username = ?";
        List<String> list = DatabaseHelper.queryColumnList(sql, username);
        return new LinkedHashSet<String>(list);
    }

    @Override
    public Set<String> getPermissionNameSet(String roleName)
    {
        String sql = "select distinct p.permission_name from role r, role_permission rp, permission p where r.id = rp.role_id and p.id = rp.permission_id and r.role_name = ?";
        List<String> list = DatabaseHelper.queryColumnList(sql, roleName);
        return new LinkedHashSet<String>(list);
    }

    @Override
    public <T> T getObject(String id)
    {
        return (T) DatabaseHelper.queryEntity(User.class,"select * from user where username = ?", id);
    }
}
