package roll_call_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Class{
	
	private int Id=-1;
	private String Name="";
	private String Description="";
	private int Credit=0;
	
	public Class(String name, String des , int cred) {
		Name=name;
		Description=des;
		Credit=cred;
	}
	
	public void addClass(Connection con) {
		try {
			//detect duplicate
			String sqltest="select ClassId from class where ClassName = ?";
			PreparedStatement stmt2=con.prepareStatement(sqltest);
			stmt2.setString(1, Name);
			ResultSet rs=stmt2.executeQuery();
			if(rs.next()) { 
				function.BugDetector(3);
				return;
			}
			
			String sql="insert into class (ClassName , ClassDescription , ClassCredit) "
				+ "values (?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,Name);
			stmt.setString(2,Description);
			stmt.setInt(3,Credit);
			stmt.executeUpdate();
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getId(Connection con){
		
		if(Id!=-1) {
			return Id;
		}
		
		try {
			//do select
			String sql="select ClassId from class where  ClassName = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, Name);
			ResultSet rs=stmt.executeQuery();
			
			//check if the number of selected data = 1
			int size=0;
			if(rs != null) {
				rs.last();
				size = rs.getRow();
			}
			if(size != 1) {
				return -1;
			}
			Id=rs.getInt(1);
			return Id;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int getIdbyName(String name , Connection con) {
		try {
			//do select
			String sql="select ClassId from class where  ClassName = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, name);
			ResultSet rs=stmt.executeQuery();
			
			//check if the number of selected data = 1
			int size=0;
			if(rs != null) {
				rs.last();
				size = rs.getRow();
			}
			if(size != 1) {
				return -1;
			}
			return rs.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static boolean isValidId(int classid , Connection con) {
		try {
			String sql = "select ClassId from class where ClassId = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, classid);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
}
