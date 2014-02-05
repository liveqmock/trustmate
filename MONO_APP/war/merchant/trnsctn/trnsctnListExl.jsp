<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.pgmate.web.util.ExcelUtil,biz.trustnet.common.util.CommonUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%
	String fileName = "TRNSCTN_"+CommonUtil.getCurrentDate("yyyyMMddHHmmss");
	response = new ExcelUtil().toExcelResponse(request,response,fileName);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv=Content-Type content='text/html; charset=utf-8'>
<link href="/common/css/search.css" rel="stylesheet" type="text/css" />
<html>
	<title>PANWORLDNETWORK - TRANSACTION LIST</title>
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
    <table width="95%" cellpadding="0" cellspacing="1" border="1" bordercolor="#C4D2E7">
	  <tr>
        <td width="3%" height="25" align="center" class="list_title">No</td>
        <td width="12%" align="center"  class="list_title">Transaction Id</td>
        <td width="13%" align="center"  class="list_title">Merchant Ref.</td>
        <td width="12%" align="center" class="list_title">Product Name</td>
        <td width="13%" align="center"  class="list_title">Status</td>
        <td width="4%" align="center" class="list_title">Currency</td>
        <td width="8%" align="center" class="list_title">Amount</td>
        <td width="10%" align="center"  class="list_title">Card Number</td>
        <td width="7%" align="center"  class="list_title">Card Brand</td>
        <td width="8%" align="center" class="list_title">Approval Number</td>
        <td width="10%" align="center"  class="list_title">Request Date</td>
      </tr>
      <tr>
        <td colspan="11" height="2" bgcolor="#1b6c8e"></td>
      </tr>
      <c:forEach var="list" items="${trnsctnList}" varStatus="status">
      	<tr>
      		<td align="center" class="list_02">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
      		<td align="center" class="list_02" style='mso-number-format:"\@";'><strong>&nbsp;${list.transactionId}</strong></td>
      		<td align="center" class="list_02">&nbsp;${list.payNo}</td>
      		<td align="center" class="list_02">${list.productName}</td>
      		<td align="center" class="list_02">
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
					<c:when test="${list.trnStatus == '15'}">Refund request
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
      		<td align="center" class="list_02">${list.curType }</td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02">${list.temp1String}</td>
      		<td align="center" class="list_02">${list.temp2String}</td>
      		<td align="center" class="list_02">${list.approvalNo}</td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.trnReqDate}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
      	</tr>
      </c:forEach>
    </table>
</span>
</div>
</font>
</body>
</html>