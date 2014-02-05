//////////////////////////////////////
// IE Embed patch
//
// @author iezn@iezn.com
// @homepage http://iezn.com
// @create date 2006.04.19
// @last modify 2006.04.21
// ������ �������� ������ �ֽñ� �ٶ��ϴ�
//////////////////////////////////////
/**
* embed ��ġ ���� �����̳�
* null�ΰ�� document ���� �⺻���� �մϴ�
* id���� �����Ѱ�� �������� ������ ������ �˴ϴ�
* 
* �����̳� �Ϻ� ��忡�� �����Ұ�� �ش� ����� id ���� �Է��Ͻ� �� �ֽ��ϴ�
* ��)
* var __embed_target_id = "contents";
* �� ó���Ѱ�� body ���� <�±� id="contents">�÷���,������...</�±�>
* �ȿ� ���뿡�� ��ġ�� ����˴ϴ�
*/
var __embed_target_id = null;

/**
* embed ��ġ�� ������ �±׸� �����մϴ�
* �⺻���� object,eembed,appelt �±��Դϴ�
* false ���ΰ�� ��ġ���� ���ܵ˴ϴ�
*/
var __embed_tags = {object:true,embed:true,applet:true}

/**
* �̺�Ʈ ���
*/
if(document.attachEvent){
	document.attachEvent('onreadystatechange',
		function embed_patch(){
			if(__embed_target_id===null){
				var __target = document;
			}else{
				var __target = document.getElementById(__embed_target_id);
			}
			if (document.readyState == "complete"){
				function _replace(obj){
					var obj_re = document.createElement(obj.outerHTML);
					obj.parentNode.replaceChild(obj_re,obj);
				}
				function _inner(obj){
					obj.insertAdjacentHTML('beforeBegin',obj.outerHTML);			
					obj.parentNode.removeChild(obj);
				}
				if(__embed_tags.object===true){
					//object ��ġ
					var objs = __target.getElementsByTagName('object');
					var i = objs.length;
					while(i-->0){
						_inner(objs[i]);
					}
				}
				if(__embed_tags.embed===true){
					//embed ��ġ
					var objs = __target.getElementsByTagName('embed');
					var i = objs.length;
					while(i-->0){
						_replace(objs[i])
					}
				}
				if(__embed_tags.applet===true){
					//applet ��ġ
					var objs = __target.getElementsByTagName('applet');
					var i = objs.length;
					while(i-->0){
						_replace(objs[i])
					}
				}
			}
		}
	);
}