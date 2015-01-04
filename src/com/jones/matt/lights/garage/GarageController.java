package com.jones.matt.lights.garage;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.jones.matt.lights.ISystemController;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 *
 */
public class GarageController implements ISystemController
{
	private static String kGarageURL = System.getProperty("garageURL", "http://192.168.0.8");

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
		return null;
	}

	/**
	 * Fetch data about the weather from the sensor in the garage
	 *
	 * @return
	 * @throws IOException
	 */
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
}
