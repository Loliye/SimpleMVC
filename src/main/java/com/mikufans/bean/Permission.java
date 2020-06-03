package com.mikufans.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.mikufans.orm.annotation.Entity;

@Entity
@Data
public class Permission
{
    private long id;
    private String permissionName;
}
