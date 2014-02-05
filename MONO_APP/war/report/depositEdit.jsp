<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>HANA Admin</title>
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
		
			function send() {	
				frm = document.editForm;
				
				if(frm.payMonth.value == ""){
					alert("변경할 년월을 입력하세요");
					frm.payMonth.focus();
				}else{
					if(frm.commnets.value == ""){
						alert("변경 사유를 입력하여 주세요");
						frm.commnets.focus();
					}else{
						opener.location.href="/report.do?request=depositEdit&idx="+frm.idx.value+"&payMonth="+frm.payMonth.value+"&commnets="+frm.commnets.value;
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
					<h5>지급 월 변경</h5>
				</div>
				<form name="editForm" method="post">
				<div class="form">
				<input type="hidden" name="idx" value="${mdBean.idx }" />
        			<div class="fields">
        				<div class="field">
							<div class="label">
								<label for="input">정산번호</label>
							</div>
							<div class="input" style="padding-left: 50px">
								${mdBean.settleId }
							</div>
						</div>
						<div class="field">
							<div class="label">
								<label for="input">누적 금액</label>
							</div>
							<div class="input" style="padding-left: 50px">
								<fmt:formatNumber type="number" value="${mdBean.lastAmt}" pattern="#,##0.00" />
							</div>
						</div>
						<div class="field">
							<div class="label">
								<label for="input">차감 금액</label>
							</div>
							<div class="input" style="padding-left: 50px">
								<fmt:formatNumber type="number" value="${mdBean.curAmt}" pattern="#,##0.00" />
							</div>
						</div>
						<div class="field">
							<div class="label">
								<label for="input">지급 금액</label>
							</div>
							<div class="input" style="padding-left: 50px">
								<fmt:formatNumber type="number" value="${mdBean.payAmt}" pattern="#,##0.00" />
							</div>
						</div>
						<div class="field">
							<div class="label">
								<label for="input">변경 월</label>
							</div>
							<div class="input" style="padding-left: 50px">
								<input name="payMonth" id="payMonth" type="text" class="text200" maxlength="6" value="${mdBean.payMonth }" />
							</div>
						</div>
						<div class="field">
							<div class="label">
								<label for="input">Comments</label>
							</div>
							<div class="input" style="padding-left: 50px">
								<textarea id="textarea" name="comments" cols="32" rows="4"></textarea>
							</div>
						</div>
					</div>
					<div class="action">
						<div class="button"><!--추가 -->
							<input type="submit" name="submit" value="CONFIRM" onclick="javascript:send();" />&nbsp;
							<input type="submit" name="submit" value="Close" onclick="javascript:self.close();" alt="close" id="close"/>
						</div>
					</div>
				</div>
				</form>
			</div>
		</div>
	</body>
</html>