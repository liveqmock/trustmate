<%@page contentType="text/html; charset=UTF-8" %>
<%@ page import="biz.trustnet.common.util.CommonUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>



					<!-- BEGIN ADD FORM TABLE-->
					<div class="portlet box blue ">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i> PAYMENT RESULT
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal form-bordered form-row-stripped" role="form" name="payment" action="/adminPayment.do" method="post">
								<div class="form-body">
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4" for="inputSuccess">RESULT_CD<span class="required">*</span>
												</label>
												<div class="col-sm-6">
													${headerBean.resultCd}
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">RESULT_MSG<span class="required">*</span></label>
												<div class="col-sm-6">
													[${headerBean.resultMsg}]${resultMsg }
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">TRANSACTION_ID<span class="required">*</span></label>
												<div class="col-sm-6">
													${headerBean.transactionId }
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">PIN_NUMBER<span class="required">*</span></label>
												<div class="col-sm-6">
													${tBean.cardNumber }
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">EXPIRE(YYMM)<span class="required">*</span></label>
												<div class="col-sm-6">
													${tBean.cardExpire  }
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">APPROVAL_NO<span class="required">*</span></label>
												<div class="col-sm-6">
													${tBean.approvalNo }
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">AMOUNT<span class="required">*</span></label>
												<div class="col-sm-6">
													<fmt:formatNumber type="number" value="${tBean.amount }" pattern="#,##0" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">RESPONSE_DATE<span class="required">*</span></label>
												<div class="col-sm-6">
													${headerBean.trnResDate }
												</div>
											</div>
										</div>
									</div>
								</div>	
								
								<div class="form-actions right">
									<div class="">
										<a class="btn green btn-sm" href="javascript:getPageContent('/payment/payForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;Payment Page&nbsp;&nbsp;</a>
										
									</div>
								</div>
								
							</form>
						</div>
					</div>
