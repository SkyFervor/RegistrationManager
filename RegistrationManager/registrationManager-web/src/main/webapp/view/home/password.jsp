<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>修改密码</title>
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
	padding: 90px 30px;
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

	function getEncryption(loginName, password) {
		hexcase = 1; // MD5插件设置，大写输出
		var str1 = hex_md5(password);
		var str2 = hex_md5(str1 + loginName);
		return str2;
	}

	function save() {
		var vo = getInput("modify_div");
		var ret = verify(vo);
		if (ret["result"] == false) {
			alert(ret["info"]);
			return;
		}

		vo.newPassword = getEncryption(vo.loginName, vo.newPassword);
		vo.password = getEncryption(vo.loginName, vo.password);
		vo.enPassword = null;
		var jsonSet = new JsonSet();
		jsonSet.put("input", jsonToString(vo));
		transaction({
			url : basePath + '/login/password',
			jsonSet : jsonSet,
			async : false,
			callback : function(result) {
				if (result == true) {
					alert("修改成功");
					parent.window.location = basePath + '/view/home/login.jsp';
				} else {
					alert("修改失败");
				}
			}
		});
	}

	function cancel() {
		parent.closeDialog();
	}

	function verify(vo) {
		var ret = new Object();

		var loginName = vo["loginName"];
		var password = vo["password"];
		var newPassword = vo["newPassword"];
		var enPassword = vo["enPassword"];

		ret['result'] = false;
		if (loginName == null || "" == loginName) {
			ret['info'] = "请输入账号";
		} else if (password == null || "" == password) {
			ret['info'] = "请输入当前密码";
		} else if (newPassword == null || "" == newPassword) {
			ret['info'] = "请输入旧密码";
		} else if (enPassword == null || "" == enPassword) {
			ret['info'] = "请输入确认密码";
		} else if (newPassword != enPassword) {
			ret['info'] = "两次输入的密码不同，请重新输入";
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
						<label>修改密码</label>
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>当前账号：</label>
					</td>
					<td class="singlepage_right">
						<input type="text" id="loginName" />
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>当前密码：</label>
					</td>
					<td class="singlepage_right">
						<input type="password" id="password" />
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>新密码：</label>
					</td>
					<td class="singlepage_right">
						<input type="password" id="newPassword" />
					</td>
				</tr>
				<tr>
					<td class="singlepage_left">
						<label style="color: red;">*</label>
						<label>确认密码：</label>
					</td>
					<td class="singlepage_right">
						<input type="password" id="enPassword" />
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
