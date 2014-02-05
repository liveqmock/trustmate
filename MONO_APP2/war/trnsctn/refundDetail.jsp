<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

						<c:if test="${empty format }">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Search Result
							</div>
							<div class="tools">
								<p class="label">
								Search :&nbsp;<fmt:formatNumber type="number" value="${dao.totalCount}" pattern="#,##0" />/<fmt:formatNumber type="number" value="${searchAmount}" pattern="#,##0.00" /> </p>
								
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
										<th data-sort="float">Refund ID</th>
										<th data-sort="float">Transaction ID</th>
										<th data-sort="string">Merchant ID</th>
										<th data-sort="string">Order No</th>
										<th data-sort="string">Status</th>
										<th data-sort="string">Previous Status</th>
										<th data-sort="float">Amount</th>
										<th data-sort="float">Card No</th>
										<th data-sort="string">Approv No</th>
										<th data-sort="string">Request Date</th>
										<th data-sort="string">Response Date</th>
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(voidList) != 0}">
										<c:forEach var="list" items="${voidList}" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td style='mso-number-format:"\@";'>${list.voidTransactionId }&nbsp;</td>
												<td style='mso-number-format:"\@";'>
													<c:if test="${empty format }">
														<a href="javascript:trnsctnView('${list.transactionId}');"><strong>${list.transactionId}</strong></a>
													</c:if>
													<c:if test="${!empty format }">
														${list.transactionId}
													</c:if>
												</td>
												<td>${list.temp1String }&nbsp;</td>
												<td style='mso-number-format:"\@";'>${list.temp5String }&nbsp;</td>
												<td>
													<c:choose>
														<c:when test="${list.status == '00'}">
															<c:if test="${list.rootTrnStatus == '02'}">
															Canceled
															</c:if>
															<c:if test="${list.rootTrnStatus != '02'}">
															<b>Refunded</b>
															</c:if>
														</c:when>
														<c:when test="${list.status == '11'}">Accept Refund</c:when>
														<c:when test="${list.status == '33'}">Request Refund</c:when>
														<c:when test="${list.status == '99'}">Error on Refund</c:when>
														<c:otherwise>
															${list.status }
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${list.rootTrnStatus == '00'}">On Approval
														</c:when>
														<c:when test="${list.rootTrnStatus == '01'}">Error on approval
														</c:when>
														<c:when test="${list.rootTrnStatus == '02'}">Approved
														</c:when>
														<c:when test="${list.rootTrnStatus == '03'}">
															<c:if test="${empty format }">
															<a href="javascript:getResultMsg('${list.resultMsg}');" >Declined</a>
															</c:if>
															<c:if test="${!empty format }">
																Declined
															</c:if>
														</c:when>
														<c:when test="${list.rootTrnStatus == '04'}">Request Cancel
														</c:when>
														<c:when test="${list.rootTrnStatus == '05'}">Error on cancel
														</c:when>
														<c:when test="${list.rootTrnStatus == '06'}">Canceled
														</c:when>
														<c:when test="${list.rootTrnStatus == '07' || list.rootTrnStatus == '08'}">Acquiring
														</c:when>
														<c:when test="${list.rootTrnStatus == '09' || list.rootTrnStatus == '10'}">Acquired
														</c:when>
														<c:when test="${list.rootTrnStatus == '11' || list.rootTrnStatus == '12'}">Ready for paid
														</c:when>
														<c:when test="${list.rootTrnStatus == '13'}">Paid
														</c:when>
														<c:when test="${list.rootTrnStatus == '14'}">Refund
														</c:when>
														<c:when test="${list.rootTrnStatus == '15'}">Request Refund
														</c:when>
														<c:when test="${list.rootTrnStatus == '17'}">Ready for cancel
														</c:when>
														<c:when test="${list.rootTrnStatus == '18' || list.rootTrnStatus == '19' || list.rootTrnStatus == '20'}">Refunded
														</c:when>
														<c:when test="${list.rootTrnStatus == '21'}">Acquiring Failed
														</c:when>
														<c:when test="${list.rootTrnStatus == '22' || list.rootTrnStatus == '23'}">Chargeback
														</c:when>
														<c:otherwise>
															${list.rootTrnStatus }
														</c:otherwise>
													</c:choose>
												</td>
												<td><fmt:formatNumber type="number" value="${list.voidAmount}" pattern="#,##0.00" /></td>
												<td>${list.temp3String}&nbsp;</td> <!-- 카드번호 -->
												<td>${list.voidApprovalNo}&nbsp;</td>
												<td><fmt:formatDate type="both" value="${list.voidReqDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
												<td><fmt:formatDate type="both" value="${list.voidResDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
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

						