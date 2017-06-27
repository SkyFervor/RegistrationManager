package com.skyfervor.rm.dao.impl;

import org.springframework.stereotype.Repository;

import com.skyfervor.framework.dictionary.DictionaryTypeVo;
import com.skyfervor.framework.orm.BaseDaoImpl;
import com.skyfervor.rm.dao.DictionaryTypeDao;
import com.skyfervor.rm.model.DictionaryTypeMd;

@Repository("dictionaryTypeDao")
public class DictionaryTypeDaoImpl extends BaseDaoImpl<DictionaryTypeVo, DictionaryTypeMd>
		implements DictionaryTypeDao {

	public DictionaryTypeDaoImpl() throws Exception {
		super();
	}

}
