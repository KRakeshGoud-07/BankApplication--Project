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

@WebServlet("/transaction")
public class Mobiletransaction extends HttpServlet{
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{

	 res.setContentType("text/html");
	   PrintWriter out = res.getWriter();
	   
	   String mb1=req.getParameter("mb1");
	   String amount=req.getParameter("amount");
	   String password=req.getParameter("password");
	   Double Uamount=Double.parseDouble(amount);
	   
	   String mb2=req.getParameter("mb2");
	  
	   
	 
	    String url="jdbc:mysql://localhost:3306/DatabaseSchemeName?user=userName&password=Userpassword";
	    String select1 = "SELECT * FROM bank WHERE MobileNumber = ?  ";
	      
	   try {
			   Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(url);
			    PreparedStatement ps1= con.prepareStatement(select1);
			     ps1.setString(1,mb1);
			     //ps1.setString(2, password);
			     ResultSet rs1=ps1.executeQuery();
			     Double damount=null;
			     Double UpdateAmt=null;
			    // String dmb1 = null;
			     //String dpassword1=null;
			     //out.print(mb1);
			     if(rs1.next())
			     {
			    	
			    	String  dmb1 = rs1.getString(4);
			    	String dpassword1 = rs1.getString(6);
			    	// out.print(dpassword1);
			    	 //out.print(dmb1);
			    	 if(password.equals(dpassword1))
			    	 {
			    		 damount=rs1.getDouble("Amount");
			    		 //out.print("true");
				    	 if(Uamount<damount)
				    	 {
					    	
					    	  String update = "update bank set Amount=? where  MobileNumber=?  ";
				                 PreparedStatement ps2 = con.prepareStatement(update);
					                

							     
							 String select2="select * from bank where MobileNumber=?";
							 PreparedStatement ps3= con.prepareStatement(select2);
							 ps3.setString(1, mb2);
							 ResultSet rs2=ps3.executeQuery();
							
							 Double damount2=null;
							 Double UpdateAmt2=null;
							 //String dmb2 = null;  
							// out.print("false");
							 
							 if(rs2.next())
							 {
								
								 String dmb2 = rs2.getString(4);
										 UpdateAmt=damount-Uamount;
										
										 ps2.setDouble(1, UpdateAmt);
							                ps2.setString(2,mb1);
								          int row1=ps2.executeUpdate();
								          
								          damount = rs2.getDouble("Amount");
										 UpdateAmt2 = damount+Uamount;
										
						                String update2 = "update bank set Amount=? where  MobileNumber=? ";
						                 PreparedStatement ps4 = con.prepareStatement(update2);
							                
						                   ps4.setDouble(1, UpdateAmt2);
							                ps4.setString(2,mb2);
								          int row2=ps4.executeUpdate();
								          if(row2!=0)
								          {
								        	  out.println("<center><h1>Transaction successfully completed</h1></center>");
								        
								          }
								          
								
							    }
							   else
					    		 {
								   RequestDispatcher rd = req.getRequestDispatcher("MobileTransaction.html");
									rd.include(req, res);
						    		 out.print("<center><h1>Invalid Reciever Details</h1></center>");
					    		 }
				    	 	}
				    	    else
				    	    {
				    	    	RequestDispatcher rd = req.getRequestDispatcher("MobileTransaction.html");
								rd.include(req, res);
					    		 out.print("<center><h1>Invalid Amount</h1></center>");
				    	    	
				    	    }
				    	   }
				    	 else
				    	 {
				    		 RequestDispatcher rd = req.getRequestDispatcher("MobileTransaction.html");
								rd.include(req, res);
					    		 out.print("<center><h1>Incorrect Password</h1></center>");
				    		 //out.print("incorrect password");
				    	 }
			    	 
			    	  }
			    	
			        
			     else
			     {
			    	 RequestDispatcher rd = req.getRequestDispatcher("MobileTransaction.html");
						rd.include(req, res);
			    		 out.print("<center><h1>Invalid Sender Details</h1></center>");
			    	// out.print("invalid sender details");
			       }
			     
			    	  
			     }
	   
			   catch(Exception e)
			   {
				// TODO Auto-generated catch block
					e.printStackTrace();
			   }
	}

}
