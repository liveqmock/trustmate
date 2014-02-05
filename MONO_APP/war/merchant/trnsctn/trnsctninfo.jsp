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
		<script language="javascript" src="/common/js/prototype.js"></script>
		<script type="text/javascript">
			
			function cancel(idx) {
				
				var comment = document.commentform.comment.value;
				if(comment == '' || comment.length < 10){
					 alert("Please enter a reason to cancel! (10 words or more)");
					 document.commentform.comment.focus();
					 return;
				}else{
					
				}

				comment = encodeURIComponent(comment);
				
				if(!confirm('Are you sure you want to cancel this transaction (${trnsctnBean.transactionId })')){
					return;
				}

				var url = '/adminPayment.do?REQ_TYPE=mechantT002&idx='+idx+'&comment='+comment;
				
			    var myAjax = new Ajax.Request(url,{
			            method: 'post',
			            parameters: {},
			            onSuccess: responseCancel,
			            onFailure: function() { alert('Request Failure');}
			        }
			    );
			}
			function responseCancel(reqResult) {
				var isUpdate = reqResult.responseText;
				if(isUpdate == 'OK'){
					alert('Credit card transaction cancellation acceptance completion');
					self.close();
				}else{
					alert(isUpdate);
				}
		
			}
		</script>	
</head>
<body scroll="auto">
		<div id="content">
			<div class="box">
				<!-- box / title -->
				<div class="title">
					<h5>Transaction Information</h5>
					<ul class="links">
						<li><a href="javascript:window.print();">PRINT</a></li>
					</ul>
				</div>
				<!-- end box / title -->	
							
				
				<div class="box">											
					<table>
						<tbody>
							<tr>
								<th class="list_tit">Transaction Id</th>
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
								<th class="list_tit">Merchant Name</th>
								<td class="list"><strong>${merchantBean.name }&nbsp;</strong></td>
							</tr>
							<tr>
								<th class="list_tit">Merchant Id</th>
								<td class="list"><strong>${trnsctnBean.merchantId }&nbsp;</strong> </td>
								<th class="list_tit">Mall Id</th>
								<td class="list"><strong>${trnsctnBean.mallId }&nbsp;</strong>  </td>
							</tr>
							<tr>
								<th class="list_tit">Approval Number</th>
								<td class="list">${trnsctnBean.approvalNo }&nbsp;</td>	
								<th class="list_tit">Merchant Ref.</th>
								<td class="list">${trnsctnBean.payNo }&nbsp;</td>
							</tr>
							
							</tr>
							<tr>
								<th class="list_tit">Product Name</th>
								<td class="list">${trnsctnBean.productName }&nbsp;</td>
								<th class="list_tit">Amount</th>
								<td class="list"><fmt:formatNumber type="number" value="${trnsctnBean.amount }" pattern="#,##0" />&nbsp;
									<c:if test="${trnsctnBean.curType == 'USD'}">$</c:if>
									<c:if test="${trnsctnBean.curType == 'JPY'}">円</c:if>
									<c:if test="${trnsctnBean.curType == 'KRW'}"></c:if>
									<c:if test="${trnsctnBean.curType == 'EUR'}">€</c:if>
								</td>
							</tr>
							<tr>
								<th class="list_tit">IP Address</th>
								<td class="list">${trnsctnBean.ipAddress }&nbsp;</td>
								<th class="list_tit">Service Type</th>
								<td class="list">${trnsctnBean.serviceType }&nbsp;</td>
							</tr>
							<tr>
								<th class="list_tit">Request Date</th>
								<td class="list"><fmt:formatDate type="both" value="${trnsctnBean.trnReqDate }" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;</td>
								<input name="trnReqDate" type="hidden" value ="<fmt:formatDate type="both" value="${trnsctnBean.trnReqDate }" pattern="yyyyMMddHHmmss" />" />
								<th class="list_tit">Response Date</th>
								<td class="list"><fmt:formatDate type="both" value="${trnsctnBean.trnResDate }" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;</td>
							</tr>
							
							<tr>
								<th class="list_tit">Card Number</th>
								<td class="list">${trnsctnSCRBean.cardNum }&nbsp;</td>
								<th class="list_tit">Card Brand</th>
								<td class="list">${trnsctnSCRBean.cardType }&nbsp;</td>
							</tr>
							<tr>
								<th class="list_tit">Payment User</th>
								<td class="list">${trnsctnBean.payName }&nbsp;</td>
								<th class="list_tit">User Email</th>
								<td class="list">${trnsctnBean.payEmail }&nbsp;</td>
							</tr>
							<tr>
								<th class="list_tit">User Tel.</th>
								<td class="list">${trnsctnBean.payTelNo }&nbsp;</td>
								<th class="list_tit">Status</th>
								<td class="list">
								<c:choose>
									<c:when test="${trnsctnBean.trnStatus == '00'}"><font color="#800080">On Approval</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '01'}"><font color="red">Error on approval</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '02'}"><font color="blue">Approved</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '03'}">Failed
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '04'}"><font color="red">Cancel request</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '05'}"><font color="red">Error on cancel</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '06'}">Canceled
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '07'}"><font color="blue">Collecting reqeust</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '08'}"><font color="blue">On collect</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '09'}"><font color="blue">Collected</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '10'}"><font color="red">Collect restored</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '11'}"><font color="blue">Ready for paid</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '12'}"><font color="blue">Paid request</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '13'}"><font color="blue">Paid</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '14'}">Refund
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '15'}">>Refund request
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '17'}"><font color="red">Ready for cancel</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '18'}"><font color="red">Refund</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '19'}"><font color="red">Refund restored</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '20'}"><font color="red">On refund</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '21'}"><font color="red">Failure of collect</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '22'}"><font color="red">Chargeback</font>
									</c:when>
									<c:when test="${trnsctnBean.trnStatus == '23'}"><font color="red">Chargeback</font>
									</c:when>
									<c:otherwise>
										${trnsctnBean.trnStatus }
									</c:otherwise>
								</c:choose>
								</td>
							</tr>
							<tr>
								<th class="list_tit">Result Msg</th>
								<td class="list" colspan="3">${resultMsg}&nbsp;[${trnsctnBean.resultMsg }]&nbsp;</td>
							</tr>
							<c:if test="${trnsctnVoidBean.status =='00'}">
							<tr>
								<th class="list_tit">Cancel Why</th>
								<td class="list" colspan="3">
									<fmt:formatDate type="both" value="${trnsctnVoidBean.voidReqDate }" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;
									${voidComment}
								</td>
							</tr>
							</c:if>
							
						</tbody>
					</table>
					
					</div>
					<c:if test="${trnsctnBean.trnStatus == '02' || trnsctnBean.trnStatus == '07' || trnsctnBean.trnStatus == '08' || trnsctnBean.trnStatus == '09' || trnsctnBean.trnStatus == '10' || trnsctnBean.trnStatus == '11' || trnsctnBean.trnStatus == '12' || trnsctnBean.trnStatus == '13'}">
					<form name="commentform">
					<div class="form">
						<div class="fields">
							<div class="field">
								
								<div class="label label-textarea">
								<label for="textarea">cancel why</label>
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
								<input type="submit" name="refund" value="Execute Cancel" onclick="javascript:cancel(${trnsctnBean.idx});" id="refund"/>&nbsp;
							</c:when>
							<c:when test="${trnsctnBean.trnStatus == '07' || trnsctnBean.trnStatus == '08' || trnsctnBean.trnStatus == '09' || trnsctnBean.trnStatus == '10' || trnsctnBean.trnStatus == '11' || trnsctnBean.trnStatus == '12' || trnsctnBean.trnStatus == '13'}">
							    <input type="submit" name="refund" value="Execute Refund" onclick="javascript:cancel(${trnsctnBean.idx});" id="refund"/>&nbsp;								
					   		</c:when>
						    <c:otherwise>						    	
						    </c:otherwise>
						</c:choose>
					    	&nbsp;&nbsp;<input type="submit" name="submit" value="Close" onclick="javascript:self.close()" alt="close" id="close"/>&nbsp;
					    		
					    </div>
					</div>
					
					<!-- end table action -->
				</div>
				
			</div>
		</div>
	</body>
</html>



