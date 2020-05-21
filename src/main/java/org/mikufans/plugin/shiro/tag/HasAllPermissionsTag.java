package org.mikufans.plugin.shiro.tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * 判断当前用户是否拥有其中所有的权限（逗号分隔，表示“与”的关系）
 */
public class HasAllPermissionsTag extends PermissionTag
{

    private static final String PERMISSION_NAMES_DELIMETER = ",";

    @Override
    protected boolean showTagBody(String permNames)
    {
        boolean hasAllPermission = false;
        Subject subject = getSubject();
        if (subject != null)
        {
            if (subject.isPermittedAll(permNames.split(PERMISSION_NAMES_DELIMETER)))
            {
                hasAllPermission = true;
            }
        }
        return hasAllPermission;
    }
}
