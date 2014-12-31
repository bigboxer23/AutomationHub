package com.jones.matt.scheduler;

import com.jones.matt.sql.BaseDao;
import com.jones.matt.sql.Mapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * dao for getting information from our database
 */
public class SchedulerDao extends BaseDao implements IConstants
{
	/**
	 * Create table
	 */
	public void initializeDatabase()
	{
		update("CREATE TABLE IF NOT EXISTS schedule " +
				"(name TEXT PRIMARY KEY NOT NULL," +
				"action TEXT NOT NULL," +
				"frequency INT," +
				"time INT)");
	}

	/**
	 * Add the event to the database
	 *
	 * @param theEvent
	 */
	public void addEvent(Event theEvent)
	{
		update("insert into schedule (name, action, frequency, time) values (" +
				"'" + theEvent.getName() + "'," +
				"'" + theEvent.getActionString() + "'," +
				"'" + theEvent.getFrequency() + "'," +
				theEvent.getTime() + ")");
	}

	/**
	 * Delete the event from the db
	 *
	 * @param theName
	 */
	public void deleteEvent(String theName)
	{
		update("delete from schedule where name = '" + theName + "'");
	}

	/**
	 * Get a list of items from the database that match the timerange
	 *
	 * @param theTime
	 * @return
	 */
	public List<Event> getItems(int theTime)
	{
		final List<Event> anItems = new ArrayList<>();
		String aSQL = "select * from schedule";
		if (theTime > -1)
		{
			aSQL += " where time >= " + theTime + " AND time < " + (theTime + kMinute);
		}
		query(aSQL, new Mapper()
		{
			@Override
			public void processRow(ResultSet theResultSet) throws SQLException
			{
				anItems.add(new Event(theResultSet.getString("name"),
						theResultSet.getString("action"),
						theResultSet.getInt("frequency"),
						theResultSet.getInt("time")));
			}
		});
		return anItems;
	}
}
