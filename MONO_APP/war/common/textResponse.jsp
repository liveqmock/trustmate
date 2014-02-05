<%@page contentType="text/plain; charset=euc-kr" %>
<%@page import="java.io.*,biz.trustnet.common.log.Log"%>
<%
	response.reset();
	response.setContentType("text/plain; charset=euc-kr");
	PrintWriter writer = response.getWriter();
	String textResponse = (String)request.getAttribute("RESPONSE");
	writer.write(textResponse);
	writer.flush();
	writer.close();
%>