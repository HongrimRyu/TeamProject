package tourpage.group.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.group.db.GroupDAO;

public class GroupNickAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String nick = request.getParameter("groupnick");
		HttpSession session = request.getSession();
		session.setAttribute("nick", nick);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./GroupWare.gw");
		forward.setRedirect(true);
		return forward;
	}
}
