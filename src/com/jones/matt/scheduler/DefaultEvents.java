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
		aLights.add("http://localhost:8080/Lights/S/Weather/2");
		aLights.add("http://localhost:8080/Lights/S/Bathroom/ON");//Bathroom
		aLights.add("http://localhost:8080/Lights/S/Kitchen/ON");//Kitchen
		aLights.add("http://localhost:8080/Lights/S/LivingRoom/ON");//Living Room
		Event anEvent = new Event("Morning On", new Action(aLights), getTime(6, 25));
		anEvent.setFrequency(kWeekly, true);
		anEvents.add(anEvent);

		aLights = new ArrayList<>();
		aLights.add("http://localhost:8080/Lights/S/AllLights/OFF");
		anEvent = new Event("Morning Off", new Action(aLights), getTime(8, 15));
		anEvent.setFrequency(kWeekly, true);
		anEvents.add(anEvent);

		aLights = new ArrayList<>();
		aLights.add("http://localhost:8080/Lights/S/Upstairs/ON");//Upstairs (for now)
		aLights.add("http://localhost:8080/Lights/S/Bathroom/ON");//Bathroom
		aLights.add("http://localhost:8080/Lights/S/Kitchen/ON");//Kitchen
		aLights.add("http://localhost:8080/Lights/S/LivingRoom/ON");//Living Room
		aLights.add("http://localhost:8080/Lights/S/FrontHouse/ON");
		aLights.add("http://localhost:8080/Lights/S/FrontStreet/ON");
		anEvent = new Event("Evening On", new Action(aLights), getTime(17, 0));
		anEvent.setFrequency(kSunset, true);
		anEvents.add(anEvent);


		aLights = new ArrayList<>();
		aLights.add("http://localhost:8080/Lights/S/AllLights/OFF");
		anEvent = new Event("Evening Off", new Action(aLights), getTime(22, 30));
		anEvent.setFrequency(kMonday, true);
		anEvent.setFrequency(kTuesday, true);
		anEvent.setFrequency(kWednesday, true);
		anEvent.setFrequency(kThursday, true);
		anEvent.setFrequency(kSaturday, true);
		anEvent.setFrequency(kSunday, true);
		anEvents.add(anEvent);

		aLights = new ArrayList<>();
		aLights.add("http://localhost:8080/Lights/S/AllLights/OFF");
		anEvent = new Event("Evening Off Backup", new Action(aLights), getTime(1, 30));
		anEvent.setFrequency(kWeekly, true);
		anEvent.setFrequency(kWeekend, true);
		anEvents.add(anEvent);

		aLights = new ArrayList<>();
		aLights.add("http://localhost:8080/Lights/S/AllLights/OFF");
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
