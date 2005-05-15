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

	public static final Log log =  LogFactory.getLog(ReloadTest.class);

    public ReloadTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
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
     */
    public void testReloadXmlFile() {
        File file1 = new File("target/test-classes/reloaded_module1.xml");
        File file2 = new File("target/test-classes/reloaded_module2.xml");
        File dest= new File("target/test-classes/reloaded_module.xml");
        dest.delete();
        boolean hecho=file1.renameTo(dest);        
        DatabaseConf conf1 = getConfigurationObject();
        assertEquals("After the first read there should be 1 table", 1,
                conf1.getTables().size());
        //In some Operating Systems deleting must be executed prior to move in order
        //to work correctly.
        dest.delete();
        hecho=file2.renameTo(dest);
        DatabaseConf conf2 = getConfigurationObject();
        assertEquals("After the reload there should be 2 tables", 2,
                conf2.getTables().size());
    }
    // .............. Helper methods ...................

    DatabaseConf getConfigurationObject() {
        return (DatabaseConf) getComponentConf().getConfigurationObject();
    }

    private ComponentConfiguration getComponentConf() {
        return EasyConf.getConfiguration("reloaded_module");
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
