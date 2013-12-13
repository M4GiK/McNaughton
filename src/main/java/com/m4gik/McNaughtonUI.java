package com.m4gik;

import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

/**
 * 
 * TODO COMMENTS MISSING!
 * 
 * @author m4gik <michal.szczygiel@wp.pl>
 * 
 */
@Theme("mytheme")
@SuppressWarnings("serial")
public class McNaughtonUI extends UI {

    /**
     * 
     * TODO COMMENTS MISSING!
     * 
     * @author m4gik <michal.szczygiel@wp.pl>
     * 
     */
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = McNaughtonUI.class, widgetset = "com.m4gik.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    /**
     * 
     */
    private final static Logger logger = Logger.getLogger(McNaughtonUI.class
            .getName());

    /**
     * Creating object button for tabs in sheet.
     */
    private TabSheet tabSheet = null;

    @Override
    protected void init(VaadinRequest request) {

        // final VerticalLayout layout = new VerticalLayout();
        // layout.setMargin(true);
        // setContent(layout);
        //
        // Chart chart = new Chart(ChartType.BAR);
        // chart.setWidth("400px");
        // chart.setHeight("300px");
        //
        // // Modify the default configuration a bit
        // Configuration conf = chart.getConfiguration();
        // conf.setTitle("Planets");
        // conf.setSubTitle("The bigger they are the harder they pull");
        // conf.getLegend().setEnabled(false); // Disable legend
        //
        // // The data
        // ListSeries series = new ListSeries("Diameter");
        // series.setData(4900, 12100, 12800, 6800, 143000, 125000, 51100,
        // 49500);
        // conf.addSeries(series);
        //
        // // Set the category labels on the axis correspondingly
        // XAxis xaxis = new XAxis();
        // xaxis.setCategories("Mercury", "Venus", "Earth", "Mars", "Jupiter",
        // "Saturn", "Uranus", "Xplaneta");
        // xaxis.setTitle("Planet");
        // conf.addxAxis(xaxis);
        //
        // // Set the Y axis title
        // YAxis yaxis = new YAxis();
        // yaxis.setTitle("Diameter");
        // yaxis.getLabels().setFormatter(
        // "function() {return Math.floor(this.value/1000) + \'Mm\';}");
        // yaxis.getLabels().setStep(2);
        // conf.addyAxis(yaxis);
        //
        // layout.addComponent(chart);

        // Button button = new Button("Click Me");
        // button.addClickListener(new Button.ClickListener() {
        // public void buttonClick(ClickEvent event) {
        // layout.addComponent(new Label("Thank you for clicking"));
        // }
        // });
        // layout.addComponent(button);
    }

}
