<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<!-- BEGIN SIDEBAR -->
	<div class="page-sidebar-wrapper">
		<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->
			<c:if test="${sso.memberRole == '0001'}">
				<ul class="page-sidebar-menu">
					<li class="sidebar-toggler-wrapper">
						<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
						<div class="sidebar-toggler hidden-phone">
						</div>
						<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					</li>
					<li class="sidebar-search-wrapper">
						<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
						<form class="sidebar-search">
							<div class="form-container">
								
								<div class="input-box">
									<input type="text" name="searchId" numberOnly id="searchId" placeholder="TransactionId"/>
									<input type="button" class="submit" value=" "/>
								</div>
								<div id="search-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1"></div>
							</div>
						</form>
						<!-- END RESPONSIVE QUICK SEARCH FORM -->
					</li>
					<li class="active">
						<a href="javascript:;">
							<i class="fa fa-home"></i>
							<span class="title">DashBoard</span><span class="selected"></span><span class="arrow open"></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/main.do">DashBoard</a>
							</li>						
						</ul>					
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-th-list"></i>
							<span class="title">Transaction</span><span class="selected"></span><span class="arrow open"></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/trnsctn/trnsctnForm.jsp">Transaction</a>
							</li>
							<li>
								<a class="ajaxify" href="/trnsctn/refundForm.jsp">Refund</a>
							</li>
							<li>
								<a class="ajaxify" href="/trnsctn/authForm.jsp">Authentication</a>
							</li>
							<li>
								<a class="ajaxify" href="/trnsctn/cbForm.jsp">ChargeBack</a>
							</li>
							<li>
								<a class="ajaxify" href="/report/acquireForm.jsp">Acquiring</a>
							</li>
							<li>
								<a class="ajaxify" href="/trnsctn/logForm.jsp">Transaction Log</a>
							</li>
							<li>
								<a class="ajaxify" href="/payment/payForm.jsp">Test Payment</a>
							</li>
						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-user"></i>
							<span class="title">MEMBER</span><span class="selected"></span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/member/memberForm.jsp">Admin</a>
							</li>
							<li>
								<a class="ajaxify" href="/member/groupForm.jsp">Group</a>
							</li>
							<li>
								<a class="ajaxify" href="/member/agentForm.jsp">Agent</a>
							</li>
							<li>
								<a class="ajaxify" href="/member/merchantForm.jsp">Merchant</a>
							</li>
							
						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-bar-chart-o"></i>
							<span class="title">REPORT</span><span class="selected"></span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/report/paidForm.jsp">Settlement</a>
							</li>
							<li>
								<a class="ajaxify" href="/report/calendarForm.jsp">Calendar</a>
							</li>
							<li>
								<a class="ajaxify" href="/report/depositForm.jsp">Deposit</a>
							</li>
							<li>
								<a class="ajaxify" href="/report.do?request=reportMerchantForm">Merchants Statistics</a>
							</li>
							<li>
								<a class="ajaxify" href="/report/reportMonthlyForm.jsp">Monthly Statistics</a>
							</li>
							<li>
								<a class="ajaxify" href="/report/approvalRateForm.jsp">Approval Rate</a>
							</li>
							<li>
								<a class="ajaxify" href="/report/exportForm.jsp">Export Data</a>
							</li>
						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-cogs"></i>
							<span class="title">CONTROL	</span><span class="selected"></span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/control/riskForm.jsp">Risk</a>
							</li>
							<li>
								<a class="ajaxify" href="/control/alertForm.jsp">System Alert</a>
							</li>
							<li>
								<a class="ajaxify" href="/control/vanForm.jsp">VAN </a>
							</li>
							<li>
								<a class="ajaxify" href="/control/accessForm.jsp">Admin Access </a>
							</li>
						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-exclamation"></i>
							<span class="title">NOTICE</span><span class="selected"></span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/notice.do?request=notice">Notice</a>
							</li>
						</ul>
					</li>
				</ul>
			</c:if>
			<c:if test="${sso.memberRole == '0002'}">
				<ul class="page-sidebar-menu">
					<li class="sidebar-toggler-wrapper">
						<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
						<div class="sidebar-toggler hidden-phone">
						</div>
						<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					</li>
					<li class="sidebar-search-wrapper">
						<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
						<form class="sidebar-search">
							<div class="form-container">
								<div class="input-box">
									<input type="text" name="searchId" numberOnly id="searchId" placeholder="TransactionId"/>
									<input type="button" class="submit" value=" "/>
								</div>
								<div id="search-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1"></div>
							</div>
						</form>
						<!-- END RESPONSIVE QUICK SEARCH FORM -->
					</li>
					<li class="active">
						<a href="javascript:;">
							<i class="fa fa-home"></i>
							<span class="title">DashBoard</span><span class="selected"></span><span class="arrow open"></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/merchant/main.do">DashBoard</a>
							</li>						
						</ul>					
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-th-list"></i>
							<span class="title">Transaction</span><span class="selected"></span><span class="arrow open"></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/merchant/trnsctn/trnsctnForm.jsp">Transaction</a>
							</li>
							<li>
								<a class="ajaxify" href="/merchant/trnsctn/authForm.jsp">Authentication</a>
							</li>
							<li>
								<a class="ajaxify" href="/merchant/trnsctn/cbForm.jsp">ChargeBack</a>
							</li>
							<li>
								<a class="ajaxify" href="/merchant/trnsctn/acquireForm.jsp">Acquiring</a>
							</li>
						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-bar-chart-o"></i>
							<span class="title">REPORT</span><span class="selected"></span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/merchant/report/invoiceForm.jsp">Invoice</a>
							</li>
						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-user"></i>
							<span class="title">MERCHANT</span><span class="selected"></span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/merchant/info.do?request=info">My Profile</a>
							</li>
							
						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-file-text"></i>
							<span class="title">DOCUMENT</span><span class="selected"></span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/notice.do?request=notice">Document</a>
							</li>
						</ul>
					</li>
				</ul>
			</c:if>
			<c:if test="${sso.memberRole == '0003'}">
				<ul class="page-sidebar-menu">
					<li class="sidebar-toggler-wrapper">
						<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
						<div class="sidebar-toggler hidden-phone">
						</div>
						<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					</li>
					<li class="sidebar-search-wrapper">
						<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
						<form class="sidebar-search">
							<div class="form-container">
								
								<div class="input-box">
									<input type="text" name="searchId" numberOnly id="searchId" placeholder="TransactionId"/>
									<input type="button" class="submit" value=" "/>
								</div>
								<div id="search-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1"></div>
							</div>
						</form>
						<!-- END RESPONSIVE QUICK SEARCH FORM -->
					</li>
					<li class="active">
						<a href="javascript:;">
							<i class="fa fa-home"></i>
							<span class="title">DashBoard</span><span class="selected"></span><span class="arrow open"></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/group/main.do">DashBoard</a>
							</li>						
						</ul>					
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-th-list"></i>
							<span class="title">Transaction</span><span class="selected"></span><span class="arrow open"></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/trnsctn/trnsctnForm.jsp">Transaction</a>
							</li>
							<li>
								<a class="ajaxify" href="/trnsctn/refundForm.jsp">Refund</a>
							</li>
							<li>
								<a class="ajaxify" href="/trnsctn/authForm.jsp">Authentication</a>
							</li>
							<li>
								<a class="ajaxify" href="/trnsctn/cbForm.jsp">ChargeBack</a>
							</li>
							<li>
								<a class="ajaxify" href="/report/acquireForm.jsp">Acquiring</a>
							</li>
							<li>
								<a class="ajaxify" href="/trnsctn/logForm.jsp">Transaction Log</a>
							</li>
						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-user"></i>
							<span class="title">MEMBER</span><span class="selected"></span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/member/merchantForm.jsp">Merchant</a>
							</li>
							<li>
								<a class="ajaxify" href="/member.do?request=gview">My Profile</a>
							</li>
						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-bar-chart-o"></i>
							<span class="title">REPORT</span><span class="selected"></span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/report/paidForm.jsp">Settlement</a>
							</li>
							<li>
								<a class="ajaxify" href="/report/depositForm.jsp">Deposit</a>
							</li>
							<li>
								<a class="ajaxify" href="/report.do?request=reportMerchantForm">Merchants Statistics</a>
							</li>
						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-exclamation"></i>
							<span class="title">NOTICE</span><span class="selected"></span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/notice.do?request=notice">Notice</a>
							</li>
						</ul>
					</li>
				</ul>
			</c:if>
			<c:if test="${sso.memberRole == '0004'}">
				<ul class="page-sidebar-menu">
					<li class="sidebar-toggler-wrapper">
						<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
						<div class="sidebar-toggler hidden-phone">
						</div>
						<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					</li>
					<li class="sidebar-search-wrapper">
						<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
						<form class="sidebar-search">
							<div class="form-container">
								<div class="input-box">
									<input type="text" name="searchId" numberOnly id="searchId" placeholder="TransactionId"/>
									<input type="button" class="submit" value=" "/>
								</div>
								<div id="search-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1"></div>
							</div>
						</form>
						<!-- END RESPONSIVE QUICK SEARCH FORM -->
					</li>
					<li class="active">
						<a href="javascript:;">
							<i class="fa fa-home"></i>
							<span class="title">DashBoard</span><span class="selected"></span><span class="arrow open"></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/agent/main.do">DashBoard</a>
							</li>						
						</ul>					
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-th-list"></i>
							<span class="title">Transaction</span><span class="selected"></span><span class="arrow open"></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/agent/trnsctn/trnsctnForm.jsp">Transaction</a>
							</li>
							<li>
								<a class="ajaxify" href="/agent/trnsctn/authForm.jsp">Authentication</a>
							</li>
							<li>
								<a class="ajaxify" href="/agent/trnsctn/cbForm.jsp">ChargeBack</a>
							</li>
							<li>
								<a class="ajaxify" href="/agent/trnsctn/acquireForm.jsp">Acquiring</a>
							</li>
						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-bar-chart-o"></i>
							<span class="title">REPORT</span><span class="selected"></span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/agent/report/invoiceForm.jsp">Invoice</a>
							</li>
						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-user"></i>
							<span class="title">MERCHANT</span><span class="selected"></span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a class="ajaxify" href="/agent/info/merchantForm.jsp">Merchant</a>
							</li>
							<li>
								<a class="ajaxify" href="/agent/info.do?request=info">My Profile</a>
							</li>
							
						</ul>
					</li>
				</ul>
			</c:if>
			<!-- END SIDEBAR MENU -->
		</div>
	</div>
	<!-- END SIDEBAR -->