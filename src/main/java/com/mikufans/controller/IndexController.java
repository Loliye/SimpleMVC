package com.mikufans.controller;


import com.mikufans.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.mikufans.SimpleConstants;
import org.mikufans.mvc.DataContext;
import org.mikufans.mvc.UploadHelper;
import org.mikufans.mvc.annotation.Controller;
import org.mikufans.mvc.annotation.Request;
import org.mikufans.mvc.bean.*;
import org.mikufans.orm.DataSet;

import java.io.File;
import java.util.Map;

@Controller
@Slf4j
public class IndexController
{


    @Request.Get("/index")
    public View index()
    {
        return new View("index.jsp");
    }

    @Request.Get("/login")
    public View login()
    {
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal());
        return new View("index.jsp");
    }

    @Request.Post("/login")
    public View login(Params params)
    {
        String username = params.getString("username");
        String password = params.getString("password");
        User user = DataSet.select(User.class, "name=? and password=?", username, password);
        System.out.println(user);
        if (user != null)
            return new View("success.jsp").data("msg", "testMsg");
        return new View("index.jsp");
    }

    @Request.Post("/uploadFile")
    public View uploadFile(Params params, Multiparts multiparts)
    {
        Multipart multipart = multiparts.getOne();
        //        System.out.println(DataContext.getRequest().getRealPath(""));
        //        System.out.println(DataContext.getServletContext().getRealPath(""));
        //        System.out.println(DataContext.getRequest().getServletContext().getRealPath(""));
        UploadHelper.uploadFile(DataContext.getServletContext().getRealPath(""), multipart);
        return new View("index.jsp").data("msg", multipart.getFileName());
    }

    @Request.Get("/image/delete")
    public View delete(Params params)
    {
        String year=params.getString("year");
        String moon=params.getString("moon");
        String id=params.getString("id");
        String path = DataContext.getServletContext().getRealPath("") + year + File.separator + moon + File.separator + id;
        System.out.println(path);
        File file = new File(path);
        boolean delete = file.delete();
        return new View("yes.jsp").data("msg", delete);
    }

    public static void main(String[] args)
    {
        boolean delete = new File("D:\\ruoyi\\2020\\6\\3a3b01ec5e9419cde5c2eea088f0955d29b6f652.jpg").delete();
        System.out.println(delete);
    }

    //    @Request.Get("/loginDemo")
    //    public Result loginDemo(Params params)
    //    {
    //        String username = params.getString("username");
    //        String password = params.getString("password");
    //        //        System.out.println(username);
    //        //        System.out.println(password);
    //
    //        List<User> users = DataSet.selectList(User.class);
    //
    //
    //        UsernamePasswordToken token = new UsernamePasswordToken(username, password, false);
    //        Subject subject = SecurityUtils.getSubject();
    //        try
    //        {
    //            subject.login(token);
    //            return Result.Success();
    //
    //        } catch (AuthenticationException e)
    //        {
    //            String msg = "用户或密码错误";
    //            if (StringUtils.isNotEmpty(e.getMessage()))
    //            {
    //                msg = e.getMessage();
    //            }
    //            return Result.Error(msg);
    //        }
    //    }


    //    @Request.Get("/test/{id}")
    //    public View test(long id)
    //    {
    //        System.out.println(id);
    //        return new View("index.jsp").data("msg","test");
    //    }
}

