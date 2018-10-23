package tourpage.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.member.db.MemberDAO;

public class MemberPhoneCheckAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String phone = request.getParameter("phone");
		MemberDAO dao = new MemberDAO();
		
		boolean result = dao.phoneCheck(phone);
		
		response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
 
        if(result)    out.println("0"); // 연락처 중복
        else        out.println("1");
        
        out.close();
		return null;
	}
}
