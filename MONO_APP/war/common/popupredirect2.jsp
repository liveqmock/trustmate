<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<script>
		window.onload = function() {
		  <c:if test="${!empty message}">alert('${message}');</c:if>
		 window.close();
		}
	</script>
</html>
