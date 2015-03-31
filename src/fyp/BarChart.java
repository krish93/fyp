package fyp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * This demonstration shows a 3D bar chart with item labels displayed.
 *
 */
public class BarChart extends ApplicationFrame {

    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    String user_email;
    public BarChart(final String title,String email) {

        super(title);
        user_email=email;
        final CategoryDataset dataset = createDataset();
       
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Creates a sample dataset.
     *
     * @return a sample dataset.
     */
   private CategoryDataset createDataset() {

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DBConfig db=new DBConfig();
        Map<String,Map<?,?>> output=db.getBarGraphDetails(user_email);
        System.out.println("output = " + output);
        Set set = output.entrySet();
        Iterator iterator = set.iterator();
          while(iterator.hasNext()) {
         Map.Entry name = (Map.Entry)iterator.next();
         String file_name=name.getKey().toString();
              System.out.println("file_name = " + file_name);
         //String remove_braces=name.getValue().toString().replaceAll("\\{", "").replaceAll("\\}","").replaceAll("=","");
         String remove_braces[]=name.getValue().toString().replaceAll("\\{", "").replaceAll("\\}","").split(",");
         System.out.println("remove_braces = " + remove_braces);
         String success="0",failure="0";
         for(int i=0;i<remove_braces.length;i++)
         {
             System.out.println("remove_braces = " + remove_braces[i]);
             if(remove_braces[i].contains("success"+file_name))
             {
                 System.out.println("in");
                 success=remove_braces[i].split("=")[1];
             }
             else if(remove_braces[i].contains("failure"+file_name))
             {
                 System.out.println("in");
                 failure=remove_braces[i].split("=")[1];
             }
         }
         /*String string_split[]=remove_braces.split(",");
              System.out.println("string_split = " + string_split[0]);
              System.out.println("string_split = " + string_split[1]);
         string_split[0]=string_split[0].replaceAll("[a-z]+", "").replaceAll("\\s+","");
         string_split[1]=string_split[1].replaceAll("[a-z]+", "").replaceAll("\\s+","");
        // string_split[0]=string_split[0].split("=")[1];
         //string_split[1]=string_split[1].split("=")[1];
         System.out.println("string_split[0] = " + string_split[0]);
         System.out.println("string_split[0] = " + string_split[1]);*/
         //dataset.addValue(Integer.parseInt(string_split[0]), "Success", file_name.replaceAll("\\s+",""));
         //dataset.addValue(Integer.parseInt(string_split[1]), "failure", file_name.replaceAll("\\s+",""));
              System.out.println("success = " + success);
              System.out.println("failure = " + failure);
              upload up=new upload();
              long size=up.getFileSize(file_name);
              String fileSize="";
              if(size>=1024)
              {
                  size=size/1024;
                  fileSize=size+"(KB)";
              }   
              else
              {
                  fileSize=size+"(Bytes)";
              }
              
              
         dataset.addValue(Integer.parseInt(success), "Success", fileSize);
         dataset.addValue(Integer.parseInt(failure), "failure", fileSize);
      }
        /*dataset.addValue(1.0, "Series 1", "Category 1");   
        dataset.addValue(2.0, "Series 1", "Category 2");   
        dataset.addValue(1.0, "Series 2", "Category 1");   
        dataset.addValue(0.0, "Series 2", "Category 2");   
        dataset.addValue(4.0, "Series 3", "Category 1");   
        dataset.addValue(1.0, "Series 3", "Category 2");   */
        return dataset;

    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createBarChart3D(
            "",      
            "File Size (KB)",              
            "No of Users",                 
            dataset,                  
            PlotOrientation.VERTICAL, 
            true,                     
            true,                     
            false                     
        );
       
        final CategoryPlot plot = chart.getCategoryPlot();
        final CategoryAxis axis = plot.getDomainAxis();
        plot.setRangeAxis(1, null);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        System.out.println(rangeAxis.getLowerBound());
        rangeAxis.setLowerBound(0);
        rangeAxis.setRange(0, 10);
        System.out.println("axis"+axis.getAxisLinePaint());
        axis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 16.0)
        );
        
        final CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setItemLabelsVisible(true);
        final BarRenderer r = (BarRenderer) renderer;
      //  r.setMaxBarWidth(0.05);
        
        return chart;

    }
    
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final BarChart demo = new BarChart("","harik312@gmail.com");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}