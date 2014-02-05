<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script language=JavaScript>
<!--
	function noticeCheck(){
		var frm = document.noticeWrite;
		
		if(isBlank(frm.subject,"Please input Subject") ){
		}else{
			frm.submit();
		}
	}
	
//-->
</script>

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
						<h5>공지 작성</h5>
					</div>
					<form name="noticeWrite" action="/notice.do?request=regist" method="post">
					<div class="form">
						<div class="fields">						
							<div class="subtitle" id="paddingL20">
								<h5>[ NOTICE ] </h5>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="subject">제목</label>
								</div>
								<div class="input" id="paddingL60">
									<input type="text" id="subject" name="subject" class="text800" />
								</div>

							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="contents">내용</label>
								</div>
								<div class="textarea">
									<textarea id="contents" name="contents" cols="80" rows="50"> </textarea>
								</div>
							</div>
						</div>
					</div>
					<div class="action">
						<div class="button"><!-- 추가 -->
							<input type="submit" name="submit" value="check" onclick="javascript:noticeCheck();" />
						</div>
					</div>
					</form>
				<!-- end forms -->
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
