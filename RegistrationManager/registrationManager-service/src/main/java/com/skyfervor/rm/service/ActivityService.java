package com.skyfervor.rm.service;

import java.util.List;

import com.skyfervor.framework.vobase.PageVo;
import com.skyfervor.rm.vo.ActivityVo;

public interface ActivityService {
	public PageVo<ActivityVo> queryPage(ActivityVo vo, Integer page, Integer rows) throws Exception;

	public ActivityVo queryById(ActivityVo vo) throws Exception;

	public ActivityVo queryById(Long activityId) throws Exception;

	public ActivityVo queryWithPersonnelById(Long activityId) throws Exception;

	public ActivityVo insert(ActivityVo vo) throws Exception;

	public Byte checkModifyPower(ActivityVo vo, Boolean limitTime) throws Exception;

	public Boolean updateModify(ActivityVo vo) throws Exception;
	
	public Byte updateStop(ActivityVo vo) throws Exception;

	public List<ActivityVo> queryList(ActivityVo vo) throws Exception;

	public Integer update(List<ActivityVo> list) throws Exception;

}
