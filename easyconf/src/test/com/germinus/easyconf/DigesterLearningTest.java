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

import java.io.IOException;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.xml.sax.SAXException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <a href="DigesterLearningTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jorge Ferrer
 * @version $Revision$
 *
 */
public class DigesterLearningTest extends TestCase {
    public DigesterLearningTest(String testName) {
        super(testName);
    }
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new DigesterLearningTest("testXmlRulesDigester"));
        return suite;
    }
    public void testXmlRulesDigester() throws ClassNotFoundException, IOException, SAXException {
        URL digesterRulesUrl = ClasspathUtil.locateResource("test_module.digesterRules.xml");
        Digester digester = DigesterLoader.createDigester(digesterRulesUrl);
        
        Object configuration = readConfig(digester);
        DatabaseAssert.assertContents(configuration);
    }


    public void testProgramaticDigester() throws ClassNotFoundException, IOException, SAXException {
        Digester digester = new Digester();
        digester.addObjectCreate("database", "com.germinus.easyconf.Databases");
        digester.addObjectCreate("database/tables/table", "com.germinus.easyconf.Table");
        digester.addSetProperties("database/tables/table");
        digester.addSetNext("database/tables/table", "addTable", "com.germinus.easyconf.Table");
        
        Object configuration = readConfig(digester);

        DatabaseAssert.assertContents(configuration);
    }
    
    // .......................... Helper methods .........................
    
    private Object readConfig(Digester digester) throws IOException, SAXException {
        Object configuration;
        digester.setUseContextClassLoader(true);
        digester.setValidating(false);
        URL confFile = ClasspathUtil.locateResource(null, "test_module.xml");
        assertNotNull("Configuration file not found", confFile);
        configuration = digester.parse(confFile.openStream());
        return configuration;
    }
}
