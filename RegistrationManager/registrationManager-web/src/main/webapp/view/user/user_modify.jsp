<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skyfervor.framework.utility.HtmlTagHelper"%>
<%@ page import="com.skyfervor.framework.constant.Constant"%>
<%@ page import="com.skyfervor.framework.user.UserVo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户修改</title>
<%@ include file="../../view/comm/headJsCss.jsp"%>
<%@ include file="../../view/comm/headJstl.jsp"%>
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
	padding: 100px 30px;
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
	font: bold 16px/100% arial, sans-serif;
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

.singlepage_right label {
	font:normal 16px/100% arial,sans-serif;
}

.singlepage_right span, input, textarea, select {
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

.singlepage_right textarea, select, [type=text] {
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

.singlepage_button button {
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
	$(function() {
		$('#save').button().click(function() {
			save();
		});
		$('#cancel').button().click(function() {
			cancel();
		});
	});

	function save() {
		var vo = getInput("modify_div");
		var ret = verify(vo);
		if (ret["result"] == false) {
			alert(ret["info"]);
			return;
		}

		var jsonSet = new JsonSet();
		jsonSet.put("input", jsonToString(vo));
		transaction({
			url : basePath + '/user/modify',
			jsonSet : jsonSet,
			async : false,
			callback : function(result) {
				if (result == true) {
					alert("修改成功");
				} else {
					alert("修改失败");
				}
				parent.closeDialog(true, null);
			}
		});
	}

	function cancel() {
		parent.closeDialog();
	}

	function verify(vo) {
		var ret = new Object();

		var userName = vo["userName"];

		ret['result'] = false;
		if (userName == null || "" == userName) {
			ret['info'] = "请输入姓名";
		} else {
			ret['result'] = true;
		}

		return ret;
	}
</script>
</head>
<body>
	<div id="modify_div">
		<table class="singlepage_table">
			<tbody>
				<tr>
					<td colspan="2" class="singlepage_title">
						<label>用户修改</label>
					</td>
				</tr>
				<tr style="display: none;">
					<td>
						<input type="text" id="userId" value="${userVo.userId}" />
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>账号：</label>
					</td>
					<td class="singlepage_right">
						<label>${userVo.loginName}</label>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>姓名：</label>
					</td>
					<td class="singlepage_right">
						<input type="text" id="userName" value="${userVo.userName}" />
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>角色：</label>
					</td>
					<td class="singlepage_right">
						<select id="roleId" name="roleId">
							<%
								UserVo userVo = (UserVo) request.getAttribute("userVo");
							%>
							<%=HtmlTagHelper.getRoleOptions(userVo.getRoleId())%>
						</select>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>状态：</label>
					</td>
					<td class="singlepage_right">
						<select id="status" name="status">
							<%=HtmlTagHelper.getDictionaryOptions(Constant.Dictionary.USER_STATUS,
									userVo.getStatus().intValue(), true)%>
						</select>
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
