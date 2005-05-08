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

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.JNDIConfiguration;

/**
 * <a href="JndiURL.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jorge Ferrer
 * @version $Revision$
 *
 */
public class JndiURL {
    private static final String JNDI_PREFIX = Conventions.JNDI_PREFIX;
    private static InitialContext ctx = null;
    private String jndiPrefix;
    private String companyId;
    private String componentName;

    public JndiURL(String jndiPath, String companyId, String componentName) {
        this.jndiPrefix = jndiPrefix.substring(JNDI_PREFIX.length());
        this.companyId = companyId;
        this.componentName = componentName;
    }

    private synchronized InitialContext getContext() throws NamingException {
        if (ctx == null) {
            ctx = new InitialContext();
        }
        return ctx;
    }

    public static boolean isJndi(String sourcePath) {
        return sourcePath.startsWith(JNDI_PREFIX);
    }

    public String getPrefix() {
        return jndiPrefix;
    }

    public Configuration getConfiguration() {
        try {
            return new JNDIConfiguration(getPrefix());
        } catch (NamingException e) {
            throw new ConfigurationException("Error loading JNDI configuration for " 
                    + getPrefix());
        }
    }


}
