package com.dd.supermarket.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

/**
* @author 作者 E-mail: 
* @version 创建时间：2018年5月28日 下午11:35:15
* 类说明：
*/
@Repository("baseDao")
public class BaseDao {
	@Resource(name="template")
	private SqlSessionTemplate template;
	
	public void save(String statement,Object obj){
		template.insert(statement, obj);
	}
	public int saveToint(String statement,Object obj){
		return template.insert(statement, obj);
	}
	
	public void batchSave(String statement,List objs){
		SqlSessionFactory factory = template.getSqlSessionFactory();
		SqlSession session = factory.openSession(ExecutorType.BATCH, false);
		try {
			for (Object obj : objs) {
				session.insert(statement, obj);
			}
			session.flushStatements();
			session.commit();
			session.clearCache();
		} finally {
			session.close();
		}
		
	}
	
	public void delete(String statement,Object obj){
		template.delete(statement,obj);
	}
	
	public void batchDelete(String statement,List objs){
		template.delete(statement, objs);
	}
	
	public void update(String statement,Object obj){
		template.update(statement,obj);
	}
	
	public void batchUpdate(String statement,List objs){
		SqlSessionFactory factory = template.getSqlSessionFactory();
		SqlSession session = factory.openSession(ExecutorType.BATCH, false);
		try {
			for (Object obj : objs) {
				session.update(statement, obj);
			}
			session.flushStatements();
			session.commit();
			session.clearCache();
		} finally {
			session.close();
		}
	}
	
	public Object findOne(String statement,Object obj){
		return template.selectOne(statement, obj);
	}
	
	public List findForList(String statement,Object obj){
		return template.selectList(statement,obj);
	}
	
	public Map findForMap(String statement,Object obj,String key){
		return template.selectMap(statement, obj, key);
	}
	
	public void executeProc(String statement,Object obj){
		SqlSessionFactory factory = template.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		try {
			session.selectOne(statement, obj);
			session.flushStatements();
			session.commit();
			session.clearCache();
		} finally {
			session.close();
		}
	}
}

