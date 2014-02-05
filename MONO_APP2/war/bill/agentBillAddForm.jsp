<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-sm-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Agent Bill <small>Registration</small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Member</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Agent Bill Registration</a>
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
								<i class="fa fa-reorder"></i> Agent Bill Registration Form
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal" role="form" data-form="true" id="form" name="form" action="/bill.do" method="post">
								
								
								<div class="form-body">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Agent ID
												</label>
												<div class="col-sm-6">
													<p class="form-control-static2">${param.agentId }</p>
													<input name="request" type="hidden" value="agentBillAdd" >
													<input name="agentId" type="hidden" value="${param.agentId}"  id="agentId">
													<input name="merchantId" type="hidden" value="${param.merchantId}"  id="merchantId">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Merchant ID
												</label>
												<div class="col-sm-5">
													<p class="form-control-static2">${param.merchantId }</p>
												</div>
											</div>
										</div>
										
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">V/M MDR<span class="required">*</span>
												</label>
												<div class="col-sm-5">
													<input type="text" class="form-control text-right" name="visamaster" value="" placeholder="0.065">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">J/A MDR<span class="required">*</span>
												</label>
												<div class="col-sm-5">
													<input type="number" class="form-control text-right" name="jcbamex" value="" placeholder="0.065">
												</div>
											</div>
										</div>
										
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Transaction Fee<span class="required">*</span>
												</label>
												<div class="col-sm-5">
													<input type="number" class="form-control text-right" name="transaction" value="0">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Refund Fee<span class="required">*</span>
												</label>
												<div class="col-sm-5">
													<input type="number" class="form-control text-right" name="refund" value="0">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">ChargeBack Fee<span class="required">*</span>
												</label>
												<div class="col-sm-5">
													<input type="number" class="form-control text-right" name="chargeback" value="0">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Annual Fee<span class="required">*</span>
												</label>
												<div class="col-sm-5">
													<input type="number" class="form-control text-right" name="management" value="0" >
												</div>
											</div>
										</div>
										
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Wire Fee<span class="required">*</span>
												</label>
												<div class="col-sm-5">
													<input type="number" class="form-control text-right" name="bankTransfer" value="0">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Setup Fee<span class="required">*</span>
												</label>
												<div class="col-sm-5">
													<input type="number" class="form-control text-right" name="setupFee" value="0" >
												</div>
											</div>
										</div>
										
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Value Added Tax <span class="required">*</span>
												</label>
												<div class="col-sm-5">
													<input type="number" class="form-control text-right" name="vat" value="0">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Settlement Status<span class="required">*</span>
												</label>
												<div class="col-sm-5">
													<select name="active" class="select2me form-control ">
														<option value="Y" selected>ACTIVE</option>
														<option value="N">INACTIVE</option>
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
										<a class="btn green btn-sm" href="javascript:getPageContent('/member/agentForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
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
								visamaster: {
			                        required: true,
			                        number : true,
			                        max : 0.2,
			                        min : 0
			                    },
			                    jcbamex: {
			                        required: true,
			                        number : true,
			                        max : 0.2,
			                        min : 0
			                    },
			                    transaction: {
			                        required: true,
			                        number : true,
			                        max : 1,
			                        min : 0
			                    },
			                    refund: {
			                        required: true,
			                        number : true,
			                        max : 10,
			                        min : 0
			                    },
			                    chargeback: {
			                        required: true,
			                        number : true,
			                        max : 100,
			                        min : 0
			                    },
			                    management: {
			                        required: true,
			                        number : true,
			                        max : 5000,
			                        min : 0
			                    },
			                    bankTransfer: {
			                        required: true,
			                        number : true,
			                        max : 200,
			                        min : 0
			                    },
			                    vat: {
			                        required: true,
			                        number : true,
			                        max : 0.2,
			                        min : 0
			                    },
			                    setupFee: {
			                        required: true,
			                        number : true,
			                        max : 5000,
			                        min : 0
			                    },
			                    active: {
			                        required: true
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
			                    ajaxFormSubmit('/member.do?request=aview&agentId=${param.agentId}');	//PAGE 이동
			                }
			            });
				
					</script>
					<!-- END FORM JAVASCRIPT -->
				</div>
			</div>
