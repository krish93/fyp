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
            Runtime rt = Runtime.getRuntime();
            //put your directory path instead of your_directory_path
            String dir=System.getProperty("user.dir");
            System.out.println("dir = " + dir);
            
            Process proc = rt.exec("attrib +H "+dir+"\\file_path"); 
            int exitVal = proc.exitValue();
            proc.destroy();

        } catch (Throwable t)
          {
            t.printStackTrace();
          }
    }
}
