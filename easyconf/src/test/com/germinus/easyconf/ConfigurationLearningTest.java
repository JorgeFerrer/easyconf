/**
 * Copyright (c) 2000-2004 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.germinus.easyconf;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalXMLConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <a href="ConfigurationLearningTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jorge Ferrer
 * @version $Revision$
 *
 */
public class ConfigurationLearningTest extends TestCase {
    

    public ConfigurationLearningTest(String testName) {
        super(testName);
    }
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new ConfigurationLearningTest("testSubset"));
        return suite;
    }
    public void testHierarchicalConfiguration() throws ConfigurationException {
        HierarchicalXMLConfiguration conf = new HierarchicalXMLConfiguration();
        conf.load("test_module.xml");
        assertEquals("Error reading hierarchical property",
                "users",
                conf.getString("tables.table(0).name "));
    }
    public void testSubset() {
        Configuration conf = new PropertiesConfiguration();
        conf.setProperty("prefix.key", "value");
        Configuration subset = conf.subset("prefix");
        assertTrue("The subset functionality does not work", subset.containsKey("key"));
    }

}
