<%@page import="tourpage.member.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
   <script src="http://54.180.2.191:8080/tourpage/js/commons/commons_js_.js"></script>
   <div id="smallNav">
   <div class="sn">
   <ul id="headerul">
   <%
      String ssid="";
     String headernick;   
     MemberDAO dao = new MemberDAO();
      if(session.getAttribute("id")==null){
         headernick = "guest";
         ssid="guest";
      }else{
         ssid = (String)session.getAttribute("id");
         headernick = dao.getNick(ssid);
      }
      %>
      <li><%=headernick %>님 로그인했습니다.</li>
      <%
      if(session.getAttribute("id") != null){
   %>
         <li><a href="./LogOut.me">logout</a></li>
   <%
      }else{ 
   %>
         <li><a href="javascript:;" onclick="loginOpen();">login</a></li>
         <li><a href="./Join.me">join</a></li>
   <%
      } 
   %>
   </ul>
   </div>
</div>

<header>
<div id="headerLayout">
   <div class="logo">
      <a href="./Login.me"><img src="http://54.180.2.191:8080/tourpage/img/commons/logo_bk.svg" alt="tourpage"></a>
   </div>
   <nav>
      <ul>
         <li><a href="./tourpage.pl">일정만들기</a></li>
         <li><a href="./SharePlan.sp">일정공유</a></li>
         <li><a href="./Shop.sh">쇼핑몰</a></li>
         <li><a href="./GroupLogin.gw">그룹웨어</a></li>
         <%
         if(session.getAttribute("id")!=null){
         %>
         <li><a href="./MyPage.mp">마이페이지</a></li>
         <%} %>
      </ul>
   </nav>
</div>
</header>
 <div id="login_cover"></div>
 <div class="login">
    <div class="txt">login</div>
   <div class="lgForm">
      <form action="./LoginActionInMain.me" method="post" name="lgFr"> <br> 
         <input type="text" name="id" id="id" class="lgInp" onkeyup="enterKey('.lgSbm');"/><br> 
         <input type="password" name="pass" id="pass" class="lgInp" onkeyup="enterKey('.lgSbm');"/><br> 
         <input type="button" value="login" class="lgSbm" /><br>
      </form>
   </div>
</div>
<!-- login popup -->