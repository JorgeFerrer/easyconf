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

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Substitutor;
import org.apache.commons.digester.substitution.MultiVariableExpander;
import org.apache.commons.digester.substitution.VariableSubstitutor;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Handles the actual reading of the configuration
 * 
 * @author jferrer
 */
class ConfigurationLoader {
    public static final String DEFAULT_ENV = "default";
    public static final String OVERRIDEN_PROPERTIES_FILES_PROPERTY = "include-and-override";
    private static final String GLOBAL_CONFIGURATION_FILE = "global-configuration";
    private static final String ENVIRONMENT_NAME_VARIABLE = "easyconf.env.name";
    private static final Log log = LogFactory.getLog(ConfigurationLoader.class);


//    public ComponentConfiguration readComponentConfiguration(String componentName)
//            throws IOException, SAXException {
//        ComponentConfiguration conf = new ComponentConfiguration(componentName);
//        conf.setProperties(readPropertiesConfiguration(componentName));
//        conf.setObjectGraph(readConfigurationObject(componentName,
//                					conf.getProperties()));
//        return conf;
//    }

    public ComponentProperties readPropertiesConfiguration(String componentName) {
        BaseAndGlobalProperties properties = new BaseAndGlobalProperties(componentName);
        properties.addGlobalFileName(GLOBAL_CONFIGURATION_FILE + ".properties");
        properties.addBaseFileName(componentName + ".properties");

        log.info("Properties for " + componentName + " loaded from " + properties.loadedFiles());
        return new ComponentProperties(properties);
    }

    public ComponentProperties readPropertiesConfiguration1(String componentName) {
        List environmentFileNames = getEnvironmentFileNames(componentName);
        Configuration envConf = readPropertiesFromFiles(environmentFileNames);
        List baseFileNames = getBaseFileNames(componentName);
        Configuration baseConf = readPropertiesFromFiles(baseFileNames);
        List filesWithOverriddenProperties = getFilesWithOverriddenProperties(baseConf);
        Configuration overriddenConf = readPropertiesFromFiles(filesWithOverriddenProperties);

        List loadOrder = new ArrayList();
        loadOrder.addAll(environmentFileNames);
        loadOrder.addAll(filesWithOverriddenProperties);
        loadOrder.addAll(baseFileNames);
        log.info("Override order for " + componentName + " (first files override last): " +
                 loadOrder);

        CompositeConfiguration componentConf = new CompositeConfiguration();
        componentConf.addConfiguration(envConf.subset(componentName));
        componentConf.addConfiguration(envConf);
        componentConf.addConfiguration(overriddenConf.subset(componentName));
        componentConf.addConfiguration(overriddenConf);
        componentConf.addConfiguration(baseConf.subset(componentName));
        componentConf.addConfiguration(baseConf);

        return new ComponentProperties(componentConf);
    }

    private CompositeConfiguration readPropertiesFromFiles(List fileNames) {
        CompositeConfiguration compositeConf = new CompositeConfiguration();
        Iterator confSources = fileNames.iterator();
        while (confSources.hasNext()) {
            String confSource = (String) confSources.next();
            try {
            	Configuration newConfiguration;
            	newConfiguration = new PropertiesConfiguration(confSource);
        	    compositeConf.addConfiguration(newConfiguration);
                log.debug("Read configuration from " + confSource);
            } catch (Exception ignore) {
                log.debug("Configuration source " + confSource + " ignored");
            }
        }
        return compositeConf;
    }

    /**
     * Get an ordered list of configuration sources. The most relevant are
     * returned first.
     * @param componentName The name of the component
     */
    private List getBaseFileNames(String componentName) {
        List sources = new ArrayList();

        sources.add(GLOBAL_CONFIGURATION_FILE + ".properties");
        sources.add(componentName + ".properties");

        return sources;
    }

    /**
     * Get an ordered list of configuration sources. The most relevant are
     * returned first.
     * @param componentName The name of the component
     */
    private List getEnvironmentFileNames(String componentName) {
        String environmentName = System.getProperty(ENVIRONMENT_NAME_VARIABLE, DEFAULT_ENV);
        List sources = new ArrayList();

        if (environmentName != DEFAULT_ENV) {
            sources.add(GLOBAL_CONFIGURATION_FILE + "." + environmentName + ".properties");
            sources.add(componentName + "." + environmentName + ".properties");
        }
        return sources;
    }

    private List getFilesWithOverriddenProperties(Configuration componentConf) {
        List files = componentConf.getList(OVERRIDEN_PROPERTIES_FILES_PROPERTY);
        Collections.reverse(files);
        return files;
    }

    public Object readConfigurationObject(String componentName,
            ComponentProperties properties) throws IOException, SAXException {
        String rulesFileName = componentName + "." + "digesterRules.xml";
        String confFileName = componentName + ".xml";

        URL confFile = ClasspathUtil.locateResource(null, confFileName);
        if (confFile == null) {
            throw new FileNotFoundException("File " + confFileName + " not found");
        }
        URL digesterRulesUrl = ClasspathUtil.locateResource(rulesFileName);
        if (digesterRulesUrl == null) {
            throw new DigesterRulesNotFoundException(componentName,
                    rulesFileName);
        }
        Digester digester = DigesterLoader.createDigester(digesterRulesUrl);
        digester.setUseContextClassLoader(true);
        digester.setValidating(false);

        MultiVariableExpander expander = new MultiVariableExpander();
        expander.addSource("$", properties.toMap());
        Substitutor substitutor = new VariableSubstitutor(expander);
        digester.setSubstitutor(substitutor);

        try {
            Object objConf = digester.parse(confFile.openStream());
            log.info("Read configuration from " + confFileName);
            return objConf;
        } catch (IllegalArgumentException e) {
            //FIXME: it does not catch the exception
            throw new InvalidPropertyException(componentName, e);
        }
    }

}
