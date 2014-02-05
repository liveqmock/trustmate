<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>MONO Admin</title>
		<meta http-equiv="popContent-Type" popContent="text/html;charset=utf-8" />
		<!-- stylesheets -->
		<link rel="stylesheet" type="text/css" href="/resources/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="/resources/css/pop_style.css" media="screen" />
		<link id="color" rel="stylesheet" type="text/css" href="/resources/css/colors/blue.css" />
		<!-- scripts (jquery) -->
		<script src="/resources/scripts/jquery-1.6.4.min.js" type="text/javascript"></script>
		<!--[if IE]><script language="javascript" type="text/javascript" src="/resources/scripts/excanvas.min.js"></script><![endif]-->
		<script src="/resources/scripts/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
		<script src="/resources/scripts/jquery.ui.selectmenu.js" type="text/javascript"></script>
		<script src="/resources/scripts/jquery.flot.min.js" type="text/javascript"></script>
		<script src="/resources/scripts/tiny_mce/tiny_mce.js" type="text/javascript"></script>
		<script src="/resources/scripts/tiny_mce/jquery.tinymce.js" type="text/javascript"></script>
		<!-- scripts (custom)-->
		<script src="/resources/scripts/smooth.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.menu.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.table.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.form.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.dialog.js" type="text/javascript"></script>
		<script src="/resources/scripts/smooth.autocomplete.js" type="text/javascript"></script>
		<script src="/common/js/common.js" type="text/javascript"></script>
		<script type="text/javascript">
			function selectgroup(){
				frm = document.groupSelect;
				
				if(frm.groupId.value == "" && frm.checkGroup.value !=""){
					if(confirm("그룹 해제를 하시겠습니까?")){
						opener.location.href="/member.do?request=groupSelect&merchantId="+frm.merchantId.value;
						self.close();
					}else{
						frm.groupId.focus();
					}
				}else{
					opener.location.href="/member.do?request=groupSelect&merchantId="+frm.merchantId.value+"&groupId="+frm.groupId.value;
					self.close();
				}
			
			}
		</script>
	</head>
	
	<body>
		<div id="content">
			<div class="box">
				<!-- box / title -->
				<div class="title">
					<h5>Select Group</h5>
				</div>
				<form name="groupSelect" method="post">
				<div class="form">
				<input type="hidden" name="merchantId" value="${merchantId }" />
				<input type="hidden" name="checkGroup" value="${groupId }" />
        			<div class="fields">
        				<div class="field">
							<div class="label">
								<label for="group">Group List</label>
							</div>
							<div class="input" style="padding-left: 50px">
								<c:choose>
									<c:when test="${fn:length(glist) != 0}">
										<select name="groupId">
											<option value="">-------------</option>
											<c:forEach var="list" items="${glist}" varStatus="status">
												<option value="${list.groupId }" <c:if test="${list.groupId == groupId}">selected</c:if>>${list.groupId }&nbsp;[${list.name }]</option>
											</c:forEach>
										</select>
									</c:when>
									<c:otherwise>&nbsp;</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="action">
						<div class="button"><!--추가 -->
							<input type="submit" name="submit" value="CONFIRM" onclick="javascript:selectgroup();return false;" />
						</div>
					</div>
				</div>
				</form>
			</div>
		</div>
	</body>
</html>