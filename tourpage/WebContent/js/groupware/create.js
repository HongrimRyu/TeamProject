		
			/*********************
			
			#호출순서
			1. idChk() 아이디 유효성 검사 
			2. idcallback() DB와 데이터 비교 후 콜백
			3. passChk() 패스워드 유효성 검사
			4. createGroup 그룹 생성
			
			**********************/
		
			var idtest = /(?=.*[a-zA-Z])(?=.*[0-9a-zA-Z])/;
			var idktest = /[ㄱ-ㅎ가-힣]/;
			var ptest = /(?=.*\d)(?=.*[a-zA-Z])./;
			var idflag = false;
			var pflag = false;
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
			
			function idcallback(){///아이디 callback
			    if(httpRequest.readyState == 4){
			        // 결과값을 가져온다.
			        var resultText = httpRequest.responseText;
			        if(resultText == 0){
			            /* alert("이미 존재하는 아이디입니다."); */
			            $('#groupid').css('background-color','#EF5350');
			            $('#groupidChkResult').html('이미 사용중인 아이디입니다.');
			            $('#groupid').focus();
			            idflag = false;
			        } 
			        else if(resultText == 1){ 
			        	/* alert("사용 가능한 아이디입니다."); */
			        	$('#groupid').css('background-color','#f9f9f9');
			        	$('#groupidChkResult').html('사용 가능한 아이디입니다.');
			        	idflag = true;
			        	$('#grouppass1').focus();
			        	grouppassChk();
			        }
			    }
			}
			function callback(){///아이디 callback
			    if(httpRequest.readyState == 4){
			        alert("생성 성공");
			        $("#gwJoin,.page_cover,body").removeClass('open');
			        $('#groupid').val("");
			        $('#grouppass1').val("");
			        $('#grouppass2').val("");
			    }
			}

			
			function groupidChk(){
				var id = document.getElementById("groupid").value;
				if(!idtest.test(id)||idktest.test(id)){
					if(id==""){
						$('#groupid').css('background-color','#f9f9f9');
			            $('#groupidChkResult').html('아이디를 입력해주세요.');
			            $('#groupid').focus();
			            idflag=false;
					}else{
						$('#groupid').css('background-color','#EF5350');
			            $('#groupidChkResult').html('영문자 또는 영문자+숫자로 입력해주세요.');
			            $('#groupid').focus();
			            idflag=false;
					}
				}else if(id.length<6 || id.length>15){
					$('#groupid').css('background-color','#EF5350');
		            $('#groupidChkResult').html('아이디를 6~15자리로  입력해주세요');
		            $('#groupid').focus();
		            idflag=false;
				}else{
			        var id = document.getElementById("groupid").value;
					var param="id="+id;
			        httpRequest = getXMLHttpRequest();
			        httpRequest.onreadystatechange = idcallback;
			        httpRequest.open("POST", "GroupIdCheckAction.gw", true);    
			        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); 
			        httpRequest.send(param);
				}
			}
			
			function grouppassChk(){
				var pass1 = document.getElementById("grouppass1").value;
				var pass2 = document.getElementById("grouppass2").value;
				
				if(!(pass1.length>=8 & pass1.length<=20)){
					/* alert("비밀번호를 8~20자리로  입력해주세요"); */
					if(pass1=="" && pass2==""){
						$('#grouppass1').css('background-color','#EF5350');
						$('#grouppass2').css('background-color','#EF5350');
						$('#passChkResult').html('비밀번호를 입력해주세요');
						pflag=false;
					}else{
						$('#grouppass1').css('background-color','#EF5350');
						$('#grouppass2').css('background-color','#f9f9f9');
						$('#passChkResult').html('비밀번호를 8~20자리로  입력해주세요');
						pflag=false;
					}
				}else if(!ptest.test(pass1)){
					/* alert("비밀번호를 영문+숫자로 입력해주세요"); */
					$('#grouppass1').css('background-color','#EF5350');
					$('#grouppass2').css('background-color','#f9f9f9');
					$('#passChkResult').html('비밀번호를 영문+숫자로 입력해주세요');
					$('#grouppass1').focus();
					pflag=false;
				}else if(!(pass1==pass2)){
					/* alert("패스워드가 일치하지 않습니다."); */
					$('#grouppass1').css('background-color','#f9f9f9');
					$('#grouppass2').css('background-color','#EF5350');
					$('#passChkResult').html('패스워드가 일치하지 않습니다.');
					$('#grouppass2').focus();
					pflag=false;
				}else{
					$('#grouppass1').css('background-color','#f9f9f9');
					$('#grouppass2').css('background-color','#f9f9f9');
					$('#passChkResult').html('패스워드 일치');
					pflag=true;
					createGroup();
				}
				
			}
			
			function createGroup(){
				if(idflag && pflag){
					var id = document.getElementById("groupid").value;
					var pass= document.getElementById("grouppass1").value;
					var plan_idx=$('#plan_idx').val();
					var param="id="+id+"&pass="+pass+"&plan_idx="+plan_idx;
			        httpRequest = getXMLHttpRequest();
			        httpRequest.onreadystatechange = callback;
			        httpRequest.open("POST", "CreateGroupAction.gw", true);    
			        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); 
			        httpRequest.send(param);
				}
			}
			function groupenterKey(){
				if(window.event.keyCode==13){
					groupidChk();
				}
			}