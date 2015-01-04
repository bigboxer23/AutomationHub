package com.jones.matt.scheduler;

import com.jones.matt.scheduler.actions.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Default list of events to add to our database
 *
 * on in morning
 * on in evening
 * off in late evening
 */
public class DefaultEvents implements IConstants
{
	public static List<Event> getDefaultEvents()
	{
		List<Event> anEvents = new ArrayList<>();
		List<String> aLights = new ArrayList<>();
		aLights.add("http://localhost:8080/Lights/Weather/2");
		aLights.add("http://localhost:8080/Lights/BathRoom/ON");//Bathroom
		aLights.add("http://localhost:8080/Lights/X/A3/ON");//Kitchen
		Event anEvent = new Event("Morning On", new Action(aLights), getTime(6, 10));
		anEvent.setFrequency(kWeekly, true);
		anEvents.add(anEvent);

		aLights = new ArrayList<>();
		aLights.add("http://localhost:8080/Lights/AllLights/OFF");
		anEvent = new Event("Morning Off", new Action(aLights), getTime(7, 45));
		anEvent.setFrequency(kWeekly, true);
		anEvents.add(anEvent);

		aLights = new ArrayList<>();
		aLights.add("http://localhost:8080/Lights/X/H2/ON");//Upstairs (for now)
		aLights.add("http://localhost:8080/Lights/BathRoom/ON");//Bathroom
		aLights.add("http://localhost:8080/Lights/X/A3/ON");//Kitchen
		aLights.add("http://localhost:8080/Lights/LivingRoom/ON");//Living Room
		anEvent = new Event("Evening On", new Action(aLights), getTime(17, 0));
		anEvent.setFrequency(kSunset, true);
		anEvents.add(anEvent);


		aLights = new ArrayList<>();
		aLights.add("http://localhost:8080/Lights/AllLights/OFF");
		anEvent = new Event("Evening Off", new Action(aLights), getTime(22, 30));
		anEvent.setFrequency(kMonday, true);
		anEvent.setFrequency(kTuesday, true);
		anEvent.setFrequency(kWednesday, true);
		anEvent.setFrequency(kThursday, true);
		anEvent.setFrequency(kSaturday, true);
		anEvent.setFrequency(kSunday, true);
		anEvents.add(anEvent);

		aLights = new ArrayList<>();
		aLights.add("http://localhost:8080/Lights/AllLights/OFF");
		anEvent = new Event("Friday Off", new Action(aLights), getTime(23, 30));
		anEvent.setFrequency(kFriday, true);
		anEvents.add(anEvent);

		return anEvents;
	}

	public static void main(String[] args)
	{
		DefaultEvents.getDefaultEvents();
	}

	/**
	 * Convert hours and minutes to number of millis
	 *
	 * @param theHour
	 * @param theMinute
	 * @return
	 */
	public static int getTime(int theHour, int theMinute)
	{
		return kHour * theHour + (kMinute * theMinute);
	}
}
