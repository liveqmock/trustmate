<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<h4 class="modal-title">Change ${param.id}'s Password &nbsp; <small class="error"></small></h4>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet-body form">
						<form class="form-horizontal form-row-seperated" role="form" data-form="true" id="modalForm" name="modalForm" action="/member.do" method="post">
							<div class="form-body">
								<div class="form-group">
									<label class="control-label col-md-4">Change Password<span class="required">*</span>
									</label>
									<div class="col-sm-6">
										<input type="password" class="form-control" maxlength="20" name="password" id="password">
										<input name="request" type="hidden" value="psUpdate" >
										<input name="role" type="hidden" value="${param.role }" >
										<input name="id" type="hidden" value="${param.id }" >
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-4">Confirm Password<span class="required">*</span>
									</label>
									<div class="col-sm-6">
										<input type="password" class="form-control" maxlength="20" name="password2" id="password">
									</div>
								</div>
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									You have some form errors. Please check above.
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
		    <button type="button" class="btn blue btn-sm update">Password Update</button>
			<button type="button" class="btn default" data-dismiss="modal">Close</button>
		</div>
	</div>
	<!-- /.modal-content -->
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
        
        var form1 = $('#modalForm');
           var error1 = $('.alert-danger', form1);

           form1.validate({
               errorElement: 'span', //default input error message container
               errorClass: 'help-inline', // default input error message class
               focusInvalid: true, // do not focus the last invalid input
               ignore: "",
               rules: {
                   password: {
                   	required: true,
                       minlength: 5,
                       required: true
                   },
                   password2: {
					required: true,
					minlength: 5,
					equalTo: "#password"
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
               }
           });
				
					
	
	</script>
<!-- /.modal-dialog -->