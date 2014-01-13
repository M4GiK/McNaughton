/**
 * Project McNaughton.
 * Copyright Michał Szczygieł.
 * Created at Jan 13, 2014.
 */
package com.m4gik.presentation;

import com.vaadin.ui.Component;

/**
 * Interface for building the proper view.
 * 
 * @author m4gik <michal.szczygiel@wp.pl>
 * 
 */
public interface ViewComponent {
    /**
     * 
     * Method to build proper view.
     * 
     * @return The layout to build the view.
     */
    public Component build();
}
