package bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/deposite")
public class Deposite extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{

	 res.setContentType("text/html");
	   PrintWriter out = res.getWriter();
	   
	   String name=req.getParameter("name");
	   String mb=req.getParameter("mobilenumber");
	   String acct=req.getParameter("account");
	   String useramount=req.getParameter("amount");
	   Double Uamount = Double.parseDouble(useramount); 
	   
	 
	    String url="jdbc:mysql://localhost:3306/project1?user=root&password=password";
	    String select = "SELECT Amount FROM bank WHERE AccountNumber = ?";
	  
	   
		

				 try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection(url);
					
			         PreparedStatement ps1 = con.prepareStatement(select);
		             
		              ps1.setString(1,acct);
		               ResultSet rs = ps1.executeQuery();
		                Double damount= null;
		             
		               if (rs.next()) {
		                    damount = rs.getDouble("Amount");
		                 
		               } else {
		            	   
		            	   RequestDispatcher rd = req.getRequestDispatcher("Deposite.html");
							rd.include(req, res);
		                   out.print("<center><h1>Account Not Found</h1></center>");
		                   return;
		               }
		       
		                double newamount =damount+Uamount;
		              
		              
		               String update = "update bank set Amount=? where  AccountNumber=? and Name=? and MobileNumber=? ";
	                 PreparedStatement ps = con.prepareStatement(update);
		                
	                   ps.setDouble(1, newamount);
		               ps.setString(2, acct);
		               ps.setString(3, name);  
		               ps.setString(4,mb);
					   
					   int row=ps.executeUpdate();
					
					   if(row!=0)
					   {
						   out.println("<center><h1>Deposite Successfully</h1></center>");
						  
					   }
					   else
					   {
						   RequestDispatcher rd = req.getRequestDispatcher("Deposite.html");
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
