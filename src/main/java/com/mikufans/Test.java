package com.mikufans;

import org.mikufans.core.bean.BaseBean;
import org.mikufans.ioc.annotation.Bean;

@Bean
public class Test
{
    public int id;
    public String name;

    public Test()
    {
    }

    public void sout(String content)
    {
        System.out.println(content);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Test(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
}
