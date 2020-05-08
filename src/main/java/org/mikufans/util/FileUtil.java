package org.mikufans.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileUtil
{

    /**
     * 获取文件名字
     * @param fileName
     * @return
     */
    public static String getRealFileName(String fileName)
    {
        return FilenameUtils.getName(fileName);
    }



    public static File createDir(String dirPath)
    {
        File dir = new File(dirPath);
        if (!dir.exists())
        {
            try
            {
                FileUtils.forceMkdir(dir);
            } catch (IOException e)
            {
                log.error("创建目录出错!", e);
                throw new RuntimeException(e);
            }
        }
        return dir;
    }

    public static File createFile(String filePath)
    {
        File file;
        try
        {
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists())
                FileUtils.forceMkdir(parentDir);

        } catch (IOException e)
        {
            log.error("创建文件出错!", e);
            throw new RuntimeException(e);
        }
        return file;
    }

}
