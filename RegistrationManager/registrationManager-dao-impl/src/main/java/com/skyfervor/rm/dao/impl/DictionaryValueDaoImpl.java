package com.skyfervor.rm.dao.impl;

import org.springframework.stereotype.Repository;

import com.skyfervor.framework.dictionary.DictionaryValueVo;
import com.skyfervor.framework.orm.BaseDaoImpl;
import com.skyfervor.rm.dao.DictionaryValueDao;
import com.skyfervor.rm.model.DictionaryValueMd;

@Repository("dictionaryValueDao")
public class DictionaryValueDaoImpl extends BaseDaoImpl<DictionaryValueVo, DictionaryValueMd>
		implements DictionaryValueDao {

	public DictionaryValueDaoImpl() throws Exception {
		super();
	}

}
