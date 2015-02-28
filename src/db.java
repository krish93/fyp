/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harikrish
 */
import java.sql.*;
public class db {

    /**
     * @param args the command line arguments
     */
    db()
            {
    String dbName = "cpabe";
        String userName = "cpabe";
        String password = "SAASwebteam";
        String hostname = "fyp.cpbqet3zj9k7.ap-southeast-1.rds.amazonaws.com";
        String port = "3306";
  String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
    port + "/" + dbName + "?user=" + userName + "&password=" + password;
  
  // Load the JDBC Driver
  try {
    System.out.println("Loading driver...");
    Class.forName("com.mysql.jdbc.Driver");
    System.out.println("Driver loaded!");
  } catch (ClassNotFoundException e) {
    throw new RuntimeException("Cannot find the driver in the classpath!", e);
  }

  Connection conn = null;
  Statement setupStatement = null;
  Statement readStatement = null;
  ResultSet resultSet = null;
  String results = "",result="";
  int numresults = 0;
  String statement = null;

  try {
    // Create connection to RDS instance
    conn = DriverManager.getConnection(jdbcUrl);

    setupStatement = conn.createStatement();
    //String table="create table sample (id INT(6) AUTO_INCREMENT PRIMARY KEY,name VARCHAR(50))";
    //String createTable = "CREATE TABLE users (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,firstname VARCHAR(30) NOT NULL,lastname VARCHAR(30) NOT NULL,email VARCHAR(50),phno VARCHAR(10),sex VARCHAR(10),country VARCHAR(50),city VARCHAR(50),qualification VARCHAR(50),institution VARCHAR(50))";
    //String insertRow1 = "INSERT INTO Beanstalk1 (Resource) VALUES ('EC2 Instance');";
    //String insertRow2 = "INSERT INTO Beanstalk1 (Resource) VALUES ('RDS Instance');";
    //String drop="drop table users;";
    String table="insert into sample(name) values('hari')";
    setupStatement.addBatch(table);
    //setupStatement.addBatch(insertRow1);
    //setupStatement.addBatch(insertRow2);
    //setupStatement.addBatch(drop);
    setupStatement.executeBatch();
  ///  setupStatement.close();
    conn.close();
  } catch (SQLException ex) {
    // handle any errors
    System.out.println("SQLException: " + ex.getMessage());
    System.out.println("SQLState: " + ex.getSQLState());
    System.out.println("VendorError: " + ex.getErrorCode());
  } finally {
    System.out.println("Closing the connection.");
    if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
  }

  try {
    conn = DriverManager.getConnection(jdbcUrl);
      System.out.println("in");
    readStatement = conn.createStatement();
    resultSet = readStatement.executeQuery("SELECT * FROM user");
    while(resultSet.next())
    {
        results = resultSet.getString("password");
        System.out.println("results = " + results);
    }
    //resultSet.first();
    //results = resultSet.getString("id");
    //resultSet.next();
    //results += ", " + resultSet.getString("Resource");
      System.out.println("results = " + results);
    resultSet.close();
    readStatement.close();
    conn.close();

  } catch (SQLException ex) {
    // handle any errors
    System.out.println("SQLException: " + ex.getMessage());
    System.out.println("SQLState: " + ex.getSQLState());
    System.out.println("VendorError: " + ex.getErrorCode());
  } finally {
       System.out.println("Closing the connection.");
      if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
  }
            }
    public static void main(String[] args) {
        // TODO code application logic here
        new db();
    }
}
