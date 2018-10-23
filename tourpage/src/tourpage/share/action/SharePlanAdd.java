package tourpage.share.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import tourpage.makePlan.db.PlanDAO;
import tourpage.makePlan.db.PlanDTO;
import tourpage.share.db.SharePlanDAO;

public class SharePlanAdd implements Action{

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      System.out.println("SharePlanAdd_execute()");
      request.setCharacterEncoding("utf-8");
      HttpSession session = request.getSession();
      String id = (String)session.getAttribute("id");
      if(id==null){
         ActionForward forward = new ActionForward();
         forward.setPath("./Login.me");
         forward.setRedirect(true);
         return forward;
      }
      int plan_idx = Integer.parseInt(request.getParameter("plan_idx"));
      
      SharePlanDAO spdao = new SharePlanDAO();
      boolean check_dulication = spdao.getduplicationcheck(plan_idx); // 이미 공유된 일정인지 확인
      //System.out.println("du : " + check_dulication);
      
      
      if(check_dulication){
      return null;
      }
      
      PlanDAO pdao = new PlanDAO();
      List<PlanDTO> list = pdao.getPlan(id, plan_idx);
      for(int i=0; i<list.size(); i++){
         PlanDTO pdto = list.get(i);
         String plan_detail = pdto.getPlan_detail();
         String place = pdao.getPlanPlace(plan_detail);
         pdto.setPlan_detail(place);
      }
      Gson gson = new Gson();
      String json = gson.toJson(list);
      response.setCharacterEncoding("utf8");
      response.getWriter().write(json);
      return null;
   }
   
}
