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
										<th data-sort="string">Type</th>
										<th data-sort="string">Target</th>
										<th data-sort="string">Code</th>
										<th data-sort="string">Message</th>
										<th data-sort="string">Register</th>
										<th data-sort="string">Register Date</th>
										<th>Action</th>
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(alertList) != 0}">
										<c:forEach var="list" items="${alertList}" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td>
													<c:choose>
														<c:when test="${list.alertType == 'ALL'}"><font color="red">ALL</font>
														</c:when>
														<c:when test="${list.alertType == 'SMS'}"><font color="blue">SMS</font>
														</c:when>
														<c:when test="${list.alertType == 'EMAIL'}"><font color="blue">EMAIL</font>
														</c:when>
														<c:otherwise>
															${list.alertType }
														</c:otherwise>
													</c:choose>&nbsp;
												</td>
												<td>
													<c:choose>
														<c:when test="${list.target == 'ALL'}"><font color="red">ALL</font>
														</c:when>
														<c:when test="${list.target == 'SYSTEM'}"><font color="blue">SYSTEM</font>
														</c:when>
														<c:when test="${list.target == 'ADMIN'}"><font color="blue">ADMIN</font>
														</c:when>
														<c:when test="${list.target == 'NONE'}"><font color="blue">NONE</font>
														</c:when>
														<c:otherwise>
															${list.target }
														</c:otherwise>
													</c:choose>&nbsp;
												</td>
												<td>[${list.resultMsg }]&nbsp;${list.msg }&nbsp;</td>
												<td>${list.message }&nbsp;</td>
												<td>${list.regId }&nbsp;</td>
												<td><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
												<td>
													<button class="btn red btn-sm" onclick="javascript:deleteAlert('${list.alertType}','${list.resultMsg}','${list.target}','${list.regId }');">DELETE</button>
													
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

						