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
    private List loadedFiles = new ArrayList();
    private boolean baseConfigurationLoaded = false;

    public BaseAndGlobalProperties(String componentName) {
        this.componentName = componentName;
    }

    /**
     * Look for the property in environment, global and base configuration,
     * in this order
     * @param key
     * @return
     */
    protected Object getPropertyDirect(String key) {
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
            value = super.getPropertyDirect(key);
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
        Configuration conf = addFileProperties(fileName, baseConf);
        if ((conf != null) && (!conf.isEmpty())) {
            baseConfigurationLoaded = true;
        }
    }

    public void addGlobalFileName(String fileName) {
        addFileProperties(fileName, globalConf);
    }

    private Configuration addFileProperties(String fileName, CompositeConfiguration conf) {
        try {
            SysPropertiesConfiguration newConf = new SysPropertiesConfiguration(fileName);
            newConf.enableSysProperties();            
            addIncludedFileProperties(newConf, conf);
            newConf.disableSysProperties();
            conf.addConfiguration(newConf);
            super.addConfiguration(newConf);
            loadedFiles.add(fileName);
            return newConf;
        } catch (org.apache.commons.configuration.ConfigurationException e) {
            throw new ConfigurationException("Error reading file" + fileName, e);
        } catch (Exception ignore) {
            log.debug("Configuration source " + fileName + " ignored");
            return null;
        }
    }

    private void addIncludedFileProperties(Configuration newConf, CompositeConfiguration conf) {
        String[] fileNames = newConf.getStringArray(Conventions.INCLUDE_PROPERTY);
        for (int i = fileNames.length - 1; i >= 0; i--) {
            String iteratedFileName = fileNames[i];
            log.info("Adding included file: " + iteratedFileName);
            addFileProperties(iteratedFileName, conf);
        }
//        Collections.reverse(fileNames);
//        Iterator it = fileNames.iterator();
//        while (it.hasNext()) {
//            String iteratedFileName = (String) it.next();
//            addFileProperties(iteratedFileName, conf);
//        }
    }

    public List loadedFiles() {
        return loadedFiles;
    }

    public boolean hasBaseConfiguration() {
        return baseConfigurationLoaded;
    }
}
