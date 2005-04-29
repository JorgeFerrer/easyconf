package com.germinus.easyconf;

import org.apache.commons.configuration.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Extends properties configuration by adding the system properties to the
 * properties defined in the file
 *
 * Note: this class is necessary to support using variables in the names
 * of files being included that refer to system properties
 * 
 * @author jferrer
 */
public class SysPropertiesConfiguration extends PropertiesConfiguration {
    private static final Log log = LogFactory.getLog(SysPropertiesConfiguration.class);
    private boolean sysPropertiesEnabled = false;

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
    public Object getProperty(String key) {
        Object value = null;
        if (value == null) {
            value = super.getProperty(key);
        }
        if ((value == null) && (sysPropertiesEnabled)) {
            value = System.getProperties().getProperty(key);
        }
        return value;
    }

    public void enableSysProperties() {
        sysPropertiesEnabled = true;        
    }
    public void disableSysProperties() {
        sysPropertiesEnabled = false;        
    }
}
