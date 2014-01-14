/**
 * Project McNaughton.
 * Copyright Michał Szczygieł.
 * Created at Jan 11, 2014.
 */
package com.m4gik.core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.vaadin.shared.ui.colorpicker.Color;

/**
 * This class represents operation to perform McNaughton algorithm.
 * 
 * @author m4gik <michal.szczygiel@wp.pl>
 * 
 */
public class McNaughton {

    /**
     * Variable keeps amount of machine.
     */
    private Integer machineAmount;

    /**
     * Variable keeps max value.
     */
    private Double max;

    /**
     * Variable keeps amount of task.
     */
    private Integer taskAmount;

    /**
     * List of tasks.
     */
    private LinkedHashMap<String, HashMap<Double, Color>> tasks = new LinkedHashMap<String, HashMap<Double, Color>>();

    /**
     * This method gets amount of machines.
     * 
     * @return the machineAmount.
     */
    public Integer getMachineAmount() {
        return machineAmount;
    }

    /**
     * This method gets max value.
     * 
     * @return the max.
     */
    public Double getMax() {
        return max;
    }

    /**
     * This method gets amount of tasks.
     * 
     * @return the taskAmount.
     */
    public Integer getTaskAmount() {
        return taskAmount;
    }

    /**
     * This method gets task list.
     * 
     * @return the tasks.
     */
    public LinkedHashMap<String, HashMap<Double, Color>> getTasks() {
        return tasks;
    }

    /**
     * This method sets inserted data.
     * 
     * @param machineAmount
     *            The amount of machines.
     * @param taskAmount
     *            The amount of tasks.
     * @return True if is possible to insert data, false if is not.
     */
    public Boolean insertData(Integer machineAmount, Integer taskAmount) {
        Boolean isInserted = false;
        if (machineAmount <= taskAmount) {
            setMachineAmount(machineAmount);
            setTaskAmount(taskAmount);
            isInserted = true;
        }

        return isInserted;
    }

    /**
     * This method inserts time data to list of tasks.
     * 
     * @param mapList
     *            The list of times to put into tasks.
     */
    public void insertTimeData(
            LinkedHashMap<String, HashMap<Double, Color>> mapList) {
        setTasks(mapList);
    }

    /**
     * The algorithm for calculating Cmax according to the McNaughton algorithm
     * assumptions. Counts average time of all tasks. Next compares with the
     * longest task: calculates the max (max (tj), 1 / m * SUM (tj)). Determines
     * the final value of the field Cmax for further calculations
     */
    public void mcnaughtonCalculation() {
        Double maxTime = 0.0;

        for (Entry<String, HashMap<Double, Color>> entry : getTasks()
                .entrySet()) {
            for (Entry<Double, Color> value : entry.getValue().entrySet()) {
                setMax(getMax() + (1 / getMachineAmount() * value.getKey()));
                if (maxTime < value.getKey()) {
                    maxTime = value.getKey();
                }
            }
        }

        if (getMax() < maxTime) {
            setMax(maxTime);
        }
    }

    /**
     * This method sets amount of machines.
     * 
     * @param machineAmount
     *            the machineAmount to set.
     */
    public void setMachineAmount(Integer machineAmount) {
        this.machineAmount = machineAmount;
    }

    /**
     * This method sets max value.
     * 
     * @param max
     *            the max to set.
     */
    public void setMax(Double max) {
        this.max = max;
    }

    /**
     * This method sets tasks amount.
     * 
     * @param taskAmount
     *            the taskAmount to set.
     */
    public void setTaskAmount(Integer taskAmount) {
        this.taskAmount = taskAmount;
    }

    /**
     * This method set tasks.
     * 
     * @param tasks
     *            the tasks to set.
     */
    public void setTasks(LinkedHashMap<String, HashMap<Double, Color>> tasks) {
        this.tasks = tasks;
    }

}
