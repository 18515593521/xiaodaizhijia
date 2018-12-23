package com.dd.supermarket.dao.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月19日 上午14:10:11 <br/>
* 类说明：
*/
@Repository("backDictionaryDao")
public class DictionaryDao {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	public Object find_dictionByType(String dic_type){
		return baseDao.findOne("backDictionary.find_dictionByType", dic_type);
	}
	public List<Object> find_dictionByParentID(String dic_parentID){
		return baseDao.findForList("backDictionary.find_dictionByParentID", dic_parentID);
	}
	public void save_diction(Map map){
		baseDao.save("backDictionary.save_diction",map);
	}
	public void del_diction(String dic_id){
		baseDao.delete("backDictionary.del_diction", dic_id);
	}
	public void update_diction(Map map){
		baseDao. update("backDictionary.update_diction", null);
	}
	
}
