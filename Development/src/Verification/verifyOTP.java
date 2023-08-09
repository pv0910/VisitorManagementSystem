package Verification;

import java.io.IOException;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import database.*; // self made package
import security.MakeEntry; // self made

@WebServlet("/verifyOTP")
public class verifyOTP extends HttpServlet
{	
	private String OTP;
	
	private boolean verify() throws SQLException
	{	
		String query = "select * from otp where otp = " + "'" + this.OTP + "'" + ";";
		ResultSet resultset = ConnectionDB.getResult(query, null);
		if(resultset!= null && resultset.next() == true)
		{	
			query = "delete from otp where otp = " + this.OTP + ";";
			ConnectionDB.executeQuery(query);
			return true;
		}
		return false;
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{	
		HttpSession session = req.getSession();
		this.OTP = req.getParameter("OTP");
		try {
			if(verify() == true)
			{	
				MakeEntry.makeEntry(req);
				session.setAttribute("entryMadeModal", true); // for showing modal
				resp.sendRedirect(req.getContextPath() + "/JSP_Files/entryForm.jsp");
			}
			else
			{
				resp.sendRedirect(req.getContextPath() + "/JSP_Files/mobOTPForm.jsp");
			}
		} 
		catch (SQLException e) 
		{	
			session.setAttribute("otpError", true);
			resp.sendRedirect(req.getContextPath() + "/JSP_Files/mobileOTPForm.jsp");
			return;
		}
	}
}
