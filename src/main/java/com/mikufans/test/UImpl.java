package com.mikufans.test;

public class UImpl implements U
{
    @Override
    public <T> T getU()
    {
        return (T) new UBean("test...");
    }
}
