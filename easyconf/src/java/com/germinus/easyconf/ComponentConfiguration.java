package com.germinus.easyconf;

import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Contains the configuration of an EasyConf component including properties 
 * configuration and an object graph configuration.
 *
 * @author  Jorge Ferrer
 * @version $Revision$
 *
 */
public class ComponentConfiguration {

    private ComponentProperties properties;
    private Object configurationObject;
    private String componentName;
    private ConfigurationLoader confManager = new ConfigurationLoader();

    ComponentConfiguration(String componentName) {
        this.componentName = componentName;
    }
    
    /**
     * Get the name of the component which is associated with this configuration
     */
    public String getComponentName() {
        return componentName;
    }
    
    /**
     * Get an object which represents the configuration of the given component
     * 
     * The object is populated using the digester rules defined in the file
     * componentName.digesterRules.xml which must be found in the classpath (first it is
     * searched in the context of the current thread and then in the context of the system
     * classpath)
     * @throws ConfigurationException if the object graph cannot be read
     */
    public Object getConfigurationObject() {
        if (configurationObject != null) {
            return configurationObject;
        }
        try {
            configurationObject = getConfigurationManager().
                    readConfigurationObject(componentName, getProperties());
        } catch (IOException e) {
            throw new ConfigurationException(componentName, "Error reading object configuration", e);
        } catch (SAXException e) {
            throw new ConfigurationException(componentName, "Error parsing the XML file", e);
        }
        return configurationObject;
    }

    private ConfigurationLoader getConfigurationManager() {
        return confManager;
    }

    /**
     * Get a typed map of the properties associated with this component
     */
    public ComponentProperties getProperties() {
        if (properties != null) {
            return properties;
        }
        properties = getConfigurationManager().readPropertiesConfiguration(componentName);
        return properties;
    }

    public boolean isCacheEnabled() {
        return getProperties().getBoolean("easyconf:cache.enabled", true);
    }


}
