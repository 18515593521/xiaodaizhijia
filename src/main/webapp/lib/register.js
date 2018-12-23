document.write("<link rel='stylesheet' href='./layer.css'>");
document.write("<script src='/supermarket/lib/layer.js'></script>");
document.write("<script src='/supermarket/lib/paramsED.js'></script>");

function regMod(telPhone, idNum, smsCode, pwd, codeHtml){
	if(!isNotNull(telPhone)||!isNotNull(idNum)||!isNotNull(smsCode)||!isNotNull(pwd)){
		layer.open({
			content : '对不起，您的输入有误',
			skin : 'msg',
			time : 2
		//2秒后自动关闭
		});
		return;
	} 
	if(null==codeHtml || ""==codeHtml)codeHtml="code.html";
	
	$.ajax({
		type : "POST",
		url : "/supermarket/app/login/register",
		data : {
			"data" : encryptByDES(
					formatParm(telPhone, idNum, smsCode, pwd), getDecrypt())
		},
		dataType:"json",
		async : false,
		success : function(data) {
			if (data.code == 0) {
				layer.open({
					content : '注册成功',
					skin : 'msg',
					time : 1,
					end: function () {
						location.href='http://www.51happypay.com/supermarket/active/html/code3.html';
					}
				});
			} else if (data.code == 20001) {
				layer.open({
					content : '验证码错误！',
					skin : 'msg',
					time : 2
				//2秒后自动关闭
				});
			} else if (data.code == 20002) {
				layer.open({
					content : '您已经注册过了！',
					skin : 'msg',
					time : 1,
					end: function () {
						location.href='http://www.51happypay.com/supermarket/active/html/code3.html';
					}
				});
			}else {
				layer.open({
					content : '系统繁忙，请稍候再试！',
					skin : 'msg',
					time : 2
				});
			}
		}
	});
}

function isNotNull(data) {
	if (null == data || "" == data) {
		return false;
	}
	return true;
}
function formatParm(tel, idNum, code, pwd) {
	var result = "{" + "\"phone\":" + "\"" + tel + "\"" + ","
			+ "\"idNum\":" + "\"" + idNum + "\"" + ","
			+ "\"sms_code\":" + "\"" + code + "\"" + ","
			+ "\"password\":" + "\"" + pwd + "\"" + "," 
			+ "\"type\":"+ "\"0\"" + ","
			/* + "\"method\":" + "\"" + getQueryString("method") + "\"" + ","  */
			+ "\"user_source\":" + "\"" + getQueryString("method") + "\"" + "}";
	return result;
}

/*
 * 这个方法是获得页面url的某个url参数的方法
var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
这个正则是寻找&+url参数名字=值+&
&可以不存在。
var r = window.location.search.substr(1).match(reg);
if (r!=null) return unescape(r[2]); return null;
这里是开始匹配，找到了返回对应url值，没找到返回null。
*/
function getQueryString(key) {
	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return "share";
}
