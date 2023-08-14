package com.naver.phone.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.naver.phone.dto.PhoneDto;

@Repository
public class PhoneDao {

	final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	
	public Connection open() {
		Connection con = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(JDBC_URL, "ora_user", "hong");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public ArrayList<PhoneDto> getAll(String userid) throws Exception { // userid 가져오기
		Connection con = open();
		
		ArrayList<PhoneDto> phoneList = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT mbid, mbnm, mbph, mbad, b.GROUPNAME	");
		sql.append("  FROM MEMTB a, GROUPTB b					");
		sql.append(" WHERE userid= ?							"); //? 로 처리하기
		sql.append(" AND a.MBGNO=b.MBGNO						");
		sql.append(" ORDER BY a.mbid							");
		

		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, userid);
		ResultSet rs = pstmt.executeQuery();
//														userid 넣어주기
		
		try(con; pstmt; rs) {
			while(rs.next()) {
				PhoneDto p = new PhoneDto();
				p.setMbid(rs.getInt("mbid"));
				p.setMbnm(rs.getString("mbnm"));
				p.setMbph(rs.getString("mbph"));
				p.setMbad(rs.getString("mbad"));
				p.setGroupName(rs.getString("groupName"));
				phoneList.add(p);
			}
			return phoneList;
		}
		
	}
	
	
	public void addMem(PhoneDto p, String userid) throws Exception { //가져와야하는거 userid
		Connection con = open();
		
		if(p.getGroupName().equals("가족")) {
			p.setMbgno("10");
		}else if(p.getGroupName().equals("친구")) {
			p.setMbgno("20");
		}else {
			p.setMbgno("30");
		}
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO MEMTB(USERID, mbid, mbnm, mbph, mbad, mbgno)	");
		sql.append("	VALUES(	?												"); // ?로 처리하기
		sql.append("	,(SELECT NVL(MAX(a.mbid),0)+1 FROM MEMTB a)				");
		sql.append("	,?														");
		sql.append("	,?														");
		sql.append("	,?														");
		sql.append("	,?														");
		sql.append(" 	)														");
		
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		
		try(con;pstmt) {
			
			pstmt.setString(1, userid); // userid 넣어야하는것
			pstmt.setString(2, p.getMbnm()); // userid 넣어야하는것
			pstmt.setString(3, p.getMbph());
			pstmt.setString(4, p.getMbad());
			pstmt.setString(5, p.getMbgno());
			pstmt.executeUpdate();
			
		}

	}
	
	
	public void updateMem(int mbid, PhoneDto p, String userid) throws Exception{ // userid 추가하기
		Connection con = open();

		if(p.getGroupName().equals("가족")) {
			p.setMbgno("10");
		}else if(p.getGroupName().equals("친구")) {
			p.setMbgno("20");
		}else {
			p.setMbgno("30");
		}
		
		
		System.out.println(p.getMbnm());
		System.out.println(userid);
		System.out.println(mbid);
		
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("	UPDATE memtb					");
		sql.append("	   SET mbnm =  ?				");
		sql.append("		 , mbph =  ?				");
		sql.append("		 , mbad =  ?				");
		sql.append("		 , mbgno = ?				");
		sql.append("		 , userid = ?				");
		sql.append("	 WHERE mbid =  ?				");
		
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		
		try(con;pstmt) {
			
			pstmt.setString(1, p.getMbnm());
			pstmt.setString(2, p.getMbph());
			pstmt.setString(3, p.getMbad());
			pstmt.setString(4, p.getMbgno());
			pstmt.setString(5, userid);
			pstmt.setInt(6, mbid);
			pstmt.executeUpdate();
		}
		 	 
	 
		
	}
	
	
	public void delMem(int mbid) throws Exception {
		Connection con = open();
		
		String sql = "DELETE FROM memtb WHERE mbid=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		try(con; pstmt) {
			pstmt.setInt(1, mbid);
			if(pstmt.executeUpdate() == 0) {
				throw new SQLException("DB에러");
			}
		}
		
	}
	
	public ArrayList<PhoneDto> searchMem(String input, String userid) throws Exception { //가져와야하는거 userid
		Connection con = open();
		ArrayList<PhoneDto> phoneList = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("	SELECT mbid, mbnm, mbph, mbad, b.GROUPNAME	");
		sql.append("	  FROM MEMTB a, GROUPTB b					");
		sql.append("	 WHERE a.userid=?							"); // ? 로 처리하기
		sql.append("	   AND a.MBGNO=b.MBGNO						");
		sql.append("	   AND a.mbnm LIKE '%' || ? || '%'			");
		sql.append("	 ORDER BY a.mbid							");
		
	 
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, userid);
		pstmt.setString(2, input); 
		ResultSet rs = pstmt.executeQuery();
		
		
		
		try(con; pstmt; rs) {
			while(rs.next()) {
				PhoneDto p = new PhoneDto();
				p.setMbid(rs.getInt("mbid"));
				p.setMbnm(rs.getString("mbnm"));
				p.setMbph(rs.getString("mbph"));
				p.setMbad(rs.getString("mbad"));

				if(rs.getString("groupName").equals("가족")) {
					p.setMbgno("10");
				}else if(rs.getString("groupName").equals("친구")) {
					p.setMbgno("20");
				}else {
					p.setMbgno("30");
				}
				p.setGroupName(rs.getString("groupName"));
				phoneList.add(p);
			}
			
		return phoneList; 
			
		}
		
	}
	
	
}
