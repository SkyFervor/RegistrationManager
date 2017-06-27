<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.skyfervor.framework.utility.HtmlTagHelper"%>
<%@ page import="com.skyfervor.rm.vo.RmConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>活动报表</title>
<%@ include file="../../view/comm/headJsCss.jsp"%>
<%@ include file="../../view/comm/headJstl.jsp"%>
<%@ include file="../../view/comm/headHighcharts.jsp"%>
<script
	src="<%=HtmlHelper.getStyleUrl(basePath, "mutiselect/multiselectSrc/jquery.multiselect.js")%>"
	type="text/javascript"></script>
<link
	href="<%=HtmlHelper.getStyleUrl(basePath, "mutiselect/multiselectSrc/jquery.multiselect.css")%>"
	rel="stylesheet" type="text/css" />

<script src="<%=HtmlHelper.getScriptUrl(basePath, "common/jquery-ui-timepicker-addon.js")%>"
	type="text/javascript"></script>
<link href="<%=HtmlHelper.getStyleUrl(basePath, "common/jquery-datetimepicker.css")%>"
	rel="stylesheet" type="text/css" />
<style type="text/css">
.container {
	min-width: 310px;
	height: 400px;
	margin: 0 auto;
}

table * {
	vertical-align: middle;
}
</style>
<script type="text/javascript">
	$(function() {
		$("button[name = 'clearTime']").each(function(i) {
			$(this).button().click(function() {
				clearPrevInput(this);
				drawReport();
			});
		});
		initTimeControl();
		multiple();
		displayViewAtChoose();
		drawReport();
	});
	
	function clearPrevInput(o) {
		$(o).prev().val("");
		if (o.id == "clearBeginTime") {
			$('#endTime').datetimepicker("option", "minDate", null);
		} else if (o.id == "clearEndTime") {
			$('#beginTime').datetimepicker('option', 'maxDate', null);
		}
	}
	
	//初始化时间设置
	function initTimeControl(){
		var beginTimePicker = $('#beginTime');
		var endTimePicker = $('#endTime');
		
		beginTimePicker.datetimepicker({
			changeYear :true,
			changeMonth : true,
			showSecond : true,
			dateFormat : 'yy-mm-dd',
			timeFormat : 'HH:mm:ss',
			hourGrid : 4,
			minuteGrid : 10,
			secondGrid : 10,
			timeText : '时间：',
			hourText : '小时:',
			minuteText : '分:',
			secondText : '秒:',
			onSelect : function(selectedDateTime) {
				endTimePicker.datetimepicker('option', 'minDate',
						beginTimePicker.datetimepicker('getDate'));
				drawReport();
			}
		});
		endTimePicker.datetimepicker({
			changeYear :true,
			changeMonth : true,
			showSecond : true,
			dateFormat : 'yy-mm-dd',
			timeFormat : 'HH:mm:ss',
			hourGrid : 4,
			minuteGrid : 10,
			secondGrid : 10,
			timeText : '时间：',
			hourText : '小时:',
			minuteText : '分:',
			secondText : '秒:',
			onSelect : function(selectedDateTime) {
				beginTimePicker.datetimepicker('option', 'maxDate',
						endTimePicker.datetimepicker('getDate'));
				drawReport();
			}
		});
	}
	
	//设置人员多选样式
	function multiple() {
		$("#idList").multiselect({
			noneSelectedText : "--请选择--",
			checkAllText : "全选",
			uncheckAllText : '全不选',
			selectedText : "# of # selected",
			selectedList : 5
		});
	}
	
	//绘制报表
	function drawReport(){
		var vo = getQueryVo();
		verifyResponse = verifyAllInput(vo);
		if(verifyResponse['isOk'] == false){
			alert(verifyResponse['info']);
			return ;
		}
 		getDataAndRefreshReport(vo);
	}
	
	//获取数据 并 刷新报表
	function getDataAndRefreshReport(vo){
		var jsonSet = new JsonSet();
		jsonSet.put('input', jsonToString(vo));
		transaction({
			url : basePath + '/report/activityreportdata',
			jsonSet : jsonSet,
			async : true,
			callback : function(data){
				refreshReport(data.map.xData, data.map.yData);
			}
		});
	}
	
	//获取查询数据
	function getQueryVo(){
		var vo = new Object();
		
		var now = new Date();
		vo['beginTime'] = $('#beginTime').val();
		vo['endTime'] = $('#endTime').val();
		
		var idList = $('#idList');
		if(idList != null){
			vo['idList'] = idList.val();
		}else{
			vo['idList'] = null;
		}
		
		vo['method'] = $('input:radio:checked').val();
		
		return vo;
	}
	
	//把字符串时间转换成Date时间
	function timeToDate(time){
		return new Date(time.replace(/\-/g, "\/"));
	}
	
	//验证所有的请求参数
	function verifyAllInput(vo){
		var ret = new Object();
		var beginDate = timeToDate(vo['beginTime']);
		var endDate = timeToDate(vo['endTime']);
		if(beginDate > endDate){
			ret['isOk'] = false;
			ret['info'] = '开始时间不能晚于结束时间';
		}else{
			ret['isOk'] = true;
			ret['info'] = '验证成功';
		}
		return ret;
		
	}
	
	//刷新页面的选择 （人员/活动 ）部分
	function displayViewAtChoose(){
		var method = $('input:radio:checked').val();
		if(method == <%=RmConstant.Report.TYPE_PERSON%>){
			$('#chooseInfo').text("活动人员");
			$('#idList').html($('#userOptions').html());
		} else if(method == <%=RmConstant.Report.TYPE_ACTIVITY%>){
			$('#chooseInfo').text("活动名称");
			$('#idList').html($('#activityOptions').html());
		}
		$('#idList').multiselect('refresh');
	}

	//维度改变 修改数据的选择 和 重新绘制图表
	function changeDimensionality() {
		displayViewAtChoose();
		drawReport();
	}

	//根据不同的维度（method）和数据刷新报表
	function refreshReport(xData, yData) {
		var method = $('input:radio:checked').val();
		if (method == <%=RmConstant.Report.TYPE_PERSON%>) {
			getReportByPerson(xData, yData);
		} else if (method == <%=RmConstant.Report.TYPE_ACTIVITY%>) {
			getReportByActivity(xData, yData);
		}
	}

	//根据人员绘制柱状图表
	function getReportByPerson(xData, yData) {
		$('#container').highcharts({
			chart : {
				type : 'column'
			},
			title : {
				text : '人员活动统计报表'
			},
			xAxis : {
				categories : xData
			},
			yAxis : {
				title : {
					text : '活动数'
				}
			},
			series : [ {
				name : '活动数',
				data : yData
			} ],
			exporting:{
                enabled:false
            },
            credits: {
                enabled: false
            }
		});
	}

	//根据活动绘制折线图表
	function getReportByActivity(xData, yData) {
		$('#container').highcharts({
			title : {
				text : '活动统计报表',
				x : -20
			//center
			},
			xAxis : {
				categories : xData
			},
			yAxis : {
				title : {
					text : '活动人员数（人）'
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
				borderWidth : 0
			},
			series : [ {
				name : '人数',
				data : yData
			} ],
			exporting:{
                enabled:false
            },
            credits: {
                enabled: false
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
				<div id="query_div">
					<div id="form" style="width: auto;">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" class="s_layout">
							<tr>
								<th>
									<label>活动日期</label>
								</th>
								<td>
									<input type="text" id="beginTime" />
									<button name="clearTime" id="clearBeginTime">清空</button>
								</td>
								<th>至</th>
								<td>
									<input type="text" id="endTime" />
									<button name="clearTime" id="clearEndTime">清空</button>
								</td>
								
								<th>
									<label id="chooseInfo">活动人员</label>
								</th>
								<td>
									<select id="idList" name="idList" multiple="multiple" size="5" style="width: auto;"
										onchange="drawReport()">
										<%-- <%=HtmlSubHelper.getUserDropdownListOptionByReportDatabase()%> --%>
									</select>
									
									<select id="userOptions" style="display: none;">
										<c:forEach var="user" items="${userList}">
											<option value="${user.userId}">${user.userName}</option>
										</c:forEach>
									</select>
									
									<select id="activityOptions" style="display: none;">
										<c:forEach var="activity" items="${activityList}">
											<option value="${activity.activityId}">${activity.activityName}</option>
										</c:forEach>
									</select>
								</td>
								<th>
									<label>统计维度</label>
								</th>
								<td>
									<input type="radio" name="dimensionality" id="byPerson" style="width: auto;"
										onclick="changeDimensionality()" value="<%=RmConstant.Report.TYPE_PERSON%>"
										checked="checked" />
									<label>按人</label>
									<input type="radio" name="dimensionality" id="byActivity" style="width: auto;"
										onclick="changeDimensionality()" value="<%=RmConstant.Report.TYPE_ACTIVITY%>" />
									<label>按活动</label>
								</td>
							</tr>
							<tr>
							</tr>
						</table>
						<div id="container" class="container"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="bottom">
		<jsp:include page="../comm/bottom.jsp"></jsp:include>
	</div>
</body>
</html>
