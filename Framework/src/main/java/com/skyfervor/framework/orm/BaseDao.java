package com.skyfervor.framework.orm;

import java.util.ArrayList;
import java.util.List;

import com.skyfervor.framework.vobase.PageVo;

public interface BaseDao<VO> {

	public VO insertVo(VO vo) throws Exception;

	public ArrayList<VO> insertVo(VO[] vos) throws Exception;

	public int updateVo(VO vo) throws Exception;

	public int updateVo(VO[] vos) throws Exception;
	
	/**
	 * 通用分页查询
	 */
	public PageVo<VO> queryPage(String sqlName, VO vo, int page, int rows) throws Exception;
	public PageVo<VO> queryPage(VO vo, int page, int rows) throws Exception;
	
	/**
	 * 通用LIST查询
	 */
	public List<VO> queryList(String sqlName, VO vo) throws Exception;
	public List<VO> queryList(VO vo) throws Exception;
	
	/**
	 * 通用总数查询
	 */
	public long queryCount(VO vo) throws Exception;
	public long queryCount(String sqlName,VO vo) throws Exception;
	
	/**
	 * 通用单条查询
	 */
	public VO queryOne(String sqlName, VO vo) throws Exception;
	public VO queryById(long id) throws Exception;
	
	/**
	 * 通用删除方法
	 */
	public void deleteVo(VO vo) throws Exception;
	
	/**
	 * 通过非id字段删除
	 */
	public void deleteVoNoById(VO vo, String[] selectFieldNameArray) throws Exception;

	/**
	 * 通用删除方法
	 */
	public void deleteVo(VO[] vos) throws Exception;
	
	/**
	 * 通用逻辑删除方法（更新EnumDataEntityStatus）
	 */
	public void deleteVoForEnumDataEntityStatus(VO vo) throws Exception;
	
}
