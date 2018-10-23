package tourpage.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.member.db.MemberDAO;

public class MemberLoginActionInMain implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      // MemberLoginAction
      System.out.println("MemberLoginAction_execute()");

      // id,pass 정보를 가져오기
      String id = request.getParameter("id");
      String pass = request.getParameter("pass");

      // MemberDAO 객체 생성
      // int check = idCheck(id,pass) 호출
      MemberDAO dao = new MemberDAO();
      boolean check = dao.loginCheck(id, pass);
      System.out.println("아이디 체크 결과 : " + check);
      
      if (check == false) {
         // response 객체로 돌아가는 응답을 html 페이지로 처리,
         // 문자 UTF-8 인코딩(한글처리 하겠다)
           response.setContentType("text/html; charset=UTF-8");
           PrintWriter out = response.getWriter();
           out.println("{\"chk\": false}");
             out.close();
         // 비밀번호가 잘못된경우 페이지 이동정보 null처리
         return null;
      }
      // check == 1
      // 로그인 처리 ,세션정보를 유지(id), ./Main.me페이지 이동
      HttpSession session = request.getSession();
      session.setAttribute("id", id);
      String nick = dao.getNick(id);
      String tag = "<li id='headerID'>"+nick+"님 로그인했습니다.</li>"
            + "<li><a href='./LogOut.me'>logout</a></li>";
       response.setContentType("text/html; charset=UTF-8");
       PrintWriter out = response.getWriter();
       out.println("{\"chk\": true,\"tag\":\""+tag+"\"}");
         out.close();
      return null;
   }
}