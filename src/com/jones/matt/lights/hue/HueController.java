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
		try
		{
			String aDevice = theCommands.get(0);
			String aCommand = theCommands.get(1);
			DefaultHttpClient aHttpClient = new DefaultHttpClient();
			HttpPut aPost = new HttpPut("http://" + kHueBridge + "/api/" + kUserName + "/lights/" + aDevice + "/state");
			JsonObject aJsonElement = new JsonObject();
			if (aCommand.equalsIgnoreCase("off"))
			{
				aJsonElement.addProperty("on", false);
			} else if(aCommand.equalsIgnoreCase("on"))
			{
				aJsonElement.addProperty("on", true);
				aJsonElement.addProperty("bri", 255);
			}
			StringEntity anEntity = new StringEntity(aJsonElement.toString(), HTTP.UTF_8);
			anEntity.setContentType("application/json");
			aPost.setEntity(anEntity);
			aHttpClient.execute(aPost);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
