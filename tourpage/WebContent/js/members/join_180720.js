
	//중복 확인과 이메일 인증을 위한 flag
	var eflag=false;//이메일 중복
	var eauth=false;//이메일 인증
	var nflag=false;//닉네임 중복
	var pflag=true;//폰번호 중복
	//id 형식
	var idtest = /(?=.*[a-zA-Z])(?=.*[0-9a-zA-Z])/;
	var idktest = /[ㄱ-ㅎ가-힣]/;
	//password 형식
	var ptest = /(?=.*\d)(?=.*[a-zA-Z])./;
	//email 형식
	var etest = /((?:[\w-]+\.)*\w[\w-]{0,30})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	//phone 형식
	//0으로 시작하는 3자리
	var p1test = /^0[0-9]{2}$/;
	//숫자 3자리 또는 4자리
	var p2test = /^[0-9]{3,4}$/;
	//숫자 4자리
	var p3test = /^[0-9]{4}$/;
	
	var authcode="";

	
	
	
	
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
	
	
	function Ecallback(){////이메일 callback
	    if(httpRequest.readyState == 4){
	        // 결과값을 가져온다.
	        var resultText = httpRequest.responseText;
	        if(resultText == 0){
	            /* alert("이미 존재하는 아이디입니다."); */
	            $('#id').css('background-color','#EF5350');
	            $('#email').css('background-color','#EF5350');
	            $('#emailChkResult').html('이미 사용중인 이메일입니다.');
	            eflag = false;
	        } 
	        else if(resultText == 1){ 
	        	/* alert("사용 가능한 아이디입니다."); */
	        	$('#id').css('background-color','#f9f9f9');
	        	$('#email').css('background-color','#f9f9f9');
	        	$('#emailChkResult').html('사용 가능한 이메일입니다.');
	        	eflag = true;
	        }
	    }
	}
	function Authcallback(){////인증메일 callback
	    if(httpRequest.readyState == 4){
	        // 결과값을 가져온다.
	        authcode = httpRequest.responseText;
	        if($('#authinput').length==0){
	        	$('#emailauth').after("<br id='tempbr'><input type='text' id='authinput' class='jiInp'/><input type='button' id='authchk'  class='btn' onclick='authChk();' value='확인'/>");
	        	$('#authResult').html('');
	        	eauth=false;
	        }
	        
	    }
	}
	
	function Ncallback(){////닉네임 callback
	    if(httpRequest.readyState == 4){
	        var resultText = httpRequest.responseText;
	        if(resultText == 0){//중복
	            $('#nick').css('background-color','#EF5350');
	            $('#nickChkResult').html('이미 사용중인 닉네임입니다.');
	            nflag = false;
	        } 
	        else if(resultText == 1){//사용가능 
	        	$('#nick').css('background-color','#f9f9f9');
	        	$('#nickChkResult').html('사용 가능한 닉네임입니다.');
	        	nflag = true;
	        }
	    }
	}
	

	
	function Pcallback(){////연락처 callback
	    if(httpRequest.readyState == 4){
	        var resultText = httpRequest.responseText;
	        if(resultText == 0){//중복
	            $('#phone1').css('background-color','#EF5350');
	            $('#phone2').css('background-color','#EF5350');
	            $('#phone3').css('background-color','#EF5350');
	            $('#phoneChkResult').html('이미 사용중인 연락처입니다.');
	            pflag = false;
	        } 
	        else if(resultText == 1){//사용가능 
	        	$('#phone1').css('background-color','#f9f9f9');
	        	$('#phone2').css('background-color','#f9f9f9');
	        	$('#phone3').css('background-color','#f9f9f9');
	        	$('#phoneChkResult').html('사용 가능한 연락처입니다.');
	        	pflag = true;
	        }
	    }
	}
	function selectEmail(val){///이메일 select태그 onchange이벤트
		if(val=="1"){
			document.getElementById("email").value = "";
			document.getElementById("email").readOnly=false;
		}else{
			document.getElementById("email").readOnly=true;
			document.getElementById("email").value = val;
		}
	}
	//////////아이디 확인 
	function emailChk(){
		
		var id = document.getElementById("id").value;
		
		if(!idtest.test(id)||idktest.test(id)){
			if(id==""){
				$('#id').css('background-color','#f9f9f9');
				$('#email').css('background-color','#f9f9f9');
	            $('#emailChkResult').html('이메일을 입력해주세요.');
	            $('#id').focus();
	            eflag=false;
			}else{
				$('#id').css('background-color','#EF5350');
				$('#email').css('background-color','#f9f9f9');
	            $('#emailChkResult').html('영문자 또는 영문자+숫자로 입력해주세요.');
	            eflag=false;
			}
		}else if(id.length<6 || id.length>30){
			$('#id').css('background-color','#EF5350');
			$('#email').css('background-color','#f9f9f9');
            $('#emailChkResult').html('이메일을 6~30자리로  입력해주세요');
            eflag=false;
		}else{
	        if(!etest.test(document.getElementById("email").value)){
    				$('#id').css('background-color','#f9f9f9');
    	            $('#email').css('background-color','#EF5350');
    	            $('#emailChkResult').html('도메인이 올바르지 않습니다.');
    	            eflag=false;
	    	}else{
		        var id_email = document.getElementById("id").value +"@"+ document.getElementById("email").value;
				var param="id="+id_email;
		        httpRequest = getXMLHttpRequest();
		        httpRequest.onreadystatechange = Ecallback;
		        httpRequest.open("POST", "MemberIdCheckAction.me", true);    
		        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); 
		        httpRequest.send(param);
	    	}
		}
	}
	function emailAuth(){
		if(!document.getElementById("id").value==""
				&&!document.getElementById("email").value==""
				&&!(!idtest.test(id)||idktest.test(id))){	
			document.getElementById("id").readOnly=true;
			document.getElementById("email").readOnly=true;
			document.getElementById("emailSelect").disabled=1;
			
			var id_email = document.getElementById("id").value +"@"+ document.getElementById("email").value;
			var param="id="+id_email;
	        httpRequest = getXMLHttpRequest();
	        httpRequest.onreadystatechange = Authcallback;
	        httpRequest.open("POST", "MemberAuthMailAction.me", true);    
	        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); 
	        httpRequest.send(param);
		}else{
			emailChk();
		}
	}
	function authChk(){
		if(document.getElementById("authinput").value == authcode.trim()){
			$('#tempbr').remove();
			$('#authchk').remove();
			$('#authinput').remove();
			$('#authResult').html('이메일 인증 완료');
			eauth=true;
		}else{
			$('#authinput').focus();
			$('#authResult').html('인증번호를 다시 확인해주세요');
		}

	}
	function nickChk(){
		var nick = document.getElementById("nick").value;
		if(nick==""){
			$('#nick').css('background-color','#f9f9f9');
        	$('#nickChkResult').html('');
        	nflag=false;
		}else{
			var param="nick="+nick;
	        httpRequest = getXMLHttpRequest();
	        httpRequest.onreadystatechange = Ncallback;
	        httpRequest.open("POST", "MemberNickCheckAction.me", true);    
	        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=utf-8'); 
	        httpRequest.send(param);
		}
	}
	
	function phoneChk(){
		if(!(p1test.test(document.getElementById("phone1").value)&&
			p2test.test(document.getElementById("phone2").value)&&
			p3test.test(document.getElementById("phone3").value))){
			if(document.getElementById("phone1").value==""&&
				document.getElementById("phone2").value==""&&
				document.getElementById("phone3").value==""){
				$('#phone1').css('background-color','#f9f9f9');
		        $('#phone2').css('background-color','#f9f9f9');
		        $('#phone3').css('background-color','#f9f9f9');
		        $('#phoneChkResult').html('');      
		        pflag=true;
			}else{
				$('#phone1').css('background-color','#EF5350');
	            $('#phone2').css('background-color','#EF5350');
	            $('#phone3').css('background-color','#EF5350');
	            $('#phoneChkResult').html('형식에 맞게 입력해주세요.');
	            pflag=false;
			}
		}else{
			var phone = document.getElementById("phone1").value+"-"+document.getElementById("phone2").value+"-"+document.getElementById("phone3").value;
			var param="phone="+phone;
	        httpRequest = getXMLHttpRequest();
	        httpRequest.onreadystatechange = Pcallback;
	        httpRequest.open("POST", "MemberPhoneCheckAction.me", true);    
	        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); 
	        httpRequest.send(param);
		}
	}
	
	function passChk(){
		var pass1 = document.getElementById("pass1").value;
		var pass2 = document.getElementById("pass2").value;
		
		if(!(pass1.length>=8 & pass1.length<=20)){
			/* alert("비밀번호를 8~20자리로  입력해주세요"); */
			if(pass1=="" && pass2==""){
				$('#pass1').css('background-color','#f9f9f9');
				$('#pass2').css('background-color','#f9f9f9');
				$('#passChkResult').html('');
			}else{
				$('#pass1').css('background-color','#EF5350');
				$('#pass2').css('background-color','#f9f9f9');
				$('#passChkResult').html('비밀번호를 8~20자리로  입력해주세요');
			}
		}else if(!ptest.test(pass1)){
			/* alert("비밀번호를 영문+숫자로 입력해주세요"); */
			$('#pass1').css('background-color','#EF5350');
			$('#pass2').css('background-color','#f9f9f9');
			$('#passChkResult').html('비밀번호를 영문+숫자로 입력해주세요');
		}else if(!(pass1==pass2)){
			/* alert("패스워드가 일치하지 않습니다."); */
			$('#pass1').css('background-color','#f9f9f9');
			$('#pass2').css('background-color','#EF5350');
			$('#passChkResult').html('패스워드가 일치하지 않습니다.');
		}else{
			$('#pass1').css('background-color','#f9f9f9');
			$('#pass2').css('background-color','#f9f9f9');
			$('#passChkResult').html('패스워드 일치');
		}
		
	}
	//////////
	function chk(){////유효성 체크
		
		//////////아이디(이메일)
		var id = document.getElementById("id").value;
		if(!idtest.test(id)||idktest.test(id)){
			alert("영문자 또는 영문자+숫자로 입력해주세요.");
			document.getElementById("id").focus();
			return false;
		}
		
		if(id.length<6 || id.length>30){
			alert("이메일을 6~30자리로  입력해주세요");
			document.getElementById("id").focus();
			return false;
		}
		/////////이메일 형식
		
		
		if(!etest.test(document.getElementById("email").value)){
			alert("이메일 형식이 올바르지 않습니다.");
			document.getElementById("email").focus();
			return false;
		}
		
		//////////비밀번호
		
		var pass1 = document.getElementById("pass1").value;
		var pass2 = document.getElementById("pass2").value;
		
		if(!(pass1==pass2)){
			alert("패스워드가 일치하지 않습니다.");
			document.getElementById("pass2").focus();
			return false;
		}

		if(!ptest.test(pass1)){
			alert("비밀번호를 영문+숫자로 입력해주세요");
			document.getElementById("pass1").focus();
			return false;
		}
		if(!(pass1.length>=8 & pass1.length<=20)){
			alert("비밀번호를 8~20자리로  입력해주세요");
			document.getElementById("pass1").focus();
			return false;
		}
		////////////닉네임
		var nick = document.getElementById("nick").value;
		if(!(nick.length>=2 || nick.length<=10)){
			alert("닉네임을 2~10자리로  입력해주세요");
			document.getElementById("nick").focus();
			return false;
		}
		if(eflag==false){
			alert("이메일을 확인해주세요.");
			document.getElementById("id").focus();
			return false;
		}
		
		if(eauth==false){
			alert("이메일 인증을 해주세요.");
			document.getElementById("emailauth").focus();
			return false;
		}
		
		if(nflag==false){
			alert("이미 존재하는 닉네임입니다.");
			document.getElementById("nick").focus();
			return false;
		}
		
		if(pflag==false){
			alert("연락처를 확인해주세요.");
			document.getElementById("phone3").focus();
			return false;
		}
		return true;
	}
	
	function sample6_execDaumPostcode() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

	            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	            var fullAddr = ''; // 최종 주소 변수
	            var extraAddr = ''; // 조합형 주소 변수

	            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                fullAddr = data.roadAddress;

	            } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                fullAddr = data.jibunAddress;
	            }

	            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
	            if(data.userSelectedType === 'R'){
	                //법정동명이 있을 경우 추가한다.
	                if(data.bname !== ''){
	                    extraAddr += data.bname;
	                }
	                // 건물명이 있을 경우 추가한다.
	                if(data.buildingName !== ''){
	                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                }
	                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
	                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
	            }

	            // 우편번호와 주소 정보를 해당 필드에 넣는다.
	            document.getElementById('sample6_postcode').value = data.zonecode; //5자리 새우편번호 사용
	            document.getElementById('sample6_address').value = fullAddr;

	            // 커서를 상세주소 필드로 이동한다.
	            document.getElementById('sample6_address2').focus();
	        }
	    }).open();
	}
