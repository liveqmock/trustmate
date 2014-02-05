<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<c:if test="${sso.memberRole == '0001'}">
						<h3 class="page-title">Notice <small>List</small></h3>
						<ul class="page-breadcrumb breadcrumb">
							<li>
								<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
							</li>
							<li>
								<a href="#">Notice</a><i class="fa fa-angle-right"></i>
							</li>
							<li>
								<a href="javascript:getReloadPage();">Notice List</a>
							</li>
							<li class="btn-group">
								<button type="button" class="btn blue" onclick="getPageContent('/notice/noticeAdd.jsp');">
								<span>REGISTRATION</span>&nbsp;<i class="fa fa-edit"></i>
								</button>
							</li>
							
						</ul>
					</c:if>
					<c:if test="${sso.memberRole == '0002'}">
						<h3 class="page-title">Document <small>List</small></h3>
						<ul class="page-breadcrumb breadcrumb">
							<li>
								<i class="fa fa-home"></i><a href="/merchant/main.jsp">Home</a><i class="fa fa-angle-right"></i>
							</li>
							<li>
								<a href="#">Document</a><i class="fa fa-angle-right"></i>
							</li>
							<li>
								<a href="javascript:getReloadPage();">Document List</a>
							</li>
							
						</ul>
					</c:if>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->

			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-2">
					<ul class="ver-inline-menu tabbable margin-bottom-10">
						<li class="active">
							<a data-toggle="tab" href="#tab_1">
							<i class="fa fa-briefcase"></i> Manual</a>
							<span class="after">
							</span>
						</li>
						<li>
							<a data-toggle="tab" href="#tab_2">
							<i class="fa fa-briefcase"></i> General Questions </a>
							<span class="after">
							</span>
						</li>
					</ul>
				</div>
				<div class="col-md-10">
					<div class="tab-content">
						<div id="tab_1" class="tab-pane active">
							<div id="accordion1" class="panel-group">
								<c:choose>
								<c:when test="${fn:length(manualList) != 0}">
								<c:forEach var="list" items="${manualList}" varStatus="status">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
										<a  href="${list.location }" target="_blank">
											${status.count}.&nbsp;${list.title }
										</a>
										</h4>
									</div>
									
								</div>
								</c:forEach>
								</c:when>
								</c:choose>
							</div>
						</div>
						<div id="tab_2" class="tab-pane ">
							<div id="accordion2" class="panel-group">
								<c:choose>
								<c:when test="${fn:length(noticeList) != 0}">
								<c:forEach var="list" items="${noticeList}" varStatus="status">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
										<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#accordion2_${status.count}">
											${list.subject }
										</a>
										</h4>
									</div>
									<div id="#accordion2_${status.count}" class="panel-collapse collapse in">
										<div class="panel-body">
											 ${list.contents }
										</div>
									</div>
								</div>
								</c:forEach>
								</c:when>
								</c:choose>
							</div>
						</div>
						
					</div>
				</div>
				
			</div>
			<!-- END PAGE CONTENT-->
