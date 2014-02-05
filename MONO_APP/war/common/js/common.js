
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_nbGroup(event, grpName) { //v3.0
  var i,img,nbArr,args=MM_nbGroup.arguments;
  if (event == "init" && args.length > 2) {
    if ((img = MM_findObj(args[2])) != null && !img.MM_init) {
      img.MM_init = true; img.MM_up = args[3]; img.MM_dn = img.src;
      if ((nbArr = document[grpName]) == null) nbArr = document[grpName] = new Array();
      nbArr[nbArr.length] = img;
      for (i=4; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
        if (!img.MM_up) img.MM_up = img.src;
        img.src = img.MM_dn = args[i+1];
        nbArr[nbArr.length] = img;
    } }
  } else if (event == "over") {
    document.MM_nbOver = nbArr = new Array();
    for (i=1; i < args.length-1; i+=3) if ((img = MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = (img.MM_dn && args[i+2]) ? args[i+2] : args[i+1];
      nbArr[nbArr.length] = img;
    }
  } else if (event == "out" ) {
    for (i=0; i < document.MM_nbOver.length; i++) {
      img = document.MM_nbOver[i]; img.src = (img.MM_dn) ? img.MM_dn : img.MM_up; }
  } else if (event == "down") {
    if ((nbArr = document[grpName]) != null)
      for (i=0; i < nbArr.length; i++) { img=nbArr[i]; img.src = img.MM_up; img.MM_dn = 0; }
    document[grpName] = nbArr = new Array();
    for (i=2; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = img.MM_dn = args[i+1];
      nbArr[nbArr.length] = img;
  } }
}

function MM_showHideLayers() { //v3.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v; }
    obj.visibility=v; }
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
	window.open(theURL,winName,features);
}

function MM_swapImgRestore() { //v3.0
	var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
	var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.0
	var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
	var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
	if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function MM_showHideLayers() { //v3.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v; }
    obj.visibility=v; }
}


function FlashEmbed(src,wd,ht,fid)
{
	document.write('<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="/common/swflash.cab#version=9,0,28,0" width="' + wd + '" height="' + ht + '" id="' + fid + '" align="middle">');
	document.write('<param name="allowScriptAccess" value="always">');
	document.write('<param name="movie" value="' + src + '">');
	document.write('<param name="menu" value="false">');
	document.write('<param name="quality" value="high">');
	document.write('<param name="wmode" value="transparent">');
	document.write('<embed src="' + src + '" menu="false" quality="high" wmode="transparent" width="' + wd + '" height="' + ht + '" name="' + fid + '" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="https://www.macromedia.com/go/getflashplayer" />');
	document.write('</object>');
}


function popup(dest,width,height,id,attribute){
		posy = screen.Height/2 - 100; 
		posx = screen.Width /2 - 200; 
		window.open(dest,id,"toolbar=no,width="+width+",height="+height+", left="+posx+",top="+posy+",status=no,scrollbars=yes, resize=yes"+attribute); 
}


function popup(id,url,width,height,px,py){
	var sw  = screen.availWidth ;
 	var sh  = screen.availHeight ;
	//px=(sw - width)/2 ;
	//py=(sh - height)/2 ;
		
	var set  = 'top=' + py + ',left=' + px ;
	set += ',width=' + width + ',height=' + height + ',toolbar=0,resizable=0,status=0,scrollbars=0,location=0,menubar=0,directories=0,copyhistory=no' ;
				
	window.open(url,id,set); 	
}

function popup2(id,url,width,height,px,py){
	var sw  = screen.availWidth ;
 	var sh  = screen.availHeight ;
	//px=(sw - width)/2 ;
	//py=(sh - height)/2 ;
		
	var set  = 'top=' + py + ',left=' + px ;
	set += ',width=' + width + ',height=' + height + ',toolbar=0,resizable=0,status=0,scrollbars=yes,location=0,menubar=0,directories=0,copyhistory=no' ;
				
	window.open(url,id,set); 	
}


var win = null;
function printIt(printThis){
   	win = window.open();
    self.focus();
    win.document.open();
    win.document.write('<'+'html'+'><'+'head'+'><'+'style'+'>');
    win.document.write('body, td { font-family: Verdana; font-size: 10pt;}');
    win.document.write('<'+'/'+'style'+'><'+'/'+'head'+'><'+'body'+'>');
    win.document.write(printThis);
    win.document.write('<'+'/'+'body'+'><'+'/'+'html'+'>');
    win.document.close();
    win.print();
    win.close();
}

  
function showloading(bool){
	var loading = document.getElementById('loading');
	if ( bool == true){
		loading.innerHTML = '<div class="loading-indicator">  Loading...</div>';
		loading.style.visibility ='';
	}else{
		loading.innerHTML = '';
		loading.style.visibility ='hidden';
	}
}


function showDiv(bool,id){
	var loading = document.getElementById(id);
	if ( bool == true){
		loading.style.visibility ='';
	}else{
		loading.style.visibility ='hidden';
	}
}





	function allblur() {
		for (i = 0; i < document.links.length; i++)
		document.links[i].onfocus = document.links[i].blur;
	}

	function bluring(){
		if(event.srcElement.tagName=="A"||event.srcElement.tagName=="IMG") document.body.focus();
	}
	
	function closeWindow() {
	window.close();
	}

	function getOnlyNumber(){ 
		if((event.keyCode<48)||(event.keyCode>57)) 
			event.returnValue=false;
		}
	
	function isBlank(obj,message){
		if(obj.value == null){	
			alert(message);
			obj.focus();
			return true;
		}
		if(obj.value == ""){
			alert(message);
			obj.focus();
			return true;
		}
			
		return false;
	}	

	function showloading(bool){
		var loading = document.getElementById('loading');
		if ( bool == true){
			loading.innerHTML = '<div class="loading-indicator">Loading...</div>';
			loading.style.visibility ='';
		}else{
			loading.innerHTML = '';
			loading.style.visibility ='hidden';
		}
	}
	
	function resizeWindow(width,height){
	    window.resizeTo(width,height);
	}
	
	function commonNextFocus( limit, next ){
		var charCode = (event.charCode) ? event.charCode : ((event.keyCode) ? event.keyCode : ((event.which) ? event.which : 0));
		if (charCode > 31 && event.srcElement.value.length == limit) {
			commonGetObj(next).focus();
		}
	}

	function commonGetObj(name) {
		var obj = [];
	
			//alert(document.getElementById(name)+","+typeof(document.getElementById(name).length)+","+typeof(document.all[name].length));
		if (document.getElementById(name) && typeof(document.getElementById(name).length)=="undefined" && typeof(document.all[name].length)=="undefined") {
			obj = document.getElementById(name);
		  	return obj;
		}else if (document.all[name] && typeof(document.getElementById(name).length)=="undefined" && typeof(document.all[name].length)!="undefined") {
			for(ii=0;ii<document.all[name].length;ii++) obj[obj.length] = document.all[name][ii];
		  	return obj;
		}else if (document.all[name] && typeof(document.getElementById(name).length)!="undefined" && typeof(document.all[name].length)!="undefined") {
			obj = document.all[name];
		  	return obj;
		}else{
		 	return "[ERROR] Can't find object";
		}
	}
	
	function onlyEnglish(field){
		var valid = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@._- ";
		var ok = true;
		var temp;
	
		for(var i=0 ; i< field.value.length ; i++){
			temp = ""+field.value.substring(i,i+1);
			if(valid.indexOf(temp) == "-1"){
				ok = false;
			}
		}
		
		if(!ok){
			alert("Please input it only with a (number or alpabet).");
			field.value="";
			field.focus();
		}
		return;
		
	}
	
	function erase(field){
		field.value="";
		field.focus();
		return;
		
	}
	
	function onlyTelNo(field){
		var valid = "0123456789-";
		var ok = true;
		var temp;
	
		for(var i=0 ; i< field.value.length ; i++){
			temp = ""+field.value.substring(i,i+1);
			if(valid.indexOf(temp) == "-1"){
				ok = false;
			}
		}
		
		if(!ok){
			alert("Please input it only with a number.");
			field.value="";
			field.focus();
		}
		return;
		
	}
	
	function onlyNumber(field){
		var valid = "0123456789.-";
		var ok = true;
		var temp;
	
		for(var i=0 ; i< field.value.length ; i++){
			temp = ""+field.value.substring(i,i+1);
			if(valid.indexOf(temp) == "-1"){
				ok = false;
			}
		}
		
		if(!ok){
			alert("Please input it only with a number.");
			field.value="";
			field.focus();
		}
		return;
		
	}
	
	function onlyDate(field){
		var valid = "0123456789";
		var ok = true;
		var temp;
	
		for(var i=0 ; i< field.value.length ; i++){
			temp = ""+field.value.substring(i,i+1);
			if(valid.indexOf(temp) == "-1"){
				ok = false;
			}
		}
		
		if(field.value.length != 8){
			ok = false;
		}
		
		if(!ok){
			alert("日付形式を YYYYMMDD に入力してください。");
			field.value="";
			field.focus();
		}
		return;
		
	}
	
	function click() {
		if ((event.button==2) || (event.button==2) || (event.ctrlKey) || (event.shiftKey)) {
			alert("Permission denied!");
		}
	} 
	
	function processKey() { 
        if( (event.ctrlKey == true && (event.keyCode == 78 || event.keyCode == 82)) || 
	        (event.keyCode >= 112 && event.keyCode <= 123) || event.keyCode == 8) { 
	        event.keyCode = 0; 
	        event.cancelBubble = true; 
	        event.returnValue = false; 
  	  	} 
	}
	
	function getCurrentDate() {
		var today 	= new Date();
		var year 	= today.getYear();
		if (year < 2000){year += 1900;}
		var month 	= today.getMonth()+1;
		if(month < 10){month = "0"+month}
		var day 	= today.getDate();
		var hour 	= today.getHours(); 
		var minute 	= today.getMinutes();
		var second 	= today.getSeconds();
		
		return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+".0";
	}
	
	function toTimestamp(field) {
		
		if(field.value.length !=8){
			alert("日付形式を YYYYMMDD に入力してください。");
			field.focus();
			return;
		}else{
			var date = field.value;
			field.value = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" 00:00:00.0";
			return;
		}
	}
	function toTimestampEnd(field) {
		
		if(field.value.length !=8){
			alert("日付形式を YYYYMMDD に入力してください。");
			field.focus();
			return;
		}else{
			var date = field.value;
			field.value = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" 23:59:59.9";
			return;
		}
	}  
	
	function msgmove(id){
		var msg = document.getElementById(id); 
 		msg.style.posLeft = event.x + 10 + document.body.scrollLeft; 
 		msg.style.posTop = event.y - 20 + document.body.scrollTop;
 	} 

 	function msgset(id,strmsg){ 
 		var msg = document.getElementById(id); 
 		text ='<table  width="400"  bgcolor="#EFF7FC" style="border:1 black solid;"><tr><td>' + strmsg + '</td></tr></table>' ;
 		msg.innerHTML=text ;
 	} 

 	function msghide(id){ 
 		var msg = document.getElementById(id); 
 		msg.innerHTML='' ;
 	} 
 	

	function reloadpage(delay)
	{
	if (delay==null) 
	        window.location.reload();
	    else 
	        window.setinterval("window.location.reload()",delay);
	}
	/**
	 * 브라우저의 버전을 체크합니다.
	 */
	
	function getbrowser() 
	{
	    var tempdocument = window.document;
	    if (tempdocument.all && tempdocument.getelementbyid) // 인터넷 익스플로러 5.x
	    { 
	        return 1;
	    }
	    else if (tempdocument.all && !tempdocument.getelementbyid) // 인터넷 익스플로러 4.x
	    { 
	        return 2;
	    }
	    else if (tempdocument.getelementbyid && !tempdocument.all) // 넷스케이프 6
	    { 
	        return 3;
	    }
	    else if (tempdocument.layers) // 넷스케이프 4.x
	    {     
	        return 4;
	    }
	}
	 
	/**
	 * 팝업창을 원하는 위치에 생성합니다.
	 */
	
	function openwindow(url, name, width, height, align, valign, option) 
	{
	    var x,y;
	    var window_option = "width="+width+",height="+height;
	    if (option!=null) window_option+=","+option;
	    if (align==null) align="center";
	    if (valign==null) valign="center";
	    if (align=="left") x=0;
	    else if (align=="right") x=(screen.width-width);
	    else if (align=="center") x=(screen.width-width)/2
	    if (valign=="top") y=0;
	    else if (valign=="bottom") y=(screen.height-height);
	    else if (valign=="center") y=(screen.height-height)/2
	    window_option+=",left="+x+",top="+y;
	    var win = window.open(url,name,window_option);
	    focus();
	    win.focus();
	    return win;
	}
	 
	/**
	 * 윈도우가 열려있는지 체크합니다.
	 */
	
	function isalivewindow(win)
	{
	    if (!win.closed) return true;
	    else return false;
	}
	 
	/**
	 * 사운드를 들을수 있는지 환경인지 체크합니다. (ie전용)
	 */
	
	function enablesound()
	{
	  document.write("<object id='player64' classid='clsid:22d6f312-b0f6-11d0-94ab-0080c74c7e95' style='display：none'></object>");
	  return player64.issoundcardenabled();
	}
	 
	/**
	 * 리얼플레이어(realplayer) 설치 여부 체크합니다.
	 */
	
	function enablerealplayer()
	{
	    var nrealmode=0;
	    var nrealplayer5=0;
	    var nrealplayer4=0;
	    var nrealplayerg2=0;
	    if (window.document.all) // ie
	    {
	        document.write('<script language=vbscript\> \n');
	        document.write('on error resume next \n');
	        document.write('nrealplayerg2 = (not isnull(createobject("rmocx.realplayer g2 control")))\n');
	        document.write('nrealplayer5 = (not isnull(createobject("realplayer.realplayer(tm) activex control (32-bit)")))\n');
	        document.write('nrealplayer4 = (not isnull(createobject("realvideo.realvideo(tm) activex control (32-bit)")))\n');
	        document.write('</script\> \n');
	    }
	    else // ns
	    {
	        var numplugins = navigator.plugins.length;
	        for (var i = 0; i < numplugins; i++)
	        {
	            plugin = navigator.plugins[i];
	            if (plugin.name.substring(0,10)=="realplayer")
	            {
	                nrealmode=1;
	            }
	        }
	    }
	    if (nrealmode || nrealplayerg2 || nrealplayer5 || nrealplayer4) 
	        return true;
	    else 
	        return false;
	}
	 
	/**
	 * 페이지 이동을 합니다.
	 * @param        delay        페이지 이동 지연 시간 (milliseconds)
	 */
	
	function movepage(str,delay)
	{
	    if (delay==null) 
	        window.location.href=str;
	    else 
	        window.setinterval("window.location.href='"+str+"'",delay);
	}
	 
	/**
	 * 현재 히스토리 엔트리에 페이지를 읽어들입니다. (뒤로가기 버튼 비활성화)
	 */
	
	function replacepage(str,delay)
	{
	    if (delay==null) 
	        window.location.replace(str);
	    else 
	        window.setinterval("window.location.replace('"+str+"')",delay);
	}
	 
	/**
	 * 현재 페이지 새로 고침
	 */
	
	function reloadpage(delay)
	{
	if (delay==null) 
	        window.location.reload();
	    else 
	        window.setinterval("window.location.reload()",delay);
	}
	 
	/**
	 * 문자열을 클립보드에 복사합니다. (ie전용)
	 */
	
	function copytoclip(str) 
	{
	    if (window.document.all) // ie일때
	        window.clipboarddata.setdata('text',str);
	}
	 
	/**
	 * 브라우저의 시작페이지 변경창을 띄웁니다. (ie전용)
	 */
	
	function sethomepage(url) 
	{
	    window.document.write("<span id='objhomepage' style='behavior:url(#default#homepage); display：none;' >s</span>");
	    window.document.all.objhomepage.sethomepage(url);
	}
	 
	/**
	 * 브라우저의 즐겨찾기 추가창을 띄웁니다. (ie전용)
	 */
	
	function addfavorite(url, homename) 
	{
	    window.external.addfavorite(url, homename);
	}
	 
	/**
	 * 모니터 해상도를 구합니다.
	 */
	
	function getwindowresolution() 
	{
	    if (window.screen)
	    {
	        var returnarray = new array(2);
	        returnarray[0] = window.screen.width;
	        returnarray[1] = window.screen.height;
	        return returnarray;
	    }
	    else return false;
	}
	 
	/**
	 * 사용자의 색상 설정을 구합니다.
	 * @return        색상비트수를 반환합니다. ( 8비트 : 256색, 16비트 : 하이컬러 , 24비트 : 트루컬러 )
	 */
	
	function getwindowcolor() 
	{
	    if (window.screen)
	    {
	        return screen.colordepth;
	    }
	}
	 
	/**
	 * 브라우저의 제목표시줄을 설정합니다.
	 */
	
	function setwindowtitle(str) 
	{
	    document.title = str;
	}
	 
	/**
	 * 브라우저의 제목표시줄의 문자열을 반환합니다.
	 */
	
	function getwindowtitle() 
	{
	    return document.title;
	}
	 
	/**
	 * 브라우저의 상태표시줄을 설정합니다.
	 */
	
	function setstatustitle(str) 
	{
	    window.status = str;
	}
	 
	/**
	 * 브라우저의 상태표시줄의 문자열을 반환합니다.
	 */
	
	function getstatustitle() 
	{
	    return window.status;
	}
	
	/**
	 * 한글 마지막 글자의 중성 유무를 체크합니다.
	 *
	 * ex ) var str = "사탕";
	 *        if (checkfinalconsonant(str)) {
	 *           window.alert(str+"을 먹었습니다.");
	 *        }
	 *        else {
	 *           window.alert(str+"를 먹었습니다.");
	 *        }
	 */
	
	function checkfinalconsonant(str) 
	{
	    var strtemp = str.substr(str.length-1);
	    if ((strtemp.charcodeat(0)-16)%28!=0) return true;
	    else return false;
	}
	 
	/**
	 * 문자열에 사용해서는 안되는 html태그가 있는지 체크합니다.
	 */
	
	function isvalidhtml(str) 
	{
	    var re = new regexp("<[\/]{0,1}[^\f\n\r\t\v]*(html|table|tr|td|script|form|xmp|!|iframe|textarea|input|meta)[^\f\n\r\t\v]*","gi");
	    var matcharray = str.match(re);
	    if (matcharray) return false;
	    else return true;
	}
	 
	/**
	 * 올바른 메일형식인지 체크합니다.
	 */
	
	function isvalidemail(str) 
	{
	    var re=new regexp("^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$","gi");
	    var matcharray=str.match(re);
	    if (matcharray) return true;
	    else return false;
	}
	 
	/**
	 * 올바른 홈페이지형식인지 체크합니다.
	 */
	
	function isvalidhomepage(str) 
	{
	    var re=new regexp("^((ht|f)tp:\/\/)((([a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3}))|(([0-9]{1,3}\.){3}([0-9]{1,3})))((\/|\\?)[a-z0-9~#%&'_\+=:\?\.-]*)*)$","gi");
	    var matcharray=str.match(re);
	    if (matcharray) return true;
	    else return false;
	}
	 
	/**
	 * 올바른 전화번호 형식(숫자-숫자-숫자)인지 체크합니다.
	 */
	
	function isvalidphone(str) 
	{
	    if (str.search(/^(\d+)-(\d+)-(\d+)$/g)!=-1) return true;
	    else return false;
	}
	 
	/**
	 * 알파벳만으로 구성된 문자열인지 체크합니다.
	 */
	
	function isalphabet(str) 
	{
	    if (str.search(/[^a-za-z]/g)==-1) return true;
	    else return false;
	}
	 
	/**
	 * 대문자로만 구성된 문자열인지 체크합니다.
	 */
	
	function isuppercase(str) 
	{
	    if (str.search(/[^a-z]/g)==-1) return true;
	    else return false;
	}
	 
	/**
	 * 소문자로만 구성된 문자열인지 체크합니다.
	 */
	
	function islowercase(str) 
	{
	    if (str.search(/[^a-z]/g)==-1) return true;
	    else return false;
	}
	 
	/**
	 * 한글로만 구성된 문자열인지 체크합니다.
	 */
	
	function iskorean(str) 
	{
	    var strlength = str.length;
	    var i;
	    var unicode;
	    for (i=0;i<strlength;i++) 
	    {
	        unicode = str.charcodeat(i);
	        if ( !(44032 <= unicode && unicode <= 55203) ) return false;    
	    }
	    return true;
	}
	 
	/**
	 * 숫자만으로 구성된 문자열인지 체크합니다.
	 */
	
	function isdigit(str) 
	{
	    if (str.search(/[^0-9]/g)==-1) return true;
	    else return false;
	}
	 
	/**
	 * 문자열이 null인지 체크합니다.
	 */
	
	function isnull(str) 
	{
	    if (str == null || str == "") return true;
	    else return false;
	}
	 
	/**
	 * 문자열에 한칸이상의 스페이스 입력이 있는지를 체크합니다.
	 */
	
	function isvalidspace(str) 
	{
	    if (isnull(str)) return false;
	    else
	    {
	        if (str.search(/[\s]{2,}/g)!=-1) return false;
	        else return true;
	    }
	}
	
	function select(obj, form){
		var now = new Date();
		var year = now.getYear();
		
		var f = form;
		var years = f.years;
		var months = f.months;
		
		var month = new Array("------","01","02","03","04","05","06","07","08","09","10","11","12" );
		
		if(years.value == ""){
			for(i=0; i<5 ; i++)
			{
				years.options[i] = new Option(year-i,year-i);
			}
			for(i=0; i<month.length ; i++)
			{
				months.options[i] = new Option(month[i],month[i]);
			}
		} 
	
	}
	
	/**
	* 한페이지에 여러개의 onload 가 필요할 시 사용
	* 사용법 addLoadEvents(불러올 함수1);
	*/
	
	function addLoadEvents( func )
	{
	  var oldonload = window.onload;
	  if ( typeof window.onload != 'function' ){
	    window.onload = func;
	  } else {
	    window.onload = function()
	    {
	      oldonload();
	      func();
	    }
	  }
	}
	
	function menuopen(obj){
		var mod;
		var menuDiv = document.getElementById(obj);
		if(menuDiv.style.display=="none"){
			menuDiv.style.display = "block";
		}else{
			menuDiv.style.display = "none";
		}
	}
	
	
	
	