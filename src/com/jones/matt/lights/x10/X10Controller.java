package com.jones.matt.lights.x10;

import com.jones.matt.lights.ISystemController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 *
 */
public class X10Controller implements ISystemController
{
	/**
	 * Port used to access mochad
	 * http://sourceforge.net/projects/mochad/
	 */
	private static int kMochadPort = Integer.getInteger("mochadPort", 1099);

	private static String kMochadHost = System.getProperty("mochadhost", "localhost");

	/**
	 * X-10 controller type of command to use
	 *
	 * If using a CM15A or CM15Pro use "pl"
	 *
	 * If using a CM19A use "rf"
	 */
	private static String kControllerType = System.getProperty("controllerType", "rf");

	@Override
	public String doAction(List<String> theCommands, HttpServletResponse theResponse)
	{
		if(theCommands.size() != 2)
		{
			return "Malformed input " + theCommands.size();
		}
		String aDevice = theCommands.get(0);
		String anAction = theCommands.get(1);
		if (!isValid(aDevice) || !isValid(anAction))
		{
			return "Malformed input";
		}
		Socket aSocket = null;
		try
		{
			aSocket = new Socket(kMochadHost, kMochadPort);
			DataOutputStream anOutputStream = new DataOutputStream(aSocket.getOutputStream());
			anOutputStream.writeBytes(kControllerType + " " + aDevice + " " + anAction + '\n');
			aSocket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return "Error calling mochad.";
		}
		return null;
	}

	private boolean isValid(String theValue)
	{
		return theValue != null && (theValue.length() == 2 || theValue.length() == 3);
	}
}
