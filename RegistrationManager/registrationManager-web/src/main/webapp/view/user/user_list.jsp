<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skyfervor.framework.constant.Constant"%>
<%@ page import="com.skyfervor.framework.utility.HtmlTagHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户列表</title>
<%@ include file="../../view/comm/headJsCss.jsp"%>
<%@ include file="../../view/comm/headJqgrid.jsp"%>
<%@ include file="../../view/comm/headJstl.jsp"%>
<style type="text/css">
#dialog {
	vertical-align: middle;
	text-align: center;
}

#dialog iframe {
	margin-top: 6px;
}

.list_div .ui-jqgrid {
	margin: auto;
}

.list_div div {
	width: auto !important;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#query').button().click(function() {
			query();
		});
		$('#add').button().click(function() {
			add();
		});

		createGrid({
			multiselect : false,
			caption : '用户列表',
			list : 'list',
			page : 'page',
			url : basePath + '/user/userdata',
			colNames : [
				'id', '账号', '姓名', '角色', '状态', '操作' ],
			colModel : [
				{name : 'userId', index : 'userId', width : 80, hidden : true},
				{name : 'loginName', index : 'loginName', width : 80, align : 'center'},
				{name : 'userName', index : 'userName', width : 80, align : 'center'},
				{name : 'roleName', index : 'roleName', width : 80, align : 'center'},
				{name : 'statusName', index : 'statusName', width : 50, align : 'center'},
				{name : 'operate', index : 'operate', width : 50, align : 'center'}
			],
			height : 230,
			button : 'button_div',
			query : 'query_div',
			gridComplete : function() {
				var ids = $('#list').jqGrid("getDataIDs");
				var data = $('#list').jqGrid("getRowData");
				for (var i = 0; i < ids.length; i++) {
					var operate = "<a href='javascript:void(0);' style='color:blue' onclick='modify("
							+ data[i].userId + ")'>修改</a>";
					operate += "&nbsp;&nbsp;&nbsp;";
					operate += "<a href='javascript:void(0);' style='color:red' onclick='del(\""
							+ data[i].loginName + "\", " + data[i].userId + ")'>删除</a>";

					$("#list").jqGrid("setRowData", ids[i], {
						"operate" : operate
					});
				}
			}
		});
		query();
	});

	function query() {
		queryGrid({
			list : 'list',
			url : basePath + '/user/userdata',
			postData : {
				'input' : jsonToString(getInput('form'))
			}
		});
	}

	function add() {
		openDialog({
			title : '用户添加',
			height : 600,
			width : 700,
			url : basePath + '/view/user/user_add.jsp',
			callback : query
		});
	}

	function modify(id) {
		openDialog({
			title : '用户修改',
			height : 600,
			width : 700,
			url : basePath + '/user/modifypage',
			urlParam : id,
			callback : query
		});
	}

	function del(loginName, id) {
		var ret = confirm("您确定要删除账号[ " + loginName + " ]吗？");
		if (ret == false)
			return;
		var jsonSet = new JsonSet();
		jsonSet.put("userId", id);
		transaction({
			url : basePath + '/user/del',
			jsonSet : jsonSet,
			async : false,
			callback : query
		});
	}
</script>
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
			<div id="mainContent" class="main_content_wrap">
				<div id="button_div">
					<button id="query" class="small-button">查询</button>
					<button id="add" class="small-button">添加</button>
				</div>
				<div id="query_div">
					<div id="form" style="width: auto;">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" class="s_layout">
							<tr>
								<th>
									<label>账号</label>
								</th>
								<td>
									<input type="text" id="loginName" />
								</td>
								<th>
									<label>姓名</label>
								</th>
								<td>
									<input type="text" id="userName" />
								</td>
								<th>
									<label>状态</label>
								</th>
								<td>
									<select id="status">
										<option value="">全部</option>
										<%=HtmlTagHelper.getDictionaryOptions(Constant.Dictionary.USER_STATUS, null,
												true)%>
									</select>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="list_div">
					<table id="list"></table>
					<div id="page"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="bottom">
		<jsp:include page="../comm/bottom.jsp"></jsp:include>
	</div>
</body>
</html>