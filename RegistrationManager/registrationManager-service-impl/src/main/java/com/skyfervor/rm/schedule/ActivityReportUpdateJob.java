/**
 * 
 */
package com.skyfervor.rm.schedule;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.skyfervor.framework.user.UserService;
import com.skyfervor.framework.user.UserVo;
import com.skyfervor.framework.utility.DateUtils;
import com.skyfervor.rm.service.ActivityReportService;
import com.skyfervor.rm.vo.ActivityReportVo;
import com.skyfervor.rm.vo.RmConstant;

/**
 * 
 * @author SkyFervor
 *
 */
public class ActivityReportUpdateJob {

	private final Logger logger = LoggerFactory.getLogger(ActivityReportUpdateJob.class);

	@Autowired
	private ActivityReportService activityReportService;

	@Autowired
	private UserService userService;

	private String begin = null;

	private String end = null;

	public void run() {
		logger.info("Begin ActivityReportUpdateJob");
		try {
			int updateCount = update();
			logger.info("ActivityReportUpdateJob updateCount = " + updateCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int update() throws Exception {
		// 待更新列表
		List<ActivityReportVo> reportList = new LinkedList<>();

		// 从业务表获取前一天的相关记录
		List<ActivityReportVo> reportSrc = queryReportSrc();
		UserVo userVo = new UserVo();
		for (ActivityReportVo vo : reportSrc) {
			// 检查是否已存在对应的记录
			if (!checkReportSrc(vo))
				continue;

			// 设置用户姓名
			userVo.setUserId(vo.getUserId());
			vo.setUserName(userService.getNameById(vo.getUserId()));
			reportList.add(vo);
		}

		return activityReportService.insert(reportList).size();
	}

	private List<ActivityReportVo> queryReportSrc() throws Exception {
		ActivityReportVo reportVo = new ActivityReportVo();
		reportVo.setApplied(RmConstant.Activity.APPLIED_TRUE);

		long todayMillis = DateUtils.getTodayMillis();
		if (begin != null)
			reportVo.setBeginTime(DateUtils.getDateFromStr(begin));
		else
			reportVo.setBeginTime(new Date(todayMillis - 24 * 60 * 60 * 1000));
		if (end != null)
			reportVo.setEndTime(DateUtils.getDateFromStr(end));
		else
			reportVo.setEndTime(new Date(todayMillis));

		/*
		System.out.println("\n\n\n\n\n");
		System.out.println(vo.getBeginTime());
		System.out.println(vo.getEndTime());
		*/
		return activityReportService.queryListSrc(reportVo);
	}

	private boolean checkReportSrc(ActivityReportVo vo) throws Exception {
		ActivityReportVo reportVo = new ActivityReportVo();
		reportVo.setActivityId(vo.getActivityId());
		reportVo.setUserId(vo.getUserId());
		return !activityReportService.contains(reportVo);
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:com/skyfervor/rm/service/rm-service-bean.xml");
		ActivityReportUpdateJob job = context.getBean(ActivityReportUpdateJob.class);
		job.setBegin("2016-01-01");
		job.run();
		System.exit(0);
	}

}
