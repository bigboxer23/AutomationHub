package com.jones.matt.lights.controllers.garage;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.jones.matt.lights.controllers.AbstractBaseController;
import com.jones.matt.lights.controllers.IStatusController;
import com.jones.matt.lights.controllers.ISystemController;
import com.jones.matt.lights.controllers.ITemperatureController;
import com.jones.matt.lights.data.WeatherData;
import com.jones.matt.scheduler.EventManager;
import com.jones.matt.scheduler.LoggedEvent;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GarageController extends AbstractBaseController implements ISystemController, IStatusController, ITemperatureController
{
	private static String kGarageURL = System.getProperty("garageURL", "http://192.168.0.8");

	public GarageController(EventManager theEventManager)
	{
		super(theEventManager);
	}

	@Override
	public String doAction(List<String> theCommands)
	{
		if(theCommands.size() != 1)
		{
			return "Malformed input " + theCommands.size();
		}
		try
		{
			URLConnection aConnection = new URL(kGarageURL + "/" + theCommands.get(0)).openConnection();
			return new String(ByteStreams.toByteArray(aConnection.getInputStream()), Charsets.UTF_8);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		if (theCommands.get(0).equalsIgnoreCase("open") || theCommands.get(0).equalsIgnoreCase("close"))
		{
			logEvent(new LoggedEvent("Garage", "Garage", theCommands.get(0).equalsIgnoreCase("open"), System.currentTimeMillis(), "User"));
		}
		return null;
	}

	/**
	 * Fetch data about the weather from the sensor in the garage
	 *
	 * @return
	 * @throws IOException
	 */
	@Override
	public WeatherData getWeatherData()
	{
		try
		{
			DefaultHttpClient aHttpClient = new DefaultHttpClient();
			HttpGet aRequest = new HttpGet(kGarageURL + "/Weather");
			HttpResponse aResponse = aHttpClient.execute(aRequest);
			String aWeatherString = new String(ByteStreams.toByteArray(aResponse.getEntity().getContent()), Charsets.UTF_8);
			return new Gson().fromJson(aWeatherString, WeatherData.class);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean getStatus(int theLightId)
	{
		List<String> aList = new ArrayList<>();
		aList.add("Status");
		return Boolean.parseBoolean(doAction(aList));
	}
}
