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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.configuration.ConfigurationUtils;

import java.net.URL;

/**
 * Contains util methods to search in the classpath
 * 
 * @author jferrer
 */
public class ClasspathUtil {
    private static final Log log = LogFactory.getLog(ClasspathUtil.class);

    /**
     * Return the Class object of the specified class name by searching the
     * current classpath and the system classpath.
     *
     * @param name the name of the resource
     *
     * @return the <code>Class</code> instance
     */
    public static Class locateClass(String name) throws ClassNotFoundException {
        Class foundClass = null;
        if (foundClass == null) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try {
                foundClass = loader.loadClass(name);
                log.debug("Class loaded from the context classpath (" + name + ")");
            } catch (ClassNotFoundException ignore) {
            }
        }

        if (foundClass == null) {
            try {
                foundClass = ClassLoader.getSystemClassLoader().loadClass(name);
                log.debug("Class loaded from the system classpath (" + name + ")");
            } catch (ClassNotFoundException ignore) {
            }
        }
        if (foundClass == null) {
            throw new ClassNotFoundException("Class " + name + " was not found " +
                                             "in context classpath nor system classpath");
        }
        return foundClass;
    }

    /**
     * Return the location of the specified resource by searching the user home
     * directory, the current classpath and the system classpath.
     *
     * @param base the base path of the resource
     * @param name the name of the resource
     *
     * @return the location of the resource or <code>null</code> if it has not
     * been found
     */
    public static URL locateResource(String  base, String name) {
        return ConfigurationUtils.locate(base, name);
    }

    /**
     * Return the location of the specified resource by searching the user home
     * directory, the current classpath and the system classpath.
     *
     * @param name the name of the resource
     *
     * @return the location of the resource or <code>null</code> if it has not
     * been found
     */
    public static URL locateResource(String name) {
        return ConfigurationUtils.locate(null, name);
    }
}
