package com.jones.matt.lights.scene;

import com.jones.matt.lights.hue.HueController;
import com.jones.matt.lights.x10.X10Controller;
import com.jones.matt.scheduler.EventManager;

/**
 * controller binding to turn all lights on/off in the house
 */
public class AllLightsController extends AbstractSceneController
{
	public AllLightsController(X10Controller theX10Controller, HueController theHueController, EventManager theEventManager)
	{
		super(theX10Controller, theHueController, theEventManager);
		myX10Lights.add("z99");
		myHueLights.add("z99");
	}

	@Override
	protected String getSceneName()
	{
		return "All";
	}
}
