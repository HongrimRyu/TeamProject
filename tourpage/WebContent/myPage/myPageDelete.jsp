<%@page import="tourpage.myPage.db.MyPageDAO"%>
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
<script src="http://code.jquery.com/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
	<title>make your page, TOURPAGE</title>
</head>
<body>
<%
request.setCharacterEncoding("utf-8");
String id = (String)session.getAttribute("id");
if(id==null){
	response.sendRedirect("./Login.me");
}

%>
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
		<!-- side menu --> 
		<div id="contentBoxFull">
				<div class="fullLayout">
					<div class="fullTit"><img src="./img/commons/dot.png"> 회원정보삭제</div>
					<div class="fullCon">
					정말 탈퇴하시겠어요ㅜㅜ???<br><br><br>
					<form action="./MyPageDeleteAction.mp"method="post">
					<label>비밀번호를 입력하세요</label><br>
					<input type="password" name="pass" class="mpInp"><br>
					<input type="submit" value="탈퇴완료" class="mpSbm">
					</form>
					</div>
					</div>
				</div>
		</div>
		</article>
		<!-- footer -->
		<jsp:include page="../inc/footer.jsp" />
		<!-- footer -->
	</div>
</body>
</html>
