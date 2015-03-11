package com.jones.matt.lights.hue;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jones.matt.lights.AbstractBaseController;
import com.jones.matt.lights.ISystemController;
import com.jones.matt.scheduler.EventManager;
import com.jones.matt.scheduler.LoggedEvent;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * controller for a philips hue light system
 */
public class HueController extends AbstractBaseController implements ISystemController
{
	/**
	 * Username to access lights with
	 */
	private static String kUserName = System.getProperty("hueUserName", "test");

	/**
	 * Hue bridge address
	 */
	private static String kHueBridge = System.getProperty("hueBridge", "localhost");

	public HueController(EventManager theEventManager)
	{
		super(theEventManager);
	}

	/**
	 * Get url to bridge with username
	 *
	 * @return
	 */
	protected String getBaseUrl()
	{
		return "http://" + kHueBridge + "/api/" + kUserName;
	}

	@Override
	public String doAction(List<String> theCommands)
	{
		String aCommand = theCommands.get(1);
		JsonObject aJsonElement = new JsonObject();
		String aLight = theCommands.get(0);
		String aUrl = getBaseUrl() + "/lights/" + aLight + "/state";
		if (aLight.equalsIgnoreCase("z99"))
		{
			aUrl = getBaseUrl() + "/groups/0/action";
		}
		if (aCommand.equalsIgnoreCase("off"))
		{
			aJsonElement.addProperty("on", false);
		} else if(aCommand.equalsIgnoreCase("on"))
		{
			aJsonElement.addProperty("on", true);
			aJsonElement.addProperty("bri", 255);
			aJsonElement.addProperty("colormode", "ct");
			aJsonElement.addProperty("ct", 287);
		} else if(aCommand.equalsIgnoreCase("pulse"))
		{
			aJsonElement.addProperty("transitiontime", 40);
			for (int ai = 1; ai <= 6; ai++)
			{
				aJsonElement.addProperty("bri", ai % 2 == 0 ? 255 : 0);
				callBridge(aUrl, aJsonElement);
				try
				{
					Thread.sleep(4000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			return null;
		} else if(aCommand.equalsIgnoreCase("movie"))
		{
			aJsonElement.addProperty("bri", 175);
			aJsonElement.addProperty("colormode", "ct");
			aJsonElement.addProperty("ct", 420);
		} else if(aCommand.equalsIgnoreCase("xy"))
		{
			aJsonElement.addProperty("on", true);
			aJsonElement.addProperty("colormode", "xy");
			float[] anXY= new float[]{Float.parseFloat(theCommands.get(2)), Float.parseFloat(theCommands.get(3))};
			aJsonElement.add("xy", new Gson().toJsonTree(anXY));
		}
		//Brightness command passed with
		if (theCommands.size() == 3)
		{
			try
			{
				aJsonElement.addProperty("bri", Integer.parseInt(theCommands.get(2)));
			} catch (NumberFormatException e){}
		}
		callBridge(aUrl, aJsonElement);
		logEvent(new LoggedEvent(aLight.equalsIgnoreCase("z99") ? "All" : aLight,
				aLight.equalsIgnoreCase("z99") ? "All" : aLight,
				aCommand.equalsIgnoreCase("on"), System.currentTimeMillis(), "User"));
		return null;
	}

	private void callBridge(String theUrl, JsonObject theJsonObject)
	{
		try
		{
			DefaultHttpClient aHttpClient = new DefaultHttpClient();
			HttpPut aPost = new HttpPut(theUrl);
			StringEntity anEntity = new StringEntity(theJsonObject.toString(), HTTP.UTF_8);
			anEntity.setContentType("application/json");
			aPost.setEntity(anEntity);
			aHttpClient.execute(aPost);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
