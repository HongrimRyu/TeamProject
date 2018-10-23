package tourpage.myPage.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.member.action.Action;
import tourpage.member.action.ActionForward;
import tourpage.member.db.MemberDAO;

public class VariableLoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("VariableLoginAction excecute()");
		String id = (String)request.getParameter("id");
		String pass= (String)request.getParameter("pass");
		MemberDAO dao = new MemberDAO();
		boolean check = dao.loginCheck(id, pass);
		if(check){
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
	    	response.setContentType("text/html; charset=UTF-8");
	    	PrintWriter out = response.getWriter();
	    	out.println("{\"chk\": true}");
	      	out.close();
		}else{
	    	response.setContentType("text/html; charset=UTF-8");
	    	PrintWriter out = response.getWriter();
	    	out.println("{\"chk\": false}");
	      	out.close();
		}
		return null;
	}

}
