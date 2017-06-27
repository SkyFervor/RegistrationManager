/**
 **v1.0.0 xue
 */
var DataFilter = function() {
	var obj = new Object();
	//根据 ID取对象
	obj.findById = function(id) {
		return document.getElementById(id);
	};
	//根据 NAME取对象，返回是一个数 组
	obj.findByName = function(name) {
		return document.getElementsByName(name);
	};
	//过滤JS对象 ，如果是空或者是未定义 则返回null;
	obj.filterObject = function(obj) {
		if (obj == null || obj == '' || obj == undefined) {
			return '';
		}
		return obj;
	};
	//过滤所有值 
	obj.filterValue = function(values) {
		if (values == null || values == '' || values == undefined) {
			return '';
		}
		return values;
	};
	//过滤对象 和值 
	obj.filterValaueByObject = function(obj) {
		if (this.filterObject(obj) == '') {
			return '';
		}
		var value = obj.value;
		return this.filterValue(value);
	};
	//覆盖之前的HTML内容 
	obj.addHTML = function(id, text) {
		document.getElementById(id).innerHTML = text;
	};
	//往指定的块中追加内容
	obj.appendHTML = function(id, text) {
		var old = document.getElementById(id).innerHTML;
		text = old + '' + text;
		this.addHTML(id, text);
	};
	return obj;
}