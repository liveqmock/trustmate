<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

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
				<!-- table -->
				<!-- forms -->
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5>CB 등록</h5>
					</div>
					
					<div class="form">
						<form name="invoiceList" action="/report.do?request=cbAddForm" method="post">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="transactionId">거래번호</label>
								</div>
								<div class="input">
									 <input name="transactionId" type="text"  maxlength="16" tabindex="1" value="" />
								</div>
								<div class="label2">
									<label for="qualification">VAN 거래번호</label>
								</div>
								<div class="input2">
									<input name="vanTransactionId" type="text"  maxlength="50" tabindex="1" value="" />
								</div>
								
							</div>
							
						</div>
						
						<!-- table action -->
						<div class="action">
							<div class="button">
								<input type="submit" name="submit" value="search"/>
							</div>
						</div>
						<!-- end table action -->			
						</form>
					</div>
					
					<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5><strong>ChargeBack Data</strong></h5>
					</div>
					<div class="total">
						<h7></h7>
					</div>
					<!-- end box / title -->		
					<form name="editForm" action="/report.do?request=cbAdd" method="post">			
					<div class="form">	
						<c:if test="${!empty trnsctnBean.transactionId }">
						<div class="fields">	
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">거래번호</label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"> <strong>${trnsctnBean.transactionId }&nbsp;</strong>
										<input name="transactionId" type="hidden" maxlength="20" style="width=260px;" value="${trnsctnBean.transactionId }" />
										<input name="cbState" type="hidden" maxlength="20" value="3" />
										<input name="flag" type="hidden" maxlength="20" value="N" />
										<input name="rootTrnStatus" type="hidden" maxlength="20" value="${trnsctnBean.trnStatus }" />
									</span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">VAN 거래번호</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong>${trnsctnBean.vanTransactionId }&nbsp;</strong></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">주문번호</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${trnsctnBean.payNo }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">거래금액</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<c:if test="${trnsctnBean.curType == 'USD'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0.00" />&nbsp;$</c:if>
										<c:if test="${trnsctnBean.curType == 'JPY'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0" />&nbsp;円</c:if>
										<c:if test="${trnsctnBean.curType == 'KRW'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0" />&nbsp;</c:if>
										<c:if test="${trnsctnBean.curType == 'EUR'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0.00" />&nbsp;€</c:if>
									</span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">승인번호</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<strong>${trnsctnBean.approvalNo }&nbsp;</strong>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">거래상태</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<c:choose>
											<c:when test="${trnsctnBean.trnStatus == '00'}"><font color="#800080">승인요청중</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '01'}"><font color="red">승인장애</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '02'}"><font color="blue">승인완료</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '03'}">승인실패
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '04'}"><font color="red">승인취소요청</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '05'}"><font color="red">승인취소장애</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '06'}">승인취소
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '07'}"><font color="blue">매입요청</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '08'}"><font color="blue">매입중</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '09'}"><font color="blue">매입완료</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '10'}"><font color="red">매입반려</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '11'}"><font color="blue">정산대기</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '12'}"><font color="blue">정산요청</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '13'}"><font color="blue">정산완료</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '14'}">정산실패
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '15'}">환불요청
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '17'}"><font color="red">취소대기</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '18'}"><font color="red">매입취소</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '19'}"><font color="red">매입취소반려</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '20'}"><font color="red">매입취소중</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '21'}"><font color="red">매입실패</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '22'}"><font color="red">C・B 등록</font>
											</c:when>
											<c:when test="${trnsctnBean.trnStatus == '23'}"><font color="red">C・B 확정</font>
											</c:when>
											<c:otherwise>
												${trnsctnBean.trnStatus }
											</c:otherwise>
										</c:choose>
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">고객 ID</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${trnsctnBean.payUserId }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">고객명</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${trnsctnBean.payName }&nbsp;</span>
								</div>							
							</div>
						</div>
						<div class="action">
							<div class="button">
								<input type="submit" name="submit" value="Regist" onclick="javascript:document.editForm.submit();"/>
							</div>
						</div>
						
						</c:if>
					</div>
					</form>
				</div>
					
					
					
				</div>
				<!-- end forms 
				<div class="box">
					<div style="height: 800px;"></div>
				</div>
				-->
				<!-- end table -->
			</div>
			<!-- end content / right -->
		</div>
		<!-- end content -->
		<!-- footer -->
		<c:import url="/include/footer.jsp" />
		<!-- end footert -->
	</body>
</html>


