package com.skyfervor.rm.service;

import com.skyfervor.framework.vobase.PageVo;
import com.skyfervor.rm.vo.ActivityUserMappingVo;

public interface ActivityUserMappingService {

	public PageVo<ActivityUserMappingVo> queryPage(ActivityUserMappingVo vo, Integer page,
			Integer rows) throws Exception;

	public ActivityUserMappingVo query(ActivityUserMappingVo vo) throws Exception;

	public Byte checkEnrollPower(ActivityUserMappingVo vo) throws Exception;

	public Boolean updateToEnroll(ActivityUserMappingVo vo) throws Exception;

	public Long getCount(ActivityUserMappingVo vo) throws Exception;

}
