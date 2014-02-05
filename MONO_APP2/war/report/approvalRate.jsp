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
										<th rowspan="2">No</th>
										<c:if test="${gsiBean.temp2String != '' }">
											<th data-sort="string" rowspan="2">Merchant</th>
										</c:if>
										<th data-sort="string" rowspan="2">Date</th>
										<th colspan="2">Total</th>
										<th colspan="4">Approval</th>
										<th colspan="4">Decliend</th>
									</tr>
									<tr>
										<th>Count</th>
										<th>Amount</th>
										<th>Count</th>
										<th>Amount</th>
										<th>Count Rate</th>
										<th>Amount Rate</th>
										<th>Count</th>
										<th>Amount</th>
										<th>Count Rate</th>
										<th>Amount Rate</th>
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(rateList) != 0}">
										<c:forEach var="list" items="${rateList}" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<c:if test="${gsiBean.temp2String != '' }">
													<td>${list.temp2String}&nbsp;</td>
												</c:if>
												<td>${list.temp1String }&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.temp5Double}" pattern="#,##0" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.temp6Double}" pattern="#,##0.00" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.temp1Double}" pattern="#,##0" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.temp2Double}" pattern="#,##0.00" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.temp1Double/list.temp5Double*100}" pattern="#,##0.##" />&nbsp;%</td>
												<td><fmt:formatNumber type="number" value="${list.temp2Double/list.temp6Double*100}" pattern="#,##0.##" />&nbsp;%</td>
												<td><fmt:formatNumber type="number" value="${list.temp3Double}" pattern="#,##0" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.temp4Double}" pattern="#,##0.00" />&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.temp3Double/list.temp5Double*100}" pattern="#,##0.##" />&nbsp;%</td>
												<td><fmt:formatNumber type="number" value="${list.temp4Double/list.temp6Double*100}" pattern="#,##0.##" />&nbsp;%</td>
											</tr>
										</c:forEach>
									</c:when>
									
								</c:choose>
								</tbody>
								</table>
							</div>
						</div>
						<!-- BEGIN PAGINATION -->
						<c:if test="${empty format }">
						${paging }
						<div id="pgmate-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1"></div>
						<!-- END PAGINATION -->
						</c:if>

						