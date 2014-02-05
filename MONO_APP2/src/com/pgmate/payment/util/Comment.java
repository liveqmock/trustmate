/* 
 * Project Name : PG_APP
 * Project      : PG_APP
 * File Name    : com.pgmate.model.comment.Comment.java
 * Date	        : Dec 23, 2008
 * Version      : 1.0
 * Author       : ginaida@panworld-net.com
 * Comment      :  
 */

package com.pgmate.payment.util;

import java.util.List;

import biz.trustnet.common.util.CommonUtil;

import com.pgmate.model.db.CommentMemberBean;
import com.pgmate.model.db.CommentServiceBean;
import com.pgmate.model.db.CommentSettleBean;
import com.pgmate.model.db.CommentTrnsctnBean;
import com.pgmate.model.db.dao.CommentMemberDAO;
import com.pgmate.model.db.dao.CommentServiceDAO;
import com.pgmate.model.db.dao.CommentSettleDAO;
import com.pgmate.model.db.dao.CommentTrnsctnDAO;

public class Comment {

	public void setMemberComment(long idx,String memberId,String comments){
		CommentMemberBean cmBean = new CommentMemberBean();
		cmBean.setIdx(idx);
		cmBean.setMemberId(memberId);
		cmBean.setComments(comments);
		new CommentMemberDAO().insert(cmBean);
	}
	
	public void setTrnsctnComment(long idx,String memberId,String comments){
		CommentTrnsctnBean cmBean = new CommentTrnsctnBean();
		cmBean.setIdx(idx);
		cmBean.setMemberId(memberId);
		cmBean.setComments(comments);
		new CommentTrnsctnDAO().insert(cmBean);
	}
	
	public void updateTrnsctnComment(long idx,String memberId,String comments){
		CommentTrnsctnBean cmBean = new CommentTrnsctnBean();
		cmBean.setIdx(idx);
		cmBean.setMemberId(memberId);
		cmBean.setComments(comments);
		new CommentTrnsctnDAO().update(cmBean);
	}
	
	public void setServiceComment(long idx,String memberId,String comments){
		CommentServiceBean cmBean = new CommentServiceBean();
		cmBean.setIdx(idx);
		cmBean.setMemberId(memberId);
		cmBean.setComments(comments);
		new CommentServiceDAO().insert(cmBean);
	}
	
	public void setSettleComment(long idx,String memberId,String comments){
		CommentSettleBean cmBean = new CommentSettleBean();
		cmBean.setIdx(idx);
		cmBean.setMemberId(memberId);
		cmBean.setComments(comments);
		new CommentSettleDAO().insert(cmBean);
	}
	
	public List<CommentMemberBean> getMemberComment(long idx){
		return new CommentMemberDAO().getByIdx(CommonUtil.toString(idx));
	}
	
	public List<CommentTrnsctnBean> getTrnsctnComment(long idx){
		return new CommentTrnsctnDAO().getByIdx(CommonUtil.toString(idx));
	}
	
	public List<CommentServiceBean> getServiceComment(long idx){
		return new CommentServiceDAO().getByIdx(CommonUtil.toString(idx));
	}
	
	public List<CommentSettleBean> getSettleComment(long idx){
		return new CommentSettleDAO().getByIdx(CommonUtil.toString(idx));
	}
		
}
