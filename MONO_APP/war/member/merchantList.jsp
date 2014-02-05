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
						<h5>가맹점 조회</h5>
						<ul class="links">
						<li><a href="/member.do?request=addForm">WRITE</a></li>
						</ul>
					</div>
					<div class="form">
						<form name="merchantList" action="/member.do?request=list" method="post">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="select">가맹점아이디</label>
								</div>
								<div class="input">
									<input type="text" name="merchantId" value="${mBean.merchantId}" />
								</div>
								<div class="label2">
									<label for="">상태</label>
								</div>
								<div class="input2">
									<select name="active">
					              		<option value="">-----&nbsp;</option>
					              		<option value="0">예비</option>
									    <option value="1">사용</option>
									    <option value="2">중지</option>
									    <option value="3">해지</option>
									</select>
					                <c:if test="${!empty mBean.active}"><script>document.merchantList.active.value="${mBean.active}"</script></c:if>
								</div>
								
								
							</div>
							<div class="field">
								<div class="label">
									<label for="select">기간</label>
								</div>
								<div class="input">
									<input type="text" id="startDate" name="startDate" class="date" value="${startDate}" onchange="onlyDate2(this);"/>
									<span class="m">-</span>
									<input type="text" id="endDate" name="endDate" class="date" value="${endDate }" onchange="onlyDate2(this);"/>
								</div>
								<div class="label2">
									<label for="">검색건수</label>
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
						</form>
						<!-- end table action -->			
					</div>
					
				</div>
				<!-- end forms -->
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5><spring:message code="searchResult" text=""/></h5>
					</div>
					<div class="total">
						<h7>
							<strong>Search result</strong>
							<strong>Count : <font color="blue"><fmt:formatNumber type="number" value="${dao.totalCount}" pattern="#,##0" /></font>&nbsp;&nbsp;&nbsp;</strong>
						</h7>
					</div>
					<!-- end box / title -->					
					<div class="table">											
						<table>
							<thead>
								<tr>
									<th class="list_tit">No</th>
									<th class="list_tit">가맹점 아이디</th>
									<th class="list_tit">가맹점 이름</th>
									<th class="list_tit">가맹점 연락처</th>
									<th class="list_tit">가맹점 팩스번호</th>
									<th class="list_tit">가맹점 이메일</th>
									<th class="list_tit">상태</th>
									<th class="list_tit">서비스 일자</th>
									<th class="list_tit">등록 일자</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(mList) != 0}">
										<c:forEach var="list" items="${mList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list">
													<a href="/member.do?request=view&merchantId=${list.merchantId }"><strong>${list.merchantId}</strong></a>
												</td>
												<td class="list">${list.name}&nbsp;</td>
												<td class="list">${list.telNo}&nbsp;</td>
												<td class="list">${list.fax}&nbsp;</td>
												<td class="list">${list.email}&nbsp;</td>
												<td class="list">
													<c:choose>
														<c:when test="${list.active == '0'}"><font color="#800080">예비</font>
														</c:when>
														<c:when test="${list.active == '1'}"><font color="blue">사용</font>
														</c:when>
														<c:when test="${list.active == '2'}"><font color="green">중지</font>
														</c:when>
														<c:when test="${list.active == '3'}"><font color="red">해지</font>
														</c:when>
														<c:otherwise>
															${list.active }
														</c:otherwise>
													</c:choose>
												</td>
												<td class="list"><fmt:formatDate type="both" value="${list.serviceDate}" pattern="yyyy/MM/dd" /></td>
												<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td class="list" colspan="9"><font color="red"> NO RESULT</font></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<!-- pagination-->
						<div class="pagination pagination-left">
							${paging}
						</div> 
						<!-- end pagination -->
						<!-- table action 
						<div class="action">
							<div class="button">
								<input type="submit" name="submit" value="Excel" />&nbsp;
								<input type="submit" name="submit" value="Search" />
							</div>
						</div>
						<!-- end table action -->
						
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
