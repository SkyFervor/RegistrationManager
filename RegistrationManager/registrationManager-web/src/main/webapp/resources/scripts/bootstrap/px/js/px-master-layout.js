/* Set the defaults for DataTables initialisation */

$(document).ready(function() {
    //导航栏左侧展开收起 图标变化特效
    $(".page-sidebar li a").click(function(){
        if($(this).find("i").hasClass('glyphicon-chevron-right')){
            $(this).find("i").removeClass('glyphicon-chevron-right');
            $(this).find("i").addClass('glyphicon-chevron-down');
          //根据左右侧导航的高度定义右侧内容长度，解决导航穿过底部的效果
            setPageContentHeight(($(this).parent().find(">ul>li").length)*38);
        }else{
            $(this).find("i").removeClass('glyphicon-chevron-down');
            $(this).find("i").addClass('glyphicon-chevron-right');
          //根据左右侧导航的高度定义右侧内容长度，解决导航穿过底部的效果
            setPageContentHeight(-($(this).parent().find(">ul>li").length)*38);
        }        
    });

    //搜索区域选择更多
    $("a.o-more").click(function (b) {
        var a = $(this);
        a.parent().prev().find(".v-fold").toggleClass("v-unfold");
        if (a.attr("class") == "o-more") {
            a.html("<b></b>更多")
        } else {
            a.html("<b></b>收起")
        }
    });
    //搜索区域多选
    $("a.o-multiple").click(function (c) {
        var b = $(this), a = b.parents(".attr");
        b.parent().hide();
        a.find("a").removeClass("selected");
        a.addClass("block-multi");
        a.find(".v-fold").addClass("v-unfold");
    });
    //取消多选按钮
    $("a.cancel-btn").click(function (b) {
        var a = $(this), c = a.parents(".attr");b = a.parent();
        b.parents(".attr").removeClass("block-multi");
        c.find(".selected").removeClass("selected");
        c.find(".multi-selected").removeClass("multi-selected");
        c.find(".icon-btn-checked-small").removeClass("icon-btn-checked-small");
        c.find(".v-option").show();
        c.find(".v-fold").removeClass("v-unfold");
    });
    //点击多选的选中框
    $(".param-checkbox").click(function (b) {
        var a = $(this), p = a.parent();
        a.toggleClass("icon-btn-checked-small");
        p.toggleClass("multi-selected");
    });
    //点击确定的结果
    $("a.submit-btn").click(function (b) {
        var a = $(this), c = a.parents(".attr");b = a.parent();
        var result = "";
        c.find("multi-selected").each(function () {
            result += $(this).html()+"\n";
        });
    });
    //点击确定的结果
    function submitRes() {
        var result = "";
        $("#select a[class='selected']").each(function () {
            result += $(this).html()+"\n";
        });
        return result;
    }

    //点击多选的选中框
    $(".nav-tabs a").click(function (b) {
        var a = $(this), p = a.parents(".nav-tabs");
        $("."+a.attr('group')).removeClass("navtab-show");
        $("."+a.attr('group')).addClass("navtab-hidn");
        $("."+a.attr('datatarget')).addClass("navtab-show");
        p.find("li").removeClass("active");
        a.parent().addClass("active")
    });
    //展现菜单
    $(".defaultNavbar").click(function (b) {
        $("#sidebar").toggle();
        //$(".pxnavcat").toggle();
    });
    /*
     *  方法:Array.remove(dx) 通过遍历,重构数组
     *  功能:删除数组元素.
     *  参数:dx删除元素的下标.
     */
    // Array.prototype.remove=function(dx)
    // {
    //     if(isNaN(dx)||dx>this.length){return false;}
    //     for(var i=0,n=0;i<this.length;i++)
    //     {
    //         if(this[i]!=this[dx])
    //         {
    //             this[n++]=this[i]
    //         }
    //     }
    //     this.length-=1
    // }

    //自动补充contain高度，解决菜单太长撑爆底部问题
    var oldPageContentHeight = $(".page-content").height();
    setPageContentHeight();
    function setPageContentHeight(subheight){
    	subheight = subheight?subheight:0;
        //console.log("leftsub:"+subheight);
        var leftHeight = $(".page-sidebar").height();
        leftHeight = leftHeight + subheight;
        var rightHeight = $(".page-content").height();
        //console.log("leftheight:"+leftHeight+"&rightHeight:"+rightHeight);
        if(leftHeight>rightHeight){
            leftHeight = leftHeight;
            $(".page-content").height(leftHeight);
        }else if(rightHeight>oldPageContentHeight){
        	if(leftHeight>oldPageContentHeight){
        		$(".page-content").height(leftHeight);
        	}else{
        		 $(".page-content").height(oldPageContentHeight);
        	}
           
        }
    }


/*
<!--

showPages v1.1
=================================

Infomation
----------------------
Author : Lapuasi
E-Mail : lapuasi@gmail.com
Web : http://www.lapuasi.com
Date : 2005-11-17


Example
----------------------
var pg = new showPages('pg');
pg.pageCount = 12; //定义总页数(必要)
pg.argName = 'p';    //定义参数名(可选,缺省为page)
pg.printHtml();        //显示页数


Supported in Internet Explorer, Mozilla Firefox
*/

// function showPages(name) { //初始化属性
//     this.name = name;      //对象名称
//     this.page = 1;         //当前页数
//     this.pageCount = 1;    //总页数
//     this.argName = 'page'; //参数名
//     this.showTimes = 1;    //打印次数
// }

// showPages.prototype.getPage = function(){ //丛url获得当前页数,如果变量重复只获取最后一个
//     var args = location.search;
//     var reg = new RegExp('[\?&]?' + this.argName + '=([^&]*)[&$]?', 'gi');
//     var chk = args.match(reg);
//     this.page = RegExp.$1;
// }
// showPages.prototype.checkPages = function(){ //进行当前页数和总页数的验证
//     if (isNaN(parseInt(this.page))) this.page = 1;
//     if (isNaN(parseInt(this.pageCount))) this.pageCount = 1;
//     if (this.page < 1) this.page = 1;
//     if (this.pageCount < 1) this.pageCount = 1;
//     if (this.page > this.pageCount) this.page = this.pageCount;
//     this.page = parseInt(this.page);
//     this.pageCount = parseInt(this.pageCount);
// }
activeMenu();
reloadASelectClick();
});


//选择的结果
function RetSelecteds() {
  var result = "";
  $("#select a[class='selected']").each(function () {
      result += $(this).html()+"\n";
  });
  return result;
}

function reloadASelectClick(){
  //筛选条件点击事件
  $("#select a").click(function () {
      var a = $(this), c = a.parents(".attr");b = a.parent();
      if(!b.parents(".attr").hasClass("block-multi")){
          $(this).parents(".f-list").children("a").removeClass("selected");
          $(this).attr("class", "selected");
          //alert(RetSelecteds()); //返回选中结果   弹出
          //searchAll();		//要求在应用的页面中必须自定义这个searchAll()，具体请参考账号管理
      }
  });

}

/**
* 根据传入的地址参数定位二级菜单的显示情况
*/
function activeMenu(){
    var url = window.location.href;
    var host = window.location.host;
    var localhref = url.substring(url.lastIndexOf(host)+host.length, url.length);
    
    $(".bootstrap-admin-navbar-side a").each(function(){
        var a = $(this),b = a.parent(),c = b.parent();
        if(a.attr("href")==localhref){
            a.parent().addClass("active");
            b.parent().addClass("in");
            c.parent().addClass("active");
        }
    });    
}
