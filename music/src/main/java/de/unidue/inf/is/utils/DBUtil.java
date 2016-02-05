package de.unidue.inf.is.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.ibm.db2.jcc.DB2Driver;



public final class DBUtil {

    private DBUtil() {
    }


    static {
        com.ibm.db2.jcc.DB2Driver driver = new DB2Driver();
        try {
            DriverManager.registerDriver(driver);
        }
        catch (SQLException e) {
            throw new Error("Laden des Datenbanktreiber nicht möglich");
        }
    }


    public static Connection getConnection(String database) throws SQLException {
        final String url = "jdbc:db2://localhost:50000/" + database;
        final String user="db2inst2";
        final String password="1987";
        return DriverManager.getConnection(url,user,password);
    }


    public static Connection getExternalConnection(String database) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("securityMechanism",
                        Integer.toString(com.ibm.db2.jcc.DB2BaseDataSource.USER_ONLY_SECURITY));
        properties.setProperty("user", "dblabXX");
        properties.setProperty("password", "passwort");

        final String url = "jdbc:db2://<rechnername>.is.inf.uni-due.de:50005/" + database + ":currentSchema=<dblabXX>;";
        System.out.println(url);
        Connection connection = DriverManager.getConnection(url, properties);
        return connection;
    }


    
    public static ResultSet LoadOnQuery(String s){
    	final String url = "jdbc:db2://localhost:50000/try";
        final String user="db2inst2";
        final String password="1987";
    	Connection con = null;
		try {
			con = DriverManager.getConnection(url,user,password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try{
    		PreparedStatement Stmt=con.prepareStatement(s);
    		Stmt.executeQuery();
        	ResultSet rs=Stmt.getResultSet();
        	while(rs.next())
        	{ return rs;}
    	}catch( Exception e ) 
    	{ e . printStackTrace ( ) ;
    		 System.out.println(e);
    		 ;
    		 }
    	return null;
    }
    public static boolean checkDatabaseExistsExternal(String database) {
        // Nur für Demozwecke!
        boolean exists = false;

        try (Connection connection = getExternalConnection(database)) {
            exists = true;
        }
        catch (SQLException e) {
            exists = false;
            e.printStackTrace();
        }

        return exists;
    }


    public static boolean checkDatabaseExists(String database) {
        // Nur für Demozwecke!
        boolean exists = false;

        try (Connection connection = getConnection(database)) {
            exists = true;
        }
        catch (SQLException e) {
            exists = false;
            e.printStackTrace();
        }

        return exists;
    }

}
