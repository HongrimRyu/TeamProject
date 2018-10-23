package tourpage.member.action;

import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.member.db.MemberDAO;

public class MemberSendPassAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberSendPassAction.me");
		String id = request.getParameter("id");
		System.out.println("id: "+id);
		String pass = "";
		MemberDAO dao = new MemberDAO();
		Random rd = new Random();
		for(int i=0;i<3;i++){
			pass += ((char)(int)(rd.nextInt(58)+65))+Integer.toString(rd.nextInt(100));
		}
		try{
			
			Properties properties = System.getProperties();
			properties.put("mail.smtp.starttls.enable", "true"); // gmail은 무조건 true 고정
			// smtp 서버 주소
			properties.put("mail.smtp.host", "smtp.gmail.com"); // smtp 서버 주소
			// smtp 메일 인증, gmail은 항상 "true" 고정
			properties.put("mail.smtp.auth", "true"); // gmail은 무조건 true 고정
			// gmail 포트 번포
			properties.put("mail.smtp.port", "587"); // gmail 포트
			
			Authenticator auth = new GoogleAuthentication();
			Session s = Session.getDefaultInstance(properties,auth);
			
			Message message = new MimeMessage(s);
			
			Address address = new InternetAddress(id); 
			message.setHeader("content-type", "text/html;charset=UTF-8");
			message.addRecipient(Message.RecipientType.TO, address);
			message.setSubject("[touround] 임시 비밀번호입니다.");
			message.setContent("임시 비밀번호: "+pass+"<br> 로그인 후 바로 변경해주세요.", "text/html;charset=UTF-8");
			message.setSentDate(new java.util.Date());
			
			Transport.send(message);
			}catch (Exception e) {
				e.printStackTrace();
			}
		dao.updatePass(id, pass);
		
		
		return null;
	}
}
