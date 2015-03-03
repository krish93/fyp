
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harikrish
 */
public class HideFolder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try
        {            
          /*  Runtime rt = Runtime.getRuntime();
            //put your directory path instead of your_directory_path
            String dir=System.getProperty("user.dir");
            System.out.println("dir = " + dir);
            
            Process proc = rt.exec("attrib +H "+dir+"\\file_path"); 
            int exitVal = proc.exitValue();
            proc.destroy();*/
            Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
    	System.out.println( sdf.format(cal.getTime()) );
        
        final String dateStart = "01/14/2012 09:29:58 AM";
        final String dateStop = "01/15/2012 10:31:48 PM";
        final DateTimeFormatter format = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss a");
        final DateTime dt1 = format.parseDateTime(dateStart);
        final DateTime dt2 = format.parseDateTime(dateStop);

        System.out.print(Days.daysBetween(dt1, dt2).getDays() + " days, ");
        System.out.print(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
        System.out.print(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
        System.out.print(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");

        } catch (Throwable t)
          {
            t.printStackTrace();
          }
    }
}
