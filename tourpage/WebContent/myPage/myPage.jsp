<%@page import="tourpage.myPage.db.MyPageDAO"%>
<%@page import="tourpage.member.db.MemberDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="tourpage.makePlan.db.PlanDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="tourpage.makePlan.db.PlanDAO"%>
<%@page import="tourpage.member.db.MemberBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<link rel="stylesheet" type="text/css" href="css/commons/defaultCSS.css">
		<link rel="stylesheet" type="text/css" href="css/myPage/myPage.css">
		<link href="https://fonts.googleapis.com/css?family=Jua|Nanum+Gothic" rel="stylesheet">
		<link rel="shortcut icon" href="img/commons/favicon15.ico">
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script src="./js/commons/jquery-3.3.1.js"></script>
		<script src="./js/myPage/myPage.js"></script>
		<script src="./js/groupware/create.js"></script>
		<title>make your page, TOURPAGE</title>
	</head>
	<body>
		<div id="wrap">
			<!-- header menu -->
			<%@include file="../inc/header.jsp"%>
				<%
		
			MemberDAO mdao = new MemberDAO();
			String fromProfile = new MyPageDAO().getProfile(ssid);
			
		    %>
			<!-- header menu -->
			
			<!-- content -->
			<article> <!-- myPage side menu --> 



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
				<!-- ad div  -->
				<!-- x 버튼 누르면 사라지게 -->
				<!-- <div class="Ad">
					<div class="close" onClick="jQuery('.Ad').addClass('hidden')">X</div>
					Ad
				</div> -->
				<!-- ad div -->
			</div>
				<div id="contentBoxALayout">				
					<ul id="target">
					<li style="text-align: center;" class="">
						<div id="contentBox1">
						<img alt="로딩" src="./img/myPage/loding.gif" style="margin:40px;">
						</div>
						</li>
					</ul>					
					<div class="contentBox2">
						일정을 추가해보세요 <br> 
						<a href="./tourpage.pl">add</a>
					</div>				
				</div>
				<!-- contentBoxALayout --> <!-- 오른쪽 자세한 계획페이지 -->
				<div id="contentBoxB">
					<div class="planImg">
						<img alt="img" src="./img/myPage/back.jpg">
					</div>
					<div class="planTxt">
						<span class="titB"></span>
					</div>
					<!-- planTxt title -->
					<!-- 디테일 일정 텍스트박스 레이아웃잡아놓은거 -->
					<div class="planTxt2">
						<hr>
						<span class="date"></span><br>
						<hr>
						<span class="plist"></span><br>
						<hr>
						인원 : A B C<br>
						<hr>
						<br>
						<div class="dlist"></div>
						<hr>
						<br>
					</div>
					<!-- 버튼박스 -->
					<div class="planBtn">
						<button class="mpBtn" id="shbtn">일정공유하기</button>
						<button class="mpBtn" id="modifyPlan">일정수정하기</button>
						<button class="mpBtn" id="shbtn2">일정삭제하기</button>
						<button class='mpBtn' id='gwbtn'>그룹웨어 생성</button>
					</div>
				</div>
				<!-- contentBoxB --> <!-- 오른쪽 디테일계획박스 밑에 깔려있는거  -->
				<div id="contentBoxC">일정을 추가해보세요</div>
				
				<div id="gwJoin">
					<form method="post" name="gwFr">
						<label>아이디</label><br>
						<!-- 이메일을 입력해주세요 -->
						<input type="text" id="groupid" class="gwInp" maxlength="15" onkeyup="groupenterKey();"/><br>
						<label id="groupidChkResult" class="smallLabel"></label><br>
						<label>비밀번호</label><br>
						<input type="password" id="grouppass1" name="pass1" class="gwInp" maxlength="20" onkeyup="groupenterKey();"/><br>
						<label>비밀번호확인</label><br>
						<input type="password" id="grouppass2" name="pass2" class="gwInp" maxlength="20" onkeyup="groupenterKey();"/><br>
						<!------------- 패스워드 체크 결과 레이블 --------------->
						<label id="passChkResult" class="smallLabel"></label>
						<br>
						<input type="button" value="create" class="gwSbm" onclick="groupidChk();"/><br>
					</form>
				</div>
			</article>
			
			<!-- footer -->
			<%@include file="../inc/footer.jsp" %>
			<!-- footer -->
		</div>
		<div class="page_cover" onclick="history.back();"></div>
		<!-- wrap -->
		<form name="hifr" method="post" id="hifr">
			<input type="hidden" name="plan_idx" id="plan_idx">
		</form>
	<script type="text/javascript">
		function getUnread() {
			$.ajax({
				type: "POST",
				url : "./ChatUnreadController",
				data : {
					userID : encodeURIComponent('<%= ssid %>'),
				},
				success : function(result) {
					if(result >= 1) {
						showUnread(result);
					} else {
						showUnread('');
					}
				}
			});
		}
		function getInfiniteUnread() {
			setInterval(function() {
				getUnread();
			}, 4000);
		}
		function showUnread(result) {
			$('#unread').html(result);
		}
		var openWin;
		function winopen(){
			window.name="parentForm";
			openWin = window.open("./myPageProfileUpdate.jsp", 
					"childForm", "width=570, height=350, resizeable=no, scrollbas=no");
		}
	</script>
		
	</body>
</html>