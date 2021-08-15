<!DOCTYPE html>
<html class="loginHtml" lang="en">
<head>
	<#include "includes/_base_href.ftl">
	<script>
		//如果当前页面不是最外层，就把最外层设置为当前页面
		if(window!=top){
			top.location.href=location.href;
		}
	</script>
	<head>
		<title>登录 - 军事信息管理系统</title>
		<meta charset="utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="shortcut icon" href="assets/expand/images/army.png" type="image/x-icon" />
		<link rel="stylesheet" href="assets/common/libs/layui/css/layui.css"/>
		<link rel="stylesheet" href="assets/common/css/login.css" media="all"/>
		<link rel="stylesheet" href="assets/common/module/admin.css" media="all"/>
	</head>

<body>
<div class="login-wrapper">

	<form class="layui-form">
		<h2>用户登录</h2>
		<div class="layui-form-item layui-input-icon-group">
			<i class="layui-icon layui-icon-username"></i>
			<input class="layui-input" id="username" placeholder="请输入登录账号" autocomplete="off"
				   lay-verType="tips" lay-verify="required" required/>
		</div>
		<div class="layui-form-item layui-input-icon-group">
			<i class="layui-icon layui-icon-password"></i>
			<input class="layui-input" id="password" placeholder="请输入登录密码" type="password"
				   lay-verType="tips" lay-verify="required" required/>
		</div>
		<div class="layui-form-item layui-input-icon-group login-captcha-group">
			<i class="layui-icon layui-icon-auz"></i>
			<input class="layui-input" id="verifyCode" placeholder="请输入验证码" autocomplete="off"
				   lay-verType="tips" lay-verify="required" required/>
			<img class="login-captcha" src="captcha" alt=""/>
		</div>
		<div class="layui-form-item">
			<button class="layui-btn layui-btn-fluid" id="submit" type="button">登录</button>
		</div>
	</form>
</div>
<div class="login-copyright">© 2020 <a href="http://www.bettem.com" target="_blank" style="color: white">Bettem 版权所有</a></div>
<script type="text/javascript">
</script>
<script type="text/javascript" src="assets/common/libs/layui/layui.js"></script>
<script type="text/javascript" src="assets/common/js/common.js"></script>

<script>
	layui.use(['layer', 'form', 'index', 'ax'], function () {
		var $ = layui.jquery;
		var $ax = layui.ax;
		var layer = layui.layer;
		var form = layui.form;
		var index = layui.index;

		// 图形验证码
		$('.login-captcha').click(function () {
			this.src = this.src + '?t=' + (new Date).getTime();
		});

		var errorMsg = "";
		if (errorMsg) {
			layer.msg(errorMsg, {icon: 5, anim: 6});
		}


		//登录操作
		$('#submit').click(function () {
			var username = $("#username").val();
			if(!username){
				CommonTool.info("请输入账号！");
				return;
			}
			var password = $("#password").val();
			if(!password){
				CommonTool.info("请输入密码！");
				return;
			}
			var verifyCode = $("#verifyCode").val();
			if(!verifyCode){
				CommonTool.info("请输入验证码！");
				return;
			}
			var ajax = new $ax("login", function (data) {
				CommonTool.success("登录成功!");
				index.clearTabCache();
				window.location.reload();
			}, function (data) {
				layer.msg("登录失败！" + data.responseJSON.message, {icon: 5, anim: 6});
			});
			ajax.set("username", $("#username").val());
			ajax.set("password", $("#password").val());
			ajax.set("verifyCode", $("#verifyCode").val());
			ajax.start();
		});

	});
</script>
</body>
</html>