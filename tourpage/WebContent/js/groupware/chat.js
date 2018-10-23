		
		
		$(document).ready(function(){
			var inputMessage = document.getElementById('inputMessage');
			var textarea = document.getElementById("messageWindow");
		});
			
	 
	    function onMessage(event) {
	
	    	console.log(event.data);
	   	
	    	if(event.data.match("@#users : ")){
	    		$("#users").html("");	
	    		var users = event.data.split(":")[1].split(",");
	    		for(var i=0; i<users.length-1;i++){
	    			$("#users").html($("#users").html()+"<p>"+users[i]+"</p>");	
	    		}
	    		
	    	}else{
		        var message = event.data.split("|");
		        var sender = message[0];
		        var content = message[1];
		        if (content == "") {
		        	
		        }else if(content==null){
		        	if(event.data.match("참여")){
		        		if(!(event.data.split("님")[0]==nick)){
			    			$("#messageWindow").html($("#messageWindow").html()
			                        + "<p class='chat_content'><b class='impress'>" + event.data + "하였습니다.</b></p>");
		        		}
		    		}
		        }else {
		                if (content.match("!")) {
		                    $("#messageWindow").html($("#messageWindow").html()
		                        + "<p class='chat_content'><b class='impress'>" + sender + " : " + content + "</b></p>");
		                }else {
		                	if(sender==nick){
		                    	$("#messageWindow").html($("#messageWindow").html()
		                        + "<p class='mychat'>" + content + "</p>");
		                    }else{
		                    	$("#messageWindow").html($("#messageWindow").html()
		                        + "<p class='chat_content'>" + sender + " : " + content + "</p>");
		                    }
		                }
		        }
	        var elem = document.getElementById('messageWindow');
	        elem.scrollTop = elem.scrollHeight;
	    	}
	    }
	    function onOpen(event) {
	        $("#messageWindow").html("<p class='chat_content'>채팅에 참여하였습니다.</p>");
	        join();
	    }
	  
	    function onError(event) {
	        alert(event.data);
	    }
	    function join(){
	    	webSocket.send(nick + "님이 참여");
	    }
	    function sendMsg() {
	        if (inputMessage.value == "") {
	        } else {
	        	$("#messageWindow").html($("#messageWindow").html()
	                    + "<p class='mychat'>" + inputMessage.value + "</p>");
	        }
	        webSocket.send(nick + "|" + inputMessage.value);
	        inputMessage.value = "";
	        var elem = document.getElementById('messageWindow');
	        elem.scrollTop = elem.scrollHeight;
	    }
	    //     엔터키를 통해 send함
	    function enterkey() {
	        if (window.event.keyCode == 13) {
	        	sendMsg();
	        }
	    }
		function chatOpen(){
			$('#_chatbox').addClass('open');
			$('#groupchatOpen').css('display','none');
		}
		function chatClose(){
			$('#_chatbox').removeClass('open');
			$('#groupchatOpen').css('display','block');
		}
