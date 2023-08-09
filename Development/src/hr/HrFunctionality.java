package hr;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/HrFunctionality")
public class HrFunctionality extends HttpServlet
{	
	private void ifNotAuthenticated(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {	
    	resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // tells browser to not store in cache
    	resp.setHeader("Pragma", "no-cache"); // for older version of http like http 1.0, etc
    	
    	
    	HttpSession session = req.getSession();
    	session.removeAttribute("sucessModal"); // email sent when invite visitor or joinee
    	
    	if(session.getAttribute("loggedIn") == null)
    	{
    		resp.sendRedirect(req.getContextPath() + "/HTML/loginPage.html");
    	}
    }
	
	
	private void redirectAccordingToOption(String choosedOption, HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		if(choosedOption.equals("newJoinee") == true)
		{
			resp.sendRedirect(req.getContextPath() + "/JSP_Files/newJoineeForm.jsp");
		}
		else if(choosedOption.equals("invite") == true)
		{
			resp.sendRedirect(req.getContextPath() + "/JSP_Files/inviteVisitorForm.jsp");
		}
		else
		{
			resp.sendError(1, "Don't make changes with HTML. Invalid Option");
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String selectedOption = req.getParameter("chooseOption");
		redirectAccordingToOption(selectedOption, req, resp);
	}
}
