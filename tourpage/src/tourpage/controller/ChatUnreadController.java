package tourpage.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.chat.db.ChatDAO;
import tourpage.chat.db.ChatDTO;


public class ChatUnreadController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");
		if (userID == null || userID.equals("")) {
			response.getWriter().write("0");
		} else {
			userID = URLDecoder.decode(userID, "UTF-8");
			response.getWriter().write(new ChatDAO().getAllUnreadChat(userID) + "");
		}
	}
}
