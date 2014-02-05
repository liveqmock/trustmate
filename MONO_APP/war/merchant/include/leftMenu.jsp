<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



				<div id="menu">
					<h6 id="h-menu-trnsctn" <c:if test="${fn:indexOf(pageUri, '/merchant/trnsctn.do') == 0}"> class="selected" </c:if>><a href="#trnsctn"><span>TRANSACTION</span></a></h6>
					<ul id="menu-trnsctn" <c:if test="${fn:indexOf(pageUri, '/merchant/trnsctn.do') == -1}"> class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/merchant/trnsctn.do?request=listForm') > -1}"> class="selected" </c:if>><a href="/merchant/trnsctn.do?request=listForm">Transaction</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/merchant/trnsctn.do?request=authForm') > -1}"> class="selected" </c:if>><a href="/merchant/trnsctn.do?request=authForm">Authentication</a></li>
					</ul>
					<h6 id="h-menu-report" <c:if test="${fn:indexOf(pageUri, '/merchant/report.do') == 0}">  class="selected" </c:if>><a href="#report"><span>REPORT</span></a></h6>
					<ul id="menu-report" <c:if test="${fn:indexOf(pageUri, '/merchant/report.do') == -1}">  class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/merchant/report.do?request=acquireForm') > -1}"> class="selected" </c:if> ><a href="/merchant/report.do?request=acquireForm">Collect</a></li>
						<%-- <li <c:if test="${fn:indexOf(pageUri, '/merchant/report.do?request=settlementForm') > -1}"> class="selected" </c:if> ><a href="/merchant/report.do?request=settlementForm">Paid</a></li> --%>
						<li <c:if test="${fn:indexOf(pageUri, '/merchant/report.do?request=invoiceForm') > -1}"> class="selected" </c:if> ><a href="/merchant/report.do?request=invoiceForm">Invoice</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/merchant/report.do?request=cblistForm') > -1}"> class="selected" </c:if> ><a href="/merchant/report.do?request=cblistForm">ChargeBack</a></li>
					</ul>
					<h6 id="h-menu-member" <c:if test="${fn:indexOf(pageUri, '/merchant/info.do') == 0}">  class="selected" </c:if>><a href="#member"><span>MERCHANT</span></a></h6>
					<ul id="menu-member" <c:if test="${fn:indexOf(pageUri, '/merchant/info.do') == -1}"> class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/merchant/info.do?request=info') > -1}"> class="selected" </c:if> ><a href="/merchant/info.do?request=info">Information</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/merchant/info.do?request=notice') > -1}"> class="selected" </c:if> ><a href="/merchant/info.do?request=notice">Notice</a></li>
						<li <c:if test="${fn:indexOf(pageUri, '/merchant/info.do?request=question') > -1}"> class="selected" </c:if> ><a href="/merchant/info.do?request=question">Question</a></li>
					</ul>
					<h6 id="h-menu-document" <c:if test="${fn:indexOf(pageUri, '/merchant/document.do') == 0}">  class="selected" </c:if>><a href="#document"><span>DOCUMENT</span></a></h6>
					<ul id="menu-document" <c:if test="${fn:indexOf(pageUri, '/merchant/document.do') == -1}"> class="closed" </c:if>>
						<li <c:if test="${fn:indexOf(pageUri, '/merchant/document.do?request=manual') > -1}"> class="selected" </c:if> ><a href="/merchant/document.do?request=manual">Document</a></li>
					</ul>
				</div>
