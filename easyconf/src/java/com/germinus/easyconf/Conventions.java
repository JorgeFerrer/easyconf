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

/**
 * Conventions used by EasyConf that can be expressed as contansts
 * @author  Jorge Ferrer
 * @version $Revision$
 *
 */
public interface Conventions {

    char SELECTOR_START = '[';
    char SELECTOR_END = ']';
    char DOT = '.';
    char SLASH = '-';

    String INCLUDE_PROPERTY = "include-and-override";
    String GLOBAL_CONFIGURATION_FILE = "global-configuration";

    String DIGESTERRULES_EXTENSION = ".digesterRules.xml";
    String XML_EXTENSION = ".xml";
    String PROPERTIES_EXTENSION = ".properties";

    String DATASOURCE_PREFIX = "datasource:";
    String JNDI_PREFIX = "jndi:";
}