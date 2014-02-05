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
										<th data-sort="string">VAN</th>
										<th data-sort="string">VAN_ID</th>
										<th data-sort="string">Descriptor</th>
										<th data-sort="string">Status</th>
										<th data-sort="string">URL</th>
										<th data-sort="string">Register Date</th>
										<th>Action</th>
									</tr>
								</thead>
								
								
								
								<tbody>
								<c:choose>
									<c:when test="${fn:length(vanList) != 0}">
										<c:forEach var="list" items="${vanList}" varStatus="status">
											<tr>
												<td>${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td>${list.van }</td>
												<td>${list.vanId }</td>
												<td>${list.descriptor }</td>
												<td>${list.status }</td>
												<td>${list.url }</td>
												<td><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
												<td>
													
													<button class="btn dark  btn-sm" onclick="javascript:bootbox.alert('ID/PW=${list.adminId}/${list.adminPw}');">Access Info</button>
													
													<a class="btn red btn-sm" href="${list.url}" target="_blank">ADMIN</a>
													<button class="btn green btn-sm" onclick="javascript:getPageContent('/manage.do?request=vanEditForm&idx=${list.idx}');">Edit</button>
													
													
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

						