<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/include/header.jsp" />
<script type="text/javascript">
	$(document).ready(function () {
	
		$("select").selectmenu({
	        style: 'dropdown',
	        width: 350,
	        menuWidth: 350,
	        icons: [
			    { find: '.locked', icon: 'ui-icon-locked' },
			    { find: '.unlocked', icon: 'ui-icon-unlocked' },
			    { find: '.folder-open', icon: 'ui-icon-folder-open' }
		    ]
	    });
    });
	function update() {	
		
		frm = document.invoiceList;
		document.location.href='/report.do?request=invoiceForm&merchantId='+frm.merchantId.value;
	}

</script>	

	<!-- 상단 메인 링크 페이지 종료 -->
		<!-- content-->
		<div id="content">
			<!-- end content / left -->
			<div id="left">
				<c:import url="/include/leftMenu.jsp" />
			</div>
			<!-- end content / left -->
			<!-- content / right -->
			<div id="right">				
				<!-- table -->
				<!-- forms -->
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5>청구서확인</h5>
					</div>
					
					<div class="form">
						<form name="invoiceList" action="/report.do?request=invoice" method="post">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="merchantId">가맹점아이디</label>
								</div>
								<div class="input">
									 <c:choose>
									<c:when test="${fn:length(merchantList) != 0}">
				              			<select name="merchantId"  tabindex="1" onchange="javascript:update();">
				              				<option value="">-----&nbsp;</option>
				              				<c:forEach var="list" items="${merchantList}" varStatus="status">
				              					<option value="${list.merchantId }" <c:if test="${list.merchantId == mBean.merchantId}">selected</c:if>>${list.merchantId}</option>
				              				</c:forEach>
										</select>
									</c:when>
									<c:otherwise>
										<input name="merchantId" type="text"  maxlength="16" tabindex="1" value="${mBean.merchantId}" />
									</c:otherwise>
								</c:choose>
								</div>
								<div class="label2">
									<label for="qualification">&nbsp;</label>
								</div>
								<div class="input2">&nbsp;
								</div>
								
							</div>
							<div class="field">
								<div class="label">
									<label for="invoiceIdx">청구서번호</label>
								</div>
								<div class="input">
									<c:choose>
										<c:when test="${fn:length(sList) != 0}">
					               			<select name="invoiceIdx" class="text200" tabindex="3" style="width:350px;">
					               				<option value="">-----&nbsp;</option>
					               				<c:forEach var="list" items="${sList}" varStatus="status">
					               					<option value="${list.invoiceIdx }" <c:if test="${list.invoiceIdx == sBean.invoiceIdx}">selected</c:if>>${list.invoiceIdx }&nbsp;[${list.temp1String }&nbsp; ~ &nbsp;${list.temp2String }]</option>
					               				</c:forEach>
											</select>
										</c:when>
										<c:otherwise>
											<input name="invoiceIdx" type="text" class="small valid" maxlength="50" tabindex="3" value="" style="width:200px;"/>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="label2">
									<label for="qualification">&nbsp;</label>
								</div>
								<div class="input2">&nbsp;
								</div>
								
							</div>
							
						</div>
						
						<!-- table action -->
						<div class="action">
							<div class="button">
								<input type="submit" name="submit" value="search"/>
							</div>
						</div>
						<!-- end table action -->			
						</form>
					</div>
					
				</div>
				<!-- end forms -->
				<div class="box">
					<div style="height: 800px;"></div>
				</div>
				<!-- end table -->
			</div>
			<!-- end content / right -->
		</div>
		<!-- end content -->
		<!-- footer -->
		<c:import url="/include/footer.jsp" />
		<!-- end footert -->
	</body>
</html>

