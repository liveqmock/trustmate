<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Search Result
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:formSend('list');" class="reload"></a>
							</div>
						</div>
						<div class="portlet-body flip-scroll" id="printDiv">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover flip-content" id="sortTable"><!-- 작게 table-condensed -->
								<thead>
										<th>No</th>
										<th data-sort="float">Merchant ID</th>
										<th data-sort="string">Service Type</th>
										<th data-sort="string">Transaction Id</th>
										<th>IP Address</th>
										<th>Send</th>
										<th>Recv</th>
										<th>Status</th>
										<th data-sort="string">Date</th>
									</tr>
								</thead>
								<tbody>
					
								<c:choose>
									<c:when test="${fn:length(tLogList) != 0}">
										<c:forEach var="list" items="${tLogList}" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td>${list.merchantId }</td>
												<td>${list.serviceType }&nbsp;</td>
												<td>${list.transactionId }&nbsp;</td>
												<td>${list.ipAddress }&nbsp;</td>
												<td>
													<c:if test="${sso.memberId == 'ginaida' || sso.memberId == 'phing' }">
													${list.reqData }
													</c:if>&nbsp;
												</td>
												<td>
													<c:if test="${sso.memberId == 'ginaida' || sso.memberId == 'phing' }">
													${list.resData }
													</c:if>&nbsp;
												</td>
												<td>${list.status }&nbsp;</td>
												<td><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
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
						<div id="pgmate-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1"></div>
						<!-- END PAGINATION -->
						