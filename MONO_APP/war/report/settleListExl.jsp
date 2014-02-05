<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="com.pgmate.web.util.ExcelUtil,biz.trustnet.common.util.CommonUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%
	String fileName = "PAID_"+CommonUtil.getCurrentDate("yyyyMMddHHmmss");
	response = new ExcelUtil().toExcelResponse(request,response,fileName);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv=Content-Type content='text/html; charset=euc-kr'>
<link href="/common/css/search.css" rel="stylesheet" type="text/css" />
<html>
	<title>MONO - PAID LIST</title>
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
        <td width="5%" height="25" align="center" class="list_title">순번</td>
        <td width="8%" align="center"  class="list_title">정산번호</td>
        <td width="13%" align="center"  class="list_title">가맹점아이디</td>
        <td width="12%" align="center"  class="list_title">거래(건/금액)</td>
        <td width="12%" align="center"  class="list_title">차지백(건/금액)</td>
        <td width="12%" align="center" class="list_title">취소(건/금액)</td>
        <td width="12%" align="center" class="list_title">수수료(건/금액)</td>
        <td width="13%" align="center" class="list_title">시작일</td>
        <td width="13%" align="center"  class="list_title">종료일</td>
      </tr>
      <tr>
        <td colspan="9" height="2" bgcolor="#1b6c8e"></td>
      </tr>
      <c:forEach var="list" items="${settlementList}" varStatus="status">
      	<tr>
      		<td align="center" class="list_02">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
      		<td align="center" class="list_02"><strong>${list.idx}</strong></td>
      		<td align="center" class="list_02"><strong>${list.merchantId}</strong></td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.temp1Long}" pattern="#,##0" />&nbsp;/&nbsp;<fmt:formatNumber type="number" value="${list.temp2Long}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.temp3Long}" pattern="#,##0" />&nbsp;/&nbsp;<fmt:formatNumber type="number" value="${list.temp4Long}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.temp5Long}" pattern="#,##0" />&nbsp;/&nbsp;<fmt:formatNumber type="number" value="${list.temp6Long}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02"><fmt:formatNumber type="number" value="${list.temp7Long}" pattern="#,##0" />&nbsp;/&nbsp;<fmt:formatNumber type="number" value="${list.temp8Long}" pattern="#,##0.00" /></td>
      		<td align="center" class="list_02">${list.temp1String}</td>
      		<td align="center" class="list_02">${list.temp2String}</td>
      	</tr>
      </c:forEach>
    </table>
</span>
</div>
</font>
</body>
</html>