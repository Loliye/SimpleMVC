package org.mikufans.plugin.shiro;

import lombok.Data;
import org.mikufans.core.bean.BaseBean;

@Data
public class ShiroUser extends BaseBean
{
    private int id;
    private String username;
    private String password;
}
