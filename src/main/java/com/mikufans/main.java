package com.mikufans;

import org.mikufans.HelperLoader;
import org.mikufans.core.ClassScanner;
import org.mikufans.core.impl.StandardClassScanner;
import org.mikufans.ioc.BeanHelper;
import org.mikufans.ioc.annotation.Bean;
import org.mikufans.ioc.annotation.Inject;
import org.mikufans.util.ClassUtil;

public class main
{

    /**
     * 简易ioc容器实现
     * @param args
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
                HelperLoader.init();
                System.out.println(BeanHelper.getBean(Test.class));
    }

}
