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

import org.apache.commons.configuration.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import javax.naming.NamingException;

/**
 * Provides configuration properties from several sources making distintion from:
 * <ul>
 *   <li>Base properties specific to the current component
 *   <li>Global properties which may be prefixed
 *   <li>System properties (so that they are available as variables to the
 *       other property files)
 * </ul>
 * It also knows the source the a property to offer user information.
 * 
 * @author jferrer
 */
public class BaseAndGlobalProperties extends CompositeConfiguration {
    private static final String PREFIX_SEPARATOR = ":";
    private static final Log log = LogFactory.getLog(BaseAndGlobalProperties.class);

    private CompositeConfiguration baseConf = new CompositeConfiguration();
    private CompositeConfiguration globalConf = new CompositeConfiguration();
    private String componentName;
    private String companyId;
    private List loadedSources = new ArrayList();
    private boolean baseConfigurationLoaded = false;

    public BaseAndGlobalProperties(String companyId, String componentName) {
        this.componentName = componentName;
        this.companyId = companyId;
    }

    /**
     * Look for the property in environment, global and base configuration,
     * in this order
     * @param key
     * @return
     */
    public Object getProperty(String key) {
        Object value = null;
        if (value == null) {
            value = System.getProperty(getPrefix() + key);
        }
        if (value == null) {
            value = globalConf.getProperty(getPrefix() + key);
        }
        if (value == null) {
            value = globalConf.getProperty(key);
        }
        if (value == null) {
            value = baseConf.getProperty(key);
        }
        if (value == null) {
            value = super.getProperty(key);
        }
        if (value == null) {
            value = System.getProperty(key);
        }
        return value;
    }

    private String getPrefix() {
        return componentName + PREFIX_SEPARATOR;
    }


    public void addBaseFileName(String fileName) {
        Configuration conf = addPropertiesSource(fileName, baseConf);
        if ((conf != null) && (!conf.isEmpty())) {
            baseConfigurationLoaded = true;
        }
    }

    public void addGlobalFileName(String fileName) {
        addPropertiesSource(fileName, globalConf);
    }

    private Configuration addPropertiesSource(String sourceName, 
                                              CompositeConfiguration loadedConf) {
        try {
            Configuration newConf;
            if (DatasourceURL.isDatasource(sourceName)) {
                newConf = addDatasourceProperties(sourceName);
            } else if (JndiURL.isJndi(sourceName)) {
                newConf = addJndiProperties(sourceName);
            } else {
	            newConf = addFileProperties(sourceName, loadedConf);
            }
            
	        loadedConf.addConfiguration(newConf);	        
	        super.addConfiguration(newConf);
	        loadedSources.add(sourceName);
            return newConf;
        } catch (Exception ignore) {
            log.debug("Configuration source " + sourceName + " ignored");
            return null;
        }
    }

    private Configuration addFileProperties(String sourceName, 
                                            CompositeConfiguration loadedConf) 
    	throws ConfigurationException {
        try {
	        Configuration newConf = new SysPropertiesConfiguration(sourceName);
	        ((SysPropertiesConfiguration)newConf).enableSysProperties();            
	        addIncludedPropertiesSources(newConf, loadedConf);
	        ((SysPropertiesConfiguration)newConf).disableSysProperties();
	        return newConf;
        } catch (org.apache.commons.configuration.ConfigurationException e) {
            log.debug("Configuration source " + sourceName + " ignored");
            return null;
        }
    }

    //TODO: Add support for ASP applications
    private Configuration addDatasourceProperties(String datasourcePath) {
        DatasourceURL dsUrl = new DatasourceURL(datasourcePath, companyId, componentName);
        return dsUrl.getConfiguration();
    }

    //TODO: Add support for ASP applications
    private Configuration addJndiProperties(String sourcePath) {
        JNDIConfiguration conf = null;
        JndiURL jndiUrl = new JndiURL(sourcePath, companyId, componentName);
        return jndiUrl.getConfiguration();
    }

    private void addIncludedPropertiesSources(Configuration newConf, 
                                              CompositeConfiguration loadedConf) {
        String[] fileNames = newConf.getStringArray(Conventions.INCLUDE_PROPERTY);
        for (int i = fileNames.length - 1; i >= 0; i--) {
            String iteratedFileName = fileNames[i];
            log.info("Adding included file: " + iteratedFileName);
            addPropertiesSource(iteratedFileName, loadedConf);
        }
    }

    public List loadedSources() {
        return loadedSources;
    }

    public boolean hasBaseConfiguration() {
        return baseConfigurationLoaded;
    }
}
