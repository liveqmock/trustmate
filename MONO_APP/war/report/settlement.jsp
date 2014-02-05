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
						<h5>정산내역</h5>
					</div>
					
					<div class="form">
						<form name="settlementList" action="/report.do?request=settlement" method="post">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="merchantId">가맹점아이디</label>
								</div>
								<div class="input">
									<input type="text" name="merchantId" id="merchantId" maxlength="16" value="${sBean.merchantId}"/>
								</div>
								<div class="label2">
									<label for="qualification">기간</label>
								</div>
								<div class="input2">
									<input type="text" id="startDate" name="startDate" class="date" value="${startDate}" onchange="onlyDate2(this);"/>
									<span class="m">-</span>
									<input type="text" id="endDate" name="endDate" class="date" value="${endDate }" onchange="onlyDate2(this);"/>
								</div>
								
							</div>
							<div class="field">
								<div class="label">
									<label for="">정산번호</label>
								</div>
								<div class="input">
									<input type="text" name="invoiceIdx" id="invoiceIdx" maxlength="22" class="text200" value="${sBean.invoiceIdx}"/>
								</div>
								<div class="label2">
									<label for="day">검색건수</label>
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
					</div>
					</form>
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
									<th class="list_tit">정산번호</th>
									<th class="list_tit">가맹점아이디</th>
									<th class="list_tit">거래(건/금액)</th>
									<th class="list_tit">차지백(건/금액)</th>
									<th class="list_tit">취소(건/금액)</th>
									<th class="list_tit">수수료(건/금액)</th>
									<th class="list_tit">VANFEE(건/금액)</th>
									<th class="list_tit">시작일</th>
									<th class="list_tit">종료일</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(settlementList) != 0}">
										<c:forEach var="list" items="${settlementList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list"><a href="/report.do?request=settlementView&invoiceIdx=${list.invoiceIdx }&merchantId=${list.merchantId}"><strong>${list.invoiceIdx}</strong></a></td>
												<td class="list">${list.merchantId}</td>
												<td class="list">
													<fmt:formatNumber type="number" value="${list.temp1Double}" pattern="#,##0" />&nbsp;/&nbsp;<fmt:formatNumber type="number" value="${list.temp2Double}" pattern="#,##0.00" />
												</td>
												<td class="list">
													<fmt:formatNumber type="number" value="${list.temp3Double}" pattern="#,##0" />&nbsp;/&nbsp;<fmt:formatNumber type="number" value="${list.temp4Double}" pattern="#,##0.00" />
												</td>
												<td class="list">
													<fmt:formatNumber type="number" value="${list.temp5Double}" pattern="#,##0" />&nbsp;/&nbsp;<fmt:formatNumber type="number" value="${list.temp6Double}" pattern="#,##0.00" />
												</td>
												<td class="list">
													<fmt:formatNumber type="number" value="${list.temp7Double}" pattern="#,##0" />&nbsp;/&nbsp;<fmt:formatNumber type="number" value="${list.temp8Double}" pattern="#,##0.00" />
												</td>
												<td class="list">
													<fmt:formatNumber type="number" value="${list.temp9Double}" pattern="#,##0" />&nbsp;/&nbsp;<fmt:formatNumber type="number" value="${list.temp10Double}" pattern="#,##0.00" />
												</td>
												<td class="list">${list.temp1String}&nbsp;</td> 
												<td class="list">${list.temp2String}&nbsp;</td>  
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
		<c:import url="/include/footer.jsp" />
		<!-- end footert -->
	</body>
</html>

