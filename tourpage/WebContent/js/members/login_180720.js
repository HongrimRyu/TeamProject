
//비밀번호 찾기 버튼 이벤트
function passfind(){
	$('#pfPopup,.page_cover,body').addClass('open');
	window.location.hash='#passfind';
}
window.onhashchange = function(){
	if (location.hash != "#passfind") {
		$("#pfPopup,.page_cover,body").removeClass('open');
	
	}
	
};
			
			
function getXMLHttpRequest(){
	var httpRequest = null;
    if(window.ActiveXObject){
        try{
            httpRequest = new ActiveXObject("Msxml2.XMLHTTP");    
        } catch(e) {
            try{
                httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e2) { httpRequest = null; }
        }
    }
    else if(window.XMLHttpRequest){
        httpRequest = new window.XMLHttpRequest();
    }
    return httpRequest;    
}
			
function Ecallback(){////이메일 확인 callback
    if(httpRequest.readyState == 4){
        var resultText = httpRequest.responseText;
        if(resultText == 0){
            /* 아이디 존재  -> 메일 발송*/
            var id = document.getElementById('id').value;
            var param="id="+id;
            httpRequest = getXMLHttpRequest();
            httpRequest.onreadystatechange = AuthCallback;
            httpRequest.open("POST", "MemberSendPassAction.me", true);    
	        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); 
	        httpRequest.send(param);
        } 
        else if(resultText == 1){ 
        	$('#idChkResult').html('존재하지 않는 아이디입니다.');
        	$('#id').css('background-color','#EF5350');
        }
    }
}
function AuthCallback(){//메일 발송 callback
	if(httpRequest.readyState == 4){
        $('#idChkResult').html(' 발송 완료');           
    }
}
function sendPass(){//아이디 체크
	$('#idChkResult').html('');
	$('#id').css('background-color','#f9f9f9');
	var id = document.getElementById("id").value;
	var param="id="+id;
    httpRequest = getXMLHttpRequest();
    httpRequest.onreadystatechange = Ecallback;
    httpRequest.open("POST", "MemberIdCheckAction.me", true);    
    httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); 
    httpRequest.send(param);
}