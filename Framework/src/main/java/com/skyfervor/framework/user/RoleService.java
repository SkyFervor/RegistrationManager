package com.skyfervor.framework.user;

import java.util.List;

public interface RoleService {
	public RoleVo queryById(Long id) throws Exception;

	public List<RoleVo> queryList(RoleVo vo) throws Exception;
}
