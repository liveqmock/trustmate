<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="com.pgmate.web.util.ExcelUtil,biz.trustnet.common.util.CommonUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%
	String fileName = "MERCHANT_"+CommonUtil.getCurrentDate("yyyyMMddHHmmss");
	response = new ExcelUtil().toExcelResponse(request,response,fileName);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv=Content-Type content='text/html; charset=euc-kr'>
<link href="/common/css/search.css" rel="stylesheet" type="text/css" />
<html>
	<title>PGMATE - MERCHANT LIST</title>
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
        <td width="3%" height="25" align="center" class="list_title">����</td>
        <td width="14%" align="center"  class="list_title">������ ���̵�</td>
        <td width="15%" align="center" class="list_title">������ �̸�</td>
        <td width="10%" align="center" class="list_title">������ ����ó</td>
        <td width="10%" align="center"  class="list_title">������ �ѽ���ȣ</td>
        <td width="12%" align="center"  class="list_title">������ �̸���</td>
        <td width="8%" align="center" class="list_title">����</td>
        <td width="14%" align="center"  class="list_title">���� ����</td>
        <td width="14%" align="center"  class="list_title">��� ����</td>
      </tr>
      <tr>
        <td colspan="9" height="2" bgcolor="#1b6c8e"></td>
      </tr>
      <c:forEach var="list" items="${mList}" varStatus="status">
      	<tr>
      		<td align="center" class="list_02">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
      		<td align="center" class="list_02"><strong>${list.merchantId}</strong></td>
      		<td align="center" class="list_02">${list.name }</td>
      		<td align="center" class="list_02">${list.telNo }</td>
      		<td align="center" class="list_02">${list.fax}</td>
      		<td align="center" class="list_02">${list.email}</td>
      		<td align="center" class="list_02">
      			<c:choose>
					<c:when test="${list.active == '0'}"><font color="#800080">����</font>
					</c:when>
					<c:when test="${list.active == '1'}"><font color="blue">���</font>
					</c:when>
					<c:when test="${list.active == '2'}"><font color="green">����</font>
					</c:when>
					<c:when test="${list.active == '3'}"><font color="red">����</font>
					</c:when>
					<c:otherwise>
						${list.active }
					</c:otherwise>
				</c:choose>		
      		</td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.serviceDate}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
      	</tr>
      </c:forEach>
    </table>
</span>
</div>
</font>
</body>
</html>