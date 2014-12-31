package com.jones.matt.scheduler;

import com.jones.matt.scheduler.actions.Action;
import com.thoughtworks.xstream.XStream;

/**
 * Event value object
 *
 * Contains time, frequency, a name, and a serialized action class
 */
public class Event
{
	private String myName;

	/**
	 * Action class serialized to a string
	 */
	private String myAction;

	/**
	 * time in millis from 0 - 24 hours
	 */
	private int myTime;

	/**
	 * bit mask for frequency (weekdays, weekend, specific day, sunset/down...)
	 */
	private int myFrequency;

	public Event(String theName, Action theAction, int theTime)
	{
		this(theName, new XStream().toXML(theAction), 0, theTime);
	}

	public Event(String theName, String theAction, int theFrequency, int theTime)
	{
		myName = theName;
		myAction = theAction;
		myFrequency = theFrequency;
		myTime = theTime;
	}

	public int getFrequency()
	{
		return myFrequency;
	}

	public int getTime()
	{
		return myTime;
	}

	public void setTime(int theTime)
	{
		myTime = theTime;
	}

	public Action getAction()
	{
		return (Action)new XStream().fromXML(myAction);
	}

	public String getActionString()
	{
		return myAction;
	}

	public String getName()
	{
		return myName;
	}

	public void setName(String theName)
	{
		myName = theName;
	}

	protected boolean isFrequencyEnabled(int theFrequency)
	{
		return (myFrequency & theFrequency) == theFrequency;
	}

	protected void setFrequency(int theFrequency, boolean theValue)
	{
		myFrequency = theValue ? (myFrequency | theFrequency) : (myFrequency & ~theFrequency);
	}

	public String toString()
	{
		return myName + ":" + myAction + ":" + myFrequency + ":" + myTime;
	}
}
