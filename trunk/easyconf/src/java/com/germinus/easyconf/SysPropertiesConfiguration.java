package com.germinus.easyconf;

import org.apache.commons.configuration.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Extends properties configuration by adding the system properties to the
 * properties defined in the file
 *
 * @author jferrer
 */
public class SysPropertiesConfiguration extends PropertiesConfiguration {
    private static final Log log = LogFactory.getLog(SysPropertiesConfiguration.class);

    public SysPropertiesConfiguration(String fileName)
            throws org.apache.commons.configuration.ConfigurationException {
        super(fileName);
    }

    /**
     * Look for key in the system properties and if it is not found look
     * for it in the file associated with this instance
     * @param key
     * @return
     */
    protected Object getPropertyDirect(String key) {
        Object value = null;
        if (value == null) {
            value = System.getProperties().getProperty(key);
        }
        if (value == null) {
            value = super.getPropertyDirect(key);
        }
        log.debug(getFileName()+": "+key + "=" + value);        
        return value;
    }
}
