package com.skyfervor.rm.schedule;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.skyfervor.rm.service.ActivityService;
import com.skyfervor.rm.service.ActivityUserMappingService;
import com.skyfervor.rm.vo.ActivityUserMappingVo;
import com.skyfervor.rm.vo.ActivityVo;
import com.skyfervor.rm.vo.RmConstant;

/**
 * 
 * @author SkyFervor
 *
 */
public class ActivityStatusUpdateJob {

	private final Logger logger = LoggerFactory.getLogger(ActivityStatusUpdateJob.class);

	@Autowired
	private ActivityService activityService;

	@Autowired
	private ActivityUserMappingService activityUserMappingService;

	private Date now = new Date();

	public void run() {
		logger.info("Begin ActivityStatusUpdateJob");
		try {
			int updateCount = update();
			logger.info("ActvitiyStatusUpdateJob updateCount = " + updateCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int update() throws Exception {
		List<ActivityVo> updateList = new LinkedList<>();

		ActivityVo vo = new ActivityVo();
		vo.setStatus(RmConstant.Dictionary.STATUS_PENDING);
		List<ActivityVo> activityList = activityService.queryList(vo);
		for (ActivityVo activityVo : activityList) {
			if (updatePending(activityVo))
				updateList.add(activityVo);
		}

		vo.setStatus(RmConstant.Dictionary.STATUS_REGISTERING);
		activityList = activityService.queryList(vo);
		for (ActivityVo activityInfoVo : activityList) {
			if (updateRegistering(activityInfoVo))
				updateList.add(activityInfoVo);
		}

		return activityService.update(updateList);
	}

	private boolean updatePending(ActivityVo vo) throws Exception {
		if (now.before(vo.getBeginTime()))
			return false;

		if (now.before(vo.getEndTime()))
			vo.setStatus(RmConstant.Dictionary.STATUS_REGISTERING);
		else
			return updateRegistering(vo);
		return true;
	}

	private boolean updateRegistering(ActivityVo vo) throws Exception {
		if (now.before(vo.getBeginTime())) {
			vo.setStatus(RmConstant.Dictionary.STATUS_PENDING);
			return true;
		}

		if (now.before(vo.getEndTime()))
			return false;

		ActivityUserMappingVo mappingVo = new ActivityUserMappingVo();
		mappingVo.setActivityId(vo.getActivityId());
		mappingVo.setApplied(RmConstant.Activity.APPLIED_TRUE);
		long count = activityUserMappingService.getCount(mappingVo);
		if (count < vo.getMinimum())
			vo.setStatus(RmConstant.Dictionary.STATUS_INVALID);
		else
			vo.setStatus(RmConstant.Dictionary.STATUS_OVERDUE);
		return true;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:com/skyfervor/rm/service/context.xml");
		ActivityStatusUpdateJob job = context.getBean(ActivityStatusUpdateJob.class);
		job.run();
		System.exit(0);
	}
}
