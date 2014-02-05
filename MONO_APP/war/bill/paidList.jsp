
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/include/header.jsp" />
	  
	<script type="text/javascript">
		function paidCheck(settleId,status) {
		    $.ajax({
		    	type: "POST",
		    	url : "/bill.do",
		    	data : "request=paidCheck&settleId="+settleId+"&status="+status,
		    	success : function(msg){
		    		if(msg != 'OK'){
						alert('지급 상태 변경에 실패하였습니다.');
					}else{
						alert('지급 상태 변경 완료');
						location.href ='/bill.do?request=paidList';
					}
		    	}
		    });
		    		    		    
		}
		
	</script>

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
						<form name="paidList" action="/bill.do?request=paidList" method="post">	
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="merchantId">가맹점아이디</label>
								</div>
								<div class="input">
									<input type="text" name="merchantId" id="merchantId" maxlength="16" value="${msBean.merchantId}"/>
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
									<input type="text" name="settleId" id="settleId" maxlength="22" class="text200" value="${msBean.settleId}"/>
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
									<th class="list_tit">정산유형</th>
									<th class="list_tit">기간</th>
									<th class="list_tit">회차</th>
									<th class="list_tit">거래금액</th>
									<th class="list_tit">수수료</th>
									<th class="list_tit">예치금</th>
									<th class="list_tit">정산금액</th>
									<th class="list_tit">산출일</th>
									<th class="list_tit">지급예정일</th>
									<th class="list_tit">처리상태</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(paidList) != 0}">
										<c:forEach var="list" items="${paidList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list"><a href="javascript:popup2('paidView','/bill.do?request=paidView&settleId=${list.settleId}',900,1200,100,100);showloading(false);"><strong>${list.settleId}</strong></a></td>
												<td class="list">${list.merchantId}</td>
												<td class="list">${list.period}&nbsp;</td> 
												<td class="list">${list.startDay}~${list.endDay}&nbsp;</td>
												<td class="list">${list.settleCnt}&nbsp;</td>  
												<td class="list">
													<fmt:formatNumber type="number" value="${list.totAmt}" pattern="#,###.##" />&nbsp;
												</td>
												<td class="list">
													<fmt:formatNumber type="number" value="${list.totFee}" pattern="#,###.##" />&nbsp;
												</td>
												<td class="list">
													<fmt:formatNumber type="number" value="${list.totDeposit}" pattern="#,###.##" />&nbsp;
												</td>
												<td class="list">
													<fmt:formatNumber type="number" value="${list.totSettle}" pattern="#,###.##" />&nbsp;
												</td>
												<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd" />&nbsp;</td>
												<td class="list"><fmt:formatDate type="both" value="${list.settleDate}" pattern="yyyy/MM/dd" />&nbsp;</td>
												
												<td class="list">
													<c:choose>
														<c:when test="${list.status =='R'}">
															<div class="button">
																<input type="submit" name="format" value="정산확정" onclick="javascript:paidCheck('${list.settleId}','C')"/>&nbsp;
															</div>
														</c:when>
														<c:when test="${list.status =='C'}">
															<div class="button">
																<input type="submit" name="format" value="지급완료" onclick="javascript:paidCheck('${list.settleId}','Y')"/>&nbsp;
															</div>
														</c:when>
														<c:otherwise>
															지급완료
														</c:otherwise>
													</c:choose>
												</td>  
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(paidList)) * 12}">
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

