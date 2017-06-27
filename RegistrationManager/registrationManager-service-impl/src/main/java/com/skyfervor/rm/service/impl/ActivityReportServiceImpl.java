package com.skyfervor.rm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyfervor.rm.dao.ActivityReportDao;
import com.skyfervor.rm.service.ActivityReportService;
import com.skyfervor.rm.vo.ActivityReportVo;

@Service("activityReportService")
public class ActivityReportServiceImpl implements ActivityReportService {

	@Autowired
	private ActivityReportDao activityReportDao;

	@Override
	public List<ActivityReportVo> queryListSrc(ActivityReportVo vo) throws Exception {
		return activityReportDao.queryList("activityReportSrc-query-list", vo);
	}

	@Override
	public Boolean contains(ActivityReportVo vo) throws Exception {
		return activityReportDao.queryCount(vo) >= 1 ? true : false;
	}

	@Override
	public List<ActivityReportVo> insert(List<ActivityReportVo> list)
			throws Exception {
		return activityReportDao.insertVo(list.toArray(new ActivityReportVo[0]));
	}

	@Override
	public List<ActivityReportVo> queryAllUser(ActivityReportVo vo) throws Exception {
		return activityReportDao.queryList("activityReport-user-query-list", vo);
	}

	@Override
	public List<ActivityReportVo> queryAllActivity(ActivityReportVo vo) throws Exception {
		return activityReportDao.queryList("activityReport-activity-query-list", vo);
	}

	@Override
	public List<ActivityReportVo> queryListByPerson(ActivityReportVo vo)
			throws Exception {
		return activityReportDao.queryList("activityReport-query-list-byPerson", vo);
	}

	@Override
	public List<ActivityReportVo> queryListByActivity(ActivityReportVo vo)
			throws Exception {
		return activityReportDao.queryList("activityReport-query-list-byActivity", vo);
	}

}
