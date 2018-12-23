package com.dd.supermarket.service.back;

import java.util.Map;

public interface ILoginLog {
	public Object find_llogbyid(Map map);
	
	public void add_loginlog(Map map);
	public void upd_time(Map map);
}
