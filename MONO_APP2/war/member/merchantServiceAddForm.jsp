<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-sm-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Merchant <small>Registration</small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Member</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Merchant Service Registration</a>
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
								<i class="fa fa-reorder"></i> Merchant Service Registration Form
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal" role="form" data-form="true" id="form" name="form" action="/member.do" method="post">
								
								
								<div class="form-body">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Merchant ID
												</label>
												<div class="col-sm-6">
													<p class="form-control-static2">${param.merchantId }</p>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Currency
												</label>
												<div class="col-sm-6">
													<select name="curType" class="select2me form-control ">
														<option value="USD" selected>USD</option>
														<option value="JPY">JPY</option>
														<option value="EUR">EUR</option>
														<option value="CHY">CHY</option>
														<option value="KRW">KRW</option>
													</select>
													<input name="request" type="hidden" value="serviceAdd" >
													<input name="merchantId" type="hidden" value="${param.merchantId}"  id="merchantId">
												
												</div>
											</div>
										</div>
										
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Ticket Size
												</label>
												<div class="col-sm-6">
													<input type="number" class="form-control" name="onceLimit" value="1000">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Daily Limit
												</label>
												<div class="col-sm-6">
													<input type="number" class="form-control" name="dayLimit" value="100000">
												</div>
											</div>
										</div>
										
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Monthly Limit  
												</label>
												<div class="col-sm-6">
													<input type="number" class="form-control" name="monthLimit" value="1000000">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Monthly Limit of Card
												</label>
												<div class="col-sm-6">
													<input type="number" class="form-control" name="cardMonthLimit" value="10000">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Limit number of Card
												</label>
												<div class="col-sm-6">
													<input type="number" class="form-control" name="duplicationCount" value="5">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Monthly Limit number of Card 
												</label>
												<div class="col-sm-6">
													<input type="number" class="form-control" name="tempLimit" value="10" >
												</div>
											</div>
										</div>
										
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">VAN
												</label>
												<div class="col-sm-6">
													<select name="van" class="select2me form-control ">
														<option value="KCP" selected>KCP</option>
														<option value="INICIS">INICIS</option>
														<option value="KSNET">KSNET</option>
														<option value="DCP">DCP</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">VAN ID
												</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="20" name="vanId" >
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Authentication Service
												</label>
												<div class="col-sm-6">
													<select name="auth" class="select2me form-control ">
														<option value="N" selected>INACTIVE</option>
														<option value="Y">ACTIVE</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Report Email
												</label>
												<div class="col-sm-6">
													<input type="email" class="form-control" name="reportEMail" >
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Decision Manager
												</label>
												<div class="col-sm-6">
													<select name="dm" class="select2me form-control ">
														<option value="N" selected>INACTIVE</option>
														<option value="Y">ACTIVE</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Transaction Type
												</label>
												<div class="col-sm-6">
													<select name="demo" class="select2me form-control ">
														<option value="N" selected>SERVICE MODE</option>
														<option value="Y">TEST MODE</option>
													</select>
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
										<a class="btn green btn-sm" href="javascript:getPageContent('/member/merchantForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
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
				       
				        
				        $(':input[maxlength]' ).maxlength({
				            alwaysShow: true,
				            warningClass: "label label-success",
				            limitReachedClass: "label label-danger",
				            separator: ' out of ',
				            preText: 'You typed ',
				            postText: ' chars available.',
				            validate: true
				        });
				        
				        var form1 = $('#form');
			            var error1 = $('.alert-danger', form1);
			
			            form1.validate({
			                errorElement: 'span', //default input error message container
			                errorClass: 'help-inline', // default input error message class
			                focusInvalid: true, // do not focus the last invalid input
			                ignore: "",
			                rules: {
			                    
								onceLimit: {
			                        required: true,
			                        number : true
			                    },
			                    dayLimit: {
			                        required: true,
			                        number : true
			                    },
			                    monthLimit: {
			                        required: true,
			                        number : true
			                    },
			                    tempLimit: {
			                        required: true,
			                        number : true
			                    },
			                    cardMonthLimit: {
			                        required: true,
			                        number : true
			                    },
			                    duplicationCount: {
			                        required: true,
			                        number : true
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
			                    ajaxFormSubmit('/bill/billAddForm.jsp?&merchantId=${param.merchantId }');	//PAGE 이동
			                }
			            });
				
					</script>
					<!-- END FORM JAVASCRIPT -->
				</div>
			</div>
