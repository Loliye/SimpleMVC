package org.mikufans.core.exception;

/**
 * 初始化错误
 */
public class InitError extends Error
{
    public InitError()
    {
        super();
    }

    public InitError(String message)
    {
        super(message);
    }

    public InitError(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InitError(Throwable cause)
    {
        super(cause);
    }
}

