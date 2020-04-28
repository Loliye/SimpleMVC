package org.mikufans.mvc.bean;

import org.mikufans.core.bean.BaseBean;

public class Result extends BaseBean
{
    private boolean success;
    private int error;
    private Object data;

    public Result(boolean success)
    {
        this.success = success;
    }

    public Result error(int error)
    {
        this.error = error;
        return this;
    }

    public Result data(Object data)
    {
        this.data = data;
        return this;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public int getError()
    {
        return error;
    }

    public void setError(int error)
    {
        this.error = error;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }
}
