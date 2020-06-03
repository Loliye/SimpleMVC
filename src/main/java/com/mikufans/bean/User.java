package com.mikufans.bean;

import lombok.Data;
import lombok.ToString;
import org.mikufans.core.bean.BaseBean;
import org.mikufans.orm.annotation.Entity;
import org.mikufans.plugin.shiro.ShiroUser;

@Entity
@Data
@ToString
public class User extends ShiroUser
{
}
