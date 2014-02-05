<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

						<c:if test="${empty format }">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Search Result &nbsp;<small><fmt:formatNumber type="number" value="${dao.totalCount}" pattern="#,##0" /> </small>
							</div>
							<div class="tools">
								<a href="javascript:printDiv('printDiv');" class="btn blue btn-sm"><i class="fa fa-print"></i>&nbsp;Print</a>
								<a href="javascript:formSend('excel');" class="btn blue btn-sm"><i class="fa fa-file-text-o"></i>&nbsp;Excel</a>
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:formSend('list');" class="reload"></a>
							</div>
						</div>
						</c:if>
						<div class="portlet-body flip-scroll" id="printDiv">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover flip-content" id="sortTable"><!-- 작게 table-condensed -->
								<thead>
									<tr>
										<th>No</th>
										<th data-sort="string">Settle ID</th>
										<th data-sort="string">Type</th>
										<th data-sort="string">Period</th>
										<th data-sort="string">Week</th>
										<th data-sort="float">Total Amt</th>
										<th data-sort="float">Fee</th>
										<th data-sort="float">Deposit</th>
										<th data-sort="float">Settle Amt</th>
										<th data-sort="string">Create Date</th>
										<th data-sort="string">Settle Date</th>
										<th data-sort="string">Status</th>
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(settleList) != 0}">
										<c:forEach var="list" items="${settleList}" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<c:if test="${empty format }">
												<td><a href="javascript:popup('paidView','/bill.do?request=paidView&settleId=${list.settleId}',900,1200,100,100);">${list.settleId }</a>&nbsp;</td>
												</c:if>
												<c:if test="${!empty format }">
												<td>${list.settleId }&nbsp;</td>
												</c:if>
												<td>${list.period}&nbsp;</td>
												<td>${list.startDay}~${list.endDay}&nbsp;</td>
												<td>${list.settleCnt}&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.totAmt}" pattern="#,##0.00" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.totFee}" pattern="#,##0.00" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.totDeposit}" pattern="#,##0.00" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.totSettle}" pattern="#,##0.00" />&nbsp;</td>
												<td><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd" />&nbsp;</td>
												<td><fmt:formatDate type="both" value="${list.settleDate}" pattern="yyyy/MM/dd" />&nbsp;</td> 
												<td>
													<c:choose>
														<c:when test="${list.status =='R'}">Created</c:when>
														<c:when test="${list.status =='C'}">Fixed</c:when>
														<c:otherwise>Paid</c:otherwise>
													</c:choose>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									
								</c:choose>
								</tbody>
								</table>
							</div>
						</div>
						<c:if test="${empty format }">
						<div id="pgmate-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1"></div>
						<!-- BEGIN PAGINATION -->
						${paging }
						<!-- END PAGINATION -->
						</c:if>

						