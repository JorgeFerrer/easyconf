package com.germinus.easyconf;

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
 */
public class ConfReaderTest extends TestCase {

	public static final Log log =  LogFactory.getLog(ConfReaderTest.class);
	ComponentConfiguration componentConf;

    public ConfReaderTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        componentConf = ConfReader.getInstance().getComponentConfiguration("test_module");
    }

    protected void tearDown() throws Exception {
    }


    public static Test suite() {
        TestSuite suite = new TestSuite();
//        suite.addTestSuite(ConfReaderTest.class);
        suite.addTest(new ConfReaderTest("testFilter"));
        return suite;
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
    
    public void testFilter() {
        Object value = getProperties().getString("property-with-filter",
                                                 Filter.by("selector1", "selector2"),
                                                 "defaultvalue");
        assertEquals("Invalid value when specifying two selectors",
    				 "selector1-and-selector2", value);
        try {
            value = getProperties().getString("Inexistent property",
                    Filter.by("selector1", "selector2"));
            value = getProperties().getList("Inexistent property",
                    Filter.by("selector1", "selector2"));
            value = getProperties().getBigDecimal("Inexistent property",
                    Filter.by("selector1", "selector2"));
            value = getProperties().getBigInteger("Inexistent property",
                    Filter.by("selector1", "selector2"));
            value = getProperties().getLong("Inexistent property",
                    Filter.by("selector1", "selector2"));
            value = getProperties().getShort("Inexistent property",
                    Filter.by("selector1", "selector2"));
            value = getProperties().getDouble("Inexistent property",
                    Filter.by("selector1", "selector2"));
            value = getProperties().getByte("Inexistent property",
                    Filter.by("selector1", "selector2"));
        fail("A NoSuchElementException should have been thrown");
        } catch (NoSuchElementException success) {
        }
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
        ComponentConfiguration conf = ConfReader.getInstance().
        	getComponentConfiguration("module_without_properties");
        assertNotNull("The properties should not be null", 
                conf.getProperties());        

    }

    public void testComponentWithoutXML() {
        ComponentConfiguration conf = ConfReader.getInstance().
        	getComponentConfiguration("module_without_xml");
        try {
            conf.getConfigurationObject();
            fail("A Configuration exception should have been thrown");
        } catch (ConfigurationException ok) {}
    }
    
    public void testComponentWithoutDigesterRules() {
        String name = "module_without_digesterRules";
        try {
            ConfReader.getInstance().getComponentConfiguration(name);
        } catch (DigesterRulesNotFoundException expected) {
            assertNotNull("The exception should contain the name of the missing file",
                    expected.getDigesterRulesFileName());
            assertEquals("Invalid component name in the exception",
                    name, expected.getComponentName());
        }
    }

    // THIS FUNCTIONALITY DOES NOT WORK ALTHOUGH THE TEST PASSES
    public void testComponentWithDisabledCache() {
        ComponentConfiguration componentConf1 = ConfReader.getInstance().getComponentConfiguration("test_module");
        ComponentConfiguration componentConf2 = ConfReader.getInstance().getComponentConfiguration("test_module");
        assertNotSame("The configuration should have been read again",
                      componentConf1, componentConf2);
    }

    /** To Run this test you have to execute with -D=easyconf:environment=local */
    public void inactive_testUsingSystemProperties() {
        ComponentConfiguration componentConf = ConfReader.getInstance().getComponentConfiguration("test_module");
        String sysProperty = "easyconf:environment";
        assertEquals("The environment was not correctly read from the system property: " + sysProperty,
                     "local",
                     componentConf.getProperties().getString("test_module_environment"));

    }
    /**
     * Does not work due to a bug in digester (TODO: confirm)
     */
    public void bugtestXmlThatUsesNonExistentProperty() {
        String name = "module_with_xml_that_uses_non_existent_property";
        try {
            ComponentConfiguration conf = ConfReader.getInstance().
            	getComponentConfiguration(name);
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


}
