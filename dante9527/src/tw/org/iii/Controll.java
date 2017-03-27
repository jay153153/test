package tw.org.iii;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Controll")
public class Controll extends HttpServlet 
{	
	private PrintWriter out           ;
	private String[]    fields = {"id","gender","Collar","color","size"} ;
	
	private String Load_View(String file)
	{
		String uploadPath = getServletContext().getInitParameter("View-path") ;
		File   uploadFile = new File(uploadPath,file) ;
		
		long   len        = uploadFile.length()       ;
		byte[] buf        = new byte[(int) len]       ;
		
		try
		{
			BufferedInputStream bin = new BufferedInputStream(new FileInputStream(uploadFile)) ;
			bin.read(buf) ;
			bin.close()   ;
		}
		catch(Exception e)
		{
			System.out.println("Load Exception : " + e.toString()) ;
		}
		
		return new String(buf);
	}
	private void HTML_Switcher(ArrayList<HashMap<String,String>> data)
	{
		String htmlView = Load_View("view_MySQLTable.html") ;
		
		out.print("<script> function isDelete( 姓名)"
				+ "{return confirm('Delete '+姓名+' ?');}</script>") ;
		out.print(htmlView) ;
		
		for(HashMap<String,String> row:data)
		{
			out.print("<tr align='center'>") ;
			
			for(String field:fields)
			{
				out.print(String.format("<td>%s",row.get(field))) ;
			}
			
			out.print(String.format("<td><a href=?delid=%s onclick='return isDelete(\"%s\");'>Delete</a></td>",
					row.get(fields[0]), row.get(fields[1])));
			out.print(String.format("<td><a href='Edit?editid=%s'>Edit</a>"
	 	    		 ,row.get(fields[0]))) ; 
	       }
		
	 	   out.print("</table>");
	 	   
	 	  System.out.println("HTML_Switcher Ok") ;
	}
	
	protected void Model_Switcher(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		response.setContentType("text/html;carset=utf-8") ;
		request.setCharacterEncoding("utf-8")             ;
		response.setCharacterEncoding("utf-8")            ; 
		out    = response.getWriter()                     ;
		
		String type  = request.getParameter("type")       ;
		String delid = request.getParameter("delid")      ;
		
		Model  mod   = new Model()                        ;
		
		if(type != null && type.equals("add"))
		{
			String gender  = request.getParameter(fields[1]) ;
			String Collar   = request.getParameter(fields[2]) ;
			String color = request.getParameter(fields[3]) ;
			String size = request.getParameter(fields[4]) ;
			
			System.out.println(size);
			mod.addData(gender,Collar,color,size) ;
			response.sendRedirect("Controll");
		}
		else if(delid != null)
		{
			mod.delData(delid) ;
		}
		else if(type != null && type.equals("edit"))
		{
			String updateid = request.getParameter("updateid") ;
			
			String gender  = request.getParameter(fields[1])  ;
			String Collar   = request.getParameter(fields[2])  ;
			String color = request.getParameter(fields[3])  ;
			
			mod.editData(updateid,gender,Collar,color) ;
		}
		
		HTML_Switcher(mod.queryData()) ;
		
		System.out.println("Model_Switcher Ok") ;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{	
		Model_Switcher(request,response) ;
		System.out.println("Get Ok")    ;
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request,response)       ;
		System.out.println("Post Ok") ;
	}

}
