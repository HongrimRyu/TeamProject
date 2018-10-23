package tourpage.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.member.db.MemberDAO;
// 친구찾기 서블릿
public class UserRegisterCheckServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("UserRegisterCheckServlet_execute()");
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		if(userID == null || userID.equals("")) response.getWriter().write("-1");
		response.getWriter().write(new MemberDAO().FriendCheck(userID) + "");
	}
}