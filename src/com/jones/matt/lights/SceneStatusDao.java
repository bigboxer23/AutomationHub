package com.jones.matt.lights;

import com.jones.matt.lights.controllers.IStatusController;
import com.jones.matt.lights.controllers.ISystemController;
import com.jones.matt.lights.controllers.ITemperatureController;
import com.jones.matt.lights.data.SceneVO;
import com.jones.matt.scheduler.IConstants;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * TimerTask object to fetch status and cache locally so we don't have to query at "runtime"
 */
public class SceneStatusDao extends TimerTask implements IConstants, ServletContextListener
{
	private Timer myTimer;

	private static Map<String, SceneVO> myScenes = new HashMap<>();

	private static Logger myLogger = Logger.getLogger("com.jones");

	@Override
	public void contextInitialized(ServletContextEvent theServletContextEvent)
	{
		myLogger.config("contextInitialized");
		if (myTimer != null)
		{
			myTimer.cancel();
		}

		myTimer = new Timer();
		myTimer.scheduleAtFixedRate(this, 0, kSecond * 5);
	}

	@Override
	public void contextDestroyed(ServletContextEvent theServletContextEvent)
	{
		myLogger.config("contextDestroyed");
		myTimer.cancel();
	}

	/**
	 * Check every minute for new actions to execute
	 */
	@Override
	public void run()
	{
		Map<String, SceneVO> aScenes = new HashMap<>();
		for (SceneVO aScene : HubContext.getInstance().getScenes())
		{
			ISystemController aController = HubContext.getInstance().getControllers().get(aScene.getSceneUrl());
			if (aController != null)
			{
				if (aController instanceof IStatusController)
				{
					aScene.setStatus(((IStatusController)aController).getStatus(-1));
				}
				if (aController instanceof ITemperatureController)
				{
					aScene.setWeatherData(((ITemperatureController) aController).getWeatherData());
				}
				aScenes.put(aScene.getSceneUrl(), aScene);
			}
		}
		myScenes = aScenes;
	}

	public static Map<String, SceneVO> getScenes()
	{
		return myScenes;
	}
}
