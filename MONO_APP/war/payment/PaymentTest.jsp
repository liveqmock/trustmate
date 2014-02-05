<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="biz.trustnet.common.util.CommonUtil"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
    <form name="payment" action ="/adminPayment.do" method="POST">
	<table align="center" border="0" cellpadding="10" cellspacing="0" bgcolor="#000033" style="border-width:1; border-style:solid;">
	  <tr> 
		<td height="113" bordercolor="#000033" bgcolor="#FFFFFF" align="center" valign="center"><br><br>
		<b>PanWorld-Net Payment Sample</b><br><br>
		<table border="0" style="border-width:1; border-style:solid;">
			<tr>
				<td colspan="2" align="center">
					<font color="blue">Access IP : <%=request.getRemoteAddr() %></font>
					<br/>
					<br/>
					<input type="hidden" name="trnType" value="T001"/>
					<input type="hidden" name="specType" value="CFIX"/>
					<input type="hidden" name="serviceType" value="WEB"/>
					<input type="hidden" name="curType" value="USD"/>
					<input type="hidden" name="ipAddress" value="<%=request.getRemoteAddr() %>"/>
					<input type="hidden" name="domain" value="http://service.pgmate.com"/>
					<input type="hidden" name="trnDate" value="<%=CommonUtil.getCurrentDate("yyyyMMddHHmmss")%>"/>
					<input type="hidden" name="foreName" value="Juseop">
					<input type="hidden" name="surName" value="Lim">
					<input type="hidden" name="foreName" value="Juseop">
					<input type="hidden" name="addr1" value="304 Soungnae-dong">
					<input type="hidden" name="addr2" value="GangDong-Gu">
					<input type="hidden" name="city" value="Seoul">
					<input type="hidden" name="state" value="Seoul">
					<input type="hidden" name="country" value="KR">
					<input type="hidden" name="zip" value="43410">
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;TRN_TYPE</p>
				</td>			
				<td>
					<select name="TRN_TYPE">
						<option value="T001">TRN1 - CREDIT</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PAY_NO</p>
				</td>			
				<td>
					<input type="text" name="payNo" maxlength="50" size="50" value="<%=CommonUtil.getCurrentDate("yyyyMMddHHmmss")%>">&nbsp;
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PAY_EMAIL</p>
				</td>			
				<td>
					<input type="text" name="payEmail" maxlength="50" size="50" value="redsky@panworld-net.com">&nbsp;
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PAY_USERID</p>
				</td>			
				<td>
					<input type="text" name="payUserId" maxlength="50" size="50" value="redsky">&nbsp;
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PAY_NAME</p>
				</td>			
				<td>
					<input type="text" name="payName" maxlength="50" size="50" value="redsky">&nbsp;
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PAY_TELNO</p>
				</td>			
				<td>
					<input type="text" name="payTelNo" maxlength="50" size="50" value="01027423730">&nbsp;
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;AMOUNT ex) 5.01$ -&lt; 110</p>
				</td>			
				<td>
					<input type="text" name="amount" maxlength="50" value="110" size="50">&nbsp;
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PRODUCT_TYPE</p>
				</td>			
				<td>
					<input type="text" name="productType" maxlength="50" size="50" value="1">&nbsp;
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PRODUCT_NAME</p>
				</td>			
				<td>
					<input type="text" name="productName" maxlength="50" size="50" value="TEST PRODUCT">&nbsp;
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;MERCHANT_ID</p>
				</td>			
				<td>
					<input type="text" name="merchantId" maxlength="50" size="50" value="panworld">&nbsp;
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;MALL_ID</p>
				</td>			
				<td>
					<input type="text" name="mallId" maxlength="50" size="50" value="panworld-1">&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;
				</td>			
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;PIN_NUMBER</p>
				</td>			
				<td>
					<input type="text" name="cardNumber" maxlength="50" size="50" value="4762552207159237">&nbsp;
				</td>
			</tr>
			<tr>
				<td width="168" align="right">
					<p>&nbsp;EXPIRE(YYMM)</p>
				</td>			
				<td>
					<input type="text" name="cardExpire" maxlength="50" size="50" value="1606">&nbsp;
				</td>
			</tr>

			<tr>
				<td width="168" align="right">
					<p>&nbsp;<font color="red">Execute Payment:</font></p>
				</td>			
				<td align ="center">
					<input type="button" value="SEND" size="20" onclick="javascript:document.payment.submit();" >&nbsp;
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
