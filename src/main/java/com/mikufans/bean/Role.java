package com.mikufans.bean;

import lombok.Data;
import org.mikufans.orm.annotation.Entity;

@Entity
@Data
public class Role
{
    private long id;
    private String roleName;
}
