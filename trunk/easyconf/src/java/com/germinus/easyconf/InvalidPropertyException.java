package com.germinus.easyconf;


/**
 * Thrown when an XML configuration file contains a variable which either
 * is not of the form <code>${variableName}</code> or is not
 * defined in any of the properties files associated with its component
 *
 * @author  Jorge Ferrer
 * @version $Revision$
 *
 */
public class InvalidPropertyException extends ConfigurationException {

    public InvalidPropertyException(String componentName, Throwable e) {
        super(componentName, e);
    }

    public String getMessage() {
        return super.getMessage() + ". A variable is either malformed or " +
        		"makes a reference to " +
        		"a property which is not defined for this component in " +
        		"any of its configuration files.";
    }
}
