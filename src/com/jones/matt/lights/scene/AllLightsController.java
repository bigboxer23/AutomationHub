package com.jones.matt.lights.scene;

import com.jones.matt.lights.hue.HueController;
import com.jones.matt.lights.x10.X10Controller;

/**
 * controller binding to turn all lights on/off in the house
 */
public class AllLightsController extends AbstractSceneController
{
	public AllLightsController(X10Controller theX10Controller, HueController theHueController)
	{
		super(theX10Controller, theHueController);
		myX10Lights.add("z99");
		myHueLights.add("1");
		myHueLights.add("2");
		myHueLights.add("3");
		myHueLights.add("4");
	}
}
