<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



				<div id="menu">
					<h6 id="h-menu-trnsctn" <c:if test="${fn:indexOf(pageUri, '/group/trnsctn.do') == 0}"> class="selected" </c:if>><a href="#trnsctn"><span>TRANSACTION</span></a></h6>
					<ul id="menu-trnsctn" <c:if test="${fn:indexOf(pageUri, '/group/trnsctn.do') == -1}"> class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/group/trnsctn.do?request=listForm') > -1}"> class="selected" </c:if>><a href="/group/trnsctn.do?request=listForm">Transaction</a></li>
					</ul>
					<h6 id="h-menu-member" <c:if test="${fn:indexOf(pageUri, '/group/info.do?request=merchant') == 0}">  class="selected" </c:if>><a href="#member"><span>MERCHANT</span></a></h6>
					<ul id="menu-member" <c:if test="${fn:indexOf(pageUri, '/group/info.do?request=merchant') == -1}"> class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/group/info.do?request=info?request=merchant') > -1}"> class="selected" </c:if> ><a href="/group/info.do?request=merchantList">Merchant</a></li>
					</ul>
					<h6 id="h-menu-report" <c:if test="${fn:indexOf(pageUri, '/group/report.do') == 0}">  class="selected" </c:if>><a href="#report"><span>REPORT</span></a></h6>
					<ul id="menu-report" <c:if test="${fn:indexOf(pageUri, '/group/report.do') == -1}">  class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/group/report.do?request=invoice') > -1}"> class="selected" </c:if> ><a href="/group/report.do?request=invoice">Invoice</a></li>
					</ul>
					<%--
					<h6 id="h-menu-report" <c:if test="${fn:indexOf(pageUri, '/group/report.do') == 0}">  class="selected" </c:if>><a href="#report"><span>REPORT</span></a></h6>
					<ul id="menu-report" <c:if test="${fn:indexOf(pageUri, '/group/report.do') == -1}">  class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/group/report.do?request=acquireForm') > -1}"> class="selected" </c:if> ><a href="/group/report.do?request=acquireForm">Collect</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/group/report.do?request=settlementForm') > -1}"> class="selected" </c:if> ><a href="/group/report.do?request=settlementForm">Paid</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/group/report.do?request=invoiceForm') > -1}"> class="selected" </c:if> ><a href="/group/report.do?request=invoiceForm">Invoice</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/group/report.do?request=cblistForm') > -1}"> class="selected" </c:if> ><a href="/group/report.do?request=cblistForm">ChargeBack</a></li>
					</ul>
					<h6 id="h-menu-member" <c:if test="${fn:indexOf(pageUri, '/group/info.do') == 0}">  class="selected" </c:if>><a href="#member"><span>MERCHANT</span></a></h6>
					<ul id="menu-member" <c:if test="${fn:indexOf(pageUri, '/group/info.do') == -1}"> class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/group/info.do?request=info') > -1}"> class="selected" </c:if> ><a href="/group/info.do?request=info">Merchant</a></li>
					</ul>
					 --%>
				</div>
