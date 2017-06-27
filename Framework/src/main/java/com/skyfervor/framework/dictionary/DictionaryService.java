package com.skyfervor.framework.dictionary;

import java.util.List;

import com.skyfervor.framework.vobase.PageVo;

public interface DictionaryService {

	public DictionaryTypeVo queryTypeByName(String typeName) throws Exception;

	public DictionaryTypeVo queryTypeById(Long dictionaryTypeId) throws Exception;

	public DictionaryValueVo queryValueById(Long dictionaryValueId) throws Exception;

	public List<DictionaryValueVo> queryValueListByTypeId(Long typeId, Boolean display)
			throws Exception;

	public PageVo<DictionaryValueVo> queryPage(DictionaryValueVo vo, Integer page, Integer rows)
			throws Exception;

	public DictionaryTypeVo insertType(DictionaryTypeVo vo) throws Exception;

	public DictionaryValueVo insertValue(DictionaryValueVo vo) throws Exception;

	public Boolean updateType(DictionaryTypeVo vo) throws Exception;

	public Boolean updateValue(DictionaryValueVo vo) throws Exception;

	public Boolean deleteType(Long dictionaryTypeId) throws Exception;

	public void deleteValue(Long dictionaryValueId) throws Exception;
}
