package com.dd.supermarket.service.back.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.supermarket.dao.back.DictionaryDao;
import com.dd.supermarket.service.back.IDictionary;
import com.dd.supermarket.utils.UuidUtil;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月19日 上午14:02:34 <br/>
* 类说明：
*/
@Transactional
@Service("backDictionaryImpl")
public class DictionaryImpl implements IDictionary {
	
	@Resource(name="backDictionaryDao")
	private DictionaryDao dd;
	
	public void save_diction(Map map) {
		map.put("dic_id", UuidUtil.get32UUID());
		dd.save_diction(map);
	}

	public void del_diction(String dic_id) {
		dd.del_diction(dic_id);
	}

	public void update_diction(Map map) {
		dd.update_diction(map);
	}

	public List<Map<String,Object>> find_dictionByType(String dic_type) {
		List<Map<String,Object>> dictionList = new ArrayList<Map<String,Object>>();
		Map<String,Object> diction = (Map<String, Object>) dd.find_dictionByType(dic_type);
		dictionList.add(diction);
		dictionList.addAll((Collection<? extends Map<String, Object>>) dd.find_dictionByParentID((String)diction.get("dic_id")));
		return dictionList;
	}


}
