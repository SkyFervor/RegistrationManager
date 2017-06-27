package com.skyfervor.rm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyfervor.framework.constant.Constant;
import com.skyfervor.framework.dictionary.DictionaryService;
import com.skyfervor.framework.dictionary.DictionaryTypeVo;
import com.skyfervor.framework.dictionary.DictionaryValueVo;
import com.skyfervor.framework.utility.NumberUtils;
import com.skyfervor.framework.vobase.PageVo;
import com.skyfervor.rm.dao.DictionaryTypeDao;
import com.skyfervor.rm.dao.DictionaryValueDao;

@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {
	@Autowired
	private DictionaryTypeDao typeDao;

	@Autowired
	private DictionaryValueDao valueDao;

	@Override
	public DictionaryTypeVo queryTypeByName(String typeName) throws Exception {
		DictionaryTypeVo vo = new DictionaryTypeVo();
		vo.setName(typeName);
		return typeDao.queryList(vo).get(0);
	}

	@Override
	public DictionaryTypeVo queryTypeById(Long dictionaryTypeId) throws Exception {
		return typeDao.queryById(dictionaryTypeId);
	}

	@Override
	public DictionaryValueVo queryValueById(Long dictionaryValueId) throws Exception {
		return valueDao.queryById(dictionaryValueId);
	}

	@Override
	public List<DictionaryValueVo> queryValueListByTypeId(Long typeId, Boolean display)
			throws Exception {
		DictionaryValueVo vo = new DictionaryValueVo();
		vo.setDictionaryTypeId(typeId);
		if (display != null) {
			if (display)
				vo.setDisplay(Constant.Dictionary.DISPLAY_TRUE);
			else
				vo.setDisplay(Constant.Dictionary.DISPLAY_FALSE);
		}
		return valueDao.queryList(vo);
	}

	@Override
	public PageVo<DictionaryValueVo> queryPage(DictionaryValueVo vo, Integer page, Integer rows)
			throws Exception {
		PageVo<DictionaryValueVo> pageVo = valueDao.queryPage("dictionaryValue-query-list", vo,
				page, rows);

		for (DictionaryValueVo valueVo : pageVo.getRows()) {
			if (NumberUtils.IsEquals(valueVo.getDisplay(), Constant.Dictionary.DISPLAY_TRUE))
				valueVo.setDisplayName(Constant.Dictionary.DISPLAY_TRUE_DESC);
			else
				valueVo.setDisplayName(Constant.Dictionary.DISPLAY_FALSE_DESC);
		}
		return pageVo;
	}

	@Override
	public DictionaryTypeVo insertType(DictionaryTypeVo vo) throws Exception {
		return typeDao.insertVo(vo);
	}

	@Override
	public DictionaryValueVo insertValue(DictionaryValueVo vo) throws Exception {
		return valueDao.insertVo(vo);
	}

	@Override
	public Boolean updateType(DictionaryTypeVo vo) throws Exception {
		int ret = typeDao.updateVo(vo);
		return ret != 0 ? true : false;
	}

	@Override
	public Boolean updateValue(DictionaryValueVo vo) throws Exception {
		int ret = valueDao.updateVo(vo);
		return ret != 0 ? true : false;
	}

	@Override
	public Boolean deleteType(Long dictionaryTypeId) throws Exception {
		DictionaryValueVo valueVo = new DictionaryValueVo();
		valueVo.setDictionaryTypeId(dictionaryTypeId);
		valueVo.setModify(Constant.Dictionary.MODIFY_FALSE);
		if (valueDao.queryCount(valueVo) != 0)
			return false;
		
		valueVo.setModify(null);
		List<DictionaryValueVo> valueList = valueDao.queryList(valueVo);
		for (DictionaryValueVo vo : valueList) {
			valueDao.deleteVoForEnumDataEntityStatus(vo);
		}

		DictionaryTypeVo typeVo = new DictionaryTypeVo();
		typeVo.setDictionaryTypeId(dictionaryTypeId);
		typeDao.deleteVoForEnumDataEntityStatus(typeVo);
		return true;
	}

	@Override
	public void deleteValue(Long dictionaryValueId) throws Exception {
		DictionaryValueVo vo = new DictionaryValueVo();
		vo.setDictionaryValueId(dictionaryValueId);
		valueDao.deleteVoForEnumDataEntityStatus(vo);
	}

}
