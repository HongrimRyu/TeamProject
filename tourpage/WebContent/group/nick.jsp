<%@page import="tourpage.member.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<title>Insert title here</title>
		
		<link rel="stylesheet" type="text/css" href="http://54.180.2.191:8080/tourpage/css/commons/defaultCSS.css">
		<link rel="stylesheet" type="text/css" href="http://54.180.2.191:8080/tourpage/css/groupware/groupware.css">
		<link href="https://fonts.googleapis.com/css?family=Jua|Nanum+Gothic"
			rel="stylesheet">
		<link rel="shortcut icon" href="http://54.180.2.191:8080/tourpage/img/commons/favicon15.ico">
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script src="http://54.180.2.191:8080/tourpage/js/commons/jquery-3.3.1.js"></script>
		<script src="http://54.180.2.191:8080/tourpage/js/commons/commons_js_.js"></script>
		
		<%
		if(session.getAttribute("group_id")==null){
		%>
			<script type="text/javascript">
				alert("잘못된 접근입니다.");
				location.href="./GroupLogin.gw";
			</script>
		<%
		}
		%>
		<script type="text/javascript">
		function groupnickChk(){
			if($('#groupnick').val().length<2){
				$('#groupnick').css("background-color","#EF5350");
				$('#groupnickChk').html('닉네임 길이가 짧습니다.');
				return false;
			}
			$('#groupnick').css("background-color","#ffffff");
			$('#groupnickChk').html('');
			return true;
		}
		</script>
	</head>
	<body>
		
	<div id="wrap">
	<%@include file="../inc/header.jsp"%>
		
		<!-- content -->
		<article> 
		<div id="gWare">
			<div id="gwLogin">
				<p>
					<img alt="tourpage groupware" src="http://54.180.2.191:8080/tourpage/img/logo_gw_.svg" width="200px">
					<br>Nick
				</p>
				<%
				if(ssid==null||(ssid.equals("guest"))){
					%>
					<form action="./GroupNickAction.gw" method="post" onsubmit="return groupnickChk();">
						<label>닉네임</label><br>
						<input type="text" name="groupnick" id="groupnick" class="gwInp">
						<label id="groupnickChk"></label><br>
						<input type="submit" value="확인" class="gwSbm">
					</form>
					<%
			
				}else{
					
					String nick = dao.getNick((String)session.getAttribute("id"));
					session.setAttribute("nick", nick);
					response.sendRedirect("./GroupWare.gw");
				}
				%>
			</div>
		</div>
		</article>
		<!-- footer -->
		<jsp:include page="../inc/footer.jsp" />
		<!-- footer -->
	</div>

	</body>
</html>