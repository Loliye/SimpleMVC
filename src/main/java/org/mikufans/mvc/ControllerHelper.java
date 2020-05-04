package org.mikufans.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.mikufans.core.ClassHelper;
import org.mikufans.mvc.annotation.Controller;
import org.mikufans.mvc.annotation.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerHelper
{
    private static final Map<Requester, Handler> controllerMap = new LinkedHashMap<>();

    static
    {
        List<Class<?>> controllerClassList = ClassHelper.getClassListByAnnotation(Controller.class);
        if (CollectionUtils.isNotEmpty(controllerClassList))
        {
            //正常请求  正则表达式请求
            Map<Requester, Handler> commonMap = new HashMap<>();
            Map<Requester, Handler> regexMap = new HashMap<>();

            for (Class<?> controllerClass : controllerClassList)
            {
                //处理Controller下所有的方法
                Method[] controllerMethods = controllerClass.getDeclaredMethods();
                if (!ArrayUtils.isEmpty(controllerMethods))
                {
                    for (Method method : controllerMethods)
                    {
                        //处理controller方法
                        handleControllerMethod(controllerClass, method, commonMap, regexMap);
                    }
                }
            }
            //保证顺序
            controllerMap.putAll(commonMap);
            controllerMap.putAll(regexMap);
        }
    }

    public static Map<Requester, Handler> getControllerMap()
    {
        return controllerMap;
    }

    private static void handleControllerMethod(Class<?> controllerClass, Method controllerMethod,
                                               Map<Requester, Handler> commonMap, Map<Requester, Handler> regexMap)
    {
        if (controllerMethod.isAnnotationPresent(Request.Get.class))
        {
            String path = controllerMethod.getAnnotation(Request.Get.class).value();
            putActionMap("GET", path, controllerClass, controllerMethod, commonMap, regexMap);
        } else if (controllerMethod.isAnnotationPresent(Request.Post.class))
        {
            String path = controllerMethod.getAnnotation(Request.Post.class).value();
            putActionMap("POST", path, controllerClass, controllerMethod, commonMap, regexMap);
        } else if (controllerMethod.isAnnotationPresent(Request.Put.class))
        {
            String path = controllerMethod.getAnnotation(Request.Put.class).value();
            putActionMap("PUT", path, controllerClass, controllerMethod, commonMap, regexMap);
        } else if (controllerMethod.isAnnotationPresent(Request.Delete.class))
        {
            String path = controllerMethod.getAnnotation(Request.Delete.class).value();
            putActionMap("DELETE", path, controllerClass, controllerMethod, commonMap, regexMap);
        }
    }

    private static void putActionMap(String requestMethod, String requestPath, Class<?> controllerClass,
                                     Method controllerMethod, Map<Requester, Handler> commonActionMap,
                                     Map<Requester, Handler> regexpActionMap)
    {
        // 判断 Request Path 中是否带有占位符
        if (requestPath.matches(".+\\{\\w+\\}.*"))
        {
            // 将请求路径中的占位符 {\w+} 转换为正则表达式 (\\w+)
            requestPath = replaceAll(requestPath, "\\{\\w+\\}", "(\\\\w+)");
            regexpActionMap.put(new Requester(requestMethod, requestPath), new Handler(controllerClass, controllerMethod));
        } else
        {
            commonActionMap.put(new Requester(requestMethod, requestPath), new Handler(controllerClass, controllerMethod));
        }
    }

    /**
     * 替换原有的字符串
     *
     * @param str
     * @param regex
     * @param replacement
     * @return
     */
    public static String replaceAll(String str, String regex, String replacement)
    {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find())
        {
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);
        return sb.toString();
    }


}
