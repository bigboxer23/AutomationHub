package com.jones.matt.lights.garage;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.jones.matt.lights.ISystemController;

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

	/*public static void main(String[] theArgs)
	{
		List<String> aParams = new ArrayList<>();
		aParams.add("Status");
		new GarageController().doAction(aParams, null);
	}*/
	@Override
	public String doAction(List<String> theCommands, HttpServletResponse theResponse)
	{
		if(theCommands.size() != 1)
		{
			return "Malformed input " + theCommands.size();
		}
		theResponse.setContentType("application/json; charset=utf-8");
		try
		{
			URLConnection aConnection = new URL(kGarageURL + "/" + theCommands.get(0)).openConnection();
			theResponse.getWriter().print(new String(ByteStreams.toByteArray(aConnection.getInputStream()), Charsets.UTF_8));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
