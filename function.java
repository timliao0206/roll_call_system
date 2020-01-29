package roll_call_system;

import java.sql.*;
import java.util.*;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.*;
import java.text.DateFormat;
import java.util.Date;

public class function {
	
	//catch bug
	public static void BugDetector(int situation) {
		switch(situation) {
		case 1:
			System.out.println("duplicated StudentUserName in table \"student\"\n");
			break;
		case 2:
			System.out.println("duplicated TeacherUserName in table \"teacher\"\n");
			break;
		case 3:
			System.out.println("duplicated ClassName in table \"class\"");
		case 6:
			System.out.println("invalid class id");
			break;
		}
		return;
	}
	
	//add a class time
	public static void addClassTime(int classid , String time , Connection con) {
		try {
			if(isValidDateTime(time)) {//check if the time format is valid
				String sql="insert into classtime (ClassTimeRefClassId , Time) "
					+ "values ((select ClassId from class where ClassId= ? ) , ? ) ON DUPLICATE KEY UPDATE Time = ?";
				PreparedStatement stmt =con.prepareStatement(sql);
				stmt.setInt(1,classid);
				stmt.setString(2,time);
				stmt.setString(3,time);
				stmt.executeUpdate();
				stmt.close();
			}else {
				System.out.println("Invalid time format.\n");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//add a student into a class
	public static void addStudentinClass(int studentid , int classid , Connection con) {
		try {
			String sql="insert into studentinclass values (?,?) ON DUPLICATE KEY UPDATE StudentInClassRefStudentId = ?"; 
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, studentid);
			stmt.setInt(2, classid);
			stmt.setInt(3, studentid);
			stmt.executeUpdate();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//add a teacher into a class
	public static void addTeacherinClass(int teacherid , int classid , Connection con) {
		try {
			String sql="insert into teacherinclass values (?,?) ON DUPLICATE KEY UPDATE TeacherInClassRefTeacherId = ?"; 
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, teacherid);
			stmt.setInt(2, classid);
			stmt.setInt(3, teacherid);
			stmt.executeUpdate();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//add attendance
	public static void addAttendance(int studentid , int classtimeid , Connection con) {
		try {
			String sql="insert into attendance (AttendanceRefStudentId , AttendanceRefClassTimeId)"
					+ " values (?,?) ON DUPLICATE KEY UPDATE Attendance = 0";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, studentid);
			stmt.setInt(2, classtimeid);
			stmt.executeUpdate();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addAttendance(int studentid , int classtimeid , int attendance , Connection con) {
		try {
			String sql="insert into attendance (AttendanceRefStudentId , AttendanceRefClassTimeId , Attendance)"
					+ " values (?,?,?) ON DUPLICATE KEY UPDATE Attendance = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, studentid);
			stmt.setInt(2, classtimeid);
			stmt.setInt(3, attendance);
			stmt.setInt(4, attendance);
			stmt.executeUpdate();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//change attendance
	public static void changeAttendance(int studentid , int classtimeid , int new_attendance , Connection con) {
		try {
			String sql="UPDATE attendance SET Attendance = ? where AttendanceRefStudentId=? AND AttendanceRefClassTimeId=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1,new_attendance);
			stmt.setInt(2, studentid);
			stmt.setInt(3, classtimeid);
			stmt.executeUpdate();
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//clear student
	public static void clearStudent(Connection con) {
		try {
			String sql="delete from student";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//clear class
	public static void clearClass(Connection con) {
		try {
			String sql="delete from class";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//clear teacher
	public static void clearTeacher(Connection con) {
		try {
			String sql="delete from teacher";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//clear classtime
	public static void clearClassTime(Connection con) {
		try {
			String sql="delete from classtime";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//clear StudentinClass
	public static void clearStudentinClass(Connection con) {
		try {
			String sql="delete from studentinclass";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//clear TeacherinClass
	public static void clearTeacherinClass(Connection con) {
		try {
			String sql="delete from teacherinclass";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//clear Attendance
	public static void clearAttendance(Connection con) {
		try {
			String sql="delete from attendance";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
		
	
	public static void main(String args[]) {
		try {
			// create connection
			String conurl="jdbc:mysql://127.0.0.1:3306/roll_call_system_database";
			java.lang.Class.forName("java.sql.Driver");
			Connection con= DriverManager.getConnection(conurl,"root","hill1017");
			
			//clear ALL
			clearStudent(con);
			clearTeacher(con);
			clearClass(con);
			//clearClassTime(con);
			
			Student bob=new Student("bob0231","bob","bobnumberone","bob@bob.bob.com","0908080808");
			Teacher t= new Teacher("teacher01","teacher","password","email@email.com","0922222222","major");
			Class c = new Class("class","hardcore",30);
			
			bob.addStudent(con);
			t.addTeacher(con);
			c.addClass(con);
			
			bob.getId(con);
			t.getId(con);
			c.getId(con);
			
			if(bob.getId(con)==Student.getIdbyUserName("bob0231", con)) {
				System.out.println("pass 1\n");
			}
			
			if(t.getId(con)==Teacher.getIdbyUserName("teacher01", con)) {
				System.out.println("pass 2\n");
			}
			
			if(c.getId(con)==Class.getIdbyName("class", con)) {
				System.out.println("pass 3\n");
			}
			
			bob.joinClass(c.getId(con), con);
			t.joinClass(c.getId(con), con);
			
			// end connection
			con.close();
			
			System.out.println("process success!");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//check if the date is valid format 
	public static boolean isValidDateTime(String time) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			df.setLenient(false);
			df.parse(time);
			return true;
		}catch (ParseException e){
			return false;
		}
	}
	
	//check if studentid is valid
	public static boolean isValidStudentId(int studentid , Connection con) {
		try {
			String sql = "select StudentId from student where StudentId = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, studentid);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//check if teacherid is valid
	public static boolean isValidTeacherId(int teacherid , Connection con) {
		try {
			String sql = "select TeacherId from teacher where TeacherId = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, teacherid);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//check if classid is valid
	public static boolean isValidClassId(int classid , Connection con) {
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
