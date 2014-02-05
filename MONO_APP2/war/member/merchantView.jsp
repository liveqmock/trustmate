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
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
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
								
								<div class="form-actions right">
									<div class="">
										<a class="btn dark btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member/merchantForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
										<button type="button" class="btn purple btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member.do?request=editForm&idx=${mBean.idx}');"><i class="fa fa-edit"></i>&nbsp;Edit</button>
									</div>
								</div>
								
							</form>
						</div>

					</div>
					
					
					

					<div class="portlet box green ">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i> Service Information
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal" role="form" >
								<c:if test="${mngBean.idx != 0 }">
								<div class="form-body">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Currency
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mngBean.curType}</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Ticket Size
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2"><fmt:formatNumber type="number" value="${mngBean.onceLimit }" pattern="#,##0" /></p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Daily Limit
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2"><fmt:formatNumber type="number" value="${mngBean.dayLimit }" pattern="#,##0" /></p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Monthly Limit 
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2"><fmt:formatNumber type="number" value="${mngBean.monthLimit }" pattern="#,##0" /></p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Monthly Limit of Card
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2"><fmt:formatNumber type="number" value="${mngBean.cardMonthLimit }" pattern="#,##0" /></p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Limit number of Card
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2"><fmt:formatNumber type="number" value="${mngBean.duplicationCount }" pattern="#,##0" /></p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Monthly Limit number of Card 
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2"><fmt:formatNumber type="number" value="${mngBean.tempLimit }" pattern="#,##0" /></p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Transaction Type
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2 text-danger">
														<c:if test="${mngBean.demo =='Y' }">
															TEST MODE
														</c:if>
														<c:if test="${mngBean.demo =='N' }">
															SERVICE MODE
														</c:if>
													</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">VAN
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mngBean.van }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">VAN ID
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mngBean.vanId }&nbsp;&nbsp;[${vanBean.descriptor}]</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Authentication Service
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2 text-danger">
														<c:if test="${mngBean.auth =='Y' }">
															ACTIVE
														</c:if>
														<c:if test="${mngBean.auth =='N' }">
															INACTIVE
														</c:if>
													</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Report Email
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">
														${mngBean.reportEMail }&nbsp;
													</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Decision Manager
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2 text-danger">
														<c:if test="${mngBean.dm =='Y' }">
															ACTIVE
														</c:if>
														<c:if test="${mngBean.dm =='N' }">
															INACTIVE
														</c:if>
													</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">&nbsp;
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">
														&nbsp;
													</p>
												</div>
											</div>
										</div>
									</div>
								</div>	
								</c:if>
								<div class="form-actions right">
									<div class="">
										<c:choose>
											<c:when test="${sso.memberRole == '0001'}">
												<c:if test="${mngBean.idx == 0 }">
													<button type="button" class="btn purple btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member/merchantServiceAddForm.jsp?merchantId=${mBean.merchantId}');"><i class="fa fa-edit"></i>&nbsp;Registration</button>
												</c:if>
												<c:if test="${mngBean.idx != 0 }">
													<a class="btn dark btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member/merchantForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
													<button type="button" class="btn purple btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member.do?request=serviceEditForm&idx=${mngBean.idx}');"><i class="fa fa-edit"></i>&nbsp;Edit</button>
												</c:if>
											</c:when>
											<c:otherwise>
												<a class="btn dark btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member/merchantForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
											</c:otherwise>
											
										</c:choose>
										
									</div>
								</div>
							</form>
						</div>

					</div>
					
					
					
					
					<div class="portlet box green ">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i> Settlement Information
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal" role="form" >
								<c:if test="${mbBean.merchantId != '' }">
								<div class="form-body">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Settle Type
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">
													[${mbBean.period}]&nbsp;
													
													<c:choose>
														<c:when test="${mbBean.period == 'TYPE070' }">Three Weeks Arrear (-28~-22 Monday)</c:when>
														<c:when test="${mbBean.period == 'TYPE071' }">Two Weeks Arrear (-21~-15 Monday)</c:when>
														<c:when test="${mbBean.period == 'TYPE100' }">10 Days (Every 10day +3)</c:when>
														<c:when test="${mbBean.period == 'TYPE150' }">15 Days (Every 15day +3 )</c:when>
														<c:when test="${mbBean.period == 'TYPE300' }">30 Days (Every one month +3)</c:when>
														<c:otherwise>&nbsp;</c:otherwise>
													</c:choose>
													</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">V/M MDR
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2 text-danger"><fmt:formatNumber type="number" value="${mbBean.visamaster *100 }" pattern="###.###" />&nbsp;%</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">J/A MDR
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2 text-danger"><fmt:formatNumber type="number" value="${mbBean.jcbamex *100}" pattern="###.###" />&nbsp;%</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Transaction Fee
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2 text-danger"><fmt:formatNumber type="number" value="${mbBean.transaction }" pattern="###.###" /></p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Refund Fee
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2"><fmt:formatNumber type="number" value="${mbBean.refund }" pattern="###.###" /></p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">ChargeBack Fee
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2"><fmt:formatNumber type="number" value="${mbBean.chargeback }" pattern="###.###" /></p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Annual Fee
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2"><fmt:formatNumber type="number" value="${mbBean.management }" pattern="###.##" /></p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Wire Fee
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2 text-danger">
														<fmt:formatNumber type="number" value="${mbBean.bankTransfer }" pattern="###.##" />
													</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Deposit
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2"><fmt:formatNumber type="number" value="${mbBean.deposit *100 }" pattern="###.##" />&nbsp;%</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Value Added Tax
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2"><fmt:formatNumber type="number" value="${mbBean.vat *100}" pattern="###.##" />&nbsp;%</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Setup Fee
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">
														<fmt:formatNumber type="number" value="${mbBean.setupFee }" pattern="###.##" />
													</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Settlement Status 
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2 text-danger">
														<c:choose>
															<c:when test="${mbBean.active == 'Y' }">ACTIVE</c:when>
															<c:when test="${mbBean.active == 'N' }">INACTIVE</c:when>
															<c:otherwise>&nbsp;</c:otherwise>
														</c:choose>
													</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">BankCode
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2 text-danger">${mbBean.bankCode }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Bank Branch&nbsp;
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mbBean.branch }</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Bank Account
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2 text-danger">${mbBean.account }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Account Holder&nbsp;
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mbBean.accountHolder }</p>
												</div>
											</div>
										</div>
									</div>
								</div>	
								</c:if>
								<div class="form-actions right">
									<div class="">
										<c:choose>
											<c:when test="${sso.memberRole == '0001'}">
												<c:if test="${mbBean.merchantId == '' }">
												<button type="button" class="btn purple btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/bill/billAddForm.jsp?merchantId=${mBean.merchantId}');"><i class="fa fa-edit"></i>&nbsp;Registration</button>
												</c:if>
												<c:if test="${mbBean.merchantId != ''}">
													<a class="btn dark btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member/merchantForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
													<button type="button" class="btn purple btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/bill.do?request=editForm&merchantId=${mngBean.merchantId}');"><i class="fa fa-edit"></i>&nbsp;Edit</button>
												</c:if>
											</c:when>
											<c:otherwise>
												<a class="btn dark btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member/merchantForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
											</c:otherwise>
										</c:choose>
										
									</div>
								</div>
								
							</form>
						</div>

					</div>
					
					
					
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
								<c:if test="${gBean.groupId != '' }">
								<div class="form-body">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Group ID
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">
													${gBean.groupId }
													</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Group Name
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${gBean.name }</p>
												</div>
											</div>
										</div>
									</div>
								</div>	
								</c:if>
								<div class="form-actions right">
									<div class="">
									
										<c:choose>
											<c:when test="${sso.memberRole == '0001'}">
												<c:if test="${gBean.groupId == '' }">
												<button type="button" class="btn purple btn-sm" onclick="javascript:changeGroup('${mBean.merchantId}');"><i class="fa fa-edit"></i>&nbsp;Add GROUP</button>
												</c:if>
												<c:if test="${gBean.groupId != '' }">
												<a class="btn dark btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member/merchantForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
												<button type="button" class="btn purple btn-sm" onclick="javascript:changeGroup('${mBean.merchantId}');"><i class="fa fa-edit"></i>&nbsp;Edit GROUP</button>
												</c:if>
											</c:when>
											<c:otherwise>
												<a class="btn dark btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member/merchantForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								
							</form>
						</div>

					</div>
					
					<div class="portlet box green ">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Mall Infomation &nbsp; 
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body flip-scroll" id="printDiv">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover flip-content" id="sortTable"><!-- 작게 table-condensed -->
								<thead>
									<tr>
										<th>No</th>
										<th data-sort="string">Mall ID</th>
										<th data-sort="string">Tel No.</th>
										<th data-sort="string">Fax No.</th>
										<th data-sort="string">E-Mail</th>
										<th data-sort="string">Site</th>
										<th data-sort="string">Status</th>
										<th data-sort="string">Create Date</th>
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(mallList) != 0}">
										<c:forEach var="list" items="${(mallList) }" varStatus="status">
											<tr>
												<td>${status.count)}</td>
												<td>${list.mallId }&nbsp;</td>
												<td>${list.tel }&nbsp;</td>
												<td>${list.fax }&nbsp;</td>
												<td>${list.email }&nbsp;</td>
												<td>${list.site }&nbsp;</td>
												<td>
													<c:choose>
														<c:when test="${list.active == '0'}"><font color="#800080">READY</font>
														</c:when>
														<c:when test="${list.active == '1'}"><font color="blue">ACTIVE</font>
														</c:when>
														<c:when test="${list.active == '2'}"><font color="green">INACTIVE</font>
														</c:when>
														<c:when test="${list.active == '3'}"><font color="red">TERMINATED</font>
														</c:when>
														<c:otherwise>
															${list.active }
														</c:otherwise>
													</c:choose>
												</td>
												<td><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
											</tr>
										</c:forEach>
									</c:when>
									
								</c:choose>
								</tbody>
								</table>
							</div>
						</div>
					</div>
					<div id="pgmate-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1"></div>
					
					<script>
						function changeGroup(merchantId){
							var $modal = $('#pgmate-modal');
							
							$modal.attr("data-width","600px");
							$('body').modalmanager('loading');
							setTimeout(function(){
						    	$modal.load('/member.do',{"request":"groupSelectForm","merchantId":merchantId}, function(){
						        $modal.modal();
						    	});
						   	}, 100);
							
							//여기 부분은 코딩해야함.
						    $modal.on('click', '.update', function(){
						    	
						      if($('#modalForm').validate().form()){
						    	  $modal.modal('loading');
						    	  var datas = $("#modalForm").serialize();
						    		$.ajax({
						    			url : $("#modalForm").attr("action"),
						    			type : 'POST',
						    			data : datas,
						    			success : function(msg){
						    				var res = msg.split("||");
						    				if(res[0] == "OK"){
						    					$modal.modal('hide');
						    					bootbox.alert(res[1]);
						    					getPageContent('/member.do?request=view&merchantId='+merchantId);
						    				}else{
						    					$modal.modal('removeLoading');
						    					bootbox.alert(res[1]);
						    				}
						    			},
						    			error : function(result){
						    				bootbox.alert("FUNCTION EXECUTE ERROR"+result);
						    				$modal.modal('removeLoading');
						    			}
						    		});
						    		
						      }
							});
						    $modal.on('shown', function() {
								$(this).find("#groupId").focus();
							});
							$modal.on('hidden', function() {
								 $('#pgmate-modal').removeData();
							});
						}
					
					</script>
					
					
					
				</div>
			</div>
