package com.jones.matt.scheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
		System.err.println("contextInit");
		if (myTimer != null)
		{
			myTimer.cancel();
		}
		setUpLogger();
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
		myTimer.cancel();
	}

	private void addDefaultEvents()
	{
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
		System.err.println("run");

		myEventEngine.doActions();
	}

	private void setUpLogger()
	{
		try
		{
			System.err.println("setuplogger");
			File aFile = new File(System.getProperty("log.location", "/home/pi/scheduler/logs/scheduler.log"));
			if (!aFile.exists())
			{
				aFile.createNewFile();
			}
			FileHandler aHandler = new FileHandler(System.getProperty("log.location", "/home/pi/scheduler/logs/scheduler.log"), true);
			aHandler.setFormatter(new SimpleFormatter());
			myLogger.addHandler(aHandler);
			myLogger.setLevel(Level.parse(System.getProperty("log.level", "WARNING")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
