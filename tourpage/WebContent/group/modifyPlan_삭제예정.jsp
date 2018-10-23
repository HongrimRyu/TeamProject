<%@page import="tourpage.group.db.GroupBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="tourpage.makePlan.db.PlaceDTO"%>
<%@page import="tourpage.makePlan.db.PlanDTO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link rel="stylesheet" type="text/css" href="./css/commons/defaultCSS.css">
	<link rel="stylesheet" type="text/css" href="./css/makePlan/map.css">
	<link rel="stylesheet" type="text/css" href="./css/makePlan/plan_v_180718_map.css">
	<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic"  rel="stylesheet">
	<link rel="shortcut icon" href="./img/commons/favicon15.ico">
	<link rel="stylesheet"  href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link rel="stylesheet" href="/resources/demos/style.css">
	  <!-- Link Swiper's CSS -->
  <link rel="stylesheet" href="./css/makePlan/swiper.min.css">
	
	
	
	
	<script src="./js/commons/jquery-3.3.1.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="./js/groupware/commons_plan_js_.js"></script>
	<script src="./js/groupware/modifyPlan_.js"></script>
	<script src="./js/groupware/jquery.uploadPreview.js"></script>
	<script src="./js/groupware/markerclusterer_packed.js"></script>
	
	<title>TourPage</title>
</head>
<body>
<% 

GroupBean gb = (GroupBean)request.getAttribute("plan");
Map<Integer, PlaceDTO> map=(HashMap)request.getAttribute("placeinfo");

%>
	<%
	String groupid = "";
	session.setAttribute("group_id", "skyrhl");
	
	if(session.getAttribute("group_id")!=null){
		groupid = (String)session.getAttribute("group_id");
	}else{
		%>
		<script type="text/javascript">
			alert("잘못된 접근입니다.");
			location.href="./GroupLogin.gw";
		</script>
		<%
	}
// 	String nick = (String)session.getAttribute("nick");
	String nick = "test"+ Math.random();//(String)session.getAttribute("nick");
	%>
	<script type="text/javascript">
	var groupid = "<%=groupid%>";
	var nick = "<%=nick%>";
	$(document).ready(function(){
		var inputMessage = document.getElementById('inputMessage');
		var textarea = document.getElementById("messageWindow");
	});
		
    function onMessage(event) {

    	console.log(event.data);
    	var str = event.data.split("/");
    	if(str[0]=="actiondd"){
        	var number=1
        	g_idx+=1;
    		var place=JSON.parse(str[1]);
    		console.log(place.prod);
	     	$('.plan_listA').append('<li class="tour_item ui-state-default" data="'+g_idx+'"data-idx="'+number+'" data-day="'+str[2]+'"'+
	     			'lat="'+place.lat+'" lng="'+place.lng+'" name="'+place.name+'" addr="'+place.address+'"'+
	     			'prod="'+place.prod+'" type="'+place.type+'" style="cursor:all-scroll;"></li>');
	    	var str_list = '<div class="img_box fl"><img src="./img/makePlan/info/'+place.image+'">'+
					'</div><div class="info_box fl">'+place.name+
	 				'<div class="control_box"><img src="./img/makePlan/del_btn.png" class="del_btn" onclick="deletePlan('+g_idx+','+day_num+')"></div>'+
	 				'</div><div id="clear"></div>';
	    	$('.tour_item[data='+g_idx+']').append(str_list);
	    	if(str[2]!=day_num) $('.tour_item[data='+g_idx+']').css("display","none");
	     	for(var i=0 ; i < g_days ; i +=1){
	     		day_num=i;
	     		day_m[i]=[];
	    	 $('.tour_item[data-day='+i+']').each(function(i,e){
	    		var point = new google.maps.LatLng(
	    		        parseFloat($(this).attr('lat'),10),
	    		        parseFloat($(this).attr('lng'),10)
	    		        );//위도 경도 객체로 저장.
	    		var infowincontent = '<div><strong>'+$(this).attr('name')+'</strong><br>'+
	    							  $(this).attr('addr')+'<br>'+
	    							  '<img src="./img/makePlan/add.jpg" class="add_btn"'+
	    							  											'data-prod="'+$(this).attr('data-idx')+'"></div>';
	    		planMarker(point,$(this).attr('type'),infowincontent,$(this).attr('data-idx'));
	    	 });
	     	}
	    		    	
	    	drawPath();
    		return ;
    	}else if(str[0]=="add"){
    		var plan_m=[];
    		day_m.push(plan_m);
    		day_num=day_m.length-1;
    		g_days=day_m.length;
    		var date = document.getElementById('date'),value
    		listPrint();
    		drawPath();
    		setplan(s_date)
    		return ;
    	}else if(str[0]=="sort"||str[0]=="action"){
    		var place=JSON.parse(str[1]);
    		console.log(str[2]);
    		var day = str[2].split("@");
    		console.log('길이 : '+ day.length);
    		$('.plan_listA *').remove();
    		for(var i =0, max=day.length ; i < max ; i+=1){
    			var day_plan=day[i].split(",");
    			console.log(day_plan);
    			for(var j=0,mx=day_plan.length ; j < mx ; j+=1){
    			g_idx +=1;
    	     	$('.plan_listA').append('<li class="tour_item ui-state-default" data="'+g_idx+'"data-idx="'+place[day_plan[j]].prod+'" data-day="'+i+'"'+
    	     			'lat="'+place[day_plan[j]].lat+'" lng="'+place[day_plan[j]].lng+'" name="'+place[day_plan[j]].name+'" addr="'+place[day_plan[j]].address+'"'+
    	     			'prod="'+place[day_plan[j]].prod+'" type="'+place[day_plan[i]].type+'" style="cursor:all-scroll;"></li>');
    	    	var str_list = '<div class="img_box fl"><img src="./img/makePlan/info/'+place[day_plan[j]].image+'">'+
    					'</div><div class="info_box fl">'+place[day_plan[j]].name+
    	 				'<div class="control_box"><img src="./img/makePlan/del_btn.png" class="del_btn" onclick="deletePlan('+g_idx+','+day_num+')"></div>'+
    	 				'</div><div id="clear"></div>';
    	    	$('.tour_item[data='+g_idx+']').append(str_list);
    	    	if(i!=day_num) $('.tour_item[data='+g_idx+']').css("display","none");
    		}
    			
    	}
    	drawPath();
    	return ;
    	}else if(str[0]=="delete"){
    		alert("삭제됩니다");
    		Array.prototype.removeElement = function(index)
    		{

    			this.splice(index,1);
    			
    			return this;

    		};
    		$('.tour_item[data-day='+str[2]+']').each(function(i,e){
    			console.log($(this).attr('data')+','+str[1]);
    			if($(this).attr('data')==str[1]){
    				day_m[str[2]][i].setMap(null);
    				day_m[str[2]].removeElement(i);
    				
    			}
    		});
    		$('.tour_item[data='+str[1]+']').remove();
    		drawPath();
    		return;
    	}
    	
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
	    			$("#messageWindow").html($("#messageWindow").html()
	                        + "<p class='chat_content'><b class='impress'>" + event.data + "하였습니다.</b></p>");
	    		}
	        }else {
	                if (content.match("!")) {
	                    $("#messageWindow").html($("#messageWindow").html()
	                        + "<p class='chat_content'><b class='impress'>" + sender + " : " + content + "</b></p>");
	                }else {
	                	if(sender==nick){
	                    	$("#messageWindow").html($("#messageWindow").html()
	                        + "<p class='chat_content'>나 : " + content + "</p>");
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
                    + "<p class='chat_content'>나 : " + inputMessage.value + "</p>");
        }
        webSocket.send(nick + "|" + inputMessage.value);
        inputMessage.value = "";
        var elem = document.getElementById('messageWindow');
        elem.scrollTop = elem.scrollHeight;
    }
    //     엔터키를 통해 send함
    function enterkey() {
        if (window.event.keyCode == 13) {
            send();
        }
    }
    function action(){
    	 webSocket.send('action');
    }
	</script>
<input type="hidden" id="startdate" value="<%=gb.getPlan_startdate() %>" >
<input type="hidden" id="days" value="<%=gb.getPlan_days() %>" >
 <div id="login_cover"></div>
 <div class="login">
    <div class="txt">login</div>
	<div class="lgForm">
		<form action="./LoginActionInMain.me" method="post" name="lgFr"> <br> 
			<input type="text" name="id" id="id" class="lgInp" /><br> 
			<input type="password" name="pass" id="pass" class="lgInp" /><br> 
			<input type="button" value="login" class="lgSbm" /><br>
		</form>
	</div>
</div>
	<div id="wrap">
		<!-- header menu -->
		<%@include file="../inc/header.jsp" %>
		<!-- header menu -->
		<article> <aside>
		<div id="asideLayout">
		<div  id="place_add" >
		  <form action="placeAddAction.pl" method="post" name="fr" id="fr"  enctype="multipart/form-data">
			  <input type="hidden" id="type" name="type">
		
			  <div>
			  	<div style="background-color: gray; height: 40px;">
			  		<span style="font-weight: bold; font-size: 30px;">신규 장소 등록</span>
			  		<img src="./img/makePlan/close_btn.png" class="cls_btn" style="cursor: pointer; position: relative; top: 5px; right: -215px" class="add_close">
			  	</div>
			  	<div style="padding: 15px;">
			  	  	-이미지<br>
					<div id="image-preview"  style="border: 1px dotted #aeaeae; border-radius: 5px; padding-bottom: 5px">
					  <label for="image-upload" id="image-label">Choose File</label>
					  <input type="file" name="image" id="image-upload" />
					</div>
			  		
			  	   -장소 이름*<br>
				   <input id="pac-name" name="pac-name"class="controls" type="text"
				   				style=" border-radius: 5px; width: 100%; height: 25px; padding-bottom: 5px" title="장소이름을 적어주세요." ><br>
				   -장소 주소<br>
				   <input id="pac-input" name="pac-input" class="controls" type="text" placeholder="Search Box" 
				   				style=" border-radius: 5px; width: 100%; height: 25px; padding-bottom: 5px"><br>
				   <input id="latlng" name="latlng" type="hidden">
				   <div id="map"></div>
				   -장소 설명<br>
				   <textarea name="detail" id="detail" rows="10" cols="65"  style="width:100%; resize: none; border-radius: 5px;"></textarea>
				   -운영시간<br>
				   <input id="o_time" name="o_time" class="controls" type="text" 
				   				style=" border-radius: 5px; width: 100%; height: 25px; padding-bottom: 5px"><br>
				   -연락처<br>
				   <input id="phone" name="phone" class="controls" type="text" 
				   				style=" border-radius: 5px; width: 100%; height: 25px; padding-bottom: 5px"><br>
				   -가격정보<br>
				   <input id="price" name="price" class="controls" type="text"  
				   				style=" border-radius: 5px; width: 100%; height: 25px; padding-bottom: 5px"><br>
				   -교통정보<br>
				   <input id="traffic" name="traffic" class="controls" type="text" 
				   				style=" border-radius: 5px; width: 100%; height: 25px; padding-bottom: 5px"><br>
				   -홈페이지<br>
				   <input id="homepage" name="homepage" class="controls" type="text"
				   				style=" border-radius: 5px; width: 100%; height: 25px; padding-bottom: 5px"><br>
				   </div>
			</div>
			</form>	   
			  <div class="cat_box">
			   <div>카테고리*</div>
			   <div class="cat_img">
			   	<img  class="class_item" src="./img/makePlan/category/shop_1.png" data-name="shop" style="cursor: pointer; filter:grayscale(100);">
			   	<img  class="class_item" src="./img/makePlan/category/rest_1.png" data-name="rest" style="cursor: pointer; filter:grayscale(100);">
			   	<img  class="class_item" src="./img/makePlan/category/expr_1.png" data-name="expr" style="cursor: pointer; filter:grayscale(100);">
			   	<img  class="class_item" src="./img/makePlan/category/history_1.png" data-name="history" style="cursor: pointer; filter:grayscale(100);">
			   	<img  class="class_item" src="./img/makePlan/category/nature_1.png" data-name="nature" style="cursor: pointer; filter:grayscale(100);">
			   </div>
			  </div>
			  <div id="clear"></div>
			 
			  <div class="btn_area">
			  		<img class="addPlace_btn" src="./img/makePlan/add_btn.png">
			  </div>
		  </div>
		   <div id="detail_form">
				<img class="close" style="float:right ; cursor: pointer;" src="./img/makePlan/close1.png" >
			    <img class="detail_img" style="width:100%;height:200px;">
			    <img src="./img/makePlan/add_btn.png" id="detail_btn" style="cursor: pointer;  position: relative; float: right;  top: -30px;  right: 20px;">
			    <div class="detail_name" style="font-size: 30px;font-weight: bold; position: relative; top: -30px; left: 20px; width: 290px;"></div>
			    <div class="detail_item" style="padding: 20px;"></div><br>
			    <div class="detail_address" style="word-break:break-all; position: relative; top: -15px; left: 20px;"></div><br>
			    <div class="detail_o_time" style="word-break:break-all; position: relative; top: -15px; left: 20px;"></div><br>
			    <div class="detail_phone" style="word-break:break-all; position: relative; top: -15px; left: 20px;"></div><br>
			    
			</div>
			<div id="planLeft">
				<!-- 일정추가 -->
				<input type="button" class="planButton2" value="날짜변경하기">
				<div class="picker-popup" style="z-index: 9999; position: absolute;"></div> 
				<input type="button" class="planAddButton" value="일정추가">
				<!-- 일정추가 -->
				<div class="planLeftA">
				<ul class="leftlist" id="sortable1"></ul>
				</div><!-- 날짜 출력 -->
				<div class="planLeftB">
					<input type="button" class="planButton2_1" value="일정 전체 삭제" onclick="location.reload() ">
					<input type="button" class="planSaveButton" value="일정저장"
						onclick="modifyPlan();">
				</div>
			</div>
			<div id="planRight">
				<div class="planRightTop" style="border : 1px solid #92dcff; padding:15px; height: 50px; text-align:center;"> 1 일차</div>
				<div class="planRightA" >
				   <ul class="plan_listA" id="sortable">
				       <% 
				       		int data_day=0,data=0;
				       		String plan_de[]=gb.getPlan_detail().split("@");
				       		for(int j=0,days=gb.getPlan_days();j<days;j+=1){
				       		
					       String[] placelist = plan_de[j].split(",");
					       for(int i=0,max=placelist.length ; i < max ; i+=1){
					    	   System.out.println(map.containsKey(Integer.parseInt(placelist[i],10)));
					    	   System.out.println(placelist[i]);
					    	   PlaceDTO pldto = map.get(Integer.parseInt(placelist[i],10));
					    	   
				       %>
				     	<li class="tour_item ui-state-default" data="<%=data %>" data-day="<%=data_day %>" 
				     			lat="<%=pldto.getLat() %>" lng="<%=pldto.getLng() %>" name="<%=pldto.getName() %>" addr="<%=pldto.getAddress() %>"
		     			        prod="<%=pldto.getProd()%>" type="<%=pldto.getType() %>" style="cursor:all-scroll;">
		    				<div class="img_box fl"><img src="./img/makePlan/info/<%=pldto.getImage()%>">
							</div><div class="info_box fl"><%=pldto.getName() %>
		 					<div class="control_box"><img src="./img/makePlan/del_btn.png" class="del_btn" onclick="deletePlan(<%=data%>,<%=data_day%>)"></div>
		 					</div><div id="clear"></div>
				         </li>
				       
				       <%
				       		data+=1;
				       		}
					       	data_day += 1;
				       		}
					   %>
				   </ul>
				</div>
				<div class="planRightB">
					<!-- 장소추가버튼 -->
					<input type="text" id="pInput" class="planInput">
					<br>
					<input
						type="button" value="장소추가하기" onclick="addMap();"
						class="planButton3"><br>
				</div>
			</div>
		</div>
		</aside> <section>
		<div id="sLayout">
			<div class="mapLayout">
				<div class="mapIcon">
					<!-- 아이콘 이미지 들어갈 예정 -->
					<ul>
						<li>쇼핑</li>
						<li>역사</li>
						<li>식당</li>
						<li>교통</li>
						<li>자연</li>
						<li>문화</li>
						<li>숙소</li>
						<li>북마크</li>
					</ul>
				</div>
				<!-- map들어갈곳 -->
				<div id="googleMap"></div>			
				<script async defer	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA0RaRBOgbRI5gID0YDXFW41VUh2rfBLqE&libraries=places&callback=initMap"></script>
				<!-- map들어갈곳 -->
			</div>
			<div class="placeLayout">
				<div class="RightBox" style="padding: 15px; height: 100%; width: 100%;">
					<div class="searchBox">
						<input type="text" id="search" style="border-radius: 5px; width:100%; height: 50px; margin-bottom: 30px;">
					</div>
					<div class="placeBox" style="height: 80%; width: 100%; border: 1px solid; overflow: scroll;">					</div>
				</div>
			</div>
		</div>
			</section> </article>
		<!-- footer -->
		<%@include file="../inc/footer.jsp" %>
		<!-- footer -->
		</div>
		
		<form  method="post" id="myform">
			<input type="hidden" id="plan_idx" name="plan_idx" value="<%=gb.getPlan_idx() %>">
			<input type="hidden" id="plan_title" name="plan_title" value="<%=gb.getPlan_name()%>">
			<input type="hidden" id="planInfo" name="planInfo">
			<input type="hidden" id="plan_start" name="plan_start">
			<input type="hidden" id="period" name="period">
		</form>
		  <!-- Swiper JS -->
  <script src="./js/makePlan/swiper.min.js"></script>

	  <!-- Initialize Swiper -->
	  <script>
	  var swiper = new Swiper('.swiper-container', {
		    slidesPerView: 3,
		    spaceBetween: 30,
		    slidesPerGroup: 3,
		    loop: true,
		    loopFillGroupWithBlank: true,
		    pagination: {
		      el: '.swiper-pagination',
		      clickable: true,
		    },
		    navigation: {
		      nextEl: '.swiper-button-next',
		      prevEl: '.swiper-button-prev',
		    },
		  });
		var webSocket = new WebSocket('ws://127.0.0.1:8088/tourpage/GroupChat.gw/'+groupid+"/"+nick);
		webSocket.onerror = function(event) {
	        onError(event)
	    };
	    webSocket.onopen = function(event) {
	        onOpen(event)
	    };
	    webSocket.onmessage = function(event) {
	        onMessage(event)
	    };
	  
	  
	  </script>

  
	</body>
</html>