package com.germinus.easyconf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Main class to obtain the configuration of a software component.
 *
 * The main method is <code>getComponentConfiguration</code> which must be
 * given the name of a component.
 *
 * @author jferrer@germinus.com
 */
public class ConfReader {
	private static final ConfReader instance = new ConfReader();
    private static final Log log = LogFactory.getLog(ConfReader.class);
    private static Map cache = new HashMap();

    public static ConfReader getInstance() {
        return instance;
    }

    private ConfReader() {
    }

    /**
     * Get the full configuration of the given component
     * @param componentName any String which can be used to identified a
     * configuration component.
     * @return a <code>ComponentConf</code> instance 
     */
    public ComponentConfiguration getComponentConfiguration(String componentName) {
        try {
            ComponentConfiguration componentConf = (ComponentConfiguration)
            cache.get(componentName);
            if ((componentConf == null) || (!componentConf.isCacheEnabled())) {
                componentConf = new ComponentConfiguration(componentName);
                cache.put(componentName, componentConf);
            }
            return componentConf;
        } catch (ConfigurationException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigurationException(componentName, 
                    "Error reading the configuration", e); 
        }
    
    }

    /**
     * Refresh the configuration of the given component
     * 
     * KNOWN BUG: this method does not refresh the properties configuration because the underlying
     * library Jakarta Commons Configuration also contains a cache which is not refreshable. This 
     * issue is scheduled to be solved after version 1.0 of such library. 
     */
    public void refreshComponent(String componentName) {
        String entryName = componentName;
        Object componentConf = cache.get(entryName);
        if (componentConf != null) {
            cache.remove(componentConf);
            log.info("Refreshed the configuration of component " + entryName);
        }
    }
    /**
     * Refresh the configuration of all components
     * 
     * KNOWN BUG: this method does not refresh the properties configuration because the underlying
     * library Jakarta Commons Configuration also contains a cache which is not refreshable. This 
     * issue is scheduled to be solved after version 1.0 of such library. 
     */
    public void refreshAll() {
        cache = new HashMap();
        log.info("Refreshed the configuration of all components");
    }

    // ************************** Deprecated methods ***************************

//    /**
//     * Get the properties based configuration of the given component.
//     * @deprecated use getComponentConf() instead.
//     */
//    public Configuration getConfiguration(String componentName) {
//        return getComponentConfiguration(componentName).getProperties().
//        	getConfiguration();
//    }




}
