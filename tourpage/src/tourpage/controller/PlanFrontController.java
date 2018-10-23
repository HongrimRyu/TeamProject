package tourpage.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tourpage.makePlan.action.*;

public class PlanFrontController extends HttpServlet{

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doProcess()호출");
		
		//가상주소 가져오기
		String requestURI = req.getRequestURI();
		System.out.println("requestURI : " + requestURI);
		
		String contextPath = req.getContextPath();
		System.out.println("contextPath : " + contextPath);
		
		String command = requestURI.substring(contextPath.length());
		System.out.println("command : " + command );
		
		ActionForward forward = null;
		Action action = null;
		
		if(command.equals("/tourpage.pl")){
			forward = new ActionForward();
			forward.setPath("./makePlan/makePlan.jsp");
			forward.setRedirect(false);	
		}else if(command.equals("/grab.pl")){
			forward = new ActionForward();
			forward.setPath("./tour/marker.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/tour_clip.pl")){
			forward = new ActionForward();
			forward.setPath("./tour/tour_clip.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/place_add.pl")){
			forward = new ActionForward();
			forward.setPath("./makePlan/place_add.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/store.pl")){//일정저장합니
			System.out.println("/store.pl ");
			action = new SavePlanAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/addPlaceAction.pl")){
			System.out.println("/addPlaceAction.me ");
			action = new addPlaceAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/getMarkerAction.pl")){//상황에 따른 마커 출력
			System.out.println("/getMarkerAction.pl");
			action = new GetMarkerAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(command.equals("/getCityAction.pl")){
			System.out.println("/getCityAction.pl");
			action = new getCityAction();
			try {
				forward =action.execute(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(command.equals("/modifyPlan.pl")){
			System.out.println("modifyPlan.pl");
			action = new ModifyPlan();
			try {
				forward=action.execute(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(command.equals("/modifyPlanAction.pl")){
			System.out.println("/modifyPlanAction.pl ");
			action = new ModifyPlanAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/likeAction.pl")){
			System.out.println("/likeAction.pl ");
			action = new LikeAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/likeCheck.pl")){
			System.out.println("/likeCheck.pl ");
			action = new LikeCheck();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/unlikeAction.pl")){
			System.out.println("/unlikeAction.pl ");
			action = new UnLikeAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(forward != null){
			if(forward.isRedirect()){
				resp.sendRedirect(forward.getPath());
			}else{
				RequestDispatcher dis = req.getRequestDispatcher(forward.getPath());
				dis.forward(req, resp);
			}
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
