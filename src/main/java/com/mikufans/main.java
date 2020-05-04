package com.mikufans;

import org.mikufans.HelperLoader;
import org.mikufans.core.ClassScanner;
import org.mikufans.core.bean.BaseBean;
import org.mikufans.core.impl.StandardClassScanner;
import org.mikufans.ioc.BeanHelper;
import org.mikufans.ioc.annotation.Autowired;
import org.mikufans.ioc.annotation.Bean;
import org.mikufans.util.ClassUtil;

@Bean
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
        System.out.println(BeanHelper.getBean(Test.class));
        System.out.println(this.test);
    }


}
