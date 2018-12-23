package com.dd.supermarket.dao.h5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dd.supermarket.dao.BaseDao;
@Repository("h5homeDao")
public class H5HomeDao {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	public List<Map<String, Object>> getHomeDataById(@Param("lable_dic_id") String lable_dic_id){
		Map m=new HashMap<>();
		m.put("lable_dic_id", lable_dic_id);
		return baseDao.findForList("H5Home.find_commodityList", m);
	}
	
	//添加申请人信息
	public void save_h5apply(Map map){
		baseDao.save("H5Home.save_h5apply", map);
	}
}
