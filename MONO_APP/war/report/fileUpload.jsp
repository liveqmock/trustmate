<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>::::::[PGMATE]::::::</TITLE>
		<link href="/common/css/search.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</HEAD>
<script type="text/javascript">

	function fileUp() {	
		frm = document.fileUpload;
		
		if(frm.file.value == ""){
			alert("파일을 등록하세요");
			frm.file.focus();
		}else{
			frm.submit();
			//self.close();
		}
	}

</script>

<body>
<!-- top -->
<table width="540" border="0" cellspacing="1" cellpadding="0">
  <tr>
    <td height="35"><img src="/images/popup/bullet_blue2.gif" width="14" height="14" align="absmiddle"><span class="font_bold_14"> [ File Upload ]</span></td>
    <td width="100" align="right"></td>
  </tr>
</table>
<table width="540" border="0" cellpadding="0" cellspacing="5" bgcolor="#dbdbdb">
  <tr>
    <td bgcolor="#FFFFFF" class="p10">
    <!-- 내용 -->
     <form name="fileUpload" action="/upload.do" method="post" enctype="multipart/form-data">
     <table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#bfbfbf">
      <br>
      <tr>
        <td width="25%" align="center" class="poplist_02">FILE</td>
        <td width="75%" bgcolor="#FFFFFF" class="poplist_01">
	   		<input name="file" type="file" style="width=340px;" />
	   		<input name="merchantId" type="hidden" value="${merchantId }" />
	   		<input name="invoiceIdx" type="hidden" value="${invoiceIdx }" />
	   		<input name="settleIdx" type="hidden" value="${settleIdx }" />
		</td>
      </tr>
    </table>
    </form>
    </td>
  </tr>
</table>
<table width="540" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" align="right"><a href="javascript:fileUp();"><img src="/images/popup/button_confirm.gif" alt="File Upload" width="79" height="32"></a></td>
  </tr>
</table>
</body>
</html>