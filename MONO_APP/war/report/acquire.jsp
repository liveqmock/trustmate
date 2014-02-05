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
						<h5><spring:message code="searchPannel" text=""/>매입내역</h5>
					</div>
					
					<div class="form">
						<form name="acquireList" action="/report.do?request=acquire" method="post">
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
									<label for="day">상태</label>
								</div>
								<div class="input">
									<select name="status" class="input_select2">
					              		<option value="">-----&nbsp;</option>
									    <option value="00">매입완료</option>
									    <option value="11">매입실패</option>
									    <option value="22">매입반송</option>
									    <option value="33">재매입</option>
									    <option value="44">차지백</option>
									</select>
					                <c:if test="${!empty trnsctnAcquireBean.status}"><script>document.acquireList.status.value="${trnsctnAcquireBean.status}"</script></c:if>
								</div>
								
								<div class="label2">
									<label for="qualification">검색건수</label>
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
									<th class="list_tit">거래번호</th>
									<th class="list_tit">상태</th>
									<th class="list_tit">매입일시</th>
									<th class="list_tit">금액(USD)</th>
									<th class="list_tit">Card Error</th>
									<th class="list_tit">Van Error</th>
									<th class="list_tit">Van</th>
									<th class="list_tit">송신일시</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(acquireList) != 0}">
										<c:forEach var="list" items="${acquireList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list">${list.transactionId }&nbsp;</td>
												<td class="list">
													<c:choose>
														<c:when test="${list.status == '00'}"><font color="blue">매입완료</font>
														</c:when>
														<c:when test="${list.status == '11'}"><font color="blue">매입실패</font>
														</c:when>
														<c:when test="${list.status == '22'}"><font color="blue">매입반송</font>
														</c:when>
														<c:when test="${list.status == '33'}"><font color="red">재매입</font>
														</c:when>
														<c:when test="${list.status == '44'}"><font color="red">차지백</font>
														</c:when>
														<c:otherwise>
															${list.trnStatus }
														</c:otherwise>
													</c:choose>
												</td>
												<td class="list">${list.acquireDate}&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" /></td>
												<td class="list">${list.cardErrCd}&nbsp;</td> <!-- 카드번호 -->
												<td class="list">${list.vanErrCd}&nbsp;</td> <!-- 카드종류 --> 
												<td class="list">${list.van}&nbsp;</td>
												<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(acquireList)) * 12}">
								    			<td align="center" class="list" colspan="9">&nbsp;NO RESULT</td>
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

