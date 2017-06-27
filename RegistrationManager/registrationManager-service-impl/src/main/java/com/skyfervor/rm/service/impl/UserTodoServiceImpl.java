package com.skyfervor.rm.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyfervor.framework.constant.Constant;
import com.skyfervor.framework.dictionary.DictionaryUtils;
import com.skyfervor.framework.usertodo.UserTodoService;
import com.skyfervor.framework.usertodo.UserTodoVo;
import com.skyfervor.framework.vobase.PageVo;
import com.skyfervor.rm.dao.UserTodoDao;

@Service("userTodoService")
public class UserTodoServiceImpl implements UserTodoService {
	@Autowired
	private UserTodoDao userTodoDao;

	@Override
	public PageVo<UserTodoVo> queryPage(UserTodoVo vo, Integer page, Integer rows)
			throws Exception {
		PageVo<UserTodoVo> pageVo = userTodoDao.queryPage("userTodo-query-list", vo, page, rows);

		for (UserTodoVo todoVo : pageVo.getRows()) {
			String todoStatusName = DictionaryUtils.getValueDescription(
					Constant.Dictionary.USERTODO_STATUS, todoVo.getTodoStatus());
			todoVo.setTodoStatusName(todoStatusName);
		}
		return pageVo;
	}

	@Override
	public Long getCount(UserTodoVo vo) throws Exception {
		return userTodoDao.queryCount(vo);
	}

	@Override
	public UserTodoVo insertOrUpdate(UserTodoVo vo) throws Exception {
		// 已存在同项提醒
		UserTodoVo todoVo = userTodoDao.queryOne("userTodo-query-list", vo);
		if (todoVo != null) {
			todoVo.setTodoStatus(Constant.UserTodo.UNREAD);
			userTodoDao.updateVo(todoVo);
			return todoVo;
		}

		vo.setTodoStatus(Constant.UserTodo.UNREAD);
		return userTodoDao.insertVo(vo);

	}

	@Override
	public Boolean updateToReaded(UserTodoVo vo) throws Exception {
		vo.setTodoStatus(Constant.UserTodo.READED);
		vo.setReadTime(new Date());
		return userTodoDao.updateVo(vo) != 0 ? true : false;
	}

	@Override
	public Boolean updateToUnreaded(UserTodoVo vo) throws Exception {
		vo.setTodoStatus(Constant.UserTodo.UNREAD);
		return userTodoDao.updateVo(vo) != 0 ? true : false;
	}

}
