<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html  lang="zh" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>注册若依系统</title>
    <meta name="description" content="若依后台管理框架">
    <link href="../static/css/bootstrap.min.css" th:href="@{/css/bootstrap.min.css}" rel="stylesheet"/>
    <link href="../static/css/font-awesome.min.css" th:href="@{/css/font-awesome.min.css}" rel="stylesheet"/>
    <link href="../static/css/style.css" th:href="@{/css/style.css}" rel="stylesheet"/>
    <link href="../static/css/login.min.css" th:href="@{/css/login.min.css}" rel="stylesheet"/>
    <link href="../static/ruoyi/css/ry-ui.css" th:href="@{/ruoyi/css/ry-ui.css?v=4.2.0}" rel="stylesheet"/>
    <!-- 360浏览器急速模式 -->
    <meta name="renderer" content="webkit">
    <!-- 避免IE使用兼容模式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="shortcut icon" href="../static/favicon.ico" th:href="@{favicon.ico}"/>
    <style type="text/css">label.error { position:inherit;  }</style>
</head>
<body class="signin">
    <div class="signinpanel">
        <div class="row">
            <div class="col-sm-7">
                <div class="signin-info">
                    <div class="logopanel m-b">
                        <h1><img alt="[ 若依 ]" src="../static/ruoyi.png" th:src="@{/ruoyi.png}"></h1>
                    </div>
                    <div class="m-b"></div>
                    <h4>欢迎使用 <strong>若依 后台管理系统</strong></h4>
                    <ul class="m-b">
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> SpringBoot</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> Mybatis</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> Shiro</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> Thymeleaf</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> Bootstrap</li>
                    </ul>
                    <strong>已经注册过? <a th:href="@{/login}">直接登录&raquo;</a></strong>
                </div>
            </div>
            <div class="col-sm-5">
                <form id="registerForm" autocomplete="off">
                    <h4 class="no-margins">注册：</h4>
                    <p class="m-t-md">你若不离不弃，我必生死相依</p>
                    <input type="text"     name="username" class="form-control uname"         placeholder="用户名"   maxlength="20" />
                    <input type="password" name="password" class="form-control pword"         placeholder="密码"     maxlength="20" />
                    <input type="password" name="confirmPassword" class="form-control pword"  placeholder="确认密码" maxlength="20" />
					<div class="row m-t" th:if="${captchaEnabled==true}">
						<div class="col-xs-6">
						    <input type="text" name="validateCode" class="form-control code"  placeholder="验证码"   maxlength="5" >
						</div>
						<div class="col-xs-6">
							<a href="javascript:void(0);" title="点击更换验证码">
								<img th:src="@{captcha/captchaImage(type=${captchaType})}" class="imgcode" width="85%"/>
							</a>
						</div>
					</div>
                    <div class="checkbox-custom" th:classappend="${captchaEnabled==false} ? 'm-t'">
				        <input type="checkbox" id="acceptTerm" name="acceptTerm"> <label for="acceptTerm">我已阅读并同意</label>
				        <a href="https://gitee.com/y_project/RuoYi/blob/master/README.md" target="_blank">使用条款</a>
				    </div>
                    <button class="btn btn-success btn-block" id="btnSubmit" data-loading="正在验证注册，请稍后...">注册</button>
                </form>
            </div>
        </div>
        <div class="signup-footer">
            <div class="pull-left">
                &copy; 2019 All Rights Reserved. RuoYi <br>
            </div>
        </div>
    </div>
<script th:inline="javascript"> var ctx = [[@{/}]]; var captchaType = [[${captchaType}]]; </script>
<!-- 全局js -->
<script src="../static/js/jquery.min.js" th:src="@{/js/jquery.min.js}"></script>
<script src="../static/js/bootstrap.min.js" th:src="@{/js/bootstrap.min.js}"></script>
<!-- 验证插件 -->
<script src="../static/ajax/libs/validate/jquery.validate.min.js" th:src="@{/ajax/libs/validate/jquery.validate.min.js}"></script>
<script src="../static/ajax/libs/validate/messages_zh.min.js" th:src="@{/ajax/libs/validate/messages_zh.min.js}"></script>
<script src="../static/ajax/libs/layer/layer.min.js" th:src="@{/ajax/libs/layer/layer.min.js}"></script>
<script src="../static/ajax/libs/blockUI/jquery.blockUI.js" th:src="@{/ajax/libs/blockUI/jquery.blockUI.js}"></script>
<script src="../static/ruoyi/js/ry-ui.js" th:src="@{/ruoyi/js/ry-ui.js?v=4.2.0}"></script>
<script src="../static/ruoyi/register.js" th:src="@{/ruoyi/register.js}"></script>
</body>
</html>
