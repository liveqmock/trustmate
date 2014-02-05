<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/include/header.jsp" />
<script type="text/javascript">
	$(document).ready(function () {
	
		$("select").selectmenu({
	        style: 'dropdown',
	        width: 350,
	        menuWidth: 350,
	        icons: [
			    { find: '.locked', icon: 'ui-icon-locked' },
			    { find: '.unlocked', icon: 'ui-icon-unlocked' },
			    { find: '.folder-open', icon: 'ui-icon-folder-open' }
		    ]
	    });
    });
	
	
	function view(type, idx){
		showloading(false);
		if(type == '1'){
			window.open('/report.do?request=invoice2&invoiceIdx='+idx,'','width=730,height=760,left=100,top=100');
		}else{
			window.open("/report.do?request=invoice2&invoiceIdx="+idx,"","width=730,height=440,left=100,top=100");
		}
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
						<h5>청구서확인</h5>
					</div>
					
					<div class="form">
						<form name="invoiceList" action="/bill.do?request=invoice" method="post">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="merchantId">가맹점아이디</label>
								</div>
								<div class="input">
									 <c:choose>
									<c:when test="${fn:length(merchantList) != 0}">
				              			<select name="merchantId" id="select" tabindex="1" style="width:350px;" onchange="javascript:document.invoiceList.submit();">
				              				<option value="">-----&nbsp;</option>
				              				<c:forEach var="list" items="${merchantList}" varStatus="status">
				              					<option value="${list.merchantId }" <c:if test="${list.merchantId == merchantId}">selected</c:if>>${list.merchantId} [${list.name }]</option>
				              				</c:forEach>
										</select>
									</c:when>
									<c:otherwise>
										<input name="merchantId" type="text"  maxlength="16" tabindex="1" value="${mBean.merchantId}" />
									</c:otherwise>
								</c:choose>
								</div>
								<div class="label2">
									<label for="qualification">&nbsp;</label>
								</div>
								<div class="input2">&nbsp;
								</div>
								
							</div>
							<!--  
							<div class="field">
								<div class="label">
									<label for="invoiceIdx">청구서번호</label>
								</div>
								<div class="input">
									<c:choose>
										<c:when test="${fn:length(sList) != 0}">
					               			<select name="invoiceIdx" tabindex="3" style="width:350px;">
					               				<option value="">-----&nbsp;</option>
					               				<c:forEach var="list" items="${sList}" varStatus="status">
					               					<option value="${list.invoiceIdx }" <c:if test="${list.invoiceIdx == sBean.invoiceIdx}">selected</c:if>>${list.invoiceIdx }&nbsp;[${list.temp1String }&nbsp; ~ &nbsp;${list.temp2String }]</option>
					               				</c:forEach>
											</select>
										</c:when>
										<c:otherwise>
											<input name="invoiceIdx" type="text" class="small valid" maxlength="50" tabindex="3" value="" style="width:350px;"/>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="label2">
									<label for="qualification">&nbsp;</label>
								</div>
								<div class="input2">&nbsp;
								</div>
								
							</div>
							-->
						</div>
						
						<!-- table action -->
						<div class="action">
							<div class="button">
								<input type="submit" name="submit" value="search"/>
							</div>
						</div>
						<!-- end table action -->			
						</form>
					</div>
					
					<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5><strong>Search result</strong></h5>
					</div>
					<div class="total">
						<h7></h7>
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
									<th class="list_tit">excel</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(settleList) != 0}">
										<c:forEach var="list" items="${settleList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list"><a href="javascript:popup2('paidView','/bill.do?request=paidView&settleId=${list.settleId}',900,1200,100,100);showloading(false);"><strong>${list.settleId}</strong></a></td>
												<td class="list">${list.merchantId}</td>
												<td class="list">${list.period}&nbsp;</td> 
												<td class="list">${list.startDay}~${list.endDay}&nbsp;</td>
												<td class="list">${list.settleCnt}&nbsp;</td>  
												<td class="list">
													<fmt:formatNumber type="number" value="${list.totAmt}" pattern="#,##0" />&nbsp;
												</td>
												<td class="list">
													<fmt:formatNumber type="number" value="${list.totFee}" pattern="#,##0" />&nbsp;
												</td>
												<td class="list">
													<fmt:formatNumber type="number" value="${list.totDeposit}" pattern="#,##0" />&nbsp;
												</td>
												<td class="list">
													<fmt:formatNumber type="number" value="${list.totSettle}" pattern="#,##0" />&nbsp;
												</td>
												<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd" />&nbsp;</td>
												<td class="list"><fmt:formatDate type="both" value="${list.settleDate}" pattern="yyyy/MM/dd" />&nbsp;</td>
												
												<td class="list">
													
														<c:if test="${list.status =='C'}">
															정산확정
														</c:if>
														<c:if test="${list.status =='Y'}">
															지급완료
														</c:if>
														
												</td>  
												<td class="list">
													<a href="/bill.do?request=invoiceExcel&settleId=${list.settleId }"><input type="button" name="Excel" value="Excel" onclick="javascript:showloading(false);"/></a>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(settleList)) * 12}">
								    			<td align="center" class="list" colspan="14">&nbsp;NO RESULT</td>
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
					
					
					
				</div>
				<!-- end forms 
				<div class="box">
					<div style="height: 800px;"></div>
				</div>
				-->
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

