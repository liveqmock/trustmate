<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>PGMATE Admin</title>
		<meta http-equiv="popContent-Type" popContent="text/html;charset=utf-8" />
		<!-- stylesheets -->
		<link rel="stylesheet" type="text/css" href="/resources/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="/resources/css/pop_style.css" media="screen" />
		<link id="color" rel="stylesheet" type="text/css" href="/resources/css/colors/blue.css" />
		<!-- scripts (jquery) -->
		<script src="/resources/scripts/jquery-1.6.4.min.js" type="text/javascript"></script>
		<!--[if IE]><script language="javascript" type="text/javascript" src="/resources/scripts/excanvas.min.js"></script><![endif]-->
		<script src="/resources/scripts/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
		<script src="/resources/scripts/jquery.ui.selectmenu.js" type="text/javascript"></script>
		<script src="/resources/scripts/jquery.flot.min.js" type="text/javascript"></script>
		<script src="/resources/scripts/tiny_mce/tiny_mce.js" type="text/javascript"></script>
		<script src="/resources/scripts/tiny_mce/jquery.tinymce.js" type="text/javascript"></script>
		<!-- scripts (custom)-->
		<script src="/resources/scripts/smooth.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.menu.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.table.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.form.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.dialog.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.autocomplete.js" type="text/javascript"></script>
		<script src="/common/js/common.js" type="text/javascript"></script>
		<script type="text/javascript">
		
			function pwUpdate() {	
				frm = document.passwordModify;
				
				if(frm.uppassword.value == ""){
					alert("변경할 패스워드를 입력하세요");
					frm.uppassword.focus();
				}else{
					if(frm.checkpassword.value == ""){
						alert("변경할 패스워드를 입력하세요(check)");
						frm.checkpassword.focus();
					}else{
						if(frm.uppassword.value == frm.checkpassword.value){
							opener.location.href="/member.do?request=modify&uppassword="+frm.uppassword.value+"&merchantId="+frm.merchantId.value;
							//alert("OK");
							self.close();
						}else{
							alert("변경할 패스워드가 일치하지 않습니다. 다시 입력하여 주시기 바랍니다.");
							frm.checkpassword.value = "";
							frm.checkpassword.focus();
						}
					}
				}
				
			}
		
		</script>
	</head>
	
	<body>
		<div id="content">
			<div class="box">
				<!-- box / title -->
				<div class="title">
					<h5>Change Password</h5>
				</div>
				<form name="passwordModify" method="post">
				<div class="form">
				<input type="hidden" name="merchantId" value="${merchantBean.merchantId }" />
        			<div class="fields">
        				<div class="field">
							<div class="label">
								<label for="password">New PASSWORD</label>
							</div>
							<div class="input" style="padding-left: 50px">
								<input name="uppassword" id="uppassword" type="password" class="text200" maxlength="20" value="" />
							</div>
						</div>
						<div class="field">
							<div class="label">
								<label for="checkpassword">New PASSWORD(Check)</label>
							</div>
							<div class="input" style="padding-left: 50px">
								<input name="checkpassword" id="checkpassword" type="password" class="text200" maxlength="20" value="" />
							</div>
						</div>
					</div>
					<div class="action">
						<div class="button"><!--추가 -->
							<input type="submit" name="submit" value="CONFIRM" onclick="javascript:pwUpdate();return false;" />
						</div>
					</div>
				</div>
				</form>
			</div>
		</div>
	</body>
</html>