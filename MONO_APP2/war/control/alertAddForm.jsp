<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-sm-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Alert Unit <small>Registration</small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Control</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Alert Unit Registration</a>
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
							<form class="form-horizontal form-bordered form-row-stripped" role="form" data-form="true" id="form" name="form" action="/manage.do" method="post">
								<div class="form-body">
									
									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label col-sm-4" for="inputSuccess">TYPE<span class="required">*</span>
												</label>
												<div class="col-sm-4">
													<select name="alertType" class="select2me form-control">
														<option value="ALL">ALL</option>
													    <option value="SMS">SMS</option>
													    <option value="EMAIL">EMAIL</option>
													</select>
													<input name="request" type="hidden" value="alertAdd" >
												</div>
											</div>
										</div>
										<div class="col-sm-8">
											<div class="form-group">
												<label class="control-label col-sm-2">Code<span class="required">*</span></label>
												<div class="col-sm-4">
													<input type="text" class="form-control" maxlength="4" name="resultMsg" id="resultMsg">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label col-sm-4" for="inputSuccess">TARGET<span class="required">*</span>
												</label>
												<div class="col-sm-4">
													<select name="target" class="select2me form-control">
														<option value="ALL">ALL</option>
													    <option value="SYSTEM">SYSTEM</option>
													    <option value="ADMIN">ADMIN</option>
													    <option value="NONE">NONE</option>
													</select>
													
												</div>
											</div>
										</div>
										<div class="col-sm-8">
											<div class="form-group">
												<label class="control-label col-sm-2">Message<span class="required">*</span></label>
												<div class="col-sm-8">
													<input type="text" class="form-control" maxlength="100" name="message">
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
										<a class="btn green btn-sm" href="javascript:getPageContent('/control/alertForm.jsp');"><i class="fa fa-reorder"></i>&nbsp;List Page&nbsp;&nbsp;</a>
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
			                    alertType: {
			                        required: true
			                    },
			                    code: {
			                    	minlength: 4,
			                        required: true
			                    },
			                    target: {
			                    	required: true,
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
			                    ajaxFormSubmit('/control/alertForm.jsp');	//PAGE 이동
			                }
			            });
				
					</script>
					<!-- END FORM JAVASCRIPT -->
				</div>
			</div>
