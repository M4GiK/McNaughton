/**
 * Project McNaughton.
 * Copyright Michał Szczygieł.
 * Created at Jan 11, 2014.
 */
package com.m4gik;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
     * The root layout for application.
     */
    private VerticalLayout rootLayout;

    /**
     * List of components.
     */
    private List<Component> taskList;

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
        verticalLayout.setStyleName(Reindeer.LAYOUT_BLACK); // TODO: Delete
                                                            // this!
        grid.addComponent(verticalLayout, 1, 1);

        Button generateButton = new Button("Show chart");
        generateButton.setWidth("100px");
        generateButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                verticalLayout.removeAllComponents();
                verticalLayout.addComponent(generateChart());
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
     * This method creates task, and adds to task list.
     * 
     * @return task to display.
     */
    protected Component addTask() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        horizontalLayout.setMargin(true);
        horizontalLayout.setWidth("250px");
        Label time = new Label("Task No." + (getTaskList().size() + 1));
        time.addStyleName(Reindeer.LABEL_H2);

        TextField taskTime = new TextField();
        taskTime.setSizeFull();

        horizontalLayout.addComponent(time);
        horizontalLayout.addComponent(taskTime);
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

        Label description = new Label("Press button to add new task »");
        addTaskLayout.addComponent(description);

        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setVisible(false);
        grid.addComponent(verticalLayout, 0, 1);

        Button addTaskButton = new Button("Add task");
        addTaskButton.setWidth("100px");
        addTaskLayout.addComponent(addTaskButton);
        addTaskButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                verticalLayout.setVisible(true);
                verticalLayout.addComponent(addTask());
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
        Label machineAmount = new Label("Machine amount: ");
        TextField machineAmountInput = new TextField();
        machineAmountInput.setWidth("140px");
        machineAmount.addStyleName(Reindeer.LABEL_H2);
        horizontalLayout.setMargin(true);
        horizontalLayout.addComponent(machineAmount);
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
     * @return
     */
    protected Component generateChart() {
        // TODO Auto-generated method stub
        return null;
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
     * This method gets list of components.
     * 
     * @return the taskList.
     */
    public List<Component> getTaskList() {
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
        setTaskList(new ArrayList<Component>());
        setContent(setRootLayout(createRootLayout()));
        addTitle(getRootLayout());
        buildFiledMachineAmount(getRootLayout());
        GridLayout grid = createGridLayout();
        grid.setMargin(true);
        grid.setSpacing(true);
        grid.setSizeFull();
        grid.setColumnExpandRatio(1, 5);
        getRootLayout().addComponent(grid);

        grid.addComponent(addTaskButton(grid), 0, 0);
        grid.addComponent(addGenerateChartButton(grid), 1, 0);
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
    public void setTaskList(List<Component> taskList) {
        this.taskList = taskList;
    }

}
