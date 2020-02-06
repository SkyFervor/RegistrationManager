(function($){
$.extend({
ms_DatePicker: function (options) {
            var defaults = {
                YearSelector: ".sel_year",
                MonthSelector: ".sel_month",
                DaySelector: ".sel_day",
                FirstText: "--",
                resTextid: "",
                FirstValue: 0
            };
            var opts = $.extend({}, defaults, options);
            var $YearSelector = $(opts.YearSelector);
            var $MonthSelector = $(opts.MonthSelector);
            var $DaySelector = $(opts.DaySelector);
            if(opts.resTextid){
                var $birthday = $("#"+opts.resTextid);
            }
            var FirstText = opts.FirstText;
            var FirstValue = opts.FirstValue;

            var Yearselectize = $YearSelector[0].selectize;
            var Monthselectize = $MonthSelector[0].selectize;
            var Dayselectize = $DaySelector[0].selectize;
            // 初始化
            // var str = "<option value=\"" + FirstValue + "\">" + FirstText + "</option>";
            // $YearSelector.html(str);
            // $MonthSelector.html(str);
            // $DaySelector.html(str);
            Yearselectize.addOption({text: '年', value: '0',sid:1});
            Monthselectize.addOption({text: '月', value: '0',sid:1});
            //Dayselectize.addOption({text: '日', value: '0',sid:1});

            // 年份列表
            var yearNow = new Date().getFullYear();
            if($YearSelector.attr("rel")){
                var yearSel = $YearSelector.attr("rel");
            }else{

                var yearSel = 0;
            }
			var sid=1;
            for (var i = yearNow; i >= 1960; i--) {
				var sed = yearSel==i?"selected":"";
				// var yearStr = "<option value=\"" + i + "\" " + sed+">" + i + "</option>";
                // $YearSelector.append(yearStr);
                //alert(i);
                sid++;
                Yearselectize.addOption({text: i, value: i,sid:sid});
            }
            Yearselectize.setValue(yearSel);
            // 月份列表
			var monthSel = $MonthSelector.attr("rel");
            if($MonthSelector.attr("rel")){
                var monthSel = $MonthSelector.attr("rel");
                if(monthSel<10){
                    monthSel = monthSel.substr(-1,1);
                }
            }else{
                var monthSel = 0;
            }
            for (var i = 1; i <= 12; i++) {
				//var sed = monthSel==i?"selected":"";
                //var monthStr = "<option value=\"" + i + "\" "+sed+">" + i + "</option>";
                //$MonthSelector.append(monthStr);
                //alert(i);
                sid++;
                Monthselectize.addOption({text: i, value: i,sid:sid});
            }
            Monthselectize.setValue(monthSel);
            
            //Dayselectize.addOption({text: '日1', value: '0',sid:1});
            // 日列表(仅当选择了年月)
            function BuildDay() {
                Dayselectize.clearOptions();
                
                if ($YearSelector.val() == 0 || $MonthSelector.val() == 0) {
                    // 未选择年份或者月份
                    //$DaySelector.html(str);
                    Dayselectize.addOption({text: '日', value: '0',sid:1});
                } else {
                    //$DaySelector.html(str);
                    Dayselectize.addOption({text: '日', value: '0',sid:1});
                    var year = parseInt($YearSelector.val());
                    var month = parseInt($MonthSelector.val());
                    var dayCount = 0;
                    switch (month) {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                        case 8:
                        case 10:
                        case 12:
                            dayCount = 31;
                            break;
                        case 4:
                        case 6:
                        case 9:
                        case 11:
                            dayCount = 30;
                            break;
                        case 2:
                            dayCount = 28;
                            if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
                                dayCount = 29;
                            }
                            break;
                        default:
                            break;
                    }
					
					var daySel = $DaySelector.attr("rel");
                     if($DaySelector.attr("rel")){
                        var daySel = $DaySelector.attr("rel");
                        if(daySel<10){
                            daySel = daySel.substr(-1,1);
                        }
                    }else{
                        var daySel = 0;
                    }
                    for (var i = 1; i <= dayCount; i++) {
						var sed = daySel==i?"selected":"";
						//var dayStr = "<option value=\"" + i + "\" "+sed+">" + i + "</option>";
                        //$DaySelector.append(dayStr);
                        sid++;
                        Dayselectize.addOption({text: i, value: i,sid:sid});
                    }
                }
                if(daySel){
                    Dayselectize.setValue(daySel);
                }else{
                    Dayselectize.setValue('0');
                }
                getbirthday();
            }

            function getbirthday() {
                if(opts.resTextid){
                    var year = Yearselectize.getValue();
                    var mon = Monthselectize.getValue();
                    var day = Dayselectize.getValue();
                    var str = "";
                    if(year&&year!="0"){
                        str += Yearselectize.getValue();
                    }
                    if(mon&&mon!="0"){
                        if(mon<10){
                            str += "0"+Monthselectize.getValue();
                        }else{
                            str += Monthselectize.getValue();
                        }
                        
                    }
                    if(day&&day!="0"){
                         if(mon<10){
                            str += "0"+Dayselectize.getValue();
                        }else{
                            str += Dayselectize.getValue();
                        }
                    }
                    $birthday[0].value = str;
                }
            }

            $MonthSelector.change(function () {
                BuildDay();
            });
            $YearSelector.change(function () {
                BuildDay();
            });
            $DaySelector.change(function () {
                getbirthday();
            });
			if($DaySelector.attr("rel")!=""){
				BuildDay();
			}
        } // End ms_DatePicker
});
})(jQuery);