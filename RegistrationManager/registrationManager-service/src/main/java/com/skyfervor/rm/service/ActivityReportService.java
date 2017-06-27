package com.skyfervor.rm.service;

import java.util.List;

import com.skyfervor.rm.vo.ActivityReportVo;

public interface ActivityReportService {
	public List<ActivityReportVo> queryListSrc(ActivityReportVo vo) throws Exception;
	
	public Boolean contains(ActivityReportVo vo) throws Exception;

	public List<ActivityReportVo> insert(List<ActivityReportVo> list) throws Exception;

	public List<ActivityReportVo> queryAllUser(ActivityReportVo vo) throws Exception;

	public List<ActivityReportVo> queryAllActivity(ActivityReportVo vo) throws Exception;

	public List<ActivityReportVo> queryListByPerson(ActivityReportVo vo) throws Exception;

	public List<ActivityReportVo> queryListByActivity(ActivityReportVo vo) throws Exception;
}
