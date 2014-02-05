<%@page contentType="text/plain; charset=utf-8" %>
<%@page import="java.io.*,biz.trustnet.common.log.Log"%>
<%
	response.reset();
	response.setContentType("text/plain; charset=utf-8");
	PrintWriter writer = response.getWriter();
	String textResponse = (String)request.getAttribute("PAYRESPONSE");
	writer.write(textResponse);
	writer.flush();
	writer.close();
%>