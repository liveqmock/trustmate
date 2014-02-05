<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Transaction Data <small>${param.request }</small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/group/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Transaction</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Transaction ${param.request }</a>
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
							<form class="form-horizontal" role="form" data-form="true" id="searchForm" name="searchForm" action="/group/trnsctn.do" method="post">
								<div class="form-body">
									<div class="form-group">
										<label class="col-sm-2 control-label">Transaction ID</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="transactionId" placeholder="Transaction ID">
										</div>
										<label class="col-sm-2 control-label">Order No.</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="payNo" placeholder="Order No.">
										</div>
										<label class="col-sm-2 control-label">Approv No.</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="approvalNo" placeholder="Approv No." >
										</div>
										<label class="col-sm-2 control-label">E-Mail</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="payEmail" placeholder="E-Mail" >
										</div>
										<label class="col-sm-2 control-label">Card No.</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="temp1String" placeholder="Card No.">
										</div>
										<label class="col-sm-2 control-label">Status</label>
										<div class="col-sm-2">
											<select class="form-control select2me input-sm" name="trnStatus">
												<option value="">-----&nbsp;</option>
												<option value="'00','01','02','03','04','05','06'">Approved</option>
												<option value="'07','08','09','10','18','19','20','21'">Acquired</option>
												<option value="'02','07','08','09','11','12','13','10'">Valid Transaction</option>
												<option value="'11','12','13'">Settlement</option>
												<option value="'06','14','18'">Refunded</option>
												<option value="'17'">Request Cancel</option>
												<option value="'15'">Request Refund</option>
												<option value="'22','23'">Chargeback</option>
												
											</select>
										
										</div>
										
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">Merchant ID</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="merchantId" placeholder="Merchant ID">
										</div>
										<label class="col-sm-2 control-label">Period</label>
										<div class="col-sm-2">
											<div class="input-group date-picker input-daterange" data-date-format="yyyymmdd">
												<input type="text" class="form-control input-sm" name="startDate" value="${startDate}">
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
											<input id="request" type="hidden" name="request" value="list" >
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
