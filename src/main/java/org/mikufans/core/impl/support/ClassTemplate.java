package org.mikufans.core.impl.support;

import org.apache.commons.lang.StringUtils;
import org.mikufans.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 用于获取类的模板类
 */
public abstract class ClassTemplate
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassTemplate.class);

    protected final String packageName;

    public ClassTemplate(String packageName)
    {
        this.packageName = packageName;
    }

    public final List<Class<?>> getClassList()
    {
        List<Class<?>> classList = new ArrayList<>();
        try
        {
            //遍历资源
            Enumeration<URL> urls = ClassUtil.getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements())
            {
                URL url = urls.nextElement();
                if (url != null)
                {
                    //区分file  和 jar
                    String protocol = url.getProtocol();
                    if (protocol.equals("file"))
                    {
                        //添加class文件
                        String packagePath = url.toURI().getPath();
                        addClass(classList, packagePath, packageName);
                    } else if (protocol.equals("jar"))
                    {
                        //解析jar包中得文件
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> jarEntries = jarFile.entries();
                        while (jarEntries.hasMoreElements())
                        {
                            JarEntry jarEntry = jarEntries.nextElement();
                            String jarEntryName = jarEntry.getName();

                            if (jarEntryName.equals(".class"))
                            {
                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");

                                doAddClass(classList, className);
                            }
                        }
                    }
                }
            }
        } catch (URISyntaxException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return classList;
    }

    private void addClass(List<Class<?>> classList, String packagePath, String packageName)
    {
        try
        {
            File[] files = new File(packagePath).listFiles((FileFilter) (file) -> {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            });

            for (File file : files)
            {
                String fileName = file.getName();
                //遍历文件|目录
                if (file.isFile())
                {
                    String className = fileName.substring(0, fileName.lastIndexOf("."));
                    if (StringUtils.isNotEmpty(packageName))
                    {
                        className = packageName + "." + className;
                    }
                    doAddClass(classList, className);
                } else //递归获取下面得子包
                {
                    String subPackagePath = fileName;
                    if (StringUtils.isNotEmpty(packagePath))
                    {
                        subPackagePath = packagePath + "/" + subPackagePath;

                    }
                    String subPackageName = fileName;
                    if (StringUtils.isNotEmpty(packageName))
                    {
                        subPackageName = packageName + subPackageName;
                    }

                    addClass(classList, subPackagePath, subPackageName);
                }
            }
        } catch (Exception e)
        {
            LOGGER.error("添加类出错", e);
        }
    }

    private void doAddClass(List<Class<?>> classList, String className)
    {
        Class<?> cls = ClassUtil.loadClass(className, false);
        if (checkAddClass(cls))
            classList.add(cls);
    }

    public abstract boolean checkAddClass(Class<?> cls);

}
