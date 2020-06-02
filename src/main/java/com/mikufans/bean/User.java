package com.mikufans.bean;

import lombok.Data;
import org.mikufans.core.bean.BaseBean;
import org.mikufans.orm.annotation.Entity;

@Entity
@Data
public class User extends BaseBean
{
    private int id;
    private String name;
    private String password;
}
