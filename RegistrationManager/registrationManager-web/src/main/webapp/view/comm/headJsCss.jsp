<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skyfervor.framework.utility.HtmlHelper"%>
<script
	src="<%=HtmlHelper.getScriptUrl(request.getContextPath(),
					"common/jquery-1.8.2.min.js")%>"
	type="text/javascript"></script>
<script
	src="<%=HtmlHelper.getScriptUrl(request.getContextPath(),
					"common/jquery-ui-1.9.1.min.js")%>"
	type="text/javascript"></script>
<script src="<%=HtmlHelper.getScriptUrl(request.getContextPath(), "common/common.js")%>"
	type="text/javascript"></script>

<link href="<%=HtmlHelper.getStyleUrl(request.getContextPath(), "common/MWeb_Main.css")%>"
	rel="stylesheet" type="text/css" />
<link href="<%=HtmlHelper.getStyleUrl(request.getContextPath(), "common/thickbox.css")%>"
	rel="stylesheet" type="text/css" />
<link href="<%=HtmlHelper.getStyleUrl(request.getContextPath(), "common/jquery-ui.css")%>"
	rel="stylesheet" type="text/css" />
<link href="<%=HtmlHelper.getStyleUrl(request.getContextPath(), "common/my.css")%>" rel="stylesheet"
	type="text/css" />