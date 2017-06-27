<%@page import="com.skyfervor.framework.utility.HtmlTagHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skyfervor.framework.constant.Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>消息提醒</title>
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

		createGrid({
			list : 'list',
			page : 'page',
			url : basePath + '/usertodo/usertododata',
			colNames : [
				'userTodoId',
				'associatedDataId',
				'活动信息',
				'bduserId',
				'bdUserName',
				'状态',
				'操作'
			],
			colModel : [
				{name : 'userTodoId', index : 'userTodoId', width : 80, align : 'center', hidden : true},
				{name : 'associatedDataId', index : 'associatedDataId', width : 80, align : 'center', hidden : true},
				{name : 'todoContent', index : 'todoContent', width : 80, align : 'center'},
				{name : 'bdUserId', index : 'bdUserId', width : 50, align : 'center', hidden : true},
				{name : 'bdUserName', index : 'bdUserName', width : 50, align : 'center', hidden : true},
				{name : 'todoStatusName', index : 'todoStatusName', width : 50, align : 'center'},
				{name : 'operate', index : 'operate', width : 70, align : "center"}
			],
			height : 230,
			//width: $("#mainContent").width(),
			button : 'button_div',
			query : 'query_div',
			gridComplete : function() {
				var ids = $("#list").jqGrid("getDataIDs");
				data = $('#list').jqGrid("getRowData");
				for (var i = 0; i < ids.length; i++) {
					console.log(data[i]);
					var id = ids[i];
					var operate = "<a href='javascript:void(0);' style='color:blue' onclick='detail("
							+ data[i].userTodoId
							+ ","
							+ data[i].associatedDataId
							+ ")'>详情</a>&nbsp;&nbsp;&nbsp;";
					$("#list").jqGrid("setRowData", ids[i], {
						"operate" : operate
					});
				}
			}
		});
		query();
	});

	function detail(id, assId) {
		var userTodoVo = {};
		userTodoVo.userTodoId = id;
		userTodoVo.associatedDataId = assId;
		openDialog({
			title : '报名详情',
			height : 700,
			width : 800,
			url : basePath + '/usertodo/detailpage',
			urlParam : userTodoVo,
			callback : query,
			close : query
		});
	}

	function query() {
		var condition = jsonToString(getInput('query_div'));
		queryGrid({
			list : 'list',
			url : basePath + '/usertodo/usertododata',
			postData : {
				'input' : condition
			}
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
				</div>
				<div id="query_div">
					<div id="form" style="width: auto;">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" class="s_layout">
							<tr>
								<th>
									<label>活动报名通知</label>
								</th>
								<td>
									<select id="todoStatus">
										<option value="">全部</option>
										<%=HtmlTagHelper.getDictionaryOptions(Constant.Dictionary.USERTODO_STATUS,
												(int) Constant.UserTodo.UNREAD, true)%>
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
