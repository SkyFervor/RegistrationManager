package com.skyfervor.rm.dao.impl;

import org.springframework.stereotype.Repository;

import com.skyfervor.framework.orm.BaseDaoImpl;
import com.skyfervor.framework.user.RoleVo;
import com.skyfervor.rm.dao.RoleDao;
import com.skyfervor.rm.model.RoleMd;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<RoleVo, RoleMd> implements RoleDao {

	public RoleDaoImpl() throws Exception {
		super();
	}

}
