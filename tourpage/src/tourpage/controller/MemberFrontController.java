package tourpage.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import tourpage.member.action.*;
import tourpage.myPage.action.VariableLoginAction;



public class MemberFrontController extends HttpServlet {

	// 메서드 생성 - doProcess(request,response)
	// -> get/post 방식상관하지 않고 무조건 실행
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doProcess()");
		// 가상주소 가져오기
		// http://localhost:8088/Model2/Main.me
		// http://localhost:8088/Model2/MemberJoin.me
		// http://localhost:8088/Model2/MemberJoinAction.me

		// [/Model2/MemberJoinAction.me]
		// ->/프로젝트명/가상주소
		String requestURI = request.getRequestURI();
		System.out.println("requestURI : " + requestURI);
		// [/Model2]
		// -> /프로젝트명
		String contextPath = request.getContextPath();
		System.out.println("contextPath :" + contextPath);
		// [/MemberJoinAction.me]

		System.out.println("프로젝트의 이름 길이 : " + contextPath.length());

		String command = requestURI.substring(contextPath.length());
		System.out.println("계산된 가상 주소 : " + command);

		// 계산된 가상주소와 비교값(해당주소) 같으면
		// /MemberJoin.me // /MemberJoin.me
		// 해당페이지 이동
		// /member/insertForm.jsp

		// ActionForward 객체 생성
		ActionForward forward = null;
		Action action = null;

		if (command.equals("/Join.me")) {
			System.out.println("/Join.me");
			// 이동방식
			// 1. response 객체 사용
			// response.sendRedirect("./member/insertForm.jsp");
			// http://localhost:8088/Model2/member/insertForm.jsp

			// 2. forward 사용
			// A 정보를 가지고 => B 이동 , 주소줄 A페이지, 내용(실행화면) B페이지
			// 주소 : http://localhost:8088/Model2/MemberJoin.me
			// 내용 : /member/insertForm.jsp

			// RequestDispatcher dis =
			// request.getRequestDispatcher("./member/insertForm.jsp");
			// dis.forward(request, response);
			
			// ActionForward (이동할주소,이동할방식) 
			forward = new ActionForward();
			// 이동할 주소
			forward.setPath("./members/join.jsp");
			// 이동할 방식 
			forward.setRedirect(false);		

		}else if(command.equals("/Login.me")){
			// /MemberJoinAction.me
			System.out.println("/Login.me ");
			
			// ActionForward (이동할주소,이동할방식) 
			forward = new ActionForward();
			// 이동할 주소
			forward.setPath("./members/login.jsp");
			// 이동할 방식 
			forward.setRedirect(false);
			
		}else if(command.equals("/JoinAction.me")){
			// /MemberJoinAction.me
			System.out.println("/JoinAction.me ");
			
			// 회원가입 처리 작업 
			// 처리하는 틀 생성 - interface 생성 
			// net.member.action - Action파일(interface)
			// MemberJoinAction 객체 생성
			action = new MemberJoinAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MemberIdCheckAction.me")){
	         System.out.println("/MemberIdCheckAction.me");
	         System.out.println("id: "+request.getParameter("id"));
	         action = new MemberIdCheckAction();
	         try {
	            forward = action.execute(request, response);
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	    }else if(command.equals("/MemberNickCheckAction.me")){
	         System.out.println("/MemberNickCheckAction.me");
	         System.out.println("nick: "+request.getParameter("nick"));
	         action = new MemberNickCheckAction();
	         try {
	            forward = action.execute(request, response);
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	    }else if(command.equals("/MemberPhoneCheckAction.me")){
	         System.out.println("/MemberPhoneCheckAction.me");
	         System.out.println("phone: "+request.getParameter("phone"));
	         action = new MemberPhoneCheckAction();
	         try {
	            forward = action.execute(request, response);
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	    }else if(command.equals("/MemberAuthMailAction.me")){
	         System.out.println("/MemberAuthMailAction.me");
	         action = new MemberAuthMailAction();
	         try {
	            forward = action.execute(request, response);
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	    }else if(command.equals("/LoginAction.me")){
			//./MemberLoginAction.me
			// 로그인 처리
			// Action 인터페이스 상속(구현)하는 객체 생성
			// MemberLoginAction.java(객체)
	    	System.out.println("/MemberLoginAction.me");
			action = new MemberLoginAction(); 
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Main.me")){
			System.out.println("/Main.me");
			forward = new ActionForward();
			forward.setPath("./main.jsp");
			forward.setRedirect(false);		
		}else if(command.equals("/MemberSendPassAction.me")){
			System.out.println("/MemberSendPassAction.me");
			action = new MemberSendPassAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		/*타 플랫폼 간편로그인부분*/	
		}else if(command.equals("/MemberJoinCheckAction.me")){// login 관련
			System.out.println("/MemberJoinCheckAction.me");
			action = new MemberJoinCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }else if(command.equals("/SNSJoin.me")){ // login 관련
	    	System.out.println("/SNSJoin.me");
	    	
			forward = new ActionForward();
			forward.setPath("./members/SNSJoin.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/LoginActionInMain.me")){
			//./MemberLoginAction.me
			// 로그인 처리
			// Action 인터페이스 상속(구현)하는 객체 생성
			// MemberLoginAction.java(객체)
	    	System.out.println("/LoginActionInMain.me");
			action = new MemberLoginActionInMain(); 
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/VariableLoginAction.me")){
	    	System.out.println("/VariableLoginAction.me");
			action = new VariableLoginAction(); 
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/LogOut.me")){
			System.out.println("/LogOut.me");
			action = new LogOut(); 
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// 페이지 이동 
		if(forward != null){ //이동할 정보가 있으면
			
			// 이동방식 
			// 1. sendRedirect
			if(forward.isRedirect()){
				response.sendRedirect(forward.getPath());
			}			
			// 2. forward
			else{
				RequestDispatcher dis =
						request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);			
			}
			
			
		}
		
		

	} // doprocess()

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
