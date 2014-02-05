<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
	
function view(transactionId){
	showloading(false);
	window.open("/merchant/trnsctn.do?request=view&transactionId="+transactionId,"","width=730,height=520,left=100,top=100");
	
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
						<h5>Transaction</h5>
					</div>
					<form name="trnsctnList" action="/merchant/trnsctn.do?request=list" method="post">
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
									<label for="qualification">Merchant Ref. </label>
								</div>
								<div class="input2">
									<input type="text" name="payNo" id="payNo" maxlength="16" class="payNo" value="${trnsctnBean.payNo}"/>
								</div>
							</div>
							<div class="field">
								<div class="label">
									<label for="day">Approval Number </label>
								</div>
								<div class="input">
									<input type="text" name="approvalNo" id="approvalNo" maxlength="16" class="approvalNo" value="${trnsctnBean.approvalNo}"/>
								</div>
								
								<div class="label2">
									<label for="qualification">User Email </label>
								</div>
								<div class="input2">
									<input type="text" name="payEmail" id="payEmail" maxlength="16" class="payEmail" value="${trnsctnBean.payEmail}"/>
					             
								</div>
							</div>
							<div class="field">
								<div class="label">
									<label for="cardnum">Card Number</label>
								</div>
								<div class="input">
									<input type="text" name="temp1String" id="temp1String" maxlength="16" class="temp1String" value="${trnsctnBean.temp1String}"/>&nbsp;
								</div>
								
								<div class="label2">
									<label for="status">Status</label>
								</div>
								<div class="input2" >
									<select name="trnStatus" class="select2">
					              		<option value="">-----&nbsp;</option>
									    <option value="'00','01','02','03','04','05','06'">Approved</option>
									    <option value="'07','08','09','10','18','19','20','21'">collect</option>
									    <option value="'02','07','08','09','11','12','13','10'">Valid transaction</option>
									    <option value="'11','12','13'">Paid</option>
									    <option value="'06','14','18'">Canceled or Refund</option>
									    <option value="'17'">Ready for cancel</option>
									    <option value="'15'">Refund request</option>
									    <option value="'22','23'">Chargeback</option>
									    
					                </select>
					                <c:if test="${!empty trnsctnBean.trnStatus}"><script>document.trnsctnList.trnStatus.value="${trnsctnBean.trnStatus}"</script></c:if>
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
			        		<font color="blue"><fmt:formatNumber type="number" value="${dao.totalCount}" pattern="#,##0" />
			        		</font>&nbsp;SumAmount : 
			        		<font color="red"><fmt:formatNumber type="number" value="${searchAmount}" pattern="#,##0.00" />
			        		</font>&nbsp;]&nbsp;&nbsp;
			        		</strong>
						</h7>
					</div>
					<!-- end box / title -->					
					<div class="table">											
						<table>
							<thead>
								<tr>
									<th class="list_tit">No</th>
									<th class="list_tit">Transaction Id</th>
									<th class="list_tit">Merchant Ref.</th>
									<th class="list_tit">Product Name</th>
									<th class="list_tit">Status</th>
									<th class="list_tit">Currency</th>
									<th class="list_tit">Amount</th>
									<th class="list_tit">Card Number</th>
									<th class="list_tit">Card Brand</th>
									<th class="list_tit">Approval Number</th>
									<th class="list_tit">Request Date</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(trnsctnList) != 0}">
										<c:forEach var="list" items="${trnsctnList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list"><strong><a href="javascript:view('${list.transactionId}');">${list.transactionId}</a></strong></td>
												<td class="list">${list.payNo }&nbsp;</td>
												<td class="list">${list.productName }&nbsp;</td>
												<td class="list">
													<c:choose>
														<c:when test="${list.trnStatus == '00'}"><font color="#800080">On Approval</font>
														</c:when>
														<c:when test="${list.trnStatus == '01'}"><font color="red">Error on approval</font>
														</c:when>
														<c:when test="${list.trnStatus == '02'}"><font color="blue">Approved</font>
														</c:when>
														<c:when test="${list.trnStatus == '03'}">Failed
														</c:when>
														<c:when test="${list.trnStatus == '04'}"><font color="red">Cancel request</font>
														</c:when>
														<c:when test="${list.trnStatus == '05'}"><font color="red">Error on cancel</font>
														</c:when>
														<c:when test="${list.trnStatus == '06'}">Canceled
														</c:when>
														<c:when test="${list.trnStatus == '07'}"><font color="blue">Collecting reqeust</font>
														</c:when>
														<c:when test="${list.trnStatus == '08'}"><font color="blue">On collect</font>
														</c:when>
														<c:when test="${list.trnStatus == '09'}"><font color="blue">Collected</font>
														</c:when>
														<c:when test="${list.trnStatus == '10'}"><font color="red">Collect restored</font>
														</c:when>
														<c:when test="${list.trnStatus == '11'}"><font color="blue">Ready for paid</font>
														</c:when>
														<c:when test="${list.trnStatus == '12'}"><font color="blue">Paid request</font>
														</c:when>
														<c:when test="${list.trnStatus == '13'}"><font color="blue">Paid</font>
														</c:when>
														<c:when test="${list.trnStatus == '14'}">Refund
														</c:when>
														<c:when test="${list.trnStatus == '15'}">>Refund request
														</c:when>
														<c:when test="${list.trnStatus == '17'}"><font color="red">Ready for cancel</font>
														</c:when>
														<c:when test="${list.trnStatus == '18'}"><font color="red">Refund</font>
														</c:when>
														<c:when test="${list.trnStatus == '19'}"><font color="red">Refund restored</font>
														</c:when>
														<c:when test="${list.trnStatus == '20'}"><font color="red">On refund</font>
														</c:when>
														<c:when test="${list.trnStatus == '21'}"><font color="red">Failure of collect</font>
														</c:when>
														<c:when test="${list.trnStatus == '22'}"><font color="red">Chargeback</font>
														</c:when>
														<c:when test="${list.trnStatus == '23'}"><font color="red">Chargeback</font>
														</c:when>
														<c:otherwise>
															${list.trnStatus }
														</c:otherwise>
													</c:choose>
												</td>
												<td class="list">${list.curType}&nbsp;</td>
												<td class="list"><fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" /></td>
												<td class="list">${list.temp1String}&nbsp;</td> <!-- 카드번호 -->
												<td class="list">${list.temp2String}&nbsp;</td> <!-- 카드종류 --> 
												<td class="list">${list.approvalNo}&nbsp;</td>
												<td class="list"><fmt:formatDate type="both" value="${list.trnReqDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
												
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(trnsctnList)) * 12}">
								    			<td align="center" class="list" colspan="11">&nbsp;NO RESULT</td>
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

