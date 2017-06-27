package com.skyfervor.rm.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyfervor.framework.user.UserService;
import com.skyfervor.framework.usertodo.UserTodoService;
import com.skyfervor.framework.usertodo.UserTodoVo;
import com.skyfervor.framework.utility.DateUtils;
import com.skyfervor.framework.utility.NumberUtils;
import com.skyfervor.framework.vobase.PageVo;
import com.skyfervor.rm.dao.ActivityUserMappingDao;
import com.skyfervor.rm.service.ActivityService;
import com.skyfervor.rm.service.ActivityUserMappingService;
import com.skyfervor.rm.vo.ActivityUserMappingVo;
import com.skyfervor.rm.vo.ActivityVo;
import com.skyfervor.rm.vo.RmConstant;

@Service("activityUserMappingService")
public class ActivityUserMappingServiceImpl implements ActivityUserMappingService {

	@Autowired
	private ActivityUserMappingDao activityUserMappingDao;

	@Autowired
	private UserTodoService userTodoService;

	@Autowired
	private UserService userService;

	@Autowired
	private ActivityService activityService;

	@Override
	public PageVo<ActivityUserMappingVo> queryPage(ActivityUserMappingVo vo, Integer page,
			Integer rows) throws Exception {
		PageVo<ActivityUserMappingVo> pageVo = activityUserMappingDao
				.queryPage("activityUserMapping-query-list", vo, page, rows);

		for (ActivityUserMappingVo activityUserMappingVo : pageVo.getRows()) {
			// 设置人员姓名
			String userName = userService.getNameById(activityUserMappingVo.getUserId());
			activityUserMappingVo.setUserName(userName);
		}
		return pageVo;
	}

	@Override
	public ActivityUserMappingVo query(ActivityUserMappingVo vo) throws Exception {
		return activityUserMappingDao.queryOne("activityUserMapping-query-list", vo);
	}

	@Override
	public Byte checkEnrollPower(ActivityUserMappingVo vo) throws Exception {
		// 获取活动信息
		ActivityVo activityVo = activityService.queryById(vo.getActivityId());
		// 尝试获取登录者对应mapping
		ActivityUserMappingVo mappingVo = query(vo);

		// 活动人员设置为非全选
		if (activityVo.getIsAllSelect().equals(RmConstant.Activity.ALLSELECT_FALSE)) {
			// 对应mapping不存在，不在活动人员列表中
			if (mappingVo == null)
				return RmConstant.Activity.ENROLL_NOT_IN;
			// 对应mapping存在，且已报名
			else if (mappingVo.getApplied().equals(RmConstant.Activity.APPLIED_TRUE))
				return RmConstant.Activity.ENROLL_SUCCESS;
		}
		// 活动人员设置为全选
		else if (activityVo.getIsAllSelect().equals(RmConstant.Activity.ALLSELECT_TRUE)) {
			// 已报名
			if (mappingVo != null
					&& mappingVo.getApplied().equals(RmConstant.Activity.APPLIED_TRUE))
				return RmConstant.Activity.ENROLL_SUCCESS;
		}

		// 统计当前已报名人数
		ActivityUserMappingVo con = new ActivityUserMappingVo();
		con.setActivityId(vo.getActivityId());
		con.setApplied(RmConstant.Activity.APPLIED_TRUE);
		int count = getCount(con).intValue();
		// 报名人数已满
		if (Integer.compare(activityVo.getMaximum(), count) <= 0)
			return RmConstant.Activity.ENROLL_FULL;

		return RmConstant.Activity.ENROLL_SUCCESS;
	}

	@Override
	public synchronized Boolean updateToEnroll(ActivityUserMappingVo vo) throws Exception {
		if (checkEnrollPower(vo) != RmConstant.Activity.ENROLL_SUCCESS)
			return false;

		int ret = 0;
		// 尝试获取报名信息
		ActivityUserMappingVo mappingVo = activityUserMappingDao
				.queryOne("activityUserMapping-query-list", vo);
		// 报名信息存在 - 更新
		if (mappingVo != null) {
			mappingVo.setDescription(vo.getDescription());
			mappingVo.setApplied(RmConstant.Activity.APPLIED_TRUE);
			mappingVo.setApplyTime(new Date());
			ret = activityUserMappingDao.updateVo(mappingVo);
		}
		// 报名信息不存在 - 插入
		else {
			vo.setApplied(RmConstant.Activity.APPLIED_TRUE);
			vo.setApplyTime(new Date());
			ActivityUserMappingVo retObj = activityUserMappingDao.insertVo(vo);
			ret = retObj != null ? 1 : 0;
		}

		// 获取活动信息
		ActivityVo activityVo = activityService.queryById(vo.getActivityId());
		// 报名人不为发布人时，推送报名信息给发布人
		if (!NumberUtils.IsEquals(activityVo.getPublisherId(), vo.getUserId())) {
			// 消息推送
			UserTodoVo todoVo = new UserTodoVo();
			todoVo.setBdUserId(activityVo.getPublisherId());
			todoVo.setBdUserName(activityVo.getPublisherName());
			todoVo.setAssociatedDataId(activityVo.getActivityId());
			todoVo.setTodoContent(activityVo.getName() + " : "
					+ DateUtils.formatDateTimeToStr(activityVo.getActivityTime()) + " 有人报名了");
			userTodoService.insertOrUpdate(todoVo);
		}

		return ret != 0 ? true : false;
	}

	@Override
	public Long getCount(ActivityUserMappingVo vo) throws Exception {
		return activityUserMappingDao.queryCount(vo);
	}

}