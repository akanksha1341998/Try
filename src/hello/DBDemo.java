package hello;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBDemo {

	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "root";

	/** The name of the computer running MySQL */
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/** The name of the database we are testing with (this default is installed with MySQL) */
	private final String dbName = "Project";
	
	/** The name of the table we are testing with */
	private final String tableName = "USER_INFO";
	
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName,
				connectionProps);

		return conn;
	}

	public boolean executeUpdate(Connection conn, String command) throws SQLException {
	    Statement stmt = null;
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); // This will throw a SQLException if it fails
	        return true;
	    } finally {

	    	// This will run whether we throw an exception or not
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	
	public void run() {

		// Connect to MySQL
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}

		// Create a table
		try {
		    String createString =
			        "CREATE TABLE " + this.tableName + " ( " +
			        "USER_ID varchar(100) NOT NULL, " +
			        "KEY_ID varchar(100) NOT NULL, " +
			        "UNAME varchar(100) NOT NULL UNIQUE, " +
			        "PASS varchar(100) NOT NULL, UNIQUE " +
			        "KEY_ALGO varchar(100) NOT NULL, " +
			        "KEY_TYPE varchar(100) NOT NULL, " +
			        "PUB_KEY_FILE TEXT NOT NULL, " +
			        "PRI_KEY_FILE TEXT NOT NULL, " +
			        "PRIMARY KEY (USER_ID))";
			this.executeUpdate(conn, createString);
			System.out.println("Created a table");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}
		
		// Drop the table
//		try {
//		    String dropString = "DROP TABLE " + this.tableName;
//			this.executeUpdate(conn, dropString);
//			System.out.println("Dropped the table");
//	    } catch (SQLException e) {
//			System.out.println("ERROR: Could not drop the table");
//			e.printStackTrace();
//			return;
//		}
	}
	
	
	 //Connect to the DB and do some stuff
	 
	public static void main(String[] args) {
		DBDemo app = new DBDemo();
		app.run();
	}
}