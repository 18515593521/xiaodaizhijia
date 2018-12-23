document.write("<script src='/supermarket/h5/js/CryptoJS/core.js'></script>");
document.write("<script src='/supermarket/h5/js/CryptoJS/enc-base64.js'></script>");
document.write("<script src='/supermarket/h5/js/CryptoJS/cipher-core.js'></script>");
document.write("<script src='/supermarket/h5/js/CryptoJS/tripledes.js'></script>");
document.write("<script src='/supermarket/h5/js/CryptoJS/mode-ecb.js'></script>");

function getDecrypt(){
	
	var str;
	var ciphertext;
	var prefix = "+8dq/K!B=My";
	var decrypt;
	$.ajax({
		type : "POST",
		url : "/supermarket/wechatUserinfo/encryption.do",
		data : {},
		dataType:"JSON",
		async : false,
		success : function(data) {
			if (data.code == 1) {
				str = data.result.str;
				ciphertext = data.result.ciphertext.replace(prefix, '');
				decrypt = decryptByDES(ciphertext, str);
			}
		}
	});
	return decrypt;
}

// DES加密
function encryptByDES(message, key) {
	var keyHex = CryptoJS.enc.Utf8.parse(key);
	var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
		mode : CryptoJS.mode.ECB,
		padding : CryptoJS.pad.Pkcs7
	});
	//console.log(encrypted.toString());
	return encrypted.toString();
}

// DES解密
function decryptByDES(ciphertext, key) {
	var keyHex = CryptoJS.enc.Utf8.parse(key);
	var decrypted = CryptoJS.DES.decrypt({
		ciphertext : CryptoJS.enc.Base64.parse(ciphertext)
	}, keyHex, {
		mode : CryptoJS.mode.ECB,
		padding : CryptoJS.pad.Pkcs7
	});
	return decrypted.toString(CryptoJS.enc.Utf8);
}
