package roll_call_system;

import java.sql.*;
import java.util.*;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.*;
import java.text.DateFormat;
import java.util.Date;

public class function {
	
	//add a row into student
	public static void addStudent(String name , String username , String password ,String email,String phone, Connection con) {
		try {
			String sql="insert into student (StudentName , StudentUserName , StudentPassword , StudentEmail , StudentPhoneNumber) "
				+ "values ( ? , ? , ? , ? , ? )";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,name);
			stmt.setString(2,username);
			stmt.setString(3,password);
			stmt.setString(4,email);
			stmt.setString(5,phone);
			
			stmt.executeUpdate();
			
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//add a row into class
	public static void addClass(String name , String description , int credit , Connection con) {
		try {
			String sql="insert into class (ClassName , ClassDescription , ClassCredit) "
				+ "values (?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,name);
			stmt.setString(2,description);
			stmt.setInt(3,credit);
			stmt.executeUpdate();
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//add a row into teacher
	public static void addTeacher(String name ,String major, String username , String password ,String email,String phone, Connection con) {
		try {
			String sql="insert into teacher (TeacherName , TeacherMajor , TeacherUserName , TeacherPassword , TeacherEmail , TeacherPhoneNumber) "
				+ "values (?,?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,name);
			stmt.setString(2, major);
			stmt.setString(3,username);
			stmt.setString(4,password);
			stmt.setString(5,email);
			stmt.setString(6,phone);
			stmt.executeUpdate();
			stmt.close();
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
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
	
	//get student ID by user name , return the id or -1 if there is a bug
	public static int getStudentId(String username,Connection con) {
		try {
			//do select
			String sql="select StudentId from student where StudentUserName = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, username);
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
	
	//get teacher id by user name , return the id or -1 if there is a bug
	public static int getTeacherId(String username , Connection con){
		try {
			//do select
			String sql="select TeacherId from teacher where TeacherUserName = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, username);
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
	
	//get class id by name , return the id or -1 if there is a bug
	public static int getClassId(String name , Connection con){
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
	
	//make a student join a class
	//after I separate them into different class(in java) , the name would be turned into joinClass
	public static void StudentjoinClass(int studentid , int classid , Connection con) {
		try {
			
			if(!(isValidStudentId(studentid,con)&&isValidClassId(classid,con))) {
				System.out.println("invalid studentid or classid in StudentjoinClass.");
				return;
			}
			
			//insert into studentinclass
			addStudentinClass(studentid , classid , con);
			
			//add Attendance
			Date dnow = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql="select ClassTimeId from classtime where ClassTimeRefClassId = ? AND Time > ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, classid);
			stmt.setString(2, formatter.format(dnow));
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				addAttendance(studentid,rs.getInt(1),0,con);
			}
			
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
			
			//add student 
			addStudent("JonCina", "bob0102" , "iampassword" , "bob0102@sudo.com" , "0912345678" , con);
			
			//add teacher
			addTeacher("Jesus" , "ALMIGHTY" , "GodHimself" , "HoWDarEYouHaCKGod" , "jesus@cloud.com" , "0142424242" , con);
			
			//add class
			addClass("Chinese 101" , "We will introduce proses of the ancients" , 100 , con);
			
			//add classtime
			//addClassTime( 34 , "2021-02-06 18:00:00",con);
			
			//add studentinclass
			//addStudentinClass(36,35,con);
			
			//add teacherinclass
			//addTeacherinClass(40,36,con);
			
			//get id
			int std_id = getStudentId("bob0102",con);
			int teacher_id=getTeacherId("GodHimself",con);
			int class_id=getClassId("Chinese 101",con);
			
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DAY_OF_YEAR,365);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			addClassTime(class_id,formatter.format(now.getTime()),con);
			
			now.add(Calendar.DAY_OF_YEAR,365);
			
			addClassTime(class_id,formatter.format(now.getTime()),con);
			
			now.add(Calendar.DAY_OF_YEAR,-5*365);
			
			addClassTime(class_id,formatter.format(now.getTime()),con);
			
			
			StudentjoinClass(std_id,class_id,con);
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
			return rs!=null;
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
			return rs!=null;
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
			return rs!=null;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
}
