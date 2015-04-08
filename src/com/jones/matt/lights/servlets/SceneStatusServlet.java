package com.jones.matt.lights.servlets;

import com.google.gson.Gson;
import com.jones.matt.lights.SceneStatusDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Status for all our known controllers returned from this servlet
 */
public class SceneStatusServlet extends AbstractServlet
{
	@Override
	public void process(HttpServletRequest theRequest, HttpServletResponse theResponse) throws ServletException, IOException
	{
		theResponse.setContentType("application/json");
		theResponse.getOutputStream().print(new Gson().toJson(SceneStatusDao.getScenes()));
		theResponse.getOutputStream().flush();
		theResponse.getOutputStream().close();
	}
}
