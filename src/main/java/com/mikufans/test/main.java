package com.mikufans.test;

import org.mikufans.HelperLoader;
import org.mikufans.core.bean.BaseBean;
import org.mikufans.ioc.BeanHelper;
import org.mikufans.ioc.annotation.Autowired;
import org.mikufans.ioc.annotation.Bean;
import org.mikufans.transaction.annotation.Service;

@Service
public class main extends BaseBean
{
    @Autowired
    Test test;

    /**
     * 简易ioc容器实现
     *
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @org.junit.Test
    public void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        HelperLoader.init();
        System.out.println(BeanHelper.getBean(Test2.class));
        System.out.println(this.test);
    }

    @org.junit.Test
    public void test2()
    {
        U u = new UImpl();
        UBean u1 = u.getU();
        System.out.println(u1);
        System.out.println(u1.getName());

    }

}
