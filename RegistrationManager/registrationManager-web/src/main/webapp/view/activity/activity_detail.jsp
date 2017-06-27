<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.skyfervor.rm.vo.RmConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>活动详情</title>
<%@ include file="../../view/comm/headJsCss.jsp"%>
<%@ include file="../../view/comm/headJqgrid.jsp"%>
<%@ include file="../../view/comm/headJstl.jsp"%>
<style type="text/css">
.list_div .ui-jqgrid {
	margin: auto;
}
/* start singlepage_style */
.singlepage_title {
	text-align: center;
	padding-bottom: 20px;
	font: bold 30px/100% arial, sans-serif;
}

.singlepage_table {
	width: 100%;
	padding: 20px 30px;
}

.singlepage_content tr {
	height: 35px;
}

.singlepage_left {
	width: 35%;
	text-align: right;
	vertical-align: middle;
}

.singlepage_left label {
	font:bold 16px/100% arial,sans-serif;
}

.singlepage_right {
	width: 65%;
	text-align: left;
	vertical-align: middle;
}

.singlepage_right label {
	font:normal 16px/100% arial,sans-serif;
}

.singlepage_button {
	text-align: center;
}

.singlepage_button button {
	font: 18px/100% arial, sans-serif;
	padding: 5px 25px;
	margin: 0px 25px;
}

.singlepage_enroll {
	background: #FF9933;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#enroll').button().click(function() {
			enroll();
		});
		$('#close').button().click(function() {
			close();
		});
		
		//人员列表
		createGrid({
			multiselect : false,
			caption : '活动人员',
			list : 'list_per',
			page : 'page_per',
			colNames : [
				'userId',
				'姓名',
			],
			colModel : [
				{name : 'userId', index : 'userId', width : 80, hidden : true},
				{name : 'userName', index : 'userName', width : 80, align : 'center'},
			],
			height : 230,
			width : 500
		});
		
		//报名列表
		createGrid({
			multiselect : false,
			caption : '报名人员',
			list : 'list_reg',
			page : 'page_reg',
			colNames : [
				'userId',
				'姓名',
				'报名时间',
				'报名说明'
			],
			colModel : [
				{name : 'userId', index : 'userId', width : 80, hidden : true},
				{name : 'userName', index : 'userName', width : 80, align : 'center'},
				{name : 'applyTime', index : 'applyTime', width : 120, align : 'center'},
				{name : 'description', index : 'description', width : 120, align : 'center'}
			],
			height : 230,
			width : 500
		});
		query_per();
		query_reg();
	});

	function query_per(){
		var isisAllSelect = ${activityVo.isAllSelect};
		if(isisAllSelect == <%=RmConstant.Activity.ALLSELECT_TRUE%>)
			return;
		
		var activityUserMappingVo = {};
		activityUserMappingVo.activityId = ${activityVo.activityId};
		
		queryGrid({
			list: 'list_per',
			url: basePath + '/activity/personneldata',
			postData : {
				'input' : jsonToString(activityUserMappingVo)
			}
		});
	}
	
	function query_reg(){
		var activityUserMappingVo = {};
		activityUserMappingVo.activityId = ${activityVo.activityId};
		activityUserMappingVo.applied = <%=RmConstant.Activity.APPLIED_TRUE%>;
		
		queryGrid({
			list: 'list_reg',
			url: basePath + '/activity/personneldata',
			postData : {
				'input' : jsonToString(activityUserMappingVo)
			}
		});
	}

	function enroll() {
		var id = $('#activityId').text();
		var jsonSet = new JsonSet();
		jsonSet.put("activityId", id);
		transaction({
			url : basePath + '/activity/enrollpower',
			jsonSet : jsonSet,
			async : false,
			callback : function(result){
				if(result == <%=RmConstant.Activity.ENROLL_SUCCESS%>){
					window.location = basePath + '/activity/enrollpage?urlParam=' + id;
				}
				else if(result == <%=RmConstant.Activity.ENROLL_FULL%>)
        			alert("报名人数已满");
				else if(result == <%=RmConstant.Activity.ENROLL_NOT_IN%>)
        			alert("您不在该活动的人员列表中");
			}
		});
	}

	function close() {
		parent.closeDialog(true, null);
	}
</script>
</head>
<body>
	<div id="detail_div">
		<table class="singlepage_table">
			<tbody class="singlepage_content">
				<tr>
					<td colspan="3" class="singlepage_title">
						<label>活动详情</label>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label>活动名称：</label>
					</td>
					<td class="singlepage_right">
						<label>${activityVo.name}</label>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label>活动类型：</label>
					</td>
					<td class="singlepage_right">
						<label>${activityVo.typeName}</label>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label>发布人：</label>
					</td>
					<td class="singlepage_right">
						<label>${activityVo.publisherName}</label>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label>活动时间：</label>
					</td>
					<td class="singlepage_right">
						<label>
							<fmt:formatDate value="${activityVo.activityTime}" pattern="yyyy-MM-dd HH:mm" />
						</label>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label>报名时间：</label>
					</td>
					<td class="singlepage_right">
						<label>
							<fmt:formatDate value="${activityVo.beginTime}" pattern="yyyy-MM-dd HH:mm" />
						</label>
						<label>至</label>
						<label>
							<fmt:formatDate value="${activityVo.endTime}" pattern="yyyy-MM-dd HH:mm" />
						</label>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label>人数限制：</label>
					</td>
					<td class="singlepage_right">
						<label>${activityVo.minimum}</label>
						<label>至</label>
						<label>${activityVo.maximum}</label>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label>活动说明：</label>
					</td>
					<td class="singlepage_right">
						<c:choose>
							<c:when test="${!empty activityVo.description}">
								<label>${activityVo.description}</label>
							</c:when>
							<c:otherwise>
								<label>无</label>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label>活动人员：</label>
					</td>
					<td class="singlepage_right">
						<c:set var="ALLSELECT_TRUE" value="<%=RmConstant.Activity.ALLSELECT_TRUE%>" />
						<c:if test="${activityVo.isAllSelect==ALLSELECT_TRUE}">
							<label>全部</label>
						</c:if>
					</td>
				</tr>
			</tbody>
			<tr>
				<td colspan="2">
					<c:if test="${activityVo.isAllSelect!=ALLSELECT_TRUE}">
						<div class="list_div">
							<table id="list_per"></table>
							<div id="page_per"></div>
						</div>
					</c:if>
				</td>
			</tr>
			<tbody class="singlepage_content">
				<tr>
					<td class="singlepage_left">
						<label>已报名人员：</label>
					</td>
				</tr>
			</tbody>
			<tr>
				<td colspan="2">
					<div class="list_div">
						<table id="list_reg"></table>
						<div id="page_reg"></div>
					</div>
				</td>
			</tr>
			<tfoot class="singlepage_content">
				<tr>
					<td style="display: none;">
						<label id="activityId">${activityVo.activityId}</label>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="singlepage_button">
						<c:set var="status_reg" value="<%=RmConstant.Dictionary.STATUS_REGISTERING%>" />
						<c:if test="${activityVo.status == status_reg}">
							<button id="enroll" class="singlepage_enroll">报名</button>
						</c:if>
						<button id="close">关闭</button>
					</td>
				</tr>
				<tr></tr>
			</tfoot>
		</table>
	</div>
</body>
</html>