/*
 * Copyright 2004-2005 Germinus XXI
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    private String companyId;

    public ComponentConfiguration(String componentName) {
        this(null, componentName);
    }
    
    public ComponentConfiguration(String companyId, String componentName) {
        this.companyId = companyId;
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
                    readConfigurationObject(companyId,
                                            componentName, 
                                            getAvailableProperties());
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
        ComponentProperties properties = getAvailableProperties();
        if (!properties.hasBaseConfiguration()) {
            String msg = "The base properties file was not found";
            throw new ConfigurationNotFoundException(componentName, msg);
        }
        return properties;
    }

    private ComponentProperties getAvailableProperties() {
        if (properties != null) {
            return properties;
        }
        properties = getConfigurationManager().
        	readPropertiesConfiguration(companyId, componentName);
        return properties;
    }


    public boolean isCacheEnabled() {
        return getAvailableProperties().getBoolean("easyconf:cache.enabled", true);
    }


}
