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
						<h5><spring:message code="searchPannel" text=""/>Transaction Log</h5>
					</div>
					
					<div class="form">
						<form name="trnsctnLogList" action="/trnsctn.do?request=log" method="post">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="type">서비스 유형</label>
								</div>
								<div class="input">
									<select name="serviceType">
					              		<option value="">-----&nbsp;</option>
									    <option value="WEB">WEB</option>
									    <option value="MOBILE">MOBILE</option>
									    <option value="TERMINAL">TERMINAL</option>
									</select>
					                <c:if test="${!empty tLogBean.serviceType}"><script>document.trnsctnList.serviceType.value="${tLogBean.serviceType}"</script></c:if>
								</div>
								<div class="label2">
									<label for="day">기간</label>
								</div>
								<div class="input2">
									<input type="text" id="startDate" name="startDate" class="date" value="${startDate}" onchange="onlyDate2(this);"/>
									<span class="m">-</span>
									<input type="text" id="endDate" name="endDate" class="date" value="${endDate }" onchange="onlyDate2(this);"/>
								</div>
							</div>
							<div class="field">
								<div class="label">
									<label for="cardnum">가맹점아이디</label>
								</div>
								<div class="input">
									<input type="text" name="merchantId" value="${tLogBean.merchantId}" />
									
								</div>
								
								<div class="label2">
									<label for="transactionId">거래번호</label>
								</div>
								<div class="input2" >
									<input type="text" name="transactionId" value="${tLogBean.transactionId}" />
								</div>
								
							</div>
							<div class="field">
								<div class="label">
									<label for="ipAddress">IP ADDRESS</label>
								</div>
								<div class="input">
									<input type="text" name="ipAddress" value="${tLogBean.ipAddress}" />
								</div>
								
								<div class="label2">
									<label for="">검색건수</label>
								</div>
								<div class="input2">
									<select name="pageSize" class="input_select">
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
								<input type="submit" name="format" value="excel" />&nbsp;
								<input type="submit" name="submit" value="search" />
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
						<h5><strong>Search result</strong></h5>
					</div>
					<div class="total">
						<h7>
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
									<th class="list_tit">서비스유형</th>
									<th class="list_tit">거래번호</th>
									<th class="list_tit">IP Address</th>
									<th class="list_tit">송신전문</th>
									<th class="list_tit">수신전문</th>
									<th class="list_tit">상태</th>
									<th class="list_tit">일시</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(tLogList) != 0}">
										<c:forEach var="list" items="${tLogList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list">${list.merchantId }&nbsp;</td>
												<td class="list">${list.serviceType }&nbsp;</td>
												<td class="list">${list.transactionId }&nbsp;</td>
												<td class="list">${list.ipAddress }&nbsp;</td>
												<td class="list" onmousemove="javascript:msgmove('${status.count}');" onmouseover="javascript:msgset('${status.count}','${list.reqData }');return true;" onmouseout="javascript:msghide('${status.count}');return true;">
													<strong>view&nbsp;<div id="${status.count}" style="position:absolute;left:0;top:0;width:0;height:0;"></div></strong>
												</td>
												<td class="list" onmousemove="javascript:msgmove('${status.count}-1');" onmouseover="javascript:msgset('${status.count}-1','${list.resData }');return true;" onmouseout="javascript:msghide('${status.count}-1');return true;">
													<strong>view&nbsp;<div id="${status.count}-1" style="position:absolute;left:0;top:0;width:0;height:0;"></div></strong>
												</td>
												<td class="list">${list.status }&nbsp;</td>
												<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(tLogList)) * 12}">
								    			<td align="center" class="list" colspan="13">&nbsp;NO RESULT</td>
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
