<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="tourpage.group.db.GmemoDAO"%>
<%@page import="tourpage.group.db.GmemoBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="tourpage.group.db.GroupBean"%>
<%@page import="tourpage.makePlan.db.PlaceDTO"%>
<%@page import="tourpage.member.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="css/commons/defaultCSS.css">
	<link rel="stylesheet" type="text/css" href="css/groupware/groupware.css">
	<link href="https://fonts.googleapis.com/css?family=Jua|Nanum+Gothic"
		rel="stylesheet">
	<link rel="shortcut icon" href="img/commons/favicon15.ico">
	<link rel="stylesheet" type="text/css" href="./css/commons/defaultCSS.css">
	<link rel="stylesheet" type="text/css" href="./css/groupware/map.css">
	<link rel="stylesheet" type="text/css" href="./css/groupware/plan_v_180718_map.css">
	<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic"  rel="stylesheet">
	<link rel="shortcut icon" href="./img/commons/favicon15.ico">
	<link rel="stylesheet"  href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link rel="stylesheet" href="/resources/demos/style.css">
	  <!-- Link Swiper's CSS -->
 	<link rel="stylesheet" href="./css/makePlan/swiper.min.css">

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="./js/commons/jquery-3.3.1.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="/tourpage/js/commons/commons_js_.js"></script>
	<script src="./js/groupware/commons_plan_js_.js"></script>
	<script src="./js/groupware/jquery.uploadPreview.js"></script>
	<script src="./js/groupware/markerclusterer_packed.js"></script>
	<script src="./js/groupware/group_map_.js"></script>
	<script src="./js/groupware/chat.js"></script>
	<script src="./js/groupware/group_memo.js"></script>
	<script src="./js/groupware/detail.js"></script>
	
	

<title>make your page, TOURPAGE</title>
	<%
	String groupid = "";
	System.out.println(session.getAttribute("group_id"));
	if(session.getAttribute("group_id")!=null){
		groupid = (String)session.getAttribute("group_id");
	}else{
		%>
			<script>
				alert("잘못된 접근입니다.");
				location.href="./GroupLogin.gw";
			</script>
		<%
	}
	String nick = (String)session.getAttribute("nick");
	%>
	<script type="text/javascript">
		var groupid = "<%=groupid%>";
		var nick = "<%=nick%>";
	</script>
	
</head>
<body>
<!-- <input type="hidden" id="groupid"> -->
<!-- <input type="hidden" id="nick"> -->
	<div id="wrap">
		<!-- header menu -->
		<%@include file="../inc/header.jsp" %>
		<!-- header menu -->
		<!-- content -->
		<div id="chat" style="position: absolute;">
			<article > 
			<div id="gWare">
			<!-- side -->
				<!-- <div id="gwSide">
					<div class="gwS_profile" onclick="">
					</div>
					<div class="gwS_list">
						<ul>
						<li class="detailli" onclick="changeDetail(this);">여행 일정</li>
						<li class="mapli" onclick="changeMap(this);">일정 수정</li>
						</ul>
					</div>
				</div> -->
			<!-- side -->
			
			<!-- content -->
			<div id="gwConLayout">
				<div id="gwCon">
					<div id="contentBox" class="hidden visible">
						<div class="planImg">
							<img alt="img" src="./img/myPage/back.jpg">
						</div>
						<div class="planTxt">
							<span class="title"></span>
						</div>
						<!-- planTxt title -->
						<!-- 디테일 일정 텍스트박스 레이아웃잡아놓은거 -->
						<div class="planTxt2">
							<hr>
							<span class="date"></span><br>
							<hr>
							<span class="peopleslb">인원: <span class="peoples"></span>
							<input type="button" class="modBtn" value="수정" onclick="poepleMod();">
							</span>
							<br>
							<hr>
							<br>
							<div class="dlist"></div>
							<hr>
							<br>
							<input type="button" class="modBtn" value="일정 수정하기" onclick="changeMap();">
						</div>
						<!-- 버튼박스 -->
						
					</div>
				</div>
			</div>
			<!-- content -->
			<!-- chat -->
			<div id="gwMemo" oncontextmenu="return false">
			    <div id="memoCover" ></div>
				<div id="gwMemoBox">
				<%
					
					GmemoDAO gdao= new GmemoDAO();
					List<GmemoBean> mlist = gdao.getMemoList(groupid);
					Iterator<GmemoBean> iter =  mlist.iterator();
					while(iter.hasNext()){
						GmemoBean gb = iter.next();
				%>
						<div class='gwMemoContent stickerMemo' data-idx="<%=gb.getIdx() %>" 
														style=" width:<%=gb.getGw_width()%>; height:<%=gb.getGw_height() %>; 
														left: <%=gb.getGw_left() %>; top: <%=gb.getGw_top()%>; ">
	        	           <div class='inner_memo'><span><%=gb.getGw_nick() %></span><div class='m_comment'><%=gb.getGw_comment() %></div>
		        	           <div class="configure" data-idx="<%=gb.getIdx() %>"
		        	            style="cursor: pointer; position: absolute; padding:  10px;bottom:  2%;  right: 2%;"><img alt="@" src="./img/commons/dot.png"></div>
	        	           </div>
	        	        </div>
				<%
					}
				%>							
				</div>
			</div>
			
			<!-- chat -->
			</div>
			</article>
			<!-- footer -->
			<jsp:include page="../inc/footer.jsp" />
			<!-- footer -->	
		</div>
		<!------------------ 채팅박스 --------------------->
		<div id="_chatbox" >
		<div id="groupchatClose" onclick="chatClose();">x</div>
	        <fieldset>
	            <div id="messageWindow"></div>
	        </fieldset>
	        <fieldset>
	        	<p class="uhead">채팅방 인원</p>
	            <div id="users"></div>
	        </fieldset>
	        <input id="inputMessage" type="text" onkeyup="enterkey()" />
	        <input type="submit" value="send" onclick="sendMsg()" class="chatBtn"/>
	    </div>
	    <!------------------ 채팅박스 --------------------->
	    
	    <!------------- 채팅오픈div ------------->
	    <div id="groupchatOpen" onclick="chatOpen();">▲채팅</div>
	    <!------------- 채팅오픈div ------------->
<div id="maps">
<div id="wrap_map">
 <article> 
   <aside>
      <div id="planTitle">
         <div id="title_show"><h1>제목을 입력하세요 </h1><img src="./img/makePlan/icon/icon_edit.png" alt="edit"></div>
         <div id="title_modify"><input type="text" id="in_title"><div id="m_btn"><img src="./img/makePlan/icon/icon_edit.png" alt="edit"></div></div>      
      </div>
      <div id="place_add">
       <div class="addNewPlace">
        <form action="placeAddAction.pl" method="post" name="fr" id="fr"  enctype="multipart/form-data">
           <input type="hidden" id="type" name="type">
                 <img src="./img/commons/xbox.svg" width="30px" class="xbox cls_btn">
             <span class="npTit">신규 장소 등록</span>
              <!-- 장소추가 input -->
                 <div class="npInput">
                  <img src="./img/commons/menuicon.png" width="5px"> 이미지<br>
                  <div id="image-preview"  style="border: 1px dotted #aeaeae; border-radius: 5px; padding-bottom: 5px">
                    <label for="image-upload" id="image-label">Choose File</label>
                    <input type="file" name="image" id="image-upload" />
                  </div><!-- image-preview -->
                    <img src="./img/commons/menuicon.png" width="5px"> 장소 이름*<br>
                  <input id="pac-name" name="pac-name"class="controls" type="text"><br>
                 <img src="./img/commons/menuicon.png" width="5px">  장소 주소*<br>
                  <input id="pac-input" name="pac-input" class="controls" type="text" placeholder="Search Box"><br>
                  <input id="latlng" name="latlng" type="hidden">
                  <div id="map"></div>
                 <img src="./img/commons/menuicon.png" width="5px">  장소 설명<br>
                  <textarea name="detail" id="detail" rows="10" cols="65"></textarea>
                  <img src="./img/commons/menuicon.png" width="5px"> 운영시간<br>
                  <input id="o_time" name="o_time" class="controls" type="text" ><br>
                 <img src="./img/commons/menuicon.png" width="5px">  연락처<br>
                  <input id="phone" name="phone" class="controls" type="text"><br>
                  <img src="./img/commons/menuicon.png" width="5px"> 가격정보<br>
                  <input id="price" name="price" class="controls" type="text"><br>
                  <img src="./img/commons/menuicon.png" width="5px"> 교통정보<br>
                  <input id="traffic" name="traffic" class="controls" type="text" ><br>
                  <img src="./img/commons/menuicon.png" width="5px"> 홈페이지<br>
                  <input id="homepage" name="homepage" class="controls" type="text"><br>
                  </div><!-- npInput -->
         </form>    
         <div class="cat_box">
            <div><img src="./img/commons/menuicon.png" width="5px"> 카테고리*</div>
            <div class="cat_img"> <!-- iconbox -->
               <img  class="class_item" src="./img/makePlan/icon/icon_shop.svg" width="40px" data-name="shop" style="cursor: pointer; filter:grayscale(100);">
               <img  class="class_item" src="./img/makePlan/icon/icon_restaurant.svg" width="40px" data-name="rest" style="cursor: pointer; filter:grayscale(100);">
               <img  class="class_item" src="./img/makePlan/icon/icon_culture.svg" width="40px" data-name="expr" style="cursor: pointer; filter:grayscale(100);">
               <img  class="class_item" src="./img/makePlan/icon/icon_drive.svg" width="40px" data-name="history" style="cursor: pointer; filter:grayscale(100);">
               <img  class="class_item" src="./img/makePlan/icon/icon_hotel.svg" width="40px" data-name="nature" style="cursor: pointer; filter:grayscale(100);">
            </div>
           </div><!-- cat_box -->
			  <div id="clear"></div>
			  <div class="btn_area"><!-- 추가하기 -->
			  		<img class="addPlace_btn" src="./img/makePlan/add_btn.png">
			  </div><!-- btn_area -->
		 </div><!-- .addNewPlace -->
		 </div><!-- #placeAdd -->
		   
	   <div id="detail_form">
			<img class="close xbox" src="./img/commons/xbox.svg">
		    <img class="detail_img">
		 <div class="dForm">   
		    <div class="detail_name"></div>
		    <div class="detail_item" ></div><br>
		    <div class="detail_address"></div><br>
		    <div class="detail_o_time"></div><br>
		    <div class="detail_phone"></div><br>
		</div>
		<img src="./img/makePlan/add_btn.png" id="detail_btn">
		</div><!-- detail_form -->
			
			
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
				<input type="button" class="planSaveButton" value="닫기" onclick="changeDetail()">
			</div>
		</div><!-- planLeft -->
			
			
		<div id="planRight">
			<div class="planRightTop"> 1 일차</div>
			<div class="planRightA" >
			   <ul class="plan_listA" id="sortable">
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
		</div><!-- planRight -->
	</aside> 
   <!-- 본문 지도부분 -->      
   <section>
      <div id="sLayout">
         <div class="mapLayout">
            <div class="mapIcon">
               <!-- 아이콘 이미지 들어갈 예정 -->
               <ul>
                  <li><img src="./img/makePlan/icon/icon_shop.svg" alt="쇼핑" width="40px"></li>
                  <li><img src="./img/makePlan/icon/icon_culture.svg" alt="문화" width="40px"></li>
                  <li><img src="./img/makePlan/icon/icon_restaurant.svg" alt="식당" width="40px"></li>
                  <li><img src="./img/makePlan/icon/icon_drive.svg" alt="교통" width="40px"></li>
                  <li><img src="./img/makePlan/icon/icon_hotel.svg" alt="숙박" width="40px"></li>
               </ul>
            </div>
            <!-- map들어갈곳 -->
            <div id="googleMap"></div>         
            <script async defer   src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA0RaRBOgbRI5gID0YDXFW41VUh2rfBLqE&libraries=places&callback=initMap"></script>
            <!-- map들어갈곳 -->
         </div>
         
         <!-- 우측 박스  -->
         <div class="placeLayout">
         <div class="icon">◀</div>
            <div class="RightBox">
               <div class="searchBox">
                <h3><img src="./img/commons/menuicon.png" width="5px"> 장소검색</h3>
                  <input type="text" id="search">
               </div>
               <div class="placeBox"></div>
            </div>
         </div><!-- placeLayout -->
         
      </div><!-- sLayout -->
   </section> 
   </article>
	<!-- 본문 끝   -->
		
	
	<form action="store.pl" method="post" id="myform">
		<input type="hidden" id="plan_idx" name="plan_idx">
		<input type="hidden" id="plan_title" name="plan_title">
		<input type="hidden" id="planInfo" name="planInfo">
		<input type="hidden" id="plan_start" name="plan_start">
		<input type="hidden" id="period" name="period">
	</form>
</div>
</div> 
		<script>
		var webSocket = new WebSocket('ws://54.180.2.191:8080/tourpage/GroupChat.gw/'+groupid+"/"+nick);
// 		var webSocket = new WebSocket('ws://127.0.0.1:8088/tourpage/GroupChat.gw/'+groupid+"/"+nick);

		webSocket.onerror = function(event) {
	        onError(event)
	    };
	    webSocket.onopen = function(event) {
	        onOpen(event)
	    };
	    webSocket.onmessage = function(event) {
	        onMessage(event)
	    };
	    printGroupDetail();
 </script>
	  
	 	
		
	</div><!-- wrap -->
</body>
</html>
