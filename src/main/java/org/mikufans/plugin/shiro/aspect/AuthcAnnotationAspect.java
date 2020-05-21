package org.mikufans.plugin.shiro.aspect;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.mikufans.aop.AspectProxy;
import org.mikufans.mvc.exception.AuthzException;
import org.mikufans.plugin.shiro.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


/**
 * 授权注解的切面
 */
public class AuthcAnnotationAspect extends AspectProxy
{

    /**
     * 定义一个基于授权功能的注解类数组
     */
    private static final Class[] annotationClassArray = {
            Authenticated.class, User.class, Guest.class, HasRoles.class, HasPermissions.class
    };

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable
    {
        Annotation annotation = getAnnotation(cls, method);
        if (annotation != null)
        {
            Class<?> type = annotation.annotationType();
            if (type.equals(Authenticated.class))
                handleAuthenticated();
            else if (type.equals(User.class))
                handleUser();
            else if (type.equals(Guest.class))
                handleGuest();
            else if (type.equals(HasRoles.class))
                handleHasRoles((HasRoles) annotation);
            else if (type.equals(HasPermissions.class))
                handleHasPermissions((HasPermissions) annotation);

        }
    }

    private Annotation getAnnotation(Class<?> cls, Method method)
    {
        //遍历方法 类上是否有shiro相关注解信息
        for (Class<? extends Annotation> annotation : annotationClassArray)
        {
            if (method.isAnnotationPresent(annotation))
                return method.getAnnotation(annotation);
            if (cls.isAnnotationPresent(annotation))
                return cls.getAnnotation(annotation);
        }

        return null;
    }

    private void handleAuthenticated()
    {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated())
            throw new AuthzException("当前用户尚未认证");
    }

    private void handleUser()
    {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = currentUser.getPrincipals();
        if (principalCollection == null || principalCollection.isEmpty())
            throw new AuthzException("当前用户尚未登录");
    }

    private void handleGuest()
    {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principals = currentUser.getPrincipals();
        if (principals != null && !principals.isEmpty())
            throw new AuthzException("当前用户不是访客");
    }

    private void handleHasRoles(HasRoles hasRoles)
    {
        String roleName = hasRoles.value();
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.hasRole(roleName))
        {
            throw new AuthzException("当前用户没有指定角色，角色名：" + roleName);
        }
    }

    private void handleHasPermissions(HasPermissions hasPermissions)
    {
        String permissionName = hasPermissions.value();
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isPermitted(permissionName))
        {
            throw new AuthzException("当前用户没有指定权限，权限名：" + permissionName);
        }
    }

}
