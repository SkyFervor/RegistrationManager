package com.skyfervor.rm.dao.impl;

import org.springframework.stereotype.Repository;

import com.skyfervor.framework.orm.BaseDaoImpl;
import com.skyfervor.rm.dao.ActivityUserMappingDao;
import com.skyfervor.rm.model.ActivityUserMappingMd;
import com.skyfervor.rm.vo.ActivityUserMappingVo;

@Repository("activityUserMappingDao")
public class ActivityUserMappingDaoImpl
		extends BaseDaoImpl<ActivityUserMappingVo, ActivityUserMappingMd>
		implements ActivityUserMappingDao {

	public ActivityUserMappingDaoImpl() throws Exception {
		super();
	}

}
