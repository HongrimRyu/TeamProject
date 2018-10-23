package tourpage.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.shop.action.*;

public class ShopFrontController extends HttpServlet {
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String reqURI = request.getRequestURI();
		String ctxpath = request.getContextPath();
		String command = reqURI.substring(ctxpath.length()); 
		
		Action action = null;
		ActionForward forward = null;
		System.out.println("계산된 주소 : "+command);
		if(command.equals("/Shop.sh")){
			// ActionForward (이동할주소,이동할방식) 
			forward = new ActionForward();
			// 이동할 주소
			forward.setPath("./shop/shop.jsp");
			// 이동할 방식 
			forward.setRedirect(false);
		}else if(command.equals("/searchAction.sh")){
			action = new searchAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(command.equals("/searchResult.sh")){
			forward = new ActionForward();
			forward.setPath("./shop/searchResult.jsp");
			forward.setRedirect(false);
		}
		
		// 페이지 이동 
		if(forward != null){ //이동할 정보가 있으면
			if(forward.isRedirect()){
				response.sendRedirect(forward.getPath());
			}			
			else{
				RequestDispatcher dis =	request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);			
			}
		}
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req,resp);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
}
