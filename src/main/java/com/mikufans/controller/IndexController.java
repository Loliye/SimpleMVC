package com.mikufans.controller;

import lombok.extern.slf4j.Slf4j;
import org.mikufans.mvc.annotation.Controller;
import org.mikufans.mvc.annotation.Request;
import org.mikufans.mvc.bean.View;

@Controller
@Slf4j
public class IndexController
{
    @Request.Get("/index")
    public View index()
    {
        log.info("indexController.......");
        return new View("index.jsp").data("test", "test");
    }

}
