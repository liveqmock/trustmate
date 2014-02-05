/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.ctl.member.UploadController.java
 * Date	        : Feb 24, 2009
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.ctl.member;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import biz.trustnet.common.log.Log;
import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.DocumentBean;
import com.pgmate.model.db.SettleBean;
import com.pgmate.model.db.dao.SettleDAO;
import com.pgmate.web.util.ParamUtil;

public class UploadController extends SimpleFormController {
	
	private String FILE_DIRECTORY = "/upload";
	
	public UploadController(){
		setCommandName("documentUpload");
		setCommandClass(DocumentBean.class);
	}
	
	protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class,new ByteArrayMultipartFileEditor());
	}

	protected ModelAndView onSubmit(HttpServletRequest request,HttpServletResponse response, Object command, BindException errors)throws Exception {
		
		ParamUtil param = new ParamUtil(request);
		DocumentBean docBean = (DocumentBean)command;
		
		boolean isSuccess = false;
		
		String directory = getServletContext().getRealPath(FILE_DIRECTORY);
		if(docBean.getFile().getSize() !=0){
			
			isSuccess = saveFile(directory,docBean.getFile());
		}
		if(isSuccess){
			SettleBean sBean = new SettleBean();
			sBean.setIdx(CommonUtil.parseLong(param.getString("settleIdx")));
			sBean.setFileName(FILE_DIRECTORY + File.separator + docBean.getFile().getOriginalFilename());
			if(new SettleDAO().updateByFile(sBean)){
				request.setAttribute("redirectURL","/report.do?request=settlementView&merchantId="+param.getString("merchantId")+"&invoiceIdx="+param.getString("invoiceIdx")+"&fileCheck=Y&fileName="+docBean.getFile().getOriginalFilename());
				return new ModelAndView("/common/popupredirect","message","파일 업로드 성공="+ docBean.getFile().getOriginalFilename());
			}else{
				request.setAttribute("redirectURL","/report.do?request=settlementView&merchantId="+param.getString("merchantId")+"&invoiceIdx="+param.getString("invoiceIdx")+"&fileCheck=Y&fileName="+docBean.getFile().getOriginalFilename());
				return new ModelAndView("/common/popupredirect","message","파일 업로드 성공="+ docBean.getFile().getOriginalFilename()+" & SETTLE TABLE UPDATE FAIL");
			}
		}else{
			return new ModelAndView("/common/popupredirect","message","파일 업로드 실패 (재시도하십시오.)");
		}
	
		//return super.onSubmit(request, response, command, errors);
		
	}
	
	
	public boolean saveFile(String directory,MultipartFile newFile){
		
		boolean isSave = false;
		
		
		File filePath = new File(directory);
		if (!filePath.exists()) { 
			filePath.mkdirs(); 
		}
		
		String newFileName = directory + File.separator + newFile.getOriginalFilename();
		File createFile = new File(newFileName);
		try{
			if (createFile.exists()) {
			    try {
			       isSave = createFile.createNewFile();
			    }catch (IOException ex){
			    }
			}
			newFile.transferTo(createFile); //move to createFile
			isSave = true;
			Log.debug("log.day","신규파일등록 =["+newFileName+"]",this);
		}catch(Exception e){
			Log.debug("log.day","파일업로드 장애 =["+newFileName+"]",this);
			Log.debug("log.day",CommonUtil.getExceptionMessage(e),this);
		}
		return isSave;
	
	}

}
