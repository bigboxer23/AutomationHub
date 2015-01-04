package com.jones.matt.lights.scene;

import com.jones.matt.lights.ISystemController;
import com.jones.matt.lights.hue.HueController;
import com.jones.matt.lights.x10.X10Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Controller to tie together x10 and hue lights as a single RESTful call
 */
public abstract class AbstractSceneController implements ISystemController
{
	protected static Logger myLogger = Logger.getLogger("com.jones");

	protected X10Controller myX10Controller;

	protected HueController myHueController;

	protected List<String> myX10Lights;

	protected List<String> myHueLights;

	public AbstractSceneController(X10Controller theX10Controller, HueController theHueController)
	{
		myX10Controller = theX10Controller;
		myHueController = theHueController;
		myX10Lights = new ArrayList<>();
		myHueLights = new ArrayList<>();
	}

	@Override
	public String doAction(List<String> theCommands)
	{
		String aCommand = theCommands.get(0);
		if (aCommand.equalsIgnoreCase("off"))
		{
			doCommand("off");
		} else if (aCommand.equalsIgnoreCase("on"))
		{
			doCommand("on");
		}
		return null;
	}

	/**
	 * Iterate our list of x10 and hue lights
	 * @param theCommand
	 * @param theResponse
	 */
	private void doCommand(String theCommand)
	{
		for (String aLights : myX10Lights)
		{
			List<String> aCommands = new ArrayList<>();
			aCommands.add(aLights);
			aCommands.add(theCommand);
			myX10Controller.doAction(aCommands);
		}
		for (String aLights : myHueLights)
		{
			List<String> aCommands = new ArrayList<>();
			aCommands.add(aLights);
			aCommands.add(theCommand);
			myHueController.doAction(aCommands);
		}
	}
}
