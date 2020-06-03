package com.mikufans.bean;

import lombok.Data;
import org.mikufans.orm.annotation.Entity;

@Entity
@Data
public class Log
{
    private long id;
    private String date;
    private String time;
    private String description;
}
