<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Search Result &nbsp;<small><fmt:formatNumber type="number" value="${dao.totalCount}" pattern="#,##0" /> </small>
							</div>
							<div class="tools">
								<a href="javascript:printDiv('printDiv');" class="btn blue btn-sm"><i class="fa fa-print"></i>&nbsp;Print</a>
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:formSend('list');" class="reload"></a>
							</div>
						</div>
						<div class="portlet-body flip-scroll" id="printDiv">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover flip-content" id="sortTable"><!-- 작게 table-condensed -->
								<thead>
									<tr>
										<th rowspan="2">No</th>
										<th data-sort="string" rowspan="2">Merchant</th>
										<th data-sort="string" rowspan="2">Month</th>
										<th colspan="2">Total</th>
										<th colspan="2">Normal</th>
										<th colspan="2">Refund</th>
										<th colspan="2">Chargeback</th>
										<th colspan="2">Refund Rate</th>
										<th colspan="2">C*B Rate</th>
									</tr>
									<tr>
										<th>Count</th>
										<th>Amount</th>
										<th>Count</th>
										<th>Amount</th>
										<th>Count</th>
										<th>Amount</th>
										<th>Count</th>
										<th>Amount</th>
										<th>Count</th>
										<th>Amount</th>
										<th>Count</th>
										<th>Amount</th>
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(reportList) != 0}">
										<c:forEach var="list" items="${reportList}" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td>${list.merchantId}&nbsp;[${list.name}]&nbsp;</td>
												<td>${list.mon }&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.totCnt}" pattern="#,##0" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.totAmt}" pattern="#,##0.00" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.sucCnt}" pattern="#,##0" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.sucAmt}" pattern="#,##0.00" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.refundCnt}" pattern="#,##0" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.refundAmt}" pattern="#,##0.00" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.cbCnt}" pattern="#,##0" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.cbAmt}" pattern="#,##0.00" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.refundRateByCnt * 100}" pattern="#,##0.##" />&nbsp;%</td>
												<td><fmt:formatNumber type="number" value="${list.refundRateByAmt * 100}" pattern="#,##0.##" />&nbsp;%</td>
												<td><fmt:formatNumber type="number" value="${list.cbRateByCnt * 100}" pattern="#,##0.##" />&nbsp;%</td>
												<td><fmt:formatNumber type="number" value="${list.cbRateByAmt * 100}" pattern="#,##0.##" />&nbsp;%</td>
											</tr>
										</c:forEach>
									</c:when>
									
								</c:choose>
								</tbody>
								</table>
							</div>
						</div>
						<!-- BEGIN PAGINATION -->
						${paging }
						<!-- END PAGINATION -->

						