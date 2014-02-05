<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Search Result &nbsp; <small><fmt:formatNumber type="number" value="${dao.totalCount}" pattern="#,##0" /> </small>
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
										<th data-sort="string">Merchant ID</th>
										<th data-sort="string">Name</th>
										<th>Contact No.</th>
										<th data-sort="string">Fax No.</th>
										<th data-sort="string">E-Mail</th>
										<th data-sort="string">Status</th>
										<th data-sort="string">Service Date</th>
										<th data-sort="string">Create Date</th>
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(mList) != 0}">
										<c:forEach var="list" items="${(mList) }" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td><a href="javascript:getPageContent('/member.do?request=view&merchantId=${list.merchantId}');">${list.merchantId}</a></td>
												<td>${list.name }&nbsp;</td>
												<td>${list.telNo }&nbsp;</td>
												<td>${list.fax }&nbsp;</td>
												<td>${list.email }&nbsp;</td>
												<td>
													<c:choose>
														<c:when test="${list.active == '0'}"><font color="#800080">READY</font>
														</c:when>
														<c:when test="${list.active == '1'}"><font color="blue">ACTIVE</font>
														</c:when>
														<c:when test="${list.active == '2'}"><font color="green">INACTIVE</font>
														</c:when>
														<c:when test="${list.active == '3'}"><font color="red">TERMINATED</font>
														</c:when>
														<c:otherwise>
															${list.active }
														</c:otherwise>
													</c:choose>
												</td>
												<td><fmt:formatDate type="both" value="${list.serviceDate}" pattern="yyyy/MM/dd" /></td>
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
						
						
						