package com.jones.matt.lights.scene;

import com.jones.matt.lights.hue.HueController;
import com.jones.matt.lights.x10.X10Controller;

/**
 * Controller for bathroom in the house
 */
public class BathroomController extends AbstractSceneController
{
	public BathroomController(X10Controller theX10Controller, HueController theHueController)
	{
		super(theX10Controller, theHueController);
		myX10Lights.add("a4");
		myHueLights.add("3");
		myHueLights.add("1");
	}
}
