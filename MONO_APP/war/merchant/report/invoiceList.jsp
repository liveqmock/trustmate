<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<c:import url="/merchant/include/header.jsp" />
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
				<!-- table -->
				<!-- forms -->
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5>INVOICE</h5>
					</div>
					<form name="trnsctnList" action="/merchant/report.do?request=invoiceForm" method="post">
					<div class="form">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="day">&nbsp;</label>
								</div>
								<div class="input">&nbsp;</div>
								
								<div class="label2">
									<label for="">&nbsp;</label>
								</div>
								<div class="input2">&nbsp;</div>
								
							</div>
							
						</div>
						
						<!-- table action -->
						<div class="action">
							<div class="button">
								<input type="submit" name="submit" value="search"/>
							</div>
						</div>
						<!-- end table action -->			
					</div>
					</form>
					
					
					
					<div class="box">
						<div class="title">
							<h5><strong>Search result</strong></h5>
						</div>
						<div class="total">
							<h7>&nbsp;</h7>
						</div>
					<!-- end box / title -->					
						<div class="table">											
							<table>
								<thead>
									<tr>
										<th class="list_tit">No</th>
										<th class="list_tit">Invoice No</th>
										<th class="list_tit">Settle Type</th>
										<th class="list_tit">Period</th>
										<th class="list_tit">Turn</th>
										<th class="list_tit">Total Amount</th>
										<th class="list_tit">Total Fee</th>
										<th class="list_tit">Deposit</th>
										<th class="list_tit">Settle Amount</th>
										<th class="list_tit">RegDate</th>
										<th class="list_tit">Pay due date</th>
										<th class="list_tit">Status</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(settleList) != 0}">
											<c:forEach var="list" items="${settleList}" varStatus="status">
												<tr>
													<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
													<td class="list"><a href="javascript:popup2('paidView','/merchant/report.do?request=paidView&settleId=${list.settleId}',900,1200,100,100);showloading(false);"><strong>${list.settleId}</strong></a></td>
													<td class="list">${list.period}&nbsp;</td> 
													<td class="list">${list.startDay}~${list.endDay}&nbsp;</td>
													<td class="list">${list.settleCnt}&nbsp;</td>  
													<td class="list">
														<fmt:formatNumber type="number" value="${list.totAmt}" pattern="#,##0" />&nbsp;
													</td>
													<td class="list">
														<fmt:formatNumber type="number" value="${list.totFee}" pattern="#,##0" />&nbsp;
													</td>
													<td class="list">
														<fmt:formatNumber type="number" value="${list.totDeposit}" pattern="#,##0" />&nbsp;
													</td>
													<td class="list">
														<fmt:formatNumber type="number" value="${list.totSettle}" pattern="#,##0" />&nbsp;
													</td>
													<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd" />&nbsp;</td>
													<td class="list"><fmt:formatDate type="both" value="${list.settleDate}" pattern="yyyy/MM/dd" />&nbsp;</td>
													
													<td class="list">
														
															<c:if test="${list.status =='C'}">
																fixed settle
															</c:if>
															<c:if test="${list.status =='Y'}">
																completion of payment
															</c:if>
															
													</td>  
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr height="${(50-fn:length(settleList)) * 12}">
									    			<td align="center" class="list" colspan="13">&nbsp;NO RESULT</td>
									    	</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							<!-- pagination-->
							<div class="pagination pagination-left">
								${paging}
							</div> 
							
							
						</div>
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
