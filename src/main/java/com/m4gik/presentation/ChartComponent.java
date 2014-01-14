/**
 * Project McNaughton.
 * Copyright Michał Szczygieł.
 * Created at Jan 13, 2014.
 */
package com.m4gik.presentation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.m4gik.core.McNaughton;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.ChartOptions;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.PlotOptionsColumnRange;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.themes.GridTheme;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

/**
 * Class responsible for generating graphical representation for McNaughton
 * algorithm.
 * 
 * @author m4gik <michal.szczygiel@wp.pl>
 * 
 */
public class ChartComponent implements ViewComponent {

    /**
     * Logger for event registration.
     */
    private final static Logger logger = Logger.getLogger(ChartComponent.class
            .getName());

    /**
     * Generated serialVersionUID.
     */
    private static final long serialVersionUID = -591629057559729547L;

    /**
     * This field checks is allow to build chart.
     */
    private Boolean isAllow = false;

    /**
     * Object for perform calculation for McNaughton algorithm.
     */
    private McNaughton mcnaughton;

    /**
     * Constructor for {@link ChartComponent}. This method also initialize
     * McNaughton algorithm.
     * 
     * @param amount
     *            The amount of machines.
     * @param mapList
     *            The data for tasks.
     */
    public ChartComponent(Integer amount,
            LinkedHashMap<String, HashMap<Double, Color>> mapList) {
        this.mcnaughton = new McNaughton();
        if (!mcnaughton.insertData(amount, mapList.size())) {
            Notification.show("Improper machines amount for tasks",
                    "\nAmount of machines should be less then tasks amount",
                    Type.WARNING_MESSAGE);
        } else {
            setIsAllow(true);
            mcnaughton.insertTimeData(mapList);
            mcnaughton.mcnaughtonCalculation();
            logger.info("Max: " + mcnaughton.getMax().toString());
        }
    }

    /**
     * This method creates layout with set chart. This method overrides an
     * existing method.
     * 
     * @see com.m4gik.presentation.ViewComponent#build()
     */
    @Override
    public Component build() {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        if (getIsAllow()) {
            Chart chart = new Chart(ChartType.COLUMNRANGE);
            Configuration conf = setup(chart);

            String[] machinesName = new String[mcnaughton.getMachineAmount()];
            for (int i = 0; i < mcnaughton.getMachineAmount(); i++) {
                machinesName[i] = "machine No." + (i + 1);
            }

            XAxis x = new XAxis();
            x.setCategories(machinesName);
            x.setTitle("Machines");
            x.setEndOnTick(true);
            conf.addxAxis(x);

            YAxis y = new YAxis();
            y.setTitle("Time");
            y.setMin(0);
            y.setMax(mcnaughton.getMax());
            conf.addyAxis(y);

            DataSeries[] dataSeries = new DataSeries[mcnaughton.getTaskAmount()];
            Integer taskIterator = 0;
            for (Entry<String, HashMap<Double, Color>> entry : mcnaughton
                    .getTasks().entrySet()) {
                PlotOptionsColumnRange option = new PlotOptionsColumnRange();
                for (Entry<Double, Color> value : entry.getValue().entrySet()) {
                    option.setColor(new SolidColor(value.getValue().getRed(),
                            value.getValue().getGreen(), value.getValue()
                                    .getBlue()));
                }
                if (dataSeries.length > taskIterator) {
                    dataSeries[taskIterator] = new DataSeries();
                    dataSeries[taskIterator].setName(entry.getKey());
                    dataSeries[taskIterator].setPlotOptions(option);
                }
                taskIterator++;
            }

            Double temp = .0;
            Integer machine = 0;
            DataSeriesItem item;
            taskIterator = 0;
            for (Entry<String, HashMap<Double, Color>> entry : mcnaughton
                    .getTasks().entrySet()) {
                for (Entry<Double, Color> value : entry.getValue().entrySet()) {
                    System.out.println(value.getKey());
                    if (value.getKey() + temp > mcnaughton.getMax()) {
                        if (temp < mcnaughton.getMax()) {
                            Double over = value.getKey() + temp
                                    - mcnaughton.getMax();
                            item = new DataSeriesItem();
                            item.setName(machinesName[machine]);
                            item.setLow(temp);
                            item.setHigh(mcnaughton.getMax());
                            pushDown(dataSeries[taskIterator], machine);
                            dataSeries[taskIterator].add(item);

                            temp = .0;
                            machine++;

                            if (machine < mcnaughton.getMachineAmount()) {

                                item = new DataSeriesItem();
                                item.setName(machinesName[machine]);
                                item.setLow(temp);
                                item.setHigh(temp + over);
                                dataSeries[taskIterator].add(item);
                                temp += over;
                            }
                        } else {
                            temp = .0;
                            machine++;
                            item = new DataSeriesItem();
                            item.setName(machinesName[machine]);
                            item.setLow(temp);
                            item.setHigh(temp + value.getKey());
                            pushDown(dataSeries[taskIterator], machine);
                            dataSeries[taskIterator].add(item);
                            temp += value.getKey();
                        }
                    } else {
                        item = new DataSeriesItem();
                        item.setName(machinesName[machine]);
                        item.setLow(temp);
                        item.setHigh(temp + value.getKey());
                        pushDown(dataSeries[taskIterator], machine);
                        dataSeries[taskIterator].add(item);
                        temp += value.getKey();
                    }
                    taskIterator++;
                }
            }

            conf.setSeries(dataSeries);
            layout.addComponent(chart);
        }

        return layout;
    }

    /**
     * @return the isAllow
     */
    public Boolean getIsAllow() {
        return isAllow;
    }

    /**
     * This method pushs down the tasks, to avoid not proper displaying. This
     * operation is necessary to allocate task in proper machine.
     * 
     * @param dataSeries
     *            The data series to make operation.
     * @param machine
     *            The current machine.
     */
    private void pushDown(DataSeries dataSeries, Integer machine) {
        for (int i = 0; i < machine; i++) {
            DataSeriesItem item = new DataSeriesItem();
            item.setLow(0);
            item.setHigh(0);
            dataSeries.add(item);
        }
    }

    /**
     * @param isAllow
     *            the isAllow to set
     */
    public void setIsAllow(Boolean isAllow) {
        this.isAllow = isAllow;
    }

    /**
     * This method sets up basic options for chart.
     * 
     * @param chart
     *            The chart object to set configuration.
     * @return The configuration for chart.
     */
    private Configuration setup(Chart chart) {
        // Presentation options
        chart.setSizeFull();

        // Set chart theme
        ChartOptions.get().setTheme(new GridTheme());

        // Create legend
        Legend legend = new Legend();
        legend.setBackgroundColor("#FFFFFF");
        legend.setReversed(true);

        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Time scheduling tasks");
        conf.setSubTitle("McNaughton algorithm");
        conf.getChart().setInverted(true);

        // Tooltip configuration for displaying description
        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.series.name +': '+ this.point.low + ' - ' +this.point.high");
        conf.setTooltip(tooltip);

        // Set data labels
        Labels dataLabels = new Labels(true);
        dataLabels
                .setFormatter("this.y == this.point.low ? '' : this.series.name");
        dataLabels.setInside(true);
        dataLabels.setColor(new SolidColor("white"));

        // Plot column range options
        PlotOptionsColumnRange columnRange = new PlotOptionsColumnRange();
        columnRange.setGrouping(false);
        columnRange.setDataLabels(dataLabels);
        conf.setPlotOptions(columnRange);

        return conf;
    }
}
