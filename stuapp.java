package App;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.io.IOException;
import java.util.Scanner;


// import com.mysql.cj.xdevapi.PreparableStatement;

//connect to data base
class connect {
    static Connection con;

    public static Connection createC() {
        try {
            // load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // connet to the database
            String user = "root";
            String password = "bca12345";
            String url = "jdbc:mysql://localhost:3306/students";

            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}

// data store into the db
class db{
    public static boolean insertintodb(Input in) {
        boolean f = false;
        try {
            Connection con = connect.createC();
            String quary = "insert into studetail(sname, scity, sphone) values(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(quary);
            pstmt.setString(1, in.getstudentname());
            pstmt.setString(2, in.getstudentcity());
            pstmt.setString(3, in.getstudentphone());
            pstmt.executeUpdate();
            f = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
    public static boolean deletefromdb(int i){
        boolean f = false;
        try {
            Connection con = connect.createC();
            String quary = "delete from studetail where sid=?";
            PreparedStatement pstmt = con.prepareStatement(quary);
            pstmt.setInt(1, i);
            pstmt.executeUpdate();
            f = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
    public static void showall(){
        try {
            Connection con = connect.createC();
            String quary = "select * from studetail;";
            java.sql.Statement stmt=con.createStatement();
            ResultSet set= stmt.executeQuery(quary);
            while(set.next()){

                int id=((ResultSet) set).getInt("sid");
                String name=((ResultSet) set).getString("sname");
                String city=((ResultSet) set).getString("scity");
                String phone=((ResultSet) set).getString("sphone");

                System.out.println("Student ID: "+id);
                System.out.println("Student Name: "+name);
                System.out.println("Student City: "+city);
                System.out.println("Student Phone: "+phone);
                System.out.println("\n################################\n");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

// Input from the user class
class Input {
    private int studentid;
    private String studentname;
    private String studentcity;
    private String studentphone;

    public void setstudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getstudentname() {
        return studentname;
    }

    public void setstudentcity(String studentcity) {
        this.studentcity = studentcity;
    }

    public String getstudentcity() {
        return studentcity;
    }

    public void setstudentid(int studentid) {
        this.studentid = studentid;
    }

    public int getstudentid() {
        return studentid;
    }

    public void setstudentphone(String studentphone) {
        this.studentphone = studentphone;
    }

    public String getstudentphone() {
        return studentphone;
    }

    public Input(int studentid, String studentname, String studentcity, String studentphone) {
        super();
        this.studentid = studentid;
        this.studentname = studentname;
        this.studentcity = studentcity;
        this.studentphone = studentphone;
    }

    public Input(String studentname, String studentcity, String studentphone) {
        super();
        this.studentname = studentname;
        this.studentcity = studentcity;
        this.studentphone = studentphone;
    }

    public Input() {
        super();
    }

    public String toString() {
        return "Student Details = [Student Id = " + studentid + ", Student Name = " + studentname + ", Student Phone = "
                + studentphone + ", Student City = " + studentcity + "]";

    }
}

// Main Application
public class stuapp {
    public static void main(String[] args) {
        System.out.println("........Welcome........");
        try (Scanner s = new Scanner(System.in)) {
            while (true) {
                System.out.println("Press 1 to Add student");
                System.out.println("Press 2 to Delete student");
                System.out.println("Press 3 to Show all students");
                System.out.println("Press 4 to Exit student");
                int c = s.nextInt();

                if (c == 1) {

                    System.out.print("Add a student!!");
                    String a = s.nextLine();
                    System.out.println("\nEnter your name: ");
                    String name = s.nextLine();
                    System.out.println("Enter your Phone No.: ");
                    String phone = s.nextLine();
                    System.out.println("Enter your city: ");
                    String city = s.nextLine();

                    Input in = new Input(name, city, phone);
                    boolean ans = db.insertintodb(in);
                    if (ans) {
                        System.out.println("Added");
                    } else {
                        System.out.println("Not Added");
                    }
                    System.out.println(in);

                } else if (c == 2) {
                    System.out.println("Enter The student ID: ");
                    int i =s.nextInt();
                    db.deletefromdb(i);
                    boolean ans = db.deletefromdb(i);
                    if (ans) {
                        System.out.println("Deleted");
                    } else {
                        System.out.println("Not Deleted");
                    }
            
                } else if (c == 3) {
                    db.showall();
                } else if (c == 4) {
                    break;
                } else {

                }
            }
            System.out.println("Thankyou!");
        }
    }
}
