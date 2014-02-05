<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<h5 class="modal-title">Transaction Detail &nbsp; <small class="error">${trnsctnBean.transactionId}</small></h5>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet-body form">
						<form class="form-horizontal form-row-seperated" role="form" data-form="true" id="modalForm" name="modalForm" action="/adminPayment.do?TRN_TYPE=T002" method="post">
							<div class="form-body">
								<div class="form-group">
									<label class="col-sm-3 control-label">Transaction ID</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.transactionId}</label>
									
									<input name="transactionId" type="hidden" value ="${trnsctnBean.transactionId }" />
									<input name="specType" type="hidden" value="CFIX" />
						        	<input name="trnType" type="hidden" value="T002" />
						        	<input name="merchantId" type="hidden" value="${trnsctnBean.merchantId }" />
						        	<input name="mallId" type="hidden" value="${trnsctnBean.mallId }" />
						        	<input name="serviceType" type="hidden" value="${trnsctnBean.serviceType }" />
						        	<input name="ipAddress" type="hidden" value="${trnsctnBean.ipAddress }" />
						        	<input name="payNo" type="hidden" value="${trnsctnBean.payNo }" />
						        	<input name="voidType" type="hidden" value="1" />
						        	<input name="rTransactionId" type="hidden" value="${trnsctnBean.transactionId }"/>
						        	<input name="comment" type="hidden" value=""/>
						        	<input name="memberId" type="hidden" value="${ssoBean.memberId }"/>
									
									<label class="col-sm-3 control-label">Merchant Name</label>
									<label class="col-sm-3 control-label text-danger">${merchantBean.name }</label>
								
									<label class="col-sm-3 control-label">Merchant ID</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.merchantId }</label>
									<label class="col-sm-3 control-label">Mall ID</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.mallId }</label>
								
									<label class="col-sm-3 control-label">Approval No.</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.approvalNo }</label>
									<label class="col-sm-3 control-label">Order No.</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.payNo }</label>
									
									<label class="col-sm-3 control-label">Product Name</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.productName }</label>
									<label class="col-sm-3 control-label">Amount</label>
									<label class="col-sm-3 control-label text-danger">
										<c:if test="${trnsctnBean.curType == 'USD'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0.00" />&nbsp;$</c:if>
										<c:if test="${trnsctnBean.curType == 'JPY'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0" />&nbsp;円</c:if>
										<c:if test="${trnsctnBean.curType == 'KRW'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0" />&nbsp;</c:if>
										<c:if test="${trnsctnBean.curType == 'EUR'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0.00" />&nbsp;€</c:if>
									</label>
									
									<label class="col-sm-3 control-label">IP Address</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.ipAddress }</label>
									<label class="col-sm-3 control-label">Service Type</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.serviceType }</label>
									
									<label class="col-sm-3 control-label">Request Date</label>
									<label class="col-sm-3 control-label text-danger"><fmt:formatDate type="both" value="${trnsctnBean.trnReqDate }" pattern="yyyy/MM/dd HH:mm:ss" /></label>
									<input name="trnReqDate" type="hidden" value ="<fmt:formatDate type="both" value="${trnsctnBean.trnReqDate }" pattern="yyyyMMddHHmmss" />" />
									<label class="col-sm-3 control-label">Response Date</label>
									<label class="col-sm-3 control-label text-danger"><fmt:formatDate type="both" value="${trnsctnBean.trnResDate }" pattern="yyyy/MM/dd HH:mm:ss" /></label>
									
									<label class="col-sm-3 control-label">Card Brand</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnSCRBean.cardType }</label>
									<label class="col-sm-3 control-label">Card No.</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnSCRBean.cardNum }</label>
									
									<label class="col-sm-3 control-label">Payment User</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.payName }</label>
									<label class="col-sm-3 control-label">User Email</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.payEmail }</label>
									
									<label class="col-sm-3 control-label">User TelNo.</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.payTelNo }</label>
									<label class="col-sm-3 control-label">Currency</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.curType }</label>
									
									
									<label class="col-sm-3 control-label">Status</label>
									<label class="col-sm-3 control-label text-danger">
										<c:choose>
											<c:when test="${trnsctnBean.trnStatus == '00'}">On Approval
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '01'}">Error on approval
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '02'}">Approved
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '03'}">Declined
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '04'}">Request Cancel
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '05'}">Error on cancel
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '06'}">Canceled
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '07' || trnsctnBean.trnStatus == '08'}">Acquiring
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '09' || trnsctnBean.trnStatus == '10'}">Acquired
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '11' || trnsctnBean.trnStatus == '12'}">Ready for paid
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '13'}">Paid
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '14'}">Refund
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '15'}">Request Refund
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '17'}">Ready for cancel
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '18' || trnsctnBean.trnStatus == '19' || trnsctnBean.trnStatus == '20'}">Refunded
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '21'}">Acquiring Failed
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '22' || trnsctnBean.trnStatus == '23'}">Chargeback
											</c:when>
											<c:otherwise>
												${trnsctnBean.trnStatus }
											</c:otherwise>
										</c:choose>
									</label>
									<label class="col-sm-3 control-label">URL</label>
									<label class="col-sm-3 control-label text-danger"><a href="http://${trnsctnBean.temp1 }" target="_blank">${trnsctnBean.temp1 }</a></label>
									<label class="col-sm-3 control-label">Result Msg</label>
									<label class="col-sm-9 control-label text-danger text-left">${resultMsg}&nbsp;[${trnsctnBean.resultMsg }]</label>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label text-left">reason of refund</label>
									<label class="col-sm-9 control-label ">
									<textarea class="col-sm-9 form-control" rows="3"></textarea>
									</label>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<c:choose>
				<c:when test="${trnsctnBean.trnStatus == '02' }">
					<button type="button" class="btn blue btn-sm refund">Cancel</button>
				</c:when>
				<c:when test="${trnsctnBean.trnStatus == '07' || trnsctnBean.trnStatus == '08' || trnsctnBean.trnStatus == '09' || trnsctnBean.trnStatus == '10' || trnsctnBean.trnStatus == '11' || trnsctnBean.trnStatus == '12' || trnsctnBean.trnStatus == '13'}">
				    <button type="button" class="btn blue btn-sm refund">Refund</button>								
		   		</c:when>
			    <c:otherwise>						    	
			    </c:otherwise>
		    </c:choose>
			<button type="button" class="btn default" data-dismiss="modal">Close</button>
		</div>
	</div>
	<!-- /.modal-content -->

<!-- /.modal-dialog -->