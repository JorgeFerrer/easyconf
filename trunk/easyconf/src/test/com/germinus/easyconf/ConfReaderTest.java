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
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * System Test of the whole functionality of easyconf.
 * Note that it depends on external files
 * 
 * @author Jorge Ferrer
 * @author Ismael F. Olmedo
 */
public class ConfReaderTest extends TestCase {

	public static final Log log =  LogFactory.getLog(ConfReaderTest.class);
	ComponentConfiguration componentConf;

    public ConfReaderTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        componentConf = ConfReader.getConfiguration("test_module");
    }

    protected void tearDown() throws Exception {
    }


    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(ConfReaderTest.class);
//        suite.addTest(new ConfReaderTest("testUsingSystemPropertiesInIncludes"));
        return suite;
    }

    public void testGetExistentClass() throws ClassNotFoundException {
        Class theClass = getProperties().getClass("com.germinus.easyconf.DatabaseConf-class");
        assertEquals("An invalid class was loaded", 
                DatabaseConf.class, theClass);
        theClass = getProperties().getClass("com.germinus.easyconf.DatabaseConf-class", Table.class);
        assertEquals("An invalid class was loaded", 
                DatabaseConf.class, theClass);
        theClass = getProperties().getClass("non-existent-property", Table.class);
        assertEquals("The default class should have been used", 
                Table.class, theClass);
    }

    public void testGetNonexistentClass() {
        try {
            Class theClass = getProperties().getClass("non-existent-class");
            fail("A ClassNotFoundException should have been thrown");
        } catch (ClassNotFoundException success) {        
        }
        try {
            Class theClass = getProperties().getClass("non-existent-class", Table.class);
            fail("A ClassNotFoundException should have been thrown");
        } catch (ClassNotFoundException success) {        
        }
    }

    public void testAllFilesHaveBeenRead() {
    	assertTrue("File test_module.properties not loaded", 
    			getProperties().containsKey("property-only-in-test-module"));
    	assertTrue("File global-configuration.properties not loaded", 
    			getProperties().containsKey("property-only-in-global-configuration"));   	
    	assertTrue("File global-configuration-env.properties not loaded", 
    			getProperties().containsKey("property-only-in-env"));
    	assertTrue("File global-configuration-prj.properties not loaded", 
    			getProperties().containsKey("property-only-in-prj"));
    }
    public void testReadStringNotOverridden() {
    	assertEquals("Invalid value for string-not-overridden", 
    				 "test_module", 
					 getProperties().getString("string-not-overridden"));
    }
    
    /**
     * Expected property keys and values:
     * long-with-filter:selector1.selector2=1234
     * short-with-filter:selector1.selector2=1234
     * int-with-filter:selector1.selector2=1234
     * byte-with-filter:selector1.selector2=123
     * biginteger-with-filter:selector1.selector2=1234
     * bigdecimal-with-filter:selector1.selector2=1234
     * double-with-filter:selector1.selector2=1234
     * float-with-filter:selector1.selector2=1234
     * list-with-filter:selector1.selector2=1234,5678
     * boolean-with-filter:selector1.selector2=false
     */
    public void testFilterWithDefault() {
        String value = getProperties().getString("property-with-filter",
                                                 Filter.by("selector1", "selector2"),
                                                 "defaultvalue");
        assertEquals("Invalid string value when specifying two selectors",
    				 "selector1-and-selector2", value);
        assertEquals("Invalid long value when specifying two selectors", 1234, 
				 	  getProperties().getLong("long-with-filter", Filter.by("selector1", "selector2"), 0l));
        assertEquals("Invalid short value when specifying two selectors", 1234, 
			 	  getProperties().getShort("short-with-filter", Filter.by("selector1", "selector2"), (short)0));
        assertEquals("Invalid int value when specifying two selectors", 1234, 
			 	  getProperties().getInt("int-with-filter", Filter.by("selector1", "selector2"), 0));
        assertEquals("Invalid byte value when specifying two selectors", 123, 
			 	  getProperties().getByte("byte-with-filter", Filter.by("selector1", "selector2"), (byte)0));
        assertEquals("Invalid BigInteger value when specifying two selectors", new BigInteger("1234"), 
			 	  getProperties().getBigInteger("biginteger-with-filter", 
			 	          Filter.by("selector1", "selector2"), new BigInteger("0")));
        assertEquals("Invalid BigDecimal value when specifying two selectors", new BigDecimal(1234), 
			 	  getProperties().getBigDecimal("bigdecimal-with-filter", 
			 	          Filter.by("selector1", "selector2"), new BigDecimal(0d)));
        assertEquals("Invalid double value when specifying two selectors", 1234d, 
			 	  getProperties().getDouble("double-with-filter", Filter.by("selector1", "selector2"), 0), 0);
        assertEquals("Invalid float value when specifying two selectors", 1234f, 
			 	  getProperties().getFloat("float-with-filter", Filter.by("selector1", "selector2"), 0), 0);
        assertEquals("Invalid boolean value when specifying two selectors", false, 
			 	  getProperties().getBoolean("boolean-with-filter", Filter.by("selector1", "selector2"), true));
        assertEquals("Invalid list value when specifying two selectors", Arrays.asList(new String[] {"1234","5678"}), 
			 	  getProperties().getList("list-with-filter", Filter.by("selector1", "selector2"), null));
        assertEquals("Invalid string array value when specifying two selectors", new String[] {"1234","5678"}, 
			 	  getProperties().getStringArray("list-with-filter", Filter.by("selector1", "selector2"), null));
    }

    public void testFilterWithoutDefault() {
        try {
            getProperties().getString("Inexistent property", Filter.by("selector1", "selector2"));
            fail("A NoSuchElementException should have been thrown");
        } catch (NoSuchElementException success) {}
        try {
            getProperties().getLong("Inexistent property", Filter.by("selector1", "selector2"));
            fail("A NoSuchElementException should have been thrown");
        } catch (NoSuchElementException success) {}
        try {
            getProperties().getShort("Inexistent property", Filter.by("selector1", "selector2"));
            fail("A NoSuchElementException should have been thrown");
        } catch (NoSuchElementException success) {}
        try {
            getProperties().getInt("Inexistent property", Filter.by("selector1", "selector2"));
            fail("A NoSuchElementException should have been thrown");
        } catch (NoSuchElementException success) {}
        try {
            getProperties().getByte("Inexistent property", Filter.by("selector1", "selector2"));
            fail("A NoSuchElementException should have been thrown");
        } catch (NoSuchElementException success) {}
        try {
            getProperties().getBigInteger("Inexistent property", Filter.by("selector1", "selector2"));
            fail("A NoSuchElementException should have been thrown");
        } catch (NoSuchElementException success) {}
        try {
            getProperties().getBigDecimal("Inexistent property", Filter.by("selector1", "selector2"));
            fail("A NoSuchElementException should have been thrown");
        } catch (NoSuchElementException success) {}
        try {
            getProperties().getDouble("Inexistent property", Filter.by("selector1", "selector2"));
            fail("A NoSuchElementException should have been thrown");
        } catch (NoSuchElementException success) {}
        try {
            getProperties().getFloat("Inexistent property", Filter.by("selector1", "selector2"));
            fail("A NoSuchElementException should have been thrown");
        } catch (NoSuchElementException success) {}
        try {
            getProperties().getList("Inexistent property", Filter.by("selector1", "selector2"));
            fail("A NoSuchElementException should have been thrown");
        } catch (NoSuchElementException success) {}
        try {
            getProperties().getStringArray("Inexistent property", Filter.by("selector1", "selector2"));
            fail("A NoSuchElementException should have been thrown");
        } catch (NoSuchElementException success) {}
    }

    public void testReadStringOverriddenInPrj() {
    	assertEquals("Invalid value for string-overridden-in-prj", 
    				 "prj", 
					 getProperties().getString("string-overridden-in-prj"));
    }
    
    public void testReadStringOverriddenInPrjWithPrefix() {
    	assertEquals("Invalid value for string-overridden-in-prj-with-prefix",
    				 "prj",
					 getProperties().getString("string-overridden-in-prj-with-prefix"));
    }

    public void testReadStringOverriddenInEnv() {
    	assertEquals("Invalid value for string-overridden-in-env", 
    				 "env", 
					 getProperties().getString("string-overridden-in-env"));
    }
    
    public void testReadStringOverriddenInPrjAndEnv() {
    	assertEquals("Invalid value for string-overridden-in-prj-and-env", 
    				 "env", 
					 getProperties().getString("string-overridden-in-prj-and-env"));
    }
    
    public void testReadListNotOverridden() {
    	List expected = Arrays.asList(new String[]{"test_module1", "test_module2"});
		assertEquals("Invalid value for list-not-overridden", 
		        expected, 
		        getProperties().getList("list-not-overridden"));
    }
    
    public void testReadListOverriddenInPrj() {
    	List expected = Arrays.asList(new String[]{"prj1", "prj2"});
    	assertEquals("Invalid value for list-overridden-in-prj", 
    				expected, 
					getProperties().getList("list-overridden-in-prj"));
    }

    public void testObjectConfiguration() {
        DatabaseAssert.assertContents(getConfigurationObject());
    }
    
    public void testVariablesInObjectConfiguration() {
        Table table1 = (Table) getConfigurationObject().getTables().get(0);
        assertEquals("The table type is not the one specified as a property",
                getProperties().getString("default.table.type"),
                table1.getTableType());
    }

    public void testComponentWithoutProperties() {
        ComponentConfiguration conf = ConfReader.getConfiguration("module_without_properties");
        assertNotNull("The properties should not be null", 
                conf.getProperties());        

    }

    public void testComponentWithoutXML() {
        ComponentConfiguration conf = ConfReader.getConfiguration("module_without_xml");
        try {
            conf.getConfigurationObject();
            fail("A Configuration exception should have been thrown");
        } catch (ConfigurationException ok) {}
    }
    
    public void testComponentWithoutDigesterRules() {
        String name = "module_without_digesterRules";
        try {
            ConfReader.getConfiguration(name);
        } catch (DigesterRulesNotFoundException expected) {
            assertNotNull("The exception should contain the name of the missing file",
                    expected.getDigesterRulesFileName());
            assertEquals("Invalid component name in the exception",
                    name, expected.getComponentName());
        }
    }

    // THIS FUNCTIONALITY DOES NOT WORK ALTHOUGH THE TEST PASSES
    public void testComponentWithDisabledCache() {
        ComponentConfiguration componentConf1 = ConfReader.getConfiguration("test_module");
        ComponentConfiguration componentConf2 = ConfReader.getConfiguration("test_module");
        assertNotSame("The configuration should have been read again",
                      componentConf1, componentConf2);
    }

    public void testUsingSystemProperties() {
        System.setProperty("easyconf-environment", "local");
        assertEquals("The environment was not correctly read from the system property",
                     "local",
                     getProperties().getString("test_module_environment"));
        System.setProperty("easyconf-environment", "");

    }

    public void testUsingSystemPropertiesInIncludes() {
        System.setProperty("easyconf-environment", "local");
        assertEquals("The file with a sysproperty in the name was not loaded",
                     "mysql",
                     getProperties().getString("test_module_db"));
        System.setProperty("easyconf-environment", "");
    }

    /**
     * Does not work due to a bug in digester (TODO: confirm)
     */
    public void bugtestXmlThatUsesNonExistentProperty() {
        String name = "module_with_xml_that_uses_non_existent_property";
        try {
            ComponentConfiguration conf = ConfReader.getConfiguration(name);
        } catch (InvalidPropertyException expected) {
            assertEquals("Invalid component name in the exception",
                    name, expected.getComponentName());
        }
        
    }

    // .............. Helper methods ...................

    ComponentProperties getProperties() {
        return componentConf.getProperties();
    }

    DatabaseConf getConfigurationObject() {
        return (DatabaseConf) componentConf.getConfigurationObject();
    }

    private void assertEquals(String msg, String[] expected, String[] obtained) {
        if (expected.length != obtained.length) {
            fail(msg + ". Expected and obtained arrays length differ");
        }
        for (int i = 0; i < expected.length; i++) {
            assertEquals(msg + ". (" + i + "th element)", expected[i], obtained[i]);
        }
    }
    

}
