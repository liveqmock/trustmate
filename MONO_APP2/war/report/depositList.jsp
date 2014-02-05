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
										<th data-sort="string">Merchant ID</th>
										<th data-sort="string">Name</th>
										<th data-sort="float">Month</th>
										<th data-sort="float">Release Month</th>
										<th data-sort="float">Rate</th>
										<th data-sort="string">Currency</th>
										<th data-sort="float">Accumulated Balance</th>
										<th data-sort="float">Current Deposit</th>
										<th data-sort="float">Release Deposit</th>
										<th data-sort="float">Balance</th>
										<th data-sort="string">Settle Id</th>
										<th data-sort="string">Status</th>
										<th data-sort="string">Paid Id</th>
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(dlist) != 0}">
										<c:forEach var="list" items="${dlist}" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td>${list.merchantId }&nbsp;</td>
												<td>${list.temp1String }&nbsp;</td>
												<td><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyyMM" />&nbsp;</td>
												<td>${list.payMonth}&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.rate*100}" pattern="#,##0" />&nbsp;%</td>
												<td>${list.temp2String}&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.lastAmt}" pattern="#,##0.00" /></td>
												<td><fmt:formatNumber type="number" value="${list.curAmt}" pattern="#,##0.00" /></td>
												<td><fmt:formatNumber type="number" value="${list.payAmt}" pattern="#,##0.00" /></td>
												<td><fmt:formatNumber type="number" value="${list.totAmt}" pattern="#,##0.00" /></td> 
												<td>
													<a href="javascript:popup('paidView','/bill.do?request=paidView&settleId=${list.settleId}',900,1200,100,100);">
														${list.settleId }
													</a>
												</td>
												<td title="${list.comments }">
													<c:choose>
														<c:when test="${!empty list.paySettleId }">
															<font color="blue">Release</font>
														</c:when>
														<c:otherwise >Pending</c:otherwise>
													</c:choose>
												</td>
												<td>
													<a href="javascript:popup('paidView','/bill.do?request=paidView&settleId=${list.paySettleId}',900,1200,100,100);">
														${list.paySettleId }
													</a>
												</td>
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

						