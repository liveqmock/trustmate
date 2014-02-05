<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.pgmate.web.util.ExcelUtil,biz.trustnet.common.util.CommonUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%
	String fileName = "Settlement_"+CommonUtil.getCurrentDate("yyyyMMddHHmmss");
	response = new ExcelUtil().toExcelResponse(request,response,fileName);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv=Content-Type content='text/html; charset=utf-8'>
<link href="/common/css/search.css" rel="stylesheet" type="text/css" />
<html>
	<title>HANA - SETTLEMENT TRANSACTION LIST</title>
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
	<td colspan="13" align="center">
		<font color="blue">
			<b>Settlement List</b>
		</font>
	</td>
    </tr>
    </table>
    <table width="95%" cellpadding="0" cellspacing="1" border="1" bordercolor="#C4D2E7">
	  <tr>
        <td width="3%" height="25" align="center" class="list_title">No</td>
        <td align="center"  class="list_title">Invoice No</td>
        <td align="center" class="list_title">Merchant Id</td>
        <td align="center" class="list_title">Type</td>
        <td align="center"  class="list_title">Period</td>
        <td align="center"  class="list_title">Count</td>
        <td align="center"  class="list_title">Transaction Amount</td>
        <td align="center"  class="list_title">Fee Amount</td>
        <td align="center" class="list_title">Deposit</td>
        <td align="center" class="list_title">Total Amount</td>
        <td align="center"  class="list_title">Status</td>
        <td align="center"  class="list_title">Pay Date</td>
        <td align="center" class="list_title">Reg Date</td>
      </tr>
      <tr>
        <td colspan="13" height="2" bgcolor="#1b6c8e"></td>
      </tr>
      <c:forEach var="list" items="${paidList}" varStatus="status">
      	<tr>
      		<td align="center" class="list_02" style="border-left:0 none;">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
      		<td align="center" class="list_02" style='mso-number-format:"\@";'>${list.settleId}</td>
      		<td align="center" class="list_02">${list.merchantId }</td>
      		<td align="center" class="list_02">${list.period }</td>
      		<td align="center" class="list_02">${list.startDay}~${list.endDay}</td>
      		<td align="center" class="list_02">${list.settleCnt}</td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.totAmt}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.totFee}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.totDeposit}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.totSettle}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02">
      			<c:choose>
      				<c:when test="${list.status =='R'}">정산예정</c:when>
      				<c:when test="${list.status =='C'}">정산확정</c:when>
      				<c:when test="${list.status =='Y'}">지급완료</c:when>
      				<c:otherwise>&nbsp;</c:otherwise>
      			</c:choose>
      		</td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.settleDate}" pattern="yyyy/MM/dd" /></td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd" /></td>
      	</tr>
      </c:forEach>
    </table>
</span>
</div>
</font>
</body>
</html>