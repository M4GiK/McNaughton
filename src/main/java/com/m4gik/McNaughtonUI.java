/**
 * Project McNaughton.
 * Copyright Michał Szczygieł.
 * Created at Jan 11, 2014.
 */
package com.m4gik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

import com.m4gik.presentation.ChartComponent;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ColorPicker;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * 
 * This class represents graphical represents for McNaughton algorithm for
 * scheduling tasks.
 * 
 * @author m4gik <michal.szczygiel@wp.pl>
 * 
 */
@Theme("mytheme")
@SuppressWarnings("serial")
public class McNaughtonUI extends UI {

    /**
     * 
     * Servlet class based on Vaadin servlet.
     * 
     * @author m4gik <michal.szczygiel@wp.pl>
     * 
     */
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = McNaughtonUI.class, widgetset = "com.m4gik.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    /**
     * Logger for event registration.
     */
    private final static Logger logger = Logger.getLogger(McNaughtonUI.class
            .getName());
    /**
     * Fields enumerates tasks.
     */
    private Integer globalIncrement = 1;

    /**
     * Field keeps amount of machines.
     */
    private TextField machineAmountInput;

    /**
     * The root layout for application.
     */
    private VerticalLayout rootLayout;

    /**
     * List of components.
     */
    private List<HorizontalLayout> taskList;

    /**
     * This method creates button for generate chart.
     * 
     * @param grid
     * 
     * @return Button with click actions.
     */
    private Component addGenerateChartButton(GridLayout grid) {
        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setSizeFull();

        grid.addComponent(verticalLayout, 1, 1);

        Button generateButton = new Button("Show chart");
        generateButton.setWidth("100px");
        generateButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                TextField time = null;
                LinkedHashMap<String, HashMap<Double, Color>> mapList = new LinkedHashMap<String, HashMap<Double, Color>>();
                Boolean isDoneProperly = true;

                try {
                    for (HorizontalLayout component : getTaskList()) {
                        time = (TextField) component.getComponent(2);
                        Label key = (Label) component.getComponent(0);
                        ColorPicker colorPicker = (ColorPicker) component
                                .getComponent(4);
                        if (!time.getValue().equals("")) {
                            HashMap<Double, Color> mapTime = new HashMap<Double, Color>();
                            mapTime.put(Double.parseDouble(time.getValue()),
                                    colorPicker.getColor());
                            mapList.put(key.getValue(), mapTime);
                        } else {
                            time.setValue("0");
                        }
                    }

                } catch (NumberFormatException ex) {
                    Notification.show("Improper value for task",
                            "\nValue should be a digit number",
                            Type.WARNING_MESSAGE);
                    time.focus();
                    isDoneProperly = false;
                }

                if (isDoneProperly) {
                    try {
                        Integer machineAmount = Integer
                                .parseInt(machineAmountInput.getValue());
                        verticalLayout.removeAllComponents();
                        verticalLayout.addComponent(generateChart(
                                machineAmount, mapList));

                    } catch (NumberFormatException ex) {
                        Notification.show("Improper value",
                                "\nValue should be an integer",
                                Type.WARNING_MESSAGE);
                        machineAmountInput.focus();
                    }
                }
            }
        });

        return generateButton;
    }

    /**
     * This method add slogan/subtitle to Title.
     * 
     * @param root
     */
    private void addSlogan(VerticalLayout root) {
        Label slogan = new Label("Tasks scheduling");
        slogan.addStyleName(Reindeer.LABEL_SMALL);
        slogan.setSizeUndefined();
        root.addComponent(slogan);
        root.setComponentAlignment(slogan, Alignment.TOP_CENTER);
    }

    /**
     * Method adds for spacer between texts.
     * 
     * @return Label which make a space.
     */
    private Label addSpacer() {
        Label text = new Label("");
        text.setWidth("10px");

        return text;
    }

    /**
     * This method creates task, and adds to task list.
     * 
     * @param verticalLayout
     * 
     * @return task to display.
     */
    protected Component addTask(final VerticalLayout verticalLayout) {
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setMargin(true);

        Label time = new Label("Task No." + globalIncrement++);
        time.addStyleName(Reindeer.LABEL_H2);

        TextField taskTime = new TextField();
        taskTime.setWidth("80px");

        ColorPicker colorPicker = new ColorPicker("Color for task",
                randomColor());
        colorPicker.setSizeFull();

        Image delete = new Image(null, new ThemeResource("img/delete.png"));
        delete.addClickListener(new com.vaadin.event.MouseEvents.ClickListener() {

            @Override
            public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
                getTaskList().remove(horizontalLayout);
                horizontalLayout.removeAllComponents();
                verticalLayout.removeComponent(horizontalLayout);
            }
        });

        horizontalLayout.addComponent(time);
        horizontalLayout.addComponent(addSpacer());
        horizontalLayout.addComponent(taskTime);
        horizontalLayout.addComponent(addSpacer());
        horizontalLayout.addComponent(colorPicker);
        horizontalLayout.addComponent(addSpacer());
        horizontalLayout.addComponent(delete);
        horizontalLayout.setComponentAlignment(delete, Alignment.MIDDLE_RIGHT);
        getTaskList().add(horizontalLayout);

        return getTaskList().get(getTaskList().size() - 1);
    }

    /**
     * This method creates button with click actions.
     * 
     * @param grid
     * @return Button with click operations.
     */
    private Component addTaskButton(GridLayout grid) {
        HorizontalLayout addTaskLayout = new HorizontalLayout();
        addTaskLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        addTaskLayout.setWidth("380px");
        addTaskLayout.addComponent(addSpacer());

        Label description = new Label("Press button to add »");
        description.setStyleName(Reindeer.LABEL_SMALL);
        addTaskLayout.addComponent(description);
        addTaskLayout
                .setComponentAlignment(description, Alignment.MIDDLE_RIGHT);

        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setVisible(false);
        grid.addComponent(verticalLayout, 0, 1);

        Button addTaskButton = new Button("Add task");
        addTaskButton.setWidth("100px");
        addTaskLayout.addComponent(addTaskButton);
        addTaskLayout.setComponentAlignment(addTaskButton,
                Alignment.MIDDLE_RIGHT);
        addTaskButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                verticalLayout.setVisible(true);
                verticalLayout.addComponent(addTask(verticalLayout));
            }
        });

        return addTaskLayout;
    }

    /**
     * This method sets Title for application.
     * 
     * @param root
     */
    private void addTitle(VerticalLayout root) {
        Label title = new Label("McNaughton");
        title.addStyleName(Reindeer.LABEL_H1);
        title.setSizeUndefined();
        root.addComponent(title);
        root.setComponentAlignment(title, Alignment.TOP_CENTER);
        addSlogan(root);
    }

    /**
     * This method adds horizontal layout with fields to root layout.
     * 
     * @param rootLayout
     *            .
     */
    private void buildFiledMachineAmount(VerticalLayout rootLayout) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Label machineAmount = new Label("Machines amount: ");
        machineAmountInput = new TextField();
        machineAmountInput.setWidth("130px");
        machineAmount.addStyleName(Reindeer.LABEL_H2);
        horizontalLayout.setMargin(true);
        horizontalLayout.addComponent(addSpacer());
        horizontalLayout.addComponent(machineAmount);
        horizontalLayout.addComponent(addSpacer());
        horizontalLayout.addComponent(machineAmountInput);
        rootLayout.addComponent(horizontalLayout);
    }

    /**
     * This method creates grid layout.
     * 
     * @return Configured grid layout.
     */
    private GridLayout createGridLayout() {
        GridLayout gridLayout = new GridLayout(2, 2);

        return gridLayout;
    }

    /**
     * This method creates root layout with proper configuration.
     * 
     * @return Setup root layout.
     */
    private VerticalLayout createRootLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        return layout;
    }

    /**
     * This method generates chart for McNaughton scheduling tasks.
     * 
     * @param amount
     *            The amount of machines.
     * @param mapList
     *            The data for tasks.
     * 
     * @return Chart component to display.
     */
    protected Component generateChart(Integer amount,
            LinkedHashMap<String, HashMap<Double, Color>> mapList) {
        return new ChartComponent(amount, mapList).build();
    }

    /**
     * This method gets root layout.
     * 
     * @return The rootLayout.
     */
    public VerticalLayout getRootLayout() {
        return rootLayout;
    }

    /**
     * This method gets list of HorizontalLayout.
     * 
     * @return the taskList.
     */
    public List<HorizontalLayout> getTaskList() {
        return taskList;
    }

    /**
     * 
     * This method initialize graphical interface. This method overrides an
     * existing method.
     * 
     * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
     */
    @Override
    protected void init(VaadinRequest request) {
        setTaskList(new ArrayList<HorizontalLayout>());
        setContent(setRootLayout(createRootLayout()));
        addTitle(getRootLayout());
        buildFiledMachineAmount(getRootLayout());
        GridLayout grid = createGridLayout();

        grid.setMargin(true);
        grid.setSpacing(true);
        grid.setSizeFull();
        grid.setColumnExpandRatio(1, 2);
        getRootLayout().addComponent(grid);

        grid.addComponent(addTaskButton(grid), 0, 0);
        grid.addComponent(addGenerateChartButton(grid), 1, 0);
    }

    /**
     * This method generates random color.
     * 
     * @return Random color.
     */
    private Color randomColor() {
        Random randomGenerator = new Random();

        int red = randomGenerator.nextInt(255);
        int green = randomGenerator.nextInt(255);
        int blue = randomGenerator.nextInt(255);

        return new Color(red, green, blue);
    }

    /**
     * This method sets the rootLayout.
     * 
     * @param rootLayout
     *            The rootLayout to set.
     */
    public VerticalLayout setRootLayout(VerticalLayout rootLayout) {
        this.rootLayout = rootLayout;
        return rootLayout;
    }

    /**
     * This method sets list of components.
     * 
     * @param taskList
     *            the taskList to set.
     */
    public void setTaskList(List<HorizontalLayout> taskList) {
        this.taskList = taskList;
    }

}
