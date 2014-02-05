	/*
	var tempHtmlContent; 
	//var all = document.all? document.all : document.getElementById("pbody");
	//var layer = document.all? document.all("pDiv") : document.getElementById("pDiv");
	
	
	function printDiv() { 
	   if (document.all && window.print) { 
	       window.onbeforeprint = beforeDivs; 
	       window.onafterprint = afterDivs; 
	       window.print(); 
	   } 
	} 
	function beforeDivs() {
	   if (document.all) { 
	       var rng = document.body.createTextRange( ); 
	       if (rng!=null) { 
	           //alert(rng.htmlText); 
	           tempHtmlContent = rng.htmlText; 
	           rng.pasteHTML("<table border=0 align=center><tr><td align=center>"  
	           + document.all("pDiv").innerHTML + "</td></tr></table>");
	       } 
	   } 
	} 
	function afterDivs() { 
	   if (document.all) { 
	       var rng = document.body.createTextRange( ); 
	           if (rng!=null) { 
	                       rng.pasteHTML(tempHtmlContent); 
	           } 
	   }	
	}
	
	/*
	function printDiv(all, layer) { 
		//alert(document.all("pDiv"));
		//alert(document.getElementById("pDiv"));
	   if (all && window.print) { 
	       window.onbeforeprint = beforeDivs; 
	       window.onafterprint = afterDivs; 
	       window.print(); 
	   } 
	} 
	function beforeDivs(all, layer) {
	   if (all) { 
	       var rng = document.body.createTextRange( ); 
	       if (rng!=null) { 
	           //alert(rng.htmlText); 
	           tempHtmlContent = rng.htmlText; 
	           rng.pasteHTML("<table border=0 align=center><tr><td align=center>"  
	           + layer.innerHTML + "</td></tr></table>");
	       } 
	   } 
	} 
	function afterDivs(all) { 
	   if (all) { 
	       var rng = document.body.createTextRange( ); 
	           if (rng!=null) { 
	                       rng.pasteHTML(tempHtmlContent); 
	           } 
	   } 
	} 
	*/
	/*
	//print
	function printDiv(){
   		window.onbeforeprint = Before_Print;
   		window.onafterprint = After_Print;
   		window.print();
	}

	
	//print Contents
	var initBody;
	function Before_Print(){
	    initBody = document.body.innerHTML;
		document.body.innerHTML = pDiv.innerHTML;
	}
	//print After Contents
	function After_Print(){
   		document.body.innerHTML = initBody;
	}  
	*/

	//print Contents
	var initBody;
	function Before_Print(){
	    initBody = document.body.innerHTML;
		document.body.innerHTML = pDiv.innerHTML;
	}

	//print After Contents
	function After_Print(){
   		document.body.innerHTML = initBody;
	}

	//print
	function printDiv(){
   		window.onbeforeprint = Before_Print;
   		window.onafterprint = After_Print;
   		window.print();
	}

	//print ScriptX
	function printDivSX(settleId){
		if(navigator.appName  == 'Microsoft Internet Explorer'){ 
			factory.printing.header = "";
			factory.printing.footer = "";
			factory.printing.portrait = true;
			factory.printing.leftMargin = 10.0;
			factory.printing.topMargin = 5;
			factory.printing.rightMargin = 10.0;
			factory.printing.bottomMargin = 1;
			factory.printing.paperSize = "A4";
			factory.printing.Print(true);
		}else{
			Before_Print();	
			After_Print();	
			window.onbeforeprint = Before_Print;	
	   		window.onafterprint = After_Print;	
			window.print();
		}
	}
	
	function printDivSX(){

		Before_Print();	
		After_Print();	

		window.onbeforeprint = Before_Print;	
   		window.onafterprint = After_Print;		
   		window.print();
	}
	
	


	function goPrint(title){
	     var sw=screen.width;
	     var sh=screen.height;
	     var w=800;//팝업창 가로길이
	     var h=600;//세로길이
	     var xpos=(sw-w)/2; //화면에 띄울 위치
	     var ypos=(sh-h)/2; //중앙에 띄웁니다.
	 
	     var pHeader="<html><head><link rel='stylesheet' type='text/css' media='print' href='/resources/css/statement.css'><title>" + title + "</title></head><body>";
	     var pgetContent=document.getElementById("pDiv").innerHTML + "<br>";
	     //innerHTML을 이용하여 Div로 묶어준 부분을 가져옵니다.
	     var pFooter="</body></html>";
	     pContent=pHeader + pgetContent + pFooter;  
	      
	     pWin=window.open("","print","width=" + w +",height="+ h +",top=" + ypos + ",left="+ xpos +",status=yes,scrollbars=yes"); //동적인 새창을 띄웁니다.
	     pWin.document.open(); //팝업창 오픈
	     pWin.document.write(pContent); //새롭게 만든 html소스를 씁니다.
	     pWin.document.close(); //클로즈
	     pWin.print(); //윈도우 인쇄 창 띄우고
	     pWin.close(); //인쇄가 되던가 취소가 되면 팝업창을 닫습니다.
	}
	 
	
	