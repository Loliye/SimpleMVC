package org.mikufans.plugin.shiro.realm;

import javafx.scene.effect.SepiaTone;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.mikufans.plugin.shiro.SimpleSecurity;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm
{

    private final SimpleSecurity security;

    public CustomRealm(SimpleSecurity security)
    {
        this.security = security;
        super.setName("custom");
        super.setCredentialsMatcher(new PasswordMatcher());
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

        String username = (String) super.getAvailablePrincipal(principals);
        Set<String> roleNameSet = security.getRoleNameSet(username);

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
     * 认证
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
        String username = ((UsernamePasswordToken) token).getUsername();
        String password = security.getPassword(username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        info.setPrincipals(new SimplePrincipalCollection(username, super.getName()));
        info.setCredentials(password);
        return info;
    }
}
