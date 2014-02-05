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
						<h5>CHARGEBACK 조회</h5>
						<c:if test="${sso.memberRole == '0001' }">
						<ul class="links">
						<li><a href="/report.do?request=cbAddForm">WRITE</a></li>
						</ul>
						</c:if>
					</div>
					<form name="cbList" action="/report.do?request=cblist" method="post">
					<div class="form">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="temp1String">가맹점아이디</label>
								</div>
								<div class="input">
									<input type="text" name="temp1String" id="temp1String" maxlength="16" class="temp1String" value="${cbBean.temp1String}"/>
								</div>
								<div class="label2">
									<label for="transactionId">거래번호</label>
								</div>
								<div class="input2">
									<input type="text" name="transactionId" id="transactionId" maxlength="16" class="transactionId" value="${cbBean.transactionId}"/>
								</div>
							</div>
							<div class="field">
								
								<div class="label">
									<label for="">기간</label>
								</div>
								<div class="input">
									<input type="text" id="startDate" name="startDate" class="date" value="${startDate}" onchange="onlyDate2(this);"/>
									<span class="m">-</span>
									<input type="text" id="endDate" name="endDate" class="date" value="${endDate }" onchange="onlyDate2(this);"/>
								</div>
								<div class="label2">
									<label for="pageSize">검색건수</label>
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
									<th class="list_tit">거래번호</th>
									<th class="list_tit">상태</th>
									<th class="list_tit">통화</th>
									<th class="list_tit">금액</th>
									<th class="list_tit">원거래상태</th>
									<th class="list_tit">전송상태</th>
									<th class="list_tit">등록일시</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(cbList) != 0}">
										<c:forEach var="list" items="${cbList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list">${list.transactionId }&nbsp;</td>
												<td class="list">
													<c:if test="${list.cbState == '1' }">등록</c:if>
									      			<c:if test="${list.cbState == '2' }">처리중</c:if>
									      			<c:if test="${list.cbState == '3' }">처리완료</c:if>
									      			<c:if test="${list.cbState == '4' }">반려</c:if>
												</td>
												<td class="list">${list.temp2String}&nbsp;</td> <!-- 카드번호 -->
												<td class="list"><fmt:formatNumber type="number" value="${list.temp1Double}" pattern="#,##0.00" /></td>
												<td class="list">${list.rootTrnStatus}&nbsp;</td> <!-- 카드번호 -->
												<td class="list">${list.flag}&nbsp;</td> <!-- 카드종류 --> 
												<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(cbList)) * 12}">
								    			<td align="center" class="list" colspan="8">&nbsp;NO RESULT</td>
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

