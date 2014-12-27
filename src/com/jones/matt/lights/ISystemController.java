package com.jones.matt.lights;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 */
public interface ISystemController
{
	public String doAction(List<String> theCommands, HttpServletResponse theResponse);
}
