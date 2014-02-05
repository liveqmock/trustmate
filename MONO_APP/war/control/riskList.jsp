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
						<h5><spring:message code="searchPannel" text=""/>Risk</h5>
						<ul class="links">
						<li><a href="/manage.do?request=riskAddForm">WRITE</a></li>
						</ul>
					</div>
					
					<div class="form">
						<form name="riskList" action="/manage.do?request=riskList" method="post">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="unit">정보</label>
								</div>
								<div class="input">
									<input type="text" name="unit" id="unit" maxlength="16" class="unit" value="${trBean.unit}"/>
								</div>
								<div class="label2">
									<label for="active">상태</label>
								</div>
								<div class="input2">
									<select name="active" class="input_select2">
					              		<option value="">-----&nbsp;</option>
									    <option value="Y">차단</option>
									    <option value="N">해제</option>
									</select>
					                <c:if test="${!empty trBean.active}"><script>document.riskList.active.value="${trBean.active}"</script></c:if>
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
									<th class="list_tit">정보</th>
									<th class="list_tit">상태</th>
									<th class="list_tit">등록사유</th>
									<th class="list_tit">등록일시</th>
									<th class="list_tit">수정</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(trList) != 0}">
										<c:forEach var="list" items="${trList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list">${list.unit }&nbsp;</td>
												<td class="list">
													<c:choose>
														<c:when test="${list.active == 'Y'}"><font color="red">차단</font>
														</c:when>
														<c:when test="${list.active == 'N'}"><font color="blue">해제</font>
														</c:when>
														<c:otherwise>
															${list.active }
														</c:otherwise>
													</c:choose>
												</td>
												<td class="list">${list.comments}&nbsp;</td>
												<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
												<td class="list">
													<a href="/manage.do?request=riskEditForm&idx=${list.idx }"><input type="button" name="Edit" value="Edit"/></a>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(trList)) * 12}">
								    			<td align="center" class="list" colspan="6">&nbsp;NO RESULT</td>
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

