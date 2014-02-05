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
							<i class="fa fa-home"></i><a href="/group/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Merchant</a><i class="fa fa-angle-right"></i>
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
										<h4 class="form-section2">Information</h4>
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
												<label class="control-label col-sm-4"></label>
												<div class="col-sm-8">
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
