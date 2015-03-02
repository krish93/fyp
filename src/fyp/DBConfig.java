package fyp;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harikrish
 */
import java.sql.*;
import javax.swing.JOptionPane;
public class DBConfig {

    /**
     * @param args the command line arguments
     */
    String dbName = "cpabe";
    String userName = "cpabe";
    String password = "SAASwebteam";
    String hostname = "fyp.cpbqet3zj9k7.ap-southeast-1.rds.amazonaws.com";
    String port = "3306";
    String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
      port + "/" + dbName + "?user=" + userName + "&password=" + password;
    int c=0;
    DBConfig()
    {
        System.out.println("jdbcUrl = " + jdbcUrl);
        try 
        {
            System.out.println("Loading driver...");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } 
        catch (ClassNotFoundException e) 
        {
            throw new RuntimeException("Cannot find the driver in the classpath!", e);
         }
        Connection conn = null;
        Statement setupStatement = null,count_statement=null;
        try
        {
            conn=DriverManager.getConnection(jdbcUrl);
            setupStatement=conn.createStatement();
            //String createTable = "CREATE TABLE user_file_history (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,email VARCHAR(50),filename VARCHAR(500),policy VARCHAR(1000),success VARCHAR(1000),failure VARCHAR(1000));";
            //String insertRow1 = "INSERT INTO users (id,firstname,lastname,email,phno,sex,country,city,qualification,institution) VALUES (2,'a','a','a','a','a','a','a','a','a');";
            //String insertRow1= "Truncate table user_file_history";
            //setupStatement.addBatch(insertRow1);
            //setupStatement.addBatch(createTable);
            //System.out.println("create "+setupStatement.executeBatch());
            setupStatement.close();
            conn.close();
            
            
        }
        catch(Exception e)
        {
            System.out.println("e = " + e);
        }
      }
    public String Policy(String email,String filename)
    {
        Connection conn=null;
        Statement get_policy=null;
        try
        {
            String result="";
            conn=DriverManager.getConnection(jdbcUrl);
            get_policy=conn.createStatement();
            String data="select policy from user_file_history where email='"+email+"' and filename='"+filename+"'";
            ResultSet res=get_policy.executeQuery(data);
            while(res.next())
            {
                result=res.getString("policy");
            }
            get_policy.close();
            conn.close();
            return result;
        }
        catch(Exception e)
        {
            System.out.println("Get Policy Error = " + e);
        }
        return null;
    }
    public void insertPolicy(String email,String filename,String policy)
    {
        Connection conn=null;
        Statement insert_policy=null;
        try
        {
            conn=DriverManager.getConnection(jdbcUrl);
            insert_policy=conn.createStatement();
            String data="insert into user_file_history(email,filename,policy,success,failure) values('"+email+"','"+filename+"','"+policy+"','0','0')";
            insert_policy.execute(data);
            insert_policy.close();
            conn.close();
        }
        catch(Exception e)
        {
            System.out.println("Policy insert error = " + e);
        }
    }
    public int verifyDuplicate(String roll,String email)
    {
        Connection conn=null;
        Statement search=null;
        try
        {
            conn=DriverManager.getConnection(jdbcUrl);
            search=conn.createStatement();
            String search_data="select * from user where roll='"+roll+"' and email='"+email+"';";
            ResultSet res=search.executeQuery(search_data);
            int count=0;
            while(res.next())
            {
                count=res.getRow();
            }
           return count;
        }
        catch(Exception e)
        {
            System.out.println("Search Duplicate Error = " + e);
        }
        return 0;
    }
    public int insertNewUser(String roll,String firstname,String lastname,String email,String password,String phone,String date,String month,String year,String sex,String country,String city,String college,String branch,String department,String type,String experience)
    {
        Connection conn=null;
        Statement insert=null;
        try
        {
            
            int verify=verifyDuplicate(roll,email);
            if(verify==0)
            {
                conn=DriverManager.getConnection(jdbcUrl);
                insert=conn.createStatement();
                String insert_data="insert into user(roll,firstname,lastname,email,password,phno,sex,country,city,date,month,year,college,branch,department,type,experience) values('"+roll+"','"+firstname+"','"+lastname+"','"+email+"','"+password+"','"+phone+"','"+sex+"','"+country+"','"+city+"','"+date+"','"+month+"','"+year+"','"+college+"','"+branch+"','"+department+"','"+type+"','"+experience+"');";
                System.out.println("insert_data = " + insert_data);
                Boolean insert_flag=insert.execute(insert_data);
                System.out.println("insert_flag = " + insert_flag);
                insert.close();
                conn.close();
                if(!insert_flag)
                {
                    return 2;
                }
                else
                {
                    return 3;
                }
            }
            else{
                return verify;
            }
            
        }
        catch(Exception e)
        {
            System.out.println("Insert New Entry Error = " + e);
        }
        return 0;
    }
    public String login_user(String email,String password)
    {
        Connection conn=null;
        Statement select=null;
        try
        {
            conn=DriverManager.getConnection(jdbcUrl);
            select=conn.createStatement();
            String select_data="select * from user where email='"+email+"' and password='"+password+"';";
            ResultSet res=select.executeQuery(select_data);
            if(res.next())
            {
                String ret="roll:"+res.getString("roll").replaceAll("\\s+","")+" firstname:"+res.getString("firstname").replaceAll("\\s+","")+" lastname:"+res.getString("lastname").replaceAll("\\s+","")+" email:"+res.getString("email").replaceAll("\\s+","")+" phone:"+res.getString("phno").replaceAll("\\s+","")+" sex:"+res.getString("sex").replaceAll("\\s+","")+" country:"+res.getString("country").replaceAll("\\s+","")+" city:"+res.getString("city").replaceAll("\\s+","")+" date:"+res.getString("date").replaceAll("\\s+","")+" month:"+res.getString("month").replaceAll("\\s+","")+" year:"+res.getString("year").replaceAll("\\s+","")+" college:"+res.getString("college").replaceAll("\\s+","")+" branch:"+res.getString("branch").replaceAll("\\s+","")+" department:"+res.getString("department").replaceAll("\\s+","")+" type:"+res.getString("type").replaceAll("\\s+","")+" experience:"+res.getString("experience").replaceAll("\\s+","");
                return ret;
            }
            else
            {
                return "failed";
            }
            
        }
        catch(Exception e)
        {
            System.out.println("Login detect error = " + e);
        }
        return null;
    }
        public static void main(String[] args) {
            // TODO code application logic here
            new DBConfig();
        }
      }
