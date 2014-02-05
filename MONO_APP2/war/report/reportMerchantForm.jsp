<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Merchant <small>Statistics</small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Report</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Merchant Statistics</a>
						</li>
						
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN STATISTIC PORTLET-->
			<div class="row">
				<div class="col-md-12">
					
					
					<div class="portlet box purple">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Merchant Statistics
							</div>
							<div class="actions">
								
								<a href="javascript:merchantChart($('#chartId').val(),'AMOUNT');" class="btn yellow btn-sm"><i class="fa fa-bar-chart-o"></i> AMOUNT</a>
								<a href="javascript:merchantChart($('#chartId').val(),'COUNT');" class="btn green btn-sm"><i class="fa fa-bar-chart-o"></i> COUNT</a>
								<a href="javascript:merchantChart($('#chartId').val(),'RATE');" class="btn purple btn-sm"><i class="fa fa-bar-chart-o"></i> RATE</a>
								
								<div class="btn-group">
									<select class="form-control select2me input-small" name="chartId" id="chartId">
										<option value="">-----&nbsp;</option>
										<c:forEach var="list" items="${chartList}" varStatus="status">
										<option value="${list.temp1String }">${list.temp1String }</option>
										</c:forEach>
									</select>
								</div>
								
							</div>
						</div>
						<div class="portlet-body" id="merchantChart" style="height: 218px;">
					
						</div>
					</div>
					
					
				</div>
			</div>
			<!-- END STATISTIC PORTLET-->
			
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN SEARCH FORM TABLE-->
					<div class="portlet box green ">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-search"></i> Search Form
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal" role="form" data-form="true" id="searchForm" name="searchForm" action="/report.do" method="post">
								<div class="form-body">
									<div class="form-group">
										<label class="col-sm-2 control-label">Merchant ID</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm merchantId" data-provide="typeahead" name="merchantId" id="merchantId" placeholder="Merchant ID" >
										</div>
										<label class="col-sm-2 control-label">Period</label>
										<div class="col-sm-2">
											<div class="input-group date-picker input-daterange" data-date-format="yyyymmdd">
												<input type="text" class="form-control input-sm" name="startDate">
												<span class="input-group-addon">~</span>
												<input type="text" class="form-control input-sm" name="endDate" value="${endDate}">
											</div>
										</div>
										<label class="col-sm-2 control-label">Page Count</label>
										<div class="col-sm-2">
											<select class="form-control select2me input-sm" name="pageSize">
												<option value="50">50</option>
					                			<option value="100">100</option>
					                			<option value="200">200</option>
					                			<option value="500">500</option>
											</select>
											<input id="request" type="hidden" name="request" value="reportMerchant" >
											<input id="format" type="hidden" name="format" value="" >
										</div>
									</div>
								</div>
								<div class="form-actions nobg right">
									<div class="">
										
										<button type="reset" class="btn dark btn-sm"><i class="fa fa-eraser"></i>&nbsp;Reset&nbsp;&nbsp;</button>
										<button type="button" class="btn purple btn-sm loading-btn" data-loading-text="Loading..." onclick="setChart();formSend('list');" ><i class="fa fa-search"></i>&nbsp;Submit</button>
									</div>
								</div>
								<script>
									function setChart(){
										var id = $('#merchantId').val();
										if(id != ''){
											merchantChart(id,'AMOUNT');
											$("select[name=chartId]").val(id);
										}
									}
								</script>
							</form>
						</div>
					</div>
					<!-- END SEARCH FORM TABLE-->

					<!-- BEGIN SEARCH RESULT FORM-->
					<div class="portlet box blue" id="searchResult">
					</div>
					<!-- END SEARCH RESULT FORM-->
					
				</div>
			</div>
