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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.configuration.beanutils.ConfigurationDynaBean;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Part of a component configuration which contains its properties.
 * 
 * The properties can be accessed by type and automatic conversion will
 * be performed. The supported types are: BigDecimal, BigInteger,
 * Boolean, Byte, Double, Float, Integer, List, Long, Short, String
 * and StringArray
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

    BaseAndGlobalProperties properties;
    public static final String NULL_STRING = null;
    private static final Log log = LogFactory.getLog(ComponentProperties.class);

    ComponentProperties(BaseAndGlobalProperties conf) {
        this.properties = conf;
    }
    
    // .................. Conversion methods ...................
    
    public Map toMap() {
        Map props = new HashMap();
        
        Iterator it = properties.getKeys();
        while (it.hasNext()) {
            String key = (String) it.next();
            props.put(key, properties.getProperty(key));
        }
        return props;
    }
    
    public DynaBean toDynaBean() {
        return new ConfigurationDynaBean(properties);
    }
    
  
    public java.util.Properties getProperties(String key) {
        return properties.getProperties(key);
    }
    public Object getProperty(String key) {
        return properties.getProperty(key);
    }

    // ..................... Delegated methods ...............
    
    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    public boolean equals(Object obj) {
        return properties.equals(obj);
    }

    public Iterator getKeys() {
        return properties.getKeys();
    }
    public Iterator getKeys(String prefix) {
        return properties.getKeys(prefix);
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

    // ..................... BigDecimal ......................
    
    public BigDecimal getBigDecimal(String key) {
        return properties.getBigDecimal(key);
    }
    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        return properties.getBigDecimal(key, defaultValue);
    }

    public BigDecimal getBigDecimal(String key, Filter filter) {
        BigDecimal value = getBigDecimal(key, filter, null);
        assertNotNull(key, value);
        return value;
    }
    public BigDecimal getBigDecimal(String key, Filter filter, BigDecimal defaultValue) {
        return (BigDecimal) getPropertyWithFilter(key, filter, BigDecimal.class, defaultValue);
    }
    
    // ..................... BigInteger ......................
    
    public BigInteger getBigInteger(String key) {
        return properties.getBigInteger(key);
    }
    public BigInteger getBigInteger(String key, BigInteger defaultValue) {
        return properties.getBigInteger(key, defaultValue);
    }

    public BigInteger getBigInteger(String key, Filter filter) {
        BigInteger value = getBigInteger(key, filter, null);        
        assertNotNull(key, value);
        return value;
    }
    public BigInteger getBigInteger(String key, Filter filter, BigInteger defaultValue) {
        return (BigInteger) getPropertyWithFilter(key, filter, BigInteger.class, defaultValue);
    }
    
    // ..................... Boolean ......................
    
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

    public boolean getBoolean(String key, Filter filter) {
        Boolean value = getBoolean(key, filter, null);        
        assertNotNull(key, value);
        return value.booleanValue();
    }
    public Boolean getBoolean(String key, Filter filter, Boolean defaultValue)
            throws NoClassDefFoundError {
        return (Boolean) getPropertyWithFilter(key, filter, Boolean.class, defaultValue);
    }
    public boolean getBoolean(String key, Filter filter, boolean defaultValue) {
        return getBoolean(key, filter, new Boolean(defaultValue)).booleanValue();
    }
    
    // ..................... Byte ......................
    
    public byte getByte(String key) {
        return properties.getByte(key);
    }
    public byte getByte(String key, byte defaultValue) {
        return properties.getByte(key, defaultValue);
    }
    public Byte getByte(String key, Byte defaultValue) {
        return properties.getByte(key, defaultValue);
    }

    public byte getByte(String key, Filter filter) {
        Byte value = getByte(key, filter, null);        
        assertNotNull(key, value);
        return value.byteValue();
    }
    public Byte getByte(String key, Filter filter, Byte defaultValue) {
        return (Byte) getPropertyWithFilter(key, filter, Byte.class, defaultValue);
    }
    public byte getByte(String key, Filter filter, byte defaultValue) {
        return getByte(key,filter,new Byte(defaultValue)).byteValue();       
    }
    
    // ..................... Double ......................
    
    public double getDouble(String key) {
        return properties.getDouble(key);
    }
    public double getDouble(String key, double defaultValue) {
        return properties.getDouble(key, defaultValue);
    }
    public Double getDouble(String key, Double defaultValue) {
        return properties.getDouble(key, defaultValue);
    }

    public double getDouble(String key, Filter filter) {
        Double value = getDouble(key, filter, null);        
        assertNotNull(key, value);
        return value.doubleValue();
    }
    public Double getDouble(String key, Filter filter, Double defaultValue) {
        return (Double) getPropertyWithFilter(key, filter, Double.class, defaultValue);
    }
    public double getDouble(String key, Filter filter, double defaultValue) {
        return getDouble(key, filter, new Double(defaultValue)).doubleValue();
    }
        
    // ..................... Class ......................  

    /**
     * Get the <code>Class</code> representation of the class name
     * specified in the given property
     * @throws ClassNotFoundException if the specified class is not found
     */
    public Class getClass(String key) throws ClassNotFoundException {
        String className = getString(key);
        return ClasspathUtil.locateClass(className);
    }
    
    /**
     * Get the <code>Class</code> representation of the class name
     * specified in the given property. Or the <code>defaultValue</code>
     * if no value has been given to the property. 
     * 
     * @throws ClassNotFoundException if a class has been configured
     * but it is not found
     */
    public Class getClass(String key, Class defaultValue) throws ClassNotFoundException {
        String className = getString(key);
        if (className == null) {
            return defaultValue;
        }
        return ClasspathUtil.locateClass(className);
    }

    /**
     * Similar to the previous methods but complementing the property key with the given filter
     */
    public Class getClass(String key, Filter filter) throws ClassNotFoundException {
        String className = getString(key, filter);
        return ClasspathUtil.locateClass(className);
    }
    /**
     * Equivalent to the previous method but giving a default value which will be used if no
     * value has been specified in the configurations file
     */
    public Class getClass(String key, Filter filter, Class defaultValue) throws ClassNotFoundException {
        String className = getString(key, filter);
        if (className == null) {
            return defaultValue;
        }
        return ClasspathUtil.locateClass(className);
    }
    
    // ..................... Float ......................
    
    public float getFloat(String key) {
        return properties.getFloat(key);
    }
    public float getFloat(String key, float defaultValue) {
        return properties.getFloat(key, defaultValue);
    }
    public Float getFloat(String key, Float defaultValue) {
        return properties.getFloat(key, defaultValue);
    }

    public float getFloat(String key, Filter filter) {
        Float value = getFloat(key, filter, null);
        assertNotNull(key, value);
        return value.floatValue();
    }
    public Float getFloat(String key, Filter filter, Float defaultValue) {
        return (Float) getPropertyWithFilter(key, filter, Float.class, defaultValue);
    }
    public float getFloat(String key, Filter filter, float defaultValue) {
        return getFloat(key, filter, new Float(defaultValue)).floatValue();
    }
    
    // ..................... Integer ......................  
    
    public int getInt(String key) {
        return properties.getInt(key);
    }
    public int getInt(String key, int defaultValue) {
        return properties.getInt(key, defaultValue);
    }
    public Integer getInteger(String key, Integer defaultValue) {
        return properties.getInteger(key, defaultValue);
    }

    public int getInt(String key, Filter filter) {
        Integer value = getInteger(key, filter, null);
        assertNotNull(key, value);
        return value.intValue();
    }
    public Integer getInteger(String key, Filter filter, Integer defaultValue) {
        return (Integer) getPropertyWithFilter(key, filter, Integer.class, defaultValue);
    }
    public int getInt(String key, Filter filter, int defaultValue) {
        return getInteger(key,filter, new Integer(defaultValue)).intValue();
    }
    
    // ..................... List ......................  

    public List getList(String key) {
        return properties.getList(key);
    }
    public List getList(String key, List defaultValue) {
        return properties.getList(key, defaultValue);
    }

    public List getList(String key, Filter filter) {
        List value = (List) getPropertyWithFilter(key, filter, List.class, null);
        assertNotNull(key, value);
        return value;
    }
    public List getList(String key, Filter filter, List defaultValue) {
        return (List) getPropertyWithFilter(key, filter, List.class, defaultValue);
    }
    
    // ..................... Long ......................  

    public long getLong(String key) {
        return properties.getLong(key);
    }
    public Long getLong(String key, Long defaultValue) {
        return properties.getLong(key, defaultValue);
    }
    public long getLong(String key, long defaultValue) {
        return properties.getLong(key, defaultValue);
    }

    public long getLong(String key, Filter filter) {
        Long value = getLong(key, filter, null);        
        assertNotNull(key, value);
        return value.longValue();
    }
    public Long getLong(String key, Filter filter, Long defaultValue) {
        return (Long) getPropertyWithFilter(key, filter, Long.class, defaultValue);
    }
    public long getLong(String key, Filter filter, long defaultValue) {
        return getLong(key, filter, new Long(defaultValue)).longValue();
    }
    
    // ..................... Short ......................  

    public short getShort(String key) {
        return properties.getShort(key);
    }
    public Short getShort(String key, Short defaultValue) {
        return properties.getShort(key, defaultValue);
    }
    public short getShort(String key, short defaultValue) {
        return properties.getShort(key, defaultValue);
    }

    public short getShort(String key, Filter filter) {
        Short value = getShort(key, filter, null);        
        assertNotNull(key, value);
        return value.shortValue();
    }
    public Short getShort(String key, Filter filter, Short defaultValue) {
        return (Short) getPropertyWithFilter(key, filter, Short.class, defaultValue);
    }
    public short getShort(String key, Filter filter, short defaultValue) {
        return getShort(key,filter,new Short(defaultValue)).shortValue();
    }
    
    // ..................... String ......................  

    public String getString(String key) {
        return properties.getString(key);
    }
    public String getString(String key, String defaultValue) {
        return properties.getString(key, defaultValue);
    }

    public String getString(String key, Filter filter) {
        String value = getString(key, filter, null);
        assertNotNull(key, value);
        return value;
    }
    public String getString(String key, Filter filter, String defaultValue) {
        return (String) getPropertyWithFilter(key, filter, String.class, defaultValue);
    }
    
    // ..................... StringArray ......................  

    public String[] getStringArray(String key) {
        return properties.getStringArray(key);
    }

    public String[] getStringArray(String key, Filter filter) {
        List value = getList(key, filter);
        if (value == null) {
            return null;
        } else {
            return (String[]) value.toArray(new String[0]);
        }
    }
    public String[] getStringArray(String key, Filter filter, String[] defaultValue) {
        List defaultList = null;
        if (defaultValue != null) {
            defaultList = Arrays.asList(defaultValue);
        }
        List value = getList(key, filter, defaultList);
        if (value == null) {
            return null;
        } else {
            return (String[]) value.toArray(new String[0]);
        }
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


    public boolean hasBaseConfiguration() {
        return properties.hasBaseConfiguration();
    }
}
