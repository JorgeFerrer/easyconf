package com.germinus.easyconf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


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
    public static final String NULL_STRING = null;
    private static final Log log = LogFactory.getLog(ComponentProperties.class);

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


    public Float getFloat(String key, Filter filter) {
        Float value = getFloat(key, filter, null);
        assertNotNull(key, value);
        return value;
    }
    public Float getFloat(String key, Filter filter, Float defaultValue) {
        return (Float) getPropertyWithFilter(key, filter, Float.class, defaultValue);
    }

    public Integer getInteger(String key, Filter filter) {
        Integer value = getInteger(key, filter, null);
        assertNotNull(key, value);
        return value;
    }
    public Integer getInteger(String key, Filter filter, Integer defaultValue) {
        return (Integer) getPropertyWithFilter(key, filter, Integer.class, defaultValue);
    }

    public String getString(String key, Filter filter) {
        String value = getString(key, filter, null);
        assertNotNull(key, value);
        return value;
    }
    public String getString(String key, Filter filter, String defaultValue) {
        return (String) getPropertyWithFilter(key, filter, String.class, defaultValue);
    }

    public BigDecimal getBigDecimal(String key, Filter filter) {
        BigDecimal value = getBigDecimal(key, filter, null);
        assertNotNull(key, value);
        return value;
    }
    public BigDecimal getBigDecimal(String key, Filter filter, BigDecimal defaultValue) {
        return (BigDecimal) getPropertyWithFilter(key, filter, BigDecimal.class, defaultValue);
    }
    
    public BigInteger getBigInteger(String key, Filter filter) {
        BigInteger value = getBigInteger(key, filter, null);        
        assertNotNull(key, value);
        return value;
    }
    public BigInteger getBigInteger(String key, Filter filter, BigInteger defaultValue) {
        return (BigInteger) getPropertyWithFilter(key, filter, BigInteger.class, defaultValue);
    }

    public Boolean getBoolean(String key, Filter filter) {
        Boolean value = getBoolean(key, filter, null);        
        assertNotNull(key, value);
        return value;
    }
    public Boolean getBoolean(String key, Filter filter, Boolean defaultValue)
            throws NoClassDefFoundError {
        return (Boolean) getPropertyWithFilter(key, filter, Boolean.class, defaultValue);
    }
    
    public Double getDouble(String key, Filter filter) {
        Double value = getDouble(key, filter, null);        
        assertNotNull(key, value);
        return value;
    }
    public Double getDouble(String key, Filter filter, Double defaultValue) {
        return (Double) getPropertyWithFilter(key, filter, Double.class, defaultValue);
    }

    public List getList(String key, Filter filter) {
        List value = (List) getPropertyWithFilter(key, filter, List.class, null);
        assertNotNull(key, value);
        return value;
    }
    public List getList(String key, Filter filter, List defaultValue) {
        return (List) getPropertyWithFilter(key, filter, List.class, defaultValue);
    }

    public Long getLong(String key, Filter filter) {
        Long value = getLong(key, filter, null);        
        assertNotNull(key, value);
        return value;
    }
    public Long getLong(String key, Filter filter, Long defaultValue) {
        return (Long) getPropertyWithFilter(key, filter, Long.class, defaultValue);
    }

    public Short getShort(String key, Filter filter) {
        Short value = getShort(key, filter, null);        
        assertNotNull(key, value);
        return value;
    }
    public Short getShort(String key, Filter filter, Short defaultValue) {
        return (Short) getPropertyWithFilter(key, filter, Short.class, defaultValue);
    }

    public Byte getByte(String key, Filter filter) {
        Byte value = getByte(key, filter, null);        
        assertNotNull(key, value);
        return value;
    }
    public Byte getByte(String key, Filter filter, Short defaultValue) {
        return (Byte) getPropertyWithFilter(key, filter, Byte.class, defaultValue);
    }

    // ............ Helper methods for filters ........

    public Object getPropertyWithFilter(String key, Filter filter, Class theClass, Object defaultValue) {
        Object value = null;
        for (int i = filter.size(); (i >= 0) && (value == null); i--) {
            value = getTypedPropertyWithDefault(key + filter.getFilterSuffix(i), theClass);
            log.info("Value for "+key + filter.getFilterSuffix(i) + "=" + value);
        }
        return value;
    }


    private Object getTypedPropertyWithDefault(String key, Class theClass) {
        if (theClass.equals(Float.class)) {
            return properties.getFloat(key, null);

        } else if (theClass.equals(Integer.class)) {
            return properties.getInteger(key, null);

        } else if (theClass.equals(String.class)) {
            return properties.getString(key, null);

        } else if (theClass.equals(Double.class)) {
            return properties.getDouble(key, null);

        } else if (theClass.equals(Long.class)) {
            return properties.getLong(key, null);

        } else if (theClass.equals(Boolean.class)) {
            return properties.getBoolean(key, null);

        } else if (theClass.equals(List.class)) {
            return properties.getList(key, null);

        } else if (theClass.equals(BigInteger.class)) {
            return properties.getBigInteger(key, null);

        } else if (theClass.equals(BigDecimal.class)) {
            return properties.getBigDecimal(key, null);

        } else if (theClass.equals(Byte.class)) {
            return properties.getByte(key, null);

        } else if (theClass.equals(Short.class)) {
            return properties.getShort(key, null);
        }
        return null;
    }
    private void assertNotNull(String key, Object value) {
        if (value == null) {
            throw new NoSuchElementException("Property with key="+key+"was not found");
        }
    }


}
