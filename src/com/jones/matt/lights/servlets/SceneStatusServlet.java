package com.jones.matt.lights.servlets;

import com.google.gson.Gson;
import com.jones.matt.lights.HubContext;
import com.jones.matt.lights.controllers.IStatusController;
import com.jones.matt.lights.controllers.ISystemController;
import com.jones.matt.lights.data.SceneVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class SceneStatusServlet extends AbstractServlet
{
	@Override
	public void process(HttpServletRequest theRequest, HttpServletResponse theResponse) throws ServletException, IOException
	{
		theResponse.setContentType("application/json");
		for (SceneVO aScene : HubContext.getInstance().getScenes())
		{
			ISystemController aController = HubContext.getInstance().getControllers().get(aScene.getSceneUrl());
			if (aController != null && aController instanceof IStatusController)
			{
				aScene.setStatus(((IStatusController)aController).getStatus(-1));
			}
		}
		theResponse.getOutputStream().print(new Gson().toJson(HubContext.getInstance().getScenes()));
		theResponse.getOutputStream().flush();
		theResponse.getOutputStream().close();
	}
}
