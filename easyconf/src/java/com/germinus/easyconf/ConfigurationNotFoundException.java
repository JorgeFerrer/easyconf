package com.germinus.easyconf;

/**
 * Thrown when the base properties file is not found and the getProperties
 * method is explicitly called in the configuration
 *
 * @author jferrer
 */
public class ConfigurationNotFoundException extends ConfigurationException {
    public ConfigurationNotFoundException(String componentName,
                                          String msg,
                                          Throwable thr) {
        super(componentName, msg, thr);
    }

    protected ConfigurationNotFoundException(String componentName) {
        super(componentName);
    }

    protected ConfigurationNotFoundException(String componentName,
                                             Throwable e) {
        super(componentName, e);
    }

    public ConfigurationNotFoundException(String componentName, String msg) {
        super(componentName, msg);
    }
}
