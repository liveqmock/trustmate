<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
	
	function view(type, transactionId){
		showloading(false);
		if(type == '1'){
			window.open('/merchant/trnsctn.do?request=view&transactionId='+transactionId,'','width=900,height=580,left=100,top=100');
		}else{
			window.open("/merchant/trnsctn.do?request=view&transactionId="+transactionId,"","width=900,height=490,left=100,top=100");
		}
	}
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
				<!-- forms -->
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5>Collect</h5>
					</div>
					<form name="acquireList" action="/merchant/report.do?request=acquire" method="post">
					<div class="form">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="day">Transaction Id</label>
								</div>
								<div class="input">
									<input type="text" name="transactionId" id="transactionId" maxlength="16" class="transactionId" value="${trnsctnBean.transactionId}"/>
								</div>
								
								<div class="label2">
									<label for="qualification">Status </label>
								</div>
								<div class="input2">
									<select name="status">
									    <option value="">-----&nbsp;</option>
									    <option value="00">Collected</option>
									    <option value="11">Failure of collect</option>
									    <option value="22">Collect restored</option>
									    <option value="33">Retry of collect</option>
									    <option value="44">Chargeback</option>
									</select>
									<c:if test="${!empty trnsctnAcquireBean.status}"><script>document.acquireList.status.value="${trnsctnAcquireBean.status}"</script></c:if>
								</div>
							</div>
							
							<div class="field">
								<div class="label">
									<label for="day">Period</label>
								</div>
								<div class="input">
									<input type="text" id="startDate" name="startDate" class="date" value="${startDate}" onchange="onlyDate2(this);"/>
									<span class="m">-</span>
									<input type="text" id="endDate" name="endDate" class="date" value="${endDate }" onchange="onlyDate2(this);"/>
								</div>
								<div class="label2">
									<label for="">Search count</label>
								</div>
								<div class="input2">
									<select name="pageSize">
					              		<option value="50" <c:if test="${dao.pageSize == 50}">selected</c:if>>50</option>
					                	<option value="100" <c:if test="${dao.pageSize == 100}">selected</c:if>>100</option>
					                	<option value="200" <c:if test="${dao.pageSize == 200}">selected</c:if>>200</option>
					                	<option value="500" <c:if test="${dao.pageSize == 500}">selected</c:if>>500</option>
					                </select>
								</div>
								
							</div>
							
						</div>
						
						<!-- table action -->
						<div class="action">
							<div class="button">
								<input type="submit" name="format" value="excel"/>&nbsp;
								<input type="submit" name="submit" value="search"/>
							</div>
						</div>
						<!-- end table action -->			
					</div>
					</form>
				</div>
				<!-- end forms -->
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5><strong>Search result</strong></h5>
					</div>
					<div class="total">
						<h7>
							Search [ Count : 
			        		<font color="blue"><fmt:formatNumber type="number" value="${dao.totalCount}" pattern="#,##0" /></font>]&nbsp;
						</h7>
					</div>
					<!-- end box / title -->					
					<div class="table">											
						<table>
							<thead>
								<tr>
									<th class="list_tit">No</th>
									<th class="list_tit">Transaction Id</th>
									<th class="list_tit">Status</th>
									<th class="list_tit">Collect Date</th>
									<th class="list_tit">Amount(USD)</th>
									<th class="list_tit">Card Error</th>
									<th class="list_tit">Van Error</th>
									<th class="list_tit">Request Date</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(acquireList) != 0}">
										<c:forEach var="list" items="${acquireList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list"><strong>${list.transactionId}</strong></td>
												<td class="list"><c:choose>
													<c:when test="${list.status == '00'}"><font color="blue">Collected</font>
													</c:when>
													<c:when test="${list.status == '11'}"><font color="blue">Failure of collect</font>
													</c:when>
													<c:when test="${list.status == '22'}"><font color="blue">Collect restored</font>
													</c:when>
													<c:when test="${list.status == '33'}"><font color="red">Retry of collect</font>
													</c:when>
													<c:when test="${list.status == '44'}"><font color="red">Chargeback</font>
													</c:when>
													<c:otherwise>
														${list.trnStatus }
													</c:otherwise>
												</c:choose>	
												</td>
												<td class="list">${list.acquireDate}&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" /></td>
												<td class="list">${list.cardErrCd}&nbsp;</td>
												<td class="list">${list.vanErrCd}</td>
												<td class="list"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(acquireList)) * 13}">
								    			<td align="center" class="list" colspan="8">&nbsp;NO RESULT</td>
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

