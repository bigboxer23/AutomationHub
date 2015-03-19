package com.jones.matt.lights.controllers.scene;

import com.jones.matt.lights.controllers.IStatusController;
import com.jones.matt.lights.controllers.ISystemController;
import com.jones.matt.lights.data.LightVO;
import com.jones.matt.lights.data.SceneVO;
import com.jones.matt.lights.controllers.hue.HueController;
import com.jones.matt.lights.controllers.x10.X10Controller;
import com.jones.matt.scheduler.EventManager;
import com.jones.matt.scheduler.LoggedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Controller for a scene (or room) consisting of multi lights, potentially
 * from multiple home automation ecosystems
 */
public class GenericSceneController implements ISystemController, IStatusController
{
	/**
	 * Controller for X10
	 */
	private X10Controller myX10Controller;

	/**
	 * Controller for HUE or zigbee
	 */
	private HueController myHueController;

	private EventManager myEventManager;

	private SceneVO myScene;

	private static Logger myLogger = Logger.getLogger("com.jones");

	public GenericSceneController(X10Controller theX10Controller, HueController theHueController, EventManager theEventManager, SceneVO theSceneVO)
	{
		myEventManager = theEventManager;
		myHueController = theHueController;
		myX10Controller = theX10Controller;
		myScene = theSceneVO;
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
		} else if (aCommand.equalsIgnoreCase("movie"))
		{
			doCommand("movie");
		}
		logEvent(new LoggedEvent(myScene.getSceneName(), myScene.getSceneName(),
				aCommand.equalsIgnoreCase("on"), System.currentTimeMillis(), "User"));
		return null;
	}

	public boolean getStatus()
	{
		return true;//TODO:
	}

	private void doCommand(String theCommand)
	{
		for (LightVO aLight : myScene.getLights())
		{
			if ("movie".equalsIgnoreCase(theCommand))
			{
				theCommand = aLight.getMovieModeAction();
			}
			if (theCommand != null && !theCommand.equalsIgnoreCase(""))
			{
				List<String> aCommands = new ArrayList<>();
				aCommands.add("" + aLight.getId());
				aCommands.add(theCommand);
				if (aLight.getBrightness() > 0)
				{
					aCommands.add("" + aLight.getBrightness());
				}
				getController(aLight.getType()).doAction(aCommands);
			}
		}
	}

	/**
	 * Get the appropriate controller based on the type
	 *
	 * @param theType
	 * @return
	 */
	private ISystemController getController(String theType)
	{
		if ("h".equalsIgnoreCase(theType))
		{
			return myHueController;
		} else if("x".equalsIgnoreCase(theType))
		{
			return myX10Controller;
		}
		return null;
	}

	/**
	 * Log event to the db
	 *
	 * @param theEvent
	 */
	public void logEvent(LoggedEvent theEvent)
	{
		myLogger.info("logging event: " + theEvent.toString());
		myEventManager.logEvent(theEvent);
	}
}
