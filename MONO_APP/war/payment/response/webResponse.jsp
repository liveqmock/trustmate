<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
	response.reset();
	response.setContentType("text/html; charset=utf-8");
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8\"> 
		<title>::::::::PGMS PAYMENT GATEWAY:::::::::::</title>  
			<script language="javascript">										  
				window.onload = function() {
				<c:if test="${RESULT_CD != '0'}">
					alert('Payment Declined  [message : ${MESSAGE}]');	
				</c:if>
					document.pay.submit();          								  
				};									  
			</script> 																  
	</head>																	  
	<body>																	  
	<form name="pay" action="${REDIRECT_URL }" method="POST">
		${PAYRESPONSE}		
	</form>	                                                                  
	</body>																	  
</html>  																  