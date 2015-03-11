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
	 * Create tables
	 */
	public void initializeDatabase()
	{
		createSchedulerTable();
		createLogTable();
	}

	/**
	 * Table schedule
	 *      name
	 *      action
	 *      frequency
	 *      time
	 *
	 */
	private void createSchedulerTable()
	{
		update("CREATE TABLE IF NOT EXISTS schedule " +
				"(name TEXT PRIMARY KEY NOT NULL," +
				"action TEXT NOT NULL," +
				"frequency INT," +
				"time INT)");
	}

	/**
	 * Table logs
	 *      name
	 *      id
	 *      state
	 *      time
	 *      trigger
	 *
	 */
	private void createLogTable()
	{
		update("CREATE TABLE IF NOT EXISTS logs " +
				"(name TEXT NOT NULL," +
				"id TEXT NOT NULL," +
				"state INT," +
				"time INT," +
				"trigger TEXT)");
	}

	public void logEvent(LoggedEvent theEvent)
	{
		update("insert into logs (name, id, state, time, trigger) values (" +
				"'" + theEvent.getName() + "'," +
				"'" + theEvent.getID() + "'," +
				(theEvent.isState() ? 1 : 0) + "," +
				theEvent.getTime() + "," +
				"'" + theEvent.getTrigger() + "')");
	}

	public List<LoggedEvent> getEvents(int theTime)
	{
		final List<LoggedEvent> anItems = new ArrayList<>();
		String aSQL = "select * from logs";
		if (theTime > -1)
		{
			aSQL += " where time >= " + theTime + " AND time < " + (theTime + kMinute);
		}
		query(aSQL, new Mapper()
		{
			@Override
			public void processRow(ResultSet theResultSet) throws SQLException
			{
				anItems.add(new LoggedEvent(theResultSet.getString("name"),
						theResultSet.getString("id"),
						theResultSet.getInt("state") == 1,
						theResultSet.getInt("time"),
						theResultSet.getString("trigger")));
			}
		});
		return anItems;
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
