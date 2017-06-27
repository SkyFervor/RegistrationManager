package com.skyfervor.rm.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skyfervor.framework.utility.JsonTranslator;
import com.skyfervor.framework.vobase.MapVo;
import com.skyfervor.rm.service.ActivityReportService;
import com.skyfervor.rm.vo.ActivityReportVo;
import com.skyfervor.rm.vo.RmConstant;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ActivityReportService activityReportService;

	@RequestMapping(value = "/activityreportpage")
	public ModelAndView activityReportPage() throws Exception {
		List<ActivityReportVo> userList = activityReportService
				.queryAllUser(new ActivityReportVo());

		List<ActivityReportVo> activityList = activityReportService
				.queryAllActivity(new ActivityReportVo());

		ModelAndView mv = new ModelAndView("/report/report_activity");
		mv.addObject("userList", userList);
		mv.addObject("activityList", activityList);
		return mv;
	}

	@RequestMapping(value = "/activityreportdata")
	@ResponseBody
	public MapVo<String, Object> activityReportData(String input) throws Exception {
		ActivityReportVo vo = JsonTranslator.fromString(input, ActivityReportVo.class);

		List<String> xData = new ArrayList<>();
		List<Integer> yData = new ArrayList<>();

		//根据显示维度的不同 加入不同的数据
		if (vo.getMethod().equals(RmConstant.Report.TYPE_PERSON)) {
			List<ActivityReportVo> data = activityReportService.queryListByPerson(vo);

			for (ActivityReportVo activityReportVo : data) {
				xData.add(activityReportVo.getUserName());
				yData.add(activityReportVo.getCount());
			}
		} else if (vo.getMethod().equals(RmConstant.Report.TYPE_ACTIVITY)) {
			List<ActivityReportVo> data = activityReportService
					.queryListByActivity(vo);

			for (ActivityReportVo activityReportVo : data) {
				xData.add(activityReportVo.getActivityName());
				yData.add(activityReportVo.getCount());
			}
		}
		MapVo<String, Object> mapVo = new MapVo<>();
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("xData", xData);
		hashMap.put("yData", yData);
		mapVo.setMap(hashMap);
		return mapVo;
	}

}
