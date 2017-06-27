<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>活动报名管理系统</title>
<%@ include file="../../view/comm/headJsCss.jsp"%>
<%@ include file="../../view/comm/headJstl.jsp"%>
<style type="text/css">
#right {
	height: 420px;
	text-align: center;
}

#right div {
	margin-top: 180px;
	font-size: 3em;
	overflow: visible;
}
</style>
</head>
<body>
	<div id="top">
		<jsp:include page="../comm/head.jsp"></jsp:include>
	</div>
	<div id="body">
		<div id="left">
			<jsp:include page="../comm/left.jsp"></jsp:include>
		</div>
		<div id="right">
			<div>欢迎使用活动报名管理系统！</div>
		</div>
	</div>
	<div id="bottom">
		<jsp:include page="../comm/bottom.jsp"></jsp:include>
	</div>
</body>
</html>
