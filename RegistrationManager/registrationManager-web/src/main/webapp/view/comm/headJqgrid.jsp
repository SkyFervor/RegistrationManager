<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skyfervor.framework.utility.HtmlHelper"%>
<script
	src="<%=HtmlHelper.getScriptUrl(request.getContextPath(),
					"jqgrid/i18n/grid.locale-en.js")%>"
	type="text/javascript"></script>
<script
	src="<%=HtmlHelper.getScriptUrl(request.getContextPath(),
					"jqgrid/jquery.jqGrid.min.js")%>"
	type="text/javascript"></script>
<link href="<%=HtmlHelper.getStyleUrl(request.getContextPath(), "jqgrid/ui.jqgrid.css")%>"
	rel="stylesheet" type="text/css" />