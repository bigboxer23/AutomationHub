package com.jones.matt.lights;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Encapsulate a "scene" (or room whatevs)
 */
public class SceneVO
{
	@SerializedName("name")
	private String mySceneName;

	@SerializedName("lights")
	private List<LightVO> myLights;

	public String getSceneName()
	{
		return mySceneName;
	}

	public List<LightVO> getLights()
	{
		return myLights;
	}
}
