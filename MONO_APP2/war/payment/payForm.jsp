<%@page contentType="text/html; charset=UTF-8" %>
<%@ page import="biz.trustnet.common.util.CommonUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-sm-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Payment <small>Test</small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Transaction</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Payment Test</a>
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
								<i class="fa fa-reorder"></i> PAYMENT FORM
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal form-bordered form-row-stripped" id="payment" role="form" data-form="true"  name="payment" action="/adminPayment.do" method="post">
								<div class="form-body">
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4" for="inputSuccess">MERCHANT ID<span class="required">*</span>
												</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="20" name="merchantId" id="merchantId" value="panworld">
													<input type="hidden" name="mallId" value="panworld"/>
													<input type="hidden" name="TRN_TYPE" value="T001"/>
													<input type="hidden" name="trnType" value="T001"/>
													<input type="hidden" name="productType" value="1"/>
													<input type="hidden" name="specType" value="CFIX"/>
													<input type="hidden" name="serviceType" value="WEB"/>
													<input type="hidden" name="curType" value="USD"/>
													<input type="hidden" name="ipAddress" value="<%=request.getRemoteAddr() %>"/>
													<input type="hidden" name="domain" value="http://service.pgmate.com"/>
													<input type="hidden" name="trnDate" value="<%=CommonUtil.getCurrentDate("yyyyMMddHHmmss")%>"/>
													<input type="hidden" name="foreName" value="Juseop">
													<input type="hidden" name="surName" value="Lim">
													<input type="hidden" name="foreName" value="Juseop">
													<input type="hidden" name="addr1" value="304 Soungnae-dong">
													<input type="hidden" name="addr2" value="GangDong-Gu">
													<input type="hidden" name="city" value="Seoul">
													<input type="hidden" name="state" value="Seoul">
													<input type="hidden" name="country" value="KR">
													<input type="hidden" name="zip" value="43410">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">PRODUCT_NAME<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="50" placeholder="" name="productName" value="TEST PRODUCT">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">PAY_NO<span class="required">*</span></label>
												<div class="col-sm-6">
													<input name="payNo" type="text" id="password" class="form-control" maxlength="50" value="<%=CommonUtil.getCurrentDate("yyyyMMddHHmmss")%>" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">PAY_EMAIL<span class="required">*</span></label>
												<div class="col-sm-6">
													<input name="payEmail" type="text" class="form-control" maxlength="50" value="ginaida@trustmate.net" >
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4" for="inputSuccess">PAY_USERID<span class="required">*</span>
												</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="20" data-required="1" name="payUserId" value="trustmate" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">PAY_NAME<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" name="payName" maxlength="50" class="form-control" value="trustmate" >
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">PAY_TELNO<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="50" placeholder="" name="payTelNo" value="0234463730">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">AMOUNT<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="50" placeholder="" name="amount" value="110">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">PIN NUMBER<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="16" placeholder="" name="cardNumber" value="4762552207159237">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">EXPIRE(YYMM)<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="50" placeholder="" name="cardExpire" value="1606">
												</div>
											</div>
										</div>
									</div>
								</div>	
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									You have some form errors. Please check above.
								</div>
								<div class="form-actions right">
									<div class="">
										<button type="reset" class="btn dark btn-sm"><i class="fa fa-eraser"></i>&nbsp;Reset&nbsp;&nbsp;</button>
										<button type="submit" class="btn purple btn-sm loading-btn" data-loading-text="Loading..." ><i class="fa fa-search"></i>&nbsp;Submit</button>
									</div>
								</div>
								
							</form>
						</div>
					</div>
					<!-- END ADD FORM TABLE-->
					<!-- BEGIN FORM JAVASCRIPT -->

					<script>
				       
				       
				        
				        var form1 = $('#payment');
			            var error1 = $('.alert-danger', form1);
			
			            form1.validate({
			                errorElement: 'span', //default input error message container
			                errorClass: 'help-inline', // default input error message class
			                focusInvalid: true, // do not focus the last invalid input
			                ignore: "",
			                rules: {
			                    merchantId: {
			                        minlength: 5,
			                        required: true,
			                    },
			                    productName: {
			                        required: true
			                    },
			                    payNo: {
			                        required: true
			                    },
			                    payEmail: {
									required: true
								},
			                    amount: {
			                        required: true
			                    },
			                    cardNumber: {
			                        required: true,
			                        minlength: 14
			                    },
			                    cardExpire: {
			                        required: true,
			                        minlength: 4
			                    }
			                },
			
			                invalidHandler: function (event, validator) { //display error alert on form submit              
			                    error1.show();
			                    App.scrollTo(error1, -200);
			                },
			                highlight: function (element) { // hightlight error inputs
			                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
			                },
			                unhighlight: function (element) { // revert the change done by hightlight
			                    $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
			                },
			                success: function (label) {
			                	label.closest('.form-group').removeClass('has-error'); 
			                    label.closest('.form-group').addClass('has-success'); 
			                },
			                submitHandler: function (form) {
			                    error1.hide();
			                    ajaxPaymentSubmit();	//PAGE 이동
			                }
			            });
				
					</script>
					<!-- END FORM JAVASCRIPT -->
				</div>
			</div>
			
			<div class="portlet box blue" id="searchResult">
			</div>
