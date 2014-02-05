<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%
	if(session != null){
		session.removeAttribute("sso");
		session.invalidate();
	}
	response.sendRedirect("/sso/websso.jsp");
	

%>