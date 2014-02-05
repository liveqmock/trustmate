<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<!-- 상단 메인 링크 페이지 -->
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
						<h5>Paid</h5>
					</div>
					
					<div class="form">
						<form name="settlementList" action="/merchant/report.do?request=settlement" method="post">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="merchantId">TYPE</label>
								</div>
								<div class="input">
									<select name="settleType">
					              		<option value="">-----&nbsp;</option>
									    <option value="T">Transaction</option>
									    <option value="C">Charge Back</option>
									    <option value="R">Refund</option>
									    <option value="F">Fee</option>
									</select>
					                <c:if test="${!empty siBean.settleType}"><script>document.settlementList.settleType.value="${siBean.settleType}"</script></c:if>
								</div>
								<div class="label2">
									<label for="qualification">Period</label>
								</div>
								<div class="input2">
									<input type="text" id="startDate" name="startDate" class="date" value="${startDate}" onchange="onlyDate2(this);"/>
									<span class="m">-</span>
									<input type="text" id="endDate" name="endDate" class="date" value="${endDate }" onchange="onlyDate2(this);"/>
								</div>
								
							</div>
							<div class="field">
								<div class="label">
									<label for="">Status</label>
								</div>
								<div class="input">
									<select name="settleYn" >
					              		<option value="">-----&nbsp;</option>
									    <option value="N">Ready</option>
									    <option value="Y">Fixed</option>
									</select>
					                <c:if test="${!empty siBean.settleYn}"><script>document.settlementList.settleYn.value="${siBean.settleYn}"</script></c:if>
								</div>
								<div class="label2">
									<label for="day">Search Count</label>
								</div>
								<div class="input2">
									<select name="pageSize">
					              		<option value="50" <c:if test="${dao.pageSize == 50}">selected</c:if>>50</option>
					                	<option value="100" <c:if test="${dao.pageSize == 100}">selected</c:if>>100</option>
					                	<option value="200" <c:if test="${dao.pageSize == 200}">selected</c:if>>200</option>
					                	<option value="500" <c:if test="${dao.pageSize == 500}">selected</c:if>>500</option>
					                </select>
								</div>
								
							</div>
							
						</div>
						
						<!-- table action -->
						<div class="action">
							<div class="button">
								<input type="submit" name="format" value="excel"/>&nbsp;
								<input type="submit" name="submit" value="search"/>
							</div>
						</div>
						<!-- end table action -->		
						</form>	
					</div>
					
				</div>
				<!-- end forms -->
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5><strong>Search result</strong></h5>
					</div>
					<div class="total">
						<h7>
							<strong>Count : <font color="blue"><fmt:formatNumber type="number" value="${dao.totalCount}" pattern="#,##0" /></font></strong>
						</h7>
					</div>
					<!-- end box / title -->					
					<div class="table">											
						<table>
							<thead>
								<tr>
									<th class="list_tit">No</th>
									<th class="list_tit">Paid Index</th>
									<th class="list_tit">Type</th>
									<th class="list_tit">Start Date</th>
									<th class="list_tit">End Date</th>
									<th class="list_tit">Count</th>
									<th class="list_tit">Currency</th>
									<th class="list_tit">Amount</th>
									<th class="list_tit">Status</th>
									<th class="list_tit">Regist Date</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(settlementList) != 0}">
										<c:forEach var="list" items="${settlementList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list"><strong>${list.invoiceIdx}</strong></td>
												<td class="list">
													<strong>
									      				<c:if test="${list.settleType == 'T'}">TRANSACTION</c:if>
									      				<c:if test="${list.settleType == 'C'}">CHARGE BACK</c:if>
									      				<c:if test="${list.settleType == 'R'}">REFUND</c:if>
									      				<c:if test="${list.settleType == 'F'}">FEE</c:if>
									      				<c:if test="${list.settleType == 'S'}">SETUP FEE</c:if>
									      				<c:if test="${list.settleType == 'V'}">VANFEE</c:if>
									      			</strong>
												</td>
												<td class="list">${list.startDate}</td>
												<td class="list">${list.endDate}</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.totalCount}" pattern="#,##0" /></td>
												<td class="list">${sso.curType}</td>
												
												<td class="list"><c:if test="${list.settleType != 'T'}">-</c:if><fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" /></td>
												<td class="list">
													<c:if test="${list.settleYn == 'N' }">Ready</c:if>
								      				<c:if test="${list.settleYn == 'Y' }">Fixed</c:if>
								      			</td>
												<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td> 
												
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(settlementList)) * 12}">
								    			<td align="center" class="list" colspan="10">&nbsp;NO RESULT</td>
								    	<tr>
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
				<!-- end table -->
			</div>
			<!-- end content / right -->
		</div>
		<!-- end content -->
		<!-- footer -->
		<c:import url="/merchant/include/footer.jsp" />
		<!-- end footert -->
	</body>
</html>

