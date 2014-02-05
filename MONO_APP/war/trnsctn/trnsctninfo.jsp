<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Description</title>
		<meta http-equiv="popContent-Type" popContent="text/html;charset=utf-8" />
		<!-- stylesheets -->
		<link rel="stylesheet" type="text/css" href="/resources/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="/resources/css/pop_style.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="/resources/css/pop_style.css" media="print" />
		<link id="color" rel="stylesheet" type="text/css" href="/resources/css/colors/blue.css" />
		<!-- scripts (jquery) -->
		<script src="/resources/scripts/jquery-1.6.4.min.js" type="text/javascript"></script>
		<!--[if IE]><script language="javascript" type="text/javascript" src="/resources/scripts/excanvas.min.js"></script><![endif]-->
		<script src="/resources/scripts/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
		<script src="/resources/scripts/jquery.ui.selectmenu.js" type="text/javascript"></script>
		<script src="/resources/scripts/jquery.flot.min.js" type="text/javascript"></script>
		<script src="/resources/scripts/tiny_mce/tiny_mce.js" type="text/javascript"></script>
		<script src="/resources/scripts/tiny_mce/jquery.tinymce.js" type="text/javascript"></script>
		<!-- scripts (custom)-->
		<script src="/resources/scripts/smooth.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.menu.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.table.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.form.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.dialog.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.autocomplete.js" type="text/javascript"></script> 
		<script type="text/javascript">

			function insert() {	
				var frm  = document.insertForm;
				var frm1 = document.commentform;
						
				if(frm1.comment.value == ""){
				   alert("취소 사유를 입력하여 주시기 바랍니다.!");
				   frm1.comment.focus();
				}else{
				   frm.comment.value = frm1.comment.value;
				   if(confirm("취소를 진행하시겠습니까?")){
				      frm.submit();
				   }
				}
			}

		</script>
</head>
<body scroll="auto">
		<div id="content">
			<div class="box">
				<!-- box / title -->
				<div class="title">
					<h5>거래내역 상세정보</h5>
					<ul class="links">
						<li><a href="javascript:window.print();">PRINT</a></li>
					</ul>
				</div>
				<!-- end box / title -->	
							
				
				<div class="table">											
					<table>
						<form action="/adminPayment.do?TRN_TYPE=T002" method="post" name="insertForm">
						<tbody>
							<tr>
								<th class="list_tit">거래번호</th>
								<td class="list"><strong>${trnsctnBean.transactionId }&nbsp;</strong>
									<input name="transactionId" type="hidden" value ="${trnsctnBean.transactionId }" />
									<input name="specType" type="hidden" value="CFIX" />
						        	<input name="trnType" type="hidden" value="T002" />
						        	<input name="merchantId" type="hidden" value="${trnsctnBean.merchantId }" />
						        	<input name="mallId" type="hidden" value="${trnsctnBean.mallId }" />
						        	<input name="serviceType" type="hidden" value="${trnsctnBean.serviceType }" />
						        	<input name="ipAddress" type="hidden" value="${trnsctnBean.ipAddress }" />
						        	<input name="payNo" type="hidden" value="${trnsctnBean.payNo }" />
						        	<input name="voidType" type="hidden" value="1" />
						        	<input name="rTransactionId" type="hidden" value="${trnsctnBean.transactionId }"/>
						        	<input name="comment" type="hidden" value=""/>
						        	<input name="memberId" type="hidden" value="${ssoBean.memberId }"/>
					        	</td>
								<th class="list_tit">가맹점명</th>
								<td class="list"><strong>${merchantBean.name }&nbsp;</strong></td>
							</tr>
							<tr>
								<th class="list_tit">가맹점아이디</th>
								<td class="list"><strong>${trnsctnBean.merchantId }&nbsp;</strong> </td>
								<th class="list_tit">몰 아이디</th>
								<td class="list"><strong>${trnsctnBean.mallId }&nbsp;</strong>  </td>
							</tr>
							<tr>
								<th class="list_tit">승인번호</th>
								<td class="list">${trnsctnBean.approvalNo }&nbsp;</td>	
								<th class="list_tit">주문번호</th>
								<td class="list">${trnsctnBean.payNo }&nbsp;</td>
							</tr>
							
							</tr>
							<tr>
								<th class="list_tit">상품명</th>
								<td class="list">${trnsctnBean.productName }&nbsp;</td>
								<th class="list_tit">금액</th>
								<td class="list">
									<c:if test="${trnsctnBean.curType == 'USD'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0.00" />&nbsp;$</c:if>
									<c:if test="${trnsctnBean.curType == 'JPY'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0" />&nbsp;円</c:if>
									<c:if test="${trnsctnBean.curType == 'KRW'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0" />&nbsp;</c:if>
									<c:if test="${trnsctnBean.curType == 'EUR'}"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0.00" />&nbsp;€</c:if>
								</td>
							</tr>
							<tr>
								<th class="list_tit">IP Address</th>
								<td class="list">${trnsctnBean.ipAddress }&nbsp;</td>
								<th class="list_tit">서비스 유형</th>
								<td class="list">${trnsctnBean.serviceType }&nbsp;</td>
							</tr>
							<tr>
								<th class="list_tit">송신 일시</th>
								<td class="list"><fmt:formatDate type="both" value="${trnsctnBean.trnReqDate }" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;</td>
								<input name="trnReqDate" type="hidden" value ="<fmt:formatDate type="both" value="${trnsctnBean.trnReqDate }" pattern="yyyyMMddHHmmss" />" />
								<th class="list_tit">수신 시간</th>
								<td class="list"><fmt:formatDate type="both" value="${trnsctnBean.trnResDate }" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;</td>
							</tr>
							
							<tr>
								<th class="list_tit">카드번호</th>
								<td class="list">${trnsctnSCRBean.cardNum }&nbsp;</td>
								<th class="list_tit">카드브랜드</th>
								<td class="list">${trnsctnSCRBean.cardType }&nbsp;</td>
							</tr>
							<tr>
								<th class="list_tit">고객명</th>
								<td class="list">${trnsctnBean.payName }&nbsp;</td>
								<th class="list_tit">고객 Email</th>
								<td class="list">${trnsctnBean.payEmail }&nbsp;</td>
							</tr>
							<tr>
								<th class="list_tit">고객 연락처</th>
								<td class="list">${trnsctnBean.payTelNo }&nbsp;</td>
								<th class="list_tit">CURRENCY</th>
								<td class="list">${trnsctnBean.curType }&nbsp;</td>
							</tr>
							<tr>
								<th class="list_tit">거래상태</th>
								<td class="list">
								<c:choose>
									<c:when test="${trnsctnBean.trnStatus == '00'}"><font color="#800080">승인요청중</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '01'}"><font color="red">승인장애</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '02'}"><font color="blue">승인완료</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '03'}">승인실패
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '04'}"><font color="red">승인취소요청</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '05'}"><font color="red">승인취소장애</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '06'}">승인취소
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '07'}"><font color="blue">매입요청</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '08'}"><font color="blue">매입중</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '09'}"><font color="blue">매입완료</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '10'}"><font color="red">매입반려</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '11'}"><font color="blue">정산대기</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '12'}"><font color="blue">정산요청</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '13'}"><font color="blue">정산완료</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '14'}">정산실패
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '15'}">환불요청
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '17'}"><font color="red">취소대기</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '18'}"><font color="red">매입취소</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '19'}"><font color="red">매입취소반려</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '20'}"><font color="red">매입취소중</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '21'}"><font color="red">매입실패</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '22'}"><font color="red">C・B 등록</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '23'}"><font color="red">C・B 확정</font>
									</c:when>
									<c:otherwise>
										${trnsctnBean.trnStatus }
									</c:otherwise>
								</c:choose>
								</td>
								<th class="list_tit">URL</th>
								<td class="list"><a href="${trnsctnBean.temp1 }" target="_blank">${trnsctnBean.temp1 }</a>&nbsp;</td>
							</tr>
							<tr>
								<th class="list_tit">결과 메세지</th>
								<td class="list" colspan="3">${resultMsg}&nbsp;[${trnsctnBean.resultMsg }]&nbsp;</td>
							</tr>
							<c:if test="${trnsctnVoidBean.status =='00'}">
							<tr>
								<th class="list_tit">취소 사유</th>
								<td class="list" colspan="3">
									<fmt:formatDate type="both" value="${trnsctnVoidBean.voidReqDate }" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;
									${voidComment}
								</td>
							</tr>
							</c:if>
						</tbody>
						</form>
					</table>
					
					</div>
					<c:if test="${trnsctnBean.trnStatus == '02' || trnsctnBean.trnStatus == '07' || trnsctnBean.trnStatus == '08' || trnsctnBean.trnStatus == '09' || trnsctnBean.trnStatus == '10' || trnsctnBean.trnStatus == '11' || trnsctnBean.trnStatus == '12' || trnsctnBean.trnStatus == '13'}">
					<form name="commentform">
					<div class="form">
						<div class="fields">
							<div class="field">
								
								<div class="label label-textarea">
								<label for="textarea">취소사유:</label>
							</div>
							<div class="input">
								<textarea id="textarea" name="comment" cols="63" rows="4"></textarea>
							</div>
															
							</div>
						</div>
					</div>
					</form>
					</c:if>
					<!-- table action -->
					<div class="action">
						<div class="button">
							
						<c:choose>
							<c:when test="${trnsctnBean.trnStatus == '02' }">
								<input type="submit" name="submit" value="Cancel" onclick="javascript:insert();" id="insert"/>&nbsp;
							</c:when>
							<c:when test="${trnsctnBean.trnStatus == '07' || trnsctnBean.trnStatus == '08' || trnsctnBean.trnStatus == '09' || trnsctnBean.trnStatus == '10' || trnsctnBean.trnStatus == '11' || trnsctnBean.trnStatus == '12' || trnsctnBean.trnStatus == '13'}">
							    <input type="submit" name="submit" value="Refund" onclick="javascript:insert();" id="insert"/>&nbsp;								
					   		</c:when>
						    <c:otherwise>						    	
						    </c:otherwise>
					    </c:choose>
					    	<input type="submit" name="submit" value="Close" onclick="javascript:self.close();" alt="close" id="close"/>&nbsp;
					    		
					    </div>
					</div>
					
					<!-- end table action -->
				</div>
				
			</div>
		</div>
	</body>
</html>



