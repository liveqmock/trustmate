<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>PGMS Admin</title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<!-- stylesheets -->
		<link rel="stylesheet" type="text/css" href="/resources/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="/resources/css/style.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="/resources/css/colors/blue.css" id="color" />
		<!-- scripts (jquery) -->
		<script src="/resources/scripts/jquery-1.6.4.min.js" type="text/javascript"></script>
		<!--[if IE]><script language="javascript" type="text/javascript" src="resources/scripts/excanvas.min.js"></script><![endif]-->
		<script src="/resources/scripts/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
		<script src="/resources/scripts/jquery.ui.selectmenu.js" type="text/javascript"></script>
		<script src="/resources/scripts/jquery.flot.min.js" type="text/javascript"></script>
		<script src="/resources/scripts/tiny_mce/tiny_mce.js" type="text/javascript"></script>
		<script src="/resources/scripts/tiny_mce/jquery.tinymce.js" type="text/javascript"></script>
		<!-- scripts (custom) -->
		<!-- >script src="/resources/scripts/smooth.chart.js" type="text/javascript"></script-->
		<!-- >script src="/resources/scripts/smooth.autocomplete.js" type="text/javascript"></script -->
		<!-- >script src="/resources/scripts/smooth.dialog.js" type="text/javascript"></script-->
		<script src="/resources/scripts/smooth.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.menu.js" type="text/javascript"></script>		
		<script src="/resources/scripts/smooth.table.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.form.js" type="text/javascript"></script>
		<script src="/common/js/common.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			$(document).ready(function () {
				style_path = "/resources/css/colors";
				$("#date-picker").datepicker();
				$("#box-tabs, #box-left-tabs").tabs();
			});
		
		</script>
		
	</head>
	<body onBeforeUnload="showloading(true);" onload="showloading(false);" >
	<div id="loading" style="visibility:hidden;"> <div class="loading-indicator">Loading...</div> </div>
	<!-- header -->		
	<div id="header">
		
		<!-- logo -->
		<div id="logo">
			<h1><a href="/merchant/main" title="PGMS Admin"><img src="${customizingBean.topLogo}" alt="PGMS" /></a></h1>
		</div>
		<!-- end logo -->
		<!-- user -->
		<ul id="user">
		
		 	<li class="first"><a href="<c:url value="/ssoout.do"/>"><font color="#233D91">&nbsp;&nbsp;${sso.memberName}&nbsp;</font></a></li>
			<li class="highlight last"><a href="<c:url value="/ssoout.do"/>">Logout</a></li>				
		</ul>
		<!-- end user -->
		<div id="header-inner">
			<div id="home">
				<a href="/merchant/main" title="Home"></a>
			</div>


			<!-- quick -->
			<ul id="quick">
				<li>
					<a href="#" title="transaction"><span class="normal">TRANSACTION</span></a>
					<ul>
						<li><a href="/merchant/trnsctn.do?request=listForm">Transaction</a></li>
						<li><a href="/merchant/trnsctn.do?request=authForm">Authentication</a></li>
					</ul>
				</li>
				<li>
					<a href="#" title="report"><span class="normal">REPORT</span></a>
					<ul>
						<li><a href="/merchant/report.do?request=acquireForm">Collect</a></li>
						<%-- <li><a href="/merchant/report.do?request=settlementForm">Paid</a></li> --%>
						<li><a href="/merchant/report.do?request=invoiceForm">Invoice</a></li>
						<li class="last"><a href="/merchant/report.do?request=cblistForm">ChargeBack</a></li>
					</ul>
				</li>
				<li>
					<a href="#" title="member"><span class="normal">MERCHANT</span></a>
					<ul>
						<li class="last"><a href="/merchant/info.do?request=info">Information</a></li>
						<li class="last"><a href="/merchant/info.do?request=notice">Notice</a></li>
						<li class="last"><a href="/merchant/info.do?request=question">Question</a></li>
					</ul>
				</li>
				<li>
					<a href="#" title="document"><span class="normal">DOCUMENT</span></a>
					<ul>
						<li class="last"><a href="/merchant/document.do?request=manual">Document</a></li>
					</ul>
				</li>

								
			</ul>
			<!-- end quick -->
			<div class="corner tl"></div>
			<div class="corner tr"></div>
		</div>
	</div>
	<!-- end header -->
