package org.mikufans.plugin.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

@Slf4j
public class SecurityHelper
{
    private static final PasswordService passwordService = new DefaultPasswordService();

    /**
     * 登陆
     */
    public static void login(String username, String password, boolean isRemeberMe)
    {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null)
        {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(isRemeberMe);
            currentUser.login(token);

        }
    }

    /**
     * 注销
     */
    public static void logout()
    {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null)
        {
            currentUser.logout();
        }
    }

    /**
     * 加密
     */
    public static String encrypt(String plaintext)
    {
        return passwordService.encryptPassword(plaintext);
    }


    public static String encryptPassword(String username, String password, String salt)
    {
        return new Md5Hash(username + password + salt).toHex();
    }

    @Test
    public void test()
    {
        System.out.println(encryptPassword("admin",null,"111111"));
    }

}
