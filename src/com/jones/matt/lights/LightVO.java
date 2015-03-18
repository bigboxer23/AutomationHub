package com.jones.matt.lights;

import com.google.gson.annotations.SerializedName;

/**
 * Encapsulate a singular light including it's type, id, and brightness on action
 */
public class LightVO
{
	@SerializedName("type")
	private String myType;

	@SerializedName("id")
	private int myId;

	@SerializedName("brightness")
	private int myBrightness;

	public String getType()
	{
		return myType;
	}

	public void setType(String theType)
	{
		myType = theType;
	}

	public int getId()
	{
		return myId;
	}

	public void setId(int theId)
	{
		myId = theId;
	}

	public int getBrightness()
	{
		return myBrightness;
	}
}
