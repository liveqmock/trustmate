<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Settlement <small>List</small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Report</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Settlement List</a>
						</li>
						
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->

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
											<input type="text" class="form-control input-sm merchantId" data-provide="typeahead" name="merchantId" placeholder="Merchant ID" >
										</div>
										<label class="col-sm-2 control-label">Settle ID</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="settleId" placeholder="Settle ID">
										</div>
										<label class="col-sm-2 control-label">Week</label>
										<div class="col-sm-2">
											<select class="form-control select2me input-sm" name="settleCnt">
												<option value=""></option>
												<option value="1">1</option>
					                			<option value="2">2</option>
					                			<option value="3">3</option>
					                			<option value="4">4</option>
					                			<option value="5">5</option>
											</select>
										</div>
										
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"></label>
										<div class="col-sm-2">
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
											<input id="request" type="hidden" name="request" value="plist" >
											<input id="format" type="hidden" name="format" value="" >
										</div>
									</div>
								</div>
								<div class="form-actions nobg right">
									<div class="">
										
										<button type="reset" class="btn dark btn-sm"><i class="fa fa-eraser"></i>&nbsp;Reset&nbsp;&nbsp;</button>
										<button type="button" class="btn purple btn-sm loading-btn" data-loading-text="Loading..." onclick="formSend('list');" ><i class="fa fa-search"></i>&nbsp;Submit</button>
									</div>
								</div>
								
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
