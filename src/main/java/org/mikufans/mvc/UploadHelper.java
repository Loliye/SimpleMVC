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
import java.util.*;

@Slf4j
public class UploadHelper
{
    private static ServletFileUpload fileUpload;

    /**
     * 初始化 UploadHelper
     *
     * @param servletContext
     */
    public static void init(ServletContext servletContext)
    {
        //tomcat的work目录
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
//        File repository = new File(DataContext.getServletContext().getRealPath("/WEB-INF/tmpfiles"));
        if (!repository.exists())
            repository.mkdir();
        fileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        fileUpload.setHeaderEncoding("utf-8");
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
    public static boolean isMultipart(HttpServletRequest request)
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
            String filePath = createRealFilePath(basePath, multipart.getFileName());
            System.out.println(filePath);
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

    /**
     * 根据基本路径和文件名称生成真实文件路径，基本路径\\年\\月\\fileName
     *
     * @param basePath
     * @param fileName
     * @return
     */
    private static String createRealFilePath(String basePath, String fileName)
    {
        Calendar today = Calendar.getInstance();
        String year = String.valueOf(today.get(Calendar.YEAR));
        String month = String.valueOf(today.get(Calendar.MONTH) + 1);


        String upPath = basePath + File.separator + year + File.separator + month + File.separator;
        File uploadFolder = new File(upPath);
        if (!uploadFolder.exists())
        {
            uploadFolder.mkdirs();
        }

        String realFilePath = upPath + fileName;

        return realFilePath;
    }


    /**
     * 默认上传至webapp/uploadfiles
     *
     * @param multipart
     */
    public static void uploadFile(Multipart multipart)
    {
        String realPath = DataContext.getServletContext().getRealPath(SimpleConstants.UPLOAD_PATH);
        uploadFile(realPath, multipart);
    }


    public static void uploadFiles(String basePath, Multiparts multiparts)
    {
        for (Multipart multipart : multiparts.getAll())
            uploadFile(basePath, multipart);
    }

}
