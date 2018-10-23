package tourpage.member.action;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.member.db.MemberBean;
import tourpage.member.db.MemberDAO;

public class MemberJoinCheckAction implements Action{
/*
 * 일반 로그인은 ./Join.me -> MemberJoinAction을 통해서 회원가입 페이지로 이동하고
 * SNS를 통해 로그인 한 경우 ./MemberJoinCheckAction.me로 이동해서
 * 1. 가입이 이미 된 경우 메인으로 옮기고
 * 2. 안 되있는 경우 SNSJoin.jsp로 이동한다 
 * 3. 아이디는 일치하는데 가입한 아이디의 플랫폼이랑 현재 로그인해서 들어온 플랫폼이랑 다를 경우
 * 경고창을 띄우고 main.jsp로 이동시킨다
 */
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberJoinCheckAction_execute()");
		request.setCharacterEncoding("utf-8");
		String platform = request.getParameter("platform");
		String email = request.getParameter("email");
		String nick = request.getParameter("nickname");
		String group = "";
		System.out.println("platform : "+platform);

		System.out.println("블러온값 : " + platform+","+email+","+nick+","+group);
		MemberDAO mdao = new MemberDAO();
		String check = mdao.SNSidCheck(email);
		System.out.println("check : " + check);
		/*반환값 확인위해 출력*/
		
		if(check.equals("na")){
			group = "네이버 플랫폼";
		}else if(check.equals("fb")){
			group = "페이스북 플랫폼";
		}else if(check.equals("go")){
			group = "구글 플랫폼";
		}else if(check.equals("me")){
			group = "자사 플랫폼";
		}
		System.out.println("group : " + group);
		/*<<<>>>>>*/
		ActionForward forward = new ActionForward();
		
		if(check.equals(platform)){//그룹과 디비 그룹이 일치하는 경우
			System.out.println("그룹과 디비 그룹이 일치하는 경우");

			// 세션설정
			HttpSession session = request.getSession();
			session.setAttribute("id", email);
			
			forward.setPath("./tourpage.pl");   
			forward.setRedirect(true);
			return forward;
			
		}else if(check.equals("")){//아이디가 없는 경우
			System.out.println("아이디가 없는 경우");
			
			HttpSession session = request.getSession();
			session.setAttribute("id", email);
			session.setAttribute("nick", nick);
			session.setAttribute("platform", platform);
			
			forward.setPath("./SNSJoin.me");
			forward.setRedirect(true); // true는 .을 안붙이면 못들어간다.
			return forward;
		}else{//아이디는 있으나, 디비 그룹과, 가져온 그룹이 일치하지 않는 경우
			System.out.println("아이디는 있으나, 디비 그룹과, 가져온 그룹이 일치하지 않는 경우");
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+group+"으로 이미 가입되어있습니다.')");
			out.println("location.href='./Login.me'");
			out.println("</script>");
			return null;
		}
	}//execute
}
