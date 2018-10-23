/**
 * 플랫폼연동 가입 유효성검사
 */


//중복 확인과 이메일 인증을 위한 flag
	var nflag=false;//닉네임 중복
	var pflag=true;//폰번호 중복
	//password 형식
	var ptest = /(?=.*\d)(?=.*[a-zA-Z])./;
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
	function Ncallback(){////닉네임 callback
	    if(httpRequest.readyState == 4){
	        var resultText = httpRequest.responseText;
	        if(resultText == 0){//중복
	            $('#nick').css('background-color','red');
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
	            $('#phone1').css('background-color','red');
	            $('#phone2').css('background-color','red');
	            $('#phone3').css('background-color','red');
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

	
	
	function authChk(){
		if(document.getElementById("authinput").value == authcode.trim()){
			$('#tempbr').remove();
			$('#authchk').remove();
			$('#authinput').remove();
			$('#authResult').html('이메일 인증 완료');
			eauth=true;
		}else{
			$('#authResult').html('인증번호를 다시 확인해주세요');
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
				$('#phone1').css('background-color','red');
	            $('#phone2').css('background-color','red');
	            $('#phone3').css('background-color','red');
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
				$('#pass1').css('background-color','red');
				$('#pass2').css('background-color','#f9f9f9');
				$('#passChkResult').html('비밀번호를 8~20자리로  입력해주세요');
			}
		}else if(!ptest.test(pass1)){
			/* alert("비밀번호를 영문+숫자로 입력해주세요"); */
			$('#pass1').css('background-color','red');
			$('#pass2').css('background-color','#f9f9f9');
			$('#passChkResult').html('비밀번호를 영문+숫자로 입력해주세요');
		}else if(!(pass1==pass2)){
			/* alert("패스워드가 일치하지 않습니다."); */
			$('#pass1').css('background-color','#f9f9f9');
			$('#pass2').css('background-color','red');
			$('#passChkResult').html('패스워드가 일치하지 않습니다.');
		}else{
			$('#pass1').css('background-color','#f9f9f9');
			$('#pass2').css('background-color','#f9f9f9');
			$('#passChkResult').html('패스워드 일치');
		}
		
	}
	//////////
	function chk(){////유효성 체크
			
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
	
	window.onload = function(){
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
	
	
	
	
	