package com.skyfervor.rm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyfervor.framework.constant.Constant;
import com.skyfervor.framework.dictionary.DictionaryUtils;
import com.skyfervor.framework.user.RoleService;
import com.skyfervor.framework.user.UserService;
import com.skyfervor.framework.user.UserVo;
import com.skyfervor.framework.vobase.PageVo;
import com.skyfervor.rm.dao.UserDao;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleService roleService;

	@Override
	public UserVo queryLoginByName(UserVo vo) throws Exception {
		return userDao.queryOne("login-query-list", vo);
	}

	@Override
	public UserVo queryLoginById(Long id) throws Exception {
		return userDao.queryById(id);
	}

	@Override
	public UserVo insert(UserVo vo) throws Exception {
		return userDao.insertVo(vo);
	}

	@Override
	public Boolean update(UserVo vo) throws Exception {
		int ret = userDao.updateVo(vo);
		return ret != 0 ? true : false;
	}

	@Override
	public void delete(Long userId) throws Exception {
		UserVo vo = new UserVo();
		vo.setUserId(userId);
		userDao.deleteVoForEnumDataEntityStatus(vo);
	};

	@Override
	public String getNameById(Long id) throws Exception {
		UserVo vo = userDao.queryById(id);
		if (vo == null)
			return null;
		return vo.getUserName();
	}

	@Override
	public Long getCount(UserVo vo) throws Exception {
		return userDao.queryCount(vo);

	}

	@Override
	public List<UserVo> queryListByName(UserVo vo) throws Exception {
		return userDao.queryList("user-query-list", vo);
	}

	@Override
	public Byte checkAddPower(UserVo vo) throws Exception {
		vo = queryLoginByName(vo);
		if (vo != null)
			return Constant.User.ADD_FAILURE;
		return Constant.User.ADD_SUCCESS;
	}

	@Override
	public PageVo<UserVo> queryPage(UserVo vo, Integer page, Integer rows) throws Exception {
		PageVo<UserVo> pageVo = userDao.queryPage("user-query-list", vo, page, rows);
		for (UserVo userVo : pageVo.getRows()) {
			String roleName = roleService.queryById(userVo.getRoleId()).getName();
			userVo.setRoleName(roleName);

			String statusName = DictionaryUtils.getValueDescription(Constant.Dictionary.USER_STATUS,
					userVo.getStatus());
			userVo.setStatusName(statusName);
		}
		return pageVo;
	}

	@Override
	public UserVo queryById(Long userId) throws Exception {
		UserVo vo = new UserVo();
		vo.setUserId(userId);
		return query(vo);
	}

	@Override
	public UserVo query(UserVo vo) throws Exception {
		return userDao.queryOne("user-query-list", vo);
	}

}
