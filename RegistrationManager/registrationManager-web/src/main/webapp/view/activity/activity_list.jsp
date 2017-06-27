<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skyfervor.framework.utility.HtmlHelper"%>
<%@ page import="com.skyfervor.framework.utility.HtmlTagHelper"%>
<%@ page import="com.skyfervor.rm.vo.RmConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>活动列表</title>
<%@ include file="../../view/comm/headJsCss.jsp"%>
<%@ include file="../../view/comm/headJqgrid.jsp"%>
<%@ include file="../../view/comm/headJstl.jsp"%>
<script src="<%=HtmlHelper.getScriptUrl(basePath, "common/autocomplete.js")%>"
	type="text/javascript"></script>
<script src="<%=HtmlHelper.getScriptUrl(basePath, "common/jquery.ui.datepicker-zh-CN.js")%>"
	type="text/javascript"></script>
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

 		userAutoComplete({
			inputObj : $('#publisherName'),
			hiddenObj : $('#publisherId'),
			sourceUrl : basePath + '/user/suggest'
		});
 		
 		$("#activityTime").datepicker({
 			dateFormat: 'yy-mm-dd',
 			timeFormat: 'hh:mm:ss',
 			autoSize: true,
 			changeYear:true,
 			changeMonth:true,
 			stepHour: 1,
 			stepMinute: 1,
 			stepSecond: 1
 		});
		
		createGrid({
			multiselect: false,
			caption: '活动列表',
			list: 'list',
			page: 'page',
			url: basePath + '/activity/activitydata',
			colNames: [
				'activityId',
				'publisherId',
				'status',
				'活动名称',
				'类型',
				'发布人',
				'活动时间',
				'报名开始',
				'报名结束',
				'活动状态',
				'操作'
			],
			colModel: [
				{name : 'activityId', index : 'activityId', width : 80, hidden : true},
				{name : 'publisherId', index : 'publisherId', width : 80, hidden : true},
				{name : 'status', index : 'status', width : 80, hidden : true},
				{name : 'name', index : 'name', width : 80, align : 'center'},
				{name : 'typeName', index : 'type', width : 80, align : 'center'},
				{name : 'publisherName', index : 'publisher', width : 80, align : 'center'},
				{name : 'activityTime', index : 'activityTime', width : 100, align : 'center'},
				{name : 'beginTime', index : 'beginTime', width : 100, align : 'center'},
				{name : 'endTime', index : 'endTime', width : 100, align : 'center'},
				{name : 'statusName', index : 'status', width : 80, align : 'center'},
				{name : 'operate', index : 'operate', width : 80, align : "center"}
			],
			height: 230,
			button: 'button_div',
			query: 'query_div',
			gridComplete: function() {
				var ids = $('#list').jqGrid("getDataIDs");
				data = $('#list').jqGrid("getRowData");
				for (var i = 0; i < ids.length; i++) {
					var id = data[i].activityId;
					var name = data[i].name;
					var status = data[i].status;
					var publisherId = data[i].publisherId;
					var operate = "<a href='javascript:void(0);' style='color:blue' onclick='detail("
							+ data[i].activityId + ")'>详情</a>";

					var now = new Date();
					var beginStr = data[i].beginTime.replace(eval("/-/gi"), "/");
					var endStr = data[i].endTime.replace(eval("/-/gi"), "/");
					var begin = new Date(Date.parse(beginStr));
					var end = new Date(Date.parse(endStr));
					
					if (publisherId == parseInt('${cookie.userId.value}')) {
						if (status == <%=RmConstant.Dictionary.STATUS_PENDING%> ||
								status == <%=RmConstant.Dictionary.STATUS_REGISTERING%>) {
							operate += "&nbsp;&nbsp;&nbsp;";
							operate += "<a href='javascript:void(0);' style='color:black' onclick='stop(\""
									+ name + "\", " + id + ")'>终止</a>";
						}
						
						if (status == <%=RmConstant.Dictionary.STATUS_PENDING%>) {
							operate += "&nbsp;&nbsp;&nbsp;";
							operate += "<a href='javascript:void(0);' style='color:green' onclick='modify("
									+ id + ")'>修改</a>";
						}
					}
					
					if (status == <%=RmConstant.Dictionary.STATUS_REGISTERING%>){
						operate += "&nbsp;&nbsp;&nbsp;";
						operate += "<a href='javascript:void(0);' style='color:red' onclick='enroll("
								+ id + ")'>报名</a>";
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
		var condition = jsonToString(getInput("query_div"));
		queryGrid({
			list: 'list',
			url: basePath + '/activity/activitydata',
			postData:  {'input': condition}
		});

	}
	
	function add(){
		openDialog({
			title : '活动添加',
			height : 700,
			width : 800,
			url : basePath + '/view/activity/activity_add.jsp',
			callback : query
		});
	}
	
	function detail(id){
		/* var activityVo = {};
		activityVo.activityId = id; */
		openDialog({
			title : '活动详情',
			height : 700,
			width : 800,
			url : basePath + '/activity/detailpage',
			urlParam: id
		});
	}
	
	function enroll(id){
		var jsonSet = new JsonSet();
		jsonSet.put("activityId", id);
		transaction({
			url : basePath + '/activity/enrollpower',
			jsonSet : jsonSet,
			async : false,
			callback : function(result){
				if(result == <%=RmConstant.Activity.ENROLL_SUCCESS%>){
					openDialog({
						title:'活动报名',
						height : 700,
						width : 800,
						url: basePath + '/activity/enrollpage',
						urlParam: id
					});
        		}
				else if(result == <%=RmConstant.Activity.ENROLL_FULL%>)
        			alert("报名人数已满");
        		else if(result == <%=RmConstant.Activity.ENROLL_NOT_IN%>)
        			alert("您不在该活动的人员列表中");
        	}
        });
    }
	
	function modify(id){
		var jsonSet = new JsonSet();
		jsonSet.put("activityId", id);
		transaction({
			url: basePath + '/activity/modifypower',
			jsonSet: jsonSet,
			async : false,
			callback : function(result){
				if(result == <%=RmConstant.Activity.MODIFY_SUCCESS%>){
					openDialog({
						title : '活动修改',
						height : 700,
						width : 800,
						url : basePath + '/activity/modifypage',
						urlParam : id,
						callback : query
					});
				}else if(result == <%=RmConstant.Activity.MODIFY_NOT_PUBLISHER%>)
					alert("您不是活动发布人，不能修改此活动");
				else if(result == <%=RmConstant.Activity.MODIFY_STARTED%>)
					alert("活动已开始报名，不能修改");
			}
		});
	}
	
	function stop(name, id) {
		var ret = confirm("您确定要终止活动[ " + name + " ]吗？");
		if (ret == false)
			return;
		var jsonSet = new JsonSet();
		jsonSet.put("activityId", id);
		transaction({
			url : basePath + '/activity/stop',
			jsonSet : jsonSet,
			async : false,
			callback : function(result) {
				if (result == <%=RmConstant.Activity.MODIFY_SUCCESS%>)
					alert("活动已被终止");
				else if (result == <%=RmConstant.Activity.MODIFY_NOT_PUBLISHER%>)
					alert("您不是活动发布人，不能修改此活动");
				else if (result == <%=RmConstant.Activity.MODIFY_STARTED%>)
					alert("活动已过期");
				else if (result == <%=RmConstant.Activity.MODIFY_FAILED%>)
					alert("活动终止失败");
				query();
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
					<button id="add" class="small-button">添加</button>
				</div>
				<div id="query_div">
					<div id="form" style="width: auto;">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" class="s_layout">
							<tr>
								<th>
									<label>活动名称</label>
								</th>
								<td>
									<input type="text" id="name" />
								</td>
								<th>
									<label>活动类型</label>
								</th>
								<td>
									<select id="type">
										<option value="">全部</option>
										<%=HtmlTagHelper.getDictionaryOptions(RmConstant.Dictionary.ACTIVITY_TYPE,
												null, true)%>
									</select>
								</td>
								<th>
									<label>发布人</label>
								</th>
								<td>
									<input type="text" id="publisherName" />
									<input type="hidden" id="publisherId" />
								</td>
							</tr>
							<tr>
								<th>
									<label>活动时间</label>
								</th>
								<td>
									<input type="text" id="activityTime" />
								</td>
								<th>
									<label>活动状态</label>
								</th>
								<td>
									<select id="status">
										<option value="">全部</option>
										<%=HtmlTagHelper.getDictionaryOptions(RmConstant.Dictionary.ACTIVITY_STATUS,
												null, true)%>
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