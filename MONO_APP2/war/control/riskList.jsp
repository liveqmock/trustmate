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
										<th data-sort="string">Idx</th>
										<th data-sort="string">Unit</th>
										<th>Status</th>
										<th>Register Reason</th>
										<th data-sort="string">Register Date</th>
										<th>Action</th>
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(trList) != 0}">
										<c:forEach var="list" items="${trList}" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td>${list.idx }&nbsp;</td>
												<td>${list.unit }&nbsp;</td>
												<td>
													<c:choose>
														<c:when test="${list.active == 'Y'}"><font color="red">BLOCK</font>
														</c:when>
														<c:when test="${list.active == 'N'}"><font color="blue">UNBLOCK</font>
														</c:when>
														<c:otherwise>
															${list.active }
														</c:otherwise>
													</c:choose>&nbsp;
												</td>
												<td>${list.comments}&nbsp;</td>
												<td><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
												<td>
													<c:if test="${list.active == 'Y'}">
													<button class="btn red btn-sm" onclick="javascript:doUnBlock('${list.idx }','N');">UNBLOCK</button>
													</c:if>
													<c:if test="${list.active == 'N'}">
													<button class="btn red btn-sm" onclick="javascript:doUnBlock('${list.idx }','Y');">BLOCK</button>
													</c:if>
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
						${paging }
						<!-- END PAGINATION -->

						