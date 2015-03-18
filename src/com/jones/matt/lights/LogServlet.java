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
public class LogServlet extends AbstractServlet
{
	private EventManager myEventManager;

	@Override
	public void init()
	{
		myEventManager = new EventManager();
		myEventManager.setDao(new SchedulerDao());
	}

	@Override
	public void process(HttpServletRequest theRequest, HttpServletResponse theResponse) throws ServletException, IOException
	{
		for (LoggedEvent anEvent : myEventManager.getEvents())
		{
			theResponse.getWriter().print(anEvent.toString() + "\n");
		}
		theResponse.setStatus(HttpServletResponse.SC_OK);
	}
}
