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

<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	
	String id = null;
	if (session.getAttribute("id") != null) {
		id = (String) session.getAttribute("id");
	}
	System.out.println(id);
	String toID = null;
	if(request.getParameter("toID") != null){
		toID = (String) request.getParameter("toID");
	}
	System.out.println(toID);
	MemberBean m = (MemberBean)request.getAttribute("m");
	
	String fromProfile = new MyPageDAO().getProfile(id);
%>

<script type="text/javascript">
	function autoCloasingAlert(selector, delay) {
		var alert = $(selector).alert();
		alert.show();
		window.setTimeout(function() { alert.hide() }, delay);
	}
	function submitFunction() {
		var fromID = '<%= id %>';
		var toID = '<%= toID %>';
		var chatContent = $('#chatContent').val();
		$.ajax({
			type: "POST",
			url : "./ChatSubmitController",
			data: {
				fromID : encodeURIComponent(fromID),
				toID : encodeURIComponent(toID),
				chatContent : encodeURIComponent(chatContent),
			},
			success : function(result) {
				if(result == 1) {
					autoCloasingAlert('#successMessage', 2000);
				} else if (result == 0) {
					autoCloasingAlert('#dangerMessage', 2000);
				} else {
					autoCloasingAlert('#warningMessage', 2000);
				}
			}
		});
		$('#chatContent').val('');
	}
	var lastID = 0;
	function chatListFunction(type) {
		var fromID = '<%= id %>';
		var toID = '<%= toID %>';
		$.ajax({
			type : "POST",
			url : "./ChatListController",
			data : {
				fromID : encodeURIComponent(fromID),
				toID : encodeURIComponent(toID),
				listType : encodeURIComponent(type),
				contentType: 'application/x-www-form-urlencoded; charset=utf-8',
			},
			success : function(data) {
				if(data == "") return;
				var parsed = JSON.parse(data);
				var result = parsed.result;
				for (var i=0; i<result.length; i++) {
					if(result[i][0].value == fromID) {
						result[i][0].value = '나';
					}
					addChat(result[i][0].value, result[i][2].value, result[i][3].value);
				}
				lastID = Number(parsed.last);
			}
		});
	}
	function addChat(chatName, chatContent, chatTime) {
		$('#chatList').append('<div class="row">' +
				'<div class="col-lg-12">' +
				'<div class="media">' +
				'<a class="pull-left" href="#">' +
				'<img class="media-object img-circle" style="width:30px; height:30px;" src="img/myPage/profile.png" alt="">' +
				'</a>' +
				'<div class="media-body">' +
				'<h4 class="media-heading">' +
				chatName +
				'<span class="small pull-right">' +
				chatTime +
				'</span>' +
				'</h4>' +
				'<p>' +
				chatContent +
				'</p>' +
				'</div>' +
				'</div>' +
				'</div>' +
				'</div>' +
				'<hr>');
		$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
	}
	function getInfiniteChat() {
		setInterval(function() {
			chatListFunction(lastID);
		}, 3000);
	}
</script>
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
</head>
<body>
	<div id="wrap">
		<!-- header --> 
		<jsp:include page="../inc/header.jsp" />
		<!-- header--> 
		<article>
			<!-- side menu -->
			<jsp:include page="myPageSide.jsp" />
			<!-- side menu -->
			<div id="contentBoxFull">
				<div class="fullLayout">
					<div class="fullTit"><img src="./img/commons/dot.png"> 채팅</div>
					<div class="fullCon">

	<div class="container bootstrap snippet">
		<div class="row">
			<div class="col-xs-12">
				<div class="portlet portlet-default">
					<div class ="portlet-heading">
						<div class="portlet-title">
							<h4><i class="fa fa-circle text-green"></i>실시간 채팅창</h4>
						</div>
						<div class="clearfix"></div>
					</div>
					<div id="chat" class="panel-collapse collapse in">
						<div id="chatList" class="portlet-body chat-widget" style="overflow-y: auto; width:auto; height:500px;">
						</div>
						<div class="portlet-footer">
							<div class="row" style="heigth:90px;">
								<div class="form-group col-xs-10">
									<textarea style="height:80px; width:800px;" id="chatContent" class="form-control" placeholder="메세지를 입력하세요." maxlength="100"></textarea>
								</div>
								<div class="form-group col-xs-2">
									<button type="button" class="btn btn-default pull-rigth" onclick="submitFunction();">전송</button>
									<div class="claerfix"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	</div>
	</div>
	<div class="alert alert-success" id="successMessage" style="display: none;">
		<strong>메세지 전송에 성공했습니다.</strong>
	</div>
	<div class="alert alert-success" id="dangerMessage" style="display: none;">
		<strong>이름과 내용을 모두 입력해주세요.</strong>
	</div>
	<div class="alert alert-success" id="warningMessage" style="display: none;">
		<strong>데이터베이스 오류가 발생했습니다..</strong>
	</div>
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
	</div>
			<!-- fullCon -->
		</div><!-- fullLayout -->
	</div><!-- contentBoxFull -->
</div><!-- myPage -->
</article>
	<script>
		$('#messageModal').modal("show");
	</script>
	<%
		session.removeAttribute("messageContent");
		session.removeAttribute("messageType");
		}
	%>
	
	<!-- footer -->
	<%@include file="../inc/footer.jsp" %>
	<!-- footer -->
	</div><!-- wrap -->


<script type="text/javascript">
	$(document).ready(function() {
		getUnread();
		chatListFunction('0');
		getInfiniteChat();
		getInfiniteUnread();
	});
</script>
</body>
</html>