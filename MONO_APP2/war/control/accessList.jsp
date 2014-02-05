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
										<th data-sort="string">IDX</th>
										<th data-sort="string">Member ID</th>
										<th data-sort="string">IPAddress</th>
										<th data-sort="string">AccessPage</th>
										<th data-sort="string">UserAgent</th>
										<th data-sort="string">Register Date</th>
									</tr>
								</thead>
								<tbody>
								<c:choose>
									<c:when test="${fn:length(accessList) != 0}">
										<c:forEach var="list" items="${accessList}" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td>${list.idx }</td>
												<td>${list.memberId }</td>
												<td>${list.ipAddress }</td>
												<td>${list.accessPage }</td>
												<td>${list.userAgent }</td>
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

						