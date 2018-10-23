package tourpage.member.action;

import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberAuthMailAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int random = (int)(Math.random()*1000000)+100000; 
		if(random>=1000000){
			random-=100000;
		}
		String authcode = Integer.toString(random);
		String reciever = request.getParameter("id");
		System.out.println(authcode);
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
		
		Address address = new InternetAddress(reciever); 
		message.setHeader("content-type", "text/html;charset=UTF-8");
		message.addRecipient(Message.RecipientType.TO, address);
		message.setSubject("[touround] 회원가입 인증메일입니다.");
		message.setContent(authcode, "text/html;charset=UTF-8");
		message.setSentDate(new java.util.Date());
		
		Transport.send(message);
		}catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
 
        out.println(authcode);
        
        out.close();
        
		return null;
	}
}
