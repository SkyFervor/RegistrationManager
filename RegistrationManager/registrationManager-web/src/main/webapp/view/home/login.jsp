<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skyfervor.framework.utility.HtmlHelper"%>
<%@ page import="com.skyfervor.framework.constant.Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>登录</title>
<%@ include file="../../view/comm/headJsCss.jsp"%>
<%@ include file="../../view/comm/headJstl.jsp"%>
<script src="<%=HtmlHelper.getScriptUrl(basePath, "common/md5.js")%>" type="text/javascript"></script>
<link href="<%=HtmlHelper.getStyleUrl(basePath, "login/base.css")%>" rel="stylesheet" type="text/css" />
<link href="<%=HtmlHelper.getStyleUrl(basePath, "login/index.css")%>" rel="stylesheet" type="text/css" />
<script>
	//监听回车事件 提交表单数据
	$(document).keydown(function(event) {
		if (event.keyCode == 13 && $("#loginName").val() != "" && $("#password").val() != ""
				&& $("#auth").val() != "") {
			$("#btn_login").click();
		}
	});

	$(function() {
		changeAuthCode();
	});

	function changeAuthCode() {
		var width = $('#authImg').width();
		var height = $('#authImg').height();
		$('#auth').val("");
		$('#authImg').attr('src', basePath + '/login/authimage?width=' + width + "&height=" + height
				+ "&r=" + Math.random());
	}

	function getEncryption(loginName, password, auth) {
		hexcase = 1; // MD5插件设置，大写输出
		var str1 = hex_md5(password);
		var str2 = hex_md5(str1 + loginName);
		var str3 = hex_md5(str2 + auth.toUpperCase());
		return str3;
	}

	function login() {
		var vo = getInput("form_login");
		var ret = verify(vo);
		if (ret['result'] == false) {
			alert(ret['info']);
			return;
		}
		
		vo.password = getEncryption(vo.loginName, vo.password, vo.auth);
		var jsonSet = new JsonSet();
		jsonSet.put("input", jsonToString(vo));
		transaction({
			url : basePath + '/login/login',
			jsonSet : jsonSet,
			async : false,
			callback : function(data) {
				switch (data) {
				case <%=Constant.Login.SUCCESS%>:
					location.href = basePath + "/view/home/index.jsp";
					return;
					break;
				case <%=Constant.Login.USERNAME_INCORRECT%>:
					alert("该用户不存在");
					$('#loginName').focus();
					break;
				case <%=Constant.Login.PASSWORD_INCORRECT%>:
					alert("密码错误");
					$('#password').val('');
					$('#password').focus();
					break;
				case <%=Constant.Login.AUTHCODE_INCORRECT%>:
					alert("验证码错误");
					$('#auth').val('');
					$('#auth').focus();
					break;
				default:
					break;
				}
				changeAuthCode();
			}
		});
	}

	function verify(vo){
		var ret = new Object();

		var loginName = vo["loginName"];
		var password = vo["password"];
		var auth = vo["auth"];

		ret['result'] = false;
		if (loginName == null || "" == loginName) {
			ret['info'] = "请输入账号";
		} else if (password == null || "" == password) {
			ret['info'] = "请输入密码";
		} else if (auth == null || "" == auth) {
			ret['info'] = "请输入验证码";
		} else {
			ret['result'] = true
		}

		return ret;
	}
</script>
</head>
<body>
	<div class="w">
		<div id="logo"></div>
	</div>
	<div id="content">
		<div class="login-wrap">
			<div class="w">
				<div class="login-form">
					<div class="login-box">
						<div class="mt">
							<h1>用户登录</h1>
						</div>
						<div class="msg-wrap">
							<div id="msg_err" class="msg-error hide"></div>
						</div>
						<div class="mc">
							<div class="form">
								<form id="form_login">
									<div class="item item-fore1">
										<label class="login-label name-label"></label>
										<input id="loginName" type="text" name="loginName" class="itxt" tabindex="1"
											placeholder="账号" />
									</div>
									<div class="item item-fore2">
										<label class="login-label pwd-label"></label>
										<input id="password" type="password" name="password" class="itxt itxt-error" tabindex="2"
											placeholder="密码" />
									</div>

									<div id="o-authcode" class="item item-vcode item-fore4">
										<input id="auth" type="text" name="auth" class="itxt itxt02" tabindex="5" maxlength="4"
											placeholder="验证码" />
										<img id="authImg" class="verify-code" src="" onclick="changeAuthCode();" />
										<a href="javascript:void(0)" onclick="changeAuthCode();">看不清楚换一张</a>
									</div>
									<div class="item item-fore5">
										<div class="login-btn">
											<a id="btn_login" href="javascript:;" onclick="login();" class="btn-img btn-entry"
												tabindex="6">登&nbsp;&nbsp;&nbsp;&nbsp;录</a>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="login-banner">
				<div class="w">
					<div class="i-inner"
						style="background: url(<%=HtmlHelper.getImageUrl(basePath, "login/background.png")%>) 0px 0px no-repeat"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="bottom">
		<jsp:include page="../comm/bottom.jsp"></jsp:include>
	</div>
</body>
</html>