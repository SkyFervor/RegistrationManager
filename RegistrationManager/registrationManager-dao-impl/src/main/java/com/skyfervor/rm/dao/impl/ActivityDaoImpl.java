package com.skyfervor.rm.dao.impl;

import org.springframework.stereotype.Repository;

import com.skyfervor.framework.orm.BaseDaoImpl;
import com.skyfervor.rm.dao.ActivityDao;
import com.skyfervor.rm.model.ActivityMd;
import com.skyfervor.rm.vo.ActivityVo;

@Repository("activityDao")
public class ActivityDaoImpl extends BaseDaoImpl<ActivityVo, ActivityMd> implements ActivityDao {

	public ActivityDaoImpl() throws Exception {
		super();
	}

}
