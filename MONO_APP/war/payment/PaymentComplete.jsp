<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%@ page import="biz.trustnet.common.util.CommonUtil"%>
<%
response.setHeader("Cache-Control", "No-Cache");
response.setHeader("Pragma", "No-Cache");
response.setDateHeader("Expires",0);

 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<TITLE>::::::[PanWorldNet]::::::</TITLE>


<style type="text/css">
<!--
body {
	margin-top: 35px;
	margin-left: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="/common/css/search.css" rel="stylesheet" type="text/css">


</head>

<body>
<!-- top -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="logo"><a href="/sso.do"><img src="/images/logo.gif" width="145" height="45" border="0" alt="pan world"></a></td>
  </tr>
  <tr>
    <td height="8" class="line01"></td>
  </tr>
  <tr>
    <td height="7" class="line02"></td>
  </tr>
</table>
<!-- top end -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
  		<td align="center" height="50" valign="top" >&nbsp;
  	</td>
  	</tr>
  <tr>
    <td align="center" valign="top" >
    <form name="payment" action ="/payment.do" method="POST">
	<table align="center" border="0" cellpadding="10" cellspacing="0" bgcolor="#000033" style="border-width:1; border-style:solid;">
	  <tr> 
		<td height="113" bordercolor="#000033" bgcolor="#FFFFFF" align="center" valign="center"><br><br>
		<b>PanWorld-Net Payment Sample</b><br><br>
		<table border="0" style="border-width:1; border-style:solid;">
			<tr>
				<td colspan="2" align="center">
					<font color="blue">RESULT_CODE : ${headerBean.resultCd }</font>
					<br/>
					<br/>
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;RESULT_MESSAGE</p>
				</td>			
				<td>${headerBean.resultMsg }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;TRANSACTION_ID</p>
				</td>			
				<td>${headerBean.transactionId }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PIN_NUMBER</p>
				</td>			
				<td>${tBean.cardNumber }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;EXPIRE(YYMM)</p>
				</td>			
				<td>${tBean.cardExpire }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;APPROVAL_NO</p>
				</td>			
				<td>${tBean.approvalNo }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;TRN_RES_DATE</p>
				</td>			
				<td>${headerBean.trnResDate }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;TRN_TYPE</p>
				</td>			
				<td>${headerBean.trnType }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;MERCHANT_ID</p>
				</td>			
				<td>${headerBean.merchantId }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;MALL_ID</p>
				</td>			
				<td>${headerBean.mallId }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;IP_ADDRESS</p>
				</td>			
				<td>${headerBean.ipAddress }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PAY_NO</p>
				</td>			
				<td>${headerBean.payNo }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PAY_EMAIL</p>
				</td>			
				<td>${tBean.payEmail }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PAY_USERID</p>
				</td>			
				<td>${tBean.payUserId }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PAY_NAME</p>
				</td>			
				<td>${tBean.payName }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PAY_TELNO</p>
				</td>			
				<td>${tBean.payTelNo }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;AMOUNT ex) 5.01$ -&lt; 500</p>
				</td>			
				<td><fmt:formatNumber type="number" value="${tBean.amount }" pattern="#,##0" /></td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PRODUCT_TYPE</p>
				</td>			
				<td>${tBean.productType }</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PRODUCT_NAME</p>
				</td>			
				<td>${tBean.productName }</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;
				</td>			
			</tr>
		 </table>
		 </td>
	  </tr>
	  <tr> 
		<td bordercolor="#000000" bgcolor="#003366" align="center" height="3">
		</td>
	  </tr>
	</table>
	</form>
    

    </td>
  </tr>
</table>
</body>
</html>
