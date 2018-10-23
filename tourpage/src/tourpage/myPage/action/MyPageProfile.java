package tourpage.myPage.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import tourpage.member.db.MemberBean;
import tourpage.member.encryption.BCrypt;
import tourpage.myPage.db.MyPageDAO;

public class MyPageProfile implements Action{

	@SuppressWarnings("deprecation")
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MyPageProfile_execute()");
		ActionForward forward = new ActionForward();
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		MultipartRequest multi = null;
		int fileMaxSize = 10 * 1024 * 1024;
		String savePath = request.getRealPath("/upload").replaceAll("\\\\", "/");
		try {
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("오류메세지");
			System.out.println("파일 크기는 10MB를 넘을 수 없습니다.");
			response.sendRedirect("./MyPage.mp");
			return null;
		}
		String id = multi.getParameter("id");
		HttpSession session = request.getSession();
		if(!id.equals((String) session.getAttribute("id"))) {
			request.getSession().setAttribute("massageType", "오류메세지");
			request.getSession().setAttribute("messageContent", "파일 크기는 10MB를 넘을 수 없습니다.");
			response.sendRedirect("./login.me");
			return null;
		}
		String fileName = "";
		File file = multi.getFile("profile");
		if(file != null) {
			String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			if(ext.equals("jpg") || ext.equals("png") || ext.equals("gif")) {
				String prev = new MyPageDAO().getMember(id).getProfile();
				File prevFile = new File(savePath + "/" + prev);
				if(prevFile.exists()) {
					prevFile.delete();
				}
				fileName = file.getName();
			} else {
				if (file.exists()) {
					file.delete();
				}
				System.out.println("오류메세지");
				System.out.println("이미지 파일만 업로드 가능합니다.");
				forward.setPath("./index.jsp");
				forward.setRedirect(false);
			}
		}
		
		// new MyPageDAO().Profile(id, fileName);
		MemberBean mb = new MemberBean();
		
		mb.setId(id);
		mb.setPass(BCrypt.hashpw(multi.getParameter("pass"), BCrypt.gensalt()));
		mb.setNick(multi.getParameter("nick"));
		mb.setBirth(multi.getParameter("birth1") + "-" + multi.getParameter("birth2") + "-"
				+ multi.getParameter("birth3"));
		mb.setGender(multi.getParameter("gender"));
		mb.setPhone(multi.getParameter("phone1") + "-" + multi.getParameter("phone2") + "-"
				+ multi.getParameter("phone3"));

		if((multi.getParameter("phone1") + "-" + multi.getParameter("phone2") + "-"
				+ multi.getParameter("phone3")).equals("--")) {
			mb.setPhone(null);
		}else{
			mb.setPhone(multi.getParameter("phone1") + "-" + multi.getParameter("phone2") + "-"
					+ multi.getParameter("phone3"));
		}
		mb.setProfile(fileName);
		
		MyPageDAO mpao = new MyPageDAO();
		mpao.Profile(mb);
		
		System.out.println("성공적으로 프로필이 변경되었습니다.");
		forward.setPath("./myPage/myPage.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
