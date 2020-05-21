package com.mikufans.controller;

import lombok.extern.slf4j.Slf4j;
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
        log.info("indexController.......");
        return new View("login.jsp").data("test", "test");
    }

    @Request.Post("/login")
    public View login(Params params)
    {
        String username = params.getString("username");
        String password = params.getString("password");
        if ("admin".equals(username) && "123456".equals(password))
            return new View("login.jsp").data("msg", "success");
        return new View("login.jsp").data("msg", "error");
    }

//    @Request.Get("/test/{id}")
//    public View test(long id)
//    {
//        System.out.println(id);
//        return new View("index.jsp").data("msg","test");
//    }
}
