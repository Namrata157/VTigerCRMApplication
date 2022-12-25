package genericLibraries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.Driver;

/**
 * This class contains methods to perorm database related operations
 * @author sncsr
 *
 */

public class DatabaseUtility {
	private Connection connection;
	
	/**
	 * This method establishes database connection
	 * @param databaseUrl 
	 * @param dbName 
	 * @param dbPassword 
	 * @throws SQLException 
	 */

	public void openDatabaseConnection(String databaseUrl, String dbName, String dbPassword) throws SQLException {
		Driver dbDriver = new Driver();
		DriverManager.registerDriver(dbDriver);
		connection = DriverManager.getConnection(databaseUrl,dbName,dbPassword);
	}
	
	/**
	 * This method is used to fetch data from database
	 * @param query 
	 * @param columnName 
	 * @param columnName2 
	 * @throws SQLException 
	 */
	public List<String> fetchDataFromDatabase(String query, String columnName1, String columnName2) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(query);
		List<String> list1 = new ArrayList<>();
		//List<Integer> list2 = new ArrayList<>();
		while(result.next()) {
			list1.add(result.getString(columnName1));
			//list2.add(result.getInt(columnName2));
		}
		return list1;
	}
	
	/**
	 * This method is used to modify the database
	 * @param query 
	 * @throws SQLException 
	 */
	public int modifyDatabase(String query) throws SQLException {
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate(query);
		return result;
	}
	
	/**
	 * This method is used to close the database
	 * @throws SQLException 
	 */
	public void closeDatabase() throws SQLException {
		connection.close();
	}
	
}
