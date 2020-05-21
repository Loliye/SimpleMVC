package org.mikufans.plugin.shiro.init;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.activedirectory.ActiveDirectoryRealm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.mikufans.plugin.shiro.SecurityConfig;
import org.mikufans.plugin.shiro.SecurityConstant;
import org.mikufans.plugin.shiro.SimpleSecurity;
import org.mikufans.plugin.shiro.realm.CustomRealm;
import org.mikufans.plugin.shiro.realm.SimpleJdbcRealm;
import sun.plugin2.message.transport.SerializingTransport;

import java.util.LinkedHashSet;
import java.util.Set;

public class SecurityFilter extends ShiroFilter
{
    @Override
    public void init() throws Exception
    {
        super.init();
        WebSecurityManager manager = super.getSecurityManager();
        setRealms(manager);
        setCache(manager);
    }

    private void setRealms(WebSecurityManager webSecurityManager)
    {
        String securityRealms = SecurityConfig.getRealms();
        if (securityRealms != null)
        {
            String[] securityRealmArray = securityRealms.split(",");
            if (securityRealmArray.length > 0)
            {
                Set<Realm> realms = new LinkedHashSet<>();
                for (String securityRealm : securityRealmArray)
                {
                    if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC))
                        addJdbcRealm(realms);
                    else if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_AD))
                        addAdRealm(realms);
                    else if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM))
                        addCustomRealm(realms);
                }
                RealmSecurityManager realmSecurityManager = (RealmSecurityManager) webSecurityManager;
                realmSecurityManager.setRealms(realms);
            }
        }
    }

    private void addJdbcRealm(Set<Realm> realmSet)
    {
        SimpleJdbcRealm realm = new SimpleJdbcRealm();
        realmSet.add(realm);
    }

    private void addAdRealm(Set<Realm> realmSet)
    {
        ActiveDirectoryRealm realm = new ActiveDirectoryRealm();
        realm.setUrl(SecurityConfig.getAdUrl());
        realm.setSystemUsername(SecurityConfig.getAdSystemUsername());
        realm.setSystemPassword(SecurityConfig.getAdSystemPassword());
        realm.setSearchBase(SecurityConfig.getAdSearchBase());
        realmSet.add(realm);
    }

    private void addCustomRealm(Set<Realm> realmSet)
    {
        SimpleSecurity security = SecurityConfig.getSimpleSecurity();
        CustomRealm realm = new CustomRealm(security);
        realmSet.add(realm);
    }

    private void setCache(WebSecurityManager webSecurityManager)
    {
        if (SecurityConfig.isCacheable())
        {
            CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
            CacheManager cacheManager = new MemoryConstrainedCacheManager();
            cachingSecurityManager.setCacheManager(cacheManager);
        }
    }
}
