<%@page import="tourpage.myPage.db.MyPageDAO"%>
<%@page import="tourpage.makePlan.db.PlaceDAO"%>
<%@page import="tourpage.makePlan.db.PlaceDTO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/commons/defaultCSS.css">
		<link rel="stylesheet" type="text/css" href="css/myPage/myPage.css">
		<link href="https://fonts.googleapis.com/css?family=Jua|Nanum+Gothic"
			rel="stylesheet">
		<link rel="shortcut icon" href="img/commons/favicon15.ico">
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script src="./js/commons/jquery-3.3.1.js"></script>
		<title>make your page, TOURPAGE</title>
		<%
		if(request.getAttribute("myGroupList")==null){
			response.sendRedirect("./Login.me");
		}
		JSONArray arr = (JSONArray)request.getAttribute("myGroupList");
		%>
		<script type="text/javascript">
			var del_idx=0;
			function copy(val){
				var t = document.createElement("textarea");
				document.body.appendChild(t);
				t.value = val;
				t.select();
				document.execCommand('copy');
				document.body.removeChild(t);
				alert("클립보드에 복사되었습니다.");
			}
			
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
			
			function delcallback(){
			    if(httpRequest.readyState == 4){
			    	var res = httpRequest.responseText;
			    	var cnt = 1;
			    	if(res==1){
			    		$("div#contentBox1:eq("+del_idx+")").addClass("active2");
			    		var counter = setInterval(function() {
			    			if(cnt<2){cnt++;}
			    			else {$("li#contentli:eq("+del_idx+")").remove();clearInterval(counter);}
			    		}, 400);
			    		
			    	}
			    }
			}
			
			function deleteChk(val,group_id){
				if(confirm("정말 삭제하시겠습니까?")){
					del_idx = $('button').index(val);
					var param = "group_id="+group_id
					httpRequest = getXMLHttpRequest();
			        httpRequest.onreadystatechange = delcallback;
			        httpRequest.open("POST", "GroupDeleteAction.gw", true);    
					httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); 
			    	httpRequest.send(param);
				}
			}
			
		</script>
	</head>
	<body>
	<div id="wrap">
		<!-- header --> 
			<%@include file="../inc/header.jsp"%>
				<%
		
			MemberDAO mdao = new MemberDAO();
			String fromProfile = new MyPageDAO().getProfile(ssid);
			
		    %>
		<!-- header--> 
		<article>
			<!-- side menu -->
			
		<div id="myPage">
			<div id="sideMenuBox">
				<!-- 프로필사진 -->
				<div class="profile" name="pPic">
					<a href="<%= fromProfile %>" target="_blank"><img alt="profile" src="<%= fromProfile %>" width="100%" height="100%" name="pic"></a>
				</div>
				<!-- 간단프로필? -->
				<div class="profileList">
					<span><%=headernick %></span> 
					<a href="./MyPageBox.mp"><img alt="메세지함" src="./img/myPage/message.png" width="20px">
					<span id="unread" class="label label-info">0</span></a>
				</div>
				<!-- 메뉴 -->
				<div class="myPageList">
				<ul>
					<hr>
					<li><a href="./MyPage.mp"><img src="./img/commons/menuicon.png" width="5px"> 내 여행계획</a></li>
					<hr>
					<li><a href="./MyGroupware.mp"><img src="./img/commons/menuicon.png" width="5px"> 내 그룹웨어</a></li>
					<hr>
					<li><a href="./MyPageUpdate.mp"><img src="./img/commons/menuicon.png" width="5px"> 내 정보 수정</a></li>
					<hr>
					<li><a href="./MyPageFind.mp"><img src="./img/commons/menuicon.png" width="5px"> 친구찾기</a></li>
					<hr>
					<li><a href="./MyPageDelete.mp"><img src="./img/commons/menuicon.png" width="5px"> 회원탈퇴</a></li>
					<hr>
				</ul>
				</div>
				
				<%
					if (ssid != null) {
				%>
					<script type="text/javascript">
						$(document).ready(function() {
							getInfiniteUnread();
						});
					</script>
				<%
					}
				%>
			</div>
				<div id="contentBoxALayout" style="grid-column:2/9;">				
					<ul id="target">
					<%for(int i=0;i<arr.size();i++){
						JSONObject jo = (JSONObject)arr.get(i);
						String detail = jo.get("plan_detail").toString().split(",")[0];
						PlaceDAO pdto = new PlaceDAO();
					%>
						<li id="contentli">
						 <div id='contentBox1'>
							<div class='planTxt'>
							 	<span class='titA'><%=jo.get("plan_name") %></span>
								 <br>
								 <span id='placeinfo'><%=pdto.getPlace(detail).getName() %> 출발</span>
								 <br>
								 <span id='days'>
								 <%=Integer.parseInt(jo.get("plan_days").toString())-1%>박 
								 <%=Integer.parseInt(jo.get("plan_days").toString())%>일
								 <button class="mpBtn" onclick="deleteChk(this,'<%=jo.get("group_id") %>');" style="float: right;">삭제</button>
								 </span>
					 		</div>
							 <div class='dday' style="font-size: 19px;">
							 	초대 URL : 
							 	<a href="" onclick="copy(this.innerHTML);" style="word-wrap: break-word;">
							 		http://54.180.2.191:8080/tourpage/GroupLogin.gw/<%=jo.get("group_id")%>
							 	</a>
							 </div>
							 
						 </div>
						 </li>
					 <%} %>
					</ul>					
				</div>
				<!-- contentBoxB --> <!-- 오른쪽 디테일계획박스 밑에 깔려있는거  -->
			</article>
			
			<!-- footer -->
			<%@include file="../inc/footer.jsp" %>
			<!-- footer -->
		</div>
	</body>
</html>