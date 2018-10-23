<%@page import="tourpage.myPage.db.MyPageDAO"%>
<%@page import="tourpage.member.db.MemberBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>make your page, TOURPAGE</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- css -->
<link rel="stylesheet" type="text/css" href="css/commons/defaultCSS.css">
<link rel="stylesheet" type="text/css" href="css/myPage/myPage.css">
<link href="https://fonts.googleapis.com/css?family=Jua|Nanum+Gothic" rel="stylesheet">
<link rel="shortcut icon" href="img/commons/favicon15.ico">
<!-- js / jq -->
<script src="http://code.jquery.com/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="./js/join_180720.js"></script>
<script type="text/javascript">
	function setParentPic(){
		opener.document.getElementById("p").value = document.getElementById("pf").value
	}
	
	
	var sel_file;
	$(document).ready(function(){
		$("#pf").on("change", handleImgFileSelect);
	});
	
	function handleImgFileSelect(e){
		var files= e.target.files;
		var filesArr = Array.prototype.slice.call(files);
		
		filesArr.forEach(function(f){
			if(!f.type.match("image.*")){
				alert("이미지 확장자만 가능");
				return;
			}
			sel_file=f;
			var reader = new FileReader();
			reader.onload=function(e){
				$("#wrap_img").attr("src", e.target.result);
			}
			reader.readAsDataURL(f);
		});
	}
</script>
</head>
<body>
<%
	request.setCharacterEncoding("utf-8");
	String id = (String)session.getAttribute("id");
	if(id==null){
		response.sendRedirect("./Login.me");
	}
	
	MemberBean m = (MemberBean)request.getAttribute("m");
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
					<div class="fullTit"><img src="./img/commons/dot.png"> 회원정보수정</div>
					<div class="fullCon">
						<form action="./MyPageUpdateAction.mp" method="post" name="jiFr" onsubmit="return chk();" enctype="multipart/form-data">
							<div class="innerLayoutP">
								<label>프로필사진변경</label><br><br>
								<div class="img_wrap">
									<img id="wrap_img"/>
								</div>
								<input type="file" id="pf" class="mpInp btn"><br>
							</div><!-- innerLayoutP -->
							<div class="innerLayout">
								<label>이메일</label><br>
								<input type="text" class="mpInp" value="<%=m.getId() %>" readonly><br>							
								<label>비밀번호 변경</label><br>
								<input type="password" id="pass1" name="pass" class="mpInp" maxlength="20" required onkeyup="passChk();"/><br>
								<label>비밀번호 확인</label><br>
								<input type="password" id="pass2" name="pass2" class="mpInp" maxlength="20" required onkeyup="passChk();"/>
								<!------------- 패스워드 체크 결과 레이블 --------------->
								<label id="passChkResult" class="smallLabel"></label>
								<br>
								
								<label>닉네임</label><br>
								<input type="text" id="nick" name="nick" class="mpInp" maxlength="10" required onkeyup="nickChk();" value="<%=m.getNick()%>"/> 
								<label id="nickChkResult" class="smallLabel"></label>
								<br>
								<label>생년월일</label><br>
								<select class="mpInp" name="birth1">
								<%
								//연도
									for(int i=1930;i<2018;i++){
								%>
									<option value=<%=i %>><%=i %></option>
								<%} %>
								</select>
								<select class="mpInp" name="birth2">
								<%
									//월
									for(int i=1;i<=12;i++){
								%>
									<option value=<%=i %>><%=i %></option>
								<%}	%>
								</select>
								<select class="mpInp" name="birth3">
								<%
									//일
									for(int i=1;i<=31;i++){
								%>
									<option value=<%=i %>><%=i %></option>
								<%}	%>
								</select>
								<br>
								<label>성별</label><br>
								<select class="mpInp" name="gender">
									<option value="남">남</option>
									<option value="여">여</option>
								</select><br>
								
								<label>연락처</label><br>
								<input type="text" id="phone1" name="phone1" class="mpInp" maxlength="3" onkeyup="phoneChk();" value="010"/> -
								<input type="text" id="phone2" name="phone2" class="mpInp" maxlength="4" onkeyup="phoneChk();" /> -
								<input type="text" id="phone3" name="phone3" class="mpInp" maxlength="4" onkeyup="phoneChk();"/>
								<label id="phoneChkResult" class="smallLabel"></label><br>
								<br><br>
								<input type="submit" value="회원정보수정" class="mpSbm" /><br>
							</div><!-- innerLayout -->
						</form>
					</div><!-- fullCon -->
				</div><!-- fullLayout -->
			</div><!-- contentBoxFull -->
		</div><!-- myPage -->
		</article>
		
	<!-- footer -->
	<%@include file="../inc/footer.jsp" %>
	<!-- footer -->
	</div><!-- wrap -->
	
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