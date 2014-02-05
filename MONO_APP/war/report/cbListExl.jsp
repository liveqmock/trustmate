<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="com.pgmate.web.util.ExcelUtil,biz.trustnet.common.util.CommonUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%
	String fileName = "CB_"+CommonUtil.getCurrentDate("yyyyMMddHHmmss");
	response = new ExcelUtil().toExcelResponse(request,response,fileName);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv=Content-Type content='text/html; charset=euc-kr'>
<link href="/common/css/search.css" rel="stylesheet" type="text/css" />
<html>
	<title>MONO - CHARGEBACK LIST</title>
	<!-- ��Ÿ�� ��Ű�� -->
	<style type="text/css">
	<!--
		table { font-family: "����", "Seoul", "Arial", "Helvetica"; font-size: 10pt; color: #333333}
	}
	-->
	</style>
<body>
<font size="2">
<div ID="objContents">
<span id="d1">
    <table width="95%" cellpadding="0" cellspacing="1" border="1" bordercolor="#C4D2E7">
	  <tr>
        <td width="5%" height="25" align="center" class="list_title">����</td>
        <td width="20%" align="center"  class="list_title">�ŷ���ȣ</td>
        <td width="15%" align="center"  class="list_title">����</td>
        <td width="15%" align="center" class="list_title">�ݾ�</td>
        <td width="15%" align="center"  class="list_title">���ŷ�����</td>
        <td width="5%" align="center" class="list_title">���ۻ���</td>
        <td width="25%" align="center"  class="list_title">����Ͻ�</td>
      </tr>
      <tr>
        <td colspan="7" height="2" bgcolor="#1b6c8e"></td>
      </tr>
      <c:forEach var="list" items="${cbList}" varStatus="status">
      	<tr>
      		<td align="center" class="list_02">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
      		<td align="center" class="list_02"><strong>&nbsp;${list.transactionId}</strong></td>
      		<td align="center" class="list_02">
      			<c:if test="${list.cbState == '1' }">���</c:if>
      			<c:if test="${list.cbState == '2' }">ó����</c:if>
      			<c:if test="${list.cbState == '3' }">ó���Ϸ�</c:if>
      			<c:if test="${list.cbState == '4' }">�ݷ�</c:if>
      		</td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.temp1Long}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02">${list.rootTrnStatus}</td>
      		<td align="center" class="list_02">${list.flag}</td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
      	</tr>
      </c:forEach>
    </table>
</span>
</div>
</font>
</body>
</html>