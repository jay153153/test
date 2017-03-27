package tw.org.iii;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/Model")
public class Model extends HttpServlet 
{
	private PrintWriter out           ;
	private Connection  conn   = null ;
	private String[]    fields = {"id","gender","Collar","color","size"} ;
	
    public Model() 
    {
    	try
    	{
    		Class.forName("com.mysql.jdbc.Driver") ;
    			
    		Properties prop = new Properties()     ;
    		prop.setProperty("user", "root")       ;
    		prop.setProperty("password", "root")   ;
    			
    		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test", prop) ;
    		
    		//Statement stmt = conn.createStatement() ;
    		//stmt.execute("set name UTF-8") ;
    			
    		System.out.println("Init Ok") ;
    	}
    	catch(Exception e)
    	{
    		System.out.println("Init Exception : " + e.toString()) ; 
    	}
    }

    ArrayList<HashMap<String,String>> queryData() //Model
	{
		ArrayList<HashMap<String,String>> data = new ArrayList<>() ;
		
		try
		{
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM member") ;
			ResultSet         rs    = pstmt.executeQuery();
			
			while(rs.next())
			{
				HashMap<String,String> row = new HashMap<>();
				
				for(String field : fields)
				{
					row.put(field,rs.getString(field)) ;
				}
				
				data.add(row);
			}
			
		}
		catch(SQLException e)
		{
			System.out.println("SQL Query Exception : " + e.toString()) ;
		}
		
		return data ;
	}
    
    void addData(String gender,String Collar,String color,String size) //Model
	{
		try
		{
			PreparedStatement pstmt = conn.prepareStatement("insert into member "
					+ "(gender,Collar,color,size)" + "values(?,?,?,?)") ;
			
			pstmt.setString(1,gender)  ;
			pstmt.setString(2,Collar)   ;
			pstmt.setString(3,color) ;
			pstmt.setString(4,size) ;
			pstmt.execute()             ;
		}
		catch(SQLException e)
		{
			System.out.println("AddData Exception : " + e.toString()) ;
		}
	}
    
    void delData(String id) //Model
	{
		try
		{
			PreparedStatement pstmt = conn.prepareStatement("delete from member where id = ?") ;
			
			pstmt.setString(1, id) ;
			pstmt.execute()        ;
		}
		catch(SQLException e)
		{
			System.out.println("DelData Exception : " + e.toString()) ;
		}
	}
	
	void editData(String updateid,String gender,String Collar,String color) //Model
	{
		try
		{
			PreparedStatement pstmt = conn.prepareStatement("update member set gender=?,"
					+ "Collar=?,color=? where id = ?") ;
			
			pstmt.setString(1,gender)  ;
			pstmt.setString(2,Collar)   ;
			pstmt.setString(3,color) ;
			pstmt.setString(4,updateid) ;
			pstmt.execute()             ;
		}
		catch(SQLException e)
		{
			System.out.println("editData Exception : " +e.toString()) ;
		}
	}
	void queryData(String gender,String Collar,String color,String size)
	{
		try
		{
		    PreparedStatement pstmt = conn.prepareStatement("select from member where name = ?");
		}catch(SQLException e){
			
		}
	}

}
