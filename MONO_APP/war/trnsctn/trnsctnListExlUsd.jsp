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
	<title>PGMATE - TRANSACTION LIST</title>
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
        <td width="3%" height="25" align="center" class="list_title">순번</td>
        <td width="10%" align="center"  class="list_title">거래번호</td>
        <td width="8%" align="center" class="list_title">가맹점 아이디</td>
        <td width="8%" align="center" class="list_title">몰 아이디</td>
        <td width="11%" align="center"  class="list_title">주문번호</td>
        <td width="8%" align="center"  class="list_title">상태</td>
        <td width="4%" align="center" class="list_title">통화</td>
        <td width="6%" align="center" class="list_title">금액</td>
        <td width="10%" align="center"  class="list_title">카드번호</td>
        <td width="6%" align="center"  class="list_title">카드브랜드</td>
        <td width="6%" align="center" class="list_title">승인번호</td>
        <td width="10%" align="center"  class="list_title">결제요청일시</td>
        <td width="10%" align="center"  class="list_title">결제응답일시</td>
      </tr>
      <tr>
        <td colspan="13" height="2" bgcolor="#1b6c8e"></td>
      </tr>
      <c:forEach var="list" items="${trnsctnList}" varStatus="status">
      	<tr>
      		<td align="center" class="list_02" style="border-left:0 none;">${((dao.curPage-1)*dao.pageSize +status.count)}</td>
      		<td align="center" class="list_02" style='mso-number-format:"\@";'>${list.transactionId}</td>
      		<td align="center" class="list_02">${list.merchantId }</td>
      		<td align="center" class="list_02">${list.mallId }</td>
      		<td align="center" class="list_02" style="border-left:0 none;">&nbsp;${list.payNo}</td>
      		<td align="center" class="list_02">
      			<c:choose>
					<c:when test="${list.trnStatus == '00'}"><font color="#800080">승인요청중</font>
					</c:when>
					<c:when test="${list.trnStatus == '01'}"><font color="red">승인장애</font>
					</c:when>
					<c:when test="${list.trnStatus == '02'}"><font color="blue">승인완료</font>
					</c:when>
					<c:when test="${list.trnStatus == '03'}">승인실패
					</c:when>
					<c:when test="${list.trnStatus == '04'}"><font color="red">승인취소요청</font>
					</c:when>
					<c:when test="${list.trnStatus == '05'}"><font color="red">승인취소장애</font>
					</c:when>
					<c:when test="${list.trnStatus == '06'}">승인취소
					</c:when>
					<c:when test="${list.trnStatus == '07'}"><font color="blue">매입요청</font>
					</c:when>
					<c:when test="${list.trnStatus == '08'}"><font color="blue">매입중</font>
					</c:when>
					<c:when test="${list.trnStatus == '09'}"><font color="blue">매입완료</font>
					</c:when>
					<c:when test="${list.trnStatus == '10'}"><font color="red">매입반려</font>
					</c:when>
					<c:when test="${list.trnStatus == '11'}"><font color="blue">정산대기</font>
					</c:when>
					<c:when test="${list.trnStatus == '12'}"><font color="blue">정산요청</font>
					</c:when>
					<c:when test="${list.trnStatus == '13'}"><font color="blue">정산완료</font>
					</c:when>
					<c:when test="${list.trnStatus == '14'}">정산후취소
					</c:when>
					<c:when test="${list.trnStatus == '15'}">환불요청
					</c:when>
					<c:when test="${list.trnStatus == '17'}"><font color="red">취소대기</font>
					</c:when>
					<c:when test="${list.trnStatus == '18'}"><font color="red">매입취소</font>
					</c:when>
					<c:when test="${list.trnStatus == '19'}"><font color="red">매입취소반려</font>
					</c:when>
					<c:when test="${list.trnStatus == '20'}"><font color="red">매입취소중</font>
					</c:when>
					<c:when test="${list.trnStatus == '21'}"><font color="red">매입실패</font>
					</c:when>
					<c:when test="${list.trnStatus == '22'}"><font color="red">CB 등록</font>
					</c:when>
					<c:when test="${list.trnStatus == '23'}"><font color="red">CB 확정</font>
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