<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skyfervor.framework.utility.HtmlHelper"%>
<script src="<%=HtmlHelper.getScriptUrl(request.getContextPath(), "highcharts/highcharts.js")%>"
	type="text/javascript"></script>

<%-- 导出 --%>
<script src="<%=HtmlHelper.getScriptUrl(request.getContextPath(), "highcharts/exporting.js")%>"
	type="text/javascript"></script>
