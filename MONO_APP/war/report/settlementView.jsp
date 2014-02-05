<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/include/header.jsp" />
	<!-- 상단 메인 링크 페이지 종료 -->
		<!-- content-->
		<div id="content">
			<!-- end content / left -->
			<div id="left">
				<c:import url="/include/leftMenu.jsp" />
			</div>
			<!-- end content / left -->
			<!-- content / right -->
			<div id="right">				
				<div class="box">
					<!-- forms -->
					<!-- box / title -->
					<div class="title">
						<h5>정산 상세</h5>
					</div>
					<c:choose>
				    <c:when test="${fn:length(siList) != 0}">
				    <c:forEach var="list" items="${siList}" varStatus="status">
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h6> 
									<c:if test="${list.settleType == 'T'}"> 거래</c:if>
						          	<c:if test="${list.settleType == 'C'}"> 차지백</c:if>
						          	<c:if test="${list.settleType == 'R'}"> 취소</c:if>
						          	<c:if test="${list.settleType == 'F'}"> 수수료</c:if>
						          	<c:if test="${list.settleType == 'V'}"> VANFEE</c:if>
								</h6>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">정산기간(시작일)</label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"> <strong>${list.startDate }&nbsp;</strong></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">정산기간(종료일)</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong>${list.endDate }&nbsp;</strong></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">대상 건수</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<strong><fmt:formatNumber type="number" value="${list.totalCount }" pattern="#,##0" /></strong>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">대상 금액</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong><fmt:formatNumber type="number" value="${list.amount }" pattern="#,##0.00" />&nbsp;</strong></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">정산 여부</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><strong>
										<c:if test="${list.settleYn == 'N' }">예정</c:if>
	          							<c:if test="${list.settleYn == 'Y' }">확정</c:if>&nbsp;
	          						</strong></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">등록일 </label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd/ HH:mm:ss" />&nbsp;</strong></span>
								</div>
							</div>
						</div>
					</div>
					</c:forEach>
					</c:when>
					</c:choose>
					<c:if test="${fn:length(dList) != 0}">
					<div class="form">
						<div class="title">
							<h6>예치금 이력 현황</h6>
							<ul class="links">
							<li>
								<c:if test="${sBean.settleYn == 'N'}">
	          					<a href="javascript:popup('deposit','/report.do?request=deposit&merchantId=${merchantId }&settleIdx=${sBean.idx }',540,280,100,100);">예치금등록</a>
	          					</c:if>
	          				</li>
							</ul>
						</div>
						<div class="table">												
							<table>
									<thead>
										<tr>
											<th class="list_tit">예치금비율</th>
											<th class="list_tit">마지막 금액</th>
											<th class="list_tit">현재 금액</th>
											<th class="list_tit">총 금액</th>
											<th class="list_tit">등록일</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
    										<c:when test="${fn:length(dList) != 0}">
											<c:forEach var="list" items="${dList}" varStatus="status">
												<tr>
													<td class="list"><fmt:formatNumber type="percent" value="${list.currentRate / 100}" /></td>
													<td class="list"><fmt:formatNumber type="number" value="${list.lastAmount }" pattern="#,##0.00" /></td>
													<td class="list"><fmt:formatNumber type="number" value="${list.currAmount }" pattern="#,##0.00" /></td>
													<td class="list"><fmt:formatNumber type="number" value="${list.totalAmount }" pattern="#,##0.00" /></td>
													<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
													
												</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr align="center" onMouseOver="this.style.backgroundColor='#B6D8EA'" onMouseOut="this.style.backgroundColor=''" style="height:16;word-break:break-all;">
													<td align="center" colspan="5"><font color="red"> NO RESULT</font></td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
						</div>
					</div>
					</c:if>
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL20">
								<h6>정산 파일 등록</h6>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">파일</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
									<c:if test="${empty sBean.fileName || sBean.settleYn == 'N'}">
							      		<a href="javascript:popup('fileUpload','/report.do?request=fileUpload&merchantId=${merchantId }&invoiceIdx=${invoiceIdx }&settleIdx=${sBean.idx}',540,200,100,100);"><font color="red">REGIST</font></a>
							    	</c:if>
							    	<c:if test="${!empty sBean.fileName && sBean.settleYn == 'Y'}">
							    		UploadFileName : <font color="blue">${sBean.fileName }</font>
							    	</c:if>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">&nbsp;
										<c:if test="${fileCheck == 'Y'}">
											확인
										</c:if>
									</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<c:if test="${fileCheck == 'Y'}">
								        	<a href="/report.do?request=update&invoiceIdx=${invoiceIdx }"><img src="/images/popup/button_confirm.gif" alt="settle" width="78" height="32" border="0" /></a>
								        </c:if>
									</span>
								</div>
							</div>
							
						</div>
					</div>
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
