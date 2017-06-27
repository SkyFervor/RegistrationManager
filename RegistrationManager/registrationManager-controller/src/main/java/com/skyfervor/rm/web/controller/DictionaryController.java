package com.skyfervor.rm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skyfervor.framework.dictionary.DictionaryService;
import com.skyfervor.framework.dictionary.DictionaryTypeVo;
import com.skyfervor.framework.dictionary.DictionaryValueVo;
import com.skyfervor.framework.utility.JsonTranslator;
import com.skyfervor.framework.vobase.PageVo;

@Controller
@RequestMapping(value = "/dictionary")
public class DictionaryController {
	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(value = "/data")
	@ResponseBody
	public PageVo<DictionaryValueVo> data(String input, Integer page, Integer rows)
			throws Exception {
		page = page != null ? page : 1;
		rows = rows != null ? rows : 10;
		DictionaryValueVo vo = JsonTranslator.fromString(input, DictionaryValueVo.class);

		return dictionaryService.queryPage(vo, page, rows);
	}

	@RequestMapping(value = "/addtype")
	@ResponseBody
	public Boolean addType(String input) throws Exception {
		DictionaryTypeVo vo = JsonTranslator.fromString(input, DictionaryTypeVo.class);
		vo = dictionaryService.insertType(vo);
		return vo != null ? true : false;
	}

	@RequestMapping(value = "/addvaluepage")
	public ModelAndView addValuePage(@RequestParam("urlParam") Long dictionaryTypeId)
			throws Exception {
		DictionaryTypeVo vo = dictionaryService.queryTypeById(dictionaryTypeId);

		ModelAndView mv = new ModelAndView("/dictionary/dictionaryValue_add");
		mv.addObject("typeVo", vo);
		return mv;
	}

	@RequestMapping(value = "/addvalue")
	@ResponseBody
	public Boolean addValue(String input) throws Exception {
		DictionaryValueVo vo = JsonTranslator.fromString(input, DictionaryValueVo.class);
		vo = dictionaryService.insertValue(vo);
		return vo != null ? true : false;
	}

	@RequestMapping(value = "/modifytypepage")
	@ResponseBody
	public ModelAndView modifyTypePage(@RequestParam("urlParam") Long dictionaryTypeId)
			throws Exception {
		DictionaryTypeVo vo = dictionaryService.queryTypeById(dictionaryTypeId);

		ModelAndView mv = new ModelAndView("/dictionary/dictionaryType_modify");
		mv.addObject("typeVo", vo);
		return mv;
	}

	@RequestMapping(value = "/modifytype")
	@ResponseBody
	public Boolean modifyType(String input) throws Exception {
		DictionaryTypeVo vo = JsonTranslator.fromString(input, DictionaryTypeVo.class);
		return dictionaryService.updateType(vo);
	}

	@RequestMapping(value = "/modifyvaluepage")
	@ResponseBody
	public ModelAndView modifyValuePage(@RequestParam("urlParam") Long dictionaryValueId)
			throws Exception {
		DictionaryValueVo vo = dictionaryService.queryValueById(dictionaryValueId);

		ModelAndView mv = new ModelAndView("/dictionary/dictionaryValue_modify");
		mv.addObject("valueVo", vo);
		return mv;
	}

	@RequestMapping(value = "/modifyvalue")
	@ResponseBody
	public Boolean modifyValue(String input) throws Exception {
		DictionaryValueVo vo = JsonTranslator.fromString(input, DictionaryValueVo.class);
		return dictionaryService.updateValue(vo);
	}

	@RequestMapping(value = "/deltype")
	@ResponseBody
	public Boolean delType(Long dictionaryTypeId) throws Exception {
		return dictionaryService.deleteType(dictionaryTypeId);
	}

	@RequestMapping(value = "/delvalue")
	@ResponseBody
	public void delValue(Long dictionaryValueId) throws Exception {
		dictionaryService.deleteValue(dictionaryValueId);
	}
}
