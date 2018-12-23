package com.dd.supermarket.utils.file;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年5月29日 上午12:44:33 <br/>
* 类说明：
*/
public class FileUploader {
	
	/**
	 * 获取服务器路径
	 * @param request
	 * @return
	 */
	public String getservicePath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
	
	/**
	 * 上传文件
	 * @param file 需要上传的文件<br>
	 * @param savePath 保存绝对路径（可通过 getservicePath(HttpServletRequest)方法获取服务器路径后拼接相对路径）<br>
	 * @return 返回上传成功后的文件名+后缀名<br>
	 * @throws IllegalStateException<br>
	 * @throws IOException
	 */
	public String uploade(MultipartFile file, String savePath) throws IllegalStateException, IOException {
		String savepath = ""; // 保存文件上传的完整路径
		String fileName = ""; // 文件名
		// 上传前的文件名称
		fileName = file.getOriginalFilename();
		// 文件类型
		String fileType = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
		// 判断文件夹是否存在
		File folder = new File(savePath + File.separator);
		if (!folder.exists()) folder.mkdirs(); // 若路径不能存在，则创建此路径
		// 存到服务器的文件名
		fileName = getTime()+UUID.randomUUID().toString().trim().replaceAll("-", "") + "." + fileType; // 自定义文件名称
		savepath = savePath + File.separator + fileName;
		File fir = new File(savepath);
		file.transferTo(fir); // 上传
		// 返回上传后的文件名
		return fileName;
	}
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
	public String getTime(){
		return df.format(new Date());
	}
	
	/**
	 * 文件删除
	 * @param fileName 需要删除的文件名
	 * @param path	该文件路径
	 */
	public void delFile(String fileName,String path){
		this.deleteFile(path+fileName);//删除文件
	}
	/**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public static void main(String[] args) {
    	String  path= "D:\\Temporary file\\";
    	String fileName = "新建文本文档.txt";
    	new FileUploader().delFile(fileName,path);
	}
	
}