<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:choose>
	<c:when test="${sBean.invoiceIdx == 'INVOICE_090511000019'}">
		<c:set var="setupfee" value="2000.00" scope="request"/>
	</c:when>
	<c:when test="${sBean.invoiceIdx == 'INVOICE_090831000008' || sBean.invoiceIdx =='INVOICE_091019000003' || sBean.invoiceIdx =='INVOICE_100125000004'}">
		<c:set var="setupfee" value="3000.00" scope="request"/>
	</c:when>
	<c:when test="${sBean.invoiceIdx == 'INVOICE_100510000001' }">
		<c:set var="setupfee" value="4000.00" scope="request"/>
	</c:when>
	<c:when test="${sBean.invoiceIdx == 'INVOICE_110429000001' ||  sBean.invoiceIdx == 'INVOICE_110613000001' ||  sBean.invoiceIdx == 'INVOICE_110920000001' }">
		<c:set var="setupfee" value="1000.00" scope="request"/>
	</c:when>
	<c:otherwise>
		<c:set var="setupfee" value="0.00" scope="request"/>
	</c:otherwise>
</c:choose>

	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/merchant/include/header.jsp" />
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" media="print" />
	<script src="/common/js/print.js" type="text/javascript"></script>
	<!-- 상단 메인 링크 페이지 종료 -->
		<!-- content-->

		<div id="content">
			<!-- end content / left -->
			<div id="left">
				<c:import url="/merchant/include/leftMenu.jsp" />
			</div>
			<!-- end content / left -->
			<!-- content / right -->
			<div id="right">				
				<div class="box box-left box-padding">
					<!-- forms -->
					<!-- box / title -->
					
					<div style="height: 20px;"></div>
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h8>Invoice Infomation</h8>
								<ul class="links">
								<li><a href="javascript:printDiv();">PRINT</a></li>
								</ul>
								<c:if test="${!empty sBean.fileName }">
								<ul class="links">
								<li><a href="javascript:window.open('${sBean.fileName }');" target="_blank">PDF</a></li>
								</ul>
								</c:if>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>Invoice Date</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><strong>${sBean.regDate }</strong></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>Invoice No.</strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong>${sBean.invoiceIdx }</strong></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>Merchant ID</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><strong>${sBean.merchantId }</strong></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>Period</strong></label>
								</div>
								<div class="input2" id="paddingL60">		
									<span class="m"><strong>${sBean.temp1String }&nbsp; ~ &nbsp;${sBean.temp2String }</strong></span>							
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h8>1.Settlement Infomation</h8>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>Transaction Count</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><fmt:formatNumber type="number" value="${sBean.temp1Double }" pattern="#,##0" /></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>Transaction Sales&nbsp;(A)</strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${sBean.temp2Double }" pattern="#,##0.00" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>Refund&nbsp;(B)</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><fmt:formatNumber type="number" value="${sBean.temp6Double }" pattern="#,##0.00" /></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>Chargeback&nbsp;(C)</strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${sBean.temp4Double }" pattern="#,##0.00" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>(1)&nbsp;Net Sales &nbsp;(A-B-C)</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><strong><fmt:formatNumber type="number" value="${sBean.temp2Double - sBean.temp6Double - sBean.temp4Double }" pattern="#,##0.00" /></strong></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">&nbsp;</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">&nbsp;</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h8>2.Fee Information</h8>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>Transaction Fee&nbsp;(A)</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><fmt:formatNumber type="number" value="${sBean.temp8Double }" pattern="#,##0.00" /></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>VAN Fee&nbsp;(B)</strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${sBean.temp10Double }" pattern="#,##0.00" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>Misc Fee&nbsp;(C)</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><fmt:formatNumber type="number" value="${setupfee }" pattern="#,##0.00" /></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>GST(7%)&nbsp;(D)</strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${(sBean.temp8Double+sBean.temp10Double+setupfee) * 0.07}" pattern="#,##0.00" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>(2)&nbsp;Total Fee &nbsp;(A+B+C+D)</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">&nbsp;<strong><fmt:formatNumber type="number" value="${sBean.temp8Double +sBean.temp10Double+ setupfee+(setupfee+sBean.temp8Double+sBean.temp10Double) * 0.07}" pattern="#,##0.00" /></strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">&nbsp;</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h8>3.Collateral</h8>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>Before&nbsp;(A)</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><fmt:formatNumber type="number" value="${dBean.lastAmount }" pattern="#,##0.00" /></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>Applicable percentage</strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="percent" value="${dBean.currentRate / 100}" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>(3)&nbsp;Current&nbsp;(B)</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><fmt:formatNumber type="number" value="${dBean.currAmount }" pattern="#,##0.00" /></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>Balance&nbsp;(A+B)</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><strong><fmt:formatNumber type="number" value="${dBean.totalAmount }" pattern="#,##0.00" /></strong></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">&nbsp;</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">&nbsp;</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h8>4.Total</h8>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>Total&nbsp;(1) - (2) - (3)</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m">&nbsp;</span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong><fmt:formatNumber type="number" value="${(sBean.temp2Double - sBean.temp6Double - sBean.temp4Double) - (sBean.temp8Double +sBean.temp10Double+setupfee +(setupfee+sBean.temp8Double+sBean.temp10Double) * 0.07 ) -dBean.currAmount}" pattern="#,##0.00" /></strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">&nbsp;</span>
								</div>
							</div>
							
						</div>
					</div>
					<div style="height: 260px;"></div>	
				</div>
				
			</div>
			<!-- end content / right -->
		</div>
		<!-- end content -->
		<!-- footer -->
		<c:import url="/merchant/include/footer.jsp" />
		<!-- end footert -->
		
	</body>
</html>
