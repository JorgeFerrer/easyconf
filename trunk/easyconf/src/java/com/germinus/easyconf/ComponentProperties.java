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
    Configuration properties;
    
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
}
