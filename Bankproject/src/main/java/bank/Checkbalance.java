package bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/check")
public class Checkbalance extends HttpServlet {
	
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
   {
	   res.setContentType("text/html");
	   PrintWriter out = res.getWriter();
	   
	   String mb=req.getParameter("mobilenumber");
	   String password=req.getParameter("password");
	   
	   String url="jdbc:mysql://localhost:3306/project1?user=root&password=password";
	    String select="SELECT * FROM bank WHERE MobileNumber=? AND Password=?";
	   
	   try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(url);
		PreparedStatement ps = con.prepareStatement(select);
		
		ps.setString(1, mb);
		ps.setString(2, password);
		
		ResultSet rs=ps.executeQuery();
		HttpSession session=req.getSession();
		
		if(rs.next())
		{
			double amt = rs.getDouble(5);
			session.setAttribute("Amount", amt);
		
			out.println("<center><h1>"+"Available Balance : "+amt+"</h1></center>");
			
		
		}
		else
		{
			RequestDispatcher rd = req.getRequestDispatcher("Checkbalancelogin.html");
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
