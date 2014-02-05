<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/group/include/header.jsp" />
	<!-- 상단 메인 링크 페이지 종료 -->
		<!-- content-->
		<div id="content">
			<!-- end content / left -->
			<div id="left">
				<c:import url="/group/include/leftMenu.jsp" />
			</div>
			<!-- end content / left -->
			<!-- content / right -->
			<div id="right">				
				<!-- table -->
				<!-- forms -->
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5>List of Merchant</h5>
						<ul class="links">
						<li></li>
						</ul>
					</div>
					<div class="form">
						<form name="merchantList" action="/group/info.do?request=merchantList" method="post">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="select">MERCHANT ID</label>
								</div>
								<div class="input">
									<input type="text" name="merchantId" value="${merchantBean.merchantId}" />
								</div>
								<div class="label2">
									<label for="">STATUS</label>
								</div>
								<div class="input2">
									<select name="active">
					              		<option value="">-----&nbsp;</option>
					              		<option value="0">Stable</option>
									    <option value="1">Active</option>
									    <option value="2">Non-Active</option>
									    <option value="3">Termination</option>
									</select>
					                <c:if test="${!empty merchantBean.active}"><script>document.merchantList.active.value="${merchantBean.active}"</script></c:if>
								</div>
								
								
							</div>
							<div class="field">
								<div class="label">
									<label for="select">Period</label>
								</div>
								<div class="input">
									<input type="text" id="startDate" name="startDate" class="date" value="${startDate}" onchange="onlyDate2(this);"/>
									<span class="m">-</span>
									<input type="text" id="endDate" name="endDate" class="date" value="${endDate }" onchange="onlyDate2(this);"/>
								</div>
								<div class="label2">
									<label for="">Search count</label>
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
									<th class="list_tit">MerchantId</th>
									<th class="list_tit">Name</th>
									<th class="list_tit">Tel No.</th>
									<th class="list_tit">Fax No.</th>
									<th class="list_tit">Email</th>
									<th class="list_tit">Status</th>
									<th class="list_tit">Service Date</th>
									<th class="list_tit">Regist Date</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(merchantList) != 0}">
										<c:forEach var="list" items="${merchantList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list">
													<a href="/group/info.do?request=merchantView&merchantId=${list.merchantId }"><strong>${list.merchantId}</strong></a>
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
		<c:import url="/group/include/footer.jsp" />
		<!-- end footert -->
	</body>
</html>
