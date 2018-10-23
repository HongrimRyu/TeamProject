package tourpage.myPage.action;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.myPage.db.MyPageDAO;

public class MyPageDelete implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MyPageDelete_execute()");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String pass = (String)session.getAttribute("pass");
	
		ActionForward forward = new ActionForward();
		if(id==null){
			forward.setPath("./Login.me");
			forward.setRedirect(true);
			return forward;
		}		
	
		MyPageDAO mpdao = new MyPageDAO();
		int check=0;
		if(check==0){
			response.setContentType("text/html; charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	
        	out.println("<script>");
        	out.println("alert('비밀번호 틀림');");
        	out.println("history.back()");
        	out.println("</script>");      	
        	out.close();
        	return null;
		}else if(check==-1){
			response.setContentType("text/html; charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	
        	out.println("<script>");
        	out.println("alert('아이디 없음');");
        	out.println("history.back()");
        	out.println("</script>");      	
        	out.close();
        	return null;
		}else {
			 // 회원정보 삭제완료 
	        // 세션정보 초기화
	        session.invalidate();
	        // ./Main.me 페이지 이동
	        response.setContentType("text/html; charset=UTF-8");
	    	PrintWriter out = response.getWriter();
	    	out.println("<script>");
	    	out.println("alert('회원삭제 완료');");
	    	out.println("location.href='./Login.me'");
	    	out.println("</script>");      	
	    	out.close();
	    	return null;
		}
	}

}
