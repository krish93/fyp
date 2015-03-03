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
        Map<String,Map> output=db.getBarGraphDetails(user_email);
        System.out.println("output = " + output);
        Set set = output.entrySet();
        Iterator iterator = set.iterator();
          while(iterator.hasNext()) {
         Map.Entry name = (Map.Entry)iterator.next();
         String file_name=name.getKey().toString();
         String remove_braces=name.getValue().toString().replaceAll("\\{", "").replaceAll("\\}","").replaceAll("=","");
         System.out.println("remove_braces = " + remove_braces);
         String string_split[]=remove_braces.split(",");
         string_split[0]=string_split[0].replaceAll("[a-z]+", "").replaceAll("\\s+","");
         string_split[1]=string_split[1].replaceAll("[a-z]+", "").replaceAll("\\s+","");
         System.out.println("string_split[0] = " + string_split[0]);
         dataset.addValue(Integer.parseInt(string_split[0]), "Success", file_name.replaceAll("\\s+",""));
         dataset.addValue(Integer.parseInt(string_split[1]), "failure", file_name.replaceAll("\\s+",""));
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
            "3D Bar Chart Demo",      // chart title
            "FileName",               // domain axis label
            "No of Users",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        final CategoryPlot plot = chart.getCategoryPlot();
        final CategoryAxis axis = plot.getDomainAxis();
        axis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 8.0)
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

        final BarChart demo = new BarChart("3D Bar Chart Demo 3","harik312@gmail.com");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}