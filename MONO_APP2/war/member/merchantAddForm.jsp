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
							<a href="javascript:getReloadPage();">Merchant Registration</a>
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
								<i class="fa fa-reorder"></i> Registration Form
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal" role="form" data-form="true" id="form" name="form" action="/member.do" method="post">
							
								
								<div class="form-body">
									<div class="row">
										<div class="col-sm-12 ">
										<h4 class="form-section2">1. Basic information</h4>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Merchant ID<span class="required">*</span>
												</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="20" name="merchantId" id="merchantId">
													<input name="request" type="hidden" value="add" >
													<input name="pwUpdate" type="hidden" value="N" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">
												</label>
												<div class="col-sm-6">
													
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Password<span class="required">*</span></label>
												<div class="col-sm-6">
													<input name="password" type="password" id="password" class="form-control" maxlength="20" placeholder="password" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Password<span class="required">*</span></label>
												<div class="col-sm-6">
													<input name="password2" type="password" class="form-control" maxlength="20" placeholder="confirm password" >
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Merchant Name<span class="required">*</span>
												</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="100" data-required="1" name="name" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Product<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="100" name="product" >
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Homepage<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" value="http://" maxlength="100" name="homepage" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">&nbsp;</label>
												<div class="col-sm-6">
													<p class="form-control-static2">&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<div class="form-group">
												<label class="control-label col-sm-2">ZipCode &amp; Address<span class="required">*</span></label>
												<div class="col-sm-2">
														<input type="text" class="form-control " maxlength="12" value="" name="zipCode" placeHolder="zipcode">
												</div>
												<div class="col-sm-4">
														<input type="text" class="form-control " maxlength="100" value="" name="addr1" placeHolder="detail" >
												</div>
												<div class="col-sm-4">
														<input type="text" class="form-control " maxlength="100" value="" name="addr2" placeHolder="city &amp; country" >
												</div>
											</div>
										</div>
										
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Tel No<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="20" name="telNo" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Fax No</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="20" name="fax" >
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">E-Mail<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="email" class="form-control" maxlength="100" name="email" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Registration No.</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="20" name="identiNo" >
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Bank<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="100" name="settleBank" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">BankCode<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="12" name="settleBankCode" >
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Bank Account<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="20" name="settleAccount" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Status<span class="required">*</span></label>
												<div class="col-sm-6">
													<select name="active" class="select2me form-control ">
														<option value="0" selected>READY</option>
														<option value="1">ACTIVE</option>
														<option value="2">INACTIVE</option>
														<option value="3">TERMINATED</option>
													</select>
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Service Date<span class="required">*</span></label>
												<div class="col-sm-6">
													<div class="input-group date-picker input-daterange" data-date-format="yyyymmdd">
														<input type="text" class="form-control input-sm" maxlength="8" name="serviceDates" value="${startDate}">
													</div>
													
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">&nbsp;</label>
												<div class="col-sm-6">&nbsp;
												</div>
											</div>
										</div>
									</div>
								</div>	
								
								
								<div class="form-body">
									<div class="row">
										<div class="col-sm-12 ">
										<h4 class="form-section2">2. CEO information</h4>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">CEO NAME<span class="required">*</span>
												</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="50" name="ceoName" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">CEO NAME(English)<span class="required">*</span>
												</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="50" name="ceoEngName" >
												</div>
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-12">
											<div class="form-group">
												<label class="control-label col-sm-2">CEO Address<span class="required">*</span></label>
												<div class="col-sm-2">
														<input type="text" class="form-control " maxlength="12" value="" name="ceoZipCode" placeHolder="zipcode">
												</div>
												<div class="col-sm-4">
														<input type="text" class="form-control " maxlength="100" value="" name="ceoAddr1" placeHolder="detail" >
												</div>
												<div class="col-sm-4">
														<input type="text" class="form-control " maxlength="100" value="" name="ceoAddr2" placeHolder="city &amp; country" >
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Tel No.<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="20" name="ceoTelNo" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Phone No.</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="20" name="ceoPhoneNo" >
												</div>
											</div>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">E-MAIL<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="email" class="form-control" maxlength="100" name="ceoEMail" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Iden No.</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="20" name="ceoIdentiNo" >
												</div>
											</div>
										</div>
									</div>
									
								</div>	
								
								
								<div class="form-body">
									<div class="row">
										<div class="col-sm-12 ">
										<h4 class="form-section2">3. Settlement Dept </h4>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4"> NAME
												</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="20" name="settlePartName" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">E-MAIL</label>
												<div class="col-sm-6">
													<input type="email" class="form-control" maxlength="100" name="settlePartEMail" >
												</div>
											</div>
										</div>
									</div>
									
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Tel No.</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="20" name="settlePartTelNo" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">&nbsp;</label>
												<div class="col-sm-6">
													<p class="form-control-static2">&nbsp;</p>
												</div>
											</div>
										</div>
									</div>
									
									
								</div>	
								<div class="form-body">
									<div class="row">
										<div class="col-sm-12 ">
										<h4 class="form-section2">4. Development Dept </h4>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4"> NAME
												</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="20" name="devPartName" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">E-MAIL</label>
												
												<div class="col-sm-6">
													<input type="email" class="form-control" maxlength="100" name="devPartEMail" >
												</div>
											</div>
										</div>
									</div>
									
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Tel No.</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="20" name="devPartTelNo" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">&nbsp;</label>
												<div class="col-sm-6">
													<p class="form-control-static2">&nbsp;</p>
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
			                    merchantId: {
			                        minlength: 5,
			                        required: true,
			                        remote:{
						                url: "/ajax.do?request=checkMerchantId", //make sure to return true or false with a 200 status code
						                type: "post",
						                data: {
						                    merchantId: function() {
						                        return $("#merchantId").val();
						                    }
						                }
						            }
			                    },
			                    password: {
			                    	required: true,
			                        minlength: 5,
			                        required: true
			                    },
			                    password2: {
									required: true,
									minlength: 5,
									equalTo: "#password"
								},
								name: {
			                        required: true,
			                        minlength: 5
			                    },
			                    homepage: {
			                        required: true,
			                        minlength: 7,
			                        
			                    },
			                    product: {
			                        required: true,
			                        minlength: 5
			                    },
			                    addr1: {
			                        required: true,
			                        minlength: 5
			                    },
			                    addr2: {
			                        required: true,
			                        minlength: 5
			                    },
			                    telNo: {
			                    	minlength: 10,
			                        required: true
			                    },
			                    email: {
			                    	minlength: 5,
			                        required: true,
			                        email: true
			                    },
			                    settleBank: {
			                    	minlength: 3,
			                        required: true
			                    },
			                    settleBankCode: {
			                    	minlength: 3,
			                        required: true
			                    },
			                    settleAccount: {
			                    	minlength: 5,
			                        required: true
			                    },
			                    serviceDates: {
			                    	minlength: 8,
			                        required: true,
			                        number: true
			                    },
			                    ceoName: {
			                    	minlength: 3,
			                        required: true
			                    },
			                    ceoEngName: {
			                    	minlength: 5,
			                        required: true
			                    },
			                    ceoTelNo: {
			                    	minlength: 5,
			                        required: true
			                    },
			                    ceoEMail: {
			                    	minlength: 5,
			                        required: true,
			                        email : true
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
			                    ajaxFormSubmit("/member/merchantServiceAddForm.jsp?&merchantId="+$("#merchantId").val());	//PAGE 이동
			                }
			            });
				
					</script>
					<!-- END FORM JAVASCRIPT -->
				</div>
			</div>
