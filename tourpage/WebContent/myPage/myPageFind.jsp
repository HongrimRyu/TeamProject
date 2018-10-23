<%@page import="tourpage.member.db.MemberBean"%>
<%@page import="tourpage.myPage.db.MyPageDAO"%>
<%@page import="tourpage.myPage.db.MyPageBean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<link rel="stylesheet" type="text/css" href="css/myPage/Custom.css">
<link href="https://fonts.googleapis.com/css?family=Jua|Nanum+Gothic" rel="stylesheet">
<link rel="shortcut icon" href="img/favicon15.ico">
<!-- js / jq -->
<script src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="./js/members/join_180720.js"></script>

<script type="text/javascript">
	function findFunction() {
		var userID = $('#findID').val();
		$.ajax({
			type : "POST",
			url : './UserRegisterCheckServlet',
			data : {userID : userID},
			success : function(result) {
				if (result == 0) {
					alert("친구찾기에 성공했습니다.");
					$('#checkMessage').html('친구찾기에 성공했습니다.');
					$('#checkType').attr('class', 'model-content panel-success');
					getFriend(userID);
				} else {
					alert("친구를 찾을 수 없습니다.");
					$('#checkMessage').html('친구를 찾을 수 없습니다..');
					$('#checkType').attr('class', 'model-content panel-success');
					failFriend();
				}
				// $('#checkModal').modal('show');
			}
		});
	}
	function getFriend(findID){
		$('#friendResult').html('<thead>' +
			'<tr>' +
			'<th><h4>검색 결과</h4></th>' +
			'<tr>' +
			'</thead>' +
			'<tbody>' +
			'<tr>' +
			'<td style="text-align:center;">' + findID + '</h3><a href="MyPageChat.mp?toID=' + encodeURIComponent(findID) + '" class="btn btn-parimary pull-right">' + '메세지 보내기</a></td>' +
			'</tr>' +
			'</tbody>');
	}
	function failFriend() {
		$('#friendResult').html('');
	}
</script>


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
		
			<div id="contentBoxFull">
				<div class="fullLayout">
					<div class="fullTit"><img src="./img/commons/dot.png"> 친구찾기</div>
					<div class="fullCon">
					
						<div class="container">
							<table class="table table-bordered table-hover" style="width:600px; border: 0px; text-align:left;">
								<thead>
									<tr>
										<th colspan="2"><h3><img src="./img/commons/menuicon.png"> 검색으로 친구찾기</h3></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="width:110px"><h5>친구 아이디</h5></td>
										<td><input class="form-control" type="text" id="findID" maxlength="20" placeholder="찾을 아이디를 입력하세요."></td>
									</tr>
									<tr>
										<td colspan="2"><button class="btn btn-parimary pull-right" onclick="findFunction();">검색</button></td>
									</tr>
								</tbody>
							</table>
						</div><!-- container -->
						<div class="container">
							<table id="friendResult" class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd;">
							</table>
						</div><!-- container -->
	<%
		String messageContent = null;
		if(session.getAttribute("messageContent") != null) {
			messageContent = (String) session.getAttribute("messageContent");
		}
		String messageType = null;
		if(session.getAttribute("messageType") != null) {
			messageType = (String) session.getAttribute("messageType");
		}
		if (messageContent != null) {
	%>
						<div class="modal fade" id="messageModal" tabindex="-1" role="diaLog" aria-hidden="true">
							<div class="vertical-alignment-helper">
								<div class="modal-diaLog vertical-align-center">
									<div class="modal-content <% if(messageType.equals("오류 메세지")) out.println("panel-warning"); %>">
										<div class="modal-header panel-heading">
											<button type="button" class="close" data-dismiss="modal">
												<span aria-hidden="true">&times</span>
												<span class="sr-only">Close</span>
											</button>
											<h4 class="modal-title">
												<%= messageType %>
											</h4>
										</div>
										<div class="modal-body">
											<%= messageContent %>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
										</div>
									</div>
								</div>
							</div>
						</div><!-- messageModal -->
					
					</div><!-- fullCon -->
				</div><!-- fullLayout -->
			</div><!-- contentBoxFull -->
		
		<script>
			$('#messageModal').modal("show");
		</script>
	<%
		session.removeAttribute("messageContent");
		session.removeAttribute("messageType");
		}
	%>
		</article>
		<!-- footer -->
		<div class="fixedfooter"><jsp:include page="../inc/footer.jsp"/></div>
		<!-- footer -->
</div><!-- wrap -->
</body>
</html>