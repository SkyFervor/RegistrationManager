package com.skyfervor.rm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyfervor.framework.dictionary.DictionaryUtils;
import com.skyfervor.framework.user.UserService;
import com.skyfervor.framework.utility.NumberUtils;
import com.skyfervor.framework.vobase.PageVo;
import com.skyfervor.rm.dao.ActivityDao;
import com.skyfervor.rm.dao.ActivityUserMappingDao;
import com.skyfervor.rm.service.ActivityService;
import com.skyfervor.rm.vo.ActivityUserMappingVo;
import com.skyfervor.rm.vo.ActivityVo;
import com.skyfervor.rm.vo.RmConstant;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private ActivityUserMappingDao activityUserMappingDao;

	@Autowired
	private UserService userService;

	@Override
	public PageVo<ActivityVo> queryPage(ActivityVo vo, Integer page, Integer rows)
			throws Exception {
		PageVo<ActivityVo> pageVo = activityDao.queryPage("activity-query-list", vo, page, rows);

		for (ActivityVo activityVo : pageVo.getRows()) {
			// 设置发布人姓名
			String userName = userService.getNameById(activityVo.getPublisherId());
			activityVo.setPublisherName(userName);
			// 设置类型名
			String typeName = DictionaryUtils
					.getValueDescription(RmConstant.Dictionary.ACTIVITY_TYPE, activityVo.getType());
			activityVo.setTypeName(typeName);
			// 设置状态名
			String statusName = DictionaryUtils.getValueDescription(
					RmConstant.Dictionary.ACTIVITY_STATUS, activityVo.getStatus());
			activityVo.setStatusName(statusName);

		}
		return pageVo;
	}

	@Override
	public ActivityVo queryById(ActivityVo vo) throws Exception {
		return queryById(vo.getActivityId());
	}

	@Override
	public ActivityVo queryById(Long activityId) throws Exception {
		ActivityVo vo = activityDao.queryById(activityId);

		// 设置发布人姓名
		String userName = userService.getNameById(vo.getPublisherId());
		vo.setPublisherName(userName);
		// 设置类型名
		String typeName = DictionaryUtils.getValueDescription(RmConstant.Dictionary.ACTIVITY_TYPE,
				vo.getType());
		vo.setTypeName(typeName);
		// 设置状态名
		String statusName = DictionaryUtils
				.getValueDescription(RmConstant.Dictionary.ACTIVITY_STATUS, vo.getStatus());
		vo.setStatusName(statusName);
		return vo;
	}

	@Override
	public ActivityVo queryWithPersonnelById(Long activityId) throws Exception {
		ActivityVo vo = queryById(activityId);
		if (vo == null)
			return null;

		List<Long> idList = new ArrayList<Long>();
		// 人员限制不是全选，需要返回人员列表
		if (vo.getIsAllSelect() == RmConstant.Activity.ALLSELECT_FALSE) {
			ActivityUserMappingVo mappingVo = new ActivityUserMappingVo();
			mappingVo.setActivityId(activityId);

			List<ActivityUserMappingVo> mappingList = activityUserMappingDao.queryList(mappingVo);
			for (ActivityUserMappingVo item : mappingList) {
				idList.add(item.getUserId());
			}
		}
		vo.setActivityPersonId(idList);
		return vo;
	}

	@Override
	public ActivityVo insert(ActivityVo vo) throws Exception {
		ActivityVo retVo = activityDao.insertVo(vo);
		if (retVo == null || retVo.getActivityId() == null)
			return null;

		if (vo.getIsAllSelect().equals(RmConstant.Activity.ALLSELECT_FALSE)
				&& vo.getActivityPersonId() != null) {
			ActivityUserMappingVo mappingVo = new ActivityUserMappingVo();
			mappingVo.setActivityId(retVo.getActivityId());
			mappingVo.setApplied(RmConstant.Activity.APPLIED_FALSE);
			for (Long userId : vo.getActivityPersonId()) {
				mappingVo.setUserId(userId);
				activityUserMappingDao.insertVo(mappingVo);
			}
		}
		return retVo;
	}

	@Override
	public Byte checkModifyPower(ActivityVo vo, Boolean limitTime) throws Exception {
		// 获取活动信息
		ActivityVo activityVo = queryById(vo);

		// 登录者不是活动发布人，不能修改
		if (!NumberUtils.IsEquals(activityVo.getPublisherId(), vo.getModifyUserId()))
			return RmConstant.Activity.MODIFY_NOT_PUBLISHER;

		if (limitTime != null && limitTime) {
			// 活动已开始报名，不能修改
			if (!NumberUtils.IsEquals(activityVo.getStatus(), RmConstant.Dictionary.STATUS_PENDING))
				return RmConstant.Activity.MODIFY_STARTED;
		}

		return RmConstant.Activity.MODIFY_SUCCESS;
	}

	@Override
	public Boolean updateModify(ActivityVo vo) throws Exception {
		if (checkModifyPower(vo, true) != RmConstant.Activity.MODIFY_SUCCESS)
			return false;

		if (activityDao.updateVo(vo) == 0)
			return false;

		// 删除已有人员限制信息
		ActivityUserMappingVo mappingVo = new ActivityUserMappingVo();
		mappingVo.setActivityId(vo.getActivityId());
		activityUserMappingDao.deleteVoNoById(mappingVo, new String[] { "activityId" });

		if (vo.getIsAllSelect() == RmConstant.Activity.ALLSELECT_FALSE
				&& vo.getActivityPersonId() != null) {
			mappingVo.setApplied(RmConstant.Activity.APPLIED_FALSE);
			for (Long userId : vo.getActivityPersonId()) {
				mappingVo.setUserId(userId);
				activityUserMappingDao.insertVo(mappingVo);
			}
		}
		return true;
	}

	@Override
	public Byte updateStop(ActivityVo vo) throws Exception {
		Byte result = checkModifyPower(vo, null);
		if (result != RmConstant.Activity.MODIFY_SUCCESS)
			return result;

		vo = queryById(vo);
		if (NumberUtils.IsEquals(vo.getStatus(), RmConstant.Dictionary.STATUS_OVERDUE)
				|| NumberUtils.IsEquals(vo.getStatus(), RmConstant.Dictionary.STATUS_INVALID))
			return RmConstant.Activity.MODIFY_STARTED;

		vo.setStatus(RmConstant.Dictionary.STATUS_INVALID);
		if (activityDao.updateVo(vo) == 0)
			return RmConstant.Activity.MODIFY_FAILED;
		return RmConstant.Activity.MODIFY_SUCCESS;
	}

	@Override
	public List<ActivityVo> queryList(ActivityVo vo) throws Exception {
		return activityDao.queryList(vo);
	}

	@Override
	public Integer update(List<ActivityVo> list) throws Exception {
		return activityDao.updateVo(list.toArray(new ActivityVo[0]));
	}

}
