package tourpage.myPage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.member.db.MemberBean;
import tourpage.member.db.MemberDAO;

public class MyPageUpdate implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberUpdate_execute()");
		// 로그인 되어있는 상태(세션id값이 유지되는 상태) 
		HttpSession session = request.getSession();
		String id =(String)session.getAttribute("id");
		
		// 로그인상태가 아닐때 로그인페이지로 이동(Actionforward)
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./Login.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// MemberDAO 객체 생성 
		MemberDAO dao = new MemberDAO();
		// 아이디에 해당하는 회원 정보를 불러오기 
		// getMember(id) 메서드 호출 
		MemberBean m = dao.getMember(id);
		// request객체에 저장
		request.setAttribute("m", m);

		forward.setPath("./myPage/myPageUpdate.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
