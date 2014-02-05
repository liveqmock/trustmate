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
	<c:import url="/include/header.jsp" />
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" media="print" />
	<script src="/common/js/print.js" type="text/javascript"></script>
	<!-- 상단 메인 링크 페이지 종료 -->
		<!-- content-->

		<div id="content">
			<!-- end content / left -->
			<div id="left">
				<c:import url="/include/leftMenu.jsp" />
			</div>
			<!-- end content / left -->
			<!-- content / right -->
			<div id="right" id="right">				
				<div class="box">
					<!-- forms -->
					<!-- box / title -->
					<div class="title">
						<h5>정산 명세서</h5>
						<ul class="links">
						<li><a href="javascript:printDiv();">PRINT</a></li>
						</ul>
						<c:if test="${!empty sBean.fileName }">
						<ul class="links">
						<li><a href="javascript:window.open('${sBean.fileName }');" target="_blank">PDF</a></li>
						</ul>
						</c:if>
						
					</div>
					<div style="height: 20px;"></div>
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h8>정산 정보</h8>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>정산 일자</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><strong>${sBean.regDate }</strong></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>정산 명세서</strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong>${sBean.invoiceIdx }</strong></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>가맹점 아이디</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><strong>${sBean.merchantId }</strong></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>가맹점 명</strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong>${mBean.name }</strong></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>기간</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><strong>${sBean.temp1String }&nbsp; ~ &nbsp;${sBean.temp2String }</strong></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">&nbsp;</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">&nbsp;</strong></span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h8>1.정산 내역</h8>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>거래 건수</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><fmt:formatNumber type="number" value="${sBean.temp1Double }" pattern="#,##0" /></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>거래 매상&nbsp;(A)</strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${sBean.temp2Double }" pattern="#,##0.00" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>취소&nbsp;(B)</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><fmt:formatNumber type="number" value="${sBean.temp6Double }" pattern="#,##0.00" /></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>차지백&nbsp;(C)</strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${sBean.temp4Double }" pattern="#,##0.00" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>(1)&nbsp;매출 &nbsp;(A-B-C)</strong></label>
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
								<h8>2.수수료 내용</h8>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>거래 수수료&nbsp;(A)</strong></label>
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
									<label for=""><strong>기타 수수료&nbsp;(C)</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><fmt:formatNumber type="number" value="${setupfee }" pattern="#,##0.00" /></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>부가세(7%)&nbsp;(D)</strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${(sBean.temp8Double+sBean.temp10Double+setupfee) * 0.07}" pattern="#,##0.00" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>(2)&nbsp;수수료 합계&nbsp;(A+B+C+D)</strong></label>
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
								<h8>4.예치금</h8>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>이전 예치금&nbsp;(A)</strong></label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><fmt:formatNumber type="number" value="${dBean.lastAmount }" pattern="#,##0.00" /></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for=""><strong>예치금 비율</strong></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="percent" value="${dBean.currentRate / 100}" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>(3)</strong>&nbsp;당월 예치금&nbsp;(B)</label>
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
									<label for=""><strong>예치금 현황&nbsp;(A+B)</strong></label>
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
								<h8>5.합계</h8>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for=""><strong>정산 합계&nbsp;(1) - (2) - (3)</strong></label>
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
					<div style="height: 180px;"></div>	
				</div>
				
			</div>
			<!-- end content / right -->
		</div>
		<!-- end content -->
		<!-- footer -->
		<c:import url="/include/footer.jsp" />
		<!-- end footert -->
		
	</body>
</html>
