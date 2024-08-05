package bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/withdrawlogin")
public class WithdrawLogin extends HttpServlet{
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{

	 res.setContentType("text/html");
	   PrintWriter out = res.getWriter();
	   
	   String mb=req.getParameter("mobilenumber");
	   String password=req.getParameter("password"); 
	   
	 
	    String url="jdbc:mysql://localhost:3306/project1?user=root&password=password";
	    String select = "SELECT * FROM bank WHERE MobileNumber = ? AND Password=?";
	  
	   
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection(url);
				    PreparedStatement ps1= con.prepareStatement(select);
				 
		             
		              ps1.setString(1,mb);
		              ps1.setString(2,password);
		               ResultSet rs = ps1.executeQuery();
		                 String dmb=null;
		                 String dpass=null;
		              
		              if(rs.next())
		              {
		            	  dmb = rs.getString("MobileNumber");
		            	  dpass= rs.getString("Password");
		            	
		            	 if(mb.equals(dmb) && password.equals(dpass))
		            	 {
		            		
		            		  RequestDispatcher rd = req.getRequestDispatcher("Withdraw.html");
								rd.forward(req, res);
							 
		            		  
		            	 }
		            	
		            	  
		              }
		              else
		              {
		            	  RequestDispatcher rd = req.getRequestDispatcher("Withdrawlogin.html");
							rd.include(req, res);
						   out.println("<center><h1>Invalid Details</h1></center>");

		              }
		           
					} 
				   catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					   }
		   
       
	   
	}
	   
	
}

