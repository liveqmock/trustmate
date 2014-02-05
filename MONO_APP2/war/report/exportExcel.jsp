<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="biz.trustnet.common.util.CommonUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%
	String fileName = "PGMS_"+CommonUtil.getCurrentDate("yyyyMMddHHmmss");
	
	String agent=request.getHeader("USER-AGENT");
		String requestUrl = request.getRequestURL().toString().toLowerCase();
		response.reset();
		if (agent.indexOf("MSIE 6.0") > -1 || agent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-type", "application/octet-stream;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
			response.setHeader("Content-Transfer-ENCODING", "binary");
			if(!requestUrl.startsWith("https")){
				response.setHeader("Pragma", "no-cache");
			}
			response.setHeader("Cache-Control", "private");
			response.setHeader("Expires", "0");
		} else {
			//response.setHeader("Content-type", "file/unknown");
			response.setHeader("Content-type", "application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
			response.setHeader("Content-Description", "Servlet Generated Data");
			if(!requestUrl.startsWith("https")){
				response.setHeader("Pragma", "no-cache");
			}
			response.setHeader("Cache-Control", "private");
			response.setHeader("Expires", "0");
		}
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv=Content-Type content='text/html; charset=utf-8'>
<html>
	<title>PGMATE - TRANSACTION LIST</title>
	<style type="text/css">
	<!--
		table { font-family: "Seoul", "Arial", "Helvetica"; font-size: 10pt; color: #333333 ;border-collapse:collapse;border:1px gray black;text-align:center;}

	-->
	</style>
<body>
<font size="2">
	<div class="portlet-body flip-scroll">
		<div class="table-responsive">
		<table class="table table-striped table-bordered table-hover flip-content" id="sortTable"><!-- 작게 table-condensed -->
		<thead>
			<tr>
				<th>No</th>
				<th data-sort="float">Transaction ID</th>
				<th data-sort="string">Merchant ID</th>
				<th>Mall ID</th>
				<th data-sort="string">Order No</th>
				<th data-sort="string">Status</th>
				<th data-sort="string">Currency</th>
				<th data-sort="float">Amount</th>
				<th data-sort="float">Card No</th>
				<th data-sort="string">Brand</th>
				<th data-sort="string">Approval No</th>
				<th data-sort="string">UserId</th>
				<th data-sort="string">UserName</th>
				<th data-sort="string">UserEmail</th>
				<th data-sort="string">TelNo</th>
				<th data-sort="string">ProductName</th>
				<th data-sort="string">IpAddress</th>
				<th data-sort="string">Ip Geo</th>
				<th data-sort="string">Van</th>
				<th data-sort="string">Van ID</th>
				<th data-sort="string">URL</th>
				<th data-sort="string">Result Cd</th>
				<th data-sort="string">Result Msg</th>
				<th data-sort="string">Request Date</th>
				<th data-sort="string">Response Date</th>
				<th data-sort="string">Transaction Date</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${fn:length(trnsctnList) != 0}">
					<c:forEach var="list" items="${trnsctnList}" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td style='mso-number-format:"\@";'>${list.transactionId}</td>
							<td>${list.merchantId }</td>
							<td>${list.mallId }</td>
							<td style='mso-number-format:"\@";'>${list.payNo }</td>
							<td>${list.trnStatus}</td>
							<td>${list.curType}</td>
							<td><fmt:formatNumber type="number" value="${list.amount}" pattern="#,##0.00" /></td>
							<td>${list.temp1String}</td>
							<td>${list.temp2String}</td>  
							<td>${list.approvalNo}</td>
							<td>${list.payUserId}</td>
							<td>${list.payName}</td>
							<td>${list.payEmail}</td>
							<td>${list.payTelNo}</td>
							<td>${list.productName}</td>
							<td>${list.ipAddress}</td>
							<td>${list.temp3String}</td>
							<td>${list.van}</td>
							<td>${list.temp2}</td>
							<td>${list.temp1}</td>
							<td>${list.resultCd}</td>
							<td>${list.resultMsg}</td>
							<td><fmt:formatDate type="both" value="${list.trnReqDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
							<td><fmt:formatDate type="both" value="${list.trnResDate}" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</td>
							<td>${list.trnDate}</td>
						</tr>
					</c:forEach>
				</c:when>
				
			</c:choose>
				
		</tbody>
		</table>
		</div>
	</div>
</font>
</body>
</html>