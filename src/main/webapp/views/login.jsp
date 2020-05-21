<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>登录若依系统</title>
    <meta name="description" content="若依后台管理框架">
    <link type="text/css" href="../static/css/bootstrap.min.css" rel="stylesheet" charset="utf-8"/>
    <link type="text/css" href="../static/css/font-awesome.min.css" charset="utf-8"/>
    <link type="text/css" href="../static/css/style.css" rel="stylesheet" charset="utf-8"/>
    <link type="text/css" href="../static/css/login.min.css" rel="stylesheet" charset="utf-8"/>
    <link type="text/css" href="../static/ruoyi/css/ry-ui.css" rel="stylesheet" charset="utf-8"/>
    <!-- 360浏览器急速模式 -->
    <meta name="renderer" content="webkit">
    <!-- 避免IE使用兼容模式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="shortcut icon" href="../static/favicon.ico" charset="utf-8"/>
    <style type="text/css">label.error { position:inherit;  }</style>
    <script>
        if(window.top!==window.self){alert('未登录或登录超时。请重新登录');window.top.location=window.location};
    </script>
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
                    <strong >还没有账号？ <a th:href="@{/register}">立即注册&raquo;</a></strong>
                    <%--<strong th:if="${@config.getKey('sys.account.registerUser')}">还没有账号？ <a th:href="@{/register}">立即注册&raquo;</a></strong>--%>
                </div>
            </div>
            <div class="col-sm-5">
                <form id="signupForm" autocomplete="off">
                    <h4 class="no-margins">登录：</h4>
                    <p class="m-t-md">你若不离不弃，我必生死相依</p>
                    <input type="text"     name="username" class="form-control uname"     placeholder="用户名" value="admin"    />
                    <input type="password" name="password" class="form-control pword"     placeholder="密码"   value="admin123" />
					<div class="row m-t" th:if="${captchaEnabled==true}">
						<div class="col-xs-6">
						    <input type="text" name="validateCode" class="form-control code" placeholder="验证码" maxlength="5" />
						</div>
						<div class="col-xs-6">
							<a href="javascript:void(0);" title="点击更换验证码">
								<img th:src="@{captcha/captchaImage(type=${captchaType})}" class="imgcode" width="85%"/>
							</a>
						</div>
					</div>
                    <div class="checkbox-custom" th:classappend="${captchaEnabled==false}  ? 'm-t'">
				        <input type="checkbox" id="rememberme" name="rememberme"> <label for="rememberme">记住我</label>
				    </div>
                    <button class="btn btn-success btn-block" id="btnSubmit" data-loading="正在验证登录，请稍后...">登录</button>
                </form>
            </div>
        </div>
        <div class="signup-footer">
            <div class="pull-left">
                &copy; 2019 All Rights Reserved. RuoYi <br>
            </div>
        </div>
    </div>
<script type="text/javascript"> var ctx = [[@{/}]]; var captchaType = [[${captchaType}]]; </script>
<!-- 全局js -->
<script type="text/javascript" src="../static/js/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="../static/js/bootstrap.min.js" ></script>
<!-- 验证插件 -->
<script type="text/javascript" src="../static/ajax/libs/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="../static/ajax/libs/validate/messages_zh.min.js" ></script>
<script type="text/javascript" src="../static/ajax/libs/layer/layer.min.js" ></script>
<script type="text/javascript" src="../static/ajax/libs/blockUI/jquery.blockUI.js" ></script>
<script type="text/javascript" src="../static/ruoyi/js/ry-ui.js" ></script>
<script type="text/javascript" src="../static/ruoyi/login.js" ></script>
</body>
</html>
