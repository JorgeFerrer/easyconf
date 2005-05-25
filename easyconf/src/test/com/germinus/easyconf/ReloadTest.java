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

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * System Test of the automatic reloading of files
 * 
 * @author Jorge Ferrer
 */
public class ReloadTest extends TestCase {

    private static final String INCLUDED_FILE_1 = "user-conf.properties";
    private static final String CONFIGURATION_DIR = "target/test-classes";
    private static final String COMPONENT_NAME = "reload_module";
    public static final Log log = LogFactory.getLog(ReloadTest.class);
    private static final String XML_1 = "<database><tables><table tableType=" +
    		"\"table1\"><name>users</name></table></tables></database>";
    private static final String XML_2 = "<database><tables><table tableType=" +
    		"\"table1\"><name>users</name></table>" +
    		"<table tableType=\"table2\"><name>users</name></table></tables></database>";
    private static final String DIGESTER_RULES = "<digester-rules><pattern value=\"database\">"+
    	"<object-create-rule classname=\"com.germinus.easyconf.DatabaseConf\"/>"+
    	"<pattern value=\"tables/table\">"+
    	"<object-create-rule classname=\"com.germinus.easyconf.Table\"/>"+
    	"<set-properties-rule/>"+
    	"<set-next-rule methodname=\"addTable\" paramtype=\"com.germinus.easyconf.Table\"/>"+
    	"</pattern>"+
    	"</pattern>"+
    	"</digester-rules>";


    File baseConf; 
    File includedFile1;
    File xmlConf;
    File rules;
    
    public ReloadTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        baseConf = new File(CONFIGURATION_DIR+ "/" + COMPONENT_NAME + ".properties");
        Properties props = new Properties();
        props.setProperty(Conventions.INCLUDE_PROPERTY, 
                INCLUDED_FILE_1 + ", jar-conf.properties");
        props.setProperty("reloaded-key", "invalid-value");

        FileUtil.write(baseConf, props);
        includedFile1 = new File(CONFIGURATION_DIR + "/" + INCLUDED_FILE_1);
        Properties props1 = new Properties();
        props1.setProperty(Conventions.RELOAD_DELAY_PROPERTY, "1");
        props1.setProperty("reloaded-key", "value1");
        FileUtil.write(includedFile1, props1);

        xmlConf = new File(CONFIGURATION_DIR + "/" + COMPONENT_NAME  + ".xml");
        FileUtil.write(xmlConf, XML_1);
        rules = new File(CONFIGURATION_DIR + "/" + COMPONENT_NAME  + Conventions.DIGESTERRULES_EXTENSION);
        FileUtil.write(rules, DIGESTER_RULES);
}
    protected void tearDown() throws Exception {
        baseConf.delete();
        includedFile1.delete();
        xmlConf.delete();
        rules.delete();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(ReloadTest.class);
        return suite;
    }

    // .............. Test methods ....................


    /**
     * Assumes that reloaded_module1.xml has 1 table and reloaded_module2.xml
     * has two tables.
     * @throws IOException
     * @throws InterruptedException
     */
    public void testReloadXmlFile() throws IOException, InterruptedException {
        DatabaseConf conf1 = getConfigurationObject();
        assertEquals("After the first read there should be 1 table", 1, conf1
                .getTables().size());
        Thread.sleep(1100);
        FileUtil.write(xmlConf, XML_2);
        DatabaseConf conf2 = getConfigurationObject();
        assertEquals("After the reload there should be 2 tables", 2, conf2
                .getTables().size());
    }

    public void testPropertiesReloading() throws IOException,
            InterruptedException {
        File dest = includedFile1;
        assertEquals("The first file wasn't read correctly",
                "value1", getComponentConf().getProperties().getString(
                        "reloaded-key"));

        Properties newProps = new Properties();
        newProps.setProperty("reloaded-key", "value2");
        dest.delete();
        Thread.sleep(1100);
        FileUtil.write(dest, newProps);        
        assertEquals("The file has not been reloaded!!",
                "value2", getComponentConf().getProperties().getString(
                        "reloaded-key"));

    }

    /** 
     * If the property Conventions.RELOAD_DELAY_PROPERTY is not 
     * used the file should not be reloaded
     */
    public void testDontReloadPropertiesFile() throws IOException,
    	InterruptedException {
        File dest = includedFile1;
        Properties propsWithoutReloading = new Properties();
        propsWithoutReloading.setProperty("reloaded-key", "value3");
        Thread.sleep(1100);
        FileUtil.write(dest, propsWithoutReloading);

        assertEquals("The first file wasn't read correctly",
                "value3", getComponentConf().getProperties().getString(
                        "reloaded-key"));
        Properties newProps = new Properties();
        newProps.setProperty("reloaded-key", "value4");
        // Setting the delay now should not affect as it won't be read
        newProps.setProperty(Conventions.RELOAD_DELAY_PROPERTY, "1");
        
        Thread.sleep(1100);
        FileUtil.write(dest, newProps);
        assertEquals("The file has been reloaded!!",
                "value4", getComponentConf().getProperties().getString(
                        "reloaded-key"));
        dest.delete();
    }

    /**
     * I haven't found how to automate this test using maven
     */
    public void unautomated_testReloadPropertiesFileInsideJAR() throws IOException,
            InterruptedException {
//        System.setProperty("test.classpath.dir", "target/test-classes");
//        Properties props = new Properties();
//        props.setProperty("jar-reloaded-key", "value1");
//        File xmlConf = new File(System.getProperty("test.classpath.dir")
//                + "/test-conf.jar");
//        FileUtil.writeAsJAR(xmlConf, "jar-conf.properties", props);
        assertEquals("After the first read the value should be value1",
                "value1", getComponentConf().getProperties().getString(
                        "jar-reloaded-key"));
//        props.setProperty("jar-reloaded-key", "value2");
        System.out.println("Please, switch JAR files before 5 seconds...");
        Thread.sleep(5000);
//        FileUtil.writeAsJAR(xmlConf, "user-conf.properties", props);
        assertEquals("After the first read the value should be value2",
                "value2", getComponentConf().getProperties().getString(
                        "jar-reloaded-key"));
//        xmlConf.delete();
    }

    // .............. Helper methods ...................

    DatabaseConf getConfigurationObject() {
        return (DatabaseConf) getComponentConf().getConfigurationObject();
    }

    private ComponentConfiguration getComponentConf() {
        return EasyConf.getConfiguration(COMPONENT_NAME);
    }

    private void assertEquals(String msg, String[] expected, String[] obtained) {
        if (expected.length != obtained.length) {
            fail(msg + ". Expected and obtained arrays length differ");
        }
        for (int i = 0; i < expected.length; i++) {
            assertEquals(msg + ". (" + i + "th element)", expected[i],
                    obtained[i]);
        }
    }

}