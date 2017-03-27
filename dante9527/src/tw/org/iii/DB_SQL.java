package tw.org.iii ;

import java.sql.* ;

public class DB_SQL 
{
	private Connection conn = null ;
	
	public DB_SQL()
	{}
	
	public void setConnection()
	{
		String JDBCDriver = null, connectionUrl = null ;
		
		JDBCDriver    = "com.microsoft.sqlserver.jdbc.SQLServerDriver" ;
		connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=TestDB;user=sa;password=sa;"+ "characterEncoding=UTF-8;" ;
		
		try
		{
			Class.forName(JDBCDriver) ;
			conn = DriverManager.getConnection(connectionUrl) ;
			
			System.out.println(" bean connection oK") ;
		}
		catch (SQLException SQLe) 
		{
			System.out.println(SQLe.toString());
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	public Connection getConnection() 
	{
		return conn;
	}
	public ResultSet getResultSet(String sql)
	{
		Statement  stmt = null ;
		ResultSet  rs   = null ;
		
		try
		{
			stmt = conn.createStatement()  ;
			rs   = stmt.executeQuery(sql) ;

		}
		catch (SQLException SQLe) 
		{
			System.out.println("Error: " + SQLe.toString());
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.toString());
		}
		
		return rs;
	}
	public int getUpdate(String sql)
	{
		Statement stmt  = null ;
		int       intNo = 0    ;
		
		try
		{
			stmt  = conn.createStatement()   ;
			intNo = stmt.executeUpdate(sql) ;
		}
		catch (SQLException SQLe) 
		{
			System.out.println("Error: " + SQLe.toString());
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.toString());
		}
		
		return intNo;
	}
}
