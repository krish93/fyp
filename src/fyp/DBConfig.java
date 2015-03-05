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
import java.util.HashMap;
import java.util.Map;
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
            //String insertRow1= "Truncate table access_policy";
            //String createTable = "CREATE TABLE access_policy (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,email VARCHAR(50),filename VARCHAR(500),type VARCHAR(1000),status VARCHAR(1000),start_time VARCHAR(100),end_time VARCHAR(100));";
            //String insert_data="insert into user(roll,firstname,lastname,email,password,phno,sex,country,city,date,month,year,college,branch,department,type,experience) values('Admin@1993','Admin','.','admin@admin.com','Admin@123','1234567890','Male','India','Chennai','1','jan','1111','CEG','B.E','ComputerScience&Engineering','student','4');";
            //String insertRow1= "Truncate table user";
            //setupStatement.addBatch(insert_data);
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
    public void modifyHistoryYable(String email,String filename,String type)
    {
        Connection conn=null;
        Statement modify=null;
        Statement select=null;
        try
        {
            conn=DriverManager.getConnection(jdbcUrl);
            modify=conn.createStatement();
            select=conn.createStatement();
            String select_data="select * from user_file_history where email='"+email+"' and filename='"+filename+"'";
            ResultSet res=select.executeQuery(select_data);
            String count;
            while(res.next())
            {
                if(type=="success")
                {
                    count=res.getString("success");
                    count=(Integer.parseInt(count)+1)+"";
                    String modify_data="update user_file_history set success='"+count+"' where email='"+email+"' and filename='"+filename+"'";
                    Boolean mod=modify.execute(modify_data);
                    System.out.println("mod = " + mod);
                    modify.close();
                }
                else if(type=="failure")
                {
                    count=res.getString("failure");
                    count=(Integer.parseInt(count)+1)+"";
                    String modify_data="update user_file _history set success='"+count+"' where email='"+email+"' and filename='"+filename+"'";
                    Boolean mod=modify.execute(modify_data);
                    modify.close();
                    System.out.println("mod = " + mod);
                }
            }
            select.close();
            conn.close();
            
        }
        catch(Exception e)
        {
            System.out.println("History Update error = " + e);
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
            search.close();
            conn.close();
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
                select.close();
                conn.close();
                return ret;
            }
            else
            {
                select.close();
                conn.close();
                return "failed";
            }
            
        }
        catch(Exception e)
        {
            System.out.println("Login detect error = " + e);
        }
        return null;
    }
    public void insertAccessPolicy(String email,String filename,String type,String status,String start_time,String end_time)
    {
        Connection conn=null;
        Statement insert=null;
        try
        {
            conn=DriverManager.getConnection(jdbcUrl);
            insert=conn.createStatement();
            String insert_data="insert into access_policy(email,filename,type,status,start_time,end_time) values('"+email+"','"+filename+"','"+type+"','"+status+"','"+start_time+"','"+end_time+"')";
            Boolean ins=insert.execute(insert_data);
            System.out.println("ins = " + ins);
            insert.close();
            conn.close();
        }
        catch(Exception e)
        {
            System.out.println("insert access error = " + e);
        }
                
    }
    public Object[][] getUploadDetails(String email,String type)
    {
        Connection conn=null;
        Statement history=null,policy=null;
        Object row_data[][]= new Object[100][100];
        int i=0;
        try
        {
            conn=DriverManager.getConnection(jdbcUrl);
            policy=conn.createStatement();
            
            String  policy_data="select * from access_policy where email='"+email+"' and type='"+type+"'";
            ResultSet res=policy.executeQuery(policy_data);
            while(res.next())
            {
                row_data[i][0]=(i+1);
                row_data[i][1]=res.getString("email");
                row_data[i][2]=res.getString("filename");
                System.out.println("Policy(row_data[i][1].toString(),row_data[i][2].toString()) = " + row_data[i][1].toString()+" "+row_data[i][2].toString());
                row_data[i][3]=Policy(row_data[i][1].toString(),row_data[i][2].toString());
                row_data[i][4]=res.getString("status");
                row_data[i][5]=res.getString("end_time");
                i++;
            }
            System.out.println("row_data[0][2] = " + row_data[0][2]);
            policy.close();
            conn.close();
            return (Object[][])row_data;
        }
        catch(Exception e)
        {
            System.out.println("Get Upload Detail Error = " + e);
        }
        return null;
    }
    public Object[][] getDownloadDetails(String email,String type)
    {
        Connection conn=null;
        Statement history=null,policy=null;
        Object row_data[][]= new Object[100][100];
        int i=0;
        try
        {
            conn=DriverManager.getConnection(jdbcUrl);
            policy=conn.createStatement();
            
            String  policy_data="select * from access_policy where email='"+email+"' and type='"+type+"'";
            ResultSet res=policy.executeQuery(policy_data);
            while(res.next())
            {
                row_data[i][0]=(i+1);
                row_data[i][1]=res.getString("email");
                row_data[i][2]=res.getString("filename");
                row_data[i][3]=res.getString("status");
                row_data[i][4]=res.getString("end_time");
                i++;
            }
            System.out.println("row_data[0][2] = " + row_data[0][2]);
            policy.close();
            conn.close();
            return (Object[][])row_data;
        }
        catch(Exception e)
        {
            System.out.println("Get Upload Detail Error = " + e);
        }
        return null;
    }
    public Map getBarGraphDetails(String email)
    {
        Connection conn=null;
        Statement filename=null,status=null;
        String[] file_name=new String[1000];
        Map<String,Integer> mapper=new HashMap<String,Integer>();
        Map<String,Map> output=new HashMap(mapper);
        int i=0;
        try
        {
            conn=DriverManager.getConnection(jdbcUrl);
            filename=conn.createStatement();
            status=conn.createStatement();
            String name="select filename from access_policy where email='"+email+"' and type='upload' and status='success'";
            ResultSet res=filename.executeQuery(name);
            while(res.next())
            {
                file_name[i]=res.getString("filename");
                String status_data="select success,failure from user_file_history where email='"+email+"' and filename='"+file_name[i]+"'";
                ResultSet res1=status.executeQuery(status_data);
                while(res1.next())
                {
                    String success=res1.getString("success");
                    String failure=res1.getString("failure");
                    mapper.put("success", Integer.parseInt(success));
                    mapper.put("failure",Integer.parseInt(failure));
                }
                 output.put(file_name[i],mapper);
                i++;
            }
            return output;
        }
        catch(Exception e)
        {
            System.out.println("Fetching details for graph error = " + e);
        }
        return null;
    }
    public Object[][] getCompelteUserDetails()
    {
        Connection conn=null;
        Statement details=null;
        Object user[][]=new Object[5000][5000];
        int i=0;
        try
        {
            conn=DriverManager.getConnection(jdbcUrl);
            details=conn.createStatement();
            String user_details="select * from user";
            ResultSet res=details.executeQuery(user_details);
            while(res.next())
            {
                if(!res.getString(3).toString().toLowerCase().equals("admin"))
                {
                user[i][0]=(i+1);
                user[i][1]=res.getString(2);
                user[i][2]=res.getString(3);
                user[i][3]=res.getString(4);
                user[i][4]=res.getString(5);
                user[i][5]=res.getString(6).toString().replaceAll("[A-Za-z0-9]", "*");
                user[i][6]=res.getString(7);
                user[i][7]=res.getString(8);
                user[i][8]=res.getString(9);
                user[i][8]=res.getString(10);
                user[i][10]=res.getString(11)+"/"+res.getString(12)+"/"+res.getString(13);
                user[i][11]=res.getString(14);
                user[i][12]=res.getString(15);
                user[i][13]=res.getString(16);
                user[i][14]=res.getString(17);
                user[i][15]=res.getString(18);
                i++;
                }
            }
            return user;
        }
        catch(Exception e)
        {
            System.out.println("Complete Details Error = " + e);
        }
        return null;
    }
    public Object[][] getCompelteLogDetails()
    {
        Connection conn=null;
        Statement details=null;
        Object user[][]=new Object[5000][5000];
        int i=0;
        try
        {
            conn=DriverManager.getConnection(jdbcUrl);
            details=conn.createStatement();
            String user_details="select * from access_policy";
            ResultSet res=details.executeQuery(user_details);
            while(res.next())
            {
                user[i][0]=(i+1);
                user[i][1]=res.getString(2);
                user[i][2]=res.getString(3);
                user[i][3]=res.getString(4);
                user[i][4]=res.getString(5);
                user[i][5]=res.getString(6);
                user[i][6]=res.getString(7);
                i++;
                
            }
            return user;
        }
        catch(Exception e)
        {
            System.out.println("Complete Details Error = " + e);
        }
        return null;
    }
        public static void main(String[] args) {
            // TODO code application logic here
            new DBConfig();
        }
      }
