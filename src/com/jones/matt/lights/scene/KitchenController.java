package com.jones.matt.lights.scene;

import com.jones.matt.lights.hue.HueController;
import com.jones.matt.lights.x10.X10Controller;

/**
 * Controller for the kitchen, linking X10 + Hue lights
 */
public class KitchenController extends AbstractSceneController
{
	public KitchenController(X10Controller theX10Controller, HueController theHueController)
	{
		super(theX10Controller, theHueController);
		myX10Lights.add("a3");
		myHueLights.add("5");
		myHueLights.add("6");
		myHueLights.add("7");
		myHueBrightness = "200";
	}
}
