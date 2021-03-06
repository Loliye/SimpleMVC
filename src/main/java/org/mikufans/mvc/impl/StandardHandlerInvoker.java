package org.mikufans.mvc.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.mikufans.InstanceFactory;
import org.mikufans.ioc.BeanHelper;
import org.mikufans.mvc.Handler;
import org.mikufans.mvc.HandlerInvoker;
import org.mikufans.mvc.UploadHelper;
import org.mikufans.mvc.ViewResolver;
import org.mikufans.mvc.bean.Multipart;
import org.mikufans.mvc.bean.Multiparts;
import org.mikufans.mvc.bean.Params;
import org.mikufans.mvc.exception.UploadException;
import org.mikufans.util.ClassUtil;
import org.mikufans.util.MapUtil;
import org.mikufans.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

@Slf4j
public class StandardHandlerInvoker implements HandlerInvoker
{

    private ViewResolver viewResolver = InstanceFactory.getViewResolver();

    @Override
    public void invokerHandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception
    {
        // 获取 Action 相关信息
        Class<?> actionClass = handler.getRequestClass();
        Method actionMethod = handler.getRequestMethod();
        // 从 BeanHelper 中创建 Action 实例
        Object actionInstance = BeanHelper.getBean(actionClass);
        // 创建 Action 方法的参数列表
        List<Object> actionMethodParamList = createActionMethodParamList(request, handler);
        // 检查参数列表是否合法
        checkParamList(actionMethod, actionMethodParamList);
        // 调用 Action 方法
        Object actionMethodResult = invokeActionMethod(actionMethod, actionInstance, actionMethodParamList);
        // 解析视图
        viewResolver.resolveView(request, response, actionMethodResult);
    }

    public List<Object> createActionMethodParamList(HttpServletRequest request, Handler handler) throws Exception
    {
        // 定义参数列表
        List<Object> paramList = new ArrayList<Object>();
        // 获取 Action 方法参数类型
        Class<?>[] actionParamTypes = handler.getRequestMethod().getParameterTypes();
        // 添加路径参数列表（请求路径中的带占位符参数）
        List<Object> pathParamList = createPathParamList(handler.getRequestPathMatcher(), actionParamTypes);
        if (pathParamList != null && paramList.size() != 0)
            paramList.addAll(pathParamList);
        // 分两种情况进行处理
        if (UploadHelper.isMultipart(request))
        {
            // 添加 Multipart 请求参数列表
            paramList.addAll(UploadHelper.getMultipartParamList(request));
        } else
        {
            // 添加普通请求参数列表（包括 Query String 与 Form Data）
            Map<String, Object> requestParamMap = WebUtil.getRequestParamMap(request);
            if (MapUtil.isNotEmpty(requestParamMap))
            {
                paramList.add(new Params(requestParamMap));
            }
        }
        // 返回参数列表
        return paramList;
    }

    private List<Object> createPathParamList(Matcher requestPathMatcher, Class<?>[] actionParamTypes)
    {
        // 定义参数列表
        List<Object> paramList = new ArrayList<Object>();
        // 遍历正则表达式中所匹配的组
        for (int i = 1; i <= requestPathMatcher.groupCount(); i++)
        {
            // 获取请求参数
            String param = requestPathMatcher.group(i);
            // 获取参数类型（支持四种类型：int/Integer、long/Long、double/Double、String）
            Class<?> paramType = actionParamTypes[i - 1];
            if (ClassUtil.isInt(paramType))
                paramList.add(Integer.parseInt(param));
            else if (ClassUtil.isLong(paramType))
                paramList.add(Long.parseLong(param));
            else if (ClassUtil.isDouble(paramType))
                paramList.add(Double.parseDouble(param));
            else if (ClassUtil.isString(paramType))
                paramList.add(param);

        }
        // 返回参数列表
        if (paramList.size() == 0)
            return null;
        return paramList;
    }

    private Object invokeActionMethod(Method actionMethod, Object actionInstance, List<Object> actionMethodParamList) throws IllegalAccessException, InvocationTargetException
    {
        // 通过反射调用 Action 方法
        actionMethod.setAccessible(true); // 取消类型安全检测（可提高反射性能）
        return actionMethod.invoke(actionInstance, actionMethodParamList.toArray());
    }

    private void checkParamList(Method actionMethod, List<Object> actionMethodParamList)
    {
        // 判断 Action 方法参数的个数是否匹配
        Class<?>[] actionMethodParameterTypes = actionMethod.getParameterTypes();
        if (actionMethodParameterTypes.length != actionMethodParamList.size())
        {
            String message = String.format("因为参数个数不匹配，所以无法调用 Action 方法！原始参数个数：%d，实际参数个数：%d", actionMethodParameterTypes.length, actionMethodParamList.size());
            throw new RuntimeException(message);
        }
    }

    //    private ViewResolver viewResolver = InstanceFactory.getViewResolver();
    //
    //    @Override
    //    public void invokerHandler(HttpServletRequest request, HttpServletResponse response, Handler handler)
    //    {
    //        Class<?> requestClass = handler.getRequestClass();
    //        Method requestMethod = handler.getRequestMethod();
    //        //获取实例对象
    //        Object instance = BeanHelper.getBean(requestClass);
    //        //获取方法的的参数列表
    //        List<Object> methodParamList = getMethodParamList(request, handler);
    //        //参数核对
    //        checkParamList(requestMethod, methodParamList);
    //
    //        Object result = null;
    //        //调用对应的方法
    //        try
    //        {
    //            requestMethod.setAccessible(true);
    //            result = requestMethod.invoke(instance, methodParamList.toArray());
    //        } catch (IllegalAccessException e)
    //        {
    //            e.printStackTrace();
    //        } catch (InvocationTargetException e)
    //        {
    //            e.printStackTrace();
    //        }
    //        //解析视图
    //        viewResolver.resolveView(request, response, result);
    //
    //    }
    //
    //    public List<Object> getMethodParamList(HttpServletRequest request, Handler handler)
    //    {
    //        List<Object> paramList = new ArrayList<>();
    //        Class<?>[] paramTypes = handler.getRequestMethod().getParameterTypes();
    //        //添加请求中的参数列表
    //        paramList.addAll(getPathParamList(handler.getRequestPathMatcher(), paramTypes));
    //        //参数分类  正常请求 上传文件请求
    //        if (UploadHelper.isMulitpart(request))
    //        {
    //            try
    //            {
    //                paramList.add(UploadHelper.getMultipartParamList(request));
    //            } catch (Exception e)
    //            {
    //                log.error("添加multipart请求参数出错!", e);
    //                throw new UploadException(e);
    //            }
    //        } else
    //        {
    //            //添加普通请求参数列表
    //            Map<String, Object> requestParam = WebUtil.getRequestParamMap(request);
    //            if (MapUtils.isNotEmpty(requestParam))
    //                paramList.add(new Params(requestParam));
    //        }
    //
    //        return paramList;
    //    }
    //
    //    private List<Object> getPathParamList(Matcher requestMatcher, Class<?>[] paramTypes)
    //    {
    //        List<Object> paramList = new ArrayList<>();
    //        for (int i = 1; i <= requestMatcher.groupCount(); i++)
    //        {
    //            String param = requestMatcher.group(i);
    //            Class<?> type = paramTypes[i - 1];
    //            //解析参数类型
    //            if (ClassUtil.isInt(type))
    //                paramList.add(Integer.parseInt(param));
    //            else if (ClassUtil.isLong(type))
    //                paramList.add(Long.valueOf(param));
    //            else if (ClassUtil.isDouble(type))
    //                paramList.add(Double.valueOf(param));
    //            else if (ClassUtil.isString(type))
    //                paramList.add(param);
    //        }
    //        return paramList;
    //    }
    //
    //    private void checkParamList(Method method, List<Object> paramList)
    //    {
    //        Class<?>[] paramTypes = method.getParameterTypes();
    //        if (paramTypes.length != paramList.size())
    //        {
    //            String message = String.format("因为参数个数不匹配，所以无法调用 request 方法！原始参数个数：%d，实际参数个数：%d", paramTypes.length, paramList.size());
    //            throw new RuntimeException(message);
    //        }
    //    }

}
