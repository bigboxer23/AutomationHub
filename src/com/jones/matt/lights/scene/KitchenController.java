package com.jones.matt.lights.scene;

import com.jones.matt.lights.hue.HueController;
import com.jones.matt.lights.x10.X10Controller;
import com.jones.matt.scheduler.EventManager;

/**
 * Controller for the kitchen, linking X10 + Hue lights
 */
public class KitchenController extends AbstractSceneController
{
	public KitchenController(X10Controller theX10Controller, HueController theHueController, EventManager theEventManager)
	{
		super(theX10Controller, theHueController, theEventManager);
		myX10Lights.add("a3");
		myHueLights.add("5");
		myHueLights.add("6");
		myHueLights.add("7");
		myHueLights.add("10");
		myHueLights.add("11");
		myHueBrightness = "200";
	}

	@Override
	protected String getSceneName()
	{
		return "Kitchen";
	}

	@Override
	protected String getBrightness(String theLight)
	{
		return (theLight.equals("10") || theLight.equals("11")) ? null : super.getBrightness(theLight);
	}
}
