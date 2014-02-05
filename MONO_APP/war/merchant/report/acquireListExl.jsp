<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.pgmate.web.util.ExcelUtil,biz.trustnet.common.util.CommonUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%
	String fileName = "COLLECT_"+CommonUtil.getCurrentDate("yyyyMMddHHmmss");
	response = new ExcelUtil().toExcelResponse(request,response,fileName);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv=Content-Type content='text/html; charset=utf-8'>
<link href="/common/css/search.css" rel="stylesheet" type="text/css" />
<html>
	<title>PANWORLDNETWORK - COLLECT LIST</title>
	<!-- 스타일 패키지 -->
	<style type="text/css">
	<!--
		table { font-family: "돋음", "Seoul", "Arial", "Helvetica"; font-size: 10pt; color: #333333}
	}
	-->
	</style>
<body>
<font size="2">
<div ID="objContents">
<span id="d1">
    <table width="95%" cellpadding="0" cellspacing="1" border="1" bordercolor="#C4D2E7">
	  <tr>
        <td width="5%" height="25" align="center" class="list_title">No</td>
        <td width="16%" align="center"  class="list_title">Transaction Id</td>
        <td width="15%" align="center"  class="list_title">Status</td>
        <td width="14%" align="center"  class="list_title">Collect Date</td>
        <td width="14%" align="center" class="list_title">Amount(USD)</td>
        <td width="10%" align="center"  class="list_title">Card Error</td>
        <td width="10%" align="center"  class="list_title">Van Error</td>
        <td width="16%" align="center"  class="list_title">Request Date</td>
      </tr>
      <tr>
        <td colspan="8" height="2" bgcolor="#1b6c8e"></td>
      </tr>
      <c:forEach var="list" items="${acquireList}" varStatus="status">
      	<tr>
      		<td align="center" class="list_02">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
      		<td align="center" class="list_02"><strong>&nbsp;${list.transactionId}</strong></td>
      		<td align="center" class="list_02">
      			<c:choose>
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
      		<td align="center" class="list_02">${list.acquireDate}</td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02">${list.cardErrCd}</td>
      		<td align="center" class="list_02">${list.vanErrCd}</td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
      	</tr>
      </c:forEach>
    </table>
</span>
</div>
</font>
</body>
</html>