package com.mikufans.config;

import org.mikufans.plugin.shiro.SimpleSecurity;

import java.util.Set;

public class SecurityConfig implements SimpleSecurity
{
    @Override
    public String getPassword(String username)
    {
        return null;
    }

    @Override
    public Set<String> getRoleNameSet(String username)
    {
        return null;
    }

    @Override
    public Set<String> getPermissionNameSet(String roleName)
    {
        return null;
    }
}
