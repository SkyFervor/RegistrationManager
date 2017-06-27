<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="com.skyfervor.framework.context.UserContext"%>
<%@ page import="com.skyfervor.framework.utility.CookieUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String basePath = request.getContextPath();

	Cookie cookie = CookieUtils.getCookie(request, "userName");
	String userName = "";
	if (cookie != null)
		userName = URLDecoder.decode(cookie.getValue(), "UTF-8");
%>
<c:set var="basePath" value="<%=basePath%>" />
<c:set var="userName" value="<%=userName%>" />
<script type="text/javascript">
	var basePath = '${basePath}';
</script>
