var Sys = new Object();
Sys.ie 		= navigator.userAgent.indexOf("MSIE") > 0 ? true : false;
Sys.firefox = navigator.userAgent.indexOf("Firefox") > 0 ? true : false;
Sys.chrome 	= navigator.userAgent.indexOf("Chrome") > 0 ? true : false;
Sys.opera 	= navigator.userAgent.indexOf("Opera") > 0 ? true : false;
Sys.safari 	= navigator.userAgent.indexOf("Safari") > 0 ? true : false;

String.prototype.trim = function() { return this.replace(/(^\s*)|(\s*$)/g,""); }
String.prototype.ltrim = function() { return this.replace(/(^\s*)/g, ""); }
String.prototype.rtrim = function() { return this.replace(/(\s*$)/g, ""); }

//--- 设置cookie
function setCookie(sName,sValue,expireHours) {
	var cookieString = sName + "=" + escape(sValue);
	//;判断是否设置过期时间
	if (expireHours>0) {
		var date = new Date();
		date.setTime(date.getTime + expireHours * 3600 * 1000);
		cookieString = cookieString + "; expire=" + date.toGMTString();
	}
	document.cookie = cookieString + "; path=/";
}

//--- 获取cookie
function getCookie(sName) {
	var aCookie = document.cookie.split("; ");
	for (var j=0; j < aCookie.length; j++) {
		var aCrumb = aCookie[j].split("=");
		if (escape(sName) == aCrumb[0])
			return unescape(aCrumb[1]);
	}
	return null;
}

//页面加载完成  
$(document).ready(function(){  
    //禁止退格键 作用于Firefox、Opera   
    document.onkeypress = banBackSpace;  
    //禁止退格键 作用于IE、Chrome  
    document.onkeydown = banBackSpace;  
});
//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外   
function banBackSpace(e){  
    //alert(event.keyCode)  
    var ev = e || window.event;//获取event对象     
    var obj = ev.target || ev.srcElement;//获取事件源       
    var t = obj.type || obj.getAttribute('type');//获取事件源类型       
    //获取作为判断条件的事件类型   
    var vReadOnly = obj.readOnly;  
    var vDisabled = obj.disabled;  
    //处理undefined值情况   
    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;  
    vDisabled = (vDisabled == undefined) ? true : vDisabled;  
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，    
    //并且readOnly属性为true或disabled属性为true的，则退格键失效    
    var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);  
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效      
    var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";  
    //判断      
    if (flag2 || flag1)   
        event.returnValue = false;//这里如果写 return false 无法实现效果   
}

function StringBuffer() {
  this._strings_ = new Array();
}
StringBuffer.prototype.append = function(str) {
  this._strings_.push(str);
  return this;
}
StringBuffer.prototype.toString = function() {
  return this._strings_.join("");
}

function stringToJson(str) {
    return eval('(' + str + ')');
}
function jsonToString(obj) {
    return JSON.stringify(obj);
}

function getURLParams() {
	
    var args = new Object();
    var query = location.search.substring(1);
    var pairs = query.split("&"); 
    
    for (var i=0; i<pairs.length; i++) {
        var pos = pairs[i].indexOf('=');
        if (pos == -1) { 
        	continue;
        }
        var argname = pairs[i].substring(0, pos); 
        var value = pairs[i].substring(pos + 1);
        value = decodeURIComponent(value);
        args[argname] = value;
    }
    return args;
}

function getURLParam(name) {
    var args = getURLParams();
    return args[name];
}

function appendURLParam(url, name, value) {
	
	var stringBuffer = new StringBuffer();
	
	stringBuffer.append(url);
	
	if (url.indexOf('?') == -1) {
		stringBuffer.append('?')
	} else {
		stringBuffer.append('&')
	}
	
	stringBuffer.append(name)
				.append('=')
				.append(encodeURIComponent(value));
	
	return stringBuffer.toString();
}

function getURLParamObject(urlParam) {
	
	if (urlParam == null) {
		urlParam = 'urlParam';
	}
	
	var urlParam = getURLParam(urlParam);
	if (urlParam != null) {				
		var urlParamObject = stringToJson(urlParam);
		return urlParamObject;
	}
	
	return null;
}

function JsonSet() {
	
	this.entry = new Object();

	this.put = function (key , value) {
		this.entry[key] = value;
	}
 
	this.get = function (key) {
		return key in this.entry ? this.entry[key] : null;
	}
	
	this.remove = function (key) {
		delete this.entry[key];
	}
	
	this.containsKey = function (key) {
		return (key in this.entry);
	}
 
	this.getKeys = function () {
		var keys = new Array();
		for(var key in this.entry) {
			keys.push(key);
		}
		return keys;
	}
	
	this.clean = function () {
		for(var key in this.entry) {
			delete this.entry[key];
		}
	}
	
	this.toString = function () {
		var allArray = new Array();
		for(var key in this.entry) {
			var singleArray = new Array();
			singleArray.push(key);
			singleArray.push('=');
			if (this.entry[key] instanceof Object) {
				singleArray.push(encodeURIComponent(jsonToString(this.entry[key])));
			} else {
				singleArray.push(this.entry[key]);
			}
			allArray.push(singleArray.join(''));
		}
		return allArray.join('&');
	}
}

function Map() {
	
	this.entry = new Object();
	
	this.put = function (key , value) {
		this.entry[key] = value;
	}
 
	this.get = function (key) {
		return key in this.entry ? this.entry[key] : null;
	}
	
	this.remove = function (key) {
		delete this.entry[key];
	}
	
	this.containsKey = function (key) {
		return (key in this.entry);
	}
 
	this.getKeys = function () {
		var keys = new Array();
		for(var key in this.entry) {
			keys.push(key);
		}
		return keys;
	}
	
	this.clean = function () {
		for(var key in this.entry) {
			delete this.entry[key];
		}
	}
	
	this.getEntry = function () {
		return this.entry;
	}
	
	this.setEntry = function (entry) {
		this.entry = entry;
	}
}

function transaction(obj) {
	
	/*
	 * obj.id
	 * obj.url
	 * obj.jsonSet
	 * obj.param
	 * obj.type
	 * obj.cache
	 * obj.async
	 * obj.other
	 * obj.timeout
	*/
	
	if (obj.param != undefined) {
		obj.url = appendURLParam(obj.url, obj.param);
	}
	
	$.ajax({
		
		url: obj.url,
		data: obj.jsonSet == undefined ? '' : obj.jsonSet.toString(),
		type: obj.type == undefined ? 'POST' : obj.type,
		cache: obj.cache == undefined ? false : obj.cache,
		async: obj.async == undefined ? true : obj.async,
		dataType: obj.dataType == undefined ? 'json' : obj.dataType,
		contentType: obj.contentType == undefined ? "application/x-www-form-urlencoded; charset=utf-8" : obj.contentType, 
		timeout: obj.timeout == undefined ? 20000 : obj.timeout,
		
		success: function(data, textStatus, jqXHR) {
			if (obj.callback == undefined) {
				callback(obj.id, data, obj.other);
			} else {
				obj.callback(data, obj.other);
			}
		},

		complete: function(jqXHR, textStatus) {
		},
		
		error: function(jqXHR, textStatus, errorThrown) {
			if (textStatus == 'parsererror') {
				alert('error return value!');
				return;
			} else {
				alert('ajax error info: ' + textStatus);
			}
			var data = new Object();
			data['result'] = 'false';
			if (obj.callback == undefined) {
				callback(obj.id, data, obj.other);
			} else {
				obj.callback(data, obj.other);
			}
		}
	});
}

Date.prototype.Format = function (fmt) {
	if (fmt == null) {
		fmt = "yyyy-MM-dd HH:mm:ss";
	}
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "H+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt))
		    	fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function createGrid(obj) {
	
	/*
	 * obj.list
	 * obj.page
	 * obj.url
	 * obj.colNames
	 * obj.colModel
	 * obj.height
	 * obj.width
	 * obj.button
	 * obj.query
	*/
	
	// 遍历列集合，处理时间，但列中formatter属性为function时，不做处理
	for(var i = 0; i < obj.colModel.length; i++){
		var col = obj.colModel[i];
		if(typeof col.formatter != 'function'){
			if (col.index.substr(col.index.length - 4, 4) == 'Date') {
				col.formatter = function(cellvalue, options, row){
					if(cellvalue == null || cellvalue == '1900-01-01 00:00:00.000' || cellvalue == '1900-01-01 00:00:00'){
						return "";
					}else{
						return cellvalue.substr(0, 10);
					}
				}
			} else if(col.index.substr(col.index.length - 4, 4) == 'Time') {
				col.formatter = function(cellvalue, options, row){
					if(cellvalue == null || cellvalue == '1900-01-01 00:00:00.000' || cellvalue == '1900-01-01 00:00:00'){
						return "";
					}else{
						return cellvalue;
					}
				}
			}
		}
	}
	
	$('#' + obj.list).jqGrid({
		url: obj.url != null ? obj.url : null,
		datatype: 'local',
		mtype: 'post',
		colNames: obj.colNames,
		colModel: obj.colModel,
		grouping: obj.grouping,
		groupingView: obj.groupingView,
		rowNum: obj.rowNum != null ? obj.rowNum : 10,
		rowList: obj.rowList != null ? obj.rowList : [10,20,30,50,100],
		pager: '#' + obj.page,
		sortname: 'id',
		autowidth: false,
		shrinkToFit: true,
		width: obj.width != null ? obj.width : $(window).width() - 208,
		height: obj.height != null ? obj.height : 'auto',
		altRows: obj.altRows != null ? obj.altRows : false, // 是否为交替行表格
		//显示记录数信息，如果这里设置为false,下面的不会显示 recordtext: "第{0}到{1}条, 共{2}条记录",
		//默认显示为{0}-{1} 共{2}条 scroll: false, //滚动翻页，设置为true时翻页栏将不显示  
		viewrecords: obj.viewrecords != null ? obj.viewrecords : true,
		/**这里是排序的默认设置，这些值会根据列表header点击排序时覆盖*/
		sortable: obj.sortable != null ? obj.sortable : false,
//		sortname: 'userRegionMappingId',
//		sortorder: obj.sortorder != null ? obj.sortorder : "desc",
		rownumbers: obj.rownumbers != null ? obj.rownumbers : true, //设置列表显示序号,需要注意在colModel中不能使用rn作为index
		rownumWidth: obj.rownumWidth != null ? obj.rownumWidth : 20, //设置显示序号的宽度，默认为25  
		multiselect: obj.multiselect != null ? obj.multiselect : true , // 是否多选，默认false
		multiboxonly: obj.multiboxonly != null ? obj.multiboxonly : true , //在点击表格row时只支持单选，只有当点击checkbox时才多选，需要multiselect=true是有效
		jsonReader: {
			repeatitems : false
		},
		ondblClickRow: false,
		caption: obj.caption != null ? obj.caption : '查询结果',
		onSelectRow: obj.onSelectRow,
		afterInsertRow:obj.afterInsertRow,
		gridComplete:function(){
			//repaintGrid(obj.height, obj.query, obj.list, obj.button);
			if(obj.width == null){
				$("#" + obj.list).setGridWidth($(window).width() - 208);
				window.setTimeout(function(){
					$("#" + obj.list).setGridWidth($(window).width() - 208);
				}, 1000);
				window.setTimeout(function(){
					$("#" + obj.list).setGridWidth($(window).width() - 208);
				}, 5000);
			}
			if (obj.gridComplete != undefined) {
				obj.gridComplete();
			}
			// grid页码输入域设为只读
			var pages = $(".ui-pg-input");
			if (pages.length > 0) {
				$(".ui-pg-input").attr("readonly","readonly");
			}
		}
	});
	
	$('#' + obj.list).jqGrid('navGrid', '#' + obj.page, {search:false, edit:false, add:false, del:false});
	
	$(window).resize(function(){ 
		if(obj.width == null){
			$("#" + obj.list).setGridWidth($(window).width() - 208);
		}
	});
	
	function repaintGrid(defaultHeight, expander, list, queryDiv, expanderChangeSize) {
		var listHeight = $("#" + list).height();
		var queryHeight = queryDiv != null ? $("#" + queryDiv).height() : 27;
		var expanderHeight = expanderChangeSize != null ? $("#" + expander).height() + expanderChangeSize : $("#" + expander).height();
		//var maxHeight = $(parent.$("iframe")[parent.$("iframe").size()-1]).height() - queryHeight - 98 - expanderHeight;
		var maxHeight = $(document.body).height();
		var maxHeight = $(document.body).height() - queryHeight - 88 - expanderHeight;
		
		var rowNum = $("#" + list).jqGrid('getGridParam', 'rowNum');
		var records = $("#" + list).jqGrid('getGridParam', 'records');
		var page = $("#" + list).jqGrid('getGridParam', 'page');
		
		var actual = rowNum * page > records ? records - rowNum * (page - 1) : rowNum
		
		var beyondRowNum = actual > 10 ? actual - 10 : 0;
		
		var expectHeight = defaultHeight + beyondRowNum * 23;
		var actualHeight = expectHeight;
		
		if (expectHeight > maxHeight) {
			actualHeight = defaultHeight + Math.floor((maxHeight - defaultHeight) / 23) * 23;
		}
		
		$("#" + list + "_div .ui-jqgrid-bdiv").height(actualHeight);
	}
}

function queryGrid(obj) {
	
	/*
	 * obj.list
	 * obj.url
	 * obj.postData
	*/
	
	$('#' + obj.list).jqGrid('setGridParam', {   
		url: obj.url,
		datatype: 'json',
		postData: obj.postData,
		page: obj.page != null ? obj.page : 1,
		loadComplete: obj.loadComplete
	}).trigger('reloadGrid');
}

function selectedGrid(obj) {
	
	/*
	 * obj.list
	*/
	
	return jQuery('#' + obj.list).jqGrid('getGridParam', 'selarrrow');
}

function getGrid(obj) {
	
	/*
	 * obj.list
	 * obj.index
	*/
	
	return jQuery('#' + obj.list).jqGrid('getRowData', obj.index);
}

/*弹出框*/
var dialogCallback = null;

function openDialog(obj) {
	
	/*
	 * obj.title
	 * obj.url
	 * obj.urlParam
	 * obj.height
	 * obj.width
	 * obj.callback
	*/
	
	var height = obj.height != null && obj.height != '' ? obj.height : window.screen.height - 190;
	var width = obj.width != null && obj.width != '' ? obj.width : window.screen.width - 300;
	var url = obj.url;
	if (obj.urlParam != null && obj.urlParam != '') {
		url = appendURLParam(url, 'urlParam', jsonToString(obj.urlParam));
	}
	dialogCallback = obj.callback;
	$('#dialog').dialog({
		showStatus: false,
		showControlBox: false,
		autoOpen: false,
		modal: true,
		height: height,
		width: width,
		title: obj.title,
		close: function () {
			$('#dialog').empty();
		}
	});
	
	$('#dialog').append('<iframe src="'+ url +'" width="98%" height="98%" frameborder="0"></iframe>');
	$(".ui-dialog-buttonpane").hide();
	$(".ui-resizable-handle").hide();
	$('#dialog').dialog('open');
}

function closeDialog(isCallback, param) {
	$('#dialog').dialog('close');
	if (isCallback) {
		if (dialogCallback != null) {
			dialogCallback(param);
		}
	}
}
/*弹出框*/

/*回车响应*/
$(document).keydown(function(event){ 
	// 此处先注释掉，待日后统一保存和查询方法后修改
	/*if (event.keyCode == 13) {
		if (save != null) {
			save();
		} else if (query != null) {
			query();
		}
	} */
}); 
/*回车响应*/

/*文件上传*/
function fileUploadInit(obj) {
	
	/*
	 * obj.fileBrowse
	 * obj.fileUpload
	 * obj.fileName
	 * obj.fileLoading
	 * obj.fileId
	 * obj.url
	 * obj.disableList
	*/
	
	$("#"+obj.fileBrowse).button({icons: {primary: "ui-icon-folder-open"}, text: false}).click(function() {
		fileBrowseOpen(obj.fileBrowse, obj.fileUpload, obj.fileName, obj.fileLoading, obj.fileId, obj.url, obj.disableList, obj.callback);
	});
	
	function fileBrowseOpen(fileBrowse, fileUpload, fileName, fileLoading, fileId, url, disableList, callback) {
		if ($("#"+fileId).val() != null) {
			url += '?id='+ $("#"+fileId).val();
		}
		$("#"+fileUpload).unbind('change');
		$("#"+fileUpload).change(function() {
			fileBrowseClose(fileBrowse, fileUpload, fileName, fileLoading, disableList);
			$.ajaxFileUpload({
		        url: url,
		        secureuri: false,
		        formId:'fileForm',
		        fileElementId: fileUpload,
		        dataType: 'json',
		        success: function (data, status) {
		          if(data.result == 1){
		        	$("#"+fileId).val(data.id);
		            alert("上传成功");
		            for (var i=0; i<disableList.length; i++) {		
		            	$("#"+disableList[i]).removeAttr("disabled");
		            }
		          }else{
 					console.log(data.remark);
					alert("上传失败");
					for (var i=0; i<disableList.length; i++) {		
		            	$("#"+disableList[i]).removeAttr("disabled");
		            }
					if (callback != undefined) {
						callback();
					}
		          }
		          $("#"+fileBrowse).show();
		      	  $("#"+fileLoading).hide();
		          return false;
		        },
		        error: function (data, status, e) {
		        	console.log(data.remark);
		        	alert("上传失败");
		        	$("#"+fileBrowse).show();
		        	$("#"+fileLoading).hide();
		        	for (var i=0; i<disableList.length; i++) {		
		            	$("#"+disableList[i]).removeAttr("disabled");
		            }
		        	if (callback != undefined) {
						callback();
					}
		        	return false;
		        }
		      });
		});
		$("#"+fileUpload).click();
	}
	
	function fileBrowseClose(fileBrowse, fileUpload, fileName, fileLoading, disableList) {
		var fileUpload = $("#"+fileUpload).val();
		var start = fileUpload.lastIndexOf("\\") != -1 ? fileUpload.lastIndexOf("\\") : fileUpload.lastIndexOf("/")
		fileUpload = fileUpload.substring(start + 1, fileUpload.length);
		$("#"+fileName).val(fileUpload);
		$("#"+fileBrowse).hide();
		$("#"+fileLoading).show();
		for (var i=0; i<disableList.length; i++) {		
			$("#"+disableList[i]).attr("disabled", "disabled");
		}
	}
}
/*文件上传*/

function getInput(form) {
	
	var vo = new Object();
	$('#' + form + ' :input').each(function(i){
		// 复选框
		if ($(this).attr('type') == 'checkbox') {
			vo[$(this).attr('id')] = $(this).is(':checked');
		
		// 单选框
		} else if ($(this).attr('type') == 'radio') {
			vo[$(this).attr('name')] = $("input[name=" + $(this).attr('name') + "]:checked").val();
		
		// 正常input
		} else if ($(this).val() != null && $(this).val() != '' && $(this).attr('id') != undefined) {
			if ($(this).attr('id').indexOf('Date') != -1 && $(this).val().length == 10) {
				vo[$(this).attr('id')] = $(this).val() + ' 00:00:00';
			} else if($(this).attr('multiple') != null && $(this).attr('multiple') == 'multiple' && $(this).attr('multiple') != undefined){
				vo[$(this).attr('id')] = $(this).val();
			}else{
				vo[$(this).attr('id')] = $(this).val().trim();
			}
		}
	});
	
	$('#' + form).find("textarea").each(function(i){
		vo[$(this).attr('id')] = $(this).val().trim();
	});
	
	return vo;
}

function setInput(form, data) {
	
	$('#' + form + ' :input').each(function(i){
		if (data[$(this).attr('id')] != null) {	
			if ($(this).attr('id').indexOf('Date') != -1) {
				$(this).val(data[$(this).attr('id')].substr(0,10));
			} else {
				$(this).val(data[$(this).attr('id')]);
			}
		}
	});
}

function emptyInput(form) {
	$('#' + form + ' :input').each(function(i){
			$(this).val(null);
	});
}
/* 按id数组校验非空 */
function notNullCheck(notNullArray) {
	
	for (var i = 0; i < notNullArray.length; i++) {
		var id = notNullArray[i];
		if ( ( $('#' + id).val().trim() == '' 
				|| $('#' + id).val() == '' 
				|| $('#' + id).val() == null
			  ) && $('#' + id).css("display") != "none") {
			alert($('#' + id + '_Label').text() + "不能为空");
			return false;
		}
	}
	return true;
}

function isIdCardCheck(isIdCardArray) {
	
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	
	for (var i = 0; i < isIdCardArray.length; i++) {
		var id = isIdCardArray[i];
		if (!reg.test( $('#' + id).val() )) {
			alert($('#' + id + '_Label').text() + "格式错误");
			return false;
		}
	}
	
	return true;
}

// 此方法已废弃，请勿使用
/* check form中的有id属性的input元素，
 * 若其有对应id为*_Label的属性，则提示该域不能为空 */
function checkFormNotNull(form){
	var flag = true;
	$('#' + form + ' :input[id]').each(function(){
		if(($(this).val() == '' || $(this).val() == null || $(this).val().trim() == '') 
				&& $(this).css("display") != "none"
				&& $("#" + $(this).attr("id")+"_Label").length > 0){
			alert($("#" + $(this).attr("id")+"_Label").text() + "不能为空");
			flag = false;
			return false;
		}
	});
	return flag;
}
/* 日期格式校验，默认按照年-月-日格式校验，可传入自定义校验格式正则 */
function isDate(obj, format, reg){
	var str = obj.val();
	if(str.length != 0){
		if(reg == null || reg == ""){
			reg = /^(\d{4})(-|\/)(\d{2})\2(\d{2})$/;
		}
		var r = str.match(reg);
		if(r == null){
			if(format != null && format != ""){
				alert("您输入的日期格式不正确，请以"+format+"格式输入");
			}else{
				alert('您输入的日期格式不正确，请以年-月-日格式输入，如2015-01-01');
			}
			obj.val('');
			// FireFox在输入域alert后会默认点击鼠标左键，此处判断后处理
			if (Sys.firefox && window.getSelection()) {
				window.getSelection().removeAllRanges();
			}
			return false;
		}
	}
	return true;
}
/* 长度校验（包含中文） 
 * countPerChinese: 每个中文计为多少长度 */
function isOverLength (obj, maxLength, countPerChinese) {
	var realLength = 0;
	var str = obj.val();
	var len = str.length;
	if (maxLength != null && maxLength != "") {
	} else if (obj.attr("maxlength") != null && obj.attr("maxlength").length > 0) {
		maxLength = obj.attr("maxlength");
	} else {
		maxLength = 32;
	}
	for (var i = 0; i < len; i++) {
        var charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) {
        	realLength += 1;
        } else {
        	//realLength += 3;//utf-8
        	if (countPerChinese != null && countPerChinese != "") {
        		realLength += countPerChinese;
        	} else {
        		// MYSQL 4.1 版本及以上varchar以字符为单位存储
        		realLength += 1;
        	}
        }
        if(realLength > maxLength){
        	alert("输入超过限定长度");
			obj.val(str.substr(0, i));
			// FireFox在输入域alert后会默认点击鼠标左键，此处判断后处理
			if (Sys.firefox && window.getSelection()) {
				window.getSelection().removeAllRanges();
			}
			return false;
        }
    }
	return true;
}
/* Email格式校验 */
function isEmail(obj) {
	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if (obj != null && obj.val().length > 0 && !reg.test(obj.val())) {
		alert("Email输入格式错误，请重新输入");
		// FireFox在输入域alert后会默认点击鼠标左键，此处判断后处理
		if (Sys.firefox && window.getSelection()) {
			window.getSelection().removeAllRanges();
		}
		return false;
	}
	return true;
}
/* 电话号码校验 */
function isPhoneNumber(obj){
	var reg = /^[0-9,-]+$/;
	if (obj != null && obj.val().length > 0 && !reg.test(obj.val())) {
		alert("电话号码输入格式错误，请重新输入");
		// FireFox在输入域alert后会默认点击鼠标左键，此处判断后处理
		if (Sys.firefox && window.getSelection()) {
			window.getSelection().removeAllRanges();
		}
		return false;
	}
	return true;
}
/* 数字校验
 * zeroFlag：true-首位可为0,；false-首位不可为0 */
function isNumber(obj, zeroFlag, str){
	var reg;
	if (zeroFlag) {
		reg = /^[0-9]+$/;
	} else {
		reg = /^[1-9][0-9]+$/;
	}
	if (obj != null && obj.val().length > 0 && !reg.test(obj.val())) {
		if (str && str != "") {
			alert(str + "输入格式错误，请重新输入");
		} else {
			alert("电话号码输入格式错误，请重新输入");
		}
		// FireFox在输入域alert后会默认点击鼠标左键，此处判断后处理
		if (Sys.firefox && window.getSelection()) {
			window.getSelection().removeAllRanges();
		}
		return false;
	}
	return true;
}
/* 人员姓名校验 */
function isPersonName(obj){
	var reg = /^[\da-zA-Z\u4E00-\u9FA5]{1,10}$/;
	if (obj != null && obj.val().length > 0 && !reg.test(obj.val())) {
		alert("姓名输入格式错误，请重新输入");
		// FireFox在输入域alert后会默认点击鼠标左键，此处判断后处理
		if (Sys.firefox && window.getSelection()) {
			window.getSelection().removeAllRanges();
		}
		return false;
	}
	return true;
}

function getStartUsefulTime(){
	var now = new Date(); 
	var dayOfWeek = now.getDay() != 0?now.getDay()-1 : 6;
	var endDay = new Date(now - dayOfWeek*24*60*60*1000);
	var monthCurrent = new Date();
	monthCurrent.setDate(1);
	if(monthCurrent.getTime() > endDay.getTime()){
		return monthCurrent;
	}else{
		return endDay;
	}
	
}

//特殊字符校验
function isSpecialCharacterById(columnId){
	var reg = /[^\u0000-\u00ff\u4e00-\u9fa5·~！@#￥%……&*（）——+{}|“：《》？”、。，；‘【】、=-]|\t/;
	var obj = $("#" + columnId);
	if ( obj && reg.test(obj.val())) {
		alert($('#' + columnId + '_Label').text() + "不允许输入特殊字符");
		// FireFox在输入域alert后会默认点击鼠标左键，此处判断后处理
		if (Sys.firefox && window.getSelection()) {
			window.getSelection().removeAllRanges();
		}
		return false;
	}
	return true;
}

//特殊字符
function isSpecialCharacterByIdArray(columnIdArray){
	var reg = /[^\u0000-\u00ff\u4e00-\u9fa5·~！@#￥%……&*（）——+{}|“：《》？”、。，；‘【】、=-]|\t/;
	
	for(var i = 0; i < columnIdArray.length; i++){
		var obj = $("#" + columnIdArray[i]);
		if ( obj && reg.test(obj.val())) {
			alert($('#' + columnIdArray[i] + '_Label').text() + "不允许输入特殊字符");
			// FireFox在输入域alert后会默认点击鼠标左键，此处判断后处理
			if (Sys.firefox && window.getSelection()) {
				window.getSelection().removeAllRanges();
			}
			return false;
		}
	}
	return true;
}