package com.germinus.easyconf;

import org.apache.commons.configuration.Configuration;

import java.util.Iterator;

/**
 * Utility methods
 * 
 * @author jferrer
 */
class ConfUtil {
    public static String toString(Configuration configuration) {
        Iterator it = configuration.getKeys();
        StringBuffer result = new StringBuffer();
        result.append("{");
        while (it.hasNext()) {
            String key = (String) it.next();
            result.append(key + "=" + configuration.getProperty(key));
            result.append(", ");
        }
        result.append("}");
        return result.toString();
    }


}
