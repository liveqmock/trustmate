<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-sm-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Agent <small>Information</small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Member</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Agent Information</a>
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
								<i class="fa fa-reorder"></i> Agent Information
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal form-bordered form-row-stripped" role="form" >
								<div class="form-body">
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Agent ID
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${agentBean.agentId }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Password
												</label>
												<div class="col-sm-8">
													<a class="btn green btn-sm right" href="javascript:changePassword('AGENT','${agentBean.agentId }');">Change Password</a>
												</div>
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Agent Name
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${agentBean.name }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Status</label>
												<div class="col-sm-8">
													<c:if test="${agentBean.active =='1' }">
														<p class="form-control-static2">ACTIVE</p>
													</c:if>
													<c:if test="${agentBean.active =='2' }">
														<p class="form-control-static2">INACTIVE</p>
													</c:if>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Bank Code</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${agentBean.bankCode }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Branch</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${agentBean.branch }</p>
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Account</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${agentBean.account }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Account Holder</label>
												<div class="col-sm-8">
														<p class="form-control-static2">${agentBean.accountHolder }</p>
												</div>
											</div>
										</div>
									</div>
								</div>	
								
								<div class="form-actions right">
									<div class="">
										<a class="btn dark btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member/agentForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
										<button type="button" class="btn purple btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member.do?request=agentEditForm&agentId=${agentBean.agentId}');"><i class="fa fa-edit"></i>&nbsp;Edit</button>
									</div>
								</div>
								
							</form>
						</div>

					</div>
					
					
					<div class="portlet box green ">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Agent Settlement Infomation &nbsp; 
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
										<th data-sort="string">Merchant Id</th>
										<th data-sort="string">Mercahnt Name</th>
										<th data-sort="float">V/M MDR</th>
										<th data-sort="float">J/A MDR</th>
										<th data-sort="float">Transaction Fee</th>
										<th data-sort="float">Refund Fee</th>
										<th data-sort="float">ChargeBack Fee</th>
										<th data-sort="float">Annual Fee</th>
										<th data-sort="float">Setup Fee</th>
										<th data-sort="float">Wire Fee</th>
										<th data-sort="float">Value Added Tax</th>
										<th data-sort="string">Settlement Status</th>
										<th >Action</th>
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(list) != 0}">
										<c:forEach var="list" items="${(list) }" varStatus="status">
											<tr>
												<td>${status.count)}</td>
												<td>${list.merchantId }&nbsp;</td>
												<td>${list.temp1String }&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.visamaster *100 }" pattern="###.###" />&nbsp;%</td>
												<td><fmt:formatNumber type="number" value="${list.jcbamex *100}" pattern="###.###" />&nbsp;%</td>
												<td><fmt:formatNumber type="number" value="${list.transaction }" pattern="###.###" /></td>
												<td><fmt:formatNumber type="number" value="${list.refund }" pattern="###.###" /></td>
												<td><fmt:formatNumber type="number" value="${list.chargeback }" pattern="###.###" /></td>
												<td><fmt:formatNumber type="number" value="${list.management }" pattern="###.##" /></td>
												<td><fmt:formatNumber type="number" value="${list.setupFee }" pattern="###.##" /></td>
												<td><fmt:formatNumber type="number" value="${list.bankTransfer }" pattern="###.##" /></td>
												<td><fmt:formatNumber type="number" value="${list.vat *100 }" pattern="###.###" />&nbsp;%</td>
												<td>
													<c:choose>
														<c:when test="${list.active == 'Y' }">ACTIVE</c:when>
														<c:when test="${list.active == 'N' }">INACTIVE</c:when>
														<c:otherwise>&nbsp;</c:otherwise>
													</c:choose>
												</td>
												<td><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" />
													<c:choose>
														<c:when test="${empty list.regDate}">
															<button class="btn blue btn-sm" onclick="getPageContent('/bill/agentBillAddForm.jsp?agentId=${list.agentId}&merchantId=${list.merchantId}');">Add</button>
														</c:when>
														<c:otherwise>
															<button class="btn purple btn-sm" onclick="getPageContent('/bill.do?request=agentEditForm&agentId=${list.agentId}&merchantId=${list.merchantId}');">Edit</button>
														</c:otherwise>
													</c:choose>
												</td>
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
					
					
					
					
				</div>
			</div>
