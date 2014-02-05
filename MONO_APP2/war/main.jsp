<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="ko" class="no-js">
<c:import url="/include/header.jsp" />
<!-- BEGIN CONTAINER -->
<div class="page-container">
<c:import url="/include/leftMenu.jsp" />	
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<!-- END PAGE CONTENT-->
		</div>
	</div>
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<c:import url="/include/footer.jsp" />	
<script>
	jQuery(document).ready(function(){
		$.ajax({
			url : '${sso.targetURL}',
			type : 'POST',
			success : function(msg){
				$('.page-content').html(msg);
			},
			error : function(result){
				alert("Could not load the requested content.");
			}
		});
	});
</script>
</html>
