package com.jones.matt.lights;

import com.jones.matt.lights.garage.GarageController;
import com.jones.matt.lights.hue.HueController;
import com.jones.matt.lights.scene.*;
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
		X10Controller aX10Controller = new X10Controller();
		HueController aHueController = new HueController();
		GarageController aGarageController = new GarageController();
		myControllers = new HashMap<>();
		myControllers.put("X", aX10Controller);
		myControllers.put("G", aGarageController);
		myControllers.put("H", aHueController);
		myControllers.put("LivingRoom", new LivingRoomController(aX10Controller, aHueController));
		myControllers.put("BathRoom", new BathroomController(aX10Controller, aHueController));
		myControllers.put("AllLights", new AllLightsController(aX10Controller, aHueController));
		myControllers.put("Weather", new WeatherController(aHueController, aGarageController));
		myControllers.put("Daylight", new DaylightController());

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
		String aJsonResponse = aController.doAction(Arrays.<String>asList(Arrays.copyOfRange(anArgs, 1, anArgs.length)));
		if (aJsonResponse != null)
		{
			theResponse.setContentType("application/json; charset=utf-8");
			theResponse.getWriter().print(aJsonResponse);
		}
		theResponse.setStatus(HttpServletResponse.SC_OK);
	}
}
