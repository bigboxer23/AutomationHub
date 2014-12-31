package com.jones.matt.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mapper for processing a singular row in a result set so we don't have to write
 * iteration code
 */
public interface Mapper
{
	public void processRow(ResultSet theResultSet) throws SQLException;
}
