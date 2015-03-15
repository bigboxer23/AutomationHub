package com.jones.matt.lights.scene;

import com.jones.matt.lights.AbstractBaseController;
import com.jones.matt.lights.ISystemController;
import com.jones.matt.lights.hue.HueController;
import com.jones.matt.lights.x10.X10Controller;
import com.jones.matt.scheduler.EventManager;
import com.jones.matt.scheduler.LoggedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller to tie together x10 and hue lights as a single RESTful call
 */
public abstract class AbstractSceneController extends AbstractBaseController implements ISystemController
{
	protected X10Controller myX10Controller;

	protected HueController myHueController;

	protected List<String> myX10Lights;

	protected List<String> myHueLights;

	/**
	 * Set this to value 0-255 to set the hue's brightness when we turn them on
	 */
	protected String myHueBrightness;

	public AbstractSceneController(X10Controller theX10Controller, HueController theHueController, EventManager theEventManager)
	{
		super(theEventManager);
		myX10Controller = theX10Controller;
		myHueController = theHueController;
		myX10Lights = new ArrayList<>();
		myHueLights = new ArrayList<>();
	}

	/**
	 * Should be the name of the scene for logging purposes
	 *
	 * @return
	 */
	protected abstract String getSceneName();

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
		logEvent(new LoggedEvent(getSceneName(), getSceneName(),
				aCommand.equalsIgnoreCase("on"), System.currentTimeMillis(), "User"));
		return null;
	}

	/**
	 * Iterate our list of x10 and hue lights
	 * @param theCommand
	 * @param theResponse
	 */
	private void doCommand(String theCommand)
	{
		for (String aLight : myHueLights)
		{
			List<String> aCommands = new ArrayList<>();
			aCommands.add(aLight);
			aCommands.add(theCommand);
			String aBrightness = getBrightness(aLight);
			if (aBrightness != null)
			{
				aCommands.add(aBrightness);
			}
			myHueController.doAction(aCommands);
		}
		for (String aLights : myX10Lights)
		{
			List<String> aCommands = new ArrayList<>();
			aCommands.add(aLights);
			aCommands.add(theCommand);
			myX10Controller.doAction(aCommands);
		}
	}

	/**
	 * Allow subclass to set brightness specific to light
	 *
	 * @param theLight
	 * @return
	 */
	protected String getBrightness(String theLight)
	{
		return myHueBrightness;
	}
}
