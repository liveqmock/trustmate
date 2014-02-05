<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/merchant/include/header.jsp" />
	
	<!-- 상단 메인 링크 페이지 종료 -->
		<!-- content-->
		<div id="content">
			<!-- end content / left -->
			<div id="left">
				<c:import url="/merchant/include/leftMenu.jsp" />
			</div>
			<!-- end content / left -->
			<!-- content / right -->
			<div id="right">				
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5>INQUIRY</h5>
					</div>
					<!-- end box / title -->
					<form id="form" action="" method="post">
					<div class="form">
						<div class="fields">
							<div class="field  field-first">
								<div class="label">
									<label for="input-valid">Subject</label>
								</div>
								<div class="input" id="paddingL40">
									<input type="text" id="subject" name="subject" class="small valid" size="100" />
								</div>
							</div>
							<div class="field">
								<div class="label">
									<label for="input-valid">Email</label>
								</div>
								<div class="input" id="paddingL40">
									<input type="text" id="email" name="email" class="small" size="30" />
								</div>
							</div>
							<div class="field">
								<div class="label">
									<label for="input-valid">Name</label>
								</div>
								<div class="input" id="paddingL40">
									<input type="text" id="memberName" name="memberName" class="small" size="30" />
								</div>
							</div>
							
							<div class="field">
								<div class="label label-textarea">
									<label for="textarea">Contents</label>
								</div>
								<div class="textarea textarea-editor">
									<textarea id="textarea" name="textarea" cols="60" rows="12"></textarea>
								</div>
							</div>
							<div class="buttons" align="center">
								<input type="submit" name="submit" value="Submit" />
								<input type="reset" name="reset" value="Reset" />
							</div>
						</div>
					</div>
					</form>
				</div>
				<div class="box">
					<div style="height: 520px;"></div>
				</div>
			</div>
			<!-- end content / right -->
		</div>
		<!-- end content -->
		<!-- footer -->
		<c:import url="/merchant/include/footer.jsp" />
		<!-- end footert -->
	</body>
</html>

