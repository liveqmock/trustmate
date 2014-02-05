<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-sm-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Merchant <small>Information</small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/merchant/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Member</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Merchant Information</a>
						</li>
						
						
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->

			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-sm-12">
					<!-- BEGIN ADD FORM TABLE-->
					<div class="portlet box green ">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i> Merchant Information
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal" role="form" >
								
								<div class="form-body">
									<div class="row">
										<div class="col-sm-12 ">
										<h4 class="form-section2">1. Basic information</h4>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Merchant ID
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.merchantId }</p>
													
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">
													<a class="btn dark btn-sm" href="javascript:changePassword('MERCHANT','${mBean.merchantId }');">Change Password</a>
												</label>
												<div class="col-sm-8">
													&nbsp;
												</div>
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Merchant Name
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.name }&nbsp;</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Product</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.product}&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Homepage</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.homepage }&nbsp;</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Address</label>
												<div class="col-sm-8">
													<p class="form-control-static2">
													<c:if test="${!empty mBean.zipCode}">
														[${mBean.zipCode }]
													</c:if>
													${mBean.addr1 }&nbsp;${mBean.addr2 }&nbsp;
													</p>
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Tel No</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.telNo }&nbsp;</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Fax No</label>
												<div class="col-sm-8">
														<p class="form-control-static2">${mBean.fax }&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">E-Mail</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.email }&nbsp;</p>
													
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Registration No.</label>
												<div class="col-sm-8">
														<p class="form-control-static2">${mBean.identiNo }&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Bank</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.settleBank }&nbsp;</p>
													
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">BankCode</label>
												<div class="col-sm-8">
														<p class="form-control-static2">${mBean.settleBankCode }&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Bank Account</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.settleAccount }&nbsp;</p>
													
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Status</label>
												<div class="col-sm-8">
														<c:if test="${mBean.active =='0' }">
														<p class="form-control-static2">READY</p>
														</c:if>
														<c:if test="${mBean.active =='1' }">
														<p class="form-control-static2">ACTIVE</p>
														</c:if>
														<c:if test="${mBean.active =='2' }">
															<p class="form-control-static2">INACTIVE</p>
														</c:if>
														<c:if test="${mBean.active =='3' }">
															<p class="form-control-static2">TERMINATED</p>
														</c:if>
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Service Date</label>
												<div class="col-sm-8">
													<p class="form-control-static2"><fmt:formatDate type="both" value="${mBean.serviceDate }" pattern="yyyy/MM/dd" /></p>
													
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Registration Date</label>
												<div class="col-sm-8">
														<p class="form-control-static2"><fmt:formatDate type="both" value="${mBean.regDate }" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
								</div>	
								
								
								<div class="form-body">
									<div class="row">
										<div class="col-sm-12 ">
										<h4 class="form-section2">2. CEO information</h4>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">CEO NAME
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.ceoName }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">CEO NAME(English)
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.ceoEngName }</p>
												</div>
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">CEO Address
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">
														<c:if test ="{!empty mBean.ceoZipCode}">
														[${mBean.ceoZipCode }] &nbsp; 
														</c:if>${mBean.ceoAddr1 }${mBean.ceoAddr2 }&nbsp;
													</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">&nbsp;</label>
												<div class="col-sm-8">
													<p class="form-control-static2">&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Tel No.</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.ceoTelNo }&nbsp;</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Phone No.</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.ceoPhoneNo }&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">E-MAIL</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.ceoEMail }&nbsp;</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Iden No.</label>
												<div class="col-sm-8">
														<p class="form-control-static2">${mBean.ceoIdentiNo }&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									
								</div>	
								
								
								<div class="form-body">
									<div class="row">
										<div class="col-sm-12 ">
										<h4 class="form-section2">3. Settlement Dept </h4>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4"> NAME
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.settlePartName }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">E-MAIL</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.settlePartEMail }&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Tel No.</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.settlePartTelNo }&nbsp;</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">&nbsp;</label>
												<div class="col-sm-8">
													<p class="form-control-static2">&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									
									
								</div>	
								<div class="form-body">
									<div class="row">
										<div class="col-sm-12 ">
										<h4 class="form-section2">4. Development Dept </h4>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4"> NAME
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.devPartName }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">E-MAIL</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.devPartEMail }&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Tel No.</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.devPartTelNo }&nbsp;</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">&nbsp;</label>
												<div class="col-sm-8">
													<p class="form-control-static2">&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									
								</div>	
								
								
								
							</form>
						</div>

					</div>
					
					
				</div>
				<div id="pgmate-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1"></div>
			</div>
