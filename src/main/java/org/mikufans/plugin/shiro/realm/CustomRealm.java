package org.mikufans.plugin.shiro.realm;

import com.mikufans.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.mikufans.plugin.shiro.CredentialsMatcher;
import org.mikufans.plugin.shiro.ShiroUser;
import org.mikufans.plugin.shiro.SimpleSecurity;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class CustomRealm extends AuthorizingRealm
{

    private final SimpleSecurity security;

    public CustomRealm(SimpleSecurity security)
    {
        //        this.security=new SecurityConfig();
        this.security = security;
        super.setName("custom");
        super.setCredentialsMatcher(new CredentialsMatcher());
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        if (principals == null)
            throw new AuthenticationException("参数 principals 非法!");

        //        String username = (String) super.getAvailablePrincipal(principals);
        ShiroUser user = (ShiroUser) principals.getPrimaryPrincipal();
        Set<String> roleNameSet = security.getRoleNameSet(user.getUsername());

        Set<String> permNameSet = new HashSet<>();
        if (roleNameSet != null && roleNameSet.size() > 0)
        {
            for (String roleName : roleNameSet)
            {
                Set<String> currentPerm = security.getPermissionNameSet(roleName);
                permNameSet.addAll(currentPerm);
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleNameSet);
        info.setStringPermissions(permNameSet);
        return info;
    }


    /**
     * 登陆认证
     * todo  待优化
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        if (token == null)
            throw new AuthenticationException("参数 token 非法!");
        String loginName = ((UsernamePasswordToken) token).getUsername();
        String password = String.valueOf(((UsernamePasswordToken) token).getPassword());
        String sqlPassword = security.getPassword(loginName);
        System.out.println("password: " + password);
        System.out.println("sqlPassword: " + sqlPassword);

        //        $shiro1$SHA-256$500000$vEgQXP9//FEW/4AKvslZkQ==$5G2JPj8umTRl9rf7zc7t9V2ke7a2sgNPGIlqJyqh/EE=
        Object object = null;
        if (sqlPassword.equals(password))
        {
            object = security.getObject(loginName);
        } else return null;

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(object, password, getName());
        return info;
    }
}
