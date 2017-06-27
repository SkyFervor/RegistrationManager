package com.skyfervor.framework.user;

import java.util.List;

import com.skyfervor.framework.vobase.PageVo;

public interface UserService {
	/******************************** login *******************************/

	/**
	 * 通过登录名查询
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UserVo queryLoginByName(UserVo vo) throws Exception;

	/**
	 * 通过ID查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public UserVo queryLoginById(Long id) throws Exception;
	
	

	/******************************** modify update *******************************/

	public UserVo insert(UserVo vo) throws Exception;
	
	public Boolean update(UserVo vo) throws Exception;
	
	public void delete(Long userId) throws Exception;
	

	
	/******************************** query *******************************/
	
	/**
	 * 通过ID查询用户名
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String getNameById(Long id) throws Exception;

	public Long getCount(UserVo vo) throws Exception;

	public List<UserVo> queryListByName(UserVo vo) throws Exception;
	
	public Byte checkAddPower(UserVo vo) throws Exception;

	public PageVo<UserVo> queryPage(UserVo vo, Integer page, Integer rows) throws Exception;

	UserVo queryById(Long userId) throws Exception;

	UserVo query(UserVo vo) throws Exception;

}
