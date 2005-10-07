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
    private String componentName;
    private ConfigurationLoader confManager = new ConfigurationLoader();
    private String companyId;
    private ConfigurationObjectCache configurationObjectCache;

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
        if (configurationObjectCache != null) {
            return configurationObjectCache.getConfigurationObject();
        }
        try {
            configurationObjectCache = getConfigurationManager().
                    readConfigurationObject(companyId,
                                            componentName, 
                                            getAvailableProperties());
        } catch (IOException e) {
            throw new ConfigurationException(componentName, "Error reading object configuration", e);
        } catch (SAXException e) {
            throw new ConfigurationException(componentName, "Error parsing the XML file", e);
        }
        return configurationObjectCache.getConfigurationObject();
    }
    
    /**
     * Update or create a new Object to a persistent storage.
     * 
     * In order to make this method work it should be stablished a
     * configuration-objects-source to a database as explained in the docs. 
     * If the source of configuration objects does not allow persistent storage
     * (which is the default) an exception will be thrown.
     * 
     * @param obj
     */
    public void saveConfigurationObject(Object configurationObject) {
    	getConfigurationManager().saveConfigurationObjectIntoDatabase(configurationObject, companyId, componentName, getAvailableProperties());
    	configurationObjectCache = new ConfigurationObjectCache(configurationObject, null, getAvailableProperties());
    }

    private ConfigurationLoader getConfigurationManager() {
        return confManager;
    }

    /**
     * Get a typed map of the properties associated with this component
     */
    public ComponentProperties getProperties() {
        ComponentProperties properties = getAvailableProperties();
        if ((!properties.hasBaseConfiguration())){
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

    
	public boolean equals(Object obj) {
		if (!(obj instanceof ComponentConfiguration)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ComponentConfiguration cconf = (ComponentConfiguration) obj;
		return componentName.equals(cconf.getComponentName());
	}
	
	public int hashCode() {
		return componentName.hashCode();
	}
}
