/*
 * UnitTests.java -- Suite that runs all tests
 *
 * Copyright (C) 2001 by Germinus XXI
 *
 * Version: $Id$
 */
package com.germinus.easyconf;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Launches all the tests of EasyConf
 *
 * @version $Revision$
 */
public class UnitTests extends TestCase {

    private static final Log log = LogFactory.getLog(UnitTests.class);

    public UnitTests(String name) {
        super(name);
    }

    public static void main (String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite () {
        TestSuite suite = new TestSuite("Junit Tests");
//        suite.addTestSuite(ConfReaderTest.class);
        suite.addTest(ConfReaderTest.suite());
        return suite;
    }

    public void testSuccess() {
        assertTrue(true);
    }

    public void testFail() {
        fail("This test fails on purpose to check that junit is working. " 
             + "You can now safely remove it from UnitTests.java");
    }
}
