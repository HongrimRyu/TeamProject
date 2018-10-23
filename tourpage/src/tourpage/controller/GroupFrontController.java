package tourpage.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.group.action.Action;
import tourpage.group.action.ActionForward;
import tourpage.group.action.CreateGroupAction;
import tourpage.group.action.GroupAddMemoAction;
import tourpage.group.action.GroupDeleteAction;
import tourpage.group.action.GroupDeleteMemoAction;
import tourpage.group.action.GroupDetailAction;
import tourpage.group.action.GroupDetailListAction;
import tourpage.group.action.GroupDetailPeopleAction;
import tourpage.group.action.GroupIdCheckAction;
import tourpage.group.action.GroupLoginAction;
import tourpage.group.action.GroupMapAction;
import tourpage.group.action.GroupNickAction;
import tourpage.group.action.GroupUpdateMemoAction;

public class GroupFrontController extends HttpServlet {
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String reqURI = request.getRequestURI();
		String ctxpath = request.getContextPath();
		String command = reqURI.substring(ctxpath.length()); 
		
		Action action = null;
		ActionForward forward = null;
		System.out.println("계산된 주소 : "+command);
		
		if(command.equals("/CreateGroupAction.gw")){
			// ActionForward (이동할주소,이동할방식) 
			action = new CreateGroupAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/CreateGroup.gw")){
			forward = new ActionForward();
			forward.setPath("./group/create.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/GroupIdCheckAction.gw")){
			action = new GroupIdCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.indexOf("/GroupLogin.gw")!=-1){
			if(command.equals("/GroupLogin.gw")){
				forward = new ActionForward();
				forward.setPath("./group/login.jsp");
				forward.setRedirect(false);
			}else{
				String id = command.substring(15);
				System.out.println(id);
				forward = new ActionForward();
				forward.setPath("/group/login.jsp");
				request.setAttribute("id", id);
				forward.setRedirect(false);
			}
		}else if(command.equals("/GroupLoginAction.gw")){
			action = new GroupLoginAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/GroupWare.gw")){
			forward = new ActionForward();
			forward.setPath("./group/groupware.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/GroupNick.gw")){
			forward = new ActionForward();
			forward.setPath("./group/nick.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/GroupNickAction.gw")){
			action = new GroupNickAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/GroupWareTest.gw")){
			forward = new ActionForward();
			forward.setPath("./group/makePlan.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/GroupWareTest.gw")){
			action = new GroupMapAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/GroupDeleteAction.gw")){
			action = new GroupDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/GroupDetailAction.gw")){
			action = new GroupDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/GroupDetailListAction.gw")){
			action = new GroupDetailListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/GroupDetailPeopleAction.gw")){
			action = new GroupDetailPeopleAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals(("/addMemoAction.gw"))){
			action = new GroupAddMemoAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/updateMemoAction.gw")){
			action = new GroupUpdateMemoAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/deleteMemoAction.gw")){
			action = new GroupDeleteMemoAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
