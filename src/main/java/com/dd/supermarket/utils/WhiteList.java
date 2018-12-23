package com.dd.supermarket.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WhiteList {
	/**
	 * 根据当前产品获取相邻的两产品
	 * 
	 * @param com_id
	 *            当前产品id
	 * @param com_ids
	 *            所有产品的id
	 * @return
	 */
	public static Map pre_next_comDetail(String com_id, List<Map> com_ids) {
		String curuu_id = com_id;
		String preuu_id = "";		//上一个id
		String nextuu_id = "";		//下一个id
		int preuu_mode = 0;		//上一个结算方式
		int nextuu_mode = 0;	//下一个结算方式
		Map<String, Object> map = new HashMap<String,Object>();
		for (int i = 0; i < com_ids.size(); i++) {
			int size = com_ids.size();
			if(size==1)
				preuu_id=nextuu_id=com_id;else
			if (curuu_id.equals(com_ids.get(i).get("com_id"))) {
				if (i == 0) {
					// System.out.println(size-1);
					// System.out.println(i+1);
					preuu_id = (String) com_ids.get(size - 1).get("com_id");
					preuu_mode =  (int) com_ids.get(size - 1).get("settlement");
					nextuu_mode = (int) com_ids.get(i + 1).get("settlement");
					nextuu_id = (String) com_ids.get(i + 1).get("com_id");
					break;
				} else if (i == size - 1) {
					// System.out.println(i-1);
					// System.out.println(0);
					preuu_id = (String) com_ids.get(i - 1).get("com_id");
					nextuu_id = (String) com_ids.get(0).get("com_id");
					preuu_mode = (int)  com_ids.get(i - 1).get("settlement");
					nextuu_mode = (int)  com_ids.get(0).get("settlement");
					break;
				} else {
					// System.out.println(i-1);
					// System.out.println(i+1);
					preuu_id = (String) com_ids.get(i - 1).get("com_id");
					nextuu_id = (String) com_ids.get(i + 1).get("com_id");
					preuu_mode = (int)  com_ids.get(i - 1).get("settlement");
					nextuu_mode = (int)  com_ids.get(i + 1).get("settlement");
					break;
				}
			}
		}

		map.put("preuu_id", preuu_id);
		map.put("curuu_id", curuu_id);
		map.put("nextuu_id", nextuu_id);
		map.put("preuu_mode", preuu_mode);
		map.put("nextuu_mode", nextuu_mode);

		return map;
	}
}
