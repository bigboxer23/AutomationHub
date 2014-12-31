package com.jones.matt.scheduler.actions;

import com.google.gson.annotations.SerializedName;
import com.jones.matt.scheduler.IConstants;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * action to turn off a list of actions
 */
public class Action
{
	/**
	 * Urls to call to do something to lights
	 */
	@SerializedName("lights")
	private List<String> myLights = new ArrayList<>();

	public Action(List<String> theLights)
	{
		myLights.addAll(theLights);
	}

	public void doAction()
	{
		for (String aUrl : myLights)
		{
			try
			{
				DefaultHttpClient aHttpClient = new DefaultHttpClient();
				aHttpClient.execute(new HttpGet(aUrl));
				Thread.sleep(IConstants.kX10Delay);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

	}
}
