package com.mikufans;

import lombok.var;
import org.mikufans.core.bean.BaseBean;
import org.mikufans.ioc.annotation.Bean;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

@Bean
public class Test
{
    public int id;
    public String name;

    public final double a1=Math.random();
    public static double s2=Math.random();


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

    public Test()
    {}

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {

        T.tt();


        //        Test test1=new Test();
//        Test test2=new Test();
//        System.out.println(test1.a1);
//        System.out.println(test1.s2);
//        System.out.println(test2.a1);
//        System.out.println(test2.s2);
    }
}

class T
{
    @CallerSensitive
    public static void t()
    {
        System.out.println(Reflection.getCallerClass());
    }

    public static void tt()
    {
        t();
    }
}
