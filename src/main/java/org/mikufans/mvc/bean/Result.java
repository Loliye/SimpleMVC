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

    public Result()
    {
    }

    public Result(boolean success, int error, Object data)
    {
        this.success = success;
        this.error = error;
        this.data = data;
    }

    public static Result Error(Object data)
    {
        return new Result(false, 500, data);
    }

    public static Result Error(int error, Object data)
    {
        return new Result(false, error, data);
    }

    public static Result Success(Object data)
    {
        return new Result(true, 200, data);
    }

    public static Result Success()
    {
        return new Result(true, 200, "success");
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
