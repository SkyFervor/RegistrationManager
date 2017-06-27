//<!--
/*
jqgrid pager v1.0
=================================

infomation
---------------------
Author : Sinn_wuxin
Date : 2015-06-30

Example
----------------------
var pg = new showPages('pg');
pg.pageCount = 12; //定义总页数(必要)
pg.argName = 'p';    //定义参数名(可选,缺省为page)
pg.printHtml();        //显示页数


Supported in Internet Explorer, Mozilla Firefox
*/

//指定分页
function turn_page(page,tableid) {
    if(!tableid) tableid = "list";
    $("#"+tableid).setGridParam({ page:page });
    $("#"+tableid).trigger("reloadGrid");
}
//输入指定页码
function input_page(pageCount,tableid) {
    if(!tableid) tableid = "list";
    if (parseInt($("#pager_go_num").val())) {
        if (parseInt($("#pager_go_num").val()) <= parseInt(pageCount)) {
            $("#"+tableid).setGridParam({ page:parseInt($("#pager_go_num").val()) });
        }
        else {
            alert("您指定的页码不存在！");
        }
    }
    $("#"+tableid).trigger("reloadGrid");
}

function showPages(name) { //初始化属性
    this.name = name;      //对象名称
    this.page = 1;         //当前页数
    this.pageCount = 1;    //总页数
    this.argName = 'page'; //参数名
    this.records = 1; //总数量
    this.showTimes = 1;    //打印次数
    this.tableid = "list";
}

showPages.prototype.getPage = function(){ //丛url获得当前页数,如果变量重复只获取最后一个
    var args = location.search;
    var reg = new RegExp('[\?&]?' + this.argName + '=([^&]*)[&$]?', 'gi');
    var chk = args.match(reg);
    this.page = RegExp.$1;
}
// //指定分页
// showPages.prototype.turn_page = function(page) {
//     $("#list").setGridParam({ page:page });
//     $("#list").trigger("reloadGrid");
// }
// //输入指定页码
// showPages.prototype.input_page = function() {
//     if (parseInt($("#page_go_num").val())) {
//         if (parseInt($("#page_go_num").val()) <= parseInt($("#total_pages").html())) {
//             $("#list").setGridParam({ page:parseInt($("#page_go_num").val()) });
//         }
//         else {
//             alert("您指定的页码不存在！");
//         }
//     }
//     $("#list").trigger("reloadGrid");
// }
showPages.prototype.checkPages = function(){ //进行当前页数和总页数的验证
    if (isNaN(parseInt(this.page))) this.page = 1;
    if (isNaN(parseInt(this.pageCount))) this.pageCount = 1;
    if (this.page < 1) this.page = 1;
    if (this.pageCount < 1) this.pageCount = 1;
    if (this.page > this.pageCount) this.page = this.pageCount;
    this.page = parseInt(this.page);
    this.pageCount = parseInt(this.pageCount);
}
showPages.prototype.createHtml = function(mode){ //生成html代码
    var strHtml = '', prevPage = this.page - 1, nextPage = this.page + 1;
    if (mode == '' || typeof(mode) == 'undefined') mode = 0;
    switch (mode) {
        // case 0 : //模式1 (页数,首页,前页,后页,尾页)
        //     strHtml += '<span class="count">Pages: ' + this.page + ' / ' + this.pageCount + '</span>';
        //     strHtml += '<span class="number">';
        //     if (prevPage < 1) {
        //         strHtml += '<span title="First Page">?</span>';
        //         strHtml += '<span title="Prev Page">?</span>';
        //     } else {
        //         strHtml += '<span title="First Page"><a href="javascript:' + this.name + '.toPage(1);">?</a></span>';
        //         strHtml += '<span title="Prev Page"><a href="javascript:' + this.name + '.toPage(' + prevPage + ');">?</a></span>';
        //     }
        //     for (var i = 1; i <= this.pageCount; i++) {
        //         if (i > 0) {
        //             if (i == this.page) {
        //                 strHtml += '<span title="Page ' + i + '">[' + i + ']</span>';
        //             } else {
        //                 strHtml += '<span title="Page ' + i + '"><a href="javascript:' + this.name + '.toPage(' + i + ');">[' + i + ']</a></span>';
        //             }
        //         }
        //     }
        //     if (nextPage > this.pageCount) {
        //         strHtml += '<span title="Next Page">?</span>';
        //         strHtml += '<span title="Last Page">?</span>';
        //     } else {
        //         strHtml += '<span title="Next Page"><a href="javascript:' + this.name + '.toPage(' + nextPage + ');">?</a></span>';
        //         strHtml += '<span title="Last Page"><a href="javascript:' + this.name + '.toPage(' + this.pageCount + ');">?</a></span>';
        //     }
        //     strHtml += '</span><br />';
        //     break;
        // case 1 : //模式1 (10页缩略,首页,前页,后页,尾页)
        //     strHtml += '<span class="count">Pages: ' + this.page + ' / ' + this.pageCount + '</span>';
        //     strHtml += '<span class="number">';
        //     if (prevPage < 1) {
        //         strHtml += '<span title="First Page">?</span>';
        //         strHtml += '<span title="Prev Page">?</span>';
        //     } else {
        //         strHtml += '<span title="First Page"><a href="javascript:' + this.name + '.toPage(1);">?</a></span>';
        //         strHtml += '<span title="Prev Page"><a href="javascript:' + this.name + '.toPage(' + prevPage + ');">?</a></span>';
        //     }
        //     if (this.page % 10 ==0) {
        //         var startPage = this.page - 9;
        //     } else {
        //         var startPage = this.page - this.page % 10 + 1;
        //     }
        //     if (startPage > 10) strHtml += '<span title="Prev 10 Pages"><a href="javascript:' + this.name + '.toPage(' + (startPage - 1) + ');">...</a></span>';
        //     for (var i = startPage; i < startPage + 10; i++) {
        //         if (i > this.pageCount) break;
        //         if (i == this.page) {
        //             strHtml += '<span title="Page ' + i + '">[' + i + ']</span>';
        //         } else {
        //             strHtml += '<span title="Page ' + i + '"><a href="javascript:' + this.name + '.toPage(' + i + ');">[' + i + ']</a></span>';
        //         }
        //     }
        //     if (this.pageCount >= startPage + 10) strHtml += '<span title="Next 10 Pages"><a href="javascript:' + this.name + '.toPage(' + (startPage + 10) + ');">...</a></span>';
        //     if (nextPage > this.pageCount) {
        //         strHtml += '<span title="Next Page">?</span>';
        //         strHtml += '<span title="Last Page">?</span>';
        //     } else {
        //         strHtml += '<span title="Next Page"><a href="javascript:' + this.name + '.toPage(' + nextPage + ');">?</a></span>';
        //         strHtml += '<span title="Last Page"><a href="javascript:' + this.name + '.toPage(' + this.pageCount + ');">?</a></span>';
        //     }
        //     strHtml += '</span><br />';
        //     break;
        // case 2 : //模式2 (前后缩略,页数,首页,前页,后页,尾页)
        //     strHtml += '<span class="count">Pages: ' + this.page + ' / ' + this.pageCount + '</span>';
        //     strHtml += '<span class="number">';
        //     if (prevPage < 1) {
        //         strHtml += '<span title="First Page">?</span>';
        //         strHtml += '<span title="Prev Page">?</span>';
        //     } else {
        //         strHtml += '<span title="First Page"><a href="javascript:' + this.name + '.toPage(1);">?</a></span>';
        //         strHtml += '<span title="Prev Page"><a href="javascript:' + this.name + '.toPage(' + prevPage + ');">?</a></span>';
        //     }
        //     if (this.page != 1) strHtml += '<span title="Page 1"><a href="javascript:' + this.name + '.toPage(1);">[1]</a></span>';
        //     if (this.page >= 6) strHtml += '<span>...</span>';
        //     if (this.pageCount > this.page + 2) {
        //         var endPage = this.page + 2;
        //     } else {
        //         var endPage = this.pageCount;
        //     }
        //     for (var i = this.page - 2; i <= endPage; i++) {
        //         if (i > 0) {
        //             if (i == this.page) {
        //                 strHtml += '<span title="Page ' + i + '">[' + i + ']</span>';
        //             } else {
        //                 if (i != 1 && i != this.pageCount) {
        //                     strHtml += '<span title="Page ' + i + '"><a href="javascript:' + this.name + '.toPage(' + i + ');">[' + i + ']</a></span>';
        //                 }
        //             }
        //         }
        //     }
        //     if (this.page + 3 < this.pageCount) strHtml += '<span>...</span>';
        //     if (this.page != this.pageCount) strHtml += '<span title="Page ' + this.pageCount + '"><a href="javascript:' + this.name + '.toPage(' + this.pageCount + ');">[' + this.pageCount + ']</a></span>';
        //     if (nextPage > this.pageCount) {
        //         strHtml += '<span title="Next Page">?</span>';
        //         strHtml += '<span title="Last Page">?</span>';
        //     } else {
        //         strHtml += '<span title="Next Page"><a href="javascript:' + this.name + '.toPage(' + nextPage + ');">?</a></span>';
        //         strHtml += '<span title="Last Page"><a href="javascript:' + this.name + '.toPage(' + this.pageCount + ');">?</a></span>';
        //     }
        //     strHtml += '</span><br />';
        //     break;
        // case 3 : //模式3 (箭头样式,首页,前页,后页,尾页) (only IE)
        //     strHtml += '<span class="count">Pages: ' + this.page + ' / ' + this.pageCount + '</span>';
        //     strHtml += '<span class="arrow">';
        //     if (prevPage < 1) {
        //         strHtml += '<span title="First Page">9</span>';
        //         strHtml += '<span title="Prev Page">7</span>';
        //     } else {
        //         strHtml += '<span title="First Page"><a href="javascript:' + this.name + '.toPage(1);">9</a></span>';
        //         strHtml += '<span title="Prev Page"><a href="javascript:' + this.name + '.toPage(' + prevPage + ');">7</a></span>';
        //     }
        //     if (nextPage > this.pageCount) {
        //         strHtml += '<span title="Next Page">8</span>';
        //         strHtml += '<span title="Last Page">:</span>';
        //     } else {
        //         strHtml += '<span title="Next Page"><a href="javascript:' + this.name + '.toPage(' + nextPage + ');">8</a></span>';
        //         strHtml += '<span title="Last Page"><a href="javascript:' + this.name + '.toPage(' + this.pageCount + ');">:</a></span>';
        //     }
        //     strHtml += '</span><br />';
        //     break;
        // case 4 : //模式4 (下拉框)
        //     if (this.pageCount < 1) {
        //         strHtml += '<select name="toPage" disabled>';
        //         strHtml += '<option value="0">No Pages</option>';
        //     } else {
        //         var chkSelect;
        //         strHtml += '<select name="toPage" onchange="' + this.name + '.toPage(this);">';
        //         for (var i = 1; i <= this.pageCount; i++) {
        //             if (this.page == i) chkSelect=' selected="selected"';
        //             else chkSelect='';
        //             strHtml += '<option value="' + i + '"' + chkSelect + '>Pages: ' + i + ' / ' + this.pageCount + '</option>';
        //         }
        //     }
        //     strHtml += '</select>';
        //     break;
        // case 5 : //模式5 (输入框)
        //     strHtml += '<span class="input">';
        //     if (this.pageCount < 1) {
        //         strHtml += '<input type="text" name="toPage" value="No Pages" class="itext" disabled="disabled">';
        //         strHtml += '<input type="button" name="go" value="GO" class="ibutton" disabled="disabled"></option>';
        //     } else {
        //         strHtml += '<input type="text" value="Input Page:" class="ititle" readonly="readonly">';
        //         strHtml += '<input type="text" id="pageInput' + this.showTimes + '" value="' + this.page + '" class="itext" title="Input page" onkeypress="return ' + this.name + '.formatInputPage(event);" onfocus="this.select()">';
        //         strHtml += '<input type="text" value=" / ' + this.pageCount + '" class="icount" readonly="readonly">';
        //         strHtml += '<input type="button" name="go" value="GO" class="ibutton" onclick="' + this.name + '.toPage(document.getElementById(\'pageInput' + this.showTimes + '\').value);"></option>';
        //     }
        //     strHtml += '</span>';
        //     break;
        case 6 : //仿照模式2 (前后缩略,页数,首页,前页,后页,尾页)

            strHtml +="<div class=\"input-group pull-right\" style=\" width: 110px;margin:20px 0 20px 10px\"><input id=\"pager_go_num\" class=\"form-control\" name=\"pager_go_num\" type=\"text\" size=\"3\" maxlength=\"3\"><span class=\"input-group-btn\"><button class=\"btn btn-default\" onclick=\"input_page("+this.pageCount+",\'"+this.tableid+"\')\">转到</button></span></div>";
            strHtml +=  "<ul class=\"pagination pull-right\">";   
            if (prevPage < 1) {
                strHtml += '<li title="First Page"><a>&laquo;</a></li>';
                strHtml += '<li title="Prev Page"><a>上一页</a></li>';
            } else {
                strHtml += '<li title="First Page"><a onclick="turn_page(1,\''+this.tableid+'\');">&laquo;</a></li>';
                strHtml += '<li title="Prev Page"><a onclick="turn_page('+prevPage+',\''+this.tableid+'\')">上一页</a></li>';
            }
            //当前页不是第一页默认显示第一页
            if (this.page != 1) strHtml += '<li title="Page 1"><a onclick="turn_page(1,\''+this.tableid+'\');">1</a></li>';
            //大于第三页显示……
            if (this.page >= 3) strHtml += '<li class="disabled"><span>...</span></li>';
            //最大页数大于当前页面+3 最后页面就等于当前页面+3 负责最后页面为最大页面数
            if (this.pageCount > this.page + 3) {
                var endPage = this.page + 3;
            } else {
                var endPage = this.pageCount;
            }
            //计算从第三页开始到最后一页的显示
            for (var i = this.page - 2; i <= endPage; i++) {
                if (i > 0) {
                    if (i == this.page) {
                        //如果页面与等于当前页面显示本页 
                        strHtml += '<li class="active"><a onclick="turn_page('+i+',\''+this.tableid+'\');" title="Page '+ i +'">'+ i +'</a></li>';
                    } else {
                        //如果不是本页，并且不是第一页和最后一页，显示可连接的页面
                        if (i != 1 && i != this.pageCount) {
                            strHtml += '<li ><a onclick="turn_page('+i+',\''+this.tableid+'\');">'+ i +'</a></li>';
                        }
                    }
                }
            }
            //默认当前页面超过6页后 且小于最大页面时显示 ……
            if (this.page + 3 < this.pageCount) strHtml += '<li class="disabled"><span>...</span></li>';
            //当前页面不等于页面总数
            if (this.page != this.pageCount) strHtml += '<li><a onclick="turn_page('+this.pageCount+',\''+this.tableid+'\');">'+ this.pageCount +'</a></li>';
            if (nextPage > this.pageCount) {
                strHtml += '<li><a>下一页</a></li>';
                strHtml += '<li><a>&raquo;</a></li>';
            } else {
                strHtml += '<li title="Next Page"><a onclick="turn_page('+nextPage+',\''+this.tableid+'\');">下一页</a></li>';
                strHtml += '<li><a onclick="turn_page('+this.pageCount+',\''+this.tableid+'\');">&raquo;</a></li>';
            }
            strHtml += '</ul></div>';
            break;
        default :
            strHtml = 'Javascript showPage Error: not find mode ' + mode;
            break;
    }
    return strHtml;
}
showPages.prototype.createUrl = function (page) { //生成页面跳转url
    if (isNaN(parseInt(page))) page = 1;
    if (page < 1) page = 1;
    if (page > this.pageCount) page = this.pageCount;
    var url = location.protocol + '//' + location.host + location.pathname;
    var args = location.search;
    var reg = new RegExp('([\?&]?)' + this.argName + '=[^&]*[&$]?', 'gi');
    args = args.replace(reg,'$1');
    if (args == '' || args == null) {
        args += '?' + this.argName + '=' + page;
    } else if (args.substr(args.length - 1,1) == '?' || args.substr(args.length - 1,1) == '&') {
            args += this.argName + '=' + page;
    } else {
            args += '&' + this.argName + '=' + page;
    }
    return url + args;
}
showPages.prototype.toPage = function(page){ //页面跳转
    var turnTo = 1;
    if (typeof(page) == 'object') {
        turnTo = page.options[page.selectedIndex].value;
    } else {
        turnTo = page;
    }
    self.location.href = this.createUrl(turnTo);
}
showPages.prototype.printHtml = function(mode){ //显示html代码
    this.getPage();
    this.checkPages();
    this.showTimes += 1;
    document.write('<div id="pages_' + this.name + '_' + this.showTimes + '" class="pages"></div>');
    document.getElementById('pages_' + this.name + '_' + this.showTimes).innerHTML = this.createHtml(mode);
    
}
showPages.prototype.getHtml = function(mode){ //显示html代码
    //this.getPage();
    this.checkPages();
    return this.createHtml(mode);
}
showPages.prototype.formatInputPage = function(e){ //限定输入页数格式
    var ie = navigator.appName=="Microsoft Internet Explorer"?true:false;
    if(!ie) var key = e.which;
    else var key = event.keyCode;
    if (key == 8 || key == 46 || (key >= 48 && key <= 57)) return true;
    return false;
}
//-->