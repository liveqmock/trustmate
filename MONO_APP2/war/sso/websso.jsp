<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${!empty sso}">
	<c:redirect url="/main.jsp"/>
</c:if>



<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>TRS</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
<link href="/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
<link href="/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="/assets/css/pages/login-soft.css" rel="stylesheet" type="text/css"/>
<link href="/assets/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="login">
<!-- BEGIN LOGO -->
<div class="logo">
	<img src="/assets/img/logo-big.png" alt=""/>
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">
	<!-- BEGIN LOGIN FORM -->
	<form class="login-form" action="/authentication.do" method="post">
		<h3 class="form-title">Login to your account</h3>
		<div class="alert alert-danger display-hide">
			<button class="close" data-close="alert"></button>
			<span>
				 Enter any MemberId and password.
			</span>
		</div>
		<div class="form-group">
			<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			<label class="control-label visible-ie8 visible-ie9">Username</label>
			<div class="input-icon">
				<i class="fa fa-user"></i>
				<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Member Id" name="memberId" id="memberId" value=""/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">Password</label>
			<div class="input-icon">
				<i class="fa fa-lock"></i>
				<input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" name="memberPassword" id="memberPassword" value=""/>
			</div>
		</div>
		<div class="form-actions">
			<label class="checkbox">
			<input type="checkbox" name="remember" id="remember" /> Remember me </label>
			<button type="submit" class="btn blue pull-right">
			Login <i class="m-icon-swapright m-icon-white"></i>
			</button>
		</div>
	
		
	</form>
	<!-- END LOGIN FORM -->
</div>
<!-- END LOGIN -->
<!-- BEGIN COPYRIGHT -->
<div class="copyright">
	 2009 &copy; PANWORLD PAYMENT
</div>
<!-- END COPYRIGHT -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
	<script src="/assets/plugins/respond.min.js" type="text/javascript"></script>
	<script src="/assets/plugins/excanvas.min.js" type="text/javascript"></script> 
<![endif]-->
<script src="/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<script src="/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="/assets/plugins/bootstrap/js/bootstrap-typeahead.js" type="text/javascript"></script>
<script type="text/javascript" src="/assets/plugins/select2/select2.min.js"></script>
<script src="/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="/assets/plugins/jquery-validation/dist/jquery.validate.min.js" type="text/javascript"></script>
<script src="/assets/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
<script src="/assets/plugins/select2/select2.min.js" type="text/javascript" ></script>
<script src="/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript" ></script>
<script src="/assets/plugins/printThis.js" type="text/javascript" ></script>
<script src="/assets/plugins/stupidtable.min.js" type="text/javascript" ></script>
<!-- END PAGE LEVEL PLUGINS -->
<script src="/assets/scripts/app.js"></script>
<script>

	var id = $.cookie('memberId');
	if(id == '' || id == 'null' || typeof id ==  'undefined'){
		$('#remember').attr("checked", false); 
		$('#memberId').focus();
	}else{
	  	$('#remember').attr("checked", true); 
	  	$('#memberId').val(id);
	  	$('#memberPassword').focus();
	}
	
</script>
<script>
		jQuery(document).ready(function() {     
		  App.init();
		  Login.init();
		});
		
		
		var Login = function () {
			var handleLogin = function() {
				$('.login-form').validate({
			            errorElement: 'span', //default input error message container
			            errorClass: 'help-block', // default input error message class
			            focusInvalid: false, // do not focus the last invalid input
			            rules: {
			                memberId: {
			                    required: true
			                },
			                memberPassword: {
			                    required: true
			                },
			            },
			            messages: {
			                memberId: {
			                    required: "MemberId is required."
			                },
			                memberPassword: {
			                    required: "Password is required."
			                }
			            },
		
			            invalidHandler: function (event, validator) { //display error alert on form submit   
			                $('.alert-danger', $('.login-form')).show();
			            },
		
			            highlight: function (element) { // hightlight error inputs
			                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
			            },
		
			            success: function (label) {
			                label.closest('.form-group').removeClass('has-error');
			                label.remove();
			            },
		
			            errorPlacement: function (error, element) {
			                error.insertAfter(element.closest('.input-icon'));
			            },
		
			            submitHandler: function (form) {
			            	if ($("#remember").is(":checked")) {
			                	$.cookie('memberId',$('#memberId').val() , { expires: 14, path: '/'});
			                }else{
			                	$.cookie('memberId', null);
			                }
			                form.submit();
			            }
			        });
		
			        $('.login-form input').keypress(function (e) {
			            if (e.which == 13) {
			                if ($('.login-form').validate().form()) {
			                	if ($("#remember").is(":checked")) {
			                		$.cookie('memberId',$('#memberId').val() , { expires: 14, path: '/' });
			                	}else{
			                		$.cookie('memberId', null);
			                	}
			                	$('.login-form').submit();
			                }
			                return false;
			            }
			        });
			}
		    return {
		        init: function () {
		        	
		            handleLogin();   
			       	$.backstretch([
				        "/assets/img/bg/1.jpg",
				        "/assets/img/bg/2.jpg",
				        "/assets/img/bg/3.jpg"
				        ], {
				          fade: 1000,
				          duration: 3000
				    });
		        }
		    };
		}();
		
	</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
