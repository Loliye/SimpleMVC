<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: x1448
  Date: 2020/6/2
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>success</h3>
<form action="uploadFile" method="post" enctype="multipart/form-data">
    请选择你要上传的文件：<input type="file" name="upload" siez="16"><br>
    <input type="submit" value="提交">
</form>


<h3>shiro test</h3>
<%--Shiro标签的使用--%>
您当前用户名: <shiro:principal/> <br/>
<shiro:hasRole name="admin">
    admin你好!<br />
</shiro:hasRole>
<shiro:hasRole name="user">
    user你好！<br />
</shiro:hasRole>
<shiro:hasPermission name="product.view">
    product.view<br />
</shiro:hasPermission>
<shiro:hasPermission name="product.create">
    product.create<br/>
</shiro:hasPermission>
<h4>${msg}</h4>
</body>
</html>
