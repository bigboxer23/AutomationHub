package com.jones.matt.lights.servlets;

import com.jones.matt.lights.HubContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Trigger the main servlet to reload config on next request
 */
public class LoadServlet extends AbstractServlet
{
	@Override
	public void process(HttpServletRequest theRequest, HttpServletResponse theResponse) throws ServletException, IOException
	{
		HubContext.getInstance().reset();
	}
}
