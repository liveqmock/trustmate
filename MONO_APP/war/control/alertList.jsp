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
						<h5><spring:message code="searchPannel" text=""/>Alert</h5>
						<ul class="links">
						<li><a href="/manage.do?request=alertAddForm">WRITE</a></li>
						</ul>
					</div>
					
					<div class="form">
						<form name="alertList" action="/manage.do?request=alertList" method="post">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="unit">TYPE</label>
								</div>
								<div class="input">
									<select name="alertType" class="input_select2">
					              		<option value="">-----&nbsp;</option>
									    <option value="ALL">ALL</option>
									    <option value="EMAIL">EMAIL</option>
									    <option value="SMS">SMS</option>
									</select>
					                <c:if test="${!empty aBean.alertType}"><script>document.alertList.alertType.value="${aBean.alertType}"</script></c:if>
								</div>
								<div class="label2">
									<label for="resultMsg">코드</label>
								</div>
								<div class="input2">
									<input type="text" name="resultMsg" id="resultMsg" maxlength="16" class="unit" value="${aBean.resultMsg}"/>
								</div>
							</div>
							<div class="field">
								<div class="label">
									<label for="day">기간</label>
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
							&nbsp;
						</h7>
					</div>
					<!-- end box / title -->					
					<div class="table">											
						<table>
							<thead>
								<tr>
									<th class="list_tit">No</th>
									<th class="list_tit">구분</th>
									<th class="list_tit">수신대상</th>
									<th class="list_tit">오류코드</th>
									<th class="list_tit">메세지</th>
									<th class="list_tit">등록자</th>
									<th class="list_tit">등록일시</th>
									<th class="list_tit">삭제</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(alertList) != 0}">
										<c:forEach var="list" items="${alertList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list">
													<c:choose>
														<c:when test="${list.alertType == 'ALL'}"><font color="red">ALL</font>
														</c:when>
														<c:when test="${list.alertType == 'SMS'}"><font color="blue">SMS</font>
														</c:when>
														<c:when test="${list.alertType == 'EMAIL'}"><font color="blue">EMAIL</font>
														</c:when>
														<c:otherwise>
															${list.alertType }
														</c:otherwise>
													</c:choose>
												</td>
												<td class="list">
													<c:choose>
														<c:when test="${list.target == 'ALL'}"><font color="red">ALL</font>
														</c:when>
														<c:when test="${list.target == 'SYSTEM'}"><font color="blue">SYSTEM</font>
														</c:when>
														<c:when test="${list.target == 'ADMIN'}"><font color="blue">ADMIN</font>
														</c:when>
														<c:when test="${list.target == 'NONE'}"><font color="blue">NONE</font>
														</c:when>
														<c:otherwise>
															${list.target }
														</c:otherwise>
													</c:choose>
												</td>
												<td class="list">
													[${list.resultMsg }]&nbsp;${list.msg }
												</td>
												<td class="list">${list.message}&nbsp;</td>
												<td class="list">${list.regId}&nbsp;</td>
												<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
												<td class="list">
													<a href="/manage.do?request=alertDelete&alertType=${list.alertType }&resultMsg=${list.resultMsg }&target=${list.target }&regId=${list.regId }"><input type="button" name="Delete" value="Delete"/></a>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(alertList)) * 12}">
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

