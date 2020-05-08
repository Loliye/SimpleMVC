package org.mikufans.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class StreamUtil
{
    /**
     * 输入流复制到输出流中
     * @param inputStream
     * @param outputStream
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream)
    {
        int len;
        try
        {
            byte[] buffer = new byte[4 * 1024];
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
                outputStream.write(buffer, 0, len);
            outputStream.flush();
        } catch (IOException e)
        {
            log.error("复制流出错!", e);
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                inputStream.close();
                outputStream.close();
            } catch (IOException e)
            {
                log.error("释放资源出错!");
                e.printStackTrace();
            }
        }
    }
}
