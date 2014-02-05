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
			window.open('/trnsctn.do?request=view&transactionId='+transactionId,'','width=730,height=560,left=100,top=100');
		}else{
			window.open("/trnsctn.do?request=view&transactionId="+transactionId,"","width=730,height=440,left=100,top=100");
		}
	}
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
				<!-- forms -->
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5><spring:message code="searchPannel" text=""/></h5>
					</div>
					<form name="trnsctnList" action="/trnsctn.do?request=list" method="post">
					<div class="form">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="qualification">가맹점 아이디</label>
								</div>
								<div class="input">
									<input type="text" name="merchantId" id="merchantId" maxlength="16" class="merchantId" value="${trnsctnBean.merchantId}"/>
									
								</div>
								<div class="label2">
									<label for="day">기간</label>
								</div>
								<div class="input2">
									<input type="text" id="startDate" name="startDate" class="date" value="${startDate}" onchange="onlyDate2(this);"/>
									<span class="m">-</span>
									<input type="text" id="endDate" name="endDate" class="date" value="${endDate }" onchange="onlyDate2(this);"/>
								</div>
								
								
							</div>
							<div class="field">
								<div class="label">
									<label for="day">거래번호</label>
								</div>
								<div class="input">
									<input type="text" name="transactionId" id="transactionId" maxlength="16" class="transactionId" value="${trnsctnBean.transactionId}"/>
								</div>
								
								<div class="label2">
									<label for="qualification">주문번호</label>
								</div>
								<div class="input2">
									<input type="text" name="payNo" id="payNo" maxlength="16" class="payNo" value="${trnsctnBean.payNo}"/>
								</div>
							</div>
							<div class="field">
								<div class="label">
									<label for="day">승인번호</label>
								</div>
								<div class="input">
									<input type="text" name="approvalNo" id="approvalNo" maxlength="16" class="approvalNo" value="${trnsctnBean.approvalNo}"/>
								</div>
								
								<div class="label2">
									<label for="qualification">고객이메일</label>
								</div>
								<div class="input2">
									<input type="text" name="payEmail" id="payEmail" maxlength="16" class="payEmail" value="${trnsctnBean.payEmail}"/>
					             
								</div>
							</div>
							<div class="field">
								<div class="label">
									<label for="cardnum">카드번호</label>
								</div>
								<div class="input">
									<input type="text" name="temp1String" id="temp1String" maxlength="16" class="temp1String" value="${trnsctnBean.temp1String}"/>&nbsp;
								</div>
								
								<div class="label2">
									<label for="status">상태</label>
								</div>
								<div class="input2" >
									<select name="trnStatus" class="select2">
					              		<option value="">-----&nbsp;</option>
									    <option value="'00','01','02','03','04','05','06'">승인</option>
									    <option value="'07','08','09','10','18','19','20','21'">매상</option>
									    <option value="'02','07','08','09','11','12','13','10'">유효거래</option>
									    <option value="'11','12','13'">정산</option>
									    <option value="'06','14','18'">취소</option>
									    <option value="'17'">취소대기</option>
									    <option value="'15'">환불요청</option>
									    <option value="'22'">C・B 조정중</option>
									    <option value="'23'">C・B 확정</option>
					                </select>
					                <c:if test="${!empty trnsctnBean.trnStatus}"><script>document.trnsctnList.trnStatus.value="${trnsctnBean.trnStatus}"</script></c:if>
								</div>
								
							</div>
							<div class="field">
								<div class="label">
									<label for="">검색건수</label>
								</div>
								<div class="input">
									<select name="pageSize">
					              		<option value="50" <c:if test="${dao.pageSize == 50}">selected</c:if>>50</option>
					                	<option value="100" <c:if test="${dao.pageSize == 100}">selected</c:if>>100</option>
					                	<option value="200" <c:if test="${dao.pageSize == 200}">selected</c:if>>200</option>
					                	<option value="500" <c:if test="${dao.pageSize == 500}">selected</c:if>>500</option>
					                </select>
								</div>
								<div class="label2">
									<label for="">&nbsp;</label>
								</div>
								<div class="input2" >
									&nbsp;
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
							<strong>Month [ Count : 
			        		<font color="blue">
			        			<fmt:formatNumber type="number" value="${currentTrnsctnBean.monthCount}" pattern="#,##0" />
			        		</font>&nbsp;Amount : 
			        		<font color="red">
			        			<fmt:formatNumber type="number" value="${currentTrnsctnBean.monthSum}" pattern="#,##0.00" />
			        		</font>&nbsp;]&nbsp;&nbsp;Search [ Count : 
			        		<font color="blue"><fmt:formatNumber type="number" value="${dao.totalCount}" pattern="#,##0" />
			        		</font>&nbsp;Amount : 
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
									<th class="list_tit">거래번호</th>
									<th class="list_tit">가맹점 아이디</th>
									<th class="list_tit">몰 아이디</th>
									<th class="list_tit">주문번호</th>
									<th class="list_tit">상태</th>
									<th class="list_tit">통화</th>
									<th class="list_tit">금액</th>
									<th class="list_tit">카드번호</th>
									<th class="list_tit">카드브랜드</th>
									<th class="list_tit">승인번호</th>
									<th class="list_tit">결제요청일시</th>
									<th class="list_tit">결제응답일시</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(trnsctnList) != 0}">
										<c:forEach var="list" items="${trnsctnList}" varStatus="status">
											<tr>
												<td class="list">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
												<td class="list">
													<c:choose>
												      <c:when test="${list.trnStatus == '02' || list.trnStatus == '07' || list.trnStatus == '08' || list.trnStatus == '09' || list.trnStatus == '10' || list.trnStatus == '13'}">
												    	<a href="javascript:view(1,'${list.transactionId}');"><strong>${list.transactionId}</strong></a>
													  </c:when>
													  <c:otherwise>
												    	<a href="javascript:view(2,'${list.transactionId}');"><strong>${list.transactionId}</strong></a>
												      </c:otherwise>
													</c:choose>
												</td>
												<td class="list">${list.merchantId }&nbsp;</td>
												<td class="list">${list.mallId }&nbsp;</td>
												<td class="list">${list.payNo }&nbsp;</td>
												<td class="list">
													<c:choose>
														<c:when test="${list.trnStatus == '00'}"><font color="#800080">승인요청중</font>
														</c:when>
														<c:when test="${list.trnStatus == '01'}"><font color="red">승인장애</font>
											 			</c:when>
														<c:when test="${list.trnStatus == '02'}"><font color="blue">승인완료</font>
														</c:when>
														<c:when test="${list.trnStatus == '03'}">승인실패
														</c:when>
														<c:when test="${list.trnStatus == '04'}"><font color="red">승인취소요청</font>
														</c:when>
														<c:when test="${list.trnStatus == '05'}"><font color="red">승인취소장애</font>
														</c:when>
														<c:when test="${list.trnStatus == '06'}">승인취소
														</c:when>
														<c:when test="${list.trnStatus == '07'}"><font color="blue">매입요청</font>
														</c:when>
														<c:when test="${list.trnStatus == '08'}"><font color="blue">매입중</font>
														</c:when>
														<c:when test="${list.trnStatus == '09'}"><font color="blue">매입완료</font>
														</c:when>
														<c:when test="${list.trnStatus == '10'}"><font color="red">매입반려</font>
														</c:when>
														<c:when test="${list.trnStatus == '11'}"><font color="blue">정산대기</font>
														</c:when>
														<c:when test="${list.trnStatus == '12'}"><font color="blue">정산요청</font>
														</c:when>
														<c:when test="${list.trnStatus == '13'}"><font color="blue">정산완료</font>
														</c:when>
														<c:when test="${list.trnStatus == '14'}">정산후취소
														</c:when>
														<c:when test="${list.trnStatus == '15'}">환불요청
														</c:when>
														<c:when test="${list.trnStatus == '17'}"><font color="red">취소대기</font>
														</c:when>
														<c:when test="${list.trnStatus == '18'}"><font color="red">매입취소</font>
														</c:when>
														<c:when test="${list.trnStatus == '19'}"><font color="red">매입취소반려</font>
														</c:when>
														<c:when test="${list.trnStatus == '20'}"><font color="red">매입취소중</font>
														</c:when>
														<c:when test="${list.trnStatus == '21'}"><font color="red">매입실패</font>
														</c:when>
														<c:when test="${list.trnStatus == '22'}"><font color="red">C・B 등록</font>
														</c:when>
														<c:when test="${list.trnStatus == '23'}"><font color="red">C・B 확정</font>
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
												<td class="list"><fmt:formatDate type="both" value="${list.trnResDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr height="${(50-fn:length(trnsctnList)) * 12}">
								    			<td align="center" class="list" colspan="13">&nbsp;NO RESULT</td>
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
		<c:import url="/include/footer.jsp" />
		<!-- end footert -->
	</body>
</html>

