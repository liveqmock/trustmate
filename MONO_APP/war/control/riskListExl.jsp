<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="com.pgmate.web.util.ExcelUtil,biz.trustnet.common.util.CommonUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%
	String fileName = "RISK_"+CommonUtil.getCurrentDate("yyyyMMddHHmmss");
	response = new ExcelUtil().toExcelResponse(request,response,fileName);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv=Content-Type content='text/html; charset=euc-kr'>
<link href="/common/css/search.css" rel="stylesheet" type="text/css" />
<html>
	<title>HANA - RISK LIST</title>
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
        <td width="3%" height="25" align="center" class="list_title">순번</td>
        <td width="16%" align="center"  class="list_title">정보</td>
        <td width="13%" align="center"  class="list_title">상태</td>
        <td width="12%" align="center"  class="list_title">등록사유</td>
        <td width="16%" align="center"  class="list_title">등록일시</td>
      </tr>
      <tr>
        <td colspan="5" height="2" bgcolor="#1b6c8e"></td>
      </tr>
      <c:forEach var="list" items="${trList}" varStatus="status">
      	<tr>
      		<td align="center" class="list_02">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
      		<td align="center" class="list_02"><strong>&nbsp;${list.unit}</strong></td>
      		<td align="center" class="list_02">
      			<c:choose>
					<c:when test="${list.active == 'Y'}"><font color="red">차단</font>
					</c:when>
					<c:when test="${list.active == 'N'}"><font color="blue">해제</font>
					</c:when>
					<c:otherwise>
						${list.active }
					</c:otherwise>
				</c:choose>		
      		</td>
      		<td align="center" class="list_02">&nbsp;${list.comments}</td>
      		<td align="center" class="list_02"><fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
      	</tr>
      </c:forEach>
    </table>
</span>
</div>
</font>
</body>
</html>