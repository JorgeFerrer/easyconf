package com.germinus.easyconf;

import org.apache.commons.lang.exception.NestableRuntimeException;


/**
 * Some unrecoverable but important error has ocurred while reading the configuration
 *
 * @author  Jorge Ferrer
 * @version $Revision$
 *
 */
public class ConfigurationException extends NestableRuntimeException {
    
    private String componentName;

    public ConfigurationException(String componentName, String msg, Throwable thr) {
        super(msg, thr);
        this.componentName = componentName;
    }
    
    protected ConfigurationException(String componentName) {
        super();
        this.componentName = componentName;
    }
   
    protected ConfigurationException(String componentName, Throwable e) {
        super(e);
        this.componentName = componentName;
    }

    public ConfigurationException(String componentName, String msg) {
        super(msg);
        this.componentName = componentName;
    }

    public String getComponentName() {
        return componentName;
    }
    
    
    public String getMessage() {
        return "Error reading configuration for " + componentName + ": " +
        	super.getMessage();
    }
    
    
}
