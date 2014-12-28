package com.jones.matt.lights.hue;

import com.google.gson.JsonObject;
import com.jones.matt.lights.ISystemController;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * controller for a philips hue light system
 */
public class HueController implements ISystemController
{
	/**
	 * Username to access lights with
	 */
	private static String kUserName = System.getProperty("hueUserName", "test");

	/**
	 * Hue bridge address
	 */
	private static String kHueBridge = System.getProperty("hueBridge", "localhost");

	@Override
	public String doAction(List<String> theCommands, HttpServletResponse theResponse)
	{
		String aCommand = theCommands.get(1);
		JsonObject aJsonElement = new JsonObject();
		String aUrl = "http://" + kHueBridge + "/api/" + kUserName + "/lights/" + theCommands.get(0) + "/state";
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
			aJsonElement.addProperty("bri", 45);
			aJsonElement.addProperty("colormode", "ct");
			aJsonElement.addProperty("ct", 424);
		}
		callBridge(aUrl, aJsonElement);
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
