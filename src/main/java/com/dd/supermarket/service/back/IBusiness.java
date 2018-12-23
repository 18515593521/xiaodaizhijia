package com.dd.supermarket.service.back;

import java.util.List;
import java.util.Map;

public interface IBusiness {
	public List<Object> find_business();
	public void update_business(Map map);
}
