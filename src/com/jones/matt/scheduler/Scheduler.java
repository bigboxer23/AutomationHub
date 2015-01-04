package com.jones.matt.scheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 *
 */
public class Scheduler extends TimerTask implements IConstants, ServletContextListener
{
	private Timer myTimer;

	private SchedulerDao myDao;

	private EventEngine myEventEngine;

	private static Logger myLogger = Logger.getLogger("com.jones");

	@Override
	public void contextInitialized(ServletContextEvent theServletContextEvent)
	{
		myLogger.config("contextInitialized");
		if (myTimer != null)
		{
			myTimer.cancel();
		}
		myDao = new SchedulerDao();
		myDao.initializeDatabase();
		myEventEngine = new EventEngine();
		myEventEngine.setDao(myDao);
		addDefaultEvents();
		myTimer = new Timer();
		myTimer.scheduleAtFixedRate(this, 0, kMinute);
	}

	@Override
	public void contextDestroyed(ServletContextEvent theServletContextEvent)
	{
		myLogger.config("contextDestroyed");
		myTimer.cancel();
	}

	private void addDefaultEvents()
	{
		myLogger.config("adding default events");
		for (Event anEvent : DefaultEvents.getDefaultEvents())
		{
			myDao.deleteEvent(anEvent.getName());
			myDao.addEvent(anEvent);
		}
	}

	/**
	 * Check every minute for new actions to execute
	 */
	@Override
	public void run()
	{
		myEventEngine.doActions();
	}
}
