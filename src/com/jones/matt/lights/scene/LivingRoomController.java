package com.jones.matt.lights.scene;

import com.jones.matt.lights.hue.HueController;
import com.jones.matt.lights.x10.X10Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the living room, turns x10 off/on, hue off/on/into movie mode
 */
public class LivingRoomController extends AbstractSceneController
{
	public LivingRoomController(X10Controller theX10Controller, HueController theHueController)
	{
		super(theX10Controller, theHueController);
		myX10Lights.add("a2");
		myHueLights.add("4");
	}

	@Override
	public String doAction(List<String> theCommands)
	{
		String aCommand = theCommands.get(0);
		if (aCommand.equalsIgnoreCase("movie"))
		{
			List<String> aCommands = new ArrayList<>();
			aCommands.add("a2");
			aCommands.add("off");
			myX10Controller.doAction(aCommands);
			aCommands.clear();
			aCommands.add("4");
			aCommands.add("movie");
			myHueController.doAction(aCommands);
			return null;
		}
		return super.doAction(theCommands);
	}
}
