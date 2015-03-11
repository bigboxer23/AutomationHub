package com.jones.matt.lights;

import com.jones.matt.scheduler.EventManager;
import com.jones.matt.scheduler.LoggedEvent;
import com.jones.matt.scheduler.SchedulerDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Print content of log database to screen
 */
public class LogServlet extends HttpServlet
{
	private EventManager myEventManager;

	@Override
	public void init()
	{
		myEventManager = new EventManager();
		myEventManager.setDao(new SchedulerDao());
	}

	@Override
	public void doPost(HttpServletRequest theReq, HttpServletResponse theRes) throws ServletException, IOException
	{
		doGet(theReq, theRes);
	}

	@Override
	public void doGet(HttpServletRequest theRequest, HttpServletResponse theResponse) throws ServletException, IOException
	{
		for (LoggedEvent anEvent : myEventManager.getEvents())
		{
			theResponse.getWriter().print(anEvent.toString() + "\n");
		}

		//String aJsonResponse = aController.doAction(Arrays.<String>asList(Arrays.copyOfRange(anArgs, 1, anArgs.length)));
		//if (aJsonResponse != null)
		//{
		//	theResponse.setContentType("application/json; charset=utf-8");
		//	theResponse.getWriter().print(aJsonResponse);
		//}
		theResponse.setStatus(HttpServletResponse.SC_OK);
	}
}
