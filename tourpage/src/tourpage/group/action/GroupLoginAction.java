package tourpage.group.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.group.db.GroupDAO;

public class GroupLoginAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("GroupLoginAction execute()");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		
		GroupDAO gd = new GroupDAO();
		if(!gd.loginChk(id, pass)){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디 또는 비밀번호를 확인해주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		HttpSession session = request.getSession();
		session.setAttribute("group_id", id); //그룹웨어 로그인 확인 세션
		ActionForward forward = new ActionForward();
		forward.setPath("./GroupNick.gw");
		forward.setRedirect(true);
		
		return forward;
	}
}
