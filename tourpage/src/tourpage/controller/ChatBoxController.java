package tourpage.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.chat.db.ChatDAO;
import tourpage.chat.db.ChatDTO;


public class ChatBoxController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");
		if (userID == null || userID.equals("")) {
			response.getWriter().write("");
		} else {
			try {
				userID = URLDecoder.decode(userID, "UTF-8");
				response.getWriter().write(getBox(userID));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getBox(String userID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getBox(userID);
		if(chatList.size() == 0) return "";
		for (int i=0; i<chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if (i != chatList.size() -1) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getChatID() + "\"}"); 
		return result.toString();
	}
}
