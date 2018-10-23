package tourpage.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.myPage.action.*;

public class MyPageFrontController extends HttpServlet {
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// http://localhost:8088/Model2/GoodsAdd.ag
		// /Model2/GoodsAdd.ag
		String requsetURI = req.getRequestURI();
		System.out.println("requsetURI : " + requsetURI);

		String contextPath = req.getContextPath();
		System.out.println("contextPath : " + contextPath);

		String command = requsetURI.substring(contextPath.length());
		System.out.println("command : " + command);

		ActionForward forward = null;
		Action action = null;
		
		if(command.equals("/MyPage.mp")){
			forward = new ActionForward();
			forward.setPath("./myPage/myPage.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/MyPageUpdate.mp")){
			action = new MyPageUpdate();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MyPageDelete.mp")){
			forward = new ActionForward();
			forward.setPath("./myPage/myPageDelete.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/MyPageDeleteAction.mp")){
			action = new MyPageDelete();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MyPageAction.mp")){
			action = new MyPageAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/MyPageChat.mp")){
			forward = new ActionForward();
			forward.setPath("./myPage/myPageChat.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/MyPageFind.mp")){
			forward = new ActionForward();
			forward.setPath("./myPage/myPageFind.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/MyPageBox.mp")){
			forward = new ActionForward();
			forward.setPath("./myPage/myPageBox.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/MyGroupware.mp")){
			forward = new ActionForward();
			forward.setPath("MyGroupwareAction.mp");
			forward.setRedirect(false);
		}else if(command.equals("/MyGroupwareAction.mp")){
			action = new MyGroupwareAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MyPlanDelAction.mp")){
			action = new MyPlanDelAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		// page
		if(forward !=null)
		if(forward.isRedirect()){
			resp.sendRedirect(forward.getPath());
		}else{
			RequestDispatcher dis = req.getRequestDispatcher(forward.getPath());
			dis.forward(req, resp);
		}
		
		
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doGet()");
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doPost()");
		doProcess(req, resp);
	}

}
