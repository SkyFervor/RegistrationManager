// --- 隐藏所有
function HideAll() {
	var items = document.getElementsByClassName("optiton");
	for (var j=0; j<items.length; j++) {
		items[j].style.display = "none";
	}
}

// --- 获取ClassName
document.getElementsByClassName = function(cl) {
	var retnode = [];
	var myclass = new RegExp('\\b'+cl+'\\b');
	var elem = this.getElementsByTagName('*');
	for (var j = 0; j < elem.length; j++) {
		var classes = elem[j].className;
		if (myclass.test(classes)) retnode.push(elem[j]);
	}
	return retnode;
}

window.onload = function () {
    resetmenu();
    intimenu();
}

function resetmenu() {
    $("#menu ul.optiton").each(function () {
        if ($(this).children().length == 0)
            $(this).parent().remove();
    });
}

function intimenu() {
    var show_item = "opt_settings";
    if (getCookie("show_item") != null) {
        show_item = "opt_" + getCookie("show_item");
    }
    var obj = document.getElementById(show_item);
    if (obj != null) {
        obj.style.display = "block";
    }

    var menu = document.getElementById('menu');
    if (menu) {
        var links = menu.getElementsByTagName('a');
        for (var k = 0; k < links.length; k++) {
            if (links[k].id != '') {
                links[k].onclick = function () {
                    setCookie('selected_item', this.id);
                }
            }
        }

        var selected_item = getCookie('selected_item');

        //两个页面同时显示时，另为一个页面传menuid过来指定菜单而不走cookie逻辑
        var menuid = getQueryStringByName("menuid");
        if (menuid != "") {
            selected_item = menuid;
            document.getElementById(selected_item).parentNode.parentNode.style.display = "block";
            if (obj != null)
                obj.style.display = "none";
        }
        //end

        if (selected_item != null) {
            var obj = document.getElementById(selected_item);
            if (obj != null) {
                obj.className = 'itemselected';

                var titleText = document.getElementById("titleText");
                var item = document.getElementsByName(getCookie("show_item"));
                titleText.innerHTML = item[0].innerHTML + " » " + obj.innerHTML;

                var infoText = document.getElementById("infoText");
                infoText.innerHTML = obj.title;
            }
        }
    }
}

function onclickmenu(obj)
{
	var o = document.getElementById("opt_" + obj.name);
	if (o.style.display != "block") {
		HideAll();
		o.style.display = "block";
		setCookie("show_item", obj.name);
	}
	else {
		o.style.display = "none";
	}
}


function checknumber()
{
	var a = document.getElementsByTagName("input");
   for (var i=0; i<a.length; i++)
   {   
		if(a[i].type == 'text' && a[i].alt == 'numeric')
		{
			 if(isNaN(a[i].value))
			 {
				  a[i].focus();
				  alert("数据格式不正确，必须是数字！"); 
				  return false;
			 }
		}                         
    }
}

//根据QueryString参数名称获取值
function getQueryStringByName(name)
 {
    var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1) {
        return "";
    }
    return result[1];
}