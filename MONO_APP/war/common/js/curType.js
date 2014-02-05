	
	function curTypeControl(obj, form) {
		var frm = form;
		var selectboxarray1 = new Array([["-------"],[""]],[["DCP(USD)"],["DCP"]],[["BNU(USD)"],["BNU-USD"]],[["GC(USD)"],["GC"]]);
		var selectboxarray2 = new Array([["-------"],[""]],[["BNU(JPY)"],["BNU-JPY"]],[["ENETS"],["eNETS"]],[["GC(JPY)"],["GC"]]);
		var selectboxarray3 = new Array([["-------"],[""]],[["DCP(USD)"],["DCP"]],[["GC(USD)"],["GC"]]);
		var selectboxarray4 = new Array([["-------"],[""]],[["GC(JPY)"],["GC"]],[["BNU(JPY)"],["BNU-JCB"]]);
		var selectboxarray5 = new Array([["-------"],[""]],[["AMEX(USD)"],["AMEX"]],[["GC(USD)"],["GC"]]);
		var selectboxarray6 = new Array([["-------"],[""]],[["GC(JPY)"],["GC"]],[[""],[""]]);
			
		var curType = frm.curType.value;
		var curTypeChange = frm.curTypeChange.value;
		var visaVAN = frm.visaVAN;
		var masterVAN = frm.masterVAN;
		var jcbVAN = frm.jcbVAN;
		var amexVAN = frm.amexVAN
		
		if(curTypeChange == "Y"){
			if(curType == "USD"){
				for(var i=0;i<=selectboxarray1.length-1;i++) {
						visaVAN.options[i]= new Option(selectboxarray1[i][0],selectboxarray1[i][1]);
				}
				for(var i=0;i<=selectboxarray1.length-1;i++) {
						masterVAN.options[i]= new Option(selectboxarray1[i][0],selectboxarray1[i][1]);
				}
				for(var i=0;i<=selectboxarray3.length-1;i++) {
						jcbVAN.options[i]= new Option(selectboxarray3[i][0],selectboxarray3[i][1]);
				}
				for(var i=0;i<=selectboxarray5.length-1;i++) {
						amexVAN.options[i]= new Option(selectboxarray5[i][0],selectboxarray5[i][1]);
				}
				frm.curTypeChange.value = "N";
			}
			if(curType == "JPY"){
				for(var i=0;i<=selectboxarray2.length-1;i++) {
						visaVAN.options[i]= new Option(selectboxarray2[i][0],selectboxarray2[i][1]);
				}
				for(var i=0;i<=selectboxarray2.length-1;i++) {
						masterVAN.options[i]= new Option(selectboxarray2[i][0],selectboxarray2[i][1]);
				}
				for(var i=0;i<=selectboxarray4.length-1;i++) {
						jcbVAN.options[i]= new Option(selectboxarray4[i][0],selectboxarray4[i][1]);
				}
				for(var i=0;i<=selectboxarray6.length-1;i++) {
						amexVAN.options[i]= new Option(selectboxarray6[i][0],selectboxarray6[i][1]);
				}
				frm.curTypeChange.value = "N";
			}
		}
		if(curTypeChange == "N"){
			frm.curTypeChange.value = "Y";
		}
	}	