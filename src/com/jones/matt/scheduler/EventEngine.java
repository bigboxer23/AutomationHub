package com.jones.matt.scheduler;

import com.jones.matt.lights.controllers.scene.DaylightController;

import java.util.Calendar;
import java.util.logging.Logger;

/**
 * Engine for determining what events need to trigger when.  Iterates every minute and
 * checks the dao for events that match the time period and frequency
 */
public class EventEngine implements IConstants
{
	private SchedulerDao myDao;

	private DaylightController myDaylightController;

	private boolean myDaylightStatus;

	private static Logger myLogger = Logger.getLogger("com.jones");

	public EventEngine()
	{
		myDaylightController = new DaylightController();
		myDaylightStatus = myDaylightController.isDaylight();
	}

	public void doActions()
	{
		int anHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int aMinutes = Calendar.getInstance().get(Calendar.MINUTE);
		myLogger.config("Iterate " + anHour + ":" + aMinutes);
		for(Event anEvent : myDao.getItems((anHour * kHour) + (aMinutes * kMinute)))
		{
			myLogger.config("found event: " + anEvent.getName() + ", checking frequency.");
			if (shouldRun(anEvent))
			{
				myLogger.info("running event: " + anEvent.getName());
				anEvent.getAction().doAction();
			}
		}
		boolean aDaylightStatus = myDaylightController.isDaylight();
		if (aDaylightStatus != myDaylightStatus)
		{
			myDaylightStatus = aDaylightStatus;
			for(Event anEvent : myDao.getItems(-1))
			{
				if (!myDaylightStatus && anEvent.isFrequencyEnabled(kSunset))
				{
					myLogger.info("running event: " + anEvent.getName());
					anEvent.getAction().doAction();
				} else if (myDaylightStatus && anEvent.isFrequencyEnabled(kSunrise))
				{
					myLogger.info("running event: " + anEvent.getName());
					anEvent.getAction().doAction();
				}
			}
		}
	}

	/**
	 * Check the frequency field to see if we should be running this event
	 *
	 * @param theEvent
	 * @return true if should run
	 */
	private boolean shouldRun(Event theEvent)
	{
		if (theEvent.isFrequencyEnabled(kWeekend) &&
				(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
						Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY))
		{
			return true;
		} else if (theEvent.isFrequencyEnabled(kWeekly) &&
				Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY &&
				Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
		{
			return true;
		} else if(theEvent.isFrequencyEnabled(kMonday) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
		{
			return true;
		} else if(theEvent.isFrequencyEnabled(kTuesday) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
		{
			return true;
		} else if(theEvent.isFrequencyEnabled(kWednesday) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
		{
			return true;
		} else if(theEvent.isFrequencyEnabled(kThursday) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
		{
			return true;
		} else if(theEvent.isFrequencyEnabled(kFriday) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
		{
			return true;
		} else if(theEvent.isFrequencyEnabled(kSaturday) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
		{
			return true;
		} else if(theEvent.isFrequencyEnabled(kSunday) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
		{
			return true;
		}
		return false;
	}

	public void setDao(SchedulerDao theDao)
	{
		myDao = theDao;
	}
}
