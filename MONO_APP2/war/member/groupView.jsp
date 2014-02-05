<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-sm-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Group <small>Information</small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Member</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Group Information</a>
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
								<i class="fa fa-reorder"></i> Group Information
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
												<label class="control-label col-sm-4">Group ID
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${gBean.groupId }</p>
													
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">
													<a class="btn dark btn-sm" href="javascript:changePassword('GROUP','${gBean.groupId }');">Change Password</a>
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
												<label class="control-label col-sm-4">Group Name
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${gBean.name }&nbsp;</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Product</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${gBean.product}&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Homepage</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${gBean.homepage }&nbsp;</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Address</label>
												<div class="col-sm-8">
													<p class="form-control-static2">
													<c:if test="${!empty gBean.zipCode}">
														[${gBean.zipCode }]
													</c:if>
													${gBean.addr1 }&nbsp;${gBean.addr2 }&nbsp;
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
													<p class="form-control-static2">${gBean.telNo }&nbsp;</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Fax No</label>
												<div class="col-sm-8">
														<p class="form-control-static2">${gBean.fax }&nbsp;</p>
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
													<p class="form-control-static2">${gBean.email }&nbsp;</p>
													
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Registration No.</label>
												<div class="col-sm-8">
														<p class="form-control-static2">${gBean.identiNo }&nbsp;</p>
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
													<p class="form-control-static2">${gBean.settleBank }&nbsp;</p>
													
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">BankCode</label>
												<div class="col-sm-8">
														<p class="form-control-static2">${gBean.settleBankCode }&nbsp;</p>
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
													<p class="form-control-static2">${gBean.settleAccount }&nbsp;</p>
													
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Status</label>
												<div class="col-sm-8">
														<c:if test="${gBean.active =='0' }">
														<p class="form-control-static2">READY</p>
														</c:if>
														<c:if test="${gBean.active =='1' }">
														<p class="form-control-static2">ACTIVE</p>
														</c:if>
														<c:if test="${gBean.active =='2' }">
															<p class="form-control-static2">INACTIVE</p>
														</c:if>
														<c:if test="${gBean.active =='3' }">
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
												<label class="control-label col-sm-4">Registration Date</label>
												<div class="col-sm-8">
														<p class="form-control-static2"><fmt:formatDate type="both" value="${gBean.regDate }" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</p>
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
										<h4 class="form-section2">2. CEO information</h4>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">CEO NAME
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${gBean.ceoName }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">CEO NAME(English)
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${gBean.ceoEngName }</p>
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
														<c:if test ="{!empty gBean.ceoZipCode}">
														[${gBean.ceoZipCode }] &nbsp; 
														</c:if>${gBean.ceoAddr1 }${gBean.ceoAddr2 }&nbsp;
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
													<p class="form-control-static2">${gBean.ceoTelNo }&nbsp;</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Phone No.</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${gBean.ceoPhoneNo }&nbsp;</p>
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
													<p class="form-control-static2">${gBean.ceoEMail }&nbsp;</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Iden No.</label>
												<div class="col-sm-8">
														<p class="form-control-static2">${gBean.ceoIdentiNo }&nbsp;</p>
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
													<p class="form-control-static2">${gBean.settlePartName }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">E-MAIL</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${gBean.settlePartEMail }&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Tel No.</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${gBean.settlePartTelNo }&nbsp;</p>
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
								<div class="form-actions right">
									<div class="">
										<c:if test="${sso.memberRole == '0001' }">
											<a class="btn dark btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member/groupForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
											<button type="button" class="btn purple btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member.do?request=geditForm&idx=${gBean.idx}');"><i class="fa fa-edit"></i>&nbsp;Edit</button>
										</c:if>
									</div>
								</div>
								
							</form>
						</div>

					</div>
					
					
					<div id="pgmate-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1"></div>
					
					
				</div>
			</div>
