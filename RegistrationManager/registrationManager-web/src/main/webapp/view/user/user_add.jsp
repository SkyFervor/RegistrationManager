<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skyfervor.framework.constant.Constant"%>
<%@ page import="com.skyfervor.framework.utility.HtmlTagHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户添加</title>
<%@ include file="../../view/comm/headJsCss.jsp"%>
<%@ include file="../../view/comm/headJstl.jsp"%>
<script src="<%=HtmlHelper.getScriptUrl(basePath, "common/md5.js")%>" type="text/javascript"></script>
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
	padding: 60px 30px;
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

.singlepage_right textarea, select, [type=text], [type=password] {
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
	
	function checkLoginName() {
		var loginName = $('#loginName').val();
		var jsonSet = new JsonSet();
		jsonSet.put("loginName", loginName);
		transaction({
			url : basePath + '/user/checkaddpower',
			jsonSet : jsonSet,
			async : true,
			callback : function(result) {
				if (result == <%=Constant.User.ADD_SUCCESS%>) {
					return;
				} else if (result == <%=Constant.User.ADD_FAILURE%>) {
					alert("该账号已存在，请重新输入");
					$('#loginName').focus();
				}
			}
		});
	}

	function getEncryption(loginName, password) {
		hexcase = 1; // MD5插件设置，大写输出
		var str1 = hex_md5(password);
		var str2 = hex_md5(str1 + loginName);
		return str2;
	}

	function save() {
		var vo = getInput("add_div");
		var ret = verify(vo);
		if (ret["result"] == false) {
			alert(ret["info"]);
			return;
		}

		vo.password = getEncryption(vo.loginName, vo.password);
		vo.enPassword = null;
		var jsonSet = new JsonSet();
		jsonSet.put("input", jsonToString(vo));
		transaction({
			url : basePath + '/user/add',
			jsonSet : jsonSet,
			async : false,
			callback : function(result) {
				if (result == true) {
					alert("添加成功");
				} else {
					alert("添加失败");
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

		var roleId = vo["roleId"];
		var loginName = vo["loginName"];
		var userName = vo["userName"];
		var password = vo["password"];
		var enPassword = vo["enPassword"];
		var status = vo["status"];

		ret['result'] = false;
		if (roleId == null || -1 == roleId) {
			ret['info'] = "请选择角色";
		} else if (loginName == null || "" == loginName) {
			ret['info'] = "请输入账号";
		} else if (userName == null || "" == userName) {
			ret['info'] = "请输入姓名";
		} else if (password == null || "" == password) {
			ret['info'] = "请输入密码";
		} else if (enPassword == null || "" == enPassword) {
			ret['info'] = "请输入确认密码";
		} else if (password != enPassword) {
			ret['info'] = "两次输入的密码不同，请重新输入";
		} else if (status == null || -1 == status) {
			ret['info'] = "请选择状态";
		} else {
			ret['result'] = true;
		}

		return ret;
	}
</script>
</head>
<body>
	<div id="add_div">
		<table class="singlepage_table">
			<tbody>
				<tr>
					<td colspan="2" class="singlepage_title">
						<label>用户添加</label>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>账号：</label>
					</td>
					<td class="singlepage_right">
						<input type="text" id="loginName" name="loginName" onblur="checkLoginName();"/>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>姓名：</label>
					</td>
					<td class="singlepage_right">
						<input type="text" id="userName" name="userName" />
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>密码：</label>
					</td>
					<td class="singlepage_right">
						<input type="password" id="password" name="password" />
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>确认密码：</label>
					</td>
					<td class="singlepage_right">
						<input type="password" id="enPassword" name="enPassword" />
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>角色：</label>
					</td>
					<td class="singlepage_right">
						<select id="roleId" name ="roleId">
							<option value="-1">--请选择--</option>
							<%=HtmlTagHelper.getRoleOptions()%>
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
							<option value="-1">--请选择--</option>
							<%=HtmlTagHelper.getDictionaryOptions(Constant.Dictionary.USER_STATUS, null,
									true)%>
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
