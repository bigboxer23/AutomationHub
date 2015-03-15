package com.jones.matt.lights.scene;

import com.jones.matt.lights.hue.HueController;
import com.jones.matt.lights.x10.X10Controller;
import com.jones.matt.scheduler.EventManager;

/**
 * Controller for bathroom in the house
 */
public class BathroomController extends AbstractSceneController
{
	public BathroomController(X10Controller theX10Controller, HueController theHueController, EventManager theEventManager)
	{
		super(theX10Controller, theHueController, theEventManager);
		myX10Lights.add("a4");
		myHueLights.add("3");
		myHueLights.add("1");
	}

	@Override
	protected String getSceneName()
	{
		return "Bathroom";
	}
}
