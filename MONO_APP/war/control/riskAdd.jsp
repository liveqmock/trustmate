<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/include/header.jsp" />
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
				<div class="box">
					<!-- forms -->
					<!-- box / title -->
					<div class="title">
						<h5>Risk 정보 등록</h5>
					</div>
					<form action="/manage.do?request=riskAdd" name="editForm" method="post">
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h6> &nbsp; </h6>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">정보</label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"><input type="text" name="unit" value="" style="width:200px;"/></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">관리자명</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<select name="active">
											<option value="Y" >차단</option>
											<option value="N" >해제</option>
										</select>
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">등록사유</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="comments" value="" style="width:600px;"/></span>
								</div>						
							</div>
						</div>
						
						<div class="action">
							<div class="button">
								<input type="submit" name="submit" value="Regist" onclick="javascript:document.editForm.submit();"/>
							</div>
						</div>
					</div>
					</form>
				</div>	
			</div>
			<!-- end content / right -->
		</div>
		<!-- end content -->
		<!-- footer -->
		<c:import url="/include/footer.jsp" />
		<!-- end footert -->
		
	</body>
</html>
