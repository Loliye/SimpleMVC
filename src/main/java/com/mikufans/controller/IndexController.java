package com.mikufans.controller;

import org.mikufans.mvc.annotation.Controller;
import org.mikufans.mvc.annotation.Request;
import org.mikufans.mvc.bean.View;

@Controller
public class IndexController
{
    @Request.Get("/index")
    public View index()
    {
        return new View("index.jsp").data("test","test");
    }

}
