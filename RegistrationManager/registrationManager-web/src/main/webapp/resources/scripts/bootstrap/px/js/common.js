var myMessages = ['info','warning','','success']; 
function showAlertInfo(type,msg)
{
	$(".message").remove();
	$('body').prepend("<div class='col-lg-12'><div class=\"row\"><div class='msg-"+type+" message'><h3>"+msg+"</h3></div></div></div>");
	$('.msg-'+type).animate({top:"0"}, 500);
	setTimeout("hidnAlertInfo()",3000);
	$('.message'). click(function(){	
		$(this).animate({top: -$(this).outerHeight()}, 500);
	});	
}
function hidnAlertInfo(){
	$(".message").animate({top: -$(this).outerHeight()}, 3000)
}

//获取项目根路径
function getProjectPath(){
    var curPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curPath.indexOf(pathName);
    var localhostPath=curPath.substring(0,pos);
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPath+projectName);
}

//加入alert代码
$(function () {
  window.Modal = function () {
    var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
    //var alr = $("#ycf-alert");
    //var ahtml = alr.html();
    var alt;
    var ahtml = "<div class=\"modal-dialog modal-sm\"> \
        <div class=\"modal-content\"> \
          <div class=\"modal-header\" style=\"text-align:left;\"> \
            <button type=\"button\" class=\"close\" data-dismiss=\"modal\"><span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span></button> \
            <h5 class=\"modal-title\"><i class=\"fa fa-exclamation-circle\"></i> [Title]</h5> \
          </div> \
          <div class=\"modal-body\"> \
            <p>[Message]</p> \
          </div> \
          <div class=\"modal-footer\" > \
            <button type=\"button\" class=\"btn btn-primary ok\" data-dismiss=\"modal\">[BtnOk]</button> \
            <button type=\"button\" class=\"btn btn-default cancel\" data-dismiss=\"modal\">[BtnCancel]</button> \
          </div> \
        </div> \
    </div>";
    //关闭时恢复 modal html 原样，供下次调用时 replace 用
    //var _init = function () {
    //	alr.on("hidden.bs.modal", function (e) {
    //		$(this).html(ahtml);
    //	});
    //}();

    /* html 复原不在 _init() 里面做了，重复调用时会有问题，直接在 _alert/_confirm 里面做 */


    var _alert = function (options) {
      alertSelector = options.selector?options.selector:"#px-alert";
      alr = $(alertSelector);
      alr.html(ahtml);	// 复原
      alr.find('.ok').removeClass('btn-success').addClass('btn-primary');
      alr.find('.cancel').hide();
      _dialog(options);

      return {
        on: function (callback) {
          if (callback && callback instanceof Function) {
            alr.find('.ok').click(function () { callback(true) });
          }
        }
      };
    };

    var _confirm = function (options) {
      alertSelector = options.selector?options.selector:"#px-alert";
      alr = $(alertSelector);
      alr.html(ahtml); // 复原
      alr.find('.ok').removeClass('btn-primary').addClass('btn-success');
      alr.find('.cancel').show();
      _dialog(options);

      return {
        on: function (callback) {
          if (callback && callback instanceof Function) {
            alr.find('.ok').click(function () { callback(true) });
            alr.find('.cancel').click(function () { callback(false) });
          }
        }
      };
    };

    var _dialog = function (options) {
      var ops = {
        msg: "提示内容",
        title: "操作提示",
        btnok: "确定",
        btncl: "取消"
      };

      $.extend(ops, options);

      var html = ahtml.replace(reg, function (node, key) {
        return {
          Title: ops.title,
          Message: ops.msg,
          BtnOk: ops.btnok,
          BtnCancel: ops.btncl
        }[key];
      });
      alr.html(html);
      alr.modal({
        width: 500,
        backdrop: 'static'
      });
    }

    return {
      alert: _alert,
      confirm: _confirm
    }

  }();
});

/*
*modal层居中
*方法：middleHeight
*参数：id
*return: 无
*/
  function middleHeight(id) {
    var this_id = '#' + id;                               //获取元素id  
    var h = window.innerHeight;                         //获取浏览器可视区域高度
    //$(this_id).show().css("visibility","hidden");
    var height = $(this_id + ' .modal-content').height();  //获取要设置居中元素的高度
    $(this_id + ' .modal-dialog').css('margin-top',0); // 不同浏览器下modal-dialog的margin值不同，统一设置为0
    $(this_id + ' .modal-content').css('margin-top',(h-height)/2);
    //$(this_id).hide().css("visibility","visible");
  }