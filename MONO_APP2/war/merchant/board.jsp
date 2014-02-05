<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">DashBoard <small>statistics and more </small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getPageContent('/merchant/main.do')">DashBoard</a></i>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->

			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN STATISTIC PORTLET-->
					<div class="portlet box purple">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Monthly Transaction Statistics
							</div>
							<div class="actions">
								<a href="javascript:monthlyChart('AMOUNT');" class="btn yellow btn-sm"><i class="fa fa-bar-chart-o"></i> AMOUNT</a>
								<a href="javascript:monthlyChart('COUNT');" class="btn green btn-sm"><i class="fa fa-bar-chart-o"></i> COUNT</a>
								<a href="javascript:monthlyChart('RATE');" class="btn blue btn-sm"><i class="fa fa-bar-chart-o"></i> RATE</a>
							</div>
						</div>
						<div class="portlet-body">
							<div  id="monthlyChart" style="height:200px" >
								
							</div>
						</div>
					</div>
					<!-- END STATISTIC PORTLET-->
				</div>	
			</div>
			
			
			<div class="row">
				<div class="col-md-6 ">
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-bell-o"></i>Today Transaction
							</div>
						</div>
						<div class="portlet-body">
							<div class="scroller" style="height: 200px;" data-always-visible="1" data-rail-visible="0">
								<ul class="feeds">
									<c:forEach var="list" items="${recentTrnsctn}" varStatus="status">
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-info">
														<i class="fa fa-check"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														 ${list.transactionId}&nbsp;${list.merchantId}&nbsp;<fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" />
														 
														<span class="label label-sm label-warning ">
															 ${list.resultMsg }:${list.temp1String } <i class="fa fa-share"></i>
														</span>
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												 <fmt:formatDate type="both" value="${list.trnReqDate}" pattern="HH:mm:ss" />
											</div>
										</div>
									</li>
									</c:forEach>
								</ul>
							</div>
						</div>	
					</div>
				</div>
				<div class="col-md-6">
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-bell-o"></i>Recent Error
							</div>
						</div>
						<div class="portlet-body">
							<div class="scroller" style="height: 200px;" data-always-visible="1" data-rail-visible="0">
								<ul class="feeds">
									<c:forEach var="list" items="${recentErrorTrnsctn}" varStatus="status">
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-info">
														<i class="fa fa-check"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														 ${list.transactionId}&nbsp;${list.merchantId}&nbsp;<fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" />
														 
														<span class="label label-sm label-warning ">
															 ${list.resultMsg }:${list.temp1String } <i class="fa fa-share"></i>
														</span>
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												 <fmt:formatDate type="both" value="${list.trnReqDate}" pattern="HH:mm:ss" />
											</div>
										</div>
									</li>
									</c:forEach>
								</ul>
							</div>
							
						</div>
					</div>
				</div>
				
				
			</div>
			
			<script>
				jQuery(document).ready(function() { 
				   monthlyChart('AMOUNT');
				});
			</script>
