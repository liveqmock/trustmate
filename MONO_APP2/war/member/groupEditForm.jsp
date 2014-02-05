<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-sm-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Group <small>Registration</small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Member</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Group Edit Properties</a>
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
								<i class="fa fa-reorder"></i> Group Edit Properties
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
												<label class="control-label col-sm-4">Group ID</span>
												</label>
												<div class="col-sm-6">
													<p class="form-control-static2">${gBean.groupId }&nbsp;</p>
													<input name="request" type="hidden" value="gedit">
													<input name="pwUpdate" type="hidden" value="${gBean.pwUpdate }" >
													<input name="idx" type="hidden" value="${gBean.idx }" >
													<input name="groupId" type="hidden" value="${gBean.groupId }" id="groupId">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Group Name<span class="required">*</span>
												</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="100" data-required="1" name="name" value="${gBean.name}">
												</div>
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Product<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="100" name="product" value="${gBean.product}">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Homepage<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" value="${gBean.homepage }" maxlength="100" name="homepage" >
												</div>
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-12">
											<div class="form-group">
												<label class="control-label col-sm-2">ZipCode &amp; Address<span class="required">*</span></label>
												<div class="col-sm-2">
														<input type="text" class="form-control " maxlength="12" value="${gBean.zipCode }" name="zipCode" placeHolder="zipcode">
												</div>
												<div class="col-sm-4">
														<input type="text" class="form-control " maxlength="100" value="${gBean.addr1 }" name="addr1" placeHolder="detail" >
												</div>
												<div class="col-sm-4">
														<input type="text" class="form-control " maxlength="100" value="${gBean.addr2 }" name="addr2" placeHolder="city &amp; country" >
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
													<input type="text" class="form-control" numberHypen maxlength="20" name="telNo" value="${gBean.telNo }" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Fax No</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="20" name="fax" value="${gBean.fax }" >
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
													<input type="email" class="form-control" maxlength="100" name="email" value="${gBean.email }" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Registration No.</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="20" name="identiNo" value="${gBean.identiNo }" >
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
													<input type="text" class="form-control" maxlength="100" name="settleBank" value="${gBean.settleBank }" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">BankCode<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="12" name="settleBankCode" value="${gBean.settleBankCode }" >
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
													<input type="text" class="form-control" maxlength="20" name="settleAccount" value="${gBean.settleAccount }" >
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
													<input type="text" class="form-control" maxlength="50" name="ceoName" value="${gBean.ceoName}" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">CEO NAME(English)<span class="required">*</span>
												</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="50" name="ceoEngName" value="${gBean.ceoEngName }"  >
												</div>
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-12">
											<div class="form-group">
												<label class="control-label col-sm-2">CEO Address<span class="required">*</span></label>
												<div class="col-sm-2">
														<input type="text" class="form-control " value="${gBean.ceoZipCode }" name="ceoZipCode" placeHolder="zipcode">
												</div>
												<div class="col-sm-4">
														<input type="text" class="form-control " maxlength="100" value="${gBean.ceoAddr1 }" name="ceoAddr1" placeHolder="detail" >
												</div>
												<div class="col-sm-4">
														<input type="text" class="form-control " maxlength="100" value="${gBean.ceoAddr2 }" name="ceoAddr2" placeHolder="city &amp; country" >
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Tel No.<span class="required">*</span></label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="20" name="ceoTelNo" value="${gBean.ceoTelNo }" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Phone No.</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="20" name="ceoPhoneNo" value="${gBean.ceoPhoneNo }" >
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
													<input type="email" class="form-control" maxlength="100" name="ceoEMail" value="${gBean.ceoEMail }" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Iden No.</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" maxlength="20" name="ceoIdentiNo" value="${gBean.ceoIdentiNo }" >
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
													<input type="text" class="form-control" maxlength="20" name="settlePartName" value="${gBean.settlePartName }" >
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">E-MAIL</label>
												<div class="col-sm-6">
													<input type="email" class="form-control" maxlength="100" name="settlePartEMail" value="${gBean.settlePartEMail }" >
												</div>
											</div>
										</div>
									</div>
									
									
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label class="control-label col-sm-4">Tel No.</label>
												<div class="col-sm-6">
													<input type="text" class="form-control" numberHypen maxlength="20" name="settlePartTelNo" value="${gBean.settlePartTelNo }"  >
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
										<c:if test="${sso.memberRole == '0001' }">
										<a class="btn green btn-sm" href="javascript:getPageContent('/member/groupForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
										</c:if>
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
				       
						$("select[name=active]").val("${gBean.active}");
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
			                    ajaxFormSubmit("/member.do?request=gview&groupId="+$("#groupId").val());	//PAGE 이동
			                }
			            });
				
					</script>
					<!-- END FORM JAVASCRIPT -->
				</div>
			</div>
