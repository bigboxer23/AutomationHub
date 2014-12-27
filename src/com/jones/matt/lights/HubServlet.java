package com.jones.matt.lights;

import com.jones.matt.lights.garage.GarageController;
import com.jones.matt.lights.x10.X10Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class HubServlet extends HttpServlet
{
	private static String kServletPrefix = "Lights/";

	private static Map<String, ISystemController> myControllers;

	@Override
	public void init()
	{
		myControllers = new HashMap<>();
		myControllers.put("X", new X10Controller());
		myControllers.put("G", new GarageController());
	}

	@Override
	public void doPost(HttpServletRequest theReq, HttpServletResponse theRes) throws ServletException, IOException
	{
		doGet(theReq, theRes);
	}

	@Override
	public void doGet(HttpServletRequest theRequest, HttpServletResponse theResponse) throws ServletException, IOException
	{
		String aURI = theRequest.getRequestURI();
		aURI = aURI.substring(aURI.indexOf(kServletPrefix) + kServletPrefix.length());
		String[] anArgs = aURI.split("/");
		if(anArgs.length < 2)
		{
			theResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Malformed input " + anArgs.length);
			return;
		}
		ISystemController aController = myControllers.get(anArgs[0]);
		if (aController == null)
		{
			theResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "No controller specified");
			return;
		}
		String anError = aController.doAction(Arrays.<String>asList(Arrays.copyOfRange(anArgs, 1, anArgs.length)), theResponse);
		if (anError != null)
		{
			theResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, anError);
			return;
		}
		theResponse.setStatus(HttpServletResponse.SC_OK);
	}
}
