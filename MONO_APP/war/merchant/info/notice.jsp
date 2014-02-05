<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
<!--
	function show(id) {
		var doc = document.getElementById(id);
		if(doc.style.display == 'none'){
			doc.style.visibility ='';
			doc.style.display = '';
		}else{
			doc.style.visibility ='hidden';
			doc.style.display = 'none';
		}
		return;
	}	
	
//-->
</script>
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
									<th class="list_tit">SUBJECT</th>
									<th class="list_tit">NAME</th>
									<th class="list_tit">DATE</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(nList) != 0}">
										<c:forEach var="list" items="${nList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list" onclick="javascript:show(${list.idx});"><strong>${list.subject}</strong></td>
												<td class="list"><strong>${list.memberName}</strong></td>
												<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
											</tr>
											<tr>
												<td height="5" valign="top" colspan="4" class="list">
													<div id="${list.idx}" style="visibility:hidden;display:none;overflow-y:scroll; width:100%; height:200;padding-left:20px">${list.contents }</div>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(nList)) * 17}">
								    			<td align="center" class="list" colspan="4">&nbsp;NO RESULT</td>
								    	<tr>
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

