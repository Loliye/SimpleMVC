package com.mikufans.controller;

import lombok.extern.slf4j.Slf4j;
import org.mikufans.InstanceFactory;
import org.mikufans.ds.DataSourceFactory;
import org.mikufans.ioc.BeanHelper;
import org.mikufans.ioc.IocHelper;
import org.mikufans.mvc.annotation.Controller;
import org.mikufans.mvc.annotation.Request;
import org.mikufans.mvc.bean.Params;
import org.mikufans.mvc.bean.View;

@Controller
@Slf4j
public class IndexController
{
    @Request.Get("/index")
    public View index()
    {
        return new View("login.jsp");
    }

    @Request.Get("/login")
    public View login()
    {
        return new View("login.jsp");
    }

    @Request.Post("/login")
    public View login(Params params)
    {

    }


    //    @Request.Get("/test/{id}")
    //    public View test(long id)
    //    {
    //        System.out.println(id);
    //        return new View("index.jsp").data("msg","test");
    //    }
}
