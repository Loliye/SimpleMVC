package org.mikufans.plugin.shiro;

import java.util.Set;

/**
 * Security 接口
 * <br/>
 * 可在应用中实现该接口，或者在 simple.properties 文件中提供以下基于 SQL 的配置项：
 * <ul>
 * <li>simple.security.jdbc.authc_query：根据用户名获取密码</li>
 * <li>simple.security.jdbc.roles_query：根据用户名获取角色名集合</li>
 * <li>simple.security.jdbc.permissions_query：根据角色名获取权限名集合</li>
 * </ul>
 */
public interface SimpleSecurity
{
    /**
     * 根据用户名获取密码
     *
     * @param username 用户名
     * @return 密码
     */
    String getPassword(String username);

    /**
     * 根据用户名获取角色名集合
     *
     * @param username 用户名
     * @return 角色名集合
     */
    Set<String> getRoleNameSet(String username);

    /**
     * 根据角色名获取权限名集合
     *
     * @param roleName 角色名
     * @return 权限名集合
     */
    Set<String> getPermissionNameSet(String roleName);


    /**
     * 从数据库中获取的对象
     * @param id 主键
     * @param <T>
     * @return
     */
    <T> T getObject(String id);


}
