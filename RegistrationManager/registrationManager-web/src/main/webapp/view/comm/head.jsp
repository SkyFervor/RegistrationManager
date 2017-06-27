<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skyfervor.framework.utility.HtmlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>活动报名管理系统</title>
<%@ include file="../../view/comm/headJstl.jsp"%>
<script type="text/javascript">
	$(function() {
		pushJob();
		setInterval(pushJob, 1 * 60 * 1000);
	});

	function pushJob() {
		transaction({
			url : basePath + "/usertodo/count",
			async : true,
			callback : function(data) {
				$('#userTodoCount').text(data);
			}
		});
	}

	function changePassword() {
		openDialog({
			title : '修改密码',
			height : 600,
			width : 700,
			url : basePath + '/view/home/password.jsp'
		});
	}
</script>
</head>
<body>
	<div id="top">
		<div id="logo"></div>
		<div>
			<div id="logout">
				欢迎
				<label>${userName}</label>
				<a href="javascript:void(0);" onclick="changePassword();">
					<img src="<%=HtmlHelper.getImageUrl(basePath, "pw.png")%>" style="vertical-align: top" />
					修改密码
				</a>
				&nbsp;
				<a href="${basePath}/login/logout">
					<img src="<%=HtmlHelper.getImageUrl(basePath, "exit.gif")%>" style="vertical-align: top" />
					退出
				</a>
				&nbsp;&nbsp;
			</div>
			<div id="workOrder"
				style="text-align: right; position: absolute; text-align: right; right: 10px; margin-top: 40px;">
				<a href="${basePath}/view/usertodo/usertodo.jsp">
					您有
					<span id="userTodoCount">0</span>
					条通知
				</a>
			</div>
		</div>
		<div id="dialog"></div>
	</div>
</body>
</html>
