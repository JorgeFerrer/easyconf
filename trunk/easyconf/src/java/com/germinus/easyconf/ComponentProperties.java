package com.germinus.easyconf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;


/**
 * Part of a component configuration which contains its properties
 * 
 * It is based on the <code>Configuration</code> interface from 
 * Jakarta Commons Configuration but it is given a different name which
 * makes more sense inside EasyConf.
 *
 * @author  Jorge Ferrer
 * @version $Revision$
 *
 */
public class ComponentProperties {
    private static final char FILTER_SEPARATOR = ':';

    Configuration properties;
    public static final String NULL_STRING = null;

    ComponentProperties(Configuration conf) {
        this.properties = conf;
    }
    
    public Map toMap() {
        Map props = new HashMap();
        
        Iterator it = properties.getKeys();
        while (it.hasNext()) {
            String key = (String) it.next();
            props.put(key, properties.getProperty(key));
        }
        return props;
    }

//    /** to be removed **/
//    Configuration getConfiguration() {
//        return properties;
//    }
    
    
    // **************** Delegate methods ***************************
    
    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    public boolean equals(Object obj) {
        return properties.equals(obj);
    }
    public BigDecimal getBigDecimal(String key) {
        return properties.getBigDecimal(key);
    }
    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        return properties.getBigDecimal(key, defaultValue);
    }
    public BigInteger getBigInteger(String key) {
        return properties.getBigInteger(key);
    }
    public BigInteger getBigInteger(String key, BigInteger defaultValue) {
        return properties.getBigInteger(key, defaultValue);
    }
    public boolean getBoolean(String key) {
        return properties.getBoolean(key);
    }
    public boolean getBoolean(String key, boolean defaultValue) {
        return properties.getBoolean(key, defaultValue);
    }
    public Boolean getBoolean(String key, Boolean defaultValue)
            throws NoClassDefFoundError {
        return properties.getBoolean(key, defaultValue);
    }
    public byte getByte(String key) {
        return properties.getByte(key);
    }
    public byte getByte(String key, byte defaultValue) {
        return properties.getByte(key, defaultValue);
    }
    public Byte getByte(String key, Byte defaultValue) {
        return properties.getByte(key, defaultValue);
    }
    public double getDouble(String key) {
        return properties.getDouble(key);
    }
    public double getDouble(String key, double defaultValue) {
        return properties.getDouble(key, defaultValue);
    }
    public Double getDouble(String key, Double defaultValue) {
        return properties.getDouble(key, defaultValue);
    }
    public float getFloat(String key) {
        return properties.getFloat(key);
    }
    public float getFloat(String key, float defaultValue) {
        return properties.getFloat(key, defaultValue);
    }
    public Float getFloat(String key, Float defaultValue) {
        return properties.getFloat(key, defaultValue);
    }
    public int getInt(String key) {
        return properties.getInt(key);
    }
    public int getInt(String key, int defaultValue) {
        return properties.getInt(key, defaultValue);
    }
    public Integer getInteger(String key, Integer defaultValue) {
        return properties.getInteger(key, defaultValue);
    }
    public Iterator getKeys() {
        return properties.getKeys();
    }
    public Iterator getKeys(String prefix) {
        return properties.getKeys(prefix);
    }
    public List getList(String key) {
        return properties.getList(key);
    }
    public List getList(String key, List defaultValue) {
        return properties.getList(key, defaultValue);
    }
    public long getLong(String key) {
        return properties.getLong(key);
    }
    public Long getLong(String key, Long defaultValue) {
        return properties.getLong(key, defaultValue);
    }
    public long getLong(String key, long defaultValue) {
        return properties.getLong(key, defaultValue);
    }
    public java.util.Properties getProperties(String key) {
        return properties.getProperties(key);
    }
    public Object getProperty(String key) {
        return properties.getProperty(key);
    }
    public short getShort(String key) {
        return properties.getShort(key);
    }
    public Short getShort(String key, Short defaultValue) {
        return properties.getShort(key, defaultValue);
    }
    public short getShort(String key, short defaultValue) {
        return properties.getShort(key, defaultValue);
    }
    public String getString(String key) {
        return properties.getString(key);
    }
    public String getString(String key, String defaultValue) {
        return properties.getString(key, defaultValue);
    }
    public String[] getStringArray(String key) {
        return properties.getStringArray(key);
    }
    public int hashCode() {
        return properties.hashCode();
    }
    public boolean isEmpty() {
        return properties.isEmpty();
    }
    public void setProperty(String key, Object value) {
        properties.setProperty(key, value);
    }
    public Configuration subset(String prefix) {
        return properties.subset(prefix);
    }
    public String toString() {
        return properties.toString();
    }

    // ............ Property methods with filter ................

    public String getString(String key, Filter filter) {
        String value = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getString(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                              NULL_STRING);
            if (value != NULL_STRING) {
                break;
            }
        }
        if (value == null) {
            value = properties.getString(key);
        }
        return value;
    }

    public String getString(String key, String defaultValue, Filter filter) {
        String value = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getString(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                              NULL_STRING);
            if (value != NULL_STRING) {
                break;
            }
        }
        if (value == null) {
            value = properties.getString(key, defaultValue);
        }
        return value;
    }

    public BigDecimal getBigDecimal(String key, Filter filter) {
        BigDecimal value = null;
        final BigDecimal nullValue = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getBigDecimal(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                                  nullValue);
            if (value != nullValue) {
                break;
            }
        }
        if (value == null) {
            value = properties.getBigDecimal(key);
        }
        return value;
    }
    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue, Filter filter) {
        BigDecimal value = null;
        final BigDecimal nullValue = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getBigDecimal(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                                  nullValue);
            if (value != nullValue) {
                break;
            }
        }
        if (value == null) {
            value = properties.getBigDecimal(key, defaultValue);
        }
        return value;
    }
    public BigInteger getBigInteger(String key, Filter filter) {
        BigInteger value = null;
        final BigInteger nullValue = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getBigInteger(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                                  nullValue);
            if (value != nullValue) {
                break;
            }
        }
        if (value == null) {
            value = properties.getBigInteger(key);
        }
        return value;
    }
    public BigInteger getBigInteger(String key, BigInteger defaultValue, Filter filter) {
        BigInteger value = null;
        final BigInteger nullValue = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getBigInteger(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                                  nullValue);
            if (value != nullValue) {
                break;
            }
        }
        if (value == null) {
            value = properties.getBigInteger(key, defaultValue);
        }
        return value;
    }

    public Boolean getBoolean(String key, Boolean defaultValue, Filter filter)
            throws NoClassDefFoundError {
        Boolean value = null;
        final Boolean nullValue = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getBoolean(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                                  nullValue);
            if (value != nullValue) {
                break;
            }
        }
        if (value == null) {
            value = properties.getBoolean(key, defaultValue);
        }
        return value;
    }

    public Double getDouble(String key, Double defaultValue, Filter filter) {
        Double value = null;
        final Double nullValue = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getDouble(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                                  nullValue);
            if (value != nullValue) {
                break;
            }
        }
        if (value == null) {
            value = properties.getDouble(key, defaultValue);
        }
        return value;
    }
    public Float getFloat(String key, Float defaultValue, Filter filter) {
        Float value = null;
        final Float nullValue = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getFloat(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                                  nullValue);
            if (value != nullValue) {
                break;
            }
        }
        if (value == null) {
            value = properties.getFloat(key, defaultValue);
        }
        return value;
    }
    public Integer getInteger(String key, Integer defaultValue, Filter filter) {
        Integer value = null;
        final Integer nullValue = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getInteger(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                                  nullValue);
            if (value != nullValue) {
                break;
            }
        }
        if (value == null) {
            value = properties.getInteger(key, defaultValue);
        }
        return value;
    }
    //--
    public List getList(String key, Filter filter) {
        List value = null;
        final List nullValue = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getList(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                                  nullValue);
            if (value != nullValue) {
                break;
            }
        }
        if (value == null) {
            value = properties.getList(key);
        }
        return value;
    }
    public List getList(String key, List defaultValue, Filter filter) {
        List value = null;
        final List nullValue = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getList(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                                  nullValue);
            if (value != nullValue) {
                break;
            }
        }
        if (value == null) {
            value = properties.getList(key, defaultValue);
        }
        return value;
    }
    public Long getLong(String key, Long defaultValue, Filter filter) {
        Long value = null;
        final Long nullValue = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getLong(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                                  nullValue);
            if (value != nullValue) {
                break;
            }
        }
        if (value == null) {
            value = properties.getLong(key, defaultValue);
        }
        return value;
    }
    public Short getShort(String key, Short defaultValue, Filter filter) {
        Short value = null;
        final Short nullValue = null;
        for (int i = filter.size(); i > 0; i++) {
            value = getShort(key + FILTER_SEPARATOR + filter.getFilterFragment(i),
                                  nullValue);
            if (value != nullValue) {
                break;
            }
        }
        if (value == null) {
            value = properties.getShort(key, defaultValue);
        }
        return value;
    }

}
