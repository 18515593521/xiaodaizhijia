/**
 *	
 */
package com.dd.supermarket.utils.factories;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 作者 ：   <br/>
 *         E-mail:   <br/>
 * @version 创建时间： 2018年7月18日上午9:43:39 <br/>
 *          类说明：
 */
public class XMLToJson {

	/**
	 * XML字符串 转换成 json字符串如：
	 * 
	 * @param str		<?xml version=\"1.0\" encoding=\"utf-8\" ?><MoBaoAccount MessageType=\"UserMobilePay\" PlatformID=\"b2ctest\"><OrderNo> </OrderNo><TradeAmt>5000.00</TradeAmt><Commission>0.5</Commission><UserID>liucj</UserID><MerchID>liucj1</MerchID><tradeType>0</tradeType><CustParam>123</CustParam> <NotifyUrl>http://www.baidu.com/callback.do</NotifyUrl><TradeSummary>我爱中国</TradeSummary></MoBaoAccount>
	 * @return json		{"MoBaoAccount":{"MerchID":["liucj1"],"TradeSummary":["我爱中国"],"UserID":["liucj"],"NotifyUrl":["http://www.baidu.com/callback.do"],"Commission":["0.5"],"OrderNo":[" "],"TradeAmt":["5000.00"],"CustParam":["123"],"tradeType":["0"]}}
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static JSONObject xmlToJSON(String value) throws JDOMException, IOException {
		byte[] b = value.getBytes();
		JSONObject json = new JSONObject();
		InputStream is = new ByteArrayInputStream(b);
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(is);
		Element root = doc.getRootElement();
		json.put(root.getName(), iterateElement(root));
		return json;
	}

	private static JSONObject iterateElement(Element element) {
		List<Object> node = element.getChildren();
		Element et = null;
		JSONObject obj = new JSONObject();
		List<Object> list = null;
		for (int i = 0; i < node.size(); i++) {
			list = new LinkedList();
			et = (Element) node.get(i);
			if (et.getTextTrim().equals("")) {
				if (et.getChildren().size() == 0)
					continue;
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(iterateElement(et));
				obj.put(et.getName(), list);
			} else {
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(et.getTextTrim());
				obj.put(et.getName(), list);
			}
		}
		return obj;
	}

	public static void main(String[] args) throws JDOMException, IOException {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><MoBaoAccount MessageType=\"UserMobilePay\" PlatformID=\"b2ctest\"><OrderNo> </OrderNo><TradeAmt>5000.00</TradeAmt><Commission>0.5</Commission><UserID>liucj</UserID><MerchID>liucj1</MerchID><tradeType>0</tradeType><CustParam>123</CustParam> <NotifyUrl>http://www.baidu.com/callback.do</NotifyUrl><TradeSummary>我爱中国</TradeSummary></MoBaoAccount>";
		JSONObject json = xmlToJSON(xml);
		System.out.println(json.toJSONString());
	}
}
