package com.jones.matt.lights;

import com.jones.matt.scheduler.EventManager;
import com.jones.matt.scheduler.LoggedEvent;

import java.util.logging.Logger;

/**
 * encapsulate common logging code
 */
public class AbstractBaseController
{
	private EventManager myEventManager;

	protected static Logger myLogger = Logger.getLogger("com.jones");

	public AbstractBaseController(EventManager theEventManager)
	{
		myEventManager = theEventManager;
	}

	/**
	 * Log event to the db
	 *
	 * @param theEvent
	 */
	public void logEvent(LoggedEvent theEvent)
	{
		myLogger.info("logging event: " + theEvent.toString());
		myEventManager.logEvent(theEvent);
	}
}
