<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skyfervor.framework.constant.Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>数据字典列表</title>
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
		$('#addType').button().click(function() {
			addType();
		});

		createGrid({multiselect: false,
			caption: '字典列表',
			list : 'list',
			page : 'page',
			url : basePath + '/dictionary/data',
			colNames : [
				'dictionaryTypeId',
				'dictionaryValueId',
				'modify',
				'类型',
				'类型名称',
				'值',
				'值名称',
				'值',
				'排序索引',
				'是否显示',
				'操作'
			],
			colModel : [
				{name : 'dictionaryTypeId', index : 'dictionaryTypeId', width : 80, hidden : true},
				{name : 'dictionaryValueId', index : 'dictionaryValueId', width : 80, hidden : true},
				{name : 'modify', index : 'modify', width : 80, hidden : true},
				{name : 'dictionaryTypeName', index : 'dictionaryTypeName', width : 80, align : 'center'},
				{name : 'dictionaryTypeDesc', index : 'dictionaryTypeDesc', width : 100, align : 'center'},
				{name : 'name', index : 'name', width : 80, align : 'center'},
				{name : 'description', index : 'description', width : 100, align : 'center'},
				{name : 'value', index : 'value', width : 50, align : 'center'},
				{name : 'indexId', index : 'indexId', width : 50, align : 'center'},
				{name : 'displayName', index : 'displayName', width : 50, align : 'center'},
				{name : 'operate', index : 'operate', width : 50, align : 'center'}
			],
			height : 230,
			button : 'button_div',
			query : 'query_div',
			grouping : true,
			groupingView : {
				groupField : [ 'dictionaryTypeName', 'dictionaryTypeId' ],
				groupColumnShow : [ true, false ],
				groupText : [
					"<b>{0}</b>",
					"<a href='javascript:void(0);' style='color:green' onclick='addValue({0})'>添加值</a>"
							+ "&nbsp;&nbsp;&nbsp;"
							+ "<a href='javascript:void(0);' style='color:blue' onclick='modifyType({0})'>修改</a>"
							+ "&nbsp;&nbsp;&nbsp;"
							+ "<a href='javascript:void(0);' style='color:red' onclick='delType({0})'>删除</a>"
				]
			},
			gridComplete : function() {
				var ids = $('#list').jqGrid("getDataIDs");
				var data = $('#list').jqGrid("getRowData");
				for (var i = 0; i < ids.length; i++) {
					if (data[i].dictionaryValueId == "")
						continue;
					
					var valueId = data[i].dictionaryValueId;
					var name = new String(data[i].name);
					var operate = "";
					
					if (data[i].modify == <%=Constant.Dictionary.MODIFY_TRUE%>) {
						operate += "<a href='javascript:void(0);' style='color:blue' onclick='modifyValue("
								+ valueId + ")'>修改</a>";
						operate += "&nbsp;&nbsp;&nbsp;";
						operate += "<a href='javascript:void(0);' style='color:red' onclick='delValue(\""
								+ name + "\", "+ valueId + ")'>删除</a>";
					}
						
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
			url : basePath + '/dictionary/data',
			postData : {
				'input' : jsonToString(getInput('query_div'))
			}
		});
	}

	function addType() {
		openDialog({
			title : '类型添加',
			height : 500,
			width : 600,
			url : basePath + '/view/dictionary/dictionaryType_add.jsp',
			callback : query
		});
	}

	function addValue(id) {
		openDialog({
			title : '值添加',
			height : 600,
			width : 700,
			url : basePath + '/dictionary/addvaluepage',
			urlParam : id,
			callback : query
		});
	}
	
	function modifyType(id) {
		openDialog({
			title : '类型修改',
			height : 500,
			width : 600,
			url : basePath + '/dictionary/modifytypepage',
			urlParam : id,
			callback : query
		});
	}

	function modifyValue(id) {
		openDialog({
			title : '值修改',
			height : 600,
			width : 700,
			url : basePath + '/dictionary/modifyvaluepage',
			urlParam : id,
			callback : query
		});
	}
	
	function delType(id) {
		var ret = confirm("您确定要删除该字典类型吗？");
		if (ret == false)
			return;
		var jsonSet = new JsonSet();
		jsonSet.put("dictionaryTypeId", id);
		transaction({
			url : basePath + '/dictionary/deltype',
			jsonSet : jsonSet,
			async : false,
			callback : function(result) {
				if(!result)
					alert("该类型不能被删除");
				query();
			}
		});
	}

	function delValue(name, id) {
		var ret = confirm("您确定要删除字典值[ " + name + " ]吗？");
		if (ret == false)
			return;
		var jsonSet = new JsonSet();
		jsonSet.put("dictionaryValueId", id);
		transaction({
			url : basePath + '/dictionary/delvalue',
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
					<button id="addType" class="small-button">添加类型</button>
				</div>
				<div id="query_div">
					<div id="form" style="width: auto;">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" class="s_layout">
							<tr>
								<th>
									<label>类型</label>
								</th>
								<td>
									<input type="text" id="dictionaryTypeName" name="dictionaryTypeName" />
								</td>
								<th>
									<label>值</label>
								</th>
								<td>
									<input type="text" id="name" name="name" />
								</td>
								<th>
									<label>是否显示</label>
								</th>
								<td>
									<select id="display" name="display">
										<option value="">全部</option>
										<option value="<%=Constant.Dictionary.DISPLAY_FALSE%>"><%=Constant.Dictionary.DISPLAY_FALSE_DESC%></option>
										<option value="<%=Constant.Dictionary.DISPLAY_TRUE%>"><%=Constant.Dictionary.DISPLAY_TRUE_DESC%></option>
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