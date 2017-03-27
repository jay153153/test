package tw.org.iii;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Edit")
public class Edit extends HttpServlet 
{
	private PrintWriter out           ;
	private Connection  conn   = null ;
	 							
	private String[]    fields = {"id","gender","Collar","color"} ;
	
    public Edit() 
    {
    	try
    	{
    		Class.forName("com.mysql.jdbc.Driver") ;
    			
    		Properties prop = new Properties()     ;
    		prop.setProperty("user", "root")       ;
    		prop.setProperty("password", "root")   ;
    			
    		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test", prop) ;
    			
    		System.out.println("Init Ok") ;
    	}
    	catch(Exception e)
    	{
    		System.out.println("Init Exception : " + e.toString()) ; 
    	}
    }
    
    private HashMap<String,String> queryData(String id) //Model
	{
    	HashMap<String,String> row = new HashMap<>();
		
		try
		{
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM member" + 
					" where id = ?") ;
			
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			rs.first();
			row.put("id", rs.getString("id"));
			row.put("gender", rs.getString("gender"));
			row.put("Collar", rs.getString("Collar"));
			row.put("color", rs.getString("color"));
		}
		catch(SQLException e)
		{
			System.out.println("SQL Query Exception : " + e.toString()) ;
		}
		
		return row ;
	}
    
    private void outHTML(HashMap<String,String> row)
    {
    	out.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>");
		out.println("<form action='Controll'>");
		out.println(String.format("<input type='hidden' name='updateid' value='%s'><br>",
				row.get("id")));
		out.println(String.format("gender: <input type='text' name='gender' value='%s'><br>",
				row.get("gender")));
		out.println(String.format("Collar: <input type='text' name='Collar' value='%s' ><br>",
				row.get("Collar")));
		out.println(String.format("color: <input type='text' name='color' value='%s' ><br>",
				row.get("color")));
		out.println("<input type='submit' name='type' value='edit' >");
		out.println("</form>");
    	
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		response.setContentType("text/html;carset=utf-8") ;
		request.setCharacterEncoding("utf-8")             ;
		response.setCharacterEncoding("utf-8")            ; 
		out    = response.getWriter()                     ;
		
		String editid = request.getParameter("editid")    ;
		
		outHTML(queryData(editid));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
