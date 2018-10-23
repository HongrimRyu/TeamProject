package tourpage.member.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.member.encryption.BCrypt;
import tourpage.member.db.MemberBean;
import tourpage.member.db.MemberDAO;

public class MemberJoinAction implements Action {
	// 정보를 디비에 저장
	// 페이지 이동 (컨트롤러로 이동)
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("MemberJoinAction_execute()");

		// 회원가입 처리
		// 한글 처리
		request.setCharacterEncoding("UTF-8");

		// 자바빈 객체 생성
		// MemberBean 객체 생성
		MemberBean mb = new MemberBean();
		// 파라미터 정보를 mb 객체 저장
		mb.setId(request.getParameter("id") + "@" + request.getParameter("email"));
		mb.setPass(BCrypt.hashpw(request.getParameter("pass"), BCrypt.gensalt()));
		mb.setNick(request.getParameter("nick"));
		mb.setBirth(request.getParameter("birth1") + "-" + request.getParameter("birth2") + "-"
				+ request.getParameter("birth3"));
		mb.setGender(request.getParameter("gender"));
		mb.setPhone(request.getParameter("phone1") + "-" + request.getParameter("phone2") + "-"
				+ request.getParameter("phone3"));

		if((request.getParameter("phone1") + "-" + request.getParameter("phone2") + "-"
				+ request.getParameter("phone3")).equals("--")) {
			mb.setPhone(null);
		}else{
			mb.setPhone(request.getParameter("phone1") + "-" + request.getParameter("phone2") + "-"
					+ request.getParameter("phone3"));
		}

		mb.setAddr(request.getParameter("addr"));
		mb.setReg_ip(getClientIpAddr(request));
		mb.setProfile(request.getParameter("profile"));
		mb.setPlatform(request.getParameter("platform"));

		// 디비연결 처리 객체 생성
		MemberDAO mdao = new MemberDAO();
		mdao.insertMember(mb);

		System.out.println("MemberDAO 객체 생성후 insertMember(mb) 호출 ");

		// 페이지 이동 처리
		// 로그인 페이지로 이동

		ActionForward forward = new ActionForward();
		forward.setPath("./Login.me");
		forward.setRedirect(true); // sendRedirect 방식
		return forward;
	}

	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
