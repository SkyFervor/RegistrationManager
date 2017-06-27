package com.skyfervor.rm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyfervor.framework.user.PermissionService;
import com.skyfervor.framework.user.PermissionVo;
import com.skyfervor.rm.dao.PermissionDao;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	@Override
	public Boolean contains(PermissionVo vo) throws Exception {
		long count = permissionDao.queryCount("permission-query-list", vo);
		return count != 0 ? true : false;
	}

	@Override
	public List<PermissionVo> queryList(PermissionVo vo) throws Exception {
		return permissionDao.queryList(vo);
	}
}
