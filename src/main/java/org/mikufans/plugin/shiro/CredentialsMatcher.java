package org.mikufans.plugin.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CredentialsMatcher extends SimpleCredentialsMatcher
{
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
    {
        return true;
    }
}
