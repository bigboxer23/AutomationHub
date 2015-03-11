package com.jones.matt.scheduler;

import java.util.List;

/**
 *
 */
public class EventManager
{
	private SchedulerDao myDao;

	public void setDao(SchedulerDao theDao)
	{
		myDao = theDao;
	}

	public void logEvent(LoggedEvent theEvent)
	{
		myDao.logEvent(theEvent);
	}

	public List<LoggedEvent> getEvents()
	{
		return myDao.getEvents(-1);
	}
}
