package tourpage.controller;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.chat.db.ChatDAO;
// 채팅메세지 DB에 저장하기
public class ChatSubmitController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("ChatSubmitServlet_execute()");
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String chatContent = request.getParameter("chatContent");
		
		if(fromID == null || fromID.equals("") || toID == null || toID.equals("") || chatContent == null || chatContent.equals("")) {
			response.getWriter().write("0");
		} else {
			fromID = URLDecoder.decode(fromID, "UTF-8");
			toID = URLDecoder.decode(toID, "UTF-8");
			chatContent = URLDecoder.decode(chatContent, "UTF-8");
			response.getWriter().write(new ChatDAO().submit(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8"), URLDecoder.decode(chatContent, "UTF-8")) + "");	
		}
	}
}
