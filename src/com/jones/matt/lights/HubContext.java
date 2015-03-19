package com.jones.matt.lights;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jones.matt.lights.controllers.ISystemController;
import com.jones.matt.lights.controllers.garage.GarageController;
import com.jones.matt.lights.controllers.hue.HueController;
import com.jones.matt.lights.controllers.scene.DaylightController;
import com.jones.matt.lights.controllers.scene.GenericSceneController;
import com.jones.matt.lights.controllers.scene.WeatherController;
import com.jones.matt.lights.controllers.x10.X10Controller;
import com.jones.matt.lights.data.SceneVO;
import com.jones.matt.scheduler.EventManager;
import com.jones.matt.scheduler.SchedulerDao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Config controlling class.  Reads JSON data from file, initializes scenes from file's contents
 */
public class HubContext
{
	private static Logger myLogger = Logger.getLogger("com.jones");

	private static HubContext myInstance;

	private Map<String, ISystemController> myControllers;

	private List<SceneVO> mySceneVOs;

	private static final String kJSONSource = System.getProperty("scenes.location", "/home/pi/Scenes.json");

	private HubContext()
	{

	}

	public static HubContext getInstance()
	{
		if (myInstance == null)
		{
			myInstance = new HubContext();
		}
		return myInstance;
	}

	/**
	 * Get our mapping of URL's to controllers
	 * If not initialized, trigger that here
	 *
	 * @return
	 */
	public Map<String, ISystemController> getControllers()
	{
		if (myControllers == null)
		{
			myControllers = new HashMap<>();
			EventManager aEventManager = new EventManager();
			aEventManager.setDao(new SchedulerDao());
			X10Controller aX10Controller = new X10Controller();
			HueController aHueController = new HueController();
			GarageController aGarageController = new GarageController(aEventManager);

			try
			{
				mySceneVOs = new Gson().<List<SceneVO>>fromJson(new FileReader(kJSONSource), new TypeToken<List<SceneVO>>(){}.getType());
				for (SceneVO aScene : mySceneVOs)
				{
					myControllers.put(aScene.getSceneUrl(), new GenericSceneController(aX10Controller, aHueController, aEventManager, aScene));
				}
			}
			catch (FileNotFoundException e)
			{
				myLogger.log(Level.SEVERE, "getControllers: can't find JSON file", e);
			}
			myControllers.put("Garage", aGarageController);
			myControllers.put("Weather", new WeatherController(aHueController, aGarageController));
			myControllers.put("Daylight", new DaylightController());
		}
		return myControllers;
	}

	/**
	 * Make us re-initialize
	 */
	public void reset()
	{
		myControllers = null;
	}

	public List<SceneVO> getScenes()
	{
		getControllers();//make sure we're initialized
		return mySceneVOs;
	}
}
