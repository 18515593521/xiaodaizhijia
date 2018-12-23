document.write("<script src='../lib/jquery.cookie.js'></script>");
$(function(){
	var code1 = getQueryStringToKey("code");
	if(code1!=null && code1 !=''){
		var openid = $.cookie("openid");		
		if(!openid)getOpenIdByCode();		//当cookie中没有openid时，通过code去后台获取openid
		openid = $.cookie("openid");
		getUserinfoByOpenID(openid);		//通过openid去后台获取用户信息
	}else{
		return;
	}
	
})

function getUserinfoByOpenID(openid){
	var bindUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8f40fcd4a4a89333&redirect_uri=http%3A%2F%2Fwww.51happypay.com%2Fsupermarket%2Fwx%2Fbind.html&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
	$.ajax({
		type : "POST",
		url : "/supermarket/wechatUserinfo/findUserinfoByOpenID",
		data : {
			"openid" : openid
		},
		async : false,
		success : function(data) {
			data = JSON.parse(data);
			if(data.user_id==''||data.user_id==null){
				window.location.href=bindUrl;
			}
			console.log("微信用户信息>>> "+data);				//当数据库中无法获取到用户信息时，意味着该用户未绑定微信，跳转去绑定页
		}
	});
}

function getOpenIdByCode(){
	$.ajax({
		type : "POST",
		url : "/supermarket/wechatUserinfo/getOpenIdByCode",
		data : {
			"code" : getQueryStringToKey("code")
		},
		dataType:"JSON",
		async : false,
		success : function(data) {
			$.cookie('openid',data.openid,{path:'/'});
		}
	
	});
}
function getQueryStringToKey(key) {
	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}


