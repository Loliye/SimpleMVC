package org.mikufans.plugin.shiro.tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * 判断当前用户是否拥有其中某一种权限（逗号分隔，表示“或”的关系）
 */
public class HasAnyPermissionsTag extends PermissionTag
{
    private static final String PERMISSION_NAMES_SPLIT = ",";

    @Override
    protected boolean showTagBody(String p)
    {
        boolean hasAnyPermission = false;
        Subject subject = getSubject();
        if (subject != null)
        {
            for (String permissionName : p.split(PERMISSION_NAMES_SPLIT))
            {
                if (subject.isPermitted(permissionName.trim()))
                {
                    hasAnyPermission = true;
                    break;
                }
            }
        }
        return hasAnyPermission;
    }
}
