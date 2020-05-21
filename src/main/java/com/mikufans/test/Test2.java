package com.mikufans.test;

import com.mikufans.test.Test;
import org.mikufans.ioc.annotation.Autowired;

import java.lang.reflect.Field;

//@Bean
public class Test2
{
    @Autowired
    public Test test;


    @org.junit.Test
    public void test() throws IllegalAccessException, NoSuchFieldException
    {
        Node node=new Node();
        node.id=10;node.name="haha";
        Field field=Node.class.getDeclaredField("id");
        field.set(node,2);
        field=Node.class.getDeclaredField("name");
        field.set(node,"nihao");
        System.out.println(node);
    }

    public class Node
    {
        public int id;
        public String name;

        @Override
        public String toString()
        {
            return "Node{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
