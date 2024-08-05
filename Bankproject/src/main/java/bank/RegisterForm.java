package bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterForm extends HttpServlet{
	 
     /**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException
     { 
		
		Long accountnumber=0l;
    	 PrintWriter out = res.getWriter();
 		res.setContentType("text/html");
 		String name = req.getParameter("name");
 		String mb = req.getParameter("mobilenumber");
 		String email= req.getParameter("email");
 		String aadhar= req.getParameter("aadhar");
 		String password= req.getParameter("password");
 		String uamount= req.getParameter("amount");
 		
 		Double amt = Double.parseDouble(uamount);
 		
 		String url="jdbc:mysql://localhost:3306/DatabaseSchemeName?user=Username&password=Userpassword";
 		String insert = "insert into bank"+"(Name,AccountNumber,MobileNumber,Amount,Password,EmailID,AadharID)"+"values(?,?,?,?,?,?,?)"; 
 		
 	   
 		try{
 		   Class.forName("com.mysql.cj.jdbc.Driver");
 		  Connection con = DriverManager.getConnection(url);
 		  PreparedStatement ps = con.prepareStatement(insert);
 		 
 		   

 	      Random r= new Random();
			Long act = r.nextLong(1000000000000l);
			if (act>10000000000l) {
				
				accountnumber=act;
			}
 	      
 		
 		   ps.setString(1, name);
 		   ps.setLong(2, accountnumber);
 		   ps.setString(3, mb);
 		   ps.setDouble(4, amt);
 		   ps.setString(5, password);
 		   ps.setString(6,email);
 		  ps.setString(7,aadhar );
 		   int row=ps.executeUpdate();
 		   out.print(row); 
 		   
 		  
 		    if(row!=0)
 		   {
 			   out.print("Successfully Registered ");
 			  
 			   RequestDispatcher rd = req.getRequestDispatcher("LoginPage.html");
				rd.forward(req, res);
 		   }
 		 
 	     }
 		catch(Exception e)
 		{
 			
 			e.printStackTrace();
 		}
     }
	
}
