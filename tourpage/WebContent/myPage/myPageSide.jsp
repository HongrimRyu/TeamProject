<%@page import="tourpage.member.db.MemberDAO"%>
<%@page import="tourpage.member.db.MemberBean"%>
<%@page import="tourpage.myPage.db.MyPageDAO"%>
<%@page import="tourpage.myPage.db.MyPageBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
    String id = (String)session.getAttribute("id");
    
	MemberDAO mdao = new MemberDAO();
	String fromProfile = new MyPageDAO().getProfile(id);
	
	// String toProfile = new MyPageDAO().getProfile(toid);
%>
<script type="text/javascript">
	function getUnread() {
		$.ajax({
			type: "POST",
			url : "./ChatUnreadController",
			data : {
				userID : encodeURIComponent('<%= id %>'),
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
</script>
<script type="text/javascript">
var openWin;
	function winopen(){
		window.name="parentForm";
		openWin = window.open("./myPageProfileUpdate.jsp", 
				"childForm", "width=570, height=350, resizeable=no, scrollbas=no");
	}
	
</script>

<div id="myPage">
	<div id="sideMenuBox">
		<!-- 프로필사진 -->
		<div class="profile" name="pPic">
					<a href="<%= fromProfile %>" target="_blank"><img alt="profile" src="<%= fromProfile %>" width="100%" height="100%" name="pic"></a>
				</div>
				<!-- 간단프로필? -->
				<div class="profileList">
					<span>Nick</span> 
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
			if (id != null) {
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