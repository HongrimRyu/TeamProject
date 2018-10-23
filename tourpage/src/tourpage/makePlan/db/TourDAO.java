package tourpage.makePlan.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class TourDAO {

	// 디비 연결 
		// 드라이버로드/디비연결/sql쿼리 /stmt,pstmt/실행/결과값(rs)/자원해제 
		Connection con= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="";
		
		
		private Connection getCon() throws Exception{
			Context init = new InitialContext();
			DataSource ds =(DataSource)init.lookup("java:comp/env/jdbc/jsptest");
			con = ds.getConnection();
			return con;
		}
		public void CloseDB(){
			if(rs !=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt !=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con !=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		public List<TourDTO> getMarkerList(String south,String north,String east,String west,int limit){
			List<TourDTO> list = new ArrayList<>();
			try {
				con=getCon();
				sql="select * from placeinfo where lat between ? and ? and lng between ? and ? limit 0,?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, south);
				pstmt.setString(2, north);
				pstmt.setString(3, west);
				pstmt.setString(4, east);
				pstmt.setInt(5, limit);
				rs=pstmt.executeQuery();
				while(rs.next()){
					TourDTO tdto= new TourDTO();
					tdto.setProd(rs.getInt("prod"));
					tdto.setName(rs.getString("name"));
					tdto.setAddress(rs.getString("address"));
					tdto.setLat(rs.getString("lat"));
					tdto.setLng(rs.getString("lng"));
					tdto.setType(rs.getString("type"));
					tdto.setO_time(rs.getString("o_time"));
					tdto.setPhone(rs.getString("phone"));
					tdto.setInfo(rs.getString("info").replaceAll("\"", ""));
					String img=rs.getString("image")==null? "default.png" : rs.getString("image");
					tdto.setImage(img);
					tdto.setBest(rs.getInt("best"));
					list.add(tdto);
				}
				return list;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				CloseDB();
			}
	
			return null;
		}
//위치정보 생성
		
		//도시정보
		public List<CityDTO> getCityList(String south,String north,String east,String west,int limit){
			List<CityDTO> list = new ArrayList<>();
			try {
				con=getCon();
				sql="select * from cityinfo where lat between ? and ? and lng between ? and ? limit 0,?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, south);
				pstmt.setString(2, north);
				pstmt.setString(3, west);
				pstmt.setString(4, east);
				pstmt.setInt(5, limit);
				rs=pstmt.executeQuery();
				while(rs.next()){
					CityDTO cdto = new CityDTO();
					cdto.setC_idx(rs.getInt("c_idx"));
					cdto.setC_name(rs.getString("city_name"));
					cdto.setCountry(rs.getString("country"));
					cdto.setLat(rs.getString("lat"));
					cdto.setLng(rs.getString("lng"));
					cdto.setInfo(rs.getString("info"));
					list.add(cdto);
				}
				return list;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				CloseDB();
			}
	
			return null;
		}
		
		//끝
		public int insertPlaceinfo(TourDTO dto){
			int prod=0;
			try {
				con=getCon();
				sql="select max(prod) from placeinfo";
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				if(rs.next()){
					prod=rs.getInt(1)+1;
				}else prod=1;
				
				sql="insert into placeinfo (prod,name,address,lat,lng,type,info,image,author,a_time,phone,o_time,price,traffic,homepage)"
						+ " value (?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?)";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, prod);
				pstmt.setString(2, dto.getName());
				pstmt.setString(3, dto.getAddress());
				pstmt.setString(4, dto.getLat());
				pstmt.setString(5, dto.getLng());
				pstmt.setString(6, dto.getType());
				pstmt.setString(7, dto.getInfo());
				pstmt.setString(8, dto.getImage());
				pstmt.setString(9, dto.getAuthor());
				pstmt.setString(10, dto.getPhone());
				pstmt.setString(11, dto.getO_time());
				pstmt.setString(12, dto.getPrice());
				pstmt.setString(13, dto.getTraffic());
				pstmt.setString(14, dto.getHomepage());
				pstmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				CloseDB();
			}
			
			return 0;
		}
		

		
	
	
}
