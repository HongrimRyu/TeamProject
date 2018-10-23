package tourpage.makePlan.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.makePlan.db.PlanDAO;
import tourpage.makePlan.action.ActionForward;

public class SavePlanAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String id="";
		if((String)session.getAttribute("id")==null){
			id="guest";
		}else{
			id=(String)session.getAttribute("id");
		}
		System.out.println(id);
		if(id.equals("guest")){
			// response 객체로 돌아가는 응답을 html 페이지로 처리,
			// 문자 UTF-8 인코딩(한글처리 하겠다)
			response.setContentType("text/html; charset=UTF-8");
		 	PrintWriter out = response.getWriter();
			out.println("{\"chk\": false}");
		  	out.close();
			// 비밀번호가 잘못된경우 페이지 이동정보 null처리
			return null;
		}
		String subject = request.getParameter("plan_title");
		String planInfo = request.getParameter("planInfo");
		String date = request.getParameter("plan_start");
		String[] planInfos=planInfo.split("/");
		if(planInfos.length==0){
			String[] temp={" "};
			planInfos=temp;
		}
		PlanDAO pdao = new PlanDAO();
	    pdao.savePlan(date,planInfos,subject,id);//int plan_idx, String date, String[] planInfos,String id
		
    	response.setContentType("text/html; charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	out.println("{\"chk\": true}");
      	out.close();
		return null;
	}
	

}
