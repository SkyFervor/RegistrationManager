package com.skyfervor.rm.dao.impl;

import org.springframework.stereotype.Repository;

import com.skyfervor.framework.orm.BaseDaoImpl;
import com.skyfervor.framework.user.UserVo;
import com.skyfervor.rm.dao.UserDao;
import com.skyfervor.rm.model.UserMd;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<UserVo, UserMd> implements UserDao {

	public UserDaoImpl() throws Exception {
		super();
	}

}
