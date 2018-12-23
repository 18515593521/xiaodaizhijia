package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月19日 上午13:59:56 <br/>
* 类说明：
*/
public interface IDictionary {
	public void save_diction(Map map);
	
	public void del_diction(String dic_id);
	
	public void update_diction(Map map);
	
	/**
	 * 根据字典类型查询出所有该类型的集合
	 * @param dic_id
	 * @return
	 */
	public List<Map<String,Object>> find_dictionByType(String dic_id);
	
}
