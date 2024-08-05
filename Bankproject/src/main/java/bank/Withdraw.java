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

@WebServlet("/withdraw")
public class Withdraw extends HttpServlet{
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{

	 res.setContentType("text/html");
	   PrintWriter out = res.getWriter();
	   
	   String name=req.getParameter("name");
	   String mb=req.getParameter("mb");
	   String acct=req.getParameter("Acc");
	   String password=req.getParameter("password");
	   String useramount=req.getParameter("amount");
	   Double Uamount = Double.parseDouble(useramount); 
	   
	 
	    String url="jdbc:mysql://localhost:3306/databaseSchemeName?user=userName&password=userpassword";
	    String select = "SELECT * FROM bank WHERE AccountNumber = ?  ";
	  
	   
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection(url);
				    PreparedStatement ps1= con.prepareStatement(select);
				 
				    ps1.setString(1,acct);
		               ResultSet rs = ps1.executeQuery();
		                Double damount= null;
		                String daccount= null;
		                String dpassword= null;
		                Double updateamt= null;
		               
		        if (rs.next()) {
		                    damount = rs.getDouble("Amount");
		                    daccount = rs.getString("AccountNumber");
		                    dpassword = rs.getString("Password");
		                  
		                    
		                    if(acct.equals(daccount) && password.equals(dpassword))
		                    {
		                    	if(damount>=Uamount)
		                    	{
		                    		updateamt = damount-Uamount;
		                    	
			                    	String update = "update bank set Amount=? where  AccountNumber=? and Name=? and MobileNumber=? ";
			       	                 PreparedStatement ps = con.prepareStatement(update);
			       		                
			       	                   ps.setDouble(1, updateamt);
			       		               ps.setString(2, acct);
			       		               ps.setString(3, name);  
			       		               ps.setString(4,mb);
			       					   
			       					   int row=ps.executeUpdate();
			       					
			       					   if(row!=0)
			       					   {
			       						   out.println("<center><h1>Amount Successfully Withdraw</h1></center>");
			       						  
			       					   }
		                    		
		                          }
		                    	else
			                    	{
		                    		 out.println("<center><h1>Insufficient Balance</h1></center>");
			                    	}
		                    }
		                    
		                   
		                 
		                } 
		               else {
		                   		               
		            	   RequestDispatcher rd = req.getRequestDispatcher("Withdraw.html");
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

