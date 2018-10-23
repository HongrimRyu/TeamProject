package tourpage.share.action;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.makePlan.db.PlanDAO;
import tourpage.makePlan.db.PlanDTO;
import tourpage.member.db.MemberDAO;
import tourpage.share.db.SharePlanBean;
import tourpage.share.db.SharePlanDAO;

public class SharePlanAddAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("SharePlanAddAction_execute()");
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./Login.me");
			forward.setRedirect(true);
			return forward;
		}
		
		PlanDAO pdao = new PlanDAO();

		System.out.println("content1 :" + request.getParameter("content1"));
		
		int plan_idx = Integer.parseInt(request.getParameter("plan_idx"));
		List list = pdao.getPlan(id, plan_idx);
		
		SharePlanBean spb = new SharePlanBean();
		
		MemberDAO mdao = new MemberDAO();
		String nick = mdao.getNick(id);
		
		SharePlanDAO spdao = new SharePlanDAO();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일"); // 포맷
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date startdate = null;
		String startdate2 = "";
		
		boolean check = false;
		for(int i=0; i < list.size(); i++){
			PlanDTO spb2 = (PlanDTO)list.get(i);
			spb.setPlan_name(spb2.getPlan_name());
			spb.setPlan_idx(spb2.getPlan_idx());
			
			String input = spb2.getPlan_startdate();

			startdate = dateFormat2.parse(spb2.getPlan_startdate()); // yyyy-m-dd형태는 바로 파싱이 안되서 한번더 포맷해준다.
			System.out.println("startdate : " + startdate);
			
			startdate2 = dateFormat.format(startdate);
			cal.setTime(startdate);
			cal.add(cal.DATE, list.size()-1);

			spb.setTravel_period(startdate2+" ~ " + dateFormat.format(cal.getTime()));
			spb.setPlan_days(spb2.getPlan_days());
			spb.setComment(request.getParameter("content"+(i+1)));
			spb.setPlan_detail(pdao.getPlanPlace(spb2.getPlan_detail()));
			spb.setImage(spdao.getImage(spb2.getPlan_detail()));
			spb.setId(id);
			spb.setNick(nick);
			spb.setWriter_ip(getClientIpAddr(request));
			
			check = spdao.insertShareplan(spb);
		}
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(check){
			out.println("<script>");
			out.println("alert('일정 공유 성공');");
			out.println("location.href='./SharePlan.sp';");
			out.println("</script>");
			out.close();
			return null;
		}
		out.println("<script>");
		out.println("alert('일정 공유 실패');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
		return null;
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
	
	
	
	
	
	
	
}//SharePlanAddAction
