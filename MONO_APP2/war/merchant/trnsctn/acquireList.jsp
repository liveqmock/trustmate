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
								<a href="javascript:formSend('excel');" class="btn blue btn-sm"><i class="fa fa-file-text-o"></i>&nbsp;Excel</a>
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:formSend('list');" class="reload"></a>
							</div>
						</div>
						<div class="portlet-body flip-scroll" id="printDiv">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover flip-content" id="sortTable"><!-- 작게 table-condensed -->
								<thead>
									<tr>
										<th>No</th>
										<th data-sort="float">Transaction ID</th>
										<th data-sort="string">Status</th>
										<th data-sort="string">Acquiring Date</th>
										<th data-sort="float">Amount</th>
										<th data-sort="string">Card Error</th>
										<th data-sort="string">Van Error</th>
										<th data-sort="string">Van</th>
										<th data-sort="string">Send Date</th>
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(acquireList) != 0}">
										<c:forEach var="list" items="${acquireList}" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td>${list.transactionId }&nbsp;</td>
												<td>
													<c:choose>
														<c:when test="${list.status == '00'}"><font color="blue">Acquired</font>
														</c:when>
														<c:when test="${list.status == '11'}"><font color="blue">Acquiring Failed</font>
														</c:when>
														<c:when test="${list.status == '22'}"><font color="blue">Acquiring Reversed</font>
														</c:when>
														<c:when test="${list.status == '33'}"><font color="red">Re/Acquiring</font>
														</c:when>
														<c:when test="${list.status == '44'}"><font color="red">Chargeback</font>
														</c:when>
														<c:otherwise>
															${list.status }
														</c:otherwise>
													</c:choose>
												</td>
												<td>${list.acquireDate}&nbsp;</td>
												<td><fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" />&nbsp;</td>
												<td>${list.cardErrCd}&nbsp;</td>
												<td>${list.vanErrCd}&nbsp;</td> 
												<td>${list.van}&nbsp;</td>
												<td><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
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

						