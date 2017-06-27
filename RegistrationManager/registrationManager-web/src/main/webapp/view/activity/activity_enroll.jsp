<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>活动报名</title>
<%@ include file="../../view/comm/headJsCss.jsp"%>
<%@ include file="../../view/comm/headJstl.jsp"%>
<style type="text/css">
/* start singlepage_style */
.singlepage_title {
	text-align: center;
	padding-bottom: 20px;
	font: bold 30px/100% arial, sans-serif;
}

.singlepage_table {
	width: 100%;
	padding: 90px 30px;
}

.singlepage_table tr {
	height: 35px;
}

.singlepage_left {
	width: 43%;
	text-align: right;
	vertical-align: middle;
}

.singlepage_left label {
	font: bold 16px/100% arial, sans-serif;
}

.singlepage_right {
	width: 57%;
	text-align: left;
	vertical-align: middle;
}

.singlepage_right label {
	font: normal 16px/100% arial, sans-serif;
}

.singlepage_right textarea {
	box-sizing: border-box;
	margin: auto;
	vertical-align: middle;
	font: 16px/100% arial, sans-serif;
	height: auto;
	resize: none;
	border: 1px solid #919294;
	width: 185px;
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
/* end singlepage_style */
</style>
<script type="text/javascript">
	$(function() {
		$('#enroll').button().click(function() {
			enroll();
		});
		$('#cancel').button().click(function() {
			cancel();
		});
	});

	function enroll(activityUserMapping) {
		var vo = getInput('enroll_div');
		var jsonSet = new JsonSet();
		jsonSet.put("input", jsonToString(vo));

		transaction({
			url : basePath + '/activity/enroll',
			jsonSet : jsonSet,
			async : false,
			callback : function(result) {
				if (result == true) {
					alert("报名成功");
				} else {
					alert("报名失败");
				}
				parent.closeDialog(true, null);
			}
		});
	}

	function cancel() {
		parent.closeDialog(true, null);
	}
</script>
</head>
<body>
	<div id="enroll_div">
		<table class="singlepage_table">
			<tbody>
				<tr>
					<td colspan="2" class="singlepage_title">
						<label>活动报名</label>
					</td>
				</tr>
				<tr style="display: none;">
					<td>
						<input type="text" id="activityId" value="${activityVo.activityId}" />
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
						<label>报名说明：</label>
					</td>
					<td class="singlepage_right">
						<textarea id="description" rows="5">${activityUserMappingVo.description}</textarea>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr></tr>
				<tr>
					<td colspan="2" class="singlepage_button">
						<button id="enroll" class="singlepage_enroll">报名</button>
						<button id="cancel">取消</button>
					</td>
				</tr>
				<tr></tr>
			</tfoot>
		</table>
	</div>
</body>
</html>
