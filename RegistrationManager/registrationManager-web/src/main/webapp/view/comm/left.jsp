<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.skyfervor.framework.user.PermissionUtils"%>
<%@ page import="com.skyfervor.framework.utility.HtmlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>菜单</title>
<%@ include file="../../view/comm/headJstl.jsp"%>
<%
	Map<String, Boolean> map = PermissionUtils.getPermissionList();
	request.setAttribute("map", map);
%>
<script src="<%=HtmlHelper.getScriptUrl(basePath, "common/MWeb_menu.js")%>" type="text/javascript"></script>
</head>
<body>
	<div id="left">
		<div style="display: none;">
			<label id="titleText"></label>
			<br />
			<label id="infoText"></label>
		</div>
		<div id="menutitle"></div>
		<ul id="menu">
			<c:if test="${map['/user']}">
				<li class="item" id="userShow">
					<a href="javascript:void(0)" onfocus="this.blur()" class="title" onclick="onclickmenu(this)"
						name="user">用户信息</a>
					<ul id="opt_user" class="optiton" title="用户信息" style="display: none;">
						<li>
							<a href="${basePath}/view/user/user_list.jsp" id="userList" title="">用户列表</a>
						</li>
					</ul>
				</li>
			</c:if>

			<c:if test="${map['/dictionary']}">
				<li class="item" id="dictionaryShow">
					<a href="javascript:void(0)" onfocus="this.blur()" class="title" onclick="onclickmenu(this)"
						name="dictionary">数据字典</a>
					<ul id="opt_dictionary" class="optiton" title="数据字典" style="display: none;">
						<li>
							<a href="${basePath}/view/dictionary/dictionary_list.jsp" id="dictionaryList" title="数据字典列表">数据字典列表</a>
						</li>
					</ul>
				</li>
			</c:if>

			<c:if test="${map['/activity']}">
				<li class="item" id="activityShow">
					<a href="javascript:void(0)" onfocus="this.blur()" class="title" onclick="onclickmenu(this)"
						name="activity">活动信息</a>
					<ul id="opt_activity" class="optiton" title="activity" style="display: none;">
						<li>
							<a href="${basePath}/view/activity/activity_list.jsp" id="activityList" title="活动列表">活动列表</a>
						</li>
						<li>
							<a href="${basePath}/report/activityreportpage" id="activityReport" title="活动报表">报表统计</a>
						</li>
					</ul>
				</li>
			</c:if>
		</ul>
	</div>
</body>
</html>