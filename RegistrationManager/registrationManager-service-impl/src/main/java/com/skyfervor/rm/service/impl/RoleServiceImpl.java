package com.skyfervor.rm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyfervor.framework.user.RoleService;
import com.skyfervor.framework.user.RoleVo;
import com.skyfervor.rm.dao.RoleDao;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public RoleVo queryById(Long id) throws Exception {
		return roleDao.queryById(id);
	}

	@Override
	public List<RoleVo> queryList(RoleVo vo) throws Exception {
		return roleDao.queryList(vo);
	}

}
