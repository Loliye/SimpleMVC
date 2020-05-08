package org.mikufans.mvc.exception;

public class UploadException extends RuntimeException
{
    public UploadException()
    {
        super();
    }

    public UploadException(String message)
    {
        super(message);
    }

    public UploadException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UploadException(Throwable cause)
    {
        super(cause);
    }
}
