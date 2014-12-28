package com.jones.matt.lights.scene;

import com.jones.matt.lights.ISystemController;
import com.jones.matt.lights.hue.HueController;
import com.jones.matt.lights.x10.X10Controller;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the living room, turns x10 off/on, hue off/on/into movie mode
 */
public class LivingRoomController implements ISystemController
{
	private X10Controller myX10Controller;
	private HueController myHueController;

	public LivingRoomController(X10Controller theX10Controller, HueController theHueController)
	{
		myX10Controller = theX10Controller;
		myHueController = theHueController;
	}
	@Override
	public String doAction(List<String> theCommands, HttpServletResponse theResponse)
	{
		String aCommand = theCommands.get(0);
		if (aCommand.equalsIgnoreCase("off"))
		{
			List<String> aCommands = new ArrayList<>();
			aCommands.add("a2");
			aCommands.add("off");
			myX10Controller.doAction(aCommands, theResponse);
			aCommands.clear();
			aCommands.add("4");
			aCommands.add("off");
			myHueController.doAction(aCommands, theResponse);
		} else if (aCommand.equalsIgnoreCase("on"))
		{
			List<String> aCommands = new ArrayList<>();
			aCommands.add("a2");
			aCommands.add("on");
			myX10Controller.doAction(aCommands, theResponse);
			aCommands.clear();
			aCommands.add("4");
			aCommands.add("on");
			myHueController.doAction(aCommands, theResponse);
		} else if (aCommand.equalsIgnoreCase("movie"))
		{
			List<String> aCommands = new ArrayList<>();
			aCommands.add("a2");
			aCommands.add("off");
			myX10Controller.doAction(aCommands, theResponse);
			aCommands.clear();
			aCommands.add("4");
			aCommands.add("movie");
			myHueController.doAction(aCommands, theResponse);
		}
		return null;
	}
}
