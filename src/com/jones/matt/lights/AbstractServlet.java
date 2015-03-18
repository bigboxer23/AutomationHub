package com.jones.matt.lights;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Push GET/POST through common method
 */
public abstract  class AbstractServlet extends HttpServlet
{
	@Override
	public final void doPost(HttpServletRequest theRequest, HttpServletResponse theResponse) throws ServletException, IOException
	{
		process(theRequest, theResponse);
	}

	@Override
	public final void doGet(HttpServletRequest theRequest, HttpServletResponse theResponse) throws ServletException, IOException
	{
		process(theRequest, theResponse);
	}

	public abstract void process(HttpServletRequest theRequest, HttpServletResponse theResponse) throws ServletException, IOException;
}
