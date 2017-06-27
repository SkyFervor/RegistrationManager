package com.skyfervor.framework.user;

import java.util.List;

public interface PermissionService {
	public Boolean contains(PermissionVo vo) throws Exception;

	public List<PermissionVo> queryList(PermissionVo vo) throws Exception;
}
