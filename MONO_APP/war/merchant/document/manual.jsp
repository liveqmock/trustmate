<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/merchant/include/header.jsp" />
	
	<!-- 상단 메인 링크 페이지 종료 -->
		<!-- content-->
		<div id="content">
			<!-- end content / left -->
			<div id="left">
				<c:import url="/merchant/include/leftMenu.jsp" />
			</div>
			<!-- end content / left -->
			<!-- content / right -->
			<div id="right">				
				<!-- table -->
				
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5><strong>NOTICE</strong></h5>
					</div>
					<!-- end box / title -->					
					<div class="table">											
						<table>
							<thead>
								<tr>
									<th class="list_tit">No</th>
									<th class="list_tit">Document Type</th>
									<th class="list_tit">Subject</th>
									<th class="list_tit">Link</th>
								</tr>
							</thead>
							<tbody>
							<c:choose>
								<c:when test="${fn:length(list) != 0}">
								<c:forEach var="list" items="${list}" varStatus="status">
								<tr>
									<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
									<td class="list">${list.docType }</td>
									<td class="list">${list.title }</td>
									<td class="list"><a href="${list.location}" target="_blank"><font color="blue">Download</font></a></td>
								</tr>
								</c:forEach>
								</c:when>
								<c:otherwise>
									<tr height="${(50-fn:length(list)) * 12}">
							    			<td align="center" class="list" colspan="4">&nbsp;NO RESULT</td>
							    	</tr>
								</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<!-- pagination-->
						<div class="pagination pagination-left">
							${paging}
						</div> 
						
						
					</div>
				</div>
				<div class="box">
					<div style="height: 750px;"></div>
				</div>
			</div>
				<!-- end table -->
			</div>
			<!-- end content / right -->
		</div>
		<!-- end content -->
		<!-- footer -->
		<c:import url="/merchant/include/footer.jsp" />
		<!-- end footert -->
	</body>
</html>

