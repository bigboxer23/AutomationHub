package com.jones.matt.lights.x10;

import com.jones.matt.lights.AbstractBaseController;
import com.jones.matt.lights.ISystemController;
import com.jones.matt.scheduler.EventManager;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;

/**
 *
 */
public class X10Controller extends AbstractBaseController implements ISystemController
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

	public X10Controller(EventManager theEventManager)
	{
		super(theEventManager);
	}

	@Override
	public String doAction(List<String> theCommands)
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
		if(aDevice.equalsIgnoreCase("z99"))
		{
			for (int ai = 2; ai <= 5; ai++)
			{
				callMochad("a" + ai, anAction);
				try
				{
					//Sending commands fast one after another seems to not work great (seems like limitation on cm19 controller)
					Thread.sleep(2000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			return null;
		}
		return callMochad(aDevice, anAction);
	}

	private String callMochad(String theDevice, String theAction)
	{
		try
		{
			Socket aSocket = new Socket(kMochadHost, kMochadPort);
			DataOutputStream anOutputStream = new DataOutputStream(aSocket.getOutputStream());
			anOutputStream.writeBytes(kControllerType + " " + theDevice + " " + theAction + "\n");
			anOutputStream.flush();
			anOutputStream.close();
			aSocket.close();
		}
		catch (IOException e)
		{
			myLogger.log(Level.WARNING, "X10Controller", e);
			return "Error calling mochad.";
		}
		return null;
	}
	private boolean isValid(String theValue)
	{
		return theValue != null && (theValue.length() == 2 || theValue.length() == 3);
	}
}
