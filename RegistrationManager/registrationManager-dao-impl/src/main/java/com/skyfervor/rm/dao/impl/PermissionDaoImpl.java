package com.skyfervor.rm.dao.impl;

import org.springframework.stereotype.Repository;

import com.skyfervor.framework.orm.BaseDaoImpl;
import com.skyfervor.framework.user.PermissionVo;
import com.skyfervor.rm.dao.PermissionDao;
import com.skyfervor.rm.model.PermissionMd;

@Repository("permissionDao")
public class PermissionDaoImpl extends BaseDaoImpl<PermissionVo, PermissionMd>
		implements PermissionDao {

	public PermissionDaoImpl() throws Exception {
		super();
	}

}
