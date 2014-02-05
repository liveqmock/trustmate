<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.pgmate.web.util.ExcelUtil,biz.trustnet.common.util.CommonUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%
	String fileName = "INVOICE_"+CommonUtil.getCurrentDate("yyyyMMddHHmmss");
	response = new ExcelUtil().toExcelResponse(request,response,fileName);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv=Content-Type content='text/html; charset=utf-8'>
<link href="/common/css/search.css" rel="stylesheet" type="text/css" />
<html>
	<title>HANA - INVOICE TRANSACTION LIST</title>
	<style type="text/css">
	<!--
		table { font-family: "Seoul", "Arial", "Helvetica"; font-size: 10pt; color: #333333}
	}
	-->
	</style>
<body>
<font size="2">
<div ID="objContents">
<span id="d1">
	<table>
	<tr>
	<td colspan="15" align="center">
		<font color="blue">
			<b>Transaction List</b>
		</font>
	</td>
    </tr>
    </table>
    <table width="95%" cellpadding="0" cellspacing="1" border="1" bordercolor="#C4D2E7">
	  <tr>
        <td width="3%" height="25" align="center" class="list_title">No</td>
        <td width="10%" align="center"  class="list_title">Transaction Id</td>
        <td width="8%" align="center" class="list_title">Merchant Id</td>
        <td width="8%" align="center" class="list_title">Mall Id</td>
        <td width="11%" align="center"  class="list_title">Merchant Ref.</td>
        <td width="8%" align="center"  class="list_title">Payer Name</td>
        <td width="8%" align="center"  class="list_title">Payer Email</td>
        <td width="8%" align="center"  class="list_title">Payer TelNo</td>
        <td width="4%" align="center" class="list_title">Currency</td>
        <td width="6%" align="center" class="list_title">Amount</td>
        <td width="10%" align="center"  class="list_title">Card Number</td>
        <td width="6%" align="center"  class="list_title">Card Brand</td>
        <td width="6%" align="center" class="list_title">Approval Number</td>
        <td width="10%" align="center"  class="list_title">Request Date</td>
        <td width="10%" align="center"  class="list_title">Response Date</td>
      </tr>
      <tr>
        <td colspan="15" height="2" bgcolor="#1b6c8e"></td>
      </tr>
      <c:forEach var="list" items="${trnsctnList}" varStatus="status">
      	<tr>
      		<td align="center" class="list_02" style="border-left:0 none;">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
      		<td align="center" class="list_02" style='mso-number-format:"\@";'>${list.transactionId}</td>
      		<td align="center" class="list_02">${list.merchantId }</td>
      		<td align="center" class="list_02">${list.mallId }</td>
      		<td align="center" class="list_02" style="border-left:0 none;">&nbsp;${list.payNo}</td>
      		<td align="center" class="list_02">${list.payName }</td>
      		<td align="center" class="list_02">${list.payEmail }</td>
      		<td align="center" class="list_02">${list.payTelNo }</td>
      		<td align="center" class="list_02">${list.curType }</td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02">${list.temp1String}</td>
      		<td align="center" class="list_02">${list.temp2String}</td>
      		<td align="center" class="list_02">&nbsp;${list.approvalNo}</td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.trnReqDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.trnResDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      	</tr>
      </c:forEach>
    </table>
    <table>
	<tr>
	<td colspan="15" align="center">
		<font color="blue">&nbsp;</font>
	</td>
    </tr>
    </table>
    <table>
	<tr>
	<td colspan="15" align="center">
		<font color="blue">
			<b>Refund List</b>
		</font>
	</td>
    </tr>
    </table>
    <table width="95%" cellpadding="0" cellspacing="1" border="1" bordercolor="#C4D2E7">
	  <tr>
        <td width="3%" height="25" align="center" class="list_title">No</td>
        <td width="10%" align="center"  class="list_title">Transaction Id</td>
        <td width="8%" align="center" class="list_title">Merchant Id</td>
        <td width="8%" align="center" class="list_title">Mall Id</td>
        <td width="11%" align="center"  class="list_title">Merchant Ref.</td>
        <td width="8%" align="center"  class="list_title">Payer Name</td>
        <td width="8%" align="center"  class="list_title">Payer Email</td>
        <td width="8%" align="center"  class="list_title">Payer TelNo</td>
        <td width="4%" align="center" class="list_title">Currency</td>
        <td width="6%" align="center" class="list_title">Amount</td>
        <td width="10%" align="center"  class="list_title">Card Number</td>
        <td width="6%" align="center"  class="list_title">Card Brand</td>
        <td width="6%" align="center" class="list_title">Approval Number</td>
        <td width="10%" align="center"  class="list_title">Request Date</td>
        <td width="10%" align="center"  class="list_title">Response Date</td>
      </tr>
      <tr>
        <td colspan="15" height="2" bgcolor="#1b6c8e"></td>
      </tr>
      <c:forEach var="list" items="${refundList}" varStatus="status">
      	<tr>
      		<td align="center" class="list_02" style="border-left:0 none;">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
      		<td align="center" class="list_02" style='mso-number-format:"\@";'>${list.transactionId}</td>
      		<td align="center" class="list_02">${list.merchantId }</td>
      		<td align="center" class="list_02">${list.mallId }</td>
      		<td align="center" class="list_02" style="border-left:0 none;">&nbsp;${list.payNo}</td>
      		<td align="center" class="list_02">${list.payName }</td>
      		<td align="center" class="list_02">${list.payEmail }</td>
      		<td align="center" class="list_02">${list.payTelNo }</td>
      		<td align="center" class="list_02">${list.curType }</td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02">${list.temp1String}</td>
      		<td align="center" class="list_02">${list.temp2String}</td>
      		<td align="center" class="list_02">&nbsp;${list.approvalNo}</td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.trnReqDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.trnResDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      	</tr>
      </c:forEach>
    </table>
    <table>
	<tr>
	<td colspan="15" align="center">
		<font color="blue">&nbsp;</font>
	</td>
    </tr>
    </table>
    <table>
	<tr>
	<td colspan="15" align="center">
		<font color="blue">
			<b>ChargeBack List</b>
		</font>
	</td>
    </tr>
    </table>
    <table width="95%" cellpadding="0" cellspacing="1" border="1" bordercolor="#C4D2E7">
	  <tr>
        <td width="3%" height="25" align="center" class="list_title">No</td>
        <td width="10%" align="center"  class="list_title">Transaction Id</td>
        <td width="8%" align="center" class="list_title">Merchant Id</td>
        <td width="8%" align="center" class="list_title">Mall Id</td>
        <td width="11%" align="center"  class="list_title">Merchant Ref.</td>
        <td width="8%" align="center"  class="list_title">Payer Name</td>
        <td width="8%" align="center"  class="list_title">Payer Email</td>
        <td width="8%" align="center"  class="list_title">Payer TelNo</td>
        <td width="4%" align="center" class="list_title">Currency</td>
        <td width="6%" align="center" class="list_title">Amount</td>
        <td width="10%" align="center"  class="list_title">Card Number</td>
        <td width="6%" align="center"  class="list_title">Card Brand</td>
        <td width="6%" align="center" class="list_title">Approval Number</td>
        <td width="10%" align="center"  class="list_title">Request Date</td>
        <td width="10%" align="center"  class="list_title">Response Date</td>
      </tr>
      <tr>
        <td colspan="15" height="2" bgcolor="#1b6c8e"></td>
      </tr>
      <c:forEach var="list" items="${cbList}" varStatus="status">
      	<tr>
      		<td align="center" class="list_02" style="border-left:0 none;">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
      		<td align="center" class="list_02" style='mso-number-format:"\@";'>${list.transactionId}</td>
      		<td align="center" class="list_02">${list.merchantId }</td>
      		<td align="center" class="list_02">${list.mallId }</td>
      		<td align="center" class="list_02" style="border-left:0 none;">&nbsp;${list.payNo}</td>
      		<td align="center" class="list_02">${list.payName }</td>
      		<td align="center" class="list_02">${list.payEmail }</td>
      		<td align="center" class="list_02">${list.payTelNo }</td>
      		<td align="center" class="list_02">${list.curType }</td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02">${list.temp1String}</td>
      		<td align="center" class="list_02">${list.temp2String}</td>
      		<td align="center" class="list_02">&nbsp;${list.approvalNo}</td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.trnReqDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.trnResDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      	</tr>
      </c:forEach>
    </table>
</span>
</div>
</font>
</body>
</html>