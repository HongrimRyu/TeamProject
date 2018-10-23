package tourpage.group.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import tourpage.encryption.BCrypt;

public class GroupDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	private Connection getConnection() throws Exception {
		Context init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/jsptest");
		con = ds.getConnection();
		return con;
	}

	public void closeDB() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//그룹 생성
	public void insertGroup(String id,String pass){
		try {
			con = getConnection();
			sql = "insert into groupware(group_id,group_pass) values(?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, BCrypt.hashpw(pass, BCrypt.gensalt()));
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
	}
	
	//아이디 중복체크
	public boolean idCheck(String id){
		boolean chk=false;
		try {
			con = getConnection();
			sql = "select * from groupware where group_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				chk = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		
		return chk;
	}
	
	public boolean loginChk(String id,String pass){
		boolean chk=false;
		try{
			con = getConnection();
			sql = "select group_pass from groupware where group_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(BCrypt.checkpw(pass,rs.getString(1))){
					chk=true;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		
		
		return chk;
	}

	public GroupBean getGroupPlan(String group_id) {

		try {
			con=getConnection();
			sql="select * from groupware where group_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, group_id);
			rs=pstmt.executeQuery();
			if(rs.next()){
				GroupBean gb = new GroupBean();
				gb.setPlan_days(rs.getInt("plan_days"));
				gb.setPlan_startdate(rs.getString("plan_startdate"));
				gb.setPlan_detail(rs.getString("plan_detail"));
				gb.setPlan_idx(rs.getInt("plan_idx"));
				gb.setPlan_name(rs.getString("plan_name"));
				gb.setPlan_writer(rs.getString("plan_writer"));
				gb.setWriter_id(rs.getString("writer_id"));
				gb.setPlan_people(rs.getString("plan_people"));
				return gb;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB();
		}
		return null;
	}

	public void updateDetail(String groupid, String string) {
		try {
			con=getConnection();
			sql="update groupware set plan_detail=? where group_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, string);
			pstmt.setString(2, groupid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB();
		}
	}
	public void updatePoeple(String groupid, String people) {
		try {
			con=getConnection();
			sql="update groupware set plan_people=? where group_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, people);
			pstmt.setString(2, groupid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB();
		}
	}
	//그룹 생성
	public void insertGroup(String id,String pass,int plan_idx){
		try {
			ArrayList<GroupBean> arr = new ArrayList<>();
			con = getConnection();
			sql="select * from planlist where plan_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, plan_idx);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GroupBean pdto = new GroupBean();
				pdto.setPlan_idx(rs.getInt("plan_idx"));
				pdto.setPlan_name(rs.getString("plan_name"));
				pdto.setPlan_startdate(rs.getString("plan_startdate"));
				pdto.setPlan_days(rs.getInt("plan_days"));
				pdto.setPlan_detail(rs.getString("plan_detail"));
				pdto.setPlan_writer(rs.getString("plan_writer"));
				pdto.setPlan_time(rs.getTimestamp("plan_time"));
				arr.add(pdto);
			}
			GroupBean dto = new GroupBean();
			for (int i = 0; i < arr.size(); i++) {
				if(i==0){
					dto.setGroup_id(id);
					dto.setGroup_pass(BCrypt.hashpw(pass, BCrypt.gensalt()));
					dto.setPlan_idx(arr.get(i).getPlan_idx());
					dto.setPlan_name(arr.get(i).getPlan_name());
					dto.setPlan_startdate(arr.get(i).getPlan_startdate());
					dto.setPlan_days(arr.get(arr.size()-1).getPlan_days());
					dto.setPlan_detail(arr.get(i).getPlan_detail());
					dto.setPlan_writer(arr.get(i).getPlan_writer());
					dto.setPlan_time(arr.get(i).getPlan_time());
				}else{
					dto.setPlan_detail(dto.getPlan_detail()+"@"+arr.get(i).getPlan_detail());
				}
			}
			sql = "insert into groupware values(0,?,?,?,?,?,?,?,?,?,null,default)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getGroup_id());
			pstmt.setString(2, dto.getGroup_pass());
			pstmt.setInt(3, dto.getPlan_idx());
			pstmt.setString(4, dto.getPlan_name());
			pstmt.setString(5, dto.getPlan_startdate());
			pstmt.setInt(6, dto.getPlan_days());
			pstmt.setString(7, dto.getPlan_detail());
			pstmt.setString(8, dto.getPlan_writer());
			pstmt.setTimestamp(9, dto.getPlan_time());
			System.out.println("ddddddd");
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
	}
	
	public List<GroupBean> getMyGroupList(String plan_writer){
		
		List<GroupBean> arr = new ArrayList<>();
		
		try {
			con = getConnection();
			sql="select * from groupware where plan_writer=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, plan_writer);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GroupBean dto = new GroupBean();
				dto.setGroup_id(rs.getString("group_id"));
				dto.setPlan_name(rs.getString("plan_name"));
				dto.setPlan_startdate(rs.getString("plan_startdate"));
				dto.setPlan_days(rs.getInt("plan_days"));
				dto.setPlan_detail(rs.getString("plan_detail"));
				dto.setPlan_writer(rs.getString("plan_writer"));
				dto.setPlan_time(rs.getTimestamp("plan_time"));
				dto.setWriter_id(rs.getString("writer_id"));
				arr.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		
		return arr;
	}
	
public int deleteGroup(String group_id){
		int chk=0;
		try {
			con = getConnection();
			sql="delete from groupware where group_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, group_id);
			chk = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		
		return chk;
	}

public void updateDetail(String groupid, String plans, int i) {
	try {
		con=getConnection();
		sql="update groupware set plan_detail=?,plan_days=plan_days-1 where group_id=?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, plans);
		pstmt.setString(2, groupid);
		pstmt.executeUpdate();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		closeDB();
	}
	
}
	
	
	
	
	
}
