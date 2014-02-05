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
								<c:if test="${!empty currentTrnsctnBean }">
								<p class="label">
								Month :&nbsp;<fmt:formatNumber type="number" value="${currentTrnsctnBean.monthCount}" pattern="#,##0" />/<fmt:formatNumber type="number" value="${currentTrnsctnBean.monthSum}" pattern="#,##0.00" /> 
								&nbsp;&nbsp;  
								Search :&nbsp;<fmt:formatNumber type="number" value="${dao.totalCount}" pattern="#,##0" />/<fmt:formatNumber type="number" value="${searchAmount}" pattern="#,##0.00" /> </p>
								</c:if>
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
										<th data-sort="float">Transaction ID</th>
										<th data-sort="string">Merchant ID</th>
										<th data-sort="string">Order No</th>
										<th data-sort="string">Status</th>
										<th data-sort="string">Currency</th>
										<th data-sort="float">Amount</th>
										<th data-sort="float">Card No</th>
										<th data-sort="string">Brand</th>
										<th data-sort="string">Approv No</th>
										<th data-sort="string">Request Date</th>
										<th data-sort="string">Response Date</th>
										
										
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(trnsctnList) != 0}">
										<c:forEach var="list" items="${trnsctnList}" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td style='mso-number-format:"\@";'>
													<c:if test="${empty format }">
														<a href="javascript:trnsctnView('${list.transactionId}');"><strong>${list.transactionId}</strong></a>
													</c:if>
													<c:if test="${!empty format }">
														${list.transactionId}
													</c:if>
												</td>
												<td>${list.merchantId }&nbsp;</td>
												<td>${list.payNo }&nbsp;</td>
												<td>
													<c:choose>
														<c:when test="${list.trnStatus == '00'}">On Approval
														</c:when>
														<c:when test="${list.trnStatus == '01'}">Error on approval
														</c:when>
														<c:when test="${list.trnStatus == '02'}">Approved
														</c:when>
														<c:when test="${list.trnStatus == '03'}">
															<c:if test="${empty format }">
															<a href="javascript:getResultMsg('${list.resultMsg}');" >Declined</a>
															</c:if>
															<c:if test="${!empty format }">
																Declined
															</c:if>
														</c:when>
														<c:when test="${list.trnStatus == '04'}">Request Cancel
														</c:when>
														<c:when test="${list.trnStatus == '05'}">Error on cancel
														</c:when>
														<c:when test="${list.trnStatus == '06'}">Canceled
														</c:when>
														<c:when test="${list.trnStatus == '07' || list.trnStatus == '08'}">Acquiring
														</c:when>
														<c:when test="${list.trnStatus == '09' || list.trnStatus == '10'}">Acquired
														</c:when>
														<c:when test="${list.trnStatus == '11' || list.trnStatus == '12'}">Ready for paid
														</c:when>
														<c:when test="${list.trnStatus == '13'}">Paid
														</c:when>
														<c:when test="${list.trnStatus == '14'}">Refund
														</c:when>
														<c:when test="${list.trnStatus == '15'}">Request Refund
														</c:when>
														<c:when test="${list.trnStatus == '17'}">Ready for cancel
														</c:when>
														<c:when test="${list.trnStatus == '18' || list.trnStatus == '19' || list.trnStatus == '20'}">Refunded
														</c:when>
														<c:when test="${list.trnStatus == '21'}">Acquiring Failed
														</c:when>
														<c:when test="${list.trnStatus == '22' || list.trnStatus == '23'}">Chargeback
														</c:when>
														<c:otherwise>
															${list.trnStatus }
														</c:otherwise>
													</c:choose>
												</td>
												<td>${list.curType}&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" /></td>
												<td>${list.temp1String}&nbsp;</td> <!-- 카드번호 -->
												<td>${list.temp2String}&nbsp;</td> <!-- 카드종류 --> 
												<td>
													<c:if test="${list.approvalNo != ''}">
														${list.approvalNo}&nbsp;
													</c:if>
													<c:if test="${list.approvalNo == '' || empty list.approvalNo}">
														${list.resultMsg}&nbsp;
													</c:if>
												</td>
												<td><fmt:formatDate type="both" value="${list.trnReqDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
												<td><fmt:formatDate type="both" value="${list.trnResDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
											</tr>
										</c:forEach>
									</c:when>
									
								</c:choose>
								</tbody>
								</table>
							</div>
						</div>
						<c:if test="${empty format }">
						<!-- BEGIN PAGINATION -->
						${paging }
						<div id="pgmate-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1"></div>
						<!-- END PAGINATION -->
						</c:if>

						