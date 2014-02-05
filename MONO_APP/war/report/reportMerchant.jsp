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
						<h5><spring:message code="searchPannel" text=""/>가맹점별 통계</h5>
					</div>
					<form name="trnsctnList" action="/report.do?request=reportMerchant" method="post">
					<div class="form">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="qualification">가맹점 아이디</label>
								</div>
								<div class="input">
									<select name="merchantId">
				                   		<option value="">-----&nbsp;</option>
				                    	<c:forEach var="list" items="${merchantList}" varStatus="status">
				                    	<option value="${list.merchantId}" >${list.merchantId} [${list.name}]</option>
				                    	</c:forEach>
									</select>
									<c:if test="${!empty merchantId}"><script>document.trnsctnList.merchantId.value="${merchantId}";</script></c:if>
									
								</div>
								<div class="label2">
									<label for="day">&nbsp;</label>
								</div>
								<div class="input2">
									&nbsp;
								</div>
								
								
							</div>
							
							
						</div>
						
						<!-- table action -->
						<div class="action">
							<div class="button">
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
					
					<!-- end box / title -->					
					<div class="table">											
						<table>
							<thead>
								<tr>
									<th class="list_tit" rowspan="2">No</th>
									<th class="list_tit" rowspan="2">Merchant</th>
									<th class="list_tit" rowspan="2">Month</th>
									<th class="list_tit" colspan="2">Total</th>
									<th class="list_tit" colspan="2">Normal</th>
									<th class="list_tit" colspan="2">Refund</th>
									<th class="list_tit" colspan="2">Chargeback</th>
									<th class="list_tit" colspan="2">Refund Rate</th>
									<th class="list_tit" colspan="2">C*B Rate</th>
								</tr>
								<tr>
									
									<th class="list_tit">Count</th>
									<th class="list_tit">Amount</th>
									<th class="list_tit">Count</th>
									<th class="list_tit">Amount</th>
									<th class="list_tit">Count</th>
									<th class="list_tit">Amount</th>
									<th class="list_tit">Count</th>
									<th class="list_tit">Amount</th>
									<th class="list_tit">Count</th>
									<th class="list_tit">Amount</th>
									<th class="list_tit">Count</th>
									<th class="list_tit">Amount</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(reportList) != 0}">
										<c:forEach var="list" items="${reportList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list">${list.merchantId}&nbsp;[${list.name}]&nbsp;</td>
												<td class="list">${list.mon}&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.totCnt}" pattern="#,###" />&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.totAmt}" pattern="#,###.##" />&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.sucCnt}" pattern="#,###" />&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.sucAmt}" pattern="#,###.##" />&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.refundCnt}" pattern="#,###" />&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.refundAmt}" pattern="#,###.##" />&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.cbCnt}" pattern="#,###" />&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.cbAmt}" pattern="#,###.##" />&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.refundRateByCnt * 100}" pattern="#,###.##" />%&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.refundRateByAmt * 100}" pattern="#,###.##" />%&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.cbRateByCnt * 100}" pattern="#,###.##" />%&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.cbRateByAmt * 100}" pattern="#,###.##" />%&nbsp;</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(reportList)) * 12}">
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

