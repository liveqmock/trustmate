<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>::::::[PanWorldNet]::::::</TITLE>
		<link href="/common/css/search.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</HEAD>
<script type="text/javascript">

	function update() {	
		frm = document.deposit;
		
		if(frm.currentRate.value == ""){
			alert("예치금 비율을 입력하여 주세요");
			frm.currentRate.focus();
		}else if(frm.lastAmount.value == ""){
			alert("예치금 최종 금액을 입력하여 주세요");
			frm.lastAmount.focus();
		}else if(frm.currAmount.value == ""){
			alert("현재 등록 할 예치금을 입력하여 주세요");
			frm.currAmount.focus();
		}else if(frm.totalAmount.value == ""){
			alert("총 예치금 금액을 입력하여 주세요");
			frm.totalAmount.focus();
		}else{
			frm.submit();
		}
		
	}

</script>

<body>
<!-- top -->
<table width="540" border="0" cellspacing="1" cellpadding="0">
  <tr>
    <td height="35"><img src="/images/popup/bullet_blue2.gif" width="14" height="14" align="absmiddle"><span class="font_bold_14"> [ 예치금 등록 ]</span></td>
    <td width="100" align="right"></td>
  </tr>
</table>
<table width="540" border="0" cellpadding="0" cellspacing="5" bgcolor="#dbdbdb">
  <tr>
    <td bgcolor="#FFFFFF" class="p10">
    <!-- 내용 -->
     <form name="deposit" method="post" action="/report.do?request=depositUpdate">
     <table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#bfbfbf">
      <br>
      <tr>
        <td height="2" bgcolor="#1b6c8e" colspan="2">
        	<input type="hidden" name="merchantId" value="${merchantId }" />
        	<input type="hidden" name="settleIdx" value="${settleIdx }" />
        </td>
      </tr>
      <tr>
        <td width="40%" align="center" class="poplist_02">예치금비율</td>
        <td width="60%" bgcolor="#FFFFFF" class="poplist_01">
	   		<input name="currentRate" type="text" maxlength="20" style="width=260px;" />
		</td>
      </tr>
      <tr>
        <td width="40%" align="center" class="poplist_02">마지막 금액</td>
        <td width="60%" bgcolor="#FFFFFF" class="poplist_01">
	   		<input name="lastAmount" type="text" maxlength="20" style="width=260px;" />
		</td>
      </tr>
      <tr>
        <td width="40%" align="center" class="poplist_02">현재 금액</td>
        <td width="60%" bgcolor="#FFFFFF" class="poplist_01">
	   		<input name="currAmount" type="text" maxlength="20" style="width=260px;" />
		</td>
      </tr>
      <tr>
        <td width="40%" align="center" class="poplist_02">총 금액</td>
        <td width="60%" bgcolor="#FFFFFF" class="poplist_01">
	   		<input name="totalAmount" type="text" maxlength="20" style="width=260px;" />
		</td>
      </tr>
      <tr>
        <td height="2" bgcolor="#1b6c8e" colspan="2"></td>
      </tr>
    </table>
    </form>
    </td>
  </tr>
</table>
<table width="540" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" align="right"><a href="javascript:update();"><img src="/images/popup/button_confirm.gif" alt="Deposit Regist" width="79" height="32"></a></td>
  </tr>
</table>
</body>
</html>