package tourpage.makePlan.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.makePlan.db.PlanDAO;

public class ModifyPlanAction implements Action {

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
		int plan_idx = Integer.parseInt(request.getParameter("plan_idx"));
		String subject = request.getParameter("plan_title");
		String planInfo = request.getParameter("planInfo");
		String date = request.getParameter("plan_start");
		String[] planInfos=planInfo.split("/");
		if(planInfos.length==0){
			String[] temp={" "};
			planInfos=temp;
		}
		PlanDAO pdao = new PlanDAO();
		System.out.println(plan_idx);
	    pdao.modifyPlan(plan_idx,date,planInfos,subject,id);//
		
    	response.setContentType("text/html; charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	out.println("{\"chk\": true}");
      	out.close();
		return null;
	}

}
