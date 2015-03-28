package com.jones.matt.lights.servlets;

import com.google.gson.Gson;
import com.jones.matt.lights.HubContext;
import com.jones.matt.lights.controllers.IStatusController;
import com.jones.matt.lights.controllers.ISystemController;
import com.jones.matt.lights.controllers.ITemperatureController;
import com.jones.matt.lights.data.SceneVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Status for all our known controllers returned from this servlet
 */
public class SceneStatusServlet extends AbstractServlet
{
	@Override
	public void process(HttpServletRequest theRequest, HttpServletResponse theResponse) throws ServletException, IOException
	{
		theResponse.setContentType("application/json");
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
		theResponse.getOutputStream().print(new Gson().toJson(aScenes));
		theResponse.getOutputStream().flush();
		theResponse.getOutputStream().close();
	}
}
