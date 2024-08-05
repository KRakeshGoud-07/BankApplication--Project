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
import jakarta.servlet.http.HttpSession;

@WebServlet("/Loginpage")
public class LoginPage extends HttpServlet {

	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException
    { 
	
   	 PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		
		String mb = req.getParameter("mobilenumber");
		String password= req.getParameter("password");
		
		
		String url="jdbc:mysql://localhost:3306/DatabaseName?user=userName&password=userpassword";
		String select = "SELECT * FROM bank WHERE MobileNumber=? AND Password=?"; 
		
	   
		try{
		   Class.forName("com.mysql.cj.jdbc.Driver");
		  Connection con = DriverManager.getConnection(url);
		  PreparedStatement ps = con.prepareStatement(select);
		 
		   ps.setString(1,mb);
		   ps.setString(2, password );
		   ResultSet rs=ps.executeQuery();
			HttpSession session=req.getSession();
			
		    if(rs.next())
		    {
				String mbn = rs.getString(4);
				String pswd = rs.getString(6);
				if(mbn.equals(mb) && password.equals(pswd))
				{
				
					 RequestDispatcher rd = req.getRequestDispatcher("Bank.html");
						rd.forward(req, res);
				}
				
		    }
		    else
		    {
				 RequestDispatcher rd = req.getRequestDispatcher("LoginPage.html");
					rd.include(req, res);
					out.println("<center><h1>Invalid Details</h1></center>");

		    }
			
	     }
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
    }
	
	
}
