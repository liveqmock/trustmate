<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<div class="modal-content">
		<div class="modal-header">
			
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<ul class="nav nav-tabs">
				<li class="active">
					<a href="#tab_1_1" data-toggle="tab">Transaction</a>
				</li>
				<li class="">
					<a href="#tab_1_2" data-toggle="tab">Refund</a>
				</li>
				<c:if test="${trnsctnCbBean.transactionId != '' || sso.memberRole =='0001'}">
				<li class="">
					<a href="#tab_1_3" data-toggle="tab">ChargeBack</a>
				</li>
				</c:if>
			</ul>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="tab-content">
					<!--  TRANSACTION DIV START -->
					<div class="tab-pane fade active in" id="tab_1_1">
						<div class="portlet-body form">
							<div class="form-body">
								<div class="form-group">
									<label class="col-sm-3 control-label">Transaction ID</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.transactionId}
										<input type="hidden" name="transactionId" id="transactionId" value="${trnsctnBean.transactionId}">
									</label>
									
									<label class="col-sm-3 control-label">Merchant Name</label>
									<label class="col-sm-3 control-label text-danger">${merchantBean.name }&nbsp;</label>
								
									<label class="col-sm-3 control-label">Merchant ID</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.merchantId }</label>
									<label class="col-sm-3 control-label">Mall ID</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.mallId }&nbsp;</label>
								
									<label class="col-sm-3 control-label">Approval No.</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.approvalNo }</label>
									<label class="col-sm-3 control-label">Order No.</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.payNo }&nbsp;</label>
									
									<label class="col-sm-3 control-label">Product Name</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.productName }&nbsp;</label>
									<label class="col-sm-3 control-label">Amount</label>
									<label class="col-sm-3 control-label text-danger">
										<c:if test="${trnsctnBean.curType == 'USD'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0.00" />&nbsp;$</c:if>
										<c:if test="${trnsctnBean.curType == 'JPY'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0" />&nbsp;円</c:if>
										<c:if test="${trnsctnBean.curType == 'KRW'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0" />&nbsp;</c:if>
										<c:if test="${trnsctnBean.curType == 'EUR'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0.00" />&nbsp;€</c:if>
									</label>
									
									<label class="col-sm-3 control-label">IP Address</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.ipAddress }&nbsp;</label>
									<label class="col-sm-3 control-label">Service Type</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.serviceType }&nbsp;</label>
									
									<label class="col-sm-3 control-label">Request Date</label>
									<label class="col-sm-3 control-label text-danger"><fmt:formatDate type="both" value="${trnsctnBean.trnReqDate }" pattern="yyyy/MM/dd HH:mm:ss" /></label>
									<input name="trnReqDate" type="hidden" value ="<fmt:formatDate type="both" value="${trnsctnBean.trnReqDate }" pattern="yyyyMMddHHmmss" />" />
									<label class="col-sm-3 control-label">Response Date</label>
									<label class="col-sm-3 control-label text-danger"><fmt:formatDate type="both" value="${trnsctnBean.trnResDate }" pattern="yyyy/MM/dd HH:mm:ss" /></label>
									
									<label class="col-sm-3 control-label">Card Brand</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnSCRBean.cardType }&nbsp;</label>
									<label class="col-sm-3 control-label">Card No.</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnSCRBean.cardNum }&nbsp;</label>
									
									<label class="col-sm-3 control-label">Payment User</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.payName }&nbsp;</label>
									<label class="col-sm-3 control-label">User Email</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.payEmail }&nbsp;</label>
									
									<label class="col-sm-3 control-label">User TelNo.</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.payTelNo }&nbsp;</label>
									<label class="col-sm-3 control-label">Currency</label>
									<label class="col-sm-3 control-label text-danger">${trnsctnBean.curType }&nbsp;</label>
									
									
									<label class="col-sm-3 control-label">Status</label>
									<label class="col-sm-3 control-label text-danger">${trnStatus}</label>
									<label class="col-sm-3 control-label">URL</label>
									<label class="col-sm-3 control-label text-danger">
										<c:if test="${!empty trnsctnBean.temp1}">
										<a href="http://${trnsctnBean.temp1 }" target="_blank">${trnsctnBean.temp1 }</a>
										</c:if>&nbsp;
									</label>
									<label class="col-sm-3 control-label">Result Msg</label>
									<label class="col-sm-9 control-label text-danger text-left">${resultMsg}&nbsp;[${trnsctnBean.resultMsg }]</label>
								</div>
								
							</div>
						</div>
					</div>
					<!--  TRANSACTION DIV END -->
					<!--  REFUND DIV START -->
					<div class="tab-pane fade" id="tab_1_2">
						<div class="portlet-body form">
							
							<div class="form-body">
								<c:if test="${trnsctnVoidBean.transactionId != '' }">
									<div class="form-group">
										<label class="col-sm-3 control-label">Transaction ID</label>
										<label class="col-sm-3 control-label text-danger">${trnsctnBean.transactionId}</label>
										<label class="col-sm-3 control-label">Refund ID</label>
										<label class="col-sm-3 control-label text-danger">${trnsctnVoidBean.voidTransactionId}</label>
										
										<label class="col-sm-3 control-label">Previous Status</label>
										<label class="col-sm-3 control-label text-danger">${voidTrnStatus}</label>
										<label class="col-sm-3 control-label">Refund Status</label>
										<label class="col-sm-3 control-label text-danger">
											<c:choose>
												<c:when test="${trnsctnVoidBean.status == '11' || trnsctnVoidBean.status == '33' }">
													refund request
												</c:when>
												<c:when test="${trnsctnVoidBean.status == '00'}">
													refunded
												</c:when>
												<c:when test="${trnsctnVoidBean.status == '99'}">
													refund failed
												</c:when>
											</c:choose>
											</label>
										
										<label class="col-sm-3 control-label">Request Date</label>
										<label class="col-sm-3 control-label text-danger"><fmt:formatDate type="both" value="${trnsctnVoidBean.voidReqDate }" pattern="yyyy/MM/dd HH:mm:ss" /></label>
										<label class="col-sm-3 control-label">Response Date</label>
										<label class="col-sm-3 control-label text-danger"><fmt:formatDate type="both" value="${trnsctnVoidBean.voidResDate }" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</label>
										
										<label class="col-sm-3 control-label">Refund Comment</label>
										<label class="col-sm-9 control-label text-danger">
											<c:forEach var="list" items="${voidCommentList}" varStatus="status">
											<fmt:formatDate type="both" value="${list.regDate }" pattern="yyyy-MM-dd HH:mm:ss" /> : ${list.comments} : ${list.memberId }<br/>
											</c:forEach>
											
										</label>
									</div>
								</c:if>
								<c:if test="${trnsctnVoidBean.transactionId == '' }">
									<div class="form-group">
										<label class="col-sm-3 control-label">Transaction ID</label>
										<label class="col-sm-3 control-label text-danger">${trnsctnBean.transactionId}</label>
										<label class="col-sm-3 control-label">Status</label>
										<label class="col-sm-3 control-label text-danger">${trnStatus}</label>
										<label class="col-sm-12 control-label">&nbsp;</label>
									</div>
									<c:if test="${trnsctnBean.trnStatus == '02' || trnsctnBean.trnStatus == '07' || trnsctnBean.trnStatus == '08' || trnsctnBean.trnStatus == '09' || trnsctnBean.trnStatus == '10' || trnsctnBean.trnStatus == '11' || trnsctnBean.trnStatus == '12' || trnsctnBean.trnStatus == '13' || trnsctnBean.trnStatus == '23' }">
									<c:if test="${sso.memberRole != '0003'}">
									
									<div class="form-group">
										<label class="col-sm-3 control-label text-left">reason of refund</label>
										<label class="col-sm-9 control-label ">
										<textarea class="col-sm-9 form-control" rows="5" name="refundComment" id="refundComment"></textarea>
										</label>
									</div>
									<div class="form-group">
										<c:choose>
											<c:when test="${trnsctnBean.trnStatus == '02' }">
												<button type="button" class="btn blue btn-sm refund pull-right">Request Cancel</button>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '07' || trnsctnBean.trnStatus == '08' || trnsctnBean.trnStatus == '09' || trnsctnBean.trnStatus == '10' || trnsctnBean.trnStatus == '11' || trnsctnBean.trnStatus == '12' || trnsctnBean.trnStatus == '13' || trnsctnBean.trnStatus == '23' }">
											    <button type="button" class="btn blue btn-sm refund pull-right">Request Refund</button>								
									   		</c:when>
										    <c:otherwise>						    	
										    </c:otherwise>
									    </c:choose>
									</div>
									</c:if>
									</c:if>
								
								</c:if>
							</div>
							
						</div>
					</div>
					<!--  REFUND DIV END -->
					<!--  CHARGEBACK DIV START -->
					<div class="tab-pane fade" id="tab_1_3">
						<div class="portlet-body form">
							
							<div class="form-body">
								<c:if test="${trnsctnCbBean.transactionId != '' }">
									<div class="form-group">
										<label class="col-sm-3 control-label">Transaction ID</label>
										<label class="col-sm-3 control-label text-danger">${trnsctnBean.transactionId}</label>
										
										<label class="col-sm-3 control-label">Previous Status</label>
										<label class="col-sm-3 control-label text-danger">${cbTrnStatus}&nbsp;</label>
										
										<label class="col-sm-3 control-label">C*B Status</label>
										<label class="col-sm-3 control-label text-danger">
											<c:choose>
												<c:when test="${trnsctnCbBean.cbState == '1' || trnsctnCbBean.cbState == '2' }">
													C*B accept
												</c:when>
												<c:when test="${trnsctnCbBean.cbState == '3'}">
													C*B REGISTED
												</c:when>
												<c:otherwise>
													${trnsctnCbBean.cbState}
												</c:otherwise>
											</c:choose>
										</label>
										
										<label class="col-sm-3 control-label">Regist Date</label>
										<label class="col-sm-3 control-label text-danger"><fmt:formatDate type="both" value="${trnsctnCbBean.regDate }" pattern="yyyy/MM/dd HH:mm:ss" /></label>
										
										<label class="col-sm-3 control-label">C*B Comment</label>
										<label class="col-sm-9 control-label text-danger">
											<c:forEach var="list" items="${cbCommentList}" varStatus="status">
											<fmt:formatDate type="both" value="${list.regDate }" pattern="yyyy-MM-dd HH:mm:ss" /> : ${list.comments} : ${list.memberId }<br/>
											</c:forEach>&nbsp;
										</label>
									</div>
								</c:if>
								<c:if test="${trnsctnCbBean.transactionId == '' &&  sso.memberRole =='0001'}">
									<div class="form-group">
										<label class="col-sm-3 control-label">Transaction ID</label>
										<label class="col-sm-3 control-label text-danger">${trnsctnBean.transactionId}</label>
										<label class="col-sm-3 control-label">Current Status</label>
										<label class="col-sm-3 control-label text-danger">${trnStatus}</label>
										<label class="col-sm-12 control-label">&nbsp;</label>
									</div>
									<c:if test="${trnsctnBean.trnStatus == '06' || trnsctnBean.trnStatus == '02' || trnsctnBean.trnStatus == '07' || trnsctnBean.trnStatus == '08' || trnsctnBean.trnStatus == '09' || trnsctnBean.trnStatus == '10' || trnsctnBean.trnStatus == '11' || trnsctnBean.trnStatus == '12' || trnsctnBean.trnStatus == '13' }">									
									<div class="form-group">
										<label class="col-sm-3 control-label text-left">reason of chargeback</label>
										<label class="col-sm-9 control-label ">
										<textarea class="col-sm-9 form-control" rows="5" name="cbComment" id="cbComment"></textarea>
										</label>
									</div>
									<div class="form-group">
										<button type="button" class="btn blue btn-sm chargeback pull-right">C*B REGISTRATION</button>
									</div>
									</c:if>
								</c:if>
							</div>
							
						</div>
					</div>
					<!--  CHARGEBACK DIV END -->
				</div>
					
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">Close</button>
		</div>
	</div>
	<!-- /.modal-content -->

<!-- /.modal-dialog -->