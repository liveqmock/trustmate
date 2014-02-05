<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-sm-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Member <small>Information</small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Member</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Member Information</a>
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
					<div class="portlet box blue ">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i> View Member Info
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
												<label class="control-label col-sm-4">Member ID
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.memberId }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Password
												</label>
												<div class="col-sm-8">
													<a class="btn green btn-sm right" href="javascript:changePassword('MEMBER','${mBean.memberId }');">Change Password</a>
												</div>
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Member Name
												</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.name }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">E-Mail</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.officeEmail }</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Telephone No.</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.phoneNo }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Grade</label>
												<div class="col-sm-8">
													<p class="form-control-static2">${mBean.memberGrade }등급</p>
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Status</label>
												<div class="col-sm-8">
													<c:if test="${mBean.active =='1' }">
														<p class="form-control-static2">ACTIVE</p>
													</c:if>
													<c:if test="${mBean.active =='2' }">
														<p class="form-control-static2">INACTIVE</p>
													</c:if>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Dept &amp; Notice</label>
												<div class="col-sm-8">
														<p class="form-control-static2">${mBean.notice }</p>
												</div>
											</div>
										</div>
									</div>
								</div>	
								
								<div class="form-actions right">
									<div class="">
										<a class="btn dark btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member/memberForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
										<button type="button" class="btn purple btn-sm loading-btn" data-loading-text="Loading..." onclick="getPageContent('/member.do?request=mEditForm&memberId=${mBean.memberId}');"><i class="fa fa-edit"></i>&nbsp;Edit</button>
									</div>
								</div>
								
							</form>
						</div>
					</div>
					

				</div>
				<div id="pgmate-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1"></div>
			</div>
