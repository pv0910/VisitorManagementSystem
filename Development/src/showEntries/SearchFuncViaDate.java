package showEntries;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.*; // self made package

@WebServlet("/SearchFuncViaDate")
public class SearchFuncViaDate extends HttpServlet 
{	
	
	private void ifNotAuthenticated(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {	
    	resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // tells browser to not store in cache
    	resp.setHeader("Pragma", "no-cache"); // for older version of http like http 1.0, etc
    	
    	HttpSession session = req.getSession();
    	if(session.getAttribute("loggedIn") == null)
    	{
    		resp.sendRedirect(req.getContextPath() + "/HTML/loginPage.html");
    	}
    }
	
	
	
	private ResultSet getEntries(String startDate, String endDate)
	{	
		startDate = startDate + " 00:00:00";
		endDate = endDate + " 23:59:59";
		
		String query = "select * from entries where entry_time >= " + "'" + startDate + "'" + " and entry_time <= " + "'" + endDate + "' order by entry_time desc;";
		return ConnectionDB.executeQueryResultSet(query);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		ifNotAuthenticated(req, resp);
		
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endData");
		
		
		ResultSet resultset = getEntries(startDate, endDate);
		
		HttpSession session = req.getSession();
		session.setAttribute("entries", resultset);
		
		resp.sendRedirect(req.getContextPath() + "/JSP_Files/showData.jsp");
	}

}
