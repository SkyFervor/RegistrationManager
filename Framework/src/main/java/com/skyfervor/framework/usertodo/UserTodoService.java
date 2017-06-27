package com.skyfervor.framework.usertodo;

import com.skyfervor.framework.vobase.PageVo;

public interface UserTodoService {

	/**
	 * 分页条件查询
	 * 
	 * @param vo
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	public PageVo<UserTodoVo> queryPage(UserTodoVo vo, Integer page, Integer rows) throws Exception;

	/**
	 * 提醒事项数量
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Long getCount(UserTodoVo vo) throws Exception;

	/**
	 * 插入提醒
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public UserTodoVo insertOrUpdate(UserTodoVo vo) throws Exception;

	/**
	 * 置为已读
	 * 
	 * @param vo
	 * @return 
	 * @throws Exception
	 */
	public Boolean updateToReaded(UserTodoVo vo) throws Exception;

	/**
	 * 置为未读
	 * 
	 * @param vo
	 * @return 
	 * @throws Exception
	 */
	public Boolean updateToUnreaded(UserTodoVo vo) throws Exception;
}
