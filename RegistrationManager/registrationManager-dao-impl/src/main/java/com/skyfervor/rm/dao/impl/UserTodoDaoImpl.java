package com.skyfervor.rm.dao.impl;

import org.springframework.stereotype.Repository;

import com.skyfervor.framework.orm.BaseDaoImpl;
import com.skyfervor.framework.usertodo.UserTodoVo;
import com.skyfervor.rm.dao.UserTodoDao;
import com.skyfervor.rm.model.UserTodoMd;

@Repository("userTodoDao")
public class UserTodoDaoImpl extends BaseDaoImpl<UserTodoVo, UserTodoMd> implements UserTodoDao {
	public UserTodoDaoImpl() throws Exception {
		super();
	}

}
