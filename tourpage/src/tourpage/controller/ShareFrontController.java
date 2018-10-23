package tourpage.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tourpage.share.action.*;

public class ShareFrontController extends HttpServlet {
	static String json = null;
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String requsetURI = req.getRequestURI();
		System.out.println("requsetURI : " + requsetURI);

		String contextPath = req.getContextPath();
		System.out.println("contextPath : " + contextPath);

		String command = requsetURI.substring(contextPath.length());
		System.out.println("command : " + command);

		ActionForward forward = null;
		Action action = null;

		if (command.equals("/SharePlan.sp")) {
			forward = new ActionForward();
			forward.setPath("./share/share.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/SharePlanAdd.sp")){ //
			action = new SharePlanAdd();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/SharePlanAddAction.sp")){ //
			action = new SharePlanAddAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ShareContent.sp")){ // shareContent.jsp이동
			forward = new ActionForward();
			forward.setPath("./share/shareContent.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/ShareForm.sp")){ // shareForm.jsp로 이동
			forward = new ActionForward();
			forward.setPath("./share/shareForm.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/SharePlanDeleteAction.sp")){ // shareplan디비에 저장된 플랜삭제
			action = new SharePlanDeleteAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/SharePlanUpdate.sp")){ // shareUpdate.jsp로 디비값 가지고 이동
			action = new SharePlanUpdate();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/SharePlanUpdateAction.sp")){
			action = new SharePlanUpdateAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/SharePlanBoard.sp")){ //공유된 일정들 받아옴.
			action = new SharePlanBoardAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/SharePlanContent.sp")){ // 선택된 공유게시글로 넘어감
			action = new SharePlanContentAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ShareCommentAddAction.sp")){
			action = new ShareCommentAddAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ShareCommentReAddAction.sp")){
			action = new ShareCommentReAddAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ShareCommentDeleteAction.sp")){
			action = new ShareCommentDeleteAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ShareCommentUpdateAction.sp")){
			action = new ShareCommentUpdateAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/SharePlanLikeAction.sp")){
			action = new SharePlanLikeAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		if (forward != null)
			if (forward.isRedirect()) {
				resp.sendRedirect(forward.getPath());
			} else {
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
