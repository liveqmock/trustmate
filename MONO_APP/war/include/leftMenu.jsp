<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



				<div id="menu">
					<h6 id="h-menu-trnsctn" <c:if test="${fn:indexOf(pageUri, '/trnsctn.do') == 0}"> class="selected" </c:if>><a href="#trnsctn"><span>TRANSACTION</span></a></h6>
					<ul id="menu-trnsctn" <c:if test="${fn:indexOf(pageUri, '/trnsctn.do') == -1}"> class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/trnsctn.do?request=listForm') > -1}"> class="selected" </c:if>><a href="/trnsctn.do?request=listForm">Transaction(JPY)</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/trnsctn.do?request=listFormKrw') > -1}"> class="selected" </c:if>><a href="/trnsctn.do?request=listFormKrw">Transaction(KRW)</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/trnsctn.do?request=listFormUsd') > -1}"> class="selected" </c:if>><a href="/trnsctn.do?request=listFormUsd">Transaction(USD)</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/trnsctn.do?request=listFormEur') > -1}"> class="selected" </c:if>><a href="/trnsctn.do?request=listFormEur">Transaction(EUR)</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/trnsctn.do?request=authForm') > -1}"> class="selected" </c:if>><a href="/trnsctn.do?request=authForm">Authentication</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/trnsctn.do?request=logForm') > -1}"> class="selected" </c:if>><a href="/trnsctn.do?request=logForm">Log</a></li>
					</ul>
					<h6 id="h-menu-report" <c:if test="${fn:indexOf(pageUri, '/report.do') == 0 || fn:indexOf(pageUri, '/bill.do') == 0}">  class="selected" </c:if>><a href="#report"><span>REPORT</span></a></h6>
					<ul id="menu-report" <c:if test="${fn:indexOf(pageUri, '/report.do') == -1 && fn:indexOf(pageUri, '/bill.do') == -1}">  class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/report.do?request=acquireForm') > -1}"> class="selected" </c:if> ><a href="/report.do?request=acquireForm">Collect</a></li>
						<%--<li <c:if test="${fn:indexOf(pageUri, '/report.do?request=settlementForm') > -1}"> class="selected" </c:if> ><a href="/report.do?request=settlementForm">Paid()</a></li> 
						<li <c:if test="${fn:indexOf(pageUri, '/report.do?request=invoiceForm') > -1}"> class="selected" </c:if> ><a href="/report.do?request=invoiceForm">Invoice</a></li>--%>
						<li <c:if test="${fn:indexOf(pageUri, '/bill.do?request=paidForm') > -1}"> class="selected" </c:if> ><a href="/bill.do?request=paidForm">Settlement</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/bill.do?request=invoice') > -1}"> class="selected" </c:if> ><a href="/bill.do?request=invoice">Invoice</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/report.do?request=cblistForm') > -1}"> class="selected" </c:if> ><a href="/report.do?request=cblistForm">ChargeBack</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/report.do?request=depositList') > -1}"> class="selected" </c:if> ><a href="/report.do?request=depositList">Deposit</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/report.do?request=reportMerchant') > -1}"> class="selected" </c:if> ><a href="/report.do?request=reportMerchant">Merchants Statistics</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/report.do?request=reportMonthly') > -1}"> class="selected" </c:if> ><a href="/report.do?request=reportMonthly">Monthly Statistics</a></li>
					</ul>
					<h6 id="h-menu-member" <c:if test="${fn:indexOf(pageUri, '/member.do') == 0}">  class="selected" </c:if>><a href="#member"><span>MEMBER</span></a></h6>
					<ul id="menu-member" <c:if test="${fn:indexOf(pageUri, '/member.do') == -1}"> class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/member.do?request=mlistForm') > -1}"> class="selected" </c:if> ><a href="/member.do?request=mlistForm">Member</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/member.do?request=listForm') > -1}"> class="selected" </c:if> ><a href="/member.do?request=listForm">Merchant</a></li>
					</ul>
					<h6 id="h-menu-member" <c:if test="${fn:indexOf(pageUri, '/manage.do') == 0}">  class="selected" </c:if>><a href="#manage"><span>CONTROL</span></a></h6>
					<ul id="menu-manage" <c:if test="${fn:indexOf(pageUri, '/manage.do') == -1}"> class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/manage.do?request=riskList') > -1}"> class="selected" </c:if> ><a href="/manage.do?request=riskList">Risk</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/manage.do?request=alertList') > -1}"> class="selected" </c:if> ><a href="/manage.do?request=alertList">System Alert</a></li>
					</ul>
					<h6 id="h-menu-notice" <c:if test="${fn:indexOf(pageUri, '/notice.do') == 0}">  class="selected" </c:if>><a href="#notice"><span>NOTICE</span></a></h6>
					<ul id="menu-notice" <c:if test="${fn:indexOf(pageUri, '/notice.do') == -1}"> class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/notice.do?request=notice') > -1}"> class="selected" </c:if> ><a href="/notice.do?request=notice">Notice</a></li>
					</ul>
				</div>
