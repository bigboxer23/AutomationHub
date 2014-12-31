package com.jones.matt.sql;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * wrapper dao for basic connection, SQL specific items
 */
public class BaseDao
{
	private final static String kDatabaseName = "scheduler.db";

	protected static Logger myLogger = Logger.getLogger("com.jones");

	/**
	 * Query an sql string
	 *
	 * @param theSQL
	 * @param theMapper
	 */
	protected void query(String theSQL, Mapper theMapper)
	{
		myLogger.fine(theSQL);
		Connection aConnection = null;
		Statement aStatement = null;
		try {
			aConnection = getConnection();
			aConnection.setAutoCommit(false);
			aStatement = aConnection.createStatement();
			ResultSet aResultSet = aStatement.executeQuery(theSQL);
			while (aResultSet.next())
			{
				theMapper.processRow(aResultSet);
			}
			aResultSet.close();
			aStatement.close();
			aConnection.close();
		} catch ( Exception e )
		{
			myLogger.log(Level.WARNING, "query", e);
		}
	}

	/**
	 * perform an sql update with the given string
	 *
	 * @param theSQL
	 */
	protected void update(String theSQL)
	{
		myLogger.fine(theSQL);
		Connection aConnection = null;
		Statement aStatement = null;
		try {
			aConnection = getConnection();
			aStatement = aConnection.createStatement();
			aStatement.executeUpdate(theSQL);
			aStatement.close();
			aConnection.close();
		} catch (Exception e)
		{
			myLogger.log(Level.WARNING, "update", e);
		}
	}

	private Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection("jdbc:sqlite:" + kDatabaseName);
	}
}
