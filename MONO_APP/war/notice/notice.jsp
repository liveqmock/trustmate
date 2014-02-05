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
	<c:import url="/include/header.jsp" />
	<!-- 상단 메인 링크 페이지 종료 -->
		<!-- content-->
		<div id="content">
			<!-- end content / left -->
			<div id="left">
				<c:import url="/include/leftMenu.jsp" />
			</div>
			<!-- end content / left -->
			<!-- content / right -->
			<div id="right">				
				<!-- table -->

				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5>NOTICE</h5>
						<ul class="links">
						<li><a href="/notice.do?request=noticeForm">WRITE</a></li>
						</ul>
					</div>
					<div class="total">
						<h7>&nbsp;</h7>
					</div>
					<!-- end box / title -->			
					<div class="table">											
						<table>
							<thead>
								<tr>
									<th class="list_tit">No</th>
									<th class="list_tit">제목</th>
									<th class="list_tit">등록자</th>
									<th class="list_tit">등록일시</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(nList) != 0}">
										<c:forEach var="list" items="${nList}" varStatus="status">
										<tr>
											<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
											<td class="list" align="left" onclick="javascript:show(${list.idx});"><font color="blue"><strong>&nbsp;${list.subject }</strong></font></td>
											<td class="list">${list.memberName }</td>
											<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
										</tr>
										<tr>
											<td class="list" valign="top" colspan="4">
												<div id="${list.idx}" style="visibility:hidden;display:none;overflow-y:scroll; width:100%; height:200;padding-left:20px">${list.contents }</div>
											</td>
										</tr>
										<tr><td colspan="4" height="1" bgcolor="#b6d9ea"></td></tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(nList)) * 16}">
											<td align="center" colspan="4"><font color="red"> NO RESULT</font></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<!-- pagination-->
						<div class="pagination pagination-left">
							${paging}
						</div> 
						<!-- end pagination -->
						
					</div>
				</div>
				<!-- end table -->
			</div>
			<!-- end content / right -->
		</div>
		<!-- end content -->
		<!-- footer -->
		<c:import url="/include/footer.jsp" />
		<!-- end footert -->
	</body>
</html>
