<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<title>PGMATE</title>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
	<meta content="PGMATE" name="description"/>
	<meta content="TrustMate" name="author"/>
	<meta name="MobileOptimized" content="640">
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	<link href="/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<link href="/assets/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css"/>
	<link href="/assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES -->
	<link href="/assets/plugins/select2/select2_metro.css" type="text/css" rel="stylesheet"  />
	<link href="/assets/plugins/data-tables/DT_bootstrap.css" type="text/css" rel="stylesheet" />
	<!-- END PAGE LEVEL STYLES -->
	<!-- BEGIN THEME STYLES -->
	<link href="/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
	<link href="/assets/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
	<link href="/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="/assets/css/custom.css" rel="stylesheet" type="text/css"/>
	<!-- END THEME STYLES -->
	
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
<!-- BEGIN HEADER -->
<div class="header navbar navbar-inverse navbar-fixed-top">
	<!-- BEGIN TOP NAVIGATION BAR -->
	<div class="header-inner">
		<!-- BEGIN LOGO -->
		<c:if test="${sso.memberRole =='0001' }">
		<a class="navbar-brand" href="javascript:getPageContent('/main.do');"><img src="/assets/img/logo-big.png" alt="TRUSTMATE" class="img-responsive"/></a>
		</c:if>
		<c:if test="${sso.memberRole =='0002' }">
		<a class="navbar-brand" href="javascript:getPageContent('/merchant/main.do');"><img src="/assets/img/logo-big.png" alt="TRUSTMATE" class="img-responsive"/></a>
		</c:if>
		<c:if test="${sso.memberRole =='0003' }">
		<a class="navbar-brand" href="javascript:getPageContent('/group/main.do');"><img src="/assets/img/logo-big.png" alt="TRUSTMATE" class="img-responsive"/></a>
		</c:if>
		<c:if test="${sso.memberRole =='0004' }">
		<a class="navbar-brand" href="javascript:getPageContent('/agent/main.do');"><img src="/assets/img/logo-big.png" alt="TRUSTMATE" class="img-responsive"/></a>
		</c:if>
		<!-- END LOGO -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
		<img src="/assets/img/menu-toggler.png" alt=""/>
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		<!-- BEGIN TOP NAVIGATION MENU -->
		<ul class="nav navbar-nav pull-right">
			<!-- BEGIN NOTIFICATION DROPDOWN -->
			<c:if test="${sso.pwUpdate =='N' }">
			<li class="dropdown" id="header_notification_bar">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
				<i class="fa fa-warning"></i>
				<span class="badge">
					1
				</span>
				</a>
				<ul class="dropdown-menu extended notification">
					<li>
						<p>
							You have 1 new notifications
						</p>
					</li>
					<li>
						<ul class="dropdown-menu-list scroller" style="height: 100px;">
							<li>
								<a href="#">
								<span class="label label-icon label-success">
									<i class="fa fa-plus"></i>
								</span>
								 Change your password!
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</li>
			</c:if>
			<!-- END NOTIFICATION DROPDOWN -->
			<!-- BEGIN USER LOGIN DROPDOWN -->
			<li class="dropdown user">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
				
				<span class="username">
					${sso.memberName}
				</span>
				<i class="fa fa-angle-down"></i>
				</a>
				<ul class="dropdown-menu">
					<li>
						<c:if test="${sso.memberRole =='0001' }">
						<a href="javascript:getPageContent('/member.do?request=mview&memberId=${sso.memberId }');"><i class="fa fa-user"></i> My Profile</a>
						<a href="#" class="keepSession"><i class="fa fa-lock"></i> Keep &amp Reload</a>
						<a href="javascript:setSideBar();"><i class="fa fa-cogs"></i> SideBar Reverse</a>
						<a href="javascript:setSideBarFix();"><i class="fa fa-cogs"></i> SideBar Fix</a>
						
						</c:if>
						<c:if test="${sso.memberRole =='0002' }">
						<a href="javascript:getPageContent('/merchant/info.do?request=info');"><i class="fa fa-user"></i> My Profile</a>
						</c:if>
						<c:if test="${sso.memberRole =='0003' }">
						<a href="javascript:getPageContent('/member.do?request=gview');"><i class="fa fa-user"></i> My Profile</a>
						</c:if>
						
					</li>
					<li class="divider">
					</li>
					<li>
						<a href="/ssoout.do"><i class="fa fa-key"></i> Log Out</a>
					</li>
				</ul>
			</li>
			<!-- END USER LOGIN DROPDOWN -->
		</ul>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->
<div class="clearfix">
</div>

