<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>PGMATE:login</title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<!-- stylesheets -->
		<link rel="stylesheet" type="text/css" href="/resources/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="/resources/css/style.css" media="screen" />
		<link id="color" rel="stylesheet" type="text/css" href="/resources/css/colors/blue.css" />
		<!-- scripts (jquery) -->
		<script src="/resources/scripts/jquery-1.6.4.min.js" type="text/javascript"></script>
		<script src="/resources/scripts/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(document).ready(function () {
				style_path = "/resources/css/colors";

				$("input.focus").focus(function () {
					if (this.value == this.defaultValue) {
						this.value = "";
					}
					else {
						this.select();
					}
				});

				$("input.focus").blur(function () {
					if ($.trim(this.value) == "") {
						this.value = (this.defaultValue ? this.defaultValue : "");
					}
				});

				$("input:submit, input:reset").button();
			});
		</script>
	<script>
	<!--
	
	window.onload = function() {
		document.ssoForm.memberId.focus();
	}
	
	 
	function sendit() {
		if (document.ssoForm.memberId.value=="") {
			alert("You can't leave username empty");
			document.ssoForm.memberId.focus();
			return false;
		}
			
		str = document.ssoForm.memberPassword.value;
		if (str=="") {
			alert("You can't leave password empty");
			document.ssoForm.memberPassword.focus();
			return false;
		}
	
		return true;
	}
	//-->
  </script>
	</head>
	<body>
		<div id="login">
			<!-- login -->
			<div class="title">
				<h5>Sign In to PGMATE Admin</h5>
				<div class="corner tl"></div>
				<div class="corner tr"></div>
			</div>
			<c:if test="${!empty message}">
			<div class="messages">
				<div id="message-error" class="message message-error">
					<div class="image">
						<img src="resources/images/icons/error.png" alt="Error" height="32" />
					</div>
					<div class="text">
						<h6>Error Message</h6>
						<span>${message }</span>
					</div>
					<div class="dismiss">
						<a href="#message-error"></a>
					</div>
				</div>
			</div>
			</c:if>
			<!-- /error message alert -->
			<div class="inner">
				<form name="ssoForm" action="/authentication.do" method=post onsubmit="return sendit();">
				<div class="form">
					<!-- fields -->
					<div class="fields">
						<div class="field">
							<div class="label">
								<label for="username">Username:</label>
							</div>
							<div class="input">
								<input type="text" id="username" name="memberId" size="40" value="" class="focus" placeholder="username"/>
							</div>
						</div>
						<div class="field">
							<div class="label">
								<label for="password">Password:</label>
							</div>
							<div class="input">
								<input type="password" id="password" name="memberPassword" size="40" value="" class="focus" placeholder="password"/>
								<input name="errorURL" type="hidden" value="/sso/websso.jsp">
							</div>
						</div>
						<!-- 
						<div class="field">
							<div class="checkbox">
								<input type="checkbox" id="remember" name="remember" />
								<label for="remember">Remember me</label>
							</div>
						</div>
						 -->
						<div class="buttons">
							<input type="submit" value="Sign In" />
						</div>
					</div>
					<!-- end fields -->
					<!-- links -->
					<div class="links">
						<!-- >a href="index.html">Forgot your password?</a-->
					</div>
					<!-- end links -->
				</div>
				</form>
			</div>
			
		</div>
	</body>
</html>

