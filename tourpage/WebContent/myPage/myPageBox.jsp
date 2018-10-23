<%@page import="tourpage.member.db.MemberBean"%>
<%@page import="tourpage.myPage.db.MyPageDAO"%>
<%@page import="tourpage.myPage.db.MyPageBean"%>
<%@page import="tourpage.member.db.MemberDAO"%>
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
<link rel="shortcut icon" href="img/favicon15.ico">
<!-- js / jq -->
<script src="http://code.jquery.com/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="./js/members/join_180720.js"></script>
<%
	request.setCharacterEncoding("utf-8");
    String id = (String)session.getAttribute("id"); 
    String toID = null;
	if(request.getParameter("toID") != null){
		toID = (String) request.getParameter("toID");
	}
	
	MemberDAO mdao = new MemberDAO();
	String fromProfile = new MyPageDAO().getProfile(id);
	
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
	function chatBoxFunction() {
		var userID = '<%= id %>'
		$.ajax({
			type: "POST",
			url : "./ChatBoxController",
			data : {
				userID : encodeURIComponent(userID),
			},
			success : function(data) {
				if(data == "") return;
				$('#boxTable').html('');
				var parsed = JSON.parse(data);
				var result = parsed.result;
				for (var i=0; i<result.length; i++) {
					if(result[i][0].value == userID) {
						result[i][0].value = result[i][1].value;
					} else {
						result[i][1].value = result[i][0].value;
					}
					addBox(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value);
				}
			}
		});
	}
	function addBox(lastID, toID, chatContent, chatTime) {
		$('#boxTable').append('<tr onclick="location.href=\'MyPageChat.mp?toID=' + encodeURIComponent(toID) + '\'">' +
				'<td style="width: 150px;"><h5>' + lastID + '</h5></td>' +
				'<td>' +
				'<h5>' + chatContent + '</h5>' +
				'<div class="pull-right">' + chatTime + '</div>' +
				'</td>' +
				'</tr>');
	}
	function getInfiniteBox() {
		setInterval(function() {
			chatBoxFunction();
		}, 3000);
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
		<jsp:include page="../inc/header.jsp" />
		<!-- header--> 
		<article>
			<!-- side menu -->
			<jsp:include page="myPageSide.jsp" />
			<!-- side menu -->
			<div id="contentBoxFull">
				<div class="fullLayout">
					<div class="fullTit"><img src="./img/commons/dot.png"> 메세지함</div>
					<div class="fullCon">
						<div class="container">
							<table class="table" style="margin : 0 auto;">
								<thead>
									<tr>
										<th><h4>주소받은 메세지 목록</h4></th>
									</tr>
								</thead>
								<div style="overflow-v: auto; width:100%; max-height: 450px;" >
									<table class="table table-bordered table-hover" style="text-align: center; border:1px solid #dddddd; margin:0 auto;">
										<tbody id="boxTable"></tbody>
									</table>
								</div>
							</table>
						</div>
					</div><!-- fullCon -->
				</div><!-- fullLayout -->
			</div><!-- contentBoxFull -->
		</div><!-- myPage -->
		</article>
		
	<!-- footer -->
	<div class="fixedfooter"><jsp:include page="../inc/footer.jsp"/></div>
	<!-- footer -->
	<%
		if (id != null) {
	%>
		<script type="text/javascript">
			$(document).ready(function() {
				getUnread();
				getInfiniteUnread();
				chatBoxFunction();
				getInfiniteBox();
			});
		</script>
	<%
		}
	%>
</body>
</html>