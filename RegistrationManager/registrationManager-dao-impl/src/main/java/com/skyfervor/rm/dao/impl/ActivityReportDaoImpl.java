package com.skyfervor.rm.dao.impl;

import org.springframework.stereotype.Repository;

import com.skyfervor.framework.orm.BaseDaoImpl;
import com.skyfervor.rm.dao.ActivityReportDao;
import com.skyfervor.rm.model.ActivityReportMd;
import com.skyfervor.rm.vo.ActivityReportVo;

@Repository("activityReportDao")
public class ActivityReportDaoImpl extends BaseDaoImpl<ActivityReportVo, ActivityReportMd>
		implements ActivityReportDao {

	public ActivityReportDaoImpl() throws Exception {
		super();
	}

}
