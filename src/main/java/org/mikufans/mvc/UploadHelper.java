package org.mikufans.mvc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.mikufans.SimpleConstants;
import org.mikufans.mvc.bean.Multipart;
import org.mikufans.mvc.bean.Multiparts;
import org.mikufans.mvc.bean.Params;
import org.mikufans.mvc.exception.UploadException;
import org.mikufans.util.FileUtil;
import org.mikufans.util.StreamUtil;
import org.omg.CORBA.OBJ_ADAPTER;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class UploadHelper
{
    private static ServletFileUpload fileUpload;


    /**
     * 初始化 UploadHelper
     * @param servletContext
     */
    public static void init(ServletContext servletContext)
    {
        //tomcat的work目录
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        fileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        //上传限制
        int uploadLimit = SimpleConstants.UPLOAD_LIMIT;
        if (uploadLimit != 0)
            fileUpload.setSizeMax(uploadLimit * 1024 * 1024);
    }

    /**
     * 是否为mulitpart请求
     *
     * @param request
     * @return
     */
    public static boolean isMulitpart(HttpServletRequest request)
    {
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 获取multipart 请求参数列表
     *
     * @param request
     * @return
     */
    public static List<Object> getMultipartParamList(HttpServletRequest request) throws Exception
    {
        List<Object> paramList = new ArrayList<>();
        //上传文件的请求的信息：普通字段  文件字段
        Map<String, Object> fieldMap = new HashMap<>();
        List<Multipart> multipartList = new ArrayList<>();
        //上传文件中表单的信息
        List<FileItem> fileItemList;
        try
        {
            fileItemList = fileUpload.parseRequest(request);
        } catch (FileUploadException e)
        {
            throw new UploadException(e);
        }
        for (FileItem fileItem : fileItemList)
        {
            String fieldName = fileItem.getFieldName();
            //处理普通字段
            if (fileItem.isFormField())
            {
                String fieldValue = fileItem.getString(SimpleConstants.UTF_8);
                fieldMap.put(fieldName, fieldValue);
            } else //处理文件字段
            {
                //文件信息
                String fileName = FileUtil.getRealFileName(fileItem.getName());
                if (StringUtils.isNotEmpty(fileName))
                {
                    long fileSize = fileItem.getSize();
                    String contentType = fileItem.getContentType();
                    InputStream inputStream = fileItem.getInputStream();

                    Multipart multipart = new Multipart(fieldName, fileName, fileSize, contentType, inputStream);
                    multipartList.add(multipart);
                }
            }

        }
        //初始化参数列表
        paramList.add(new Params(fieldMap));
        paramList.add(new Multiparts(multipartList));
        return paramList;
    }

    /**
     * 上传文件
     *
     * @param basePath
     * @param multipart
     */
    public static void uploadFile(String basePath, Multipart multipart)
    {
        try
        {
            if (multipart == null)
                return;
            //创建文件绝对路径
            String filePath = basePath + multipart.getFileName();
            FileUtil.createFile(filePath);

            //执行流  复制操作
            InputStream inputStream = new BufferedInputStream(multipart.getInputStream());
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            //执行流复制操作
            StreamUtil.copyStream(inputStream, outputStream);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void uploadFiles(String basePath, Multiparts multiparts)
    {
        for (Multipart multipart : multiparts.getAll())
            uploadFile(basePath, multipart);
    }

}
