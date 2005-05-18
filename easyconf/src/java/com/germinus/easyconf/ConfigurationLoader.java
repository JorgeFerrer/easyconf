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

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Substitutor;
import org.apache.commons.digester.substitution.MultiVariableExpander;
import org.apache.commons.digester.substitution.VariableSubstitutor;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Handles the actual reading of the configuration
 * 
 * @author jferrer
 */
class ConfigurationLoader {
    private static final Log log = LogFactory.getLog(ConfigurationLoader.class);


    public ComponentProperties readPropertiesConfiguration(String companyId, String componentName) {
        AggregatedProperties properties = new AggregatedProperties(companyId, componentName);
        if (companyId != null) {
            properties.addGlobalFileName(Conventions.GLOBAL_CONFIGURATION_FILE + Conventions.SLASH + companyId
                    + Conventions.PROPERTIES_EXTENSION);
        }
        properties.addGlobalFileName(Conventions.GLOBAL_CONFIGURATION_FILE + 
                Conventions.PROPERTIES_EXTENSION);
        if (companyId != null) {
            properties.addBaseFileName(componentName + Conventions.SLASH + companyId + 
                    Conventions.PROPERTIES_EXTENSION);
        }
        properties.addBaseFileName(componentName + Conventions.PROPERTIES_EXTENSION);

        log.info("Properties for " + componentName + " loaded from " + properties.loadedSources());
        return new ComponentProperties(properties);
    }

    public Object readConfigurationObject(String companyId,
            String componentName, ComponentProperties properties) 
    	throws IOException, SAXException {
        log.info("Reading the configuration object for " + componentName);
        String rulesFileName = componentName + Conventions.DIGESTERRULES_EXTENSION;

        String confFileName = null;
        URL confFile = null;
        if (companyId != null) {
            confFileName = componentName + Conventions.SLASH + companyId + Conventions.XML_EXTENSION;
            confFile = ClasspathUtil.locateResource(null, confFileName);
            log.info("Loaded " + confFileName + ": " + confFile);
        }
        if (confFile == null) { 
	        confFileName = componentName + Conventions.XML_EXTENSION;
	        confFile = ClasspathUtil.locateResource(null, confFileName);
        }
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
