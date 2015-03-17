package com.jones.matt.scheduler;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 */
public class LoggedEvent
{
	private String myName;

	private String myID;

	private boolean myState;

	private long myTime;

	private String myTrigger;

	public LoggedEvent(String theName, String theID, boolean theState, long theTime, String theTrigger)
	{
		myName = theName;
		myID = theID;
		myState = theState;
		myTime = theTime;
		myTrigger = theTrigger;
	}

	public String getName()
	{
		return myName;
	}

	public String getID()
	{
		return myID;
	}

	public boolean isState()
	{
		return myState;
	}

	public long getTime()
	{
		return myTime;
	}

	public String getTrigger()
	{
		return myTrigger;
	}

	@Override
	public String toString()
	{
		return myName + ": " + (myState ? "On" : "Off") + ": " + DateFormat.getDateTimeInstance().format(new Date(myTime));
	}
}
