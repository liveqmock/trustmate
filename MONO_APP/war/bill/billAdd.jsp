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
				<div class="box">
					<!-- forms -->
					<!-- box / title -->
					<div class="title">
						<h5>가맹점 정보 등록</h5>
					</div>
					
					<form action="/bill.do?request=add" name="editForm" method="post">
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL20">
								<h6>가맹점 과금 설정 정보</h6>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">정산방식<font color="red">*</font></label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<input type="hidden" name="merchantId" value="${merchantId }" />
										<select name="period">
											<option value="">----------</option>
											<option value="TYPE070">7 일 정산 (-28일~-22일 월요일 산출)</option>
											<option value="TYPE071">7 일 정산 (-21일~-15일 월요일 산출)</option>
											<option value="TYPE100">10 일 정산 (10일 마다 +3일 산출)</option>
											<option value="TYPE150">15 일 정산 (15일 마다 +3일 산출)</option>
											<option value="TYPE300">30 일 정산 (한달 마다 +3일 산출)</option>
										</select>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">V/M 수수료<font color="red">*</font></label>
								</div>
								<div class="input6" id="paddingL60">
									<span class="m"><input type="text" style="text-align: right" name="visamaster" value="0" onkeyup="onlyNumber(this);"/>&nbsp;(ex.0.065)</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">J/A 수수료<font color="red">*</font></label>
								</div>
								<div class="input5" id="paddingL60">
									<span class="m"><input type="text" style="text-align: right" name="jcbamex" value="0" onkeyup="onlyNumber(this);"/>&nbsp;(ex.0.065)</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">거래 수수료(TR FEE)<font color="red">*</font></label>
								</div>
								<div class="input6" id="paddingL60">
									<span class="m"><input type="text" style="text-align: right" name="transaction" value="0" onkeyup="onlyNumber(this);"/>&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">취소 수수료<font color="red">*</font></label>
								</div>
								<div class="input5" id="paddingL60">
									<span class="m"><input type="text" style="text-align: right" name="refund" value="0" onkeyup="onlyNumber(this);"/>&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">CB 수수료<font color="red">*</font></label>
								</div>
								<div class="input6" id="paddingL60">
									<span class="m"><input type="text" style="text-align: right" name="chargeback" value="0" onkeyup="onlyNumber(this);"/>&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">년 관리비<font color="red">*</font> </label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" style="text-align: right" name="management" value="0" onkeyup="onlyNumber(this);"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">송금 수수료<font color="red">*</font></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" style="text-align: right" name="bankTransfer" value="0" onkeyup="onlyNumber(this);"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">DEPOSIT<font color="red">*</font></label>
								</div>
								<div class="input5" id="paddingL60">
									<span class="m"><input type="text" style="text-align: right" name="deposit" value="0" onkeyup="onlyNumber(this);"/>&nbsp;(ex.0.1)</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">VAT 부가세<font color="red">*</font></label>
								</div>
								<div class="input6" id="paddingL60">
									<span class="m"><input type="text" style="text-align: right" name="vat" value="0.1" onkeyup="onlyNumber(this);"/>&nbsp;(ex.0.01)</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">설정비(초기 1회)<font color="red">*</font></label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" style="text-align: right" name="setupFee" value="0" onkeyup="onlyNumber(this);"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">정산여부<font color="red">*</font></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<select name="active">
											<option value="Y" selected>사용</option>
											<option value="N">중지</option>
										</select>
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">은행코드<font color="red">*</font></label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="bankCode" size="50" value="" onkeyup="onlyEnglish(this);"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">취급지점</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="branch" size="50" value="" onkeyup="onlyEnglish(this);"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">계좌번호<font color="red">*</font></label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="account" size="50" value="" onkeyup="onlyNumber(this);"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">계좌예금주</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="accountHolder" size="50" value="" onkeyup="onlyEnglish(this);"/></span>
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
