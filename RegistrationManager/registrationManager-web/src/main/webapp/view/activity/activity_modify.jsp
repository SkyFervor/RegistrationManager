<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skyfervor.framework.utility.HtmlTagHelper"%>
<%@ page import="com.skyfervor.rm.vo.RmConstant"%>
<%@ page import="com.skyfervor.rm.vo.ActivityVo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>活动修改</title>
<%@ include file="../../view/comm/headJsCss.jsp"%>
<%@ include file="../../view/comm/headJstl.jsp"%>
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
.ui-multiselect-menu span {
	box-sizing: border-box;
	height: 22px;
	margin: auto;
	vertical-align: middle;
	font: 16px/100% arial, sans-serif;
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

.singlepage_table tr {
	height: 35px;
}

.singlepage_left {
	width: 38%;
	text-align: right;
	vertical-align: middle;
}

.singlepage_left label {
	font:bold 16px/100% arial,sans-serif;
}

.singlepage_right {
	width: 62%;
	text-align: left;
	vertical-align: middle;
}

.singlepage_right button {
	box-sizing: border-box;
	height: 22px;
	margin: auto;
	vertical-align: middle;
	width: 185px !important;
}

.singlepage_right span,input,textarea,select {
	box-sizing: border-box;
	height: 22px;
	margin: auto;
	vertical-align: middle;
	font: 16px/100% arial, sans-serif !important;
}

.singlepage_right textarea {
	height: auto;
	resize: none;
}

.singlepage_right textarea,select,[type=text] {
	border: 1px solid #919294;
	width: 185px;
}

.singlepage_right .button_clear {
	height: inherit;
	padding: 2px 5px;
}

.singlepage_button {
	text-align: center;
}

.singlepage_button button{
	font: 18px/100% arial, sans-serif;
	padding: 5px 25px;
	margin: 0px 25px;
}

.singlepage_save {
	background: #FF9933;
}
/* end singlepage_style */
</style>
<script type="text/javascript">
	var userCount = <%=HtmlTagHelper.getUserCount()%>;
	$(function() {
		$('.button_clear').each(function(i) {
			$(this).button().click(function() {
				clearPrevInput(this);
			});
		});
		$('#save').button().click(function() {
			save();
		});
		$('#cancel').button().click(function() {
			cancel();
		});

		multiple();
		initTimeControl();
		initPageInfo();
	});
	
	//初始化页面加载
	function initPageInfo() {
		var isAllSelect = ${activityVo.isAllSelect};
		if(isAllSelect == 1){
			$('#isAllSelect').attr('checked', 'checked');
			$('#activityPersonId').multiselect('disable');
		}
	}
	
	//初始化时间设置
	function initTimeControl() {
		var nowDateTime = new Date();
		$('#activityTime').datetimepicker({
			changeYear :true,
			changeMonth : true,
			showSecond : true,
			dateFormat : 'yy-mm-dd',
			timeFormat : 'HH:mm:ss',
			minDateTime : nowDateTime,
			hourGrid : 4,
			minuteGrid : 10,
			secondGrid : 10,
			timeText : '时间：',
			hourText : '小时:',
			minuteText : '分:',
			secondText : '秒:',
			onSelect : function(selectedDateTime) {
				var temp = new Date(selectedDateTime);
				$('#beginTime').datetimepicker("option", "maxDate",
						$('#activityTime').datetimepicker('getDate'));
				$('#endTime').datetimepicker("option", "maxDate",
						$('#activityTime').datetimepicker('getDate'));
			}
		});

		$('#beginTime').datetimepicker({
			changeYear :true,
			changeMonth : true,
			showSecond : true,
			dateFormat : 'yy-mm-dd',
			timeFormat : 'HH:mm:ss',
			minDateTime : nowDateTime,
			hourGrid : 4,
			minuteGrid : 10,
			secondGrid : 10,
			timeText : '时间：',
			hourText : '小时:',
			minuteText : '分:',
			secondText : '秒:',
			onSelect : function(selectedDateTime) {
				$('#endTime').datetimepicker("option", "minDate",
						$('#beginTime').datetimepicker('getDate'));
			}
		});
		
		$('#endTime').datetimepicker({
			changeYear :true,
			changeMonth : true,
			showSecond : true,
			dateFormat : 'yy-mm-dd',
			timeFormat : 'HH:mm:ss',
			minDateTime : nowDateTime,
			hourGrid : 4,
			minuteGrid : 10,
			secondGrid : 10,
			timeText : '时间：',
			hourText : '小时:',
			minuteText : '分:',
			secondText : '秒:',
			onSelect : function(selectedDateTime) {
				$('#beginTime').datetimepicker('option', 'maxDate',
						$('#endTime').datetimepicker('getDate'));
			}
		});
	}

	//清空日历控件  并清除限制范围
	function clearPrevInput(o) {
		$(o).prev().val("");
		if (o.id == "clearBeginTime") {
			var endTime = $('#endTime').val();
			$('#endTime').datetimepicker("option", "minDate", null);
			$('#endTime').val(endTime);
		} else if (o.id == "clearEndTime") {
			var beginTime = $('#beginTime').val();
			$('#beginTime').datetimepicker('option', 'maxDate', null);
			$('#beginTime').val(beginTime);
		} else if (o.id == "clearActivityTime") {
			var endTime = $('#endTime').val();
			var beginTime = $('#beginTime').val();
			$('#beginTime').datetimepicker('option', 'maxDate', null);
			$('#endTime').datetimepicker('option', 'maxDate', null);
			$('#beginTime').val(beginTime);
			$('#endTime').val(endTime);
		}
	}
	
	function save() {
		var vo = getInput("modify_div");
		var verifyResponse = verifyEverything(vo);
		if (verifyResponse["isOk"] == false) {
			alert(verifyResponse["info"]);
			return;
		}
		
		//类型转换
		vo["isAllSelect"] = vo["isAllSelect"] ? 1 : 0;
		//通过当前时间 判断状态
		var activityTime = vo["activityTime"];
		var beginTime = vo["beginTime"];
		var endTime = vo["endTime"];
		vo["status"] = getStatus(beginTime, endTime);
		if (vo['isAllSelect'] == 1) {
			vo['activityPersonId'] = null;
		}

		var jsonSet = new JsonSet();
		jsonSet.put("input", jsonToString(vo));
		transaction({
			url : basePath + '/activity/modify',
			jsonSet : jsonSet,
			async : false,
			callback : function(result){
				if(result == true){
					alert("修改成功");
				}else{
					alert("修改失败");
				}
				parent.closeDialog(true, null);
			}
		});
	}
	
	function cancel() {
		parent.closeDialog();
	}
	
	function verifyEverything(vo) {
		var ret = new Object();
		
		var activityName = vo["name"];
		var type = vo["type"];
		var count = vo["isAllSelect"] ? userCount : (vo['activityPersonId'] == null ? 0
				: vo['activityPersonId'].length);
		var isAllSelect = vo['isAllSelect'];
		var activityTime = vo["activityTime"];
		var beginTime = vo["beginTime"];
		var endTime = vo["endTime"];
		var minimum = vo["minimum"];
		var maximum = vo["maximum"];
		
		if (activityName == null || "" == activityName) {
			ret['isOk'] = false;
			ret['info'] = "请输入标题";
		} else if (type == null || -1 == type) {
			ret['isOk'] = false;
			ret['info'] = "请选择活动类型";
		} else if (activityTime == null || "" == activityTime) {
			ret['isOk'] = false;
			ret['info'] = "请选择活动时间";
		} else if (beginTime == null || "" == beginTime) {
			ret['isOk'] = false;
			ret['info'] = "请选择报名开始时间";
		} else if (endTime == null || "" == endTime) {
			ret['isOk'] = false;
			ret['info'] = "请选择报名结束时间 ";
		} else if (minimum == null || "" == minimum) {
			ret['isOk'] = false;
			ret['info'] = "请输入最少人数";
		} else if (isAllSelect == false) {
			if (count == 0) {
				ret['isOk'] = false;
				ret['info'] = "请选择人员";
			}
		} else if (maximum == null || "" == maximum) {
			ret['isOk'] = false;
			ret['info'] = "请输入最大人数";
		} else {
			ret['isOk'] = true;
			ret['info'] = "验证是否为空通过";
		}
		
		if (ret['isOk'] == false) {
			return ret;
		}
		ret = verifyDate(activityTime, beginTime, endTime);
		
		return ret;
	}
	
	//通过当前时间判断状态（1：未开始，2：报名中，3：已过期）
	function getStatus(beginTime, endTime) {
		var beginDate = timeToDate(beginTime);
		var endDate = timeToDate(endTime);
		var nowDate = new Date();
		if (nowDate < beginDate) {
			return <%=RmConstant.Dictionary.STATUS_PENDING%>;
		} else if (nowDate < endDate){
			return <%=RmConstant.Dictionary.STATUS_REGISTERING%>;
		} else
			return -1;
	}
	
	//验证报名时间、活动时间、当前时间是否符合要求
	function verifyDate(activityTime, beginTime, endTime) {
		var ret = new Object();
		var nowDate = new Date();
		var activityDate = timeToDate(activityTime);
		var beginDate = timeToDate(beginTime);
		var endDate = timeToDate(endTime);
		if (beginDate > endDate) {
			ret["isOk"] = false;
			ret["info"] = "报名开始时间不能小于报名结束时间";
		} else if (activityDate < endDate) {
			ret["isOk"] = false;
			ret["info"] = "活动开始时间不能早于报名结束时间";
		} else if (nowDate > endDate) {
			ret["isOk"] = false;
			ret["info"] = "报名结束时间不能早于当前时间";
		} else {
			ret["isOk"] = true;
			ret["info"] = "时间验证成功";
		}
		return ret;
	} 
	
	//把字符串时间转换成Date时间
	function timeToDate(time) {
		return new Date(time.replace(/\-/g, "\/"));
	}
	
	//设置人员限制的选择列表样式
	function multiple() {
		$("#activityPersonId").multiselect({
			noneSelectedText : "--请选择--",
			checkAllText : "全选",
			uncheckAllText : '全不选',
			selectedText : "# of # selected",
			selectedList : 3,
			onClick : function() {
				alert("I was just checked!");
			}
		});
		var idList = ${activityVo.activityPersonId};
		$('#activityPersonId').val(idList);
		$('#activityPersonId').multiselect("refresh");
	}
	
	
	//设置人数 验证并修改
	function setNumberRight() {
		alert("setNumberRight");
		var minimum = $('#minimum');
		var maximum = $('#maximum');
		var topNumber = 0;
		if($('#isAllSelect').is(':checked') == false){
			topNumber = ($('#activityPersonId').val()) == null ? 0
					: $('#activityPersonId').val().length;
		} else {
			topNumber = userCount;
		}
		if (minimum.val() != null && parseInt(minimum.val()) > topNumber) {
			minimum.val(topNumber);
		}
		if (maximum.val() != null && parseInt(maximum.val()) > topNumber) {
			maximum.val(topNumber);
		}
	}

	function justAllowNumber(o) {
		o.value = o.value.replace(/[^0-9-]+/, '');
		var number = parseInt(o.value);
		var minimum = parseInt($('#minimum').val());
		var maximum = parseInt($('#maximum').val());
		var topNumber = 0;
		if($('#isAllSelect').is(':checked') == false){
			topNumber = ($('#activityPersonId').val()) == null ? 0
					: $('#activityPersonId').val().length;
		} else {
			topNumber = userCount;
		}
		if (minimum != null && number < minimum) {
			o.value = minimum;
		}
		if (minimum != null && number > maximum) {
			o.value = maximum;
		}
		if (number > topNumber) {
			o.value = topNumber;
		}
	}

	function isNum(e) {
		var k = window.event ? e.keyCode : e.which;
		if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
		} else {
			if (window.event) {
				window.event.returnValue = false;
			} else {
				e.preventDefault(); //for firefox 
			}
		}
	}
	
	//判断人员限制是否全选，如果全选则隐藏勾选人员
	function isAllSelect() {
		var isChecked = $('#isAllSelect').is(':checked');
		if (isChecked) {
			$('#activityPersonId').multiselect('disable')
		} else {
			$('#activityPersonId').multiselect('enable');
		}
	}
</script>
</head>
<body>
	<div id="modify_div">
		<table class="singlepage_table">
			<tbody>
				<tr>
					<td colspan="2" class="singlepage_title">
						<label>活动修改</label>
					</td>
				</tr>
				<tr style="display: none;">
					<td>
						<input type="text" id="activityId" value="${activityVo.activityId}" />
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>活动名称：</label>
					</td>
					<td class="singlepage_right">
						<input type="text" id="name" value="${activityVo.name}" />
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red">*</label>
						<label>活动类型：</label>
					</td>
					<td class="singlepage_right">
						<select id="type" name="type">
							<%
								ActivityVo activityVo = (ActivityVo)request.getAttribute("activityVo");
							%>
							<%=HtmlTagHelper.getDictionaryOptions(RmConstant.Dictionary.ACTIVITY_TYPE,
									activityVo.getType(), true)%>
						</select>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>活动时间：</label>
					</td>
					<td class="singlepage_right">
						<input type="text" id="activityTime"
							value="<fmt:formatDate	value ='${activityVo.activityTime}' pattern='yyyy-MM-dd HH:mm:ss' />" />
						<input type="button" id="clearActivityTime" class="button_clear" value="清空" />
					</td>
				</tr>
				<tr>
					<td rowspan="2" class="singlepage_left">
						<label style="color: red;">*</label>
						<label>报名时间：</label>
					</td>
					<td class="singlepage_right">
						<input type="text" id="beginTime"
							value="<fmt:formatDate value ='${activityVo.beginTime}' pattern='yyyy-MM-dd HH:mm:ss' />" />
						<input type="button" id="clearBeginTime" class="button_clear" value="清空" />
						<span>至</span>
					</td>
				</tr>
				<tr>
					<td class="singlepage_right">
						<input type="text" id="endTime"
							value="<fmt:formatDate value='${activityVo.endTime}' pattern='yyyy-MM-dd HH:mm:ss' />" />
						<input type="button" id="clearEndTime" class="button_clear" value="清空" />
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>活动人员：</label>
					</td>
 					<td class="singlepage_right">
 	                    <select id="activityPersonId" name="activityPersonId" multiple="multiple"
 	                    	onblur="setNumberRight()"> 
	        				<%=HtmlTagHelper.getUserOptions()%>
					    </select> 					      								     										                                   						                                                                                  
						<label>
							<input type="checkbox" id="isAllSelect" value="<%=RmConstant.Activity.ALLSELECT_TRUE%>"
								onchange="isAllSelect()" /> 
							<span>全选</span>
						</label>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>人数限制：</label>
					</td>
					<td class="singlepage_right">
						<input type="text" id="minimum" value="${activityVo.minimum}"
							onkeypress="return isNum(event)" onblur="justAllowNumber(this)" style="width: 75px;" />
						<span style="margin: 0px 2px;">至</span>
						<input type="text" id="maximum" value="${activityVo.maximum}"
							onkeypress="return isNum(event)" onblur="justAllowNumber(this)" style="width: 75px;" />
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label>发布人：</label>
					</td>
					<td class="singlepage_right">
						<span>${activityVo.publisherName}</span>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label>活动说明：</label>
					</td>
					<td class="singlepage_right">
						<textarea id="description" name="description" rows="5">${activityVo.description}</textarea>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr></tr>
				<tr>
					<td colspan="2" class="singlepage_button">
						<button id="save" class="singlepage_save">保存</button>
						<button id="cancel">取消</button>
					</td>
				</tr>
				<tr></tr>
			</tfoot>
		</table>
	</div>
</body>
</html>