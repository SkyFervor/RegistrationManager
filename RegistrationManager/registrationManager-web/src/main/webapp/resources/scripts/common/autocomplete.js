function userAutoComplete(obj){
	/*
	 * obj.inputObj 输入input
	 * obj.hiddenObj 隐藏input
	 * obj.sourceUrl 查询接口
	 */
	if(obj.inputObj == undefined || obj.hiddenObj == undefined || obj.sourceUrl == undefined){
		alert("autocomplete参数定义错误");
		return;
	}
	obj.inputObj.autocomplete({
		source: obj.sourceUrl,
		autoFocus: true,
		minLength: 1,
		select: function(event, ui){
			obj.hiddenObj.val(ui.item ? ui.item.userId : "");
		},
		response: function(event, ui){
			if(ui.content){
				$.each(ui.content, function(i, one){
					one.label = one.userName;
					one.value = one.userName;
				});
			}
		},
		change: function(event, ui){
			if(ui.item){
				obj.hiddenObj.val(ui.item.userId);
				obj.inputObj.val(ui.item.userName);
			}else{
				//if(obj.inputObj.val() != ''){
				//    alert("必须从下拉选择，不能手动输入！");
				//}
				obj.hiddenObj.val('');
				obj.inputObj.val('');
			}
		}
	});
}



function tavernAutoComplete(obj){
	/*
	 * obj.inputObj 输入input
	 * obj.hiddenObj 隐藏input
	 * obj.sourceUrl 查询接口
	 */
	if(obj.inputObj == undefined || obj.sourceUrl == undefined){
		alert("autocomplete参数定义错误");
		return;
	}
	obj.inputObj.autocomplete({
		source: obj.sourceUrl,
		autoFocus: true,
		minLength: 1,
		select: function(event, ui){
			obj.inputObj.val(ui.item ? ui.item.tavernName : "");
		},
		response: function(event, ui){
			if(ui.content){
				$.each(ui.content, function(i, one){
					one.label = one.tavernName;
					one.value = one.tavernName;
				});
			}
		},
		change: function(event, ui){
			if(ui.item){
//				obj.hiddenObj.val(ui.item.regionId);
				obj.inputObj.val(ui.item.label);
//				}
//			}else{
//				//if(obj.inputObj.val() != ''){
//				//    alert("必须从下拉选择，不能手动输入！");
//				//}
//				obj.hiddenObj.val('');
//				obj.inputObj.val('');
			}
		}
	});
}

function regionAutoComplete(obj){
	/*
	 * obj.inputObj 输入input
	 * obj.hiddenObj 隐藏input
	 * obj.sourceUrl 查询接口
	 */
	if(obj.inputObj == undefined || obj.hiddenObj == undefined || obj.sourceUrl == undefined){
		alert("autocomplete参数定义错误");
		return;
	}
	obj.inputObj.autocomplete({
		source: obj.sourceUrl,
		autoFocus: true,
		minLength: 1,
		select: function(event, ui){
			obj.hiddenObj.val(ui.item ? ui.item.regionId : "");
		},
		response: function(event, ui){
			if(ui.content){
				$.each(ui.content, function(i, one){
					one.label = one.regionName + " - " + one.regionNumber;
					one.value = one.regionName;
				});
			}
		},
		change: function(event, ui){
			if(ui.item){
				obj.hiddenObj.val(ui.item.regionId);
				obj.inputObj.val(ui.item.value);
			}else{
				//if(obj.inputObj.val() != ''){
				//    alert("必须从下拉选择，不能手动输入！");
				//}
				obj.hiddenObj.val('');
				obj.inputObj.val('');
			}
		}
	});
}
	function contactInfoAutoComplete(obj){
		/*
		 * obj.inputObj 输入input
		 * obj.hiddenObj 隐藏input
		 * obj.sourceUrl 查询接口
		 */
		if(obj.inputObj == undefined || obj.hiddenObj == undefined || obj.sourceUrl == undefined){
			alert("autocomplete参数定义错误");
			return;
		}
		obj.inputObj.autocomplete({
			source: obj.sourceUrl,
			autoFocus: true,
			minLength: 1,
			select: function(event, ui){
				obj.hiddenObj.val(ui.item ? ui.item.contactInfoId : "");
			},
			response: function(event, ui){
				if(ui.content){
					$.each(ui.content, function(i, one){
						one.label = one.contactName+'/'+one.mobile;
						one.value = one.contactName+'/'+one.mobile;
					});
				}
			},
			change: function(event, ui){
				if(ui.item){
					obj.hiddenObj.val(ui.item.contactInfoId);
					obj.inputObj.val(ui.item.contactName+'/'+ui.item.mobile);
				}else{
					//if(obj.inputObj.val() != ''){
					//    alert("必须从下拉选择，不能手动输入！");
					//}
					obj.hiddenObj.val('');
					obj.inputObj.val('');
				}
			}
		});	
}